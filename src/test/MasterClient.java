package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;

public class MasterClient {

    private int portNumber = 8080;
    private String host = "172.20.10.3";
    private byte[] sendArray, reciveArray;
    private InetAddress serverIPAddress;
    private DatagramSocket clientSocket;
    private int[][] table;
    private BufferedReader inFromUser;

    public static void main(String[] args) throws IOException {
        new MasterClient().init();
    }

    public MasterClient(){
        try{
            serverIPAddress = InetAddress.getByName(host);
            clientSocket = new DatagramSocket();
            table = new int[10][10];
            inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            sendArray = new byte[4096];
            String initalPlayerMassage = "player joined";
            sendArray = initalPlayerMassage.getBytes();
            DatagramPacket initialPacket = new DatagramPacket(sendArray, sendArray.length, serverIPAddress, portNumber);
            clientSocket.send(initialPacket);
            System.out.println("initial sent");
            System.out.println(initialPacket.getAddress());
            System.out.println(initialPacket.getPort());
            for (int i = 0; i < 10; i++)
                System.out.println(Arrays.toString(table[i]));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {

        Thread sender = new Thread(() -> {
            // Sender
            try{
                while (true){
                    System.out.println("i");
                    int i = Integer.parseInt(inFromUser.readLine());
                    System.out.println("j");
                    int j = Integer.parseInt(inFromUser.readLine());
                    System.out.println("value");
                    int value = Integer.parseInt(inFromUser.readLine());

                    sendArray = new byte[4096];
                    String sendMe = i + " " + j + " " + value + "\n";
                    sendArray = sendMe.getBytes();
                    DatagramPacket sendMePacket = new DatagramPacket(sendArray, sendArray.length,serverIPAddress, portNumber);
                    clientSocket.send(sendMePacket);
                    System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSEEEEEEEEEEEEEEEEENNNNNNNNNNNNNNNTTTTTTTTT");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        sender.start();

        Thread reciver = new Thread(() -> {
            // reciver ...
            while (true){
                try{
                    reciveArray = new byte[4096];
                    DatagramPacket recivedPacket = new DatagramPacket(reciveArray, reciveArray.length);
                    System.out.println("waiting for a packet to receive");
                    clientSocket.receive(recivedPacket);
                    System.out.println("gotsomething now updating");
                    String massage = new String(recivedPacket.getData());
                    String[] tokens = massage.split("\\s");
                    table[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])] = Integer.parseInt(tokens[2]);
                    for(int i=0;i<10;i++){
                        System.out.println(Arrays.toString(table[i]));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
        reciver.start();
    }

}
