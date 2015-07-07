/**
 * Created by dylan on 6/24/2015.
 */
public enum CommandEnum {

    HELP("help"),
    BUY("buy"),
    SELL("sell"),
    CONNECT("connect"),
    LOGIN("login"),
    REGISTER("register"),
    CASH("cash"),
    INVENTORY("inventory"),
    EXIT("exit");

    private final String command;

    CommandEnum(String command) { this.command = command; }

    public String getCommand() { return command; }

}
