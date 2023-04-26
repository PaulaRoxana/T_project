package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.User;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public List<User> searchUser(String keyword) {
    if (keyword == null) {
      keyword = "";
    }
    return userRepository.searchUserBy(keyword);
  }

  @Override
  public void getUser(User user) {
    userRepository.findById(user.getId());
  }

  @Override
  public Optional<User> findByName(String name) {
    return userRepository.findByName(name);
  }


  @Override
  public void followUser(Long followedId) {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());

    optionalLoggedInUser.ifPresent(loggedInUser -> {
      Optional<User> toBeFollowed = userRepository.findById(followedId);
      toBeFollowed.ifPresent(followedUser -> userRepository.insertIntoFollowTable(loggedInUser.getId(), followedUser.getId()));
    });
  }


@Override
public void unfollowUser(Long followedId) {
  User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());

  optionalLoggedInUser.ifPresent(loggedInUser -> {
    Optional<User> toBeFollowed = userRepository.findById(followedId);
    toBeFollowed.ifPresent(followedUser -> userRepository.deleteFromFollowedTable(loggedInUser.getId(), followedUser.getId()));
  });
}

  @Override
  public List<User> getFollowedUsers() {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    return userRepository.getFollowedUsers(optionalLoggedInUser.get().getId())
      .stream()
      .map(elem -> new User(elem[0].toString(), elem[1].toString(), elem[2].toString(), elem[3].toString(), elem[4].toString()))
      .toList();
  }

  @Override
  public void deleteById(Long id) {
    Optional<User> user = userRepository.findById(id);
    user.ifPresent(user1 -> {
      userRepository.deleteFollower(id);
      // userRepository.deleteByUserId(user1.getId());
      userRepository.deleteById(id);
    });
  }

  @Override
  public Optional<User> findById(Long userId) {
    return userRepository.findById(userId);
  }

  @Override
  public void registerNewUser(User user) {
    userRepository.save(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

}
