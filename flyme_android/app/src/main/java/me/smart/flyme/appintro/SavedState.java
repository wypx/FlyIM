package me.smart.flyme.appintro;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;
/**
 * @Title: SavedState.java
 * @Package com.remote.appintro
 * @Description: TODO(序列化的保存状态)
 * @author amor smarting.me
 * @date 2015-11-26 12:06:45
 * @version V1.0
 */
public class SavedState extends BaseSavedState
{
    int currentPage;

    public SavedState(Parcelable superState) 
    {
        super(superState);
    }

    private SavedState(Parcel in)
    {
        super(in);
        currentPage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        super.writeToParcel(dest, flags);
        dest.writeInt(currentPage);
    }

    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() 
    {
        @Override
        public SavedState createFromParcel(Parcel in) 
        {
            return new SavedState(in);
        }

        @Override
        public SavedState[] newArray(int size) 
        {
            return new SavedState[size];
        }
    };
}