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

        validateShader(_vertexShader);

        _fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(_fragmentShader, fragmentShader);
        GLES20.glCompileShader(_fragmentShader);

        validateShader(_fragmentShader);

        _program = GLES20.glCreateProgram();

        GLES20.glAttachShader(_program, _vertexShader);
        GLES20.glAttachShader(_program, _fragmentShader);

        GLES20.glLinkProgram(_program);

        validateProgram(GLES20.GL_VALIDATE_STATUS);
        validateProgram(GLES20.GL_LINK_STATUS);
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
     */
    private void validateProgram(int program) {
        _success = IntBuffer.allocate(1);
        GLES20.glValidateProgram(_program);
        GLES20.glGetProgramiv(_program, program, _success);
        if (_success.get(0) == GLES20.GL_FALSE) {
            _error = GLES20.glGetProgramInfoLog(_program);
            throw new RuntimeException("Error validate shaders:"+_error);
        }
    }

    /**
     * Проверка компиляции шейдера
     * @param shader
     */
    private void validateShader(int shader) {
        _success = IntBuffer.allocate(1);
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, _success);
        if (_success.get(0) == GLES20.GL_FALSE) {
            _error = GLES20.glGetShaderInfoLog(shader);
            throw new RuntimeException("Error compile shaders:"+_error);
        }
    }
}
