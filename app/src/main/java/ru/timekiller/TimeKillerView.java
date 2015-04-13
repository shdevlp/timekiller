package ru.timekiller;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Дмитрий on 13.04.2015.
 */

class TimeKillerView extends GLSurfaceView {
    private final TimeKillerRender render;
    private float prevX;
    private float prevY;


    public TimeKillerView(Context context){
        super(context);

        setEGLContextClientVersion(2);

        render = new TimeKillerRender();

        setRenderer(render);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - prevX;
                float dy = y - prevY;

                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }
        }

        prevX = x;
        prevY = y;

        return true;
    }
}
