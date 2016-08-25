package me.smart.flyme.activity.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import me.smart.flyme.R;
import me.smart.mylibrary.flowingdrawer.MenuFragment;


public class MyMenuFragment extends MenuFragment {

    private ImageView ivMenuUserProfilePhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        ivMenuUserProfilePhoto = (ImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
//        setupHeader();
        return  setupReveal(view) ;
    }

    private void setupHeader() {
        int avatarSize = 64;
        String profilePhoto = "https://avatars1.githubusercontent.com/u/6947495?v=3&amp;s=460";

        Glide.with(getActivity()).
                load(profilePhoto).
//                thumbnail(1/3).
//                fitCenter().
                centerCrop().
                override(avatarSize,avatarSize).    //大小
                //.dontTransform()
                //.dontTransform()
                transform(new CircleTransformation()).        //动画
//                        crossFade().  ////动画
                placeholder(R.drawable.img_circle_placeholder).//占位符
//                resize(avatarSize, avatarSize).
                error(R.mipmap.ic_launcher).
                //diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
                        into(ivMenuUserProfilePhoto);
    }

    public void onOpenMenu(){
        Toast.makeText(getActivity(),"onOpenMenu",Toast.LENGTH_SHORT).show();
    }
    public void onCloseMenu(){
        Toast.makeText(getActivity(),"onCloseMenu",Toast.LENGTH_SHORT).show();
    }
}
