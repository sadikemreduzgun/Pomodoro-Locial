package com.sadikemreduzgun.config;


public class ReadConfig extends CommonRW {

    public String getUsername(){
        return readConfig("username");
    }

    public String getPomodoroCount(){
        return readConfig("pomodoroCount");
    }

    public String getDate(){
        return readConfig("date");
    }

    /*
    public Map getOtherUserInfo(String how){

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("configs/other_users.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);

                // read just one line if multiple
                if (how.equals("one")){
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();

    }*/

}
