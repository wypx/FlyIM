package me.smart.flyme.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import me.smart.flyme.RadarScan.utils.LogUtil;


/**
 * author：wypx on 2016/8/2 01:06
 * blog：smarting.me
 * 滑动自动加载监听器
 */
public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallBack
{
		final  int SCROLL_STATE_IDLE     = 0;
		final  int SCROLL_STATE_DRAGGING = 1;
		final  int SCROLL_STATE_SETTLING = 2;

		Context cx;

		private onLoadMoreListener loadMoreListener;  //加载更多回调
		private boolean isLoadingMore;          //是否加载更多

		public AutoLoadRecyclerView(Context context) {
				this(context, null);
				this.cx = context;
		}

		public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
				this(context, attrs, 0);
				this.cx = context;
		}

		public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
				super(context, attrs, defStyle);
				this.cx = context;
				isLoadingMore = false;  //默认无需加载更多
				setOnScrollListener(new AutoLoadScrollListener(null, true, true));
		}

		/**
		 * 配置显示图片，需要设置这几个参数，快速滑动时，暂停图片加载
		 *
		 * @param imageLoader   ImageLoader实例对象
		 * @param pauseOnScroll
		 * @param pauseOnFling
		 */
		public void setOnPauseListenerParams(Glide imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {

				setOnScrollListener(new AutoLoadScrollListener(imageLoader, pauseOnScroll, pauseOnFling));

		}

		public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
				this.loadMoreListener = loadMoreListener;
		}

		@Override
		public void loadFinish(Object obj) {
				isLoadingMore = false;
		}


		//加载更多的回调接口
		public interface onLoadMoreListener {
				void loadMore();
		}


		/**
		 * 滑动自动加载监听器
		 */
		private class AutoLoadScrollListener extends OnScrollListener {

				private Glide imageLoader;
				private final boolean pauseOnScroll;
				private final boolean pauseOnFling;

				public AutoLoadScrollListener(Glide imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
						super();
						this.pauseOnScroll = pauseOnScroll;
						this.pauseOnFling = pauseOnFling;
						this.imageLoader = imageLoader;
				}

				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy)
				{
						super.onScrolled(recyclerView, dx, dy);

						//由于GridLayoutManager是LinearLayoutManager子类，所以也适用
						if (getLayoutManager() instanceof LinearLayoutManager) {
								int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
								int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();

								//有回调接口，且不是加载状态，且计算后剩下2个item，且处于向下滑动，则自动加载
								if (loadMoreListener != null && !isLoadingMore &&
												lastVisibleItem >= totalItemCount - 2 && dy > 0)
								{
										loadMoreListener.loadMore();
										isLoadingMore = true;
								}
						}
				}

			  //状态为0时：当前屏幕停止滚动；
				//状态为1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；
				//状态为2时：随用户的操作，屏幕上产生的惯性滑动；
				@Override
				public void onScrollStateChanged(RecyclerView recyclerView, int newState)
				{
						//当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，
						//调用resumeRequests()恢复请求。这样是不是会好些呢？
						//根据newState状态做处理
						if (imageLoader != null)
						{
								switch (newState)
								{
										case SCROLL_STATE_IDLE:
												Glide.with(cx).resumeRequests();
												LogUtil.e("SCROLL_STATE_IDLE ");
												break;
										case SCROLL_STATE_DRAGGING:
												LogUtil.e("SCROLL_STATE_DRAGGING ");
												if (pauseOnScroll)
												{
														Glide.with(cx).pauseRequests();
												}
												else
												{
														Glide.with(cx).resumeRequests();
												}
												break;

										case SCROLL_STATE_SETTLING:
												LogUtil.e("SCROLL_STATE_SETTLING ");
												if (pauseOnFling)
												{
														Glide.with(cx).pauseRequests();
												}
												else
												{
														Glide.with(cx).resumeRequests();
												}
												break;
										default:
										//		Glide.clear();
											//	当你想清除掉所有的图片加载请求时，这个方法可以帮助到你。
												break;

								}
						}
				}
		}

		/*
		To download images on a background thread, you must use the synchronous version:

		FutureTarget<File> future = Glide.with(applicationContext)
    .load(yourUrl)
    .downloadOnly(500, 500);
		File cacheFile = future.get();


		Bitmap myBitmap = Glide.with(applicationContext)
    .load(yourUrl)
    .asBitmap()
    .centerCrop()
    .into(500, 500)
    .get()


		 */
}