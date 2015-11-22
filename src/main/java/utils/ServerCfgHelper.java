package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by dmitri on 22.11.15.
 */
public class ServerCfgHelper {
    HashMap<String, String> result = new HashMap<>();
    InputStream inputStream;

    public HashMap<String, String> getPropValues() throws IOException {

        try {
            Properties prop = new Properties();

            inputStream = new FileInputStream("cfg/config.properties");

            prop.load(inputStream);

            String host = prop.getProperty("host");
            String port = prop.getProperty("port");

            result.put("host", host);
            result.put("port", port);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        return  result;
    }


}
