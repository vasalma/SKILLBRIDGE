package main;

import ui.login;

public class Main {

    public static void main(String[] args) {
        login ms = new login(); //Holiiiiiiiiiii
        ms.setVisible(true);
        ms.setLocationRelativeTo(null);
        DBConnection.getConnection();
    }
}

