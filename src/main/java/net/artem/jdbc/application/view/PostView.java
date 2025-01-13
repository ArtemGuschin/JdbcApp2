package net.artem.jdbc.application.view;

import net.artem.jdbc.application.controller.LabelController;
import net.artem.jdbc.application.controller.PostController;
import net.artem.jdbc.application.controller.WriterController;
import net.artem.jdbc.application.enums.PostStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.service.PostService;
import net.artem.jdbc.application.service.WriterService;

import java.util.*;

public class PostView {
    private final Scanner SCANNER = new Scanner(System.in);
    private final PostService postService = new PostService();
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();
    private final WriterService writerService = new WriterService();


    private void createPost() {
        System.out.println("Enter post content ");
        String content = SCANNER.nextLine();
        PostStatus postStatus = PostStatus.ACTIVE;
        System.out.println("Enter writer id ");
        Long id = SCANNER.nextLong();
        Writer writer = writerService.getWriterById(id);
        System.out.println("Enter id label to add your post");
        List<Label> labels = labelController.getAll();
//        System.out.println(labels);

        List<Label> selectedLabels = fulfillsLabels();


        Post createdPost = postController.createPost(content, writer, selectedLabels);
        System.out.println("Created post " + createdPost);

    }


    private List<Label> fulfillsLabels() {
        List<Label> result = new ArrayList<>();
        List<Label> allLabels = labelController.getAll();
        while (true) {
            System.out.println(" (-1 To complete ) ");
            System.out.println(allLabels);

            Long choice = SCANNER.nextLong();
            if (choice == -1L) {
                return result;
            }

            Label selectedLabel = allLabels.stream()
                    .filter(cl -> cl.getId().equals(choice)).findFirst().orElse(null);
            if (Objects.nonNull(selectedLabel)) {
                result.add(selectedLabel);
            }
        }
    }

    private void updatePost() {

        System.out.println("Enter id post to update ");
        Long id = SCANNER.nextLong();
        SCANNER.nextLine();
        System.out.println("Enter content to update ");
        String content = SCANNER.nextLine();
        PostStatus postStatus = PostStatus.UNDER_REVIEW;
        List<Label> labels = fulfillsLabels();
        Writer writer = new Writer();
        Post updatedPost = postController.updatePost(id, content, labels, new Date(), new Date(), writer);


    }

    private void deletePost() {
        System.out.println("Enter id to delete :");
        Long id = SCANNER.nextLong();
        postController.deletePost(id);
        System.out.println("Post with id " + id + " deleted");
    }

    public void startPost() {
        while (true) {
            System.out.println("Hello I am firstApp!!!");
            System.out.println("1 -  create Post ");
            System.out.println("2 - update Post");
            System.out.println("3 - delete Post");
            System.out.println("4 - Get all");
            System.out.println("5 - Get post id");
            System.out.println("Press 0 for exit");

            int userChoice = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (userChoice) {
                case 1:
                    createPost();
                    break;
                case -1, -2, -3:
                    System.out.println("Good Bye");
                    System.exit(0);
                case 2:
                    updatePost();
                    break;
                case 3:
                    deletePost();
                    break;
                case 4:
                    getAll();
                    break;
                case 5:
                    getPostById();
                    break;

                default:
                    System.exit(0);
            }
        }
    }

    private void getAll() {
        List<Post> posts = postService.getAll();
        System.out.println(posts);
    }

    private void getPostById() {
        System.out.println("Enter id post ");
        Long id = SCANNER.nextLong();
        Post post = postService.getPostBYId(id);
        System.out.println(post);
    }


}
