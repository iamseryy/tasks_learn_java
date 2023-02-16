package util;

import java.io.*;
import java.util.ArrayList;


public class FileUtils {

    public static ArrayList<String> readFile(File file) throws IOException{
        var data = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
                data.add(line);
        }

        reader.close();

        return data;
    }

    public static void writeFile(String data, File file) throws IOException{
        FileWriter writer = new FileWriter(file, false);
        writer.write(data);
        writer.flush();

        writer.close();
    }
}
