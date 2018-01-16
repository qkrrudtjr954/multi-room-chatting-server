import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class WriteThread extends Thread {

    List<Socket> sockets;
    Socket socket;

    public WriteThread(List<Socket> sockets, Socket socket) {
        this.sockets = sockets;
        this.socket = socket;

    }

    @Override
    public void run() {

        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                String msg = dis.readUTF();
                System.out.println("Msg from client : " + msg);

                sockets.stream()
                        .filter(socket1 -> !socket1.equals(socket))
                        .forEach(socket1 -> {
                            try {
                                DataOutputStream dos = new DataOutputStream(socket1.getOutputStream());
                                dos.writeUTF(msg);
                                dos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                System.out.println("Send to all Msg.");
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
