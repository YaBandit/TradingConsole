import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dylan on 6/24/2015.
 */
public class Baseplate {

    private static final CommandProcessor commandProcessor = new CommandProcessor();

    /** Start of the application**/
    public static void main(String[] args) {

        Utils.print("\n-------------------");
        Utils.print("COMMAND LINE TRADER");
        Utils.print("-------------------\n");

        boolean shouldExit = false;

        final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (!shouldExit) {
            try {
                System.out.print(CommandProcessor.username + ":  ");
                final String input = bufferedReader.readLine();
                shouldExit = commandProcessor.ProcessClientComment(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Utils.print("\n-------------------");
        Utils.print("EXITING");
        Utils.print("-------------------\n");

        System.exit(0);

    }

}
