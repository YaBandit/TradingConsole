/**
 * Created by dylan on 6/24/2015.
 */
public class Trade implements TradeInterface {


    public void Buy(String[] inputs) {
        if (IsOrderValid(inputs)) {
            final int quantity = Integer.parseInt(inputs[1]);
        } else {
            Utils.print("Not a valid buy order");
        }
    }

    public void Sell(String[] inputs) {
        if (!IsOrderValid(inputs)) {
            final int quantity = Integer.parseInt(inputs[1]);
        } else {
            Utils.print("Not a valid sell order");
        }
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
