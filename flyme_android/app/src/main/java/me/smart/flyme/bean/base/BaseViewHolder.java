package me.smart.flyme.bean.phone;

import android.util.SparseArray;
import android.view.View;
/**
 * @Description:通用ViewHolder
 * @author http://blog.csdn.net/finddreams
 */
public class BaseViewHolder
{
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id)
		{
				SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
				if (viewHolder == null)
				{
						viewHolder = new SparseArray<View>();
						view.setTag(viewHolder);
				}
				View childView = viewHolder.get(id);
				if (null == childView )
				{
						childView = view.findViewById(id);
						viewHolder.put(id, childView);
				}
				return (T) childView;
		}

}

