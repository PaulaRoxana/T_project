package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;
import ro.itschool.entity.User;
import ro.itschool.repository.PostRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.PostService;
import ro.itschool.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final UserService userService;

//    public Post save(Post newPost) {
//        newPost.setTimestamp(LocalDate.now());
//        return postRepository.save(newPost);
//    }

  @Override
  public void save(Post post) {
    post.setTimestamp(LocalDateTime.now());
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    optionalLoggedInUser.ifPresent(loggedInUser -> {
      post.setUser(loggedUser);
      postRepository.save(post);
    });
  }

//  @Override
//  public List<Post> filterPosts(LocalDateTime timestamp) {
//    return postRepository.findByTimestampGreaterThan(timestamp);
//  }

  @Override
  public void deleteById(Long id) {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());

    if (optionalLoggedInUser.isPresent()) {
      User loggedInUser = optionalLoggedInUser.get();
      Optional<Post> post = postRepository.findById(id);

      post.ifPresent(p -> {
        if (p.getUser().getId().equals(loggedInUser.getId())) {
          loggedInUser.getPosts().remove(p);
          p.setUser(null);
          postRepository.save(p);
          postRepository.deleteLikesByPostId(id);
//         postRepository.deleteReplies(id);
          // postRepository.deleteById(id);
          postRepository.delete(p);
        } else {
          throw new AccessDeniedException("User not authorized to delete this post");
        }
      });
    } else {
      try {
        throw new ChangeSetPersister.NotFoundException();
      } catch (ChangeSetPersister.NotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public List<Post> getMyPosts() {
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findById(principal.getId());
    return postRepository.findByUserId(optionalLoggedInUser.get().getId())
      .stream()
      .toList();
  }

  @Override
  public List<Post> getPostsFromFollowedUsers() {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    User loggedInUser = optionalLoggedInUser.get();
    List<User> followedUsers = userRepository.getFollowedUsers(loggedInUser.getId())
      .stream()
      .map(elem -> new User(
        elem[0].toString(),
        elem[1].toString(),
        elem[2].toString(),
        elem[3].toString(),
        elem[4].toString()))
      .toList();
    return followedUsers.stream()
      .map(user -> postRepository.findByUserId(user.getId()))
      .flatMap(Collection::stream)
      .toList();
  }

  @Override
  public void copyPost(Long id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    optionalPost.ifPresent(p -> {
      Post post = new Post();
      post.setTimestamp(p.getTimestamp());
      post.setMessage(p.getMessage());
      save(post);
    });
  }

  @Override
  public List<Post> getPostWithMentions() {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    User loggedInUser = optionalLoggedInUser.get();
    List<User> allUsers = userRepository.findAll()
      .stream()
      .toList();
    return allUsers.stream()
      .map(user -> postRepository.findByUserId(user.getId()))
      .flatMap(Collection::stream)
      .filter(post -> post.getMessage().contains(optionalLoggedInUser.get().getUsername()))
      .toList();
  }

  @Override
  public Optional<Post> findById(Long postId) {
    return postRepository.findById(postId);
  }


  @Override
  public void likePost(Long postId) {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    optionalLoggedInUser.ifPresent(loggedInUser -> {
      Optional<Post> postToLike = postRepository.findById(postId);
      postToLike.ifPresent(pToLike -> postRepository.insertIntoLikesTable(loggedInUser.getId(), pToLike.getId()));
    });
  }

  @Override
  public void unLikePost(Long postId) {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    optionalLoggedInUser.ifPresent(loggedInUser -> {
      Optional<Post> postToLike = postRepository.findById(postId);
      postToLike.ifPresent(pToLike -> postRepository.deleteFromLikesTable(loggedInUser.getId(), pToLike.getId()));
    });
  }

  @Override
  public List<User> getUsersWhoLikePost(Long postId) {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    User loggedInUser = optionalLoggedInUser.get();
    Optional<Post> optionalLikedPost = postRepository.findById(postId);
    Post likedPost = optionalLikedPost.get();

    return postRepository.getUsersWhoLikePost(likedPost.getId())
      .stream()
      .map(user -> new User(user.getUsername()))
      .toList();
  }
}


