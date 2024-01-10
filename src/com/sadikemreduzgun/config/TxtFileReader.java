package com.sadikemreduzgun.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtFileReader {

    public String readFile(String filePath){
        // initiate string builder
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line+",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();

    }

}
