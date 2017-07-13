package ERFAN.networking;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class Server {

    private byte[] sendArray, receiveArray;
    private DatagramSocket socket;
    private Vector<ClientHolder> clientList;
    private Vector<byte[]> requestsQueue;
    private static int serverPortNumber;
    private static InetAddress serverIP;
    private static String loginMassage = "User Joining";
    private boolean keepServerRunning = true;
    public static void main(String[] args) {
        new Server();
    }

    public Server(){
        try{
            socket = new DatagramSocket(0);
            serverIP = InetAddress.getLocalHost();
            serverPortNumber = socket.getLocalPort();
            clientList = new Vector<>();
            requestsQueue = new Vector<>();
            System.out.println(getServerIP());
            System.out.println(getServerPortNumber());
            init();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void init(){

        new Thread(() -> {
            // reciver thread;
            System.out.println("RECEIVER RUNNING ...");
            while (keepServerRunning){
                try{
                    receiveArray = new byte[4096];
                    DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
                    socket.receive(receivePacket);
                    String massage = new String(receivePacket.getData());
                    byte[] storeMe = receiveArray.clone();
                    if(massage.contains(loginMassage)){
                        int userPort = receivePacket.getPort();
                        InetAddress userAddress = receivePacket.getAddress();
                        clientList.add(new ClientHolder(userAddress, userPort));
                        System.out.println("new User joined...\n"+userAddress+"  P:"+userPort);
                    }
                    else{
                        requestsQueue.add(storeMe);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            // sender ...
            System.out.println("SENDER RUNNING ...");
            while (keepServerRunning){
                try{
                    while (!requestsQueue.isEmpty()){
                        sendArray = new byte[4096];
                        sendArray = requestsQueue.elementAt(0);
                        for(int i = 0; i < clientList.size(); i++){
                            DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, clientList.elementAt(i).getClientAddress(), clientList.elementAt(i).getClientPort());
                            socket.send(sendPacket);
                            System.out.println("Packet sent to: "+clientList.elementAt(i).getClientAddress()+"  P:"+clientList.elementAt(i).getClientPort());
                        }
                        requestsQueue.remove(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    // Getters and Setters:


    public void setKeepServerRunning(boolean keepServerRunning) {
        this.keepServerRunning = keepServerRunning;
    }

    public InetAddress getServerIP() {
        return serverIP;
    }

    public int getServerPortNumber() {
        return serverPortNumber;
    }

    public  String getLoginMassage() {
        return loginMassage;
    }

    // nested Class:
    private class ClientHolder{
        private InetAddress clientAddress;
        private int clientPort;
        private ClientHolder(InetAddress clientAddress, int clientPort){
            this.clientAddress = clientAddress;
            this.clientPort = clientPort;
        }

        public InetAddress getClientAddress() {
            return clientAddress;
        }

        public int getClientPort() {
            return clientPort;
        }
    }

}
