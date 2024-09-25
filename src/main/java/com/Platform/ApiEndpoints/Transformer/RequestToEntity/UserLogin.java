package com.Platform.ApiEndpoints.Transformer.RequestToEntity;

import com.Platform.ApiEndpoints.DTO.RequestDTO.SaveRequest;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;


public class UserLogin {
    public static com.Platform.ApiEndpoints.Model.UserLogin transform(String id,String user_name,String login_type){
        return com.Platform.ApiEndpoints.Model.UserLogin
                .builder()
                .Id(id)
                .login_type(login_type)
                .user_name(user_name)
                .build();
    }

    public static com.Platform.ApiEndpoints.Model.UserLogin transform(SaveRequest reqBody){
        return com.Platform.ApiEndpoints.Model.UserLogin
                .builder()
                .Id(reqBody.getUser_id())
                .login_type(reqBody.getLogin_type())
                .user_name(reqBody.getUser_name())
                .build();
    }
}
