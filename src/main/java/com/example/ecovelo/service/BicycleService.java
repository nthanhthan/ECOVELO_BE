
package com.example.ecovelo.service;

import java.time.Duration;
import com.example.ecovelo.entity.Problem;
import com.example.ecovelo.entity.ReportProblem;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.example.ecovelo.repository.CoordinateModelRepository;
import com.example.ecovelo.repository.ProblemRepository;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.example.ecovelo.repository.ReportProblemRepository;
import com.example.ecovelo.request.BicycleReq;
import com.example.ecovelo.request.ReportProblemRequest;
import com.example.ecovelo.request.StopRent;
import com.example.ecovelo.request.TransactionRequest;
import com.example.ecovelo.response.StopRentResponse;

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
	private final CoordinateModelRepository coordinateRepo;
	private final StationService stationService;
	
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


	private void updateBicycle(BicycleModel bicycle, int stationID) {
		var station= stationService.getStationByID(stationID);	
		if(station!=null) {
		var updateBicycle = BicycleModel.builder().id(bicycle.getId()).isStatus(bicycle.isStatus())
				.isUsing(!bicycle.isUsing())
				.coordinate(station.getCoordinate())
				.bicycleStationModel(station)
				.build();
		bicycleModelRepository.save(updateBicycle);
		}
	}
	public void  fallBicycle(int rentID) {
		  Optional<RentBicycleModel> findRent=rentbicycleRepo.findById(rentID);
          if (findRent.isPresent()) {
              RentBicycleModel rented= findRent.get();
              var rentBicycle = RentBicycleModel.builder()
                      .id(rented.getId())
                      .beginTimeRent(rented.getBeginTimeRent())
                      .coordinateStartRent(rented.getCoordinateStartRent())
                      .coordinateEndRent(rented.getCoordinateEndRent()) // sửa đổi
                      .userModelRent(rented.getUserModelRent())
                      .numFallBicycle(rented.getNumFallBicycle()+1)
                      .bicycleModel(rented.getBicycleModel())
                      .endTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                      .totalCharge(0)
                      .build();
              rentbicycleRepo.save(rentBicycle);
              }
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
			if (user != null && bicycle.isPresent() && authService.checkPointUser(token) && user.isVerify()  ) {
				updateBicycle(bicycle.get(),bicycle.get().getBicycleStationModel().getId());
				var rentBicycle = RentBicycleModel.builder()
						.beginTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
						.coordinateStartRent(bicycle.get().getBicycleStationModel().getCoordinate())
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

	public StopRentResponse endJourney(StopRent stopRent) {
		Optional<BicycleModel> bicycle = bicycleModelRepository.findById(stopRent.getBicycleID());
		if (bicycle.isPresent()) {
			if (bicycle.get().isStatus() && bicycle.get().isUsing()) {
				updateBicycle(bicycle.get(),stopRent.getStationID());
				Optional<RentBicycleModel> rent = rentbicycleRepo.findById(stopRent.getRentID());
				var station= stationService.getStationByID(stopRent.getStationID());	
				if (rent.isPresent()&& station!=null) {
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
							.coordinateEndRent(station.getCoordinate())
							.userModelRent(rented.getUserModelRent())
							.numFallBicycle(rented.getNumFallBicycle())
							.bicycleModel(rented.getBicycleModel())
							.endTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
							.totalCharge(money).build();
					rentbicycleRepo.save(rentBicycle);
					TransactionRequest  transactionRequest= new TransactionRequest(money,"Riding "+stopRent.getBicycleID(),true, true);
					transactionService.createTransactionHistory(rented.getUserModelRent(), transactionRequest);
					Callable<Boolean> callable = () -> mqttService.rentBicycle(stopRent.getBicycleID(),"1");
					ExecutorService executor = Executors.newSingleThreadExecutor();
					Future<Boolean> future = executor.submit(callable);

					Boolean result = null;
					try {
						result = future.get();
						if(result==true) {
							StopRentResponse stopRentResp= StopRentResponse.builder()
									.idRent(rented.getId())
									.numFall(rented.getNumFallBicycle())
									.build();
						return stopRentResp;
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
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
	public void createBicycle(List<BicycleReq> bicycles) {
		List<BicycleModel> bicyclelList= new ArrayList<BicycleModel>();
		for(int i=0;i<bicycles.size();i++) {
			var coordinate = coordinateRepo.findById(bicycles.get(i).getCoordinate_id());
			var station= stationService.getStationByID(bicycles.get(i).getId_bicycle_station());			
					if(coordinate.isPresent() && station!=null) {
				bicyclelList.add(BicycleModel
						.builder()
						.id(bicycles.get(i).getId())
						.bicycleStationModel(station)			
						.coordinate(coordinate.get())
						.isUsing(bicycles.get(i).isUsing())
						.isStatus(bicycles.get(i).isStatus())
						.build());
			}
		}
		bicycleModelRepository.saveAll(bicyclelList);	
	}
}
