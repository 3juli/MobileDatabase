package newMobileContacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidMobile(String mobile) {
        String regex = "^\\+\\d{3}\\d{9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

}
