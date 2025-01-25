package io.github.karinelucion.quarkussocial.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "followers")
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User follower;
}
