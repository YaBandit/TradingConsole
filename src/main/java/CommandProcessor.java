import org.omg.CORBA.PUBLIC_MEMBER;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dylan on 6/24/2015.
 */
public class CommandProcessor {

    private static final String DELIMITER = " ";
    public static int username = 0;
    private static boolean loggedIn = false;
    private static boolean connected = false;
    private SocketConnection socketConnection = new SocketConnection();

    private long cash = 100000;
    private Map<Integer, Integer> inventory = new HashMap<Integer, Integer>();

    public CommandProcessor() {
        inventory.put(1, 0);
        inventory.put(2, 0);
        inventory.put(3, 0);
        inventory.put(4, 0);
        inventory.put(5, 0);
    }

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
                    case CASH:
                        System.out.println("Cash = " + cash);
                        break;
                    case INVENTORY:
                        displayInventory();
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
            boolean processed = false;
            switch (side) {
                case BUY:
                    processed = socketConnection.sendOrder(Integer.parseInt(inputs[1]), Long.parseLong(inputs[2]), 1);
                    break;
                case SELL:
                    if (hasInventory(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]))) {
                        processed = socketConnection.sendOrder(Integer.parseInt(inputs[1]), Long.parseLong(inputs[2]), 2);
                    }
                    break;
            }
            if (processed) {
                updateCashAndInventory(side, inputs);
            }
        } else {
            System.out.println("Not a valid order");
        }
    }

    public boolean hasInventory(int commodity, int quantity) {
        int currentInventory = inventory.get(commodity);
        if (currentInventory < quantity) {
            System.out.println("You do not have sufficient inventory for this commodity");
            return false;
        }
        return true;
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

    public void displayInventory() {
        System.out.println("Commodity : Inventory");
        Iterator iterator = inventory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }

    public void updateCashAndInventory(CommandEnum side, String[] inputs) {
        int commodity = Integer.parseInt(inputs[1]);
        int quantity = Integer.parseInt(inputs[2]);

        // I NEED TO KNOW THE ACK RESPONSE WITH THE COST TO ADD/REMOVE THE CASH
        //int cash =

        switch (side) {
            case BUY:
                int currentInventoryBuy = inventory.get(commodity);
                inventory.replace(commodity, currentInventoryBuy + quantity);
                break;
            case SELL:
                int currentInventorySell = inventory.get(commodity);
                inventory.replace(commodity, currentInventorySell - quantity);
                break;
        }
    }

    private void Help() {
        System.out.println("The following are the usable commands:" +
                "\n login" +
                "\n connect" +
                "\n register" +
                "\n buy" +
                "\n sell" +
                "\n cash" +
                "\n inventory" +
                "\n help" +
                "\n exit");
    }

}
