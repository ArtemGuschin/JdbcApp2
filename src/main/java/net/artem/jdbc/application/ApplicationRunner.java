package net.artem.javacore.jdbc.application;

import net.artem.javacore.jdbc.application.controller.LabelController;
import net.artem.javacore.jdbc.application.enums.LabelStatus;
import net.artem.javacore.jdbc.application.repository.jdbc.JdbcLabelRepositoryImpl;
import net.artem.javacore.jdbc.application.utils.JdbcUtils;
import net.artem.javacore.jdbc.application.view.LabelView;
import net.artem.javacore.jdbc.application.view.MainView;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class ApplicationRunner {
    public static void main(String[] args) throws SQLException {

        MainView mainView = new MainView();
            mainView.mainApp();


    }


}


