package lib.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static long convertMinutesToMillis(int minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }

}
