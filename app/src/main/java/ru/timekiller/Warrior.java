package ru.timekiller;

import android.opengl.GLES20;

/**
 * Created by Дмитрий on 14.04.2015.
 * Класс вОйна
 */
public class Warrior extends Enemy {

    public void draw() {
        _shader.bind();

        final int program = _shader.getId();
        final int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        final int colorHandle = GLES20.glGetUniformLocation(program, "vColor");
        final int mvpHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, GlobalVars.coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);

        GLES20.glUniform4fv(colorHandle, 1, GlobalVars.colorWarrior, 0);
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, _matrix.getMvp(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, GlobalVars.vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);

        _shader.unbind();
    }
}

