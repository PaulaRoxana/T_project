package ro.itschool.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Post {
    @Id
    // can no longer use identity key generation, because of inheritance
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "post_id")
    private Long id;

    private String message;

//    @Temporal(TemporalType.DATE)
//    private LocalDate timestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;


    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToMany(mappedBy = "likes", fetch = FetchType.EAGER)
    private Set<User> likes = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "post", cascade = ALL)
//    private Set<Reply> replies;

//    @OneToMany
//    private Set<Reply> replies = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "post", cascade = ALL)
//    private Set<Mention> mentions;

    public Post(String message, LocalDateTime timestamp, User user) {
        this.message = message;
        this.timestamp = timestamp;
        this.user = user;
    }
}
