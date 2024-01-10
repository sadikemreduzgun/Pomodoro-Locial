package com.sadikemreduzgun.tests;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class GetEnvTest {

    private static final String CONFIG_FILE_PATH = "configs/config.properties";

    public static void main(String[] args){
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);

            properties.setProperty("dynamicSetting2", String.valueOf(12));

            // Save updated configuration
            try (FileOutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
                properties.store(output, "Updated configuration");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(System.getenv("APPDATA"));

    }
}
