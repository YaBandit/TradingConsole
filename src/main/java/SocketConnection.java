import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by dylan on 6/25/2015.
 */
public class SocketConnection {

    public boolean validateCommand(String[] inputs) {

        int commandArgs = inputs.length;
        if (commandArgs != 3) { return false; }

        if (!Utils.tryParseInt(inputs[2])) { return false; }

        return true;
    }

    public void connect(String host, int port) {
        try {
            Utils.print("Connection to " + host + " on port " + port);

            InetSocketAddress hostAddress = new InetSocketAddress(host, port);
            SocketChannel client = SocketChannel.open(hostAddress);



            System.out.println("Client sending messages to server...");




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public class SocketObject implements Runnable {

        public Queue<String> outgoingMessages = new ConcurrentLinkedDeque();
        public Queue<String> incomingMessages = new ConcurrentLinkedDeque();

        private SocketChannel client;

        public SocketObject(SocketChannel client) {
            this.client = client;
        }

        public void run() {
            while (true) {
                // Send outgoing, and listen to incoming messages

                try {
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int bytesRead = client.read(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
