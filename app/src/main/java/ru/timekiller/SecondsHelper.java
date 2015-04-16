package ru.timekiller;

import java.util.Calendar;

/**
 * Created by Дмитрий on 16.04.2015.
 */
public class SecondsHelper {
    private long _counter;

    public SecondsHelper() {
        reset();
    }

    /**
     * Стартуем заново
     */
    public void reset() {
        _counter = System.currentTimeMillis();
    }

    /**
     * Получить строку секундомера
     * @return
     */
    public String getSeconds() {
        final long secs = System.currentTimeMillis() - _counter;

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(secs);

        return  cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND)
                + ":" + cl.get(Calendar.MILLISECOND);
    }
}
