package utils;

        import java.util.Arrays;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class SendingFile {
    private String fileName;
    private int totalPackage;
    private int id;
    private byte[] bytes;
    private int userId;

    public SendingFile() {
    }

    public SendingFile(String fileName, int totalPackage, int id, byte[] bytes, int userId) {
        this.fileName = fileName;
        this.totalPackage = totalPackage;
        this.id = id;
        this.bytes = bytes;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public int getTotalPackage() {
        return totalPackage;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "bytes=" + Arrays.toString(bytes);
    }
}