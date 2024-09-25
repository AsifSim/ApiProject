package com.Platform.ApiEndpoints.Kafka;

import com.Platform.ApiEndpoints.DTO.RequestDTO.SaveRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class SaveRequestSerializer implements Serializer<SaveRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, SaveRequest data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing SaveRequest", e);
        }
    }
}

