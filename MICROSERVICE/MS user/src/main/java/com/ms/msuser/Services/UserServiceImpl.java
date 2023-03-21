package com.ms.msuser.Services;

import com.ms.msuser.Entity.User;
import com.ms.msuser.Interfaces.IuserService;
import com.ms.msuser.Repository.UserRepo;
import com.ms.msuser.Spring_Security_Jwt.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IuserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private myUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Map<String,Object> createAuthenticationToken(authenticationrequest authenticationrequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationrequest.getUserCin(), authenticationrequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username and password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationrequest.getUserCin());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Map map=new HashMap();
        map.put("user",userRepo.findByUserCin(authenticationrequest.getUserCin()));
        map.put("tokken",new authenticationresponse(jwt));
      //  return ResponseEntity.ok(new authenticationresponse(jwt));ResponseEntity<?>
        return map;

    }

    @Override
    public User adduser(User user) throws Exception {
        if (!userRepo.findByUserCin(user.getUserCin()).isPresent()) {
            String genpwd = generateCommonLangPassword();
            System.out.println("password : " + genpwd);
            user.setPassword(passwordEncoder.encode(genpwd));

            User result = userRepo.saveAndFlush(user);
            sendEmail(user, genpwd);
            return result;
        } else {
            log.error("user cin exist: " + user.getUserCin());
            throw new ResourceNotFoundException("User cin exist");
        }
    }

    @Override
    public User update(User user) {
        return userRepo.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long UserId) {
        userRepo.deleteById(UserId);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getbyID(Long UserId) {
        return userRepo.findById(UserId).get();
    }

    @Override
    public void NewPassword(Long UserId, String password) throws Exception {
        User u = userRepo.findById(UserId).get();
        if (u.getActive() == 0) {
            u.setPassword(passwordEncoder.encode(password));
            u.setActive(1L);
            userRepo.save(u);
            sendEmail(u, password);
        } else {
            throw new Exception("account active");
        }

    }

    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 40, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public void sendEmail(User user, String password) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        String htmlMsg = "<body style='border:1px solid black;border-radius:5px;padding:10px;'>"
                + "- " + user.getUserCin() + " Your onetime password for registration is : <h3 style='color:red;'>" + password
                + "</h3> Please use this password to complete your new user registration on your first log in." +
                "</body>";
        String htmlMsg2 = "<body style='border:1px solid black;border-radius:5px;padding:10px;'>"
                + "- " + user.getUserCin() + " Your new password is : <h3 style='color:red;'>" + password
                + "</h3></body>";

        final String[] emailbody = new String[2];
        emailbody[0] = user.getUserCin();

        try {
            log.info("sending email");
            //SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //  simpleMailMessage.setFrom("ayarii.mahdii@gmail.com");
            // simpleMailMessage.setTo(user.getEmail());
            helper.setTo(user.getEmail());
            if (user.getActive() == 0) {
                message.setContent(htmlMsg, "text/html");
                //simpleMailMessage.setSubject("Welcome to Municipalite ");
                //simpleMailMessage.setText("- CIN :" + emailbody[0]   + " - PASSWORD : "+ password );
                helper.setSubject("Welcome to Municipalite ");
            } else {
                message.setContent(htmlMsg2, "text/html");
                //simpleMailMessage.setText("- CIN :" + emailbody[0]   + " - NEW PASSWORD : "+ password );
                helper.setSubject("new password ");
            }
            javaMailSender.send(message);
            log.info("email sent");
        } catch (Exception e) {
            throw new Exception("invalid mail");
        }
    }
}
