package com.sadikemreduzgun.gui;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.sadikemreduzgun.actions.SoundPlayer;
import com.sadikemreduzgun.config.WriteConfig;
import com.sadikemreduzgun.config.ReadConfig;
import com.sadikemreduzgun.timer.PomodoroTimer;
import com.sadikemreduzgun.network.LocalNetworkSearcher;
import com.sadikemreduzgun.network.UserStreamer;
// class uname getting open page
// class pomodoro page
// class see other users page + beta messaging (write beta next to it)
// searched the practise

// first page if no username is selected (first open, when no .properties file in configs)
class PageUsernameSelection extends JPanel implements RandomUnameDef {

    public JButton pickButton;
    public static JTextField textField;
    // constructor
    public PageUsernameSelection() {
        // initialize with a background
        setBackground(new Color(62, 80, 181));
        setLayout(new GridBagLayout());

        // place buttons in a structure
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        gridBagConstraints.insets = new Insets(10, 10, 10, 10);

        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        // define buttons, labels
        JLabel label = new JLabel("Your Username: ");
        label.setForeground(Color.WHITE);
        textField = new JTextField(randomusernamecr.createRandomUname(), 20);

        pickButton = new JButton("Pick Username");
        JButton buttonRandomUname = new JButton("Random Username");

        add(label, gridBagConstraints);

        // place buttons and text fields organized way
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(textField, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;

        // Align buttons to the left and right
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        add(buttonRandomUname, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        add(pickButton, gridBagConstraints);

        // create and put random username when pickButton clicked, username can be assigned as wish
        buttonRandomUname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText(randomusernamecr.createRandomUname());
            }
        });

        // get username set username in configs
        /*pickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                // go next page
                // ...
                System.out.println(text);
            }
        });*/

    }

    // public JFrame getJFrame()... return JFrame.add(this) // no need
}

class PageOtherUsers extends JPanel{

    public DefaultListModel<String> listModel;
    public JList<String> jList;
    public JButton backButton;

    // constructor
    public PageOtherUsers(){

        setLayout(new GridBagLayout());
        setBackground(new Color(62, 80, 181));
        // setBackground(Color.CYAN);
        // struct list and back button
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        gridBagConstraints.insets = new Insets(10, 10, 10, 10);

        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        // a list for adding the user when a user opened the app
        listModel = new DefaultListModel<String>();

        /*
        listModel.addElement(formatUsername("eleasdsam"));
        listModel.addElement(formatUsername("elemasdsad"));
        listModel.addElement(formatUsername("elem sada"));
        */

        jList = new JList<String>(listModel);
        jList.setBounds(30, 80, 220, 100);

        backButton = new JButton("Back");
        add(backButton, gridBagConstraints);

        gridBagConstraints.gridy = 1;

        add(jList, gridBagConstraints);

    }

    // rebuild list when map is updated
    public void reBuildList(Map usermap){

        Object[] userMapKeys = usermap.keySet().toArray();
        int lenMap = userMapKeys.length;

        for (int i = 0; i < lenMap; i++) {

            String elemI = userMapKeys[i].toString();
            addIntoList(elemI, usermap.get(elemI).toString());
        }

    }

    public void addIntoList(String username, String pomNum){

        if (!listModel.contains(formatUsername(username, pomNum))){
            listModel.addElement(formatUsername(username, pomNum));
        }

    }

    // format strings in a nice looking format
    // user1       14
    // user2       16
    private String formatUsername(String username, String pomCount) {
        return String.format("%-" + 60 + "s %s", username, pomCount); // Assuming a fixed value (e.g., 12) for demonstration
    }
}

// main menu panel
class MainMenu extends JPanel{
    // make buttons public to be able to add actions to them in GUI
    public JButton pomodoroButton;
    public JButton usersOnLocalButton;
    public JButton matchMobileDeviceButton;
    public JButton infoButton;
    public JButton settingsButton;
    // public JButton changeUnameButton; // changing  username is not allowed for that moment maybe next versions, no time

    // constructor
     public MainMenu(){

         // put buttons vertically
         setLayout(new GridBagLayout());
         setBackground(new Color(62, 80, 181));
         // setBackground(Color.CYAN);
        // struct buttons
         GridBagConstraints gridBagConstraints = new GridBagConstraints();
         gridBagConstraints.gridx = 0;
         gridBagConstraints.gridy = 0;

         gridBagConstraints.insets = new Insets(10, 10, 10, 10);

         gridBagConstraints.anchor = GridBagConstraints.CENTER;

         // assign buttons
         pomodoroButton = new JButton("Pomodoro");
         usersOnLocalButton = new JButton("Other users");
         matchMobileDeviceButton = new JButton("Match Mobile");
         infoButton =  new JButton("Info");
         settingsButton = new JButton("Settings");

         add(pomodoroButton, gridBagConstraints);

         gridBagConstraints.gridy = 1;
         add(usersOnLocalButton,gridBagConstraints);

         gridBagConstraints.gridy = 2;
         add(matchMobileDeviceButton, gridBagConstraints);

         gridBagConstraints.gridy = 3;
         add(infoButton, gridBagConstraints);

         gridBagConstraints.gridy = 4;
         add(settingsButton,gridBagConstraints);

     }
}

