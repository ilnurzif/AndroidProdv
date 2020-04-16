package com.naura.cityApp.ui.citydetail;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naura.myapplication.R;

public class HumidutyView extends View {
    private int dangerColor = Color.RED;
    private int normColor = Color.GREEN;
    private int lowColor = Color.YELLOW;
    private int currentValColor = Color.WHITE;
    private int textColor = Color.WHITE;

    private RectF dangerRectF = new RectF();
    private RectF normRectF = new RectF();
    private RectF lowRectF = new RectF();
    private RectF currentValF = new RectF();

    private int width = 0;
    private int height = 0;
    private final static int round = 0;

    private Paint dangerPaint;
    private Paint normPaint;
    private Paint lowPaint;
    private Paint currentValPaint;
    private Paint titleTextPaint;

    private int currentHumiduty = 0;

    private static final int padding = 10;

    public HumidutyView(Context context) {
        super(context);
        init();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    public void setCurrentHumiduty(int currentHumiduty) {
        this.currentHumiduty = currentHumiduty;
        invalidate();
    }

    public HumidutyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            HumidutyDescBottomDialog humidutyDescBottomDialog = new HumidutyDescBottomDialog();
            humidutyDescBottomDialog.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "dialog_fragment");
        }
        return true;
    }


    public HumidutyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.HumidutyView);
        dangerColor = typedArray.getColor(R.styleable.HumidutyView_dangerLevelColor, Color.RED);
        normColor = typedArray.getColor(R.styleable.HumidutyView_normLevelColor, Color.GREEN);
        lowColor = typedArray.getColor(R.styleable.HumidutyView_lowLevelColor, Color.RED);
        currentValColor = typedArray.getColor(R.styleable.HumidutyView_currentLevelColor, Color.WHITE);
        textColor = typedArray.getColor(R.styleable.HumidutyView_titleTextColor, Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w - getPaddingLeft() - getPaddingRight();
        height = h - getPaddingTop() - getPaddingBottom();

        float left = width / 2;
        float right = width / 2 + width / 8;

        dangerRectF.set(left, padding, right, ((0.33f) * height));
        normRectF.set(left, ((0.33f) * height), right, ((0.66f) * height));
        lowRectF.set(left, ((0.66f) * height), right, height - padding - 50);

        float cVal = getCalcVal(currentHumiduty, height);
        currentValF.set(left, cVal, right, cVal - 5);
    }

    private float getCalcVal(float val, int height) {
        return (height - val * height / 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(dangerRectF, round, round, dangerPaint);
        canvas.drawRoundRect(normRectF, round, round, normPaint);
        canvas.drawRoundRect(lowRectF, round, round, lowPaint);

        if (currentHumiduty > 0) {
            // Рисуем треугольник
            drawTriangle(canvas);

            canvas.drawText(currentHumiduty + "%", width / 2, height - 20, titleTextPaint);
            float cVal = getCalcVal(currentHumiduty, height);
            currentValF.setEmpty();
            currentValF.set(padding, cVal, width / 2, cVal - 5);
            canvas.drawRoundRect(currentValF, round, round, currentValPaint);
        }
        canvas.rotate(90);
        canvas.drawText(getContext().getResources().getString(R.string.airhumidity), width - 60, -width + 50, titleTextPaint);
        canvas.rotate(0);
    }

    private void drawTriangle(Canvas canvas) {
        float cVal = getCalcVal(currentHumiduty, height);
        float right = width / 2 + width / 8;
        Path path = new Path();
        path.moveTo(right - 65, cVal - 20);
        path.lineTo(right - 20, cVal);
        path.lineTo(right - 65, cVal + 20);
        path.close();
        canvas.drawPath(path, currentValPaint);
    }

    private void init() {
        dangerPaint = getPaint(dangerColor);
        normPaint = getPaint(normColor);
        lowPaint = getPaint(lowColor);
        currentValPaint = getPaint(currentValColor);

        titleTextPaint = getPaint(textColor);
        titleTextPaint.setTextSize(40f);
        titleTextPaint.setAntiAlias(true);
        titleTextPaint.setStrokeWidth(2.0f);
    }

    private Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

}
