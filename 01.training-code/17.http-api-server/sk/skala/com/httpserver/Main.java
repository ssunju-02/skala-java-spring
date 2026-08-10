package sk.skala.com.httpserver;

import sk.skala.com.httpserver.controller.UserController;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpWebServer server = new HttpWebServer(8080);
        server.registerController(UserController.class);
        server.start();
    }
}
