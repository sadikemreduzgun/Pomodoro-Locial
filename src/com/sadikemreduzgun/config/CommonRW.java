package com.sadikemreduzgun.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.sadikemreduzgun.actions.RandomUnameCreator;

// read and write can't make the work themselves an upper class was needed
public class CommonRW implements FieldKeeper{

    //  no need for function override
    public final String readTimeFromSys(){
        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define the output format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        // Format the date and time to the desired output format
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;

    }

    final RandomUnameCreator randomUnameCreator = new RandomUnameCreator();

    // no need for function override
    protected final String readConfig(String key){

        // if name.split..[1] != properties no need for this since user cant change it.
        Properties properties = new Properties();
        String value = "";

        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);

            value = properties.getProperty(key);

        } catch (Exception e) {
            createPropertyFile();
            // e.printStackTrace();
            // username = random get....;
        }

        return value;

    }

    protected final void addChangeConfigProperties(String key, String value, String configurationName){

        // if name.split..[1] != properties no need for this since user cant change it.
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);

            properties.setProperty(key, value);

            // Save updated configuration
            try (FileOutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
                properties.store(output, configurationName);
            }
        } catch (Exception e) {

            createPropertyFile();
            // e.printStackTrace();
        }

        // properties file will be saved in APPDATA next version
        System.out.println(System.getenv("APPDATA"));

    }

    public void changeDate(String date){
        addChangeConfigProperties(dateKey, date, "date change");
    }

    // create properties file if not exist in configs, project comes with no properties file.
    protected final void createPropertyFile() {
        try {
            File myObj = new File("configs/config.properties");

            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        addChangeConfigProperties(usernameKey, "", "username created");
        addChangeConfigProperties(pomodoroKey, "0", "pomodoro initiated");
        addChangeConfigProperties(dateKey, readTimeFromSys(), "date field created");
    }
}
