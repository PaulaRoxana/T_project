package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;
import ro.itschool.entity.Reply;
import ro.itschool.entity.User;
import ro.itschool.repository.ReplyRepository;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.ReplyService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository replyRepository;
  private final UserRepository userRepository;
 private final PostServiceImpl postRepository;

  @Override
  public void addReplyToReply(Long replyId, Reply reply) {
    User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<User> optionalLoggedInUser = userRepository.findByUsername(loggedUser.getUsername());
    User whoReplies = optionalLoggedInUser.get();

    Optional<Reply> optionalReply = replyRepository.findById(replyId);
    Reply replyToBeReplied = optionalReply.get();

    reply.setTimestamp(LocalDateTime.now());
    reply.setUser(whoReplies);

    Set<Reply> replies = replyToBeReplied.getReplies();
    replies.add(reply);
    replyToBeReplied.setReplies(replies);
    //reply.setPost(post);
    replyRepository.save(reply);

  }
}
