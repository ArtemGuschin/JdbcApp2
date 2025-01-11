package net.artem.jdbc.application.view;

import net.artem.jdbc.application.controller.LabelController;
import net.artem.jdbc.application.controller.PostController;
import net.artem.jdbc.application.controller.WriterController;
import net.artem.jdbc.application.enums.LabelStatus;
import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.service.PostService;
import net.artem.jdbc.application.service.WriterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class WriterView {
    private final Scanner SCANNER = new Scanner(System.in);
    private WriterService writerService = new WriterService();
    private PostService postService = new PostService();
    private final WriterController writerController = new WriterController(writerService);
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();


    private void createWriter(){
        System.out.println("Введите имя: ");
        String firstname = SCANNER.nextLine();
        System.out.println("Введите фамилию: ");
        String lastname = SCANNER.nextLine();
        List<Post>posts = postController.getAll();
        WriterStatus writerStatus = WriterStatus.UNDER_REVIEW;
        Writer createdWriter = writerController.createWriter(firstname,lastname,posts,writerStatus);
        System.out.println("Writer updated ");


    }



    private void updateWriter() {
        System.out.println("Enter  id Writer to update :");
        Long id = SCANNER.nextLong();
        SCANNER.nextLine();
        System.out.println("Enter update name :");
        String firstName = SCANNER.nextLine();
        System.out.println("Enter LastName");
        String lastName = SCANNER.nextLine();
        List<Post> posts = new ArrayList<>();
        WriterStatus writerStatus = WriterStatus.UNDER_REVIEW;
        Writer updatedWriter = writerController.updateWriter(id, firstName, lastName, posts, writerStatus);
        System.out.println("Writer updated ");


    }


    private void deleteWriter() {
        System.out.println("Enter writer Id  to delete :");
        Long id = SCANNER.nextLong();
        writerController.deleteWriter(id);
        System.out.println("Writer  with id " + id + " deleted");


    }

    private List<Post> fulfillsPosts() {
        List<Post> result = new ArrayList<>();
        List<Post> allPosts = postController.getAll();
        while (true) {
            System.out.println("Enter Post id (-1 To complete ) ");
            System.out.println(allPosts);

            Long choice = SCANNER.nextLong();
            if (choice == -1L) {
                return result;
            }
            Post selectedPost = allPosts.stream()
                    .filter(cl -> cl.getId().equals(choice)).findFirst().orElse(null);
            if (Objects.nonNull(selectedPost)) {
                result.add(selectedPost);
            }
        }
    }

    public void startWriter() {
        while (true) {
            System.out.println("Hello I am firstApp!!!");
            System.out.println("1 -  create Writer ");
            System.out.println("2 - update Writer");
            System.out.println("3 - delete Writer");
            System.out.println("4 - get all writers");
            System.out.println("5 - Get id writer ");
            System.out.println("Press 0 for exit");

            int userChoice = SCANNER.nextInt();
            SCANNER.nextLine();

            switch (userChoice) {
                case 1:
                    createWriter();
                    break;
                case -1, -2, -3:
                    System.out.println("Good Bye");
                    System.exit(0);
                case 2:
                    updateWriter();
                    break;
                case 3:
                    deleteWriter();
                    break;
                case 4:
                    getAll();
                    break;
                case 5:
                    getWriterById();
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    public void getAll() {
        List<Writer> writers = writerService.getAll();
        System.out.println(writers);
    }

    private void getWriterById() {
        System.out.println("Enter id writer ");
        Long id = SCANNER.nextLong();
        Writer writer = writerService.getWriterById(id);
        System.out.println(writer);
    }
}
