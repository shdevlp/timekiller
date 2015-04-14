package ru.timekiller;

import android.opengl.Matrix;
import android.provider.Settings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Дмитрий on 14.04.2015.
 * Операции с матрицами
 */
public class Mvp {
    private float[] modelMatrix      = new float[16]; //Модельная матрица
    private float[] viewMatrix       = new float[16]; //Видовая
    private float[] projectionMatrix = new float[16]; //Проекция
    private float[] mvp              = new float[16]; //Все вместе

    public Mvp() {
        float aspect = (float)800 / (float)600;
        Matrix.perspectiveM(projectionMatrix, 0, 45.0f, aspect, 0.1f, 100.0f);
        Matrix.setLookAtM(viewMatrix, 0, 10.0f, 3.0f, 10.0f,
                                         0.0f, 0.0f, 0.0f,
                                         0.0f, 1.0f, 0.0f);
        Matrix.setIdentityM(modelMatrix, 0);
        multiMvp();
    }

    public float[] getModelMatrix() {
        return modelMatrix;
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    public float[] getMvp() {
        return mvp;
    }

    public FloatBuffer getMvpBuffer() {
        FloatBuffer fb = ByteBuffer.allocateDirect(mvp.length * GlobalVars.bytesPerFloat)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(mvp);
        fb.position(0);

        return fb;
    }

    public void multiMvp() {
        Matrix.multiplyMM(mvp, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvp, 0, projectionMatrix, 0, mvp, 0);
    }
}
