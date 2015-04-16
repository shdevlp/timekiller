package ru.timekiller;

import android.opengl.GLES20;
import android.util.Log;

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

    protected float _x;
    protected float _y;
    private float _speed;
    private float _angle;

    public Enemy() {
        _shader = new ShaderHelper();
        _shader.compile(GlobalVars.vsColorRect, GlobalVars.fsColorRect);

        ByteBuffer bb = ByteBuffer.allocateDirect(GlobalVars.coordsRectSize);
        bb.order(ByteOrder.nativeOrder());

        _vertexBuffer = bb.asFloatBuffer();
        _vertexBuffer.put(GlobalVars.texRect);
        _vertexBuffer.position(0);
    }

    /**
     * Случайная позиция "врага"
     */
    public void setRandPosition() {
        _x = GlobalVars.randFloat((int)GlobalVars.left + 2, (int)GlobalVars.right - 2);
        _y = GlobalVars.randFloat((int)GlobalVars.bottom + 2, (int)GlobalVars.top - 2);
        _matrix.translate(_x, _y, 0); //Случайное метоположение

        _angle = GlobalVars.randFloat(0, 360);        //Случайный угол
        _speed = GlobalVars.randFloat(0, 100)/100.0f; //Случайная скорость
    }

    /**
     * Следующий ход - передвигаемся по заданному
     * направлению с заданной скоростью
     */
    public void generateNextStep() {
        final double dx = Math.cos(GlobalVars.gradInRag(_angle));
        final double dy = Math.sin(GlobalVars.gradInRag(_angle));

        float offset = _speed * 0.1f;

        float newX = _x + (float)(offset * dx);
        float newY = _y + (float)(offset * dy);

        float diffX = newX - _x;
        float diffY = newY - _y;

        _x += diffX;
        _y += diffY;

        if ( (_x < 0 && _x < GlobalVars.left)   ||
             (_x > 0 && _x > GlobalVars.right)  ||
             (_y > 0 && _y > GlobalVars.top)    ||
             (_y < 0 && _y < GlobalVars.bottom) ) {
            _angle -= 90.0f;
            return;
        }

        _matrix.translate(diffX, diffY, 0); //Случайное метоположение
    }

    /**
     * Перемещение по коорд осям
     * @param x - смещение по х
     * @param y - смещение по у
     */
    public void translate2D(float x, float y) {
        _matrix.translate(x, y, 0.0f);
    }

    /**
     * Сохраняем координатную систему
     */
    public void pushMatrix() {
        _matrix.pushMatrix();;
    }

    /**
     * Восстанавливаем координатную систему
     */
    public void popMatrix() {
        _matrix.popMatrix();
    }

    /**
     * Масштабирование по осям
     * @param x
     * @param y
     */
    public void scale2D(float x, float y) {
        _matrix.scale(x, y, 0.0f);
    }

    /**
     * Рисуем кадр
     */
    public void draw() {
        _shader.bind();

        final int program = _shader.getId();

        final int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, GlobalVars.coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);

        final int colorHandle = GLES20.glGetUniformLocation(program, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, GlobalVars.colorEmeny, 0);

        final int mvpHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, _matrix.getMvp(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, GlobalVars.vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);

        _shader.unbind();
    }
}
