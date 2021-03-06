package ru.timekiller;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Дмитрий on 13.04.2015.
 */

class TimeKillerView extends GLSurfaceView {
    private final TimeKillerRender _render;
    private GestureDetector _gestureDetector;

    /**
     *
     * @param context
     */
    public TimeKillerView(Context context){
        super(context);
        setEGLContextClientVersion(2);

        _gestureDetector = new GestureDetector(context, new GestureListener());

        _render = new TimeKillerRender(this);
        setRenderer(_render);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                GlobalVars.warriorX = e.getX();
                GlobalVars.warriorY = e.getY();
                return true;
            }
        }

        return _gestureDetector.onTouchEvent(e);
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            _render.reInit();
            return true;
        }
    }
}
