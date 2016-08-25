/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.smart.mylibrary.animator.viewpager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class ABaseTransformer implements PageTransformer {

	
	protected abstract void onTransform(View page, float position);

	
	@Override
	public void transformPage(View page, float position) {
		onPreTransform(page, position);
		onTransform(page, position);
		onPostTransform(page, position);
	}

	protected boolean hideOffscreenPages() {
		return true;
	}


	protected boolean isPagingEnabled() {
		return false;
	}

	@SuppressLint("NewApi")
	protected void onPreTransform(View page, float position) 
	{
		final float width = page.getWidth();
        
//		ViewHelper.setRotationX(page, 0);
//		ViewHelper.setPivotY(page, 0);
//		ViewHelper.setRotation(page, 0);
//		ViewHelper.setScaleX(page, 1);
//		ViewHelper.setScaleY(page, 1);
//		ViewHelper.setPivotX(page, 0);
//		ViewHelper.setPivotY(page, 0);
//		ViewHelper.setTranslationY(page, 0);
//		ViewHelper.setTranslationX(page, isPagingEnabled() ? 0f : -width * position);
		page.setRotationX(0);
		page.setRotationY(0);
		page.setRotation(0);
		page.setScaleX(1);
		page.setScaleY(1);
		page.setPivotX(0);
		page.setPivotY(0);
		page.setTranslationY(0);
		page.setTranslationX(isPagingEnabled() ? 0f : -width * position);

		if (hideOffscreenPages()) {
			
		//	ViewHelper.setAlpha(page, position <= -1f || position >= 1f ? 0f : 1f);
			page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
			page.setEnabled(false);
		} else {
			page.setEnabled(true);
			page.setAlpha(1f);
		}
	}

	protected void onPostTransform(View page, float position) {
	}

	protected static final float min(float val, float min) {
		return val < min ? min : val;
	}

}
