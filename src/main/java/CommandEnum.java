/**
 * Created by dylan on 6/24/2015.
 */
public enum CommandEnum {

    HELP("help"),
    BUY("buy"),
    SELL("sell"),
    EXIT("exit");

    private final String command;

    CommandEnum(String command) { this.command = command; }

    public String getCommand() { return command; }

}