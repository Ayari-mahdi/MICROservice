package com.ms.msuser.Spring_Security_Jwt;

import com.ms.msuser.Entity.User;
import com.ms.msuser.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class myUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       Optional<User> user = userRepo.findByUserCin(userName);
       user.orElseThrow(()-> new UsernameNotFoundException("Not found" + userName));
       return user.map(MyUserDetails::new).get();
    }
}
