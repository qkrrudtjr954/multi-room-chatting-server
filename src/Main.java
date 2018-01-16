
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        HashMap<String, List<Socket>> rooms = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            while(true){

                System.out.println("Multi Chatting Server Started.");


                Socket socket = serverSocket.accept();
                System.out.println("1. socket accepted");

                System.out.println("2. client : " +socket.getInetAddress()+":"+socket.getPort());

                DataInputStream dis = new DataInputStream(socket.getInputStream());

                String roomName = dis.readUTF();

                System.out.println("roomName : "+roomName);

                if(rooms.get(roomName)==null){
                    List<Socket> sockets = new ArrayList<>();
                    rooms.put(roomName, sockets);
                }

                rooms.get(roomName).add(socket);

                rooms.get(roomName).forEach(System.out::println);
                new WriteThread(rooms.get(roomName), socket, dis).start();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
