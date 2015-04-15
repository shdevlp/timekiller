package ru.timekiller;

/**
 * Created by Дмитрий on 14.04.2015.
 */
public class GlobalVars {
    public final static int bytesPerFloat = 4; //Байтов в float
    public final static int coordsPerVertex = 3; //Координат у точки (x,y,z)

    public static int width = 0;
    public static int height = 0;

    public final static float[] coordsEnemy = {
           -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            0.0f,  1.0f, 0.0f
    };

    public final static float[] colorEmeny = {
            0.9f, 0.1f, 0.9f, 1.0f
    };

    public final static int enemySize = 1;
    public final static int coordsEnemySize = coordsEnemy.length * bytesPerFloat;

    public final static String vsEnemy =
            "uniform mat4 mvpMatrix;"
          + "attribute vec4 vPosition;"
          + "void main() {"
          + "   gl_Position = mvpMatrix * vPosition;"
          + "}";

    public final static String fsEnemy =
            "precision mediump float;"
          + "uniform vec4 vColor;"
          + "void main() {"
          + "   gl_FragColor = vColor;"
          + "}";
}
