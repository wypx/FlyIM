package me.smart.flyme.view.wiget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import me.smart.flyme.myapp.MyApp;

/**
 *  自定义字体的TextView
 * 
 */
public class CustomTextView extends AppCompatTextView
{
    public CustomTextView(Context context)
    {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setTypeface(MyApp.canaroExtraBold);
    }

}
