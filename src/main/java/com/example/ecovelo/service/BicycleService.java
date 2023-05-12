
package com.example.ecovelo.service;
import java.time.Duration;
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
import com.example.ecovelo.jwt.JwtTokenProvider;
import com.example.ecovelo.repository.AccountModelRepository;
import com.example.ecovelo.repository.BicycleModelRepository;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.example.ecovelo.repository.UserModelRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class BicycleService {
	private final BicycleModelRepository bicycleModelRepository;
	private final JwtTokenProvider jwtService;
	private final AccountModelRepository accountRepository;
	private final MQTTService mqttService;
	private final RentBicycleModelRepository rentbicycleRepo;
	private final UserModelRepository userRepository;
	
	public boolean checkExistBicycleID(String id) {
		boolean existID=false;
		Optional<BicycleModel> bicycle= bicycleModelRepository.findById(id);
		if(bicycle.isPresent()) {
		if(bicycle.get().isStatus() && !bicycle.get().isUsing()){
			existID=true;
		}
		}
		return  existID;
	}
	private void updateBicycle(BicycleModel bicycle) {
		var updateBicycle = BicycleModel.builder()
				.id(bicycle.getId())
				.isStatus(bicycle.isStatus())
				.isUsing(!bicycle.isUsing())
				.coordinate(bicycle.getCoordinate())
				.bicycleStationModel(bicycle.getBicycleStationModel())
				.build();
		bicycleModelRepository.save(updateBicycle);
	}
	public int rentBicycle(String token, String bicycleID) {
		boolean checkExistBicycleID= checkExistBicycleID(bicycleID);
		if(checkExistBicycleID) {
		final String phoneNumber;
		phoneNumber = jwtService.extractUsername(token);
		var accountModel = accountRepository.findByPhoneNumber(phoneNumber).orElseThrow();	
		Callable<Boolean> callable = () -> mqttService.openBicycle(bicycleID);

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> future = executor.submit(callable);

		Boolean result = null;
		try {
		    result = future.get(); // đợi cho đến khi giá trị boolean được tính toán và trả về
		} catch (InterruptedException | ExecutionException e) {
		    e.printStackTrace();
		}
		Optional<UserModel> user= userRepository.findById(accountModel.getIdUser());
		var bicycle = bicycleModelRepository.findById(bicycleID);
		
		if(user.isPresent()&& bicycle.isPresent()) {
			updateBicycle(bicycle.get());
		var rentBicycle = RentBicycleModel.builder()
				.beginTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.coordinateStartRent(bicycle.get().getCoordinate())
				.userModelRent(user.get())
				.numFallBicycle(0)
				.bicycleModel(bicycle.get())
				.endTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.totalCharge(0)
				.build();
		rentbicycleRepo.save(rentBicycle);
		
		System.out.println("Result: " + result);
		return rentBicycle.getId();
			}
		}
		return -1;
	}
	public float endJourney(String bicycleID, int rentID) {
		Optional<BicycleModel> bicycle= bicycleModelRepository.findById(bicycleID);
		if(bicycle.isPresent()) {
		if(bicycle.get().isStatus() && bicycle.get().isUsing()){
			updateBicycle(bicycle.get());
			Optional<RentBicycleModel> rent= rentbicycleRepo.findById(rentID);
			if(rent.isPresent()) {
				RentBicycleModel rented= rent.get();
				float money=0;
				Instant startTime = Instant.ofEpochMilli(rent.get().getBeginTimeRent()); 
				Instant endTime = Instant.ofEpochMilli(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); 
				long durationInMillis = Duration.between(startTime, endTime).toMillis();
				long durationInMinutes = durationInMillis / (1000 * 60);
				if(durationInMinutes<30) {
					money=5000;
				}else {
					money=durationInMinutes*5000/30;
				}
				var rentBicycle = RentBicycleModel.builder()
						.id(rented.getId())
						.beginTimeRent(rented.getBeginTimeRent())
						.coordinateStartRent(rented.getCoordinateStartRent())
						.userModelRent(rented.getUserModelRent())
						.numFallBicycle(0)
						.bicycleModel(rented.getBicycleModel())
						.coordinateEndRent(rented.getCoordinateStartRent())
						.endTimeRent(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
						.totalCharge(money)
						.build();
				rentbicycleRepo.save(rentBicycle);
				Callable<Boolean> callable = () -> mqttService.openBicycle(bicycleID);

				ExecutorService executor = Executors.newSingleThreadExecutor();
				Future<Boolean> future = executor.submit(callable);

				Boolean result = null;
				try {
				    result = future.get(); // đợi cho đến khi giá trị boolean được tính toán và trả về
				} catch (InterruptedException | ExecutionException e) {
				    e.printStackTrace();
				}
				return money;
			}
		
		}
	}
		return -1;
	}
}
