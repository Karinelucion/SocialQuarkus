package io.github.karinelucion.quarkussocial.domain.repository;

import io.github.karinelucion.quarkussocial.domain.model.Follower;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
}
