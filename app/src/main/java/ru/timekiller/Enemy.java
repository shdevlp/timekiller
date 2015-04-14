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
    private ShaderHelper shader = new ShaderHelper();
    private Mvp          mvp    = new Mvp();

    final String vertexShader =
            "uniform mat4 mvpMatrix;                  \n"
          + "attribute vec4 vPosition;                \n"
          + "void main()                              \n"
          + "{                                        \n"
          + "   gl_Position = mvpMatrix * vPosition;  \n"
          + "}                                        \n";


    final String fragmentShader =
            "void main()                              \n"
          + "{                                        \n"
          + "   gl_FragColor = vec3(0.0, 0.0, 1.0);   \n"
          + "}                                        \n";

    private int BUFFER_SIZE = 0;
    private FloatBuffer byteBuffer;

    public Enemy() {
        final float[] buffer = {
                -1.0f, -1.0f, 0.0f,
                 1.0f, -1.0f, 0.0f,
                 0.0f,  1.0f, 0.0f};

        BUFFER_SIZE = buffer.length * GlobalVars.bytesPerFloat;

        byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        byteBuffer.put(buffer)
                .position(0);

        boolean error = shader.compile(vertexShader, fragmentShader);
        if (!error) {
            shader.attach();
            GLES20.glBindAttribLocation(shader.getId(), 0, "vPosition");
            error = shader.link();
            if (!error) {
                Log.d("Shader error:", shader.getLastError());
                throw new RuntimeException("Error creating shaders.");
            }
        } else {
            throw new RuntimeException("Error compile shaders.");
        }
    }

    public void draw() {
        shader.bind();
        int positionHandle = GLES20.glGetAttribLocation(shader.getId(), "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, BUFFER_SIZE,
                GLES20.GL_FLOAT, false, 0, byteBuffer);

        int mvpHandle = GLES20.glGetUniformLocation(shader.getId(), "mvpMatrix");
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvp.getMvpBuffer());

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 1);
        GLES20.glDisableVertexAttribArray(positionHandle);

        shader.unbind();
    }
}
