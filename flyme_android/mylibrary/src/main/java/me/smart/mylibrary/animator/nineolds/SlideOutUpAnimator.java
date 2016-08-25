/**   
* @Title: ddd.java 
* @Package com.remote.nineolds 
* @Description: TODO(�ϻ���ʧ��Ʈ+1��)  ����Ч��
* @author amor smarting.me  
* @date 2015-10-23 ����10:44:21 
* @version V1.0   
*/
package me.smart.mylibrary.animator.nineolds;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

public class SlideOutUpAnimator extends BaseViewAnimator {
    @Override
    public void prepare(View target) {
        ViewGroup parent = (ViewGroup)target.getParent();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 1, 0),
                ObjectAnimator.ofFloat(target,"translationY",0,-parent.getHeight()/2)
        );
    }
}