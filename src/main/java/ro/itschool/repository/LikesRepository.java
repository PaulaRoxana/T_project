//package ro.itschool.repository;//package ro.itschool.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import ro.itschool.entity.Likes;
//
//public interface LikesRepository extends JpaRepository<Likes, Long> {
//    //nu merge ca nu am user id
//    @Query(value = "INSERT INTO likes (user_id, post_id) VALUES (:userId, :postId)", nativeQuery = true)
//    void likePost(@Param("userId") Long userId, @Param("postId") Long postId);
//
//    @Query(value = "DELETE from likes where user_id = :userId AND post_id = :postId)", nativeQuery = true)
//    void unlikePost(@Param("userId") Long userId, @Param("postId") Long postId);
//}