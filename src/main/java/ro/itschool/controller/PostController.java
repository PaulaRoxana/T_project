package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Post;
import ro.itschool.entity.Reply;
import ro.itschool.entity.User;
import ro.itschool.repository.PostRepository;
import ro.itschool.service.PostService;
import ro.itschool.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class PostController {


  private final PostService postService;
  private final UserService userService;
  private final PostRepository postRepository;

  /**
   * 1. Add post : post a public message
   */

  @PostMapping(value = "/add")
  public void addPost(@RequestBody Post post) {
    postService.save(post);
  }


  /**
   * 2. Get own posts : return all posts added by the current user;
   */

  @GetMapping(value = "/my-posts")
  public List<Post> getMyPosts() {
    return postService.getMyPosts();
  }

  @GetMapping(value = "/my-posts_after")
  public List<Post> getMyPostsAfter(@RequestParam("timestamp") LocalDateTime thisTime) {
    return postService.getMyPostsAfter(thisTime);
  }

  /**
   * !! 2'.Able to filter posts newer than a timestamp
   */
//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @GetMapping(value = "/all-posts_after")
  public List<Post> getAllPostsAfter(@RequestParam("timestamp") LocalDateTime thisTime) {
    return postRepository.findByTimestampAfter(thisTime);
  }

  /**
   * 3. Get feed : return all posts added by users followed by the current user
   */
  @GetMapping(value = "/all")
  public ResponseEntity getAllPosts() {
    return new ResponseEntity<>(postService.getPostsFromFollowedUsers(), HttpStatus.OK);
  }

  /**
   * 4. Delete post and all the likes
   */
  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    if (postRepository.existsById(id)) {
      postService.deleteById(id);
      return ResponseEntity.ok().build();
    } else
      return ResponseEntity.badRequest().build();
  }


  /**
   * !!!5. Repost : “copy” an existing post from a different user
   */
  //merge
  @RequestMapping(value = "/repost/{id}")
  public void copyPost(@PathVariable Long id) {
    postService.copyPost(id);
  }

  /**
  6. Get mentions : return all posts in which the current user was mentioned
   */

  @GetMapping(value = "/mentions")
  public List<Post> getPostWithMentions() {
    return postService.getPostWithMentions();
  }

  /**
   * LIKE
   * 1. Like post : mark an existing post with a like. The owner of the post will receive in his
   * “get own posts” call (Posts.2) ok each post, a list of all the likes that message
   * received
   * 2. Remove like
   * 3. Cascade: When a post is deleted, remove all its like
   */

  @PostMapping(value = "/like/{id}")
  public void likePost(@PathVariable Long id) {
    postService.likePost(id);
  }

  @GetMapping(value = "/all-likes-post/{id}")
  public ResponseEntity getAllLikesForPost(@PathVariable Long id) {
    return new ResponseEntity<>(postService.getUsersWhoLikePost(id).stream().map(User::getUsername).toList(), HttpStatus.OK);
  }

  @PostMapping(value = "/unlike/{id}")
  public void unLikePost(@PathVariable Long id) {
    postService.unLikePost(id);
  }

  //5. Repost : “copy” an existing post from a different user
  //works
//  @PostMapping(value = "/repost/{id}")
//  public void repost(@PathVariable Long id) {
//    postService.repost(id);
//  }

  /**REPLY*/

  @PostMapping("/{postId}/replies")
  public void addReplyToPost(@PathVariable(value = "postId") Long postId, @RequestBody Reply reply) {
    postService.addReplyToPost(postId, reply);
  }

}

