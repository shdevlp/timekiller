package ru.timekiller;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Дмитрий on 14.04.2015.
 * Класс Врага
 */
public class Enemy {
    protected ShaderHelper _shader;
    protected MatrixHelper _matrix = new MatrixHelper();
    protected FloatBuffer _vertexBuffer;

    public Enemy() {
        _shader = new ShaderHelper();
        _shader.compile(GlobalVars.vertexShader, GlobalVars.fragmentShader);

        ByteBuffer bb = ByteBuffer.allocateDirect(GlobalVars.coordsRectSize);
        bb.order(ByteOrder.nativeOrder());

        _vertexBuffer = bb.asFloatBuffer();
        _vertexBuffer.put(GlobalVars.coordsRect);
        _vertexBuffer.position(0);
    }

    /**
     * Случайная позиция "врага"
     */
    public void setRandPosition() {
        final float x = GlobalVars.randFloat((int)GlobalVars.left + 2, (int)GlobalVars.right - 2);
        final float y = GlobalVars.randFloat((int)GlobalVars.bottom + 2, (int)GlobalVars.top - 2);
        _matrix.translate(x, y, 0);
    }

    /**
     * Перемещение по коорд осям
     * @param x - смещение по х
     * @param y - смещение по у
     */
    public void translate2D(float x, float y) {
        _matrix.translate(x, y, 0.0f);
    }

    public void scale2D(float x, float y) {
        _matrix.scale(x, y, 0.0f);
    }
    public void draw() {
        _shader.bind();

        final int program = _shader.getId();
        final int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        final int colorHandle = GLES20.glGetUniformLocation(program, "vColor");
        final int mvpHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, GlobalVars.coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);

        GLES20.glUniform4fv(colorHandle, 1, GlobalVars.colorEmeny, 0);
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, _matrix.getMvp(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, GlobalVars.vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);

        _shader.unbind();
    }
}