class InfoPanel extends JPanel{

    ReadConfig readConfig = new ReadConfig();
    public JLabel userLabel;
    public JLabel pomCtLabel;
    public JLabel dateLabel;
    public JButton backMMButton;
    private JPanel buttonPanel;

    // info button page
    public InfoPanel() {

        setLayout(new GridBagLayout());
        setBackground(new Color(62, 80, 181));
        // setBackground(Color.CYAN);

        // struct everything nicely
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        // Create and add the back button to the button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        backMMButton = new JButton("Back");
        buttonPanel.add(backMMButton);

        // Add the button panel to the main panel
        add(buttonPanel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        userLabel = new JLabel("Username: " + readConfig.getUsername());
        userLabel.setForeground(Color.WHITE);

        add(userLabel, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        pomCtLabel = new JLabel("Pomodoro count today: " + readConfig.getPomodoroCount());
        pomCtLabel.setForeground(Color.WHITE);
        add(pomCtLabel, gridBagConstraints);

        gridBagConstraints.gridy = 3;
        dateLabel = new JLabel("Date: " + readConfig.getDate());
        dateLabel.setForeground(Color.WHITE);
        add(dateLabel, gridBagConstraints);
    }
}

class SettingsPanel extends JPanel{

    Color color;
    public JButton settingsBackButton;
    private Color defaultColor;

    // just changing background color in that version, maybe lots of things in next versions
    public SettingsPanel(){

        defaultColor = new Color(62, 80, 181);
        setBackground(defaultColor);
        // setLayout(new BorderLayout());
        JButton chooseColor = new JButton("Choose Color");
        JButton resetColor = new JButton("Reset Color");

        settingsBackButton = new JButton("back");
        add(settingsBackButton);
        add(chooseColor);
        add(resetColor);
        chooseColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = JColorChooser.showDialog(SettingsPanel.this, "Make a choice", Color.MAGENTA);
                if (color != null) {
                    setBackground(color);
                }
            }
        });

        resetColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = defaultColor;
                setBackground(color);
            }
        });

    }

    public Color getColor(){
        return color;
    }
}

class PageMessaging{
    public PageMessaging() {

    }
}

// main gui
public class GUI{

    private JFrame frame;
    private JPanel cardPanel;
    private JButton button;
    private JLabel label;
    private JPanel nameAcqPanel;
    private JTextField textField;
    private CardLayout cardLayout;
    private String text;

    // remove static
    private static LocalNetworkSearcher localNetworkSearcher= new LocalNetworkSearcher();
    ;

    private WriteConfig writeConfig;

    private JFrame mainFrame;
    private PageUsernameSelection usernameSelectionPage;
    private PageOtherUsers pageOtherUsers;
    private PomodoroTimer pomodoroFrame;
    private ReadConfig readConfig;

    private MainMenu mainMenu;

    private InfoPanel infoPanel;

    private SettingsPanel settingsPanel;

    private Color color;

    JFrame page1;
    JFrame page2;
    private UserStreamer userStreamer;

    // pop menu for adding in hidden icons on Windows to exit check hidden icons
    private static PopupMenu createPopupMenu() {
        PopupMenu popupMenu = new PopupMenu();

        // Add an exit option to the popup menu
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popupMenu.add(exitItem);

        return popupMenu;
    }

