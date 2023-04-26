package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Reply;
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


    @PostMapping("/{replyId}/replies")
    public void addReplyToReply(@PathVariable(value = "replyId") Long replyId, @RequestBody Reply reply) {
        replyService.addReplyToReply(replyId, reply);
    }

}
