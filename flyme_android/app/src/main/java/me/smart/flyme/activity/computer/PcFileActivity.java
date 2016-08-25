package me.smart.flyme.activity.computer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.smart.flyme.R;
import me.smart.flyme.view.fab.FABToolbarLayout;

/**
 * author：wypx on 2015/12/27 21:42
 * blog：smarting.me
 */
public class PcFileActivity extends AppCompatActivity
{

		private FABToolbarLayout layout;
		private View one, two, three, four;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.base_file_recycleview);

				layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar_base);
				one = findViewById(R.id.img_base_one);
				two = findViewById(R.id.img_base_two);
				three = findViewById(R.id.img_base_three);
				four = findViewById(R.id.img_base_four);

				layout.hide();
				layout.show();
		}
}
