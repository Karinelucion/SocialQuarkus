package io.github.karinelucion.quarkussocial.rest;

import io.github.karinelucion.quarkussocial.domain.model.Post;
import io.github.karinelucion.quarkussocial.domain.model.User;
import io.github.karinelucion.quarkussocial.domain.repository.PostRepository;
import io.github.karinelucion.quarkussocial.domain.repository.UserRepository;
import io.github.karinelucion.quarkussocial.rest.dto.CreatePostRequest;
import io.github.karinelucion.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON) //Tipo de dado que sera recebido na requisicao
@Produces(MediaType.APPLICATION_JSON) //Tipo que sera retornado nas requicoes
public class PostResource {
    private UserRepository userRepository;
    private PostRepository repository;

    public PostResource(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.repository = postRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
        User user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId){
        User user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var query = repository.find(
                "user",
                Sort.by(
                        "dateTime",
                        Sort.Direction.Descending),
                user);
        var list = query.list();

        var postResponseList = list.stream()
                .map(PostResponse :: fromEntity)
                .collect(Collectors.toList());

        return Response.ok(postResponseList).build();
    }
}
