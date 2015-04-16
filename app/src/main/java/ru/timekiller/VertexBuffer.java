package ru.timekiller;

import android.opengl.GLES20;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * Created by Дмитрий on 14.04.2015.
 */
public class VertexBuffer {
    private int[] vboId = new int[2];
    private int target;

    /**
     *
     */
    public void create() {
        GLES20.glGenBuffers(1, vboId, 0);
    }

    /**
     *
     */
    public void free() {
        GLES20.glDeleteBuffers(1, vboId, 0);
    }

    /**
     *
     * @return
     */
    public boolean isOk() {
        return GLES20.glIsBuffer(vboId[0]);
    }

    /**
     *
     * @param target = GL_ARRAY_BUFFER
     */
    public void bind(int target) {
        target = target;
        GLES20.glBindBuffer(target, vboId[0]);
    }

    public void unbind() {
        GLES20.glBindBuffer(target, 0);
    }

    public int getId() {
        return vboId[0];
    }

    /**
     *
     * @param size
     * @param usage = GL_STATIC_DRAW
     */
    public void setData(Buffer data, int size, int usage) {
        GLES20.glBufferData(target, size, data, usage);
    }

    public void clear() {
        GLES20.glBufferData(target, 0, null, 0);
    }

    public void setSubData(Buffer data, int size, int offset) {
        GLES20.glBufferSubData(target, offset, size, data);
    }
}
