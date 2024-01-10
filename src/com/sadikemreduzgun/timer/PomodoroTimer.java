package com.sadikemreduzgun.timer;
import com.sadikemreduzgun.actions.SoundPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import com.sadikemreduzgun.config.ReadConfig;
import com.sadikemreduzgun.config.WriteConfig;

public class PomodoroTimer extends JPanel {
    public String buttonText;
    private JButton startButton;

    private SoundPlayer actionSoundPlayer = new SoundPlayer();

    private SoundPlayer asp = new SoundPlayer();
    private Timer timer;
    private int minutes = 25;
    private int seconds;

    private String timeLabelText = "25:00";
    private boolean timerRunning = false;
    private boolean isBreak = false;
    private JLabel label;
    private JLabel whatTimeLabel;
    private ReadConfig readConfig = new ReadConfig();
    private WriteConfig writeConfig;

    public JButton backButton;

    // constructor
    public PomodoroTimer() {

        buttonText = "Resume";

        setBackground(new Color(62, 80, 181));

        // Initialize timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });

        // Initialize frame
        setSize(300, 200);  // Increased height to accommodate the circle
        setLayout(new BorderLayout());

        // Initialize components
        writeConfig = new WriteConfig();
        startButton = new JButton("Start ");
        // label = new JLabel("your username: " + readConfig.getUsername() + "   Your total pomodoro count today: " + readConfig.getPomodoroCount() + "    Date:  " + readConfig.getDate());
        // label.setForeground(Color.WHITE);
        JButton resetButton = new JButton("Reset");

        // Create a panel for the settings button
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        settingsPanel.setOpaque(false); // Make it transparent
        // JButton settingsButton = new JButton("info");
        // JButton otherUsers = new JButton("other people");

        // settingsPanel.add(otherUsers);
        // settingsPanel.add(settingsButton);

        JPanel backButtonPlacerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPlacerPanel.setOpaque(false);
        backButton = new JButton("Back");
        label = new JLabel("Pomodoro completed today: " + readConfig.getPomodoroCount());
        whatTimeLabel = new JLabel("Study");
        whatTimeLabel.setForeground(Color.RED);

        // add buttons to panel
        backButtonPlacerPanel.add(backButton);
        backButtonPlacerPanel.add(label);
        backButtonPlacerPanel.add(whatTimeLabel, BorderLayout.CENTER);

        // Add components to the frame
        add(backButtonPlacerPanel, BorderLayout.NORTH);
        // add(label, BorderLayout.AFTER_LAST_LINE);
        add(startButton, BorderLayout.WEST);
        add(resetButton, BorderLayout.EAST);

        // Start button action listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseResumeTimer();
                startButton.setText(isTimerRunning() ? "Pause    " : buttonText);
            }
        });

        // Reset button action listener
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                resetTimer();
                startButton.setText("Start");

            }
        });
    }

    public void startTimer() {
        if (!timer.isRunning()) {
            timer.start();
            timerRunning = true;
        }
    }

    // timer pause resume events
    public void pauseResumeTimer() {
        if (timer.isRunning()) {
            timer.stop();
            timerRunning = false;
        } else {
            timer.start();
            asp.playSound(0);
            timerRunning = true;
        }
    }

    // reset timer
    public void resetTimer() {
        timer.stop();
        isBreak = false;
        minutes = 25; // 25
        seconds = 0; // 0
        updateTimeLabel(formatTime(minutes, seconds));
        timerRunning = false;
        whatTimeLabel.setText("Study");
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }

    private void updateTimer() {

        // when over, change button text, play sound
        if (minutes == 0 && seconds == 0) {
            timer.stop();
            asp.playSound(1);
            buttonText = "Resume";
            // JOptionPane.showMessageDialog(this, "Time's up! Take a break.", "Pomodoro Timer", JOptionPane.INFORMATION_MESSAGE);
            // if study time
            if (!isBreak){

                isBreak = true;
                startButton.setText("Resume");
                minutes = 5; // Break time 4
                seconds = 0;
                writeConfig.CheckIncPomodoroCount();
                label.setText("Pomodoro completed today: " + readConfig.getPomodoroCount());
                whatTimeLabel.setText("Break");

            }
            // if study time not break time
            else {

                isBreak = false;
                startButton.setText("Resume");
                minutes = 25; // Break time
                seconds = 0;
                whatTimeLabel.setText("Study");

            }
            updateTimeLabel(formatTime(minutes, seconds));
        }

        else {
            if (seconds > 0) {
                seconds--;

            } else {
                seconds = 59;
                if (minutes > 0) {
                    minutes--;
                }
            }
            updateTimeLabel(formatTime(minutes, seconds));
        }
    }

    // time formatter
    private String formatTime(int minutes, int seconds) {
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateTimeLabel(String time) {
        timeLabelText = time;
        repaint();
    }

    // make the circle perfect, theme is northern-lights
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw a radial gradient circle in the background
        Graphics2D g2d = (Graphics2D) g;
        Point2D center = new Point2D.Float(getWidth() / 2, getHeight() / 2);
        float radius = getWidth() / 2.0f;
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(163, 220, 111), new Color(42, 39, 130)};
        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(gradient);

        int circleDiameter = Math.min(getWidth(), getHeight()) - 60; // 20
        int circleX = (getWidth() - circleDiameter) / 2;
        int circleY = (getHeight() - circleDiameter) / 2 + 15;
        g2d.fill(new Ellipse2D.Float(circleX, circleY, circleDiameter, circleDiameter));

        // Draw the time label in the center
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(timeLabelText);
        int stringHeight = fontMetrics.getAscent();
        int x = (getWidth() - stringWidth) / 2;
        int y = (getHeight() + stringHeight) / 2;
        g.drawString(timeLabelText, x, y);
    }
}

// length of Map is the matter
// when newcomer, asks the length to the every node
// gets data from the one having the most length and broadcasts it
// at the end every node knows every other
// when a node receives itself, it doesn't do anything