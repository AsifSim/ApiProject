package com.Platform.ApiEndpoints.Service;


import com.Platform.ApiEndpoints.DTO.RequestDTO.SaveRequest;

public interface KafkaProducerService {
    public String addMessage(String topic, SaveRequest reqBody, String key);
}
