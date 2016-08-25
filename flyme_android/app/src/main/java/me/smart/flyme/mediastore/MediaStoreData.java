package me.smart.flyme.mediastore;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 *  单个媒体数据模型
 *  实现Parcelable步骤
 *  android提供了一种新的类型：Parcel,本类被用作封装数据的容器.
 *  封装后的数据可以通过Intent或IPC传递,除了基本类型以外,只有实
 *  现了Parcelable接口的类才能被放入Parcel中
 */

public class MediaStoreData implements Parcelable
{
		//实例化静态内部对象CREATOR实现接口Parcelable.Creator
		public static final Creator<MediaStoreData> CREATOR = new Creator<MediaStoreData>()
		{
				@Override
				public MediaStoreData createFromParcel(Parcel parcel)
				{
						return new MediaStoreData(parcel);
				}

				@Override
				public MediaStoreData[] newArray(int i)
				{
						return new MediaStoreData[i];
				}
		};

		@Override
		public int describeContents()
		{
				return 0;
		}

		public final long rowId;
		public final Uri uri;
		public final String mimeType;         //
		public final long dateModified;      //修改时间
		public final int orientation;
		public final MimeType type;             //类型
		public final long dateTaken;        //时间磋

		public final String title;			//标题
		public final String filepath;		//文件路径
		public final String thumbpath;		//缩略图路径
		public final int size;				//大小

		public MediaStoreData(long rowId, Uri uri,
		                      String mimeType, long dateTaken,
		                      long dateModified, int orientation,
		                      String title, String filepath,
		                      String thumbpath,int size,
		                      MimeType type
		)
		{
				this.rowId = rowId;
				this.uri = uri;
				this.dateModified = dateModified;
				this.mimeType = mimeType;
				this.orientation = orientation;
				this.type = type;
				this.dateTaken = dateTaken;
				this.title  = title;
				this.filepath = filepath;
				this.thumbpath = thumbpath;
				this.size = size;
		}

		MediaStoreData(Parcel in)
		{
				rowId = in.readLong();
				uri = Uri.parse(in.readString());
				mimeType = in.readString();
				dateTaken = in.readLong();
				dateModified = in.readLong();
				orientation = in.readInt();
				title = in.readString();
				filepath =in.readString();
				thumbpath = in.readString();
				size = in.readInt();
				type = MimeType.valueOf(in.readString());
		}

		/**
		 * 重写writeToParcel方法，将你的对象序列化为一个Parcel对象
		 * 将类的数据写入外部提供的Parcel中,打包需要传递的数据到Parcel容器保存,以便从 Parcel容器获取数据
		 * @param parcel
		 * @param i
		 */
		@Override
		public void writeToParcel(Parcel parcel, int i)
		{
				// Parcel对象可以通过以下方法写入或读取byte, double, float, int, long, String这6种类型变量。

				parcel.writeLong(rowId);
				parcel.writeString(uri.toString());
				parcel.writeString(mimeType);
				parcel.writeLong(dateTaken);
				parcel.writeLong(dateModified);
				parcel.writeInt(orientation);

				parcel.writeString(title);
				parcel.writeString(filepath);
				parcel.writeString(thumbpath);
				parcel.writeInt(size);

				parcel.writeString(type.name());
		}

		/**
		 * 返回媒体信息
		 * @return
		 */
		public String getMediaInfo()
		{
				return "标题:"+title+"\n 文件路径:"+filepath+
								"\n缩略图:"+thumbpath+
								" \n大小:"+size+
								" \n Uri :"+uri+" \n类型:"+mimeType+" 图片类型:"+type;

		}

		public enum MimeType
		{
				VIDEO,
				IMAGE,
				Audio,
		}
}
