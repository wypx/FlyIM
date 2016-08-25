package me.smart.flyme.fragement.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.smart.flyme.R;
import me.smart.flyme.view.explosion.ExplosionField;


@SuppressLint("InflateParams")
public class MainTab04 extends Fragment 
{

		private View main_fg4;
		private ExplosionField mExplosionField;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
				if (null == main_fg4)
				{
						main_fg4 = inflater.inflate(R.layout.main_tab_systerm, null);
				}

				ViewGroup parent = (ViewGroup) main_fg4.getParent();
				if (parent != null)
				{
						parent.removeView(main_fg4);
				}
				mExplosionField = ExplosionField.attach2Window(getActivity());
				addListener(main_fg4.findViewById(R.id.root));
				return main_fg4;
		}


		private void addListener(View root)
		{
				if (root instanceof ViewGroup)
				{
						ViewGroup parent = (ViewGroup) root;
						for (int i = 0; i < parent.getChildCount(); i++)
						{
								addListener(parent.getChildAt(i));
						}
				}
				else
				{
						root.setClickable(true);
						root.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v)
								{
										mExplosionField.explode(v);
										v.setOnClickListener(null);
								}
						});
				}
		}


//		  	View root = findViewById(R.id.root);
//		    reset(root);
//			 addListener(root);
//			 mExplosionField.clear();



		private void reset(View root)
		{
				if (root instanceof ViewGroup)
				{
						ViewGroup parent = (ViewGroup) root;
						for (int i = 0; i < parent.getChildCount(); i++)
						{
								reset(parent.getChildAt(i));
						}
				}
				else
				{
						root.setScaleX(1);
						root.setScaleY(1);
						root.setAlpha(1);
				}
		}

}
