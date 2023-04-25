package ro.itschool.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.itschool.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            value = "SELECT * FROM user u WHERE u.username LIKE %:keyword% OR u.first_name LIKE %:keyword% " +
                    "OR u.last_name LIKE %:keyword%",
            nativeQuery = true)
    List<User> searchUserBy(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM post WHERE message like %:name% ",
            nativeQuery = true)
    Optional<User> findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO follow(follower_id, followed_id) VALUES (:followerId, :followedId)", nativeQuery = true)
    void insertIntoFollowTable(@Param("followerId") Long followerId, @Param("followedId") Long followedId);

    @Query(value = """
            SELECT b.user_id, b.username, b.last_name, b.first_name, b.email FROM user a INNER JOIN follow
            ON a.user_id = follow.follower_id
            INNER JOIN user b
            ON b.user_id = follow.followed_id
            WHERE a.user_id = ?;
               """, nativeQuery = true)
    List<Object[]> getFollowedUsers(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow WHERE follower_id  = :followerId AND followed_id = :followedId", nativeQuery = true)
    void deleteFromFollowedTable(@Param("followerId") Long followerId, @Param("followedId") Long followedId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow WHERE follower_id = ?", nativeQuery = true)
    void deleteFollower(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post WHERE user_id = ?", nativeQuery = true)
    void deleteByUserId(Long id);

    //@Query(value = "SELECT * FROM user WHERE email = ?", nativeQuery = true)
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}


