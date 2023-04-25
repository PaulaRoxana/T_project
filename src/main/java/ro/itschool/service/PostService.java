package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

  //  Post save(Post newPost);
  void save(Post post);

  List<Post> getPostsFromFollowedUsers();

  List<Post> filterPosts(LocalDateTime timestamp);

  void deleteById(Long id);

  void copyPost(Long id);

  void likePost(Long id);

  void unLikePost(Long id);

  List<Post> getPostWithMentions();

  Optional<Post> findById(Long postId);

  List<Post> getMyPosts();
}
