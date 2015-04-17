package ru.timekiller;

import java.util.Calendar;

/**
 * Created by Дмитрий on 16.04.2015.
 */
public class SecondsHelper {
    private long _counter;
    private String _lastSecond;
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
     *
     * @return
     */
    public String lastSecond() {
        return _lastSecond;
    }

    /**
     * Получить строку секундомера
     * @return
     */
    public String getSeconds() {
        final long secs = System.currentTimeMillis() - _counter;

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(secs);

        _lastSecond = cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND)
                + ":" + cl.get(Calendar.MILLISECOND);

        return  _lastSecond;
    }
}
