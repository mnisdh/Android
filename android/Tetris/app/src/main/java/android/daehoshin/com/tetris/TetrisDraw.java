package android.daehoshin.com.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

/**
 * Created by daeho on 2017. 9. 29..
 */

public class TetrisDraw {
    public static void drawInnerBorder(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        drawInnerBorderLeft(canvas, size, minX, minY, maxX, maxY, fill, border);
        drawInnerBorderBottom(canvas, size, minX, minY, maxX, maxY, fill, border);
        drawInnerBorderRight(canvas, size, minX, minY, maxX, maxY, fill, border);
        drawInnerBorderTop(canvas, size, minX, minY, maxX, maxY, fill, border);
    }
    private static void drawInnerBorderLeft(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(minX, minY);
        path.lineTo(minX + size, minY + size);
        path.lineTo(minX + size, maxY - size);
        path.lineTo(minX, maxY);
        path.close();

        fill.setShader(new LinearGradient(minX - size, minY - size, minX + size, maxY, Color.WHITE, fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
    private static void drawInnerBorderBottom(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(minX, maxY);
        path.lineTo(minX + size, maxY - size);
        path.lineTo(maxX - size, maxY - size);
        path.lineTo(maxX, maxY);
        path.close();

        fill.setShader(new LinearGradient(minX - size, maxY - size * 2, maxX, maxY, Color.GRAY, fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
    private static void drawInnerBorderRight(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(maxX, minY);
        path.lineTo(maxX - size, minY + size);
        path.lineTo(maxX - size, maxY - size);
        path.lineTo(maxX, maxY);
        path.close();

        fill.setShader(new LinearGradient(maxX - size * 2, minY - size, maxX, maxY, Color.GRAY, fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
    private static void drawInnerBorderTop(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(minX, minY);
        path.lineTo(minX + size, minY + size);
        path.lineTo(maxX - size, minY + size);
        path.lineTo(maxX, minY);
        path.close();

        fill.setShader(new LinearGradient(minX - size, minY - size, maxX, minY + size, Color.WHITE, fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }

    public static void drawOuterBorder(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        drawOuterBorderLeft(canvas, size, minX, minY, maxX, maxY, fill, border);
        drawOuterBorderBottom(canvas, size, minX, minY, maxX, maxY, fill, border);
        drawOuterBorderRight(canvas, size, minX, minY, maxX, maxY, fill, border);
        drawOuterBorderTop(canvas, size, minX, minY, maxX, maxY, fill, border);
    }
    private static void drawOuterBorderLeft(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(minX, minY);
        path.lineTo(minX - size, minY - size);
        path.lineTo(minX - size, maxY + size);
        path.lineTo(minX, maxY);
        path.close();

        //fill.setShader(new LinearGradient(minX + size, minY + size, minX - size, maxY, Color.WHITE, fill.getColor(), Shader.TileMode.CLAMP));
        fill.setShader(new LinearGradient(minX + size, minY + size, minX - size, maxY, fill.getColor(), fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
    private static void drawOuterBorderBottom(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(minX, maxY);
        path.lineTo(minX - size, maxY + size);
        path.lineTo(maxX + size, maxY + size);
        path.lineTo(maxX, maxY);
        path.close();

        //fill.setShader(new LinearGradient(minX + size, maxY + size * 2, maxX, maxY, Color.GRAY, fill.getColor(), Shader.TileMode.CLAMP));
        fill.setShader(new LinearGradient(minX + size, maxY + size * 2, maxX, maxY, fill.getColor(), fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
    private static void drawOuterBorderRight(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(maxX, minY);
        path.lineTo(maxX + size, minY - size);
        path.lineTo(maxX + size, maxY + size);
        path.lineTo(maxX, maxY);
        path.close();

        //fill.setShader(new LinearGradient(maxX + size * 2, minY + size, maxX, maxY, Color.GRAY, fill.getColor(), Shader.TileMode.CLAMP));
        fill.setShader(new LinearGradient(maxX + size * 2, minY + size, maxX, maxY, fill.getColor(), fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
    private static void drawOuterBorderTop(Canvas canvas, int size, float minX, float minY, float maxX, float maxY, Paint fill, Paint border){
        Path path = new Path();
        path.moveTo(minX, minY);
        path.lineTo(minX - size, minY - size);
        path.lineTo(maxX + size, minY - size);
        path.lineTo(maxX, minY);
        path.close();

        //fill.setShader(new LinearGradient(minX + size, minY + size, maxX, minY - size, Color.WHITE, fill.getColor(), Shader.TileMode.CLAMP));
        fill.setShader(new LinearGradient(minX + size, minY + size, maxX, minY - size, fill.getColor(), fill.getColor(), Shader.TileMode.CLAMP));
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
    }
}
