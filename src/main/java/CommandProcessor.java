/**
 * Created by dylan on 6/24/2015.
 */
public class CommandProcessor {

    private static final String DELIMITER = " ";
    public static String NOT_LOGGED_IN = "NOT LOGGED IN";

    public static int username = 0;
    private static boolean loggedIn = false;
    private static boolean connected = false;
    private SocketConnection socketConnection = new SocketConnection();
    private Trade trade = new Trade(socketConnection);

    public boolean ProcessClientComment(String command) {

        String[] inputs = SplitCommand(command);
        if (inputs != null) {

            CommandEnum commandEnum = null;

            try {
                commandEnum = CommandEnum.valueOf(inputs[0].toUpperCase());
            } catch (Exception e) {
                Utils.print("Command not recognised");
            }

            if (loggedIn) {
                if (commandEnum != null) {
                    switch (commandEnum) {
                        case LOGIN:
                            Login(inputs);
                            break;
                        case HELP:
                            Help();
                            break;
                        case BUY:
                            trade.processOrder(CommandEnum.BUY, inputs);
                            break;
                        case SELL:
                            trade.processOrder(CommandEnum.SELL, inputs);
                            break;
                        case CONNECT:
                            Connect(inputs);
                            break;

                        case EXIT:
                            return true;
                    }
                }
            } else {
                if (commandEnum.equals(CommandEnum.LOGIN)) {
                    Login(inputs);
                } else if (commandEnum.equals(CommandEnum.HELP)) {
                    Help();
                } else if (commandEnum.equals(CommandEnum.EXIT)) {
                    return true;
                }else {
                    Utils.print("Please log in to use other features");
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
            if (!socketConnection.client.isConnected()) {
                Utils.print("You are already connected");
            }
            if (socketConnection.validateCommand(inputs)) {
                connected = socketConnection.connect(inputs[1], Integer.parseInt(inputs[2]));
            } else {
                Utils.print("Connection was refused");
            }
        } else {
            Utils.print("Please log in before you can connect");
        }
    }

    private void Login(String[] inputs) {
        if (Utils.tryParseInt(inputs[1])) {
            username = Integer.parseInt(inputs[1]);
            loggedIn = true;
        } else {
            Utils.print("You have entered an incorrect ID");
        }
    }

    private void Help() {
        Utils.print("The following are the usable commands:" +
                "\n login" +
                "\n connect" +
                "\n register" +
                "\n buy" +
                "\n sell" +
                "\n help" +
                "\n exit");
    }

}
