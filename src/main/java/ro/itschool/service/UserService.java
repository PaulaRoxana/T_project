package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
  List<User> searchUser(String keyword);

  void getUser(User user);

  Optional<User> findByName(String name);

  //  User followUser(Long followerId, Long followedId);
  void followUser(Long followedId);


  //List<User> getFollowedUsers();

  //     User unfollowUser(Long followerId, Long followedId);
  void unfollowUser(Long followedId);

  void deleteById(Long id);

  Optional<User> findById(Long userId);

  void registerNewUser(User user);

  List<User> findAll();

  void save(User user);
}
