/**
 * Created by dylan on 6/24/2015.
 */
public class CommandProcessor {

    public boolean Process(String command) {

        String[] inputs = SplitCommand(command);
        if (inputs != null) {

            CommandEnum commandEnum = null;

            try {
                commandEnum = CommandEnum.valueOf("buy");
            } catch (Exception e) {
                System.out.println("Command not recognised");
            }

            if (commandEnum != null) {
                switch (commandEnum) {
                    case HELP:
                        break;
                    case BUY:
                        break;
                    case SELL:
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
        return command.split(" ");
    }

}
