package com.sadikemreduzgun.timer;
import com.sadikemreduzgun.config.ReadConfig;
import com.sadikemreduzgun.config.WriteConfig;

public class DayChecker {

    ReadConfig readConfig = new ReadConfig();
    WriteConfig writeConfig = new WriteConfig();

    // just compare config date and date now
    private void checkDay(){
        String time = readConfig.readTimeFromSys();

        if (time.equals(readConfig.getDate())){
            return;
        }
        else{
            writeConfig.changeDate(time);
            writeConfig.resetPomodoroCount();
        }

    }

    // thread check day schedule
    public void checkDaySchedule(){
        try {
            // sleep 5 minutes everytime
            checkDay();
            Thread.sleep(30000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
