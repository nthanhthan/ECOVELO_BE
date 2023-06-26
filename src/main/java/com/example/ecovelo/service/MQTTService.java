package com.example.ecovelo.service;


import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class MQTTService {
	 Mqtt5BlockingClient client;
	private final RentBicycleModelRepository rentbicycleRepo;
	public void connectMQTT() {
		//final boolean isOpened;
		final String host = "9dff104817e54495b402e7a024636147.s2.eu.hivemq.cloud";
        final String username = "ecovelo";
        final String password = "0901948483";

        // create an MQTT client
     client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

        // connect to HiveMQ Cloud with TLS and username/pw
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");
	;
	}
	public boolean rentBicycle(String bicycleID, String type) {
		connectMQTT();
		  client.subscribeWith()
          .topicFilter(bicycleID)
          .send();
        client.publishWith()
        .topic(bicycleID)
        .payload(UTF_8.encode(type))
        .send();
        CountDownLatch latch = new CountDownLatch(1);
        client.toAsync().publishes(ALL, publish -> {
            System.out.println("Received message: " +
                publish.getTopic() + " -> " +
                UTF_8.decode(publish.getPayload().get()));
            latch.countDown(); // mở latch khi nhận được tin nhắn
        });
        try {
            latch.await();
            client.disconnect();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
	}
	public boolean fallBicycle(int rentID) {
	    connectMQTT();
	    AtomicBoolean isFall = new AtomicBoolean(false);
	    AtomicBoolean shouldKeepListening = new AtomicBoolean(true);
	    while (shouldKeepListening.get()) {
	    client.toAsync().publishes(ALL, publish -> {
	        String message = UTF_8.decode(publish.getPayload().get()).toString();
	        System.out.println("Received message: " +
	            publish.getTopic() + " -> " +
	            UTF_8.decode(publish.getPayload().get()));
	        if (message.equals("fall")) {
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
	                System.out.println("fall");
	                isFall.set(true);
	            }
	        } else if (message.equals("1")) {
	            shouldKeepListening.set(false);
	        }
	    });
	    }
	    client.disconnect();
	    return isFall.get();
	}
	

}
