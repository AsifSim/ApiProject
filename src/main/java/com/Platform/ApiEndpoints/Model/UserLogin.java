package com.Platform.ApiEndpoints.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Entity
@Data
@Builder
@NoArgsConstructor
public class UserLogin {
    @jakarta.persistence.Id
    String Id;
    String user_name;
    String login_type;

    public UserLogin(String id, String user_name, String login_type) {
        Id = id;
        this.user_name = user_name;
        this.login_type = login_type;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }
}
