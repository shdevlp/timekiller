package ru.timekiller;

import android.opengl.Matrix;

/**
 * Created by Дмитрий on 14.04.2015.
 * Операции с матрицами
 */
public class MatrixHelper {
    private float[] _modelMatrix      = new float[16]; //Модельная матрица
    private float[] _viewMatrix       = new float[16]; //Видовая
    private float[] _projectionMatrix = new float[16]; //Проекция
    private float[] _mvp              = new float[16]; //Все вместе

    private float[] _saveMatrixs = new float[16*4]; //Сохраненная матрица

    /**
     * Установка проекционной, видовой, модельной матриц
     */
    public MatrixHelper() {
        Matrix.orthoM(_projectionMatrix, 0, GlobalVars.left, GlobalVars.right,
                GlobalVars.bottom, GlobalVars.top, GlobalVars.near, GlobalVars.far);
        restoreViewMatrix();
        Matrix.setIdentityM(_modelMatrix, 0);
    }

    /**
     * Восстановление видовой матрицы
     */
    public void restoreViewMatrix() {
        Matrix.setLookAtM(_viewMatrix, 0, GlobalVars.eyeX, GlobalVars.eyeY, GlobalVars.eyeZ,
                GlobalVars.lookX, GlobalVars.lookY, GlobalVars.lookZ,
                GlobalVars.upX, GlobalVars.upY, GlobalVars.upZ);
    }

    /**
     * Перемещение по осям
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(_viewMatrix, 0, x, y, z);
    }

    /**
     * Масштабирование по осям
     * @param x
     * @param y
     * @param z
     */
    public void scale(float x, float y, float z) {
        Matrix.scaleM(_viewMatrix, 0, x, y, z);
    }

    /**
     *
     * @param angle
     * @param x
     * @param y
     * @param z
     */
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(_viewMatrix, 0, angle, x, y, z);
    }

    /**
     * Сохранить матрицы
     */
    public void pushMatrix() {
        System.arraycopy(_modelMatrix,      0, _saveMatrixs,  0, 16);
        System.arraycopy(_viewMatrix,       0, _saveMatrixs, 16, 16);
        System.arraycopy(_projectionMatrix, 0, _saveMatrixs, 32, 16);
        System.arraycopy(_mvp,              0, _saveMatrixs, 48, 16);
    }

    /**
     * Восстановить матрицы
     */
    public void popMatrix() {
        System.arraycopy(_saveMatrixs, 0,  _modelMatrix,      0, 16);
        System.arraycopy(_saveMatrixs, 16, _viewMatrix,       0, 16);
        System.arraycopy(_saveMatrixs, 32, _projectionMatrix, 0, 16);
        System.arraycopy(_saveMatrixs, 48, _mvp,              0, 16);
    }

    public void loadIdentity() {
        restoreViewMatrix();
        Matrix.setIdentityM(_modelMatrix, 0);
    }

    public float[] getMvp() {
        multiMvp();
        return _mvp;
    }

    private void multiMvp() {
        Matrix.multiplyMM(_mvp, 0, _viewMatrix, 0, _modelMatrix, 0);
        Matrix.multiplyMM(_mvp, 0, _projectionMatrix, 0, _mvp, 0);
    }
}
