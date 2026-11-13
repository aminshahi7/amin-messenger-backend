package com.aminmessenger.mymessenger.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Repository.UserRepository;
import com.aminmessenger.mymessenger.dto.DashboardResponseDto;


@RestController 
@RequestMapping("/dashboard")
public class Dashboard {

    private final UserRepository userRepository;

    public Dashboard(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @GetMapping("")
    public ResponseEntity<DashboardResponseDto> getDashboard(Authentication authentication){

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        DashboardResponseDto dashboardResponseDto = new DashboardResponseDto(user.getUsername(), user.getFirstnName(), user.getLastName(), user.getEmail());


        return ResponseEntity.ok(dashboardResponseDto);
    }
    
}
