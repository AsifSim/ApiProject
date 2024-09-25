package com.Platform.ApiEndpoints.DTO.RequestDTO;

public class SaveRequest {

    String user_id;
    String user_name;
    String login_type;

    public SaveRequest(String userId, String userName, String login_type) {
        this.user_id = userId;
        this.user_name = userName;
        this.login_type = login_type;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }
}
