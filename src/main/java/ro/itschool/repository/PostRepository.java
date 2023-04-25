package ro.itschool.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.itschool.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByTimestampGreaterThan(@Param("timestamp") LocalDateTime timestamp);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM likes WHERE post_id = ?", nativeQuery = true)
  void deleteLikesByPostId(Long id);

//    @Query(value = "SELECT *, 0 AS clazz_ FROM post WHERE message LIKE %:keyword%", nativeQuery = true)
//    List<Post> getPostsWithMention(String keyword);


  @Transactional
  @Modifying
  @Query(value = "DELETE FROM reply WHERE post_id = ?", nativeQuery = true)
  void deleteReplies(Long id);

  List<Post> findByUserId(Long id);


  @Transactional
  @Modifying
  @Query(value = "INSERT INTO likes(user_id, post_id) VALUES (:userId, :postId)", nativeQuery = true)
  void insertIntoLikesTable(@Param("userId") Long userId, @Param("postId") Long postId);

  @Query(value = """
    SELECT username FROM user INNER JOIN likes
    ON user.user_id = likes.user_id
    WHERE post_id = ?;
       """, nativeQuery = true)
  List<Object[]> getUsersWhoLikePost(Long id);


  @Transactional
  @Modifying
  @Query(value = "delete from likes where user_id = :userId AND post_id = :postId", nativeQuery = true)
  void deleteFromLikesTable(@Param("userId") Long userId, @Param("postId") Long postId);

}
