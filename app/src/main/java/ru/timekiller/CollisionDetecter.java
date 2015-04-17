package ru.timekiller;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by Дмитрий on 17.04.2015.
 */
public class CollisionDetecter  extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        while (!GlobalVars.isStop) {
            try {
                TimeUnit.SECONDS.sleep(10);
                GlobalVars.speed += 1.0f;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
