package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ro.itschool.entity.MyRole;
import ro.itschool.entity.Post;
import ro.itschool.entity.RoleName;
import ro.itschool.entity.User;
import ro.itschool.repository.PostRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RunAtStartup {
  private final UserService userService;
  private final PostRepository postRepository;
  private final RoleRepository roleRepository;

  @EventListener(ApplicationReadyEvent.class)

  public void insertUsersIntoDb() {
    roleRepository.save(new MyRole(RoleName.ROLE_USER));
    roleRepository.save(new MyRole(RoleName.ROLE_ADMIN));

    User user1 = new User();
    String encryptedPassword = new BCryptPasswordEncoder().encode("pass");
    user1.setFirstName("First");
    user1.setLastName("User1");
    user1.setUsername("firstUser1");
    user1.setEmail("first.user1@email.com");
    user1.setPassword(encryptedPassword);
    List<Post> posts = List.of(new Post("this is a test post 1", LocalDateTime.now(), user1),
      new Post("this is a test post 2", LocalDateTime.now(), user1),
      new Post("this is a test post 3", LocalDateTime.now(), user1));
    user1.setPosts(posts);
//    userService.save(user1);
//    postRepository.saveAll(posts);
    userService.registerNewUser(user1);

    User user2 = new User();
    encryptedPassword = new BCryptPasswordEncoder().encode("pass");
    user2.setFirstName("Second");
    user2.setLastName("User2");
    user2.setUsername("secondUser2");
    user2.setEmail("second.user2@email.com");
    user2.setPassword(encryptedPassword);
    List<Post> posts1 = List.of(new Post("this is another test post", LocalDateTime.now(), user2),
      new Post("yet another test post", LocalDateTime.now(), user2),
      new Post("post 3 of user 2", LocalDateTime.now(), user2));
    user2.setPosts(posts1);
//    userService.save(user2);
//    postRepository.saveAll(posts1);
    userService.registerNewUser(user2);

    User user3 = new User();
    encryptedPassword = new BCryptPasswordEncoder().encode("pass");
    user3.setFirstName("Third");
    user3.setLastName("User3");
    user3.setUsername("thirdUser3");
    user3.setEmail("third.user3@email.com");
    user3.setPassword(encryptedPassword);
    List<Post> posts3 = List.of(new Post("Test post 3", LocalDateTime.now(), user3),
      new Post("test 2 for user3", LocalDateTime.now(), user3),
      new Post("the last post for user 3", LocalDateTime.now(), user3));
    user3.setPosts(posts3);
//    userService.save(user3);
//    postRepository.saveAll(posts3);
    userService.registerNewUser(user3);
  }
}
