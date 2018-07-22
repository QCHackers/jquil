package jquil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <h1>Configure API credentials</h1>
 * Contains procedures to set up and return Forest API credentials
 * <p>
 */
public class Config {
    Properties prop = new Properties();
    InputStream input = null;
    String id = null;
    String key = null;

    /**
     * Copy contents of jquilconfig.properties
     * and save them to this object
     */
    public Config() {
        try {

            input = new FileInputStream(System.getProperty("user.home") + "/.jquilconfig.properties");

            // load a properties file
            prop.load(input);

            // get the property values
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

    /**
     * Return Forest API ID
     * @return user_id
     */
    public String get_id() {
        return id;
    }
    
    /**
     * Return Forest API key
     * @return api_key
     */
    public String get_key() {
        return key;
    }

}
