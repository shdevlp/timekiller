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
    private ShaderHelper _shader;
    private MatrixHelper _matrix = new MatrixHelper();
    private FloatBuffer _vertexBuffer;

    public Enemy() {
        _shader = new ShaderHelper();
        _shader.compile(GlobalVars.vsEnemy, GlobalVars.fsEnemy);

        ByteBuffer bb = ByteBuffer.allocateDirect(GlobalVars.coordsEnemySize);
        bb.order(ByteOrder.nativeOrder());

        _vertexBuffer = bb.asFloatBuffer();
        _vertexBuffer.put(GlobalVars.coordsEnemy);
        _vertexBuffer.position(0);
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

        _matrix.multiMvp();
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, _matrix.getMvp(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, GlobalVars.coordsPerVertex);
        GLES20.glDisableVertexAttribArray(positionHandle);

        _shader.unbind();
    }
}
