package net.artem.jdbc.application;


import net.artem.jdbc.application.repository.jdbc.JdbcPostRepositoryImpl;
import net.artem.jdbc.application.view.MainView;


import java.sql.SQLException;


public class
        ApplicationRunner {
    public static void main(String[] args) throws SQLException {
        MainView mainView = new MainView();
        mainView.mainApp();





    }


}


