package com.sadikemreduzgun.network;
import com.sadikemreduzgun.config.WriteConfig;
import com.sadikemreduzgun.config.ReadConfig;
import java.io.IOException;
import java.net.*;
import java.util.*;

import com.sadikemreduzgun.timer.DayChecker;
import com.sadikemreduzgun.network.UserStreamer;


// send packages every minute
public class LocalNetworkSearcher extends DynamicStorage implements NetworkKeeper{

    WriteConfig writeConfig;
    DayChecker dayChecker;
    private UserStreamer userStreamer;
    private ReadConfig readConfig;
    private static String newResponse = null;
    private static Timer timer;
    Map<String, String> knownLens;
    Map<String, String> dataOthers;

    // constructor
    public LocalNetworkSearcher(){
        // assign vars
         dayChecker = new DayChecker();
         writeConfig = new WriteConfig();
         userStreamer = new UserStreamer();
         readConfig = new ReadConfig();
         knownLens = new HashMap<>();
         dataOthers = new HashMap<>();

    }

    // receiver
    public void startSearcher() {

        try {
            // assign socket while broadcast option is true
            DatagramSocket clientSocket = new DatagramSocket(BROADCAST_PORT);
            clientSocket.setBroadcast(true);

            while (true) {

                // byte object to receive TCP/IP packet
                byte[] receiveData = new byte[PACKET_SIZE];

                // get received packet
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                // turn data into String
                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client: Received broadcast message: " + serverResponse);
                // boolean started = false;

                try {
                    System.out.println("response" + serverResponse);
                    // dig into data
                    String[] responseSplit = serverResponse.split(",");
                    String explainer = responseSplit[0];
                    String[] data = responseSplit[1].split(";");

                    // send values in format will be like knowns(explainer),username:val;uname:val;uname:val;uname:val(known people)
                    /*if (explainer.equals(LEN_WISH_TEXT)) {
                        userStreamer.broadcastData(SENDING_NUMBER_TEXT, Integer.toString(getLen()));
                    } else if (explainer.equals(SEND_KNOWN_ONES_TEXT)) {
                        userStreamer.broadcastData("data", getSeperatedData());
                    }*/
                    if (explainer.equals(POM_N_UNAME_ZERO_TEXT)) { // else if...
                        // addNew(data[0], data[1]);
                        dataOthers.put(data[0], data[1]);
                    } else if (explainer.equals(SEND_ME_UR_INFO_TEXT)){
                        userStreamer.broadcastData(POM_N_UNAME_ZERO_TEXT, readConfig.getUsername()+";"+readConfig.getPomodoroCount());
                    } /*else if (explainer.equals(SENDING_NUMBER_TEXT)) { // next version stuff, no time now :)

                        if (newResponse == null) {
                            newResponse = serverResponse;

                            // Create and start a timer thread to reset serverResponse after 1 minute
                            Thread timerThread = new Thread(() -> {
                                try {
                                    Thread.sleep(60 * 1000); // Sleep for 1 minute

                                    int max = Integer.parseInt(String.valueOf(knownLens.values().toArray()[0]));
                                    int maxIndex = 0;

                                    for (int i = 0; i < knownLens.values().toArray().length; i++) {
                                        if (Integer.parseInt(String.valueOf(knownLens.values().toArray()[0]))>max){
                                            max = Integer.parseInt(String.valueOf(knownLens.values().toArray()[i]));
                                            maxIndex = i;
                                        }
                                    }

                                    String communicationAddress = knownLens.keySet().toArray()[maxIndex].toString();
                                    try {
                                        userStreamer.oneToOneComm(InetAddress.getByName(communicationAddress), getSeperatedData());
                                    } catch (UnknownHostException e) {
                                        throw new RuntimeException(e);
                                    }

                                    newResponse = null;
                                    knownLens = new HashMap<>();

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                            timerThread.start();
                        }

                        knownLens.put(responseSplit[1], responseSplit[1]);
                    }*/
                }
                catch (Exception e){
                    // save into log... but in next version
                    System.out.println("----------------error in LocalNetworkSearcher: \n" + e);

                }

                // Process the received message...
                // open again
                // writeConfig.writeOtherUserSitu(serverResponse); // old version, removed

                // Sleep for a while before receiving the next broadcast
                try {
                    Thread.sleep(SLEEP_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getDataOthers(){
        return dataOthers;
    }

    public static void main(String[] args){
        // startSearcher();
    }

}
