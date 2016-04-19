package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import main.Main;
import utils.SendingFile;
import utils.UserId;
import utils.UserIdRequest;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class FileServerListener extends Listener {
    private FileServer server;

    public FileServerListener(FileServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection connection, Object obj) {
        if(obj instanceof UserIdRequest) {
            server.sendToAll(new UserId(server.getNextId()));
        }
        if(obj instanceof SendingFile) {
            server.sendToAll(obj);
        }
    }



}
