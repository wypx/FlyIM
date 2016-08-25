package me.smart.flyme.mediastore;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.smart.flyme.constant.MediaConf;

public class MediaStoreDataLoader extends AsyncTaskLoader<List<MediaStoreData>>
 {
		//Prjs:这个参数代表要从表中选择的列,用一个String数组来表示
	//要查询的图片列表项
  private static final String[] IMAGE_PROJECTION = new String[]
	{
	  	
	  	MediaStore.Images.Media.TITLE,    //媒体标题 常量值：title
	  	MediaStore.Images.Media.DATA,   //媒体路径  常量值：_data
	  	MediaStore.Images.Media.SIZE,   //媒体文件大小 常量值：_size
	
	  	MediaStore.Images.Media._ID,
	  	MediaStore.Images.Media.DATE_TAKEN,
	  	MediaStore.Images.Media.DATE_MODIFIED,
	  	MediaStore.Images.Media.MIME_TYPE,
	  	MediaStore.Images.Media.ORIENTATION,

	  	/**
	  	MediaStore.Images.Media.DISPLAY_NAME,
	  	MediaStore.Images.Media.DATE_ADDED,
	  	MediaStore.Images.Media.MINI_THUMB_MAGIC,  	
	  	*/
   };
  private static final String[] VIDEO_PROJECTION =new String[]
	{
	  
	  	 MediaStore.Video.Media.TITLE,
	  	 MediaStore.Images.Media.DATA,
	  	 MediaStore.Video.Media.SIZE,
	  	 MediaStore.Video.Media._ID,
	  	 MediaStore.Video.Media.DATE_MODIFIED,  
	  	 MediaStore.Video.Media.DATE_TAKEN,	 
	  	 MediaStore.Video.Media.MIME_TYPE, 	
	  	 "0 AS "+MediaStore.Images.Media.ORIENTATION,
	  	 
	/**  	 
	 	   MediaStore.Video.Media.DATA,
	  	 MediaStore.Video.Media.DISPLAY_NAME,//视频文件名,如 testVideo.mp4
	  	 MediaStore.Video.Media.DATE_ADDED,
	  	 MediaStore.Video.Media.MINI_THUMB_MAGIC,
	 */
      };
  private static final String[] thumbColumns = new String[]
	{
	  	MediaStore.Images.Media.DATA,			//缩略图路径
			MediaStore.Video.Thumbnails.KIND,
			MediaStore.Video.Thumbnails.VIDEO_ID
//    MediaStore.Video.Thumbnails.WIDTH,
//    MediaStore.Video.Thumbnails.HEIGHT,
//    MediaStore.Video.Thumbnails._COUNT,
  	  };
  
  private List<MediaStoreData> mediadata_cached;
  
  private boolean observerRegistered = false;
	//往往希望当数据发生改变时，Cursor也会自动的更新数据
	//ForceLoadContentObserver监听数据源的变化,可以自动刷新列表
	//观察者模式,无非是系统自带了,只需调用即可,无需自己构造观察者。
  private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();

  public  MediaStoreDataLoader(Context context)
  {
			  super(context);
			  Log.i("MediaStoreDataLoader", "构造");
  }

  @Override
  public void deliverResult(List<MediaStoreData> data) 
  {
	  	Log.i("MediaStoreDataLoader", "deliverResult");
	    if (!isReset() && isStarted())
	    {
			    super.deliverResult(data);
	    }
  }

  @Override
  protected void onStartLoading() 
  {
	  	Log.i("MediaStoreDataLoader", "onStartLoading");
	    if (null != mediadata_cached)
	    {
			    deliverResult(mediadata_cached);
	    }
	    if (takeContentChanged() || null == mediadata_cached)
	    {
			    forceLoad();
	    }
	    registerContentObserver();
  }

  @Override
  protected void onStopLoading() 
  {
	  	Log.i("MediaStoreDataLoader", "onStopLoading");
	  	cancelLoad();
  }

  @Override
  protected void onReset() 
  {
	  	Log.i("MediaStoreDataLoader", "onReset");
	  	super.onReset();

	    onStopLoading();
	    mediadata_cached = null;
	    unregisterContentObserver();
  }

  @Override
  protected void onAbandon() 
  {
	  	Log.i("MediaStoreDataLoader", "onAbandon");
	    super.onAbandon();
	    unregisterContentObserver();
  }
//////////////////////////////////////////////////
  @Override
  public List<MediaStoreData> loadInBackground() 
  {
		  Log.e("MediaStoreDataLoader", "loadInBackground:" + MediaConf.getCurrentMimeType());
	    List<MediaStoreData> data = null;
		  if(MediaConf.getCurrentMimeType() == MediaStoreData.MimeType.IMAGE)
		  {
				  data = queryImages();
		  }
		  else if(MediaConf.getCurrentMimeType() == MediaStoreData.MimeType.VIDEO)
		  {
				  data = queryVideos();
		  }
			if(0 == data.size() && null == data )
			{
					return null;
			}
	    Collections.sort(data, new Comparator<MediaStoreData>()
	    {
			    @Override
			    public int compare(MediaStoreData mediaStoreData, MediaStoreData mediaStoreData2)
			    {
					    return Long.valueOf(mediaStoreData2.dateTaken).compareTo(mediaStoreData.dateTaken);
			    }
      });
	  
	  return data;
  }

  //查询图片
  private List<MediaStoreData> queryImages() 
  {
    return query(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
    	MediaStore.Images.Media.DATE_TAKEN,       MediaStore.Images.Media._ID,
    	MediaStore.Images.Media.DATE_TAKEN,       MediaStore.Images.Media.DATE_MODIFIED,
    	MediaStore.Images.Media.MIME_TYPE,        MediaStore.Images.Media.ORIENTATION,
    	MediaStore.Images.Media.TITLE,	          MediaStore.Images.Media.DATA,
    	MediaStore.Images.Media.SIZE,             MediaStoreData.MimeType.IMAGE);
  }

  private List<MediaStoreData> queryVideos()
  {
    return query(
			MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
    	MediaStore.Video.Media.DATE_TAKEN,        MediaStore.Video.Media._ID,
    	MediaStore.Video.Media.DATE_TAKEN,        MediaStore.Video.Media.DATE_MODIFIED,
			MediaStore.Video.Media.MIME_TYPE,         MediaStore.Images.ImageColumns.ORIENTATION,
      MediaStore.Video.Media.TITLE,	            MediaStore.Images.Media.DATA,
      MediaStore.Video.Media.SIZE,              MediaStoreData.MimeType.VIDEO);
    
  }
  private List<MediaStoreData> queryMusic()
  {
			Context context = null;
		  ContentResolver cr = context.getContentResolver();
		  // 获取所有歌曲
		  Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		  if(null == cursor)
		  {
				  return null;
		  }
		  if (cursor.moveToFirst())
		  {
				  do
				  {
						  String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
						  String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
						  if ("<unknown>".equals(singer))
						  {
								  singer = "未知艺术家";
						  }
						  String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
						  long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
						  long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
						  String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
						  String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
						  String sbr = name.substring(name.length() - 3, name.length());

						  if (sbr.equals("mp3") || sbr.equals("wma") || sbr.equals("wav") || sbr.equals("aac")) {
								  //为每首歌 配置属性
//						  m.setTitle(title);
//						  m.setSinger(singer);
//						  m.setAlbum(album);
//						  m.setSize(size);
//						  m.setTime(time);
//						  m.setUrl(url);
//						  m.setName(name);
//						  //把每首歌加入集合
//						  musicList.add(m);
						  }

				  }
				  while (cursor.moveToNext()) ;
		  }

		  return null;
  }

  private List<MediaStoreData> query(
			Uri contentUri,           String[] projection,
		  String sortByCol,         String idCol,
		  String dateTakenCol,      String dateModifiedCol,
		  String mimeTypeCol,       String orientationCol,
		  String titleCol,          String filepathCol,
		  String sizeCol,           MediaStoreData.MimeType type)
  {

	  final List<MediaStoreData> data = new ArrayList<MediaStoreData>();

		  //获取ContentResolver实例,就可以进行各种查询了
		  //在adb shell中,在/data/data/com.android.providers.media/databases/下.db文件的媒体数据库文件.
		  /**
		   * query(_uri, prjs, selections, selectArgs, order)方法接受几个参数,参数意义如下
		   *
		   * Uri: 代表要查询的数据库名称加上表的名称。这个Uri一般都直接从MediaStore里取得。
		   * 例如获取歌曲的信息,就必须利用MediaStore.Audio.Media.EXTERNAL _CONTENT_URI这个Uri.
		   * 专辑信息要利用MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI这个Uri来查询,其他查询也都类似。
		   *
		   * Prjs：这个参数代表要从表中选择的列，用一个String数组来表示。没有则为null
		   * Selections：相当于SQL语句中的where子句，就是代表你的查询条件。
		   * Order：说明查询结果按什么来排序。
		   *
		   * 它返回的查询结果一个Cursor，这个Cursor就相当于数据库查询的中Result
		   * 参考:http://blog.csdn.net/lilizhangyuer/article/details/8077862
		   */
	  Cursor cursor=CursorQuery.getCursor(getContext(),contentUri,projection,null,null,sortByCol+" DESC");
	  if (null == cursor) 
	  {
		    return data;//null
	  }
     try
     {
		     //查询得到列
	      final int idColNum = cursor.getColumnIndexOrThrow(idCol);
	      final int dateTakenColNum = cursor.getColumnIndexOrThrow(dateTakenCol);
	      final int dateModifiedColNum = cursor.getColumnIndexOrThrow(dateModifiedCol);
	      final int mimeTypeColNum = cursor.getColumnIndex(mimeTypeCol);
	      final int orientationColNum = cursor.getColumnIndexOrThrow(orientationCol);
	      
	      final int titleColNum = cursor.getColumnIndexOrThrow(titleCol);
	      final int filepathColNum = cursor.getColumnIndexOrThrow(filepathCol);
	      final int sizeColNum = cursor.getColumnIndexOrThrow(sizeCol);

	      while (cursor.moveToNext()) 
	      {
		        long id = cursor.getLong(idColNum);
		        long dateTaken = cursor.getLong(dateTakenColNum);
		        String mimeType = cursor.getString(mimeTypeColNum);
		        long dateModified = cursor.getLong(dateModifiedColNum);
		        int orientation = cursor.getInt(orientationColNum);
		        String title = cursor.getString(titleColNum);
		        String filepath =cursor.getString(filepathColNum);
		        int size = cursor.getInt(sizeColNum);
		        String thumbpath = " ";
			      if(MediaStoreData.MimeType.VIDEO == type)
			      {
					      thumbpath= VideoThumbnailQuery.getVideoThumbnail(getContext(),
									      Uri.withAppendedPath(contentUri, Long.toString(id)));
			      }
			      else if(MediaStoreData.MimeType.IMAGE == type )
			      {
//					      thumbpath= ImageThumbnailQuery.getImageThumbnail(getContext(),
//									      Uri.withAppendedPath(contentUri, Long.toString(id)));
			      }


		//     thumbpath = CursorQuery.queryImageThumbnailByPath(getContext(),filepath);
	        data.add(
	        		new MediaStoreData(
		        		id,             Uri.withAppendedPath(contentUri, Long.toString(id)),
		        		mimeType,		    dateTaken,
		        		dateModified,	  orientation,
		        		title,			    filepath,
		        		thumbpath,		  size,
		        		type)
	        		);
	      }
       } 
	   finally 
	   {
	    	cursor.close();
	   }

     	return data;
  }

  private void registerContentObserver() 
  {
		//注册观察者
    if (!observerRegistered) 
    {
      ContentResolver cr = getContext().getContentResolver();
      cr.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, forceLoadContentObserver);
      cr.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, false,forceLoadContentObserver);

      observerRegistered = true;
    }
  }

	//解绑内容观察者
	private void unregisterContentObserver()
  {
	    if (observerRegistered) 
	    {
		      observerRegistered = false;
		      getContext().getContentResolver().unregisterContentObserver(forceLoadContentObserver);
	    }
  }

}
