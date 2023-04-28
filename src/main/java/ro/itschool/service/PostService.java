package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;
import ro.itschool.entity.Reply;
import ro.itschool.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

  void save(Post post);

//  List<Post> filterPosts(LocalDateTime timestamp);

  void deleteById(Long id);

  void copyPost(Long id);

  void likePost(Long id);

  void unLikePost(Long id);

  List<Post> getPostWithMentions();

  Optional<Post> findById(Long postId);

  List<Post> getMyPosts();

  List<Post> getMyPostsAfter(LocalDateTime from);
  List<Post> getPostsFromFollowedUsers();
  List<User> getUsersWhoLikePost(Long id);

  void addReplyToPost(Long postId, Reply reply);
}
