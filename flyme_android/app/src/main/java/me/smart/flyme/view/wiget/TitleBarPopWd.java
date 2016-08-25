package me.smart.flyme.view.wiget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import me.smart.flyme.R;

/**
 * @ClassName: TitleBarPopWd
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author amor smarting.me
 * @date 2015-11-29 下午12:56:55
 *
 */
@SuppressLint("InflateParams")
public class TitleBarPopWd extends PopupWindow
{
    private View conentView;

    public TitleBarPopWd(final Activity context)
    {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.title_bar_popwin, null);
        @SuppressWarnings("deprecation")
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        @SuppressWarnings("deprecation")
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        //this.setBackgroundDrawable(R.drawable.bg_container_shadow);
//        LinearLayout addTaskLayout = (LinearLayout) conentView.findViewById(R.id.add_task_layout);
//        LinearLayout teamMemberLayout = (LinearLayout) conentView.findViewById(R.id.team_member_layout);
//        addTaskLayout.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View arg0)
//            {
//                dismiss();
//            }
//        });
//
//        teamMemberLayout.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                dismiss();
//            }
//        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent)
    {
        if (!this.isShowing())
        {
            // 以下拉方式显示popupwindow
            // this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
            this.showAsDropDown(parent);
        }
        else
        {
            this.dismiss();

        }
    }
}

