package util;

import java.io.InputStream;
import java.util.Scanner;

public class Utility {

    public String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
