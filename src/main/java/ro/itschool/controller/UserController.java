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

  //1.1 Get all users
  @GetMapping(value = "/all")
  public List<String> getAllUsers() {
    return userService.findAll().stream().map(User::getUsername).toList();
  }


  //2. Search : search for other users by username, firstname or lastname
  @GetMapping(value = "/search")
  public List<String> searchUser(@RequestParam String keyword) {
    return userService.searchUser(keyword).stream().map(User::getUsername).toList();
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
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    // userService.deleteById(id);
    if (userRepository.existsById(id)) {
      userService.deleteById(id);
      return ResponseEntity.ok().build();
    } else
      return ResponseEntity.badRequest().build();


  }


}
