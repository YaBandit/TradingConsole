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
    private boolean isRegistered = false;

    public boolean validateCommand(String[] inputs) {

        int commandArgs = inputs.length;
        if (commandArgs != 3) { return false; }
        if (!CommandProcessor.tryParseInt(inputs[2])) { return false; }

        return true;
    }

    public boolean connect(String host, int port) {
        try {
            System.out.println("Trying connection to " + host + " on port " + port);

            InetSocketAddress hostAddress = new InetSocketAddress(host, port);
            client = SocketChannel.open(hostAddress);

            if (client.isConnected()) {
                System.out.println("You are now connected to the matching engine");
            } else {
                System.out.println("Failed to connect to Matching Engine");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client.isConnected();
    }

    public boolean registerClient() {

        if (client != null) {
            if (client.isConnected()) {
                buffer.putInt(BufferProtocol.REGISTER_REQUST.getValue());
                buffer.putInt(CommandProcessor.username);

                sendMessageAndWaitForResponse();

                BufferProtocol returnedMsg = getMsgType();

                switch (returnedMsg) {
                    case REGISTER_ACCEPTED:
                        System.out.println("Registration Accepted");
                        int nextInt = buffer.getInt();
                        System.out.println("You have registered as client: " + nextInt);
                        isRegistered = true;
                        break;
                    case REGISTER_REJECTED:
                        System.out.println("Registration Rejected");
                        isRegistered = false;
                        break;
                }
                buffer.clear();
            } else {
                System.out.println("You are not connected");
            }
        } else {
            System.out.println("You are not connected");
        }
        return isRegistered;
    }

    public boolean sendOrder(int symbol, long quantity, int side) {

        boolean successfullyTraded = false;

        if (client != null) {
            if (client.isConnected()) {
                if (isRegistered) {
                    buffer.putInt(BufferProtocol.ORDER_MARKET.getValue());
                    buffer.putInt(CommandProcessor.username);
                    buffer.putInt(symbol);
                    buffer.putLong(quantity);
                    buffer.putInt(side);

                    sendMessageAndWaitForResponse();

                    BufferProtocol returnedMsg = getMsgType();

                    switch (returnedMsg) {
                        case ORDER_ACK:
                            System.out.println("Trade Successfully Executed");
                            successfullyTraded = true;
                            break;
                        case ORDER_REJECT:
                            System.out.println("Order was rejected");
                            buffer.getInt();
                            int reasonLength = buffer.getInt();
                            Byte reasonBytes = buffer.get(reasonLength);
                            String reason = reasonBytes.toString();
                            System.out.println(reason);
                            break;
                    }
                    buffer.clear();
                } else {
                    System.out.println("You are not registered");
                }
            } else {
                System.out.println("You are not connected");
            }
        } else {
            System.out.println("You are not connected");
        }
        return successfullyTraded;
    }

    public void sendMessageAndWaitForResponse() {
        try {
            send();
            waitForResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferProtocol getMsgType() {
        buffer.flip();
        int msgTpye = buffer.getInt();
        return BufferProtocol.forValue(msgTpye);
    }

    public void send() throws IOException {
        buffer.flip();
        client.write(buffer);
        buffer.clear();
    }

    public boolean waitForResponse() throws IOException {
        int bytes = client.read(buffer);
        if (bytes == -1) {
            System.out.println("Connection to matching engine closed");
            return false;
        }
        return true;
    }

}
