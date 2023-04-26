package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.User;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

import java.util.List;

@ToString
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //1. Register : allow users to register with a unique username, a first name and a last name, an e-mail and a password


    //1.1 Get all users
    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }


    //2. Search : search for other users by username, firstname or lastname
    @GetMapping(value = "/search")
    public List<User> searchUser(@RequestParam String keyword) {
        return userService.searchUser(keyword);
    }


    // 3. Follow : follow another user and start receiving his public posts
    @PostMapping(value = "/follow/{id}")
    public ResponseEntity<Void> followUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userService.followUser(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }


    //4. Unfollow : unfollow a user and stop receiving feeds from this user
    @PostMapping(value = "/unfollow/{id}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userService.unfollowUser(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }

    //3-4.1
    @GetMapping(value = "/followed")
    public List<String> getFollowedUsers() {
        return userService.getFollowedUsers().stream().map(User::getUsername).toList();
    }

    //5. Unregister : remove user and all his posts

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }


}
