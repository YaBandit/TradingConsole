/**
 * Created by dylan on 6/24/2015.
 */
public class CommandProcessor {

    private static final String DELIMITER = " ";
    public static String NOT_LOGGED_IN = "NOT LOGGED IN";

    public static String username = NOT_LOGGED_IN;
    private static boolean loggedIn = false;

    private Trade trade = new Trade();

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
                    case HELP:
                        break;
                    case BUY:
                        trade.Buy(inputs);
                        break;
                    case SELL:
                        trade.Sell(inputs);
                        break;
                    case CONNECT:
                        break;
                    case LOGIN:
                        Login(inputs);
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



        } else {
            System.out.println("Please log in before you can connect");
        }
    }

    private void Login(String[] inputs) {
        username = inputs[1];
        loggedIn = true;
    }



}
