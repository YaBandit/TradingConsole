import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by dylan on 6/25/2015.
 */
public class SocketConnection {

    public SocketChannel client;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);


    private boolean isRegistered;

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
                isRegistered = registerClient();
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

    public boolean registerClient() throws IOException {
        buffer.putInt(Utils.CLIENT_LOGIN_INT);
        buffer.putInt(CommandProcessor.username);

        sendMessageAndWaitForResponse();

        int messageType = buffer.getInt();

        // Process Responce


        buffer.clear();
        return true;
    }

    public void sendMessageAndWaitForResponse() {
        try {
            send();
            waitForResponse();
        } catch (IOException e) {
            e.printStackTrace();
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
