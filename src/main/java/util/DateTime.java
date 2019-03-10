package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    public long currentTimeMs() {
        return System.currentTimeMillis();
    }

    public String currentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        return sdf.format(new Date());
    }
}
