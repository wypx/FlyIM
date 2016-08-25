/**   
* @Title: ssss.java 
* @Package com.remote.nineolds 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author amor smarting.me  
* @date 2015-10-23 ����10:42:08 
* @version V1.0   
*/
package me.smart.mylibrary.animator.nineolds;

import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * @ClassName: BaseViewAnimator
 * @Description: TODO(基本动画效果类BaseViewAnimator)
 *              这个BaseViewAnimator动画类使用一个动画集合AnimatorSet，
 *              包装成单个动画类似的用法，并定义了一个abstract方法prepare()：
 *              复杂动画效果基类BaseViewAnimator使用一个AnimatorSet集合来添加各种动画 ,
 *              并绑定到目标targetView ,使用 prepare(View target) 的abstract方法供其子
 *              类实现具体的动画效果。
 * @author amor smarting.me
 * @date 2015-10-23 下午10:42:58
 *
 */
public abstract class BaseViewAnimator {

    public static final long DURATION = 1000;

    private AnimatorSet mAnimatorSet;
    private long mDuration = DURATION;

    {
        mAnimatorSet = new AnimatorSet();
    }


    protected abstract void prepare(View target);

    public BaseViewAnimator setTarget(View target) {
        reset(target);
        prepare(target);
        return this;
    }

    public void animate() {
        start();
    }

    /**
     * reset the view to default status
     *
     * @param target
     */
    public void reset(View target) {
        ViewHelper.setAlpha(target, 1);
        ViewHelper.setScaleX(target, 1);
        ViewHelper.setScaleY(target, 1);
        ViewHelper.setTranslationX(target, 0);
        ViewHelper.setTranslationY(target, 0);
        ViewHelper.setRotation(target, 0);
        ViewHelper.setRotationY(target, 0);
        ViewHelper.setRotationX(target, 0);
        ViewHelper.setPivotX(target, target.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(target, target.getMeasuredHeight() / 2.0f);
    }

    /**
     * start to animate
     */
    public void start() {
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.start();
    }

    public BaseViewAnimator setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    public BaseViewAnimator setStartDelay(long delay) {
        getAnimatorAgent().setStartDelay(delay);
        return this;
    }

    public long getStartDelay() {
        return mAnimatorSet.getStartDelay();
    }

    public BaseViewAnimator addAnimatorListener(AnimatorListener l) {
        mAnimatorSet.addListener(l);
        return this;
    }

    public void cancel(){
        mAnimatorSet.cancel();
    }

    public boolean isRunning(){
        return mAnimatorSet.isRunning();
    }

    public boolean isStarted(){
        return mAnimatorSet.isStarted();
    }

    public void removeAnimatorListener(AnimatorListener l) {
        mAnimatorSet.removeListener(l);
    }

    public void removeAllListener() {
        mAnimatorSet.removeAllListeners();
    }

    public BaseViewAnimator setInterpolator(Interpolator interpolator) {
        mAnimatorSet.setInterpolator(interpolator);
        return this;
    }

    public long getDuration() {
        return mDuration;
    }

    public AnimatorSet getAnimatorAgent() {
        return mAnimatorSet;
    }

}