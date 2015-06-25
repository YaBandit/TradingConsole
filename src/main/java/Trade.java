/**
 * Created by dylan on 6/24/2015.
 */
public class Trade implements TradeInterface {

    private final SocketConnection socketConnection;

    public Trade(SocketConnection socketConnection) {
        this.socketConnection = socketConnection;
    }

    public void processOrder(CommandEnum side, String[] inputs) {
        if (socketConnection.client.isConnected()) {
            if (IsOrderValid(inputs)) {





            } else {
                Utils.print("Not a valid order");
            }
        } else {
            Utils.print("You are not connected to the matching engine");
        }
    }

    public void Buy(String[] inputs) {

    }

    public void Sell(String[] inputs) {

    }

    /**The command must be validated, otherwise
     *  incorrect orders could be processed**/
    public boolean IsOrderValid(String[] order) {

        int commandArgs = order.length;
        if (commandArgs < 2) { return false; }

        if (!Utils.tryParseInt(order[1])) { return false; }

        return true;
    }


}
