package ERFAN.networking;//package ERFAN.networking;
//
//
//import ERFAN.menuScreen.WellcomeFrame;
//
//import java.io.*;
//import java.net.*;
//import java.util.Vector;
//
//public class Client {
//
//    private byte[] sendArray, receiveArray;
//    private int clientPort, serverPort;
//    private InetAddress clientAddress, serverAddress;
//    private DatagramSocket socket;
//    private boolean keepClientGoing = true;
//    private BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//    private WellcomeFrame parrent;
//
//    public static void main(String[] args) {
//        new Client(new WellcomeFrame());
//    }
//
//    public Client(WellcomeFrame parrent){
//        this.parrent = parrent;
//        try{
//            socket = new DatagramSocket(0);
//            clientPort = socket.getLocalPort();
//            clientAddress = InetAddress.getLocalHost();
//            System.out.println("please enter the server address now:");
//            serverAddress = InetAddress.getByName(inFromUser.readLine());
//            System.out.println("please enter the server port now");
//            serverPort = Integer.getInteger(inFromUser.readLine());
//            init();
//
//
//
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void init(){
//        joinServer();
//        new Thread(() -> {
//            // sender ...
//            while (keepClientGoing){
//                try{
//                    sendArray = new byte[4096];
//                    DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length,serverAddress,serverPort);
//                    socket.send(sendPacket);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            // Receiver
//            while (keepClientGoing){
//                try{
//                    receiveArray = new byte[4096];
//                    DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
//                    socket.receive(receivePacket);
//                    System.out.println("got something");
//                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveArray);
//                    ObjectInputStream ois = new ObjectInputStream(bais);
//
//                    SerialClass sc = null;
//                    assert ois != null;
//                    sc = (SerialClass) ois.readObject();
//                    serialClasses.add(sc);
//                    System.out.println("added something");
//                    bais.close();
//                    ois.close();
//                    System.out.println("mylist");
//                    for(int i=0;i<serialClasses.size(); i++){
//                        System.out.println(serialClasses.elementAt(i).toString());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }).start();
//    }
//
//    private void joinServer(){
//        sendArray = new byte[4096];
////        sendArray = ;
//        System.out.println(Server.getLoginMassage());
//        System.out.println(Server.getServerIP());
//        System.out.println(Server.getServerPortNumber());
//        DatagramPacket loginPacket = new DatagramPacket(sendArray, sendArray.length, serverAddress,serverPort);
//        try {
//            socket.send(loginPacket);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
