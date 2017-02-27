package com.kvin.toolkit.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kvin.toolkit.R;
import com.kvin.toolkit.utils.ListUtils;
import com.kvin.toolkit.utils.MetricsUtils;

import java.util.ArrayList;

/**
 * Created by Kvin on 2017/2/5.
 */
public class ChoiceLayout extends ViewGroup {

    private TextView tv;
    private final int TYPE_TITLE = 1;
    private int marginLeft = 20;
    private final int marginTop = 20;
    private final Rect childRect = new Rect();
    private DisplayMetrics mMetrics;
    private int lineNum;

    private OnChoiceClickListener mOnChoiceClickListener;


    //attr
    private int verSpace;
    private int horSpace;
    private float txtSize;
    private int choiceTheme;

    public ChoiceLayout(Context context) {
        super(context);
    }

    public ChoiceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    /**
     * init attr
     */
    private void initAttr(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChoiceLayout);
        verSpace = typedArray.getDimensionPixelSize(R.styleable.ChoiceLayout_clVerSpace, 10);
        horSpace = typedArray.getDimensionPixelSize(R.styleable.ChoiceLayout_clHorSpace, 5);
        txtSize = typedArray.getDimension(R.styleable.ChoiceLayout_clTxtSize, 5);
        choiceTheme = typedArray.getInt(R.styleable.ChoiceLayout_clChoiceTheme, Theme.RED.value);

        typedArray.recycle();

    }

    public ChoiceLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChoiceLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * add children and init
     */
    public void init(Context context, ArrayList<String> data) {
        if (context == null || ListUtils.isEmpty(data)) return;
        mMetrics = MetricsUtils.winMetrics(context);
        for (String item : data) {
            tv = new TextView(context);
            setTheme(choiceTheme, tv);
            tv.setText(item);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
            addView(tv);
        }

        postInvalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);//don`t forget
        setMeasuredDimension(widthMeasureSpec, childRect.bottom + 40);//set last child`s bottom as total height

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View child = null;
        boolean is = changed;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(mOnClickListener);
            if (i == 0) {
                calculateMarginLeft(i);
                childRect.left = marginLeft;
                childRect.top = marginTop;
                childRect.right = marginLeft + child.getMeasuredWidth();
                childRect.bottom = marginTop + child.getMeasuredHeight();

            } else if (childRect.right + horSpace + child.getMeasuredWidth() <= getWidth()) {
                //layout right
                childRect.left = childRect.right + horSpace;
                childRect.right = childRect.left + child.getMeasuredWidth();
                childRect.bottom = childRect.top + child.getMeasuredHeight();
            } else {
                //line feed
                calculateMarginLeft(i);
                childRect.left = marginLeft;
                childRect.top = childRect.bottom + verSpace;
                childRect.right = marginLeft + child.getMeasuredWidth();
                childRect.bottom = childRect.top + child.getMeasuredHeight();
                lineNum++;
            }
            chooseTheme(lineNum / 2);
            setTheme(choiceTheme, (TextView) child);
            //layout child
            child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }

    /**
     * choose a direct theme
     *
     * @param index
     */
    private void chooseTheme(int index) {
        choiceTheme = index;
    }

    /**
     * @return a random num (<=3)
     */
    private int setTheme() {
        int ran = (int) (Math.random() * 10 / 3);
        return ran;
    }


    /**
     * calculate marginLeft
     *
     * @param position
     */
    private void calculateMarginLeft(int position) {
        int i = 0;
        int count = getChildCount();
        int totalW = getTotalWidth(position, i);
        while (totalW <= getWidth()) {
            i++;
            if (i >= count - position) break;
            totalW = getTotalWidth(position, i + 1);
        }
        marginLeft = (getWidth() - getTotalWidth(position, i)) / 2;
    }

    /**
     * measure child total w
     *
     * @param position
     * @param n
     * @return
     */
    private int getTotalWidth(int position, int n) {
        int w = getChildAt(position).getMeasuredWidth();
        for (int i = 1; i < n; i++) {
            w += horSpace + getChildAt(position + i).getMeasuredWidth();
        }
        return w;
    }


    /**
     * title layout params
     */
    public LayoutParams getTitleLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        return params;
    }

    /**
     * choice layout params
     */
    public LayoutParams getChoiceLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return params;
    }

    /**
     * child view click
     */
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOnChoiceClickListener != null)
                mOnChoiceClickListener.onChoiceClick(view, (Integer) view.getTag());
        }
    };

    /**
     * set on item click listener
     */

    public void setOnChoiceClickListener(OnChoiceClickListener mOnChoiceClickListener) {
        this.mOnChoiceClickListener = mOnChoiceClickListener;
    }

    /**
     * set theme for textViews
     */
    public void setTheme(int theme, TextView tv) {
        switch (theme) {
            case 0:
                tv.setBackgroundResource(R.drawable.choice_red_bg);
                tv.setTextColor(getResources().getColor(R.color.choice_red_bg));
                break;
            case 1:
                tv.setBackgroundResource(R.drawable.choice_yellow_bg);
                tv.setTextColor(getResources().getColor(R.color.choice_yellow_bg));
                break;
            case 2:
                tv.setBackgroundResource(R.drawable.choice_green_bg);
                tv.setTextColor(getResources().getColor(R.color.choice_green_bg));
                break;
            case 3:
                tv.setBackgroundResource(R.drawable.choice_blue_bg);
                tv.setTextColor(getResources().getColor(R.color.choice_blue_bg));
                break;

        }

    }

    public void setTheme(Theme theme, TextView tv) {
        setTheme(theme.value, tv);
    }

    public enum Theme {
        RED(0), YELLOW(1), GREEN(2), BLUE(3);

        private int value;

        Theme(int value) {
            this.value = value;
        }
    }

    /**
     * choice click listener
     */
    public interface OnChoiceClickListener {
        void onChoiceClick(View view, int position);
    }
}
