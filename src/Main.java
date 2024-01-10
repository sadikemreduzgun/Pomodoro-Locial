import javax.swing.*;
import com.sadikemreduzgun.gui.GUI;
import com.sadikemreduzgun.timer.DayChecker;

public class Main {

    private static DayChecker dayChecker;

    public static void main(String[] args) {

        // start variables
        dayChecker = new DayChecker();

        // reset pomodoro count to 0 when day is over, or when opening app another day
        Thread searcherThread = new Thread(() -> {
            while (true){

                dayChecker.checkDaySchedule();
            }

        });

        // start date checking thread
        searcherThread.start();

        // start gui
        SwingUtilities.invokeLater(() -> new GUI());

    }
}
