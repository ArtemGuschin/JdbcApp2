package net.artem.javacore.jdbc.application.controller;

import net.artem.javacore.jdbc.application.enums.PostStatus;
import net.artem.javacore.jdbc.application.model.Label;
import net.artem.javacore.jdbc.application.model.Post;
import net.artem.javacore.jdbc.application.repository.jdbc.JdbcPostRepositoryImpl;
import net.artem.javacore.jdbc.application.repository.PostRepository;

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


    public Post updatePost(Long id, String content,
                           List<Label> labels,
                           Date updated, Date date) {
        Post updatePost = Post.builder()
                .id(id)
                .content(content)
                .labels(labels)
                .updated(updated)
                .build();
        return postRepository.update(updatePost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


    public List<Post> getAll() {
        return postRepository.getAll();
    }
}
