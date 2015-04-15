package ru.timekiller;

import android.opengl.GLES20;

import java.nio.IntBuffer;

/**
 * Created by Дмитрий on 14.04.2015.
 */
public class ShaderHelper {
    private int _program;
    private int _vertexShader;
    private int _fragmentShader;

    private IntBuffer _success;
    private String _error;

    /**
     * Создание, компиляция, линковка, проверка шейдеров
     * @param vertexShader
     * @param fragmentShader
     */
    public void compile(String vertexShader, String fragmentShader) {
        _vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(_vertexShader, vertexShader);
        GLES20.glCompileShader(_vertexShader);

        if (!validateShader(_vertexShader)) {
            throw new RuntimeException("Error compile vertex shader.");
        }

        _fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(_fragmentShader, fragmentShader);
        GLES20.glCompileShader(_fragmentShader);

        if (!validateShader(_fragmentShader)) {
            throw new RuntimeException("Error compile fragment shader.");
        }

        _program = GLES20.glCreateProgram();

        GLES20.glAttachShader(_program, _vertexShader);
        GLES20.glAttachShader(_program, _fragmentShader);

        GLES20.glLinkProgram(_program);

        if (validateProgram(GLES20.GL_VALIDATE_STATUS) == false) {
            throw new RuntimeException("Error validate shaders.");
        }

        if (validateProgram(GLES20.GL_LINK_STATUS) == false) {
            throw new RuntimeException("Error link shaders.");
        }
    }

    /**
     * Удаление шейдеров
     */
    public void free() {
        GLES20.glDetachShader(_program, _vertexShader);
        GLES20.glDetachShader(_program, _fragmentShader);

        GLES20.glDeleteShader(_vertexShader);
        GLES20.glDeleteShader(_fragmentShader);
        GLES20.glDeleteProgram(_program);
    }

    /**
     * Привязка
     */
    public void bind() {
        GLES20.glUseProgram(_program);
    }

    public void unbind() {
        GLES20.glUseProgram(0);
    }

    public boolean isOk() {
        return GLES20.glIsShader(_program);
    }

    public String getLastError() {
        return _error;
    }

    public int getId() {
        return _program;
    }

    /**
     * Проверка линковки, статуса шейдера
     * @param program
     * @return
     */
    private boolean validateProgram(int program) {
        _success = IntBuffer.allocate(1);
        GLES20.glValidateProgram(_program);
        GLES20.glGetProgramiv(_program, program, _success);
        if (_success.get(0) == GLES20.GL_FALSE) {
            _error = GLES20.glGetProgramInfoLog(_program);
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
        _success = IntBuffer.allocate(1);
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, _success);
        if (_success.get(0) == GLES20.GL_FALSE) {
            _error = GLES20.glGetShaderInfoLog(shader);
            return false;
        }
        return true;
    }
}
