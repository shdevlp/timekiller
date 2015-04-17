package ru.timekiller;

import android.opengl.GLES20;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Дмитрий on 14.04.2015.
 * Класс вОйна
 */
public class Warrior extends Enemy {

    public void generateNextStep(Enemy[] arr, SecondsHelper secs) {
         if (GlobalVars.warriorX != 0 || GlobalVars.warriorY != 0) {
            //Рассчет % отношений
            final float percentValueX = (GlobalVars.warriorX * 100.0f) / GlobalVars.width;
            final float percentValueY = (GlobalVars.warriorY * 100.0f) / GlobalVars.height;

            GlobalVars.warriorX = 0;
            GlobalVars.warriorY = 0;

            _x = (((GlobalVars.right * 2.0f) * percentValueX) / 100.0f) - GlobalVars.right;
            _y = GlobalVars.top - ((GlobalVars.top * 2.0f) * percentValueY) / 100.0f;

            _matrix.loadIdentity();
            _matrix.translate(_x, _y, 0);
            _matrix.scale(1.5f, 1.5f, 0.0f);
        }

        for (int i = 0; i < GlobalVars.enemySize; i++) {
            Enemy en = arr[i];

            if (GlobalVars.intersectRect(_x, _y, en.currentX(), en.currentY())) {
                //Игра закончилась
                GlobalVars.speed = 0.0f;
                GlobalVars.isStop = true;

                break;
            }
        }
    }

    /**
     *
     */
    public void draw() {
        _shader.bind();

        final int program = _shader.getId();

        final int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, GlobalVars.coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);

        final int colorHandle = GLES20.glGetUniformLocation(program, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, GlobalVars.colorWarrior, 0);

        final int mvpHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, _matrix.getMvp(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, GlobalVars.vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);

        _shader.unbind();
    }
}

