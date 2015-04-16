package ru.timekiller;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.IntBuffer;

/**
 * Created by Дмитрий on 16.04.2015.
 */
public class TextureHelper {
    private int[] _texture;

    public TextureHelper() {
        _texture = new int[1];
        GLES20.glGenTextures(1, _texture, 0);
    }

    /**
     *
     */
    public void free() {
        if (isOk()) {
            GLES20.glDeleteTextures(1, _texture, 0);
            _texture[0] = 0;
        }
    }

    /**
     *
     */
    public void bind() {
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glActiveTexture(0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _texture[0]);
    }

    /**
     *
     */
    public void unbind() {
        GLES20.glActiveTexture(0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);

    }

    /**
     * Генерит текстуру из Bmp
     * @param bmp
     */
    public void genTextureFromBitmap(Bitmap bmp) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _texture[0]);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
        bmp.recycle();
    }

    /**
     *
     * @return
     */
    public boolean isOk() {
        return GLES20.glIsTexture(_texture[0]);
    }

    /**
     *
     * @return
     */
    public int getId() {
        return _texture[0];
    }

    /**
     *
     */
    public int getMaxTexture() {
        IntBuffer max = IntBuffer.allocate(1);
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max);
        return max.get(0);
    }
}
