package com.aminmessenger.mymessenger.Controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aminmessenger.mymessenger.Entity.User;
import com.aminmessenger.mymessenger.Service.ContactService;
import com.aminmessenger.mymessenger.Service.UserService;
import com.aminmessenger.mymessenger.dto.ContactResponseDto;
import com.aminmessenger.mymessenger.dto.DashboardResponseDto;

@RestController
@RequestMapping("/dashboard")
public class Dashboard {

    private final UserService userService;
    private final ContactService contactService;

    public Dashboard(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @GetMapping("")
    public ResponseEntity<DashboardResponseDto> getDashboard(Authentication authentication) {

        User user = userService.getUserByEmail(authentication.getName()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        DashboardResponseDto dashboardResponseDto = new DashboardResponseDto(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());

        return ResponseEntity.ok(dashboardResponseDto);
    }

    @PostMapping("/add-contact")
    public ResponseEntity<String> addContact(@RequestBody String email, Authentication authentication) {

        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not Found"));

        User contact = userService.getUserByEmail(email).orElseThrow(() -> new RuntimeException("Contact not Found"));

        contactService.addContact(user.getId(), contact.getId());

        return ResponseEntity.ok("Added");
    }

    @DeleteMapping("/delete-contact")
    public ResponseEntity<String> deleteContact(@RequestBody String email, Authentication authentication) {

        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not Found"));

        User contact = userService.getUserByEmail(email).orElseThrow(() -> new RuntimeException("Contact not Found"));

        contactService.removeContact(user.getId(), contact.getId());

        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/contacts")
    public ResponseEntity<Set<ContactResponseDto>> getAllContacts(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<ContactResponseDto> contacts
                = userService.findAllContactByUserId(user.getId())
                        .stream()
                        .map(contact -> new ContactResponseDto(
                                contact.getId(),
                                contact.getFirstName(),
                                contact.getEmail()
                        ))
                        .collect(Collectors.toSet());

        return ResponseEntity.ok(contacts);
    }

}
