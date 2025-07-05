package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static void writeBytesToFile(byte[] contentBytes, String location) {

        try {

            File afile = new File(location);
            OutputStream os = new FileOutputStream(afile);
            os.write(contentBytes);
            os.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

    }

    public static void writeContentToFile(String content, String location) {

        try {

            File afile = new File(location);
            OutputStream os = new FileOutputStream(afile);
            os.write(content.getBytes());
            os.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

    }

    public static String convertFileContentToString(String location) {

        String content = null;
        try {

            File afile = new File(location);
            InputStream is = new FileInputStream(afile);
            byte[] abs = is.readAllBytes();
            content = new String(abs);
            is.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

        return content;

    }
}