    // constructor
    public GUI(){

        readConfig = new ReadConfig();
        pageOtherUsers = new PageOtherUsers();
        userStreamer = new UserStreamer();
        String uNamePom = readConfig.getUsername() + ";" +readConfig.getPomodoroCount();
        userStreamer.sendWhenJoin(uNamePom);
        // start searcher server, listener for coming socket data
        Thread searcherThread = new Thread(() -> {
            localNetworkSearcher.startSearcher();
        });

        // assign lots of other classes and other objects of swing to be used
        mainFrame = new JFrame("Pomodoro Locial");
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        color = new Color(62, 80, 181);

        // Create instances of your frames/pages
        usernameSelectionPage = new PageUsernameSelection();
        pomodoroFrame = new PomodoroTimer();
        pageOtherUsers = new PageOtherUsers();
        writeConfig = new WriteConfig();
        readConfig = new ReadConfig();
        mainMenu = new MainMenu();
        infoPanel = new InfoPanel();
        settingsPanel = new SettingsPanel();

        // go next after username is selected
        usernameSelectionPage.pickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = PageUsernameSelection.textField.getText();

                System.out.println(username);
                writeConfig.writeUsername(username);
                if (settingsPanel.getColor() != null){
                    mainMenu.setBackground(settingsPanel.getColor());
                }

                cardLayout.show(cardPanel, "mainMenu");

            }
        });

        // update other users page, check every 5 seconds
        Thread buildListAlways = new Thread(() -> {
            while (true) {
                // Assume this method returns a list of messages
                Map<String, String> messages = localNetworkSearcher.getDataOthers();
                Object[] a = messages.keySet().toArray();

                System.out.println(a.length);
                for (int i = 0; i < a.length; i++) {
                    System.out.println("printing:" + a[i]);
                }

                // Update the GUI with the new messages
                pageOtherUsers.reBuildList(messages);
                // gui.updateMessageList(messages);

                try {
                    Thread.sleep(5000); // Sleep for 1 second (adjust as needed)
                    System.out.println("wookign fine");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        );

        // start threads
        searcherThread.start();
        buildListAlways.start();

        // Create a system tray icon, hidden applications
        SystemTray tray = SystemTray.getSystemTray();
        Image iconHidden = Toolkit.getDefaultToolkit().getImage("sources/hiddenicon.png");

        // taskbar icon initialization
        ImageIcon iconToolbar = new ImageIcon("sources/toolbaricon.png");

        PopupMenu popupMenu = createPopupMenu();
        TrayIcon trayIcon = new TrayIcon(iconHidden, "Pomodoro Locial", popupMenu);

        // add icon to hidden apps
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // action settings back button
        settingsPanel.settingsBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){
                    mainMenu.setBackground(settingsPanel.getColor());
                }

                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        // action page of others back button
        pageOtherUsers.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){
                    mainMenu.setBackground(settingsPanel.getColor());
                }

                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        // pomodoro back button
        pomodoroFrame.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){
                    mainMenu.setBackground(settingsPanel.getColor());
                }
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        // action of info button in main menu
        mainMenu.infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                infoPanel.userLabel.setText("Username: "+ readConfig.getUsername());
                infoPanel.pomCtLabel.setText("Pomodoro count today: " + readConfig.getPomodoroCount());
                infoPanel.dateLabel.setText("Date: " + readConfig.getDate());

                if (settingsPanel.getColor() != null){
                    infoPanel.setBackground(settingsPanel.getColor());
                }

                cardLayout.show(cardPanel, "info");
            }
        });

        // go users on local network page button
        mainMenu.usersOnLocalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){
                    pageOtherUsers.setBackground(settingsPanel.getColor());
                }
                cardLayout.show(cardPanel, "userlist");
            }
        });

        // go settings button
        mainMenu.settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){

                    settingsPanel.setBackground(settingsPanel.getColor());
                }
                cardLayout.show(cardPanel, "settings");
            }
        });

        // info panel back button
        infoPanel.backMMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){
                    mainMenu.setBackground(settingsPanel.getColor());
                }
                // infoPanel.userLabel.setText(); // no need because no changing username
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        // mainMenu.matchMobileDeviceButton.actionPerformed... not applied for that moment, after mobile app is over.

        // go pomodoro at main menu button
        mainMenu.pomodoroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (settingsPanel.getColor() != null){
                    pomodoroFrame.setBackground(settingsPanel.getColor());
                }
                // change to pomodoro panel
                cardLayout.show(cardPanel, "Pomodoro");
            }
        });

        // System.out.println(readConfig.getUsername());
        // Add frames/pages to the card panel, pages
        cardPanel.add(usernameSelectionPage, "UsernameSelection");
        cardPanel.add(pomodoroFrame, "Pomodoro");
        cardPanel.add(pageOtherUsers, "userlist");
        cardPanel.add(mainMenu, "mainMenu");
        cardPanel.add(infoPanel, "info");
        cardPanel.add(settingsPanel, "settings");

        // Set up the card layout for the card panel
        if (readConfig.getUsername().isEmpty()){
            cardLayout.show(cardPanel, "UsernameSelection");
        }
        else{
            // cardLayout.show(cardPanel, "Pomodoro");
            cardLayout.show(cardPanel, "mainMenu");
        }

        // Create a main panel to hold the card panel and the "Next" button
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // Add the main panel to the JFrame
        mainFrame.add(mainPanel);

        // set a toolbar icon, blue P letter.
        mainFrame.setIconImage(iconToolbar.getImage());
        mainFrame.setSize(800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // center main frame
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
        // frameUnameSelection.setVisible(true);
    }

    public void showMainFrame() {
        mainFrame.setVisible(true);
    }

    /*public static void main(String[] args) {
        // GUI gui = new GUI();
        // get data using below while thread is going on
        // Map map = localNetworkSearcher.getDynamicMap();

        // SwingUtilities.invokeLater(() -> new GUI());




        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }*/
}
