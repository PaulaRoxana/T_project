package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Reply;
import ro.itschool.repository.ReplyRepository;
import ro.itschool.service.ReplyService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public Reply save(Reply newReply) {
        newReply.setTimestamp(LocalDateTime.now());
        return replyRepository.save(newReply);
    }
}
