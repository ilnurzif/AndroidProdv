package com.naura.cityApp.utility;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.squareup.picasso.Transformation;

public class CircleTransformation implements Transformation {
    private int circleRadius=500;

    @Override
    public Bitmap transform(Bitmap source) {
        Path path = new Path();
        path.addCircle(circleRadius/2, circleRadius/2, circleRadius/2, Path.Direction.CCW);
        Bitmap answerBitMap = Bitmap.createBitmap(circleRadius, circleRadius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(answerBitMap);
        canvas.clipPath(path);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();
        return answerBitMap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
