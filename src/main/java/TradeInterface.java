/**
 * Created by dylan on 6/24/2015.
 */
public interface TradeInterface {

    void Buy(String[] inputs);

    void Sell(String[] inputs);

    void processOrder(CommandEnum side, String[] inputs);

    boolean IsOrderValid(String[] order);

}
