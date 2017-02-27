package com.kvin.toolkit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kvin.toolkit.R;
import com.kvin.toolkit.utils.MetricsUtils;


/**
 * Created by Kvin on 2016/1/5.
 */
public class ItemLinearLayout extends LinearLayout {
    //set Paint
    private Paint paint;
    //line type
    public static final int TYPE_FILL = 0;
    public static final int TYPE_NOT = 1;
    public static final int TYPE_DOUBLE = 2;
    private int lineType;
    private int leftPic;
    private int rightPic;
    private String txt;

    //左右边距及leftText与content文本间距
    private int marginLeft;
    private int marginRight;
    private int marginGap;

    private float txtSize;
    private int txtColor;
    private int lineColor;
    private ImageView imgLeft;
    private ImageView imgRight;
    private TextView content;

    public ItemLinearLayout(Context context) {
        super(context);
    }

    public ItemLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setWillNotDraw(false);//call onDraw() Method
        //loading XML attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemLinearLayout);
        leftPic = typedArray.getResourceId(R.styleable.ItemLinearLayout_leftImg, -2);
        rightPic = typedArray.getResourceId(R.styleable.ItemLinearLayout_rightImg, -2);
        txtColor=typedArray.getColor(R.styleable.ItemLinearLayout_txtColor, Color.BLACK);
        lineColor=typedArray.getColor(R.styleable.ItemLinearLayout_lineColor, Color.GRAY);
        txt = typedArray.getString(R.styleable.ItemLinearLayout_txt);
        marginLeft = typedArray.getDimensionPixelSize(R.styleable.ItemLinearLayout_marginLeft, 5);
        marginRight = typedArray.getDimensionPixelSize(R.styleable.ItemLinearLayout_marginRight, 5);
        marginGap = typedArray.getDimensionPixelSize(R.styleable.ItemLinearLayout_marginGap, 5);
        lineType = typedArray.getInt(R.styleable.ItemLinearLayout_lineType, -2);
        txtSize = typedArray.getDimension(R.styleable.ItemLinearLayout_txtSize, 5);
        typedArray.recycle();

        //add widgets to ViewGroup
        this.setOrientation(HORIZONTAL);
        imgLeft = new ImageView(context);
        imgLeft.setImageResource(leftPic);
        LayoutParams p1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p1.setMargins(marginLeft, 0, 0, 0);
        imgLeft.setLayoutParams(p1);
        this.addView(imgLeft);

        content = new TextView(context);
        content.setTextColor(txtColor);
        content.setText(txt);
        content.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);//attention to the unit of txtSize
        LayoutParams p2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p2.setMargins(marginGap, 0, 0, 0);
        content.setLayoutParams(p2);
        //content.setCompoundDrawables(null, null, getResources().getDrawable(R.mipmap.mine_next_icon), null);
        this.addView(content);

        DisplayMetrics metrics = MetricsUtils.winMetrics(context);//get Screen Metrics
        imgRight = new ImageView(context);
        imgRight.setImageResource(rightPic);
        LayoutParams p3 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(imgRight);
        int m = metrics.widthPixels - marginRight - marginGap - marginLeft - MetricsUtils.conMetrics(imgLeft).getMeasuredWidth() - MetricsUtils.conMetrics(content).getMeasuredWidth() - MetricsUtils.conMetrics(imgRight).getMeasuredWidth();
        p3.setMargins(m, 0, 0, 0);
        //p3.setMarginEnd(marginRight);
        imgRight.setLayoutParams(p3);

        //init paint
        paint = new Paint();//use to draw line
        paint.setColor(lineColor);
        paint.setStrokeWidth(3);
    }

    public ItemLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas, lineType);
    }

    //draw line
    private void drawLine(Canvas canvas, int lineType) {
        int startX = -1;
        if (lineType == TYPE_FILL) {
            startX = 1;
        } else if (lineType == TYPE_NOT) {
            startX = marginLeft + marginGap + MetricsUtils.conMetrics(imgLeft).getMeasuredWidth();
        } else if (lineType == TYPE_DOUBLE) {
            startX = 1;
            canvas.drawLine(startX, 1, getWidth() - 1, 1, paint);
        }
        canvas.drawLine(startX, getHeight() - 1, getWidth() - 1, getHeight() - 1, paint);
    }

}
