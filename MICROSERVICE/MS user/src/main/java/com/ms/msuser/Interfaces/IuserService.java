package com.ms.msuser.Interfaces;

import com.ms.msuser.Entity.User;
import com.ms.msuser.Spring_Security_Jwt.authenticationrequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IuserService {
    Map<String,Object> createAuthenticationToken(authenticationrequest auth) throws Exception;
    User adduser(User user) throws Exception;
    User update(User user) ;
    void deleteUser(Long UserId);
    List<User> getAll();
    User getbyID(Long UserId);
    void NewPassword(Long UserId,String password) throws Exception;


}
