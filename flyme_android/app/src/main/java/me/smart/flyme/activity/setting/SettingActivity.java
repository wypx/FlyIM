package me.smart.flyme.activity.setting;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.smart.flyme.R;
import me.smart.flyme.fragement.setting.SettingFragment;

public class SettingActivity extends AppCompatActivity
{

    public static final String PREFERENCE_FILE_NAME = "note.settings";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        AVLoadingIndicatorView AVLoading_view = (AVLoadingIndicatorView)findViewById(R.id.AVLoading_view);

       // AVLoading_view.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.setting_fragment, new SettingFragment(), null).commit();
    }

}
