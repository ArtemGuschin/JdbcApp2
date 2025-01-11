package net.artem.jdbc.application.controller;

import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.service.PostService;

import java.util.Date;
import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController() {
        this.postService = new PostService();
    }


    public Post createPost(String content,Writer writer, List<Label> labels) {
        Post newPost = Post.builder()
                .content(content)
                .writer(writer)
                .labels(labels)
                .created(new Date())
                .updated(new Date())
                .build();
        return postService.createPost(newPost);

    }


    public Post updatePost(Long id, String content, List<Label> labels, Date created, Date updated, Writer writer) {
        Post post = Post.builder()
                .id(id)
                .content(content)
                .labels(labels)
                .created(created)
                .updated(new Date())
                .build();
        return postService.updatePost(post);
    }

    public void deletePost(Long id) {
        postService.deletePost(id);
    }


    public List<Post> getAll() {
        return postService.getAll();
    }



}