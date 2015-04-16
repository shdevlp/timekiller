package ru.timekiller;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дмитрий on 13.04.2015.
 */
public class TimeKillerRender implements GLSurfaceView.Renderer {
    private Enemy[] _enemy;
    private Warrior _warrior;
    private GLSurfaceView _glView;
    private TextHelper _text;
    private SecondsHelper _secs;

    /**
     *
     * @param view
     */
    public TimeKillerRender(GLSurfaceView view) {
        super();
        _glView = view;
    }

    public void reInit() {
        for (int i = 0; i < GlobalVars.enemySize; i++) {
            _enemy[i].setRandPosition();
        }

        _warrior.setRandPosition();
        _warrior.scale2D(1.5f, 1.5f);

        _secs.reset();
    }

    /**
     *
     * @param unused
     * @param config
     */
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        GlobalVars.width  = _glView.getWidth();
        GlobalVars.height = _glView.getHeight();

        _enemy = new Enemy[GlobalVars.enemySize];
        for (int i = 0; i < GlobalVars.enemySize; i++) {
            _enemy[i] = new Enemy();
            _enemy[i].setRandPosition();
        }

        _warrior = new Warrior();
        _warrior.setRandPosition();
        _warrior.scale2D(1.5f, 1.5f);

        _text = new TextHelper();
        _secs = new SecondsHelper();
    }

    /**
     *
     * @param unused
     */
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < GlobalVars.enemySize; i++) {
                    _enemy[i].generateNextStep();
                    _enemy[i].draw();
                }

                _warrior.generateNextStep();
                _warrior.draw();

                _text.draw(_secs.getSeconds());
            }
        };

        r.run();
    }

    /**
     *
     * @param unused
     * @param width
     * @param height
     */
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
}
