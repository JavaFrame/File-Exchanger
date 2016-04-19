package main;

import client.FileClient;
import server.FileServer;
import utils.SendingFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class Main {
    public static FileServer server;
    public static FileClient client;

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        server = new FileServer();
        server.init(1111);

        client = new FileClient();
        client.init("localhost", 1111);

        client.sendFile(new File("D:\\Bilder\\Wallpapers\\php-tag.jpg"));
    }

    public static void stop() {
        client.stop();
        server.stop();
        System.exit(0);
    }
}
