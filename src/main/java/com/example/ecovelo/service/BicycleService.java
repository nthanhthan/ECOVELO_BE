
package com.example.ecovelo.service;

import java.time.Duration;
import com.example.ecovelo.entity.Problem;
import com.example.ecovelo.entity.ReportProblem;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.time.ZoneId;
import org.springframework.stereotype.Service;
import com.example.ecovelo.entity.BicycleModel;
import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.entity.UserModel;
import com.example.ecovelo.repository.BicycleModelRepository;
import com.example.ecovelo.repository.ProblemRepository;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.example.ecovelo.repository.ReportProblemRepository;
import com.example.ecovelo.request.ReportProblemRequest;
import com.example.ecovelo.request.TransactionRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BicycleService {
	private final BicycleModelRepository bicycleModelRepository;
	private final MQTTService mqttService;
	private final RentBicycleModelRepository rentbicycleRepo;
	private final AuthService authService;
	private final TransactionHistoryService transactionService;
	private final ReportProblemRepository  reportProblemRepo;
	private final ProblemRepository problemRepo;

	public boolean checkExistBicycleID(String id) {
		boolean existID = false;
		Optional<BicycleModel> bicycle = bicycleModelRepository.findById(id);
		if (bicycle.isPresent()) {
			if (bicycle.get().isStatus() && !bicycle.get().isUsing()) {
				existID = true;
			}
		}
		return existID;
	}


	private void updateBicycle(BicycleModel bicycle) {
		var updateBicycle = BicycleModel.builder().id(bicycle.getId()).isStatus(bicycle.isStatus())
				.isUsing(!bicycle.isUsing()).coordinate(bicycle.getCoordinate())
				.bicycleStationModel(bicycle.getBicycleStationModel()).build();
		bicycleModelRepository.save(updateBicycle);
	}

	public int rentBicycle(String token, String bicycleID) {
		boolean checkExistBicycleID = checkExistBicycleID(bicycleID);
		if (checkExistBicycleID) {
			Callable<Boolean> callable = () -> mqttService.rentBicycle(bicycleID,"0");
			ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<Boolean> future = executor.submit(callable);

			Boolean result = null;
			try {
				result = future.get(); // đợi cho đến khi giá trị boolean được tính toán và trả về
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			UserModel user = authService.getUserByToken(token);
			var bicycle = bicycleModelRepository.findById(bicycleID);
			if (user != null && bicycle.isPresent() && authService.checkPointUser(token)) {
				updateBicycle(bicycle.get());
				var rentBicycle = RentBicycleModel.builder()
						.beginTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
						.coordinateStartRent(bicycle.get().getCoordinate())
						.userModelRent(user).numFallBicycle(0)
						.bicycleModel(bicycle.get())
						.endTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
						.totalCharge(0).build();
				rentbicycleRepo.save(rentBicycle);

				System.out.println("Result: " + result);
				return rentBicycle.getId();
			}
		}
		return -1;
	}

	public UserModel endJourney(String bicycleID, int rentID) {
		Optional<BicycleModel> bicycle = bicycleModelRepository.findById(bicycleID);
		if (bicycle.isPresent()) {
			if (bicycle.get().isStatus() && bicycle.get().isUsing()) {
				updateBicycle(bicycle.get());
				Optional<RentBicycleModel> rent = rentbicycleRepo.findById(rentID);
				if (rent.isPresent()) {
					RentBicycleModel rented = rent.get();
					float money = 0;
					Instant startTime = Instant.ofEpochMilli(rent.get().getBeginTimeRent());
					Instant endTime = Instant.ofEpochMilli(
							LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
					long durationInMillis = Duration.between(startTime, endTime).toMillis();
					long durationInMinutes = durationInMillis / (1000 * 60);
					if (durationInMinutes < 30) {
						money = 5000;
					} else {
						System.out.println("money: " );
						money = Math.round(durationInMinutes * 5000 / 30);
						System.out.println(money );
					}
					authService.payRentBicycle(money, rented.getUserModelRent());
					var rentBicycle = RentBicycleModel.builder().id(rented.getId())
							.beginTimeRent(rented.getBeginTimeRent())
							.coordinateStartRent(rented.getCoordinateStartRent())
							.coordinateEndRent(rented.getCoordinateStartRent())
							.userModelRent(rented.getUserModelRent()).numFallBicycle(0)
							.bicycleModel(rented.getBicycleModel()).coordinateEndRent(rented.getCoordinateStartRent())
							.endTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
							.totalCharge(money).build();
					rentbicycleRepo.save(rentBicycle);
					TransactionRequest  transactionRequest= new TransactionRequest(money,"Riding "+bicycleID,true, true);
					transactionService.createTransactionHistory(rented.getUserModelRent(), transactionRequest);
					Callable<Boolean> callable = () -> mqttService.rentBicycle(bicycleID,"1");

					ExecutorService executor = Executors.newSingleThreadExecutor();
					Future<Boolean> future = executor.submit(callable);

					Boolean result = null;
					try {
						result = future.get();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
					return authService.getUser(null, rented.getUserModelRent());
				}

			}
		}
		return null;
	}

	public boolean reportProblem(ReportProblemRequest reportReq) {
		boolean checkID = checkBicycleReport(reportReq.getIdBicycle());
		if (checkID) {
			Optional<Problem> problem = problemRepo.findById(reportReq.getIdProblem());
			UserModel user = authService.getUserByToken(reportReq.getToken());
			if (problem.isPresent()) {
				Problem problemModel = Problem.builder().id(problem.get().getId())
						.nameProblem(problem.get().getNameProblem()).build();
				var report = ReportProblem.builder()
						.createAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
						.desciption(reportReq.getDescription()).idBicycle(reportReq.getIdBicycle())
						.uploadImage(reportReq.getUrlImage()).problemModel(problemModel).userModelReport(user).build();
				reportProblemRepo.save(report);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public boolean checkBicycleReport(String id) {
		Optional<BicycleModel> bicycle = bicycleModelRepository.findById(id);
		if (bicycle.isPresent()) {
		return true;
		}
		return false;
	}
}
