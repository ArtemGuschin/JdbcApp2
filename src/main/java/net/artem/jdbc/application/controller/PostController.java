package net.artem.jdbc.application.controller;

import net.artem.jdbc.application.enums.PostStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.jdbc.JdbcPostRepositoryImpl;
import net.artem.jdbc.application.repository.PostRepository;

import java.util.Date;
import java.util.List;

public class PostController {
    private final PostRepository postRepository;


    public PostController() {
        this.postRepository = new JdbcPostRepositoryImpl();
    }

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String content, List<Label> labels) {
        Post newPost = Post.builder()
                .content(content)
                .labels(labels)
                .created(new Date())
                .updated(new Date())
                .build();
        return postRepository.save(newPost);

    }


    public Post updatePost(Long id, String content, PostStatus postStatus, List<Label> labels, Date created, Date updated, Writer writer) {
        Post post = Post.builder()
                .id(id)
                .content(content)
                .postStatus(postStatus)
                .labels(labels)
                .created(created)
                .updated(new Date())
                .build();
        return postRepository.update(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


    public List<Post> getAll() {
        return postRepository.getAll();
    }
}
