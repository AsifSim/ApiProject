package com.Platform.ApiEndpoints.Repository;

import com.Platform.ApiEndpoints.Model.UserLogin;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoginRepo extends JpaRepository<UserLogin,Integer>{
}
