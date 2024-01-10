package com.sadikemreduzgun.network;
import java.net.*;

public class UserStreamer implements NetworkKeeper {

    private static final int BROADCAST_PORT = 8888;

    // it sends once
    // what will be sent? : number,10 for informing how many node it knows
    // the latest one will be chosen the latest one will broadcast the known people
    // handle when Wifi is off/on broken, not reached for some time

    public void sendWhenJoin(String data){
        broadcastData(POM_N_UNAME_ZERO_TEXT, data);
        // broadcastData(LEN_WISH_TEXT, "");
    }

    public void broadcastData(String explainer, String data) {

        try {
            DatagramSocket serverSocket = new DatagramSocket();
            serverSocket.setBroadcast(true);

            // send uname,pomodoroCount,date
            String broadcastMessage = explainer + "," + data;
            byte[] sendData = broadcastMessage.getBytes();

            // define broadcasting
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcastAddress, BROADCAST_PORT);
            // broadcast packet
            serverSocket.send(sendPacket);

            System.out.println("Server: Broadcast message sent.");

            // Sleep for a while before sending the next broadcast
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void oneToOneComm(InetAddress address, String data){ // one to one communication in next version
        // always broadcasting not good for health
    }

}
