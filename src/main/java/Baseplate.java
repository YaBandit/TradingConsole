import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dylan on 6/24/2015.
 */
public class Baseplate {

    private static final CommandProcessor commandProcessor = new CommandProcessor();
    private static final String ID = "ID = ";
    private static final String END = ":  ";

    /** Start of the application**/
    public static void main(String[] args) {

        System.out.println("\n-------------------");
        System.out.println("COMMAND LINE TRADER");
        System.out.println("-------------------\n");

        boolean shouldExit = false;

        final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (!shouldExit) {
            try {
                System.out.print(ID + CommandProcessor.username + END);
                final String input = bufferedReader.readLine();
                shouldExit = commandProcessor.ProcessClientComment(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n-------------------");
        System.out.println("EXITING");
        System.out.println("-------------------\n");

        System.exit(0);

    }

}
