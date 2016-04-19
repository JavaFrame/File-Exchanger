package client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import utils.KryoClassRegister;
import utils.SendingFile;
import utils.UserIdRequest;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class FileClient {
    private Client client;
    private FileClientListener listener;
    public final static int WRITE_BUFFER_SIZE = 67632;
    public final static int OBJECT_BUFFER_SIZE= 67632;

    protected int userId = -1;

    public void init(String host, int port) throws IOException {
        client = new Client(WRITE_BUFFER_SIZE, OBJECT_BUFFER_SIZE);
        client.start();
        client.connect(5000, host, port);
        listener = new FileClientListener(this);
        client.addListener(listener);
        KryoClassRegister.registeringClasses(client.getKryo());
        send(new UserIdRequest());
    }

    public void send(Object o) {
        client.sendTCP(o);
    }

    public void sendFile(File f) {
        int packageSize = 1000;
        try {
            byte[] totalBytes = new byte[(int) f.length()];
            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(totalBytes, 0, totalBytes.length);

            LinkedList<byte[]> bytesToSendList = new LinkedList<>();
            int counter = 0;
            for(int i = (int) f.length(); i > 0;) {
                if(i > packageSize) {
                    i -= packageSize;
                    byte[] bytePackage = Arrays.copyOfRange(totalBytes, counter*packageSize, (counter+1)*packageSize);
                    bytesToSendList.add(bytePackage);

                    counter++;
                } else {
                    i -= i;
                    byte[] bytePackage = Arrays.copyOfRange(totalBytes, counter*packageSize, totalBytes.length);
                    bytesToSendList.add(bytePackage);
                }
            }
            for(int i = 0; i < bytesToSendList.size(); i++) {
                SendingFile sendingFile = new SendingFile(f.getName(), bytesToSendList.size(), i, bytesToSendList.get(i), 0);
                //System.out.println(i);
                send(sendingFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        client.stop();
    }
}
