package jquil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
    Properties prop = new Properties();
    InputStream input = null;
    String id = null;
    String key = null;
    public Config() {
        try {

            input = new FileInputStream(System.getProperty("user.home") + "/.jquilconfig.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            id = prop.getProperty("user_id");
            key = prop.getProperty("api_key");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String get_id() {
        return id;
    }

    public String get_key() {
        return key;
    }

}
