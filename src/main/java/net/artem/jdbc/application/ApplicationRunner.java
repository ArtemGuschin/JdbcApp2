package net.artem.jdbc.application;


import net.artem.jdbc.application.repository.jdbc.JdbcWriterRepositoryImpl;
import net.artem.jdbc.application.view.MainView;

public class
ApplicationRunner {
    public static void main(String[] args) {
//        MainView mainView = new MainView();
//        mainView.mainApp();
        JdbcWriterRepositoryImpl jdbcWriterRepository = new JdbcWriterRepositoryImpl();
        jdbcWriterRepository.getPostsForWriter(1L);


    }


}


