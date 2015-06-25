import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by dylan on 6/25/2015.
 */
public class SocketConnection {

    private SocketChannel client;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    private final static int CLIENT_LOGIN_INT = 1;
    private final static int ORDER_INT = 1;

    public boolean validateCommand(String[] inputs) {

        int commandArgs = inputs.length;
        if (commandArgs != 3) { return false; }

        if (!Utils.tryParseInt(inputs[2])) { return false; }

        return true;
    }

    public boolean connect(String host, int port) {
        try {
            Utils.print("Trying connection to " + host + " on port " + port);

            InetSocketAddress hostAddress = new InetSocketAddress(host, port);
            client = SocketChannel.open(hostAddress);

            if (client.isConnected()) {
                registerClient();
                Utils.print("You are now connected to the matching engine");
            } else {
                Utils.print("Failed to connect to Matching Engine");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client.isConnected();
    }

    public void registerClient() {
        buffer.putInt(CLIENT_LOGIN_INT);
        buffer.putInt(CommandProcessor.username);
        sendMessage();
    }

    public String sendMessage() {
        try {
            send();
            waitForResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void IsLoggedOn() throws IOException {
        if (waitForResponse()) {
            String request = decoder.decode(buffer).toString();
            Utils.print("YOLO");
        }
    }

    public void send() throws IOException {
        buffer.flip();
        client.write(buffer);
        buffer.clear();
    }

    public boolean waitForResponse() throws IOException {
        int bytes = client.read(buffer);
        if (bytes == -1) {
            Utils.print("Connection to matching engine closed");
            return false;
        }
        return true;
    }

}
