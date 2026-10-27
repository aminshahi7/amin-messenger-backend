package com.aminmessenger.mymessenger.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Repository.UserRepository;
import com.aminmessenger.mymessenger.dto.UserRequestDto;

@Service 
public class UserService {

    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    public List<User> getAll(){
        return userRepository.findAll();
    };

    public User registerUser(
            UserRequestDto dto,
            MultipartFile profilePicture
    ) throws IOException {

        // Prüfen, ob der Username bereits existiert
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        // Prüfen, ob die E-Mail bereits existiert
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        // Neue User-Entity erstellen
        User user = new User();

        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        // Passwort hashen
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Profilbild speichern, wenn eines hochgeladen wurde
        if (profilePicture != null && !profilePicture.isEmpty()) {
            user.setProfilePicture(profilePicture.getBytes());
        }

        // Benutzer in der Datenbank speichern
        return userRepository.save(user);
    }  
}
