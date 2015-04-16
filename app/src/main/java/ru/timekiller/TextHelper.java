package ru.timekiller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Дмитрий on 16.04.2015.
 */
public class TextHelper {
    private FloatBuffer _vertexBuffer;
    private FloatBuffer _uvBuffer;

    private ShaderHelper   _shader;
    private MatrixHelper   _matrix;
    private TextureHelper  _texture;

    private int _width;
    private int _height;
    private int _fontSize;

    public TextHelper() {
        _width    = 256;
        _height   = 64;
        _fontSize = 32;

        _matrix   = new MatrixHelper();
        _texture  = new TextureHelper();
        _shader   = new ShaderHelper();

        _matrix.scale(4.0f, 1.0f, 0.0f);
        _matrix.translate(-0.3f, GlobalVars.top, 0.0f);
    }

    /**
     *
     * @param text
     */
    private Bitmap genBitmapFromText(String text) {
        Bitmap bitmap = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.TRANSPARENT);

        Paint textPaint = new Paint();
        textPaint.setTextSize(_fontSize);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        canvas.drawText(text, 2, _fontSize, textPaint);

        return bitmap;
    }

    /**
     *
     */
    private void genShaders() {
        _shader.compile(GlobalVars.vsTexture, GlobalVars.fsTexture);

        {//XYZ
            ByteBuffer bb = ByteBuffer.allocateDirect(GlobalVars.coordsRectSize);
            bb.order(ByteOrder.nativeOrder());
            _vertexBuffer = bb.asFloatBuffer();
            _vertexBuffer.put(GlobalVars.texRect);
            _vertexBuffer.position(0);
        }
        {//ST
            ByteBuffer bb = ByteBuffer.allocateDirect(GlobalVars.uvRectSize);
            bb.order(ByteOrder.nativeOrder());
            _uvBuffer = bb.asFloatBuffer();
            _uvBuffer.put(GlobalVars.uvRect);
            _uvBuffer.position(0);
        }
    }

    /**
     *
     * @param text
     */
    public void draw(String text) {
        Bitmap bmp = genBitmapFromText(text);

        _texture.genTextureFromBitmap(bmp);

        genShaders();

        _shader.bind();

        final int program = _shader.getId();

        //Передаем матрицу mvp
        final int mvpHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");
        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, _matrix.getMvp(), 0);

        //Передаем координаты треугольников
        final int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, GlobalVars.coordsPerVertex,
                GLES20.GL_FLOAT, false, 0, _vertexBuffer);

        //Передаем координаты текстуры
        final int texCoordHandle = GLES20.glGetAttribLocation(program, "aTexCoord");
        GLES20.glEnableVertexAttribArray(texCoordHandle);
        GLES20.glVertexAttribPointer(texCoordHandle,  GlobalVars.coordsPerTex,
                GLES20.GL_FLOAT, false, 0, _uvBuffer);

        _texture.bind();
        //Передаем текстуру с текстом
        final int textureHandle = GLES20.glGetUniformLocation(program, "sTexture");
        GLES20.glUniform1i(textureHandle, 0);

        //Рисуем
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, GlobalVars.vertexCount);
        GLES20.glDisable(GLES20.GL_BLEND);

        _texture.unbind();

        GLES20.glDisableVertexAttribArray(texCoordHandle);
        GLES20.glDisableVertexAttribArray(positionHandle);

        _shader.unbind();
    }
}
