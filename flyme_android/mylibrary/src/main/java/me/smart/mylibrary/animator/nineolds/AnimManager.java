/**   
* @Title: rrr.java 
* @Package com.remote.nineolds 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author amor smarting.me  
* @date 2015-10-23 ����10:47:51 
* @version V1.0   
*/
package me.smart.mylibrary.animator.nineolds;

import android.view.View;

/**
 * @ClassName: rrr
 * @Description: TODO(动画管理类)
 * @author amor smarting.me
 * @date 2015-10-23 下午10:47:51
 *
 */
//这段代码使用了类似Dialog的builder模式，感兴趣的可以搜一下 JAVA设计模式-Builder.
public final class AnimManager{

    private BaseViewAnimator animator;
    private View target;

    private AnimManager(BaseViewAnimator animator, View target){
        this.target = target;
        this.animator = animator;
    }

    public boolean isStarted(){
        return animator.isStarted();
    }

    public boolean isRunning(){
        return animator.isRunning();
    }

    public void stop(boolean reset){
        animator.cancel();

        if(reset)
            animator.reset(target);
    }
}