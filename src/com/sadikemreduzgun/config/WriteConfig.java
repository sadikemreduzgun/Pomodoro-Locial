package com.sadikemreduzgun.config;
import com.sadikemreduzgun.config.ReadConfig;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import com.sadikemreduzgun.actions.RandomUnameCreator;

public class WriteConfig extends CommonRW implements FieldKeeper {

    // listened the suggestion of IDE
    private final ReadConfig readConfig = new ReadConfig();
    // RandomUnameCreator randomUnameCreator = new RandomUnameCreator();

    /// make writeusernaem writing into properties
    public void writeUsername(String username){

        // Save the JSON string to a file
        addChangeConfigProperties(usernameKey, username, "username configuration");

    }
    // change structure, store other user info just dynamically

    // reset pom count
    public void resetPomodoroCount(){
        String pomodoroConfig = "0";
        addChangeConfigProperties(pomodoroKey, pomodoroConfig, "pomodoro reset");
    }

    // check if properties exist and increase pom count there
    // if not exist create one in used function
    public void CheckIncPomodoroCount(){

        String pomodoroConfig = "0";
        String todayDate = readConfig.readTimeFromSys();

        try {
            pomodoroConfig = readConfig.getPomodoroCount();
        }
        catch (Exception e){

            System.err.println("Error: " + e.getMessage());
            // create file if not exist

        }

        if (pomodoroConfig.isEmpty()){
            addChangeConfigProperties(pomodoroKey, "0", "change config pomodoro count");
            addChangeConfigProperties(dateKey, todayDate, "change today date");
            return;
        }

        int pomodoroCount = Integer.parseInt(readConfig.getPomodoroCount());
        String fileDateString = readConfig.getDate();

        if (fileDateString.equals(todayDate)){
            pomodoroCount = pomodoroCount + 1;
        }
        else{
            pomodoroCount = 0;
        }

        addChangeConfigProperties(pomodoroKey, String.valueOf(pomodoroCount), "increase pomodoro count");
        addChangeConfigProperties(dateKey, todayDate, "change date field");

    }

}
