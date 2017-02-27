package com.kvin.toolkit.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.kvin.toolkit.R;
import com.kvin.toolkit.utils.MetricsUtils;
import com.kvin.toolkit.utils.StringUtils;

/**
 * Created by Kvin on 2017/2/5.
 */
public class ClassifyTextView extends View {
    private final int DEFAULT_LINE_WIDTH = -1;
    private Paint linePaint;
    private Paint txtPaint;

    //attr
    private float txtSize;
    private int txtColor;
    private String txt;
    private int lineColor;
    private int lineWidth;
    private int verSpace;

    public ClassifyTextView(Context context) {
        super(context);
    }

    public ClassifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    /**
     * init attr
     *
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClassifyTextView);
        txt = typedArray.getString(R.styleable.ClassifyTextView_ctvTxt);
        if (StringUtils.isEmpty(txt))txt="  ";
        txtSize = typedArray.getDimension(R.styleable.ClassifyTextView_ctvTxtSize, 5);
        verSpace = typedArray.getDimensionPixelSize(R.styleable.ClassifyTextView_ctvVerSpace, 5);
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.ClassifyTextView_ctvLineWidth, DEFAULT_LINE_WIDTH);
        txtColor = typedArray.getColor(R.styleable.ClassifyTextView_ctvTxtColor, Color.BLACK);
        lineColor = typedArray.getColor(R.styleable.ClassifyTextView_ctvLineColor, Color.GRAY);
        typedArray.recycle();

        txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        txtPaint.setColor(txtColor);
        txtPaint.setTextSize(txtSize);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setTextSize(1.0f);

    }

    public ClassifyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassifyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw text
        float w = StringUtils.getTextWidth(txt, txtPaint);
        float h = StringUtils.getTextHeight(txtPaint);
        float lEnd = (canvas.getWidth() - w) / 2 - verSpace;
        float rStart = (canvas.getWidth() + w) / 2 + verSpace;

        canvas.drawText(txt, (canvas.getWidth() - w) / 2, h, txtPaint);
        //draw line
        if (lineWidth != DEFAULT_LINE_WIDTH) {
            canvas.drawLine(lEnd - lineWidth, h / 2, lEnd, h / 2, linePaint);
            canvas.drawLine(rStart, h / 2, rStart + lineWidth, h / 2, linePaint);
        } else {
            canvas.drawLine(0, h / 2, lEnd, h / 2, linePaint);
            canvas.drawLine(rStart, h / 2, canvas.getWidth(), h / 2, linePaint);
        }
    }

    /**
     * set text
     */
    public void setText(String msg) {
        txt = msg;
        postInvalidate();
    }
}
