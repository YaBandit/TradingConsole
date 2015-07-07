/**
 * Created by dylan on 6/24/2015.
 */
public class CommandProcessor {

    private static final String DELIMITER = " ";
    public static int username = 0;
    private static boolean loggedIn = false;
    private static boolean connected = false;
    private SocketConnection socketConnection = new SocketConnection();

    private long inventory = 0;
    private long cash = 100000;

    public boolean ProcessClientComment(String command) {

        String[] inputs = SplitCommand(command);
        if (inputs != null) {

            CommandEnum commandEnum = null;

            try {
                commandEnum = CommandEnum.valueOf(inputs[0].toUpperCase());
            } catch (Exception e) {
                System.out.println("Command not recognised");
            }

            if (commandEnum != null) {
                switch (commandEnum) {
                    case LOGIN:
                        Login(inputs);
                        break;
                    case HELP:
                        Help();
                        break;
                    case BUY:
                        processOrder(CommandEnum.BUY, inputs);
                        break;
                    case SELL:
                        processOrder(CommandEnum.SELL, inputs);
                        break;
                    case CONNECT:
                        Connect(inputs);
                        break;
                    case REGISTER:
                        socketConnection.registerClient();
                        break;
                    case EXIT:
                        return true;
                }
            }
        }
        return false;
    }

    private String[] SplitCommand(String command) {
        if (command.length() == 0) {
            return null;
        }
        return command.split(DELIMITER);
    }

    private void Connect(String[] inputs) {
        if (loggedIn) {
            if (socketConnection.client != null) {
                if (!socketConnection.client.isConnected()) {
                    System.out.println("You are already connected");
                }
            }
            if (socketConnection.validateCommand(inputs)) {
                connected = socketConnection.connect(inputs[1], Integer.parseInt(inputs[2]));
            } else {
                System.out.println("Connection was refused");
            }
        } else {
            System.out.println("Please log in before you can connect");
        }
    }

    private void Login(String[] inputs) {
        if (tryParseInt(inputs[1])) {
            username = Integer.parseInt(inputs[1]);
            loggedIn = true;
        } else {
            System.out.println("You have entered an incorrect ID");
        }
    }

    public void processOrder(CommandEnum side, String[] inputs) {
        if (IsOrderValid(inputs)) {
            switch (side) {
                case BUY:
                    socketConnection.sendOrder(Integer.parseInt(inputs[1]), Long.parseLong(inputs[2]), 1);
                    break;
                case SELL:
                    socketConnection.sendOrder(Integer.parseInt(inputs[1]), Long.parseLong(inputs[2]), 2);
                    break;
            }
        } else {
            System.out.println("Not a valid order");
        }
    }

    public boolean IsOrderValid(String[] order) {

        int commandArgs = order.length;
        if (commandArgs < 2) { return false; }
        if (!tryParseInt(order[1])) { return false; }

        return true;
    }

    public static boolean tryParseInt(String value) {
        try
        {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException nfe) {
            return false;
        }
    }

    private void Help() {
        System.out.println("The following are the usable commands:" +
                "\n login" +
                "\n connect" +
                "\n register" +
                "\n buy" +
                "\n sell" +
                "\n help" +
                "\n exit");
    }

}
