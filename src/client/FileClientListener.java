package client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import utils.SendingFile;
import utils.UserId;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class FileClientListener extends Listener {
    private FileClient client;

    private String currentName;
    private LinkedList<SendingFile> file;

    public FileClientListener(FileClient client) {
        this.client = client;
    }

    @Override
    public void received(Connection connection, Object obj) {
        if(obj instanceof UserId) {
            UserId id = (UserId) obj;
            client.userId = id.getUserId();
            System.out.println("UserId wurde auf " +  id.getUserId() + " gesetzt.");
        }
        if(obj instanceof SendingFile) {
            SendingFile f = (SendingFile) obj;
            if(f.getUserId() == client.userId) {
                return;
            }
            if(!f.getFileName().equals(currentName)) {
                System.out.println("'" + currentName + "' != '" + f.getFileName() + "'");
                file = new LinkedList<>();
                currentName = f.getFileName();
                System.out.println("create new file");
            }
            System.out.println(file.size() + " von " + f.getTotalPackage());
            file.add(f);
            if(file.size() == f.getTotalPackage()) {
                System.out.println("writeToDisk");
                file.sort((SendingFile o1, SendingFile o2)-> {
                    if(o1.getId() < o2.getId()) {
                        return -1;
                    }
                    return 1;
                });
                writeToDisk();
            }
        }

    }

    /**
     * Schreibt das aktuelle File auf die Festplatte
     */
    private void writeToDisk() {
        try {
            File outputFile = new File("C:\\Users\\Sebastian\\Downloads\\" + currentName);
            if(outputFile.exists()) {
                Desktop.getDesktop().open(outputFile);
                throw new IOException("Die Datei '" + outputFile.getPath() + "' existiert bereits.");
            }
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            for(SendingFile f : file) {
                bos.write(f.getBytes());
                bos.flush();
            }

            bos.flush();
            fos.close();
            bos.close();
            Desktop.getDesktop().open(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
