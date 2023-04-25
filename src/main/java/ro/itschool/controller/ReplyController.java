package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.repository.PostRepository;
import ro.itschool.service.PostService;
import ro.itschool.service.ReplyService;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final PostRepository postRepository;
    private final PostService postService;

    //1. Add post reply : reply to existing posts or other replies. Able to specify if the reply
    //is public or only for the parent post’s owner

   /* @PostMapping(value = "/add/{postId}")
    public ResponseEntity<?> addReply(@RequestBody Reply newReply, @PathVariable Long postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>("Post with id:" + postId + " " + "not found", HttpStatus.BAD_REQUEST);
        }
        Post post = optionalPost.get();
        newReply.setPost(post);
        return new ResponseEntity<>(replyService.save(newReply), HttpStatus.OK);
    }

    */

}
