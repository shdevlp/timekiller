package ru.timekiller;

import java.util.Random;

/**
 * Created by Дмитрий on 14.04.2015.
 */
public class GlobalVars {
    //Размеры экрана
    public final static float left = -11.25f;
    public final static float right = 11.25f;
    public final static float bottom = -20.0f;
    public final static float top = 20.0f;
    public final static float near = 0.1f;
    public final static float far = 100.0f;

    //Направление взгляда
    public final static float eyeX = 0.0f;
    public final static float eyeY = 0.0f;
    public final static float eyeZ = 50.0f;
    public final static float lookX = 0.0f;
    public final static float lookY = 0.0f;
    public final static float lookZ = 0.0f;
    public final static float upX = 0.0f;
    public final static float upY = 1.0f;
    public final static float upZ = 0.0f;

    public final static int bytesPerFloat   = 4; //Байтов в float
    public final static int coordsPerVertex = 3; //Координат у точки (x,y,z)
    public final static int coordsPerTex    = 2; //Координат у текстуры (x,y)

    public final static int vertexCount = 4; //Сколько вершин у примитива

    public static int width = 0;
    public static int height = 0;

    public static float warriorX = 0;
    public static float warriorY = 0;

    //Координаты моделей
    public final static float[] texRect = {
            -1.0f, -1.0f, 0.0f,
            -1.0f,  1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f,  1.0f, 0.0f
    };

    //U, V координаты текстуры
    public final static float[] uvRect = {
        -1.0f,  1.0f,
        -1.0f, -1.0f,
         1.0f,  1.0f,
         1.0f, -1.0f
    };

    //Цвета "врага"
    public final static float[] colorEmeny = {
         0.0f, 0.0f, 1.0f, 1.0f
    };

    //Цвет "вОйна"
    public final static float[] colorWarrior = {
         1.0f, 0.0f, 0.0f, 1.0f
    };

    public final static int enemySize      = 10;
    public final static int coordsRectSize = texRect.length * bytesPerFloat;
    public final static int uvRectSize     = uvRect.length * bytesPerFloat;

    public final static String vsColorRect =
            "uniform mat4 mvpMatrix;"
          + ""
          + "attribute vec4 vPosition;"
          + ""
          + "void main() "
          +"{"
          + "   gl_Position = mvpMatrix * vPosition;"
          + "}";

    public final static String fsColorRect =
            "precision mediump float;"
          + ""
          + "uniform vec4 vColor;"
          + ""
          + "void main() "
          +"{"
          + "   gl_FragColor = vColor;"
          + "}";


    public final static String vsTexture =
            "uniform mat4 mvpMatrix;"
          + ""
          + "attribute vec4 vPosition;"
          + "attribute vec2 aTexCoord;"
          + ""
          + "varying vec2 vTexCoord;"
          + ""
          + "void main() "
          + "{"
          + "   vTexCoord = aTexCoord;"
          + "   gl_Position = mvpMatrix * vPosition;"
          + "}";

    public final static String fsTexture =
            "precision mediump float;"
          + ""
          + "varying vec2 vTexCoord;"
          + ""
          + "uniform sampler2D sTexture;"
          + ""
          + "void main() "
          + "{"
          + "   gl_FragColor = texture2D(sTexture, vTexCoord);"
          + "}";


    /**
     * Случайное вещественное число
     *
     * @param min - Нижнияя граница
     * @param max - Верхняя граница
     * @return
     */
    public static float randFloat(int min, int max) {
        return (float) randInt(min, max);
    }

    /**
     * Случайное целое число
     *
     * @param min - Нижнияя граница
     * @param max - Верхняя граница
     * @return
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Градусы в радианы
     *
     * @param a
     * @return Радианы
     */
    public static double gradInRag(double a) {
        return (a * Math.PI) / 180.0f;
    }
}
