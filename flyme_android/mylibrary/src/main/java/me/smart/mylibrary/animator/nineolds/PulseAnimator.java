/**   
* @Title: fff.java 
* @Package com.remote.nineolds 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author amor smarting.me  
* @date 2015-10-23 ����10:45:36 
* @version V1.0   
*/
package me.smart.mylibrary.animator.nineolds;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ClassName: fff
 * @Description: TODO(点击View放大效果)
 * @author amor smarting.me
 * @date 2015-10-23 下午10:45:36
 *
 *  AnimHelper.with(new SlideOutUpAnimator()).duration(1000).playOn(textView);
 */
public class PulseAnimator extends BaseViewAnimator
{
    @Override
    public void prepare(View target) {
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "scaleY", 1, 1.2f, 1),
                ObjectAnimator.ofFloat(target, "scaleX", 1, 1.2f, 1)
        );
    }
}