package test;


import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Client {

    private byte[] sendArray, receiveArray;
    private int clientPort, serverPort;
    private InetAddress clientAddress, serverAddress;
    private DatagramSocket socket;
    private boolean keepClientGoing = true;
    private Vector<SerialClass> serialClasses;
    private int counnter = 1;

    public static void main(String[] args) {
        new Client();
    }

    public Client(){
        try{
            serialClasses = new Vector<>();
            socket = new DatagramSocket(0);
            clientPort = socket.getLocalPort();
            clientAddress = InetAddress.getLocalHost();
            serverAddress = InetAddress.getByName("172.20.10.3");
            serverPort = 61127;
            init();



        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        joinServer();
        new Thread(() -> {
            // sender ...
            while (keepClientGoing){
                try{
                    System.out.println("sending someting now");
                    SerialClass temp = new SerialClass(clientPort,counnter);
                    counnter++;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);

                    assert oos != null;
                    oos.writeObject(temp);
                    oos.flush();
                    oos.close();

                    String massage = new String(baos.toByteArray());
                    sendArray = new byte[4096];
                    sendArray = massage.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length,serverAddress,serverPort);
                    socket.send(sendPacket);

                    Thread.sleep(3000);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            // Receiver
            while (keepClientGoing){
                try{
                    receiveArray = new byte[4096];
                    DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
                    socket.receive(receivePacket);
                    System.out.println("got something!!!!!!!!!!!!!!!!!!!!!");
                    ByteInputStream bis = null;
                    bis =new ByteInputStream();
                    ObjectInputStream ois = null;
                    ois=new ObjectInputStream(bis);

                    SerialClass sc = null;
                    assert ois != null;
                    sc = (SerialClass) ois.readObject();
                    serialClasses.add(sc);
                    System.out.println("added something");

                    for(int i=0;i<serialClasses.size(); i++){
                        System.out.println(serialClasses.elementAt(i).toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

    private void joinServer(){
        sendArray = new byte[4096];
        sendArray = Server.getLoginMassage().getBytes();
        System.out.println(Server.getLoginMassage());
        System.out.println(Server.getServerIP());
        System.out.println(Server.getServerPortNumber());
        DatagramPacket loginPacket = new DatagramPacket(sendArray, sendArray.length, serverAddress,serverPort);
        try {
            socket.send(loginPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
