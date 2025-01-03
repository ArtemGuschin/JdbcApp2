package net.artem.jdbc.application.view;

import java.util.Scanner;

public class MainView {

    private final LabelView labelView = new LabelView();
    private final PostView postView = new PostView();
    private final WriterView writerView = new WriterView();
    private final Scanner SCANNER = new Scanner(System.in);

    public void mainApp() {
        while (true) {
            System.out.println("Enter 1 to work for labels");
            System.out.println("Enter 2 to work for Posts");
            System.out.println("Enter 3 to work for Writers");
            System.out.println("Enter 0 for exit");
            System.out.println("===========================");

            int userChoice = SCANNER.nextInt();

            switch (userChoice) {
                case 1:
                    labelView.startLabel();
                    break;
                case -1, -2, -3:
                    System.out.println("Good Bye");
                    System.exit(0);
                case 2:
                    postView.startPost();
                    break;
                case 3:
                    writerView.startWriter();
                    break;
                default:
                    System.exit(0);
            }
        }
    }
}
