package main;

import ui.login;

public class Main {

    public static void main(String[] args) {
        login ms = new login(); //Hello
        ms.setVisible(true);
        ms.setLocationRelativeTo(null);
        DBConnection.getConnection();
    }
}

