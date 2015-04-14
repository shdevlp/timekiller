package ru.timekiller;

import android.opengl.GLES20;

import java.nio.IntBuffer;

/**
 * Created by Дмитрий on 14.04.2015.
 */
public class ShaderHelper {
    private int shaderId;
    private int shaderVp;
    private int shaderFp;

    private String error;

    public ShaderHelper() {
    }

    /**
     * Создание, компиляция, линковка, проверка шейдеров
     * @param vertexShader
     * @param fragmentShader
     * @return Успех выполнения
     */
    public boolean compile(String vertexShader, String fragmentShader) {
        shaderVp = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        shaderFp = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        GLES20.glShaderSource(shaderVp, vertexShader);
        GLES20.glShaderSource(shaderFp, fragmentShader);

        GLES20.glCompileShader(shaderVp);
        if (!validateShader(shaderVp)) {
            return false;
        }

        GLES20.glCompileShader(shaderFp);
        if (!validateShader(shaderFp)) {
            return false;
        }

        return true;
    }

    public void attach() {
        shaderId = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderId, shaderFp);
        GLES20.glAttachShader(shaderId, shaderVp);
    }

    public boolean link() {
        GLES20.glLinkProgram(shaderId);

        if (!validateProgram(GLES20.GL_VALIDATE_STATUS)) {
            return false;
        }
        if (!validateProgram(GLES20.GL_LINK_STATUS)) {
            return false;
        }

        return true;
    }
    /**
     * Удаление шейдеров
     */
    public void free() {
        GLES20.glDetachShader(shaderId, shaderFp);
        GLES20.glDetachShader(shaderId, shaderVp);

        GLES20.glDeleteShader(shaderFp);
        GLES20.glDeleteShader(shaderVp);
        GLES20.glDeleteProgram(shaderId);
    }

    /**
     * Привязка
     */
    public void bind() {
        GLES20.glUseProgram(shaderId);
    }

    public void unbind() {
        GLES20.glUseProgram(0);
    }

    public boolean isOk() {
        return GLES20.glIsShader(shaderId);
    }

    public String getLastError() {
        return error;
    }

    public int getId() {
        return shaderId;
    }

    /**
     * Проверка линковки, статуса шейдера
     * @param pname
     * @return
     */
    private boolean validateProgram(int pname) {
        IntBuffer success = IntBuffer.allocate(1);
        GLES20.glValidateProgram(shaderId);
        GLES20.glGetProgramiv(shaderId, pname, success);
        if (success.get(0) != 0) {
            error = GLES20.glGetProgramInfoLog(shaderId);
            return false;
        }
        return true;
    }

    /**
     * Проверка компиляции шейдера
     * @param shader
     * @return
     */
    private boolean validateShader(int shader) {
        IntBuffer success = IntBuffer.allocate(1);
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, success);
        if (success.get(0) != 0) {
            error = GLES20.glGetShaderInfoLog(shader);
            return false;
        }

        return true;
    }
}
