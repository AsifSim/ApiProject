package com.Platform.ApiEndpoints.Service.Impl;

import com.Platform.ApiEndpoints.DTO.RequestDTO.SaveRequest;
import com.Platform.ApiEndpoints.Kafka.SaveRequestSerializer;
import com.Platform.ApiEndpoints.Service.KafkaProducerService;
import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    public String addMessage(String topic, SaveRequest reqBody, String key) {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SaveRequestSerializer.class.getName());
        Producer<String, SaveRequest> producer = new KafkaProducer<>(prop);

        ProducerRecord<String, SaveRequest> record = new ProducerRecord<>(topic, key, reqBody);
        try {
            // Asynchronous send with a callback for success/failure
            Future<RecordMetadata> future = producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    // Success: Log or return success information
                    System.out.println("Message sent successfully to topic: " + metadata.topic() +
                            " partition: " + metadata.partition() +
                            " offset: " + metadata.offset());
                } else {
                    // Failure: Log or handle the error
                    exception.printStackTrace();
                }
            });
            future.get(); // This will block until the message is acknowledged or fails
            return "Message sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Message sending failed: " + e.getMessage();
        } finally {
            producer.close();
        }
    }
}
