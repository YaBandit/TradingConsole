/**
 * Created by dylan on 6/24/2015.
 */
public class Utils {

    public final static int CLIENT_LOGIN_INT = 1;
    public final static int ORDER_INT = 1;

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
