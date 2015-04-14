package ru.timekiller;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Дмитрий on 13.04.2015.
 */
public class TimeKillerRender implements GLSurfaceView.Renderer {
    private int ENEMY_SIZE = 1;

    private Enemy[] enemy = new Enemy[ENEMY_SIZE];
    private Warrior warrior = new Warrior();

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        for (int i = 0; i < ENEMY_SIZE; i++) {
            enemy[i] = new Enemy();
        }
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);

        for (int i = 0; i < ENEMY_SIZE; i++) {
            enemy[i].draw();
        }

        warrior.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
       GLES20.glViewport(0, 0, width, height);
    }
}
