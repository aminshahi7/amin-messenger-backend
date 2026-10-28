package com.aminmessenger.mymessenger.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Repository.UserRepository;
import com.aminmessenger.mymessenger.dto.UserRequestDto;

@Service 
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public User save(UserRequestDto reqDto){
        User user = new User();
        user.setFirstName(reqDto.getFirstName());
        user.setLastName(reqDto.getLastName());
        user.setUsername(reqDto.getUsername());
        user.setEmail(reqDto.getEmail());
        user.setPassword(hashPassword(reqDto.getPassword()));

        userRepository.save(user);

        return user;
    }

    
}
