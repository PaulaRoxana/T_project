package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
