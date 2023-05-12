package com.example.ecovelo.service;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.springframework.stereotype.Service;


import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import lombok.RequiredArgsConstructor;
import java.util.concurrent.CountDownLatch;

@Service
@RequiredArgsConstructor
public class MQTTService {
	 Mqtt5BlockingClient client;
	public void connectMQTT() {
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

        // subscribe to the topic "my/test/topic"
//        client.subscribeWith()
//                .topicFilter("testTopic")
//                .send();
//
//        // set a callback that is called when a message is received (using the async API style)
//        client.toAsync().publishes(ALL, publish -> {
//            System.out.println("Received message: " +
//                publish.getTopic() + " -> " +
//                UTF_8.decode(publish.getPayload().get()));
//
//            // disconnect the client after a message was received
//           // client.disconnect();
//        });
//
//        // publish a message to the topic "my/test/topic"
//        client.publishWith()
//                .topic("testTopic")
//                .payload(UTF_8.encode("Hello"))
//                .send();
	}
	public boolean openBicycle(String bicycleID) {
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
		  client.subscribeWith()
          .topicFilter(bicycleID)
          .send();
        client.publishWith()
        .topic(bicycleID)
        .payload(UTF_8.encode("HelloIOT"))
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
	

}