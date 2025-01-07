package net.artem.jdbc.application.service;

import net.artem.jdbc.application.enums.LabelStatus;
import net.artem.jdbc.application.enums.PostStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.repository.PostRepository;
import net.artem.jdbc.application.repository.jdbc.JdbcPostRepositoryImpl;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public PostService (){
        this.postRepository = new JdbcPostRepositoryImpl();
    }
    public Post createPost(Post post) {
       post.setPostStatus(PostStatus.ACTIVE);
        return postRepository.save(post);
    }

    public Post updatePost(Post post){
        post.setPostStatus(PostStatus.UNDER_REVIEW);
        return postRepository.update(post);

    }
    public void deletePost(Long id){
        postRepository.deleteById(id);


    }


    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post getPostBYId(Long id) {
        return postRepository.getById(id);

    }
}
