package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import utils.KryoClassRegister;
import utils.SendingFile;

import java.io.IOException;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class FileServer {
    public Server server;
    public FileServerListener listener;
    private int nextId = 0;

    public void init(int port) throws IOException {
        server = new Server();
        server.start();
        server.bind(port);
        listener = new FileServerListener(this);
        server.addListener(listener);
        KryoClassRegister.registeringClasses(server.getKryo());
    }

    public void sendToAll(Object obj) {
        server.sendToAllTCP(obj);
    }

    public void stop() {
        server.stop();
    }

    public int getNextId() {
        return nextId++;
    }
}
