package ru.timekiller;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Дмитрий on 13.04.2015.
 */

class TimeKillerView extends GLSurfaceView {
    private final TimeKillerRender _render;
    private float _prevX;
    private float _prevY;


    public TimeKillerView(Context context){
        super(context);
        setEGLContextClientVersion(2);

        _render = new TimeKillerRender(this);
        setRenderer(_render);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - _prevX;
                float dy = y - _prevY;

                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }
        }

        _prevX = x;
        _prevY = y;

        return true;
    }
}
