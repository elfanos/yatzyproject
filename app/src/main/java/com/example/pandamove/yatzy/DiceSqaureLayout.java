package com.example.pandamove.yatzy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Rasmus on 30/06/17.
 */


public class DiceSqaureLayout  extends RelativeLayout {

    public DiceSqaureLayout(Context context) {
        super(context);
    }


    public DiceSqaureLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DiceSqaureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


// here we are returning the width in place of height, so width = height
// you may modify further to create any proportion you like ie. height = 2*width etc

    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, size);
    }


}
