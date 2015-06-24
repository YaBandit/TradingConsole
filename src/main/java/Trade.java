/**
 * Created by dylan on 6/24/2015.
 */
public class Trade implements TradeInterface {


    public void Buy(String[] inputs) {

        final int quantity = Integer.parseInt(inputs[1]);
    }

    public void Sell(String[] inputs) {
        final int quantity = Integer.parseInt(inputs[1]);
    }

    /**The command must be validated, otherwise
     *  incorrect orders could be processed**/
    public boolean OrderValidation(String[] order) {
        boolean isValid = true;

        


        return isValid;
    }
}
