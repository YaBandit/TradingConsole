/**
 * Created by dylan on 6/24/2015.
 */
public class Utils {

    public static boolean tryParseInt(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException nfe) {
            return false;
        }
    }

    public static void print(String string) {
        System.out.println(string);
    }

}
