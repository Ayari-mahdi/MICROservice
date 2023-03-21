package com.ms.msuser.Controller;

import com.ms.msuser.Entity.User;
import com.ms.msuser.Repository.UserRepo;
import com.ms.msuser.Services.UserServiceImpl;
import com.ms.msuser.Spring_Security_Jwt.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
//@Api(tags = "Gestion des categories Produit")
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServiceImpl userService;


    //********************************************************************************************* LOGIN

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> createAuthenticationToken(@RequestBody authenticationrequest authenticationrequest) throws Exception {
        return userService.createAuthenticationToken(authenticationrequest);
    }

    //********************************************************************************************* REGISTRATION
    @PostMapping("/registrate")
    public User adduser(@RequestBody User user) throws Exception {
        return userService.adduser(user);
    }

    //********************************************************************************************* DELETE USER
    @DeleteMapping("/delete/{userid}")
    public void deleteUser(@PathVariable("userid") Long UserId) {
        userService.deleteUser(UserId);
    }
    //********************************************************************************************* update
    @PutMapping("/update")
    public User update(@RequestBody User user)  {
        return userService.update(user);
    }

    @GetMapping("/AllUsers")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userid}")
    public User getbyId(@PathVariable("userid") Long UserId) {
        return userService.getbyID(UserId);
    }

    @PostMapping("/newpassword/{userid}")
    public void newpassword(@PathVariable("userid") Long UserId, @RequestBody String password) throws Exception {
        userService.NewPassword(UserId, password);
    }
}