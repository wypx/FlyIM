package me.smart.flyme.view.wiget;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import me.smart.flyme.R;

public class LoginDialog extends AppCompatDialog  implements View.OnClickListener
{
		@SuppressWarnings("unused")
	private int mDuration = -1;
	/** 布局文件 **/
	int layoutRes;
	/** 上下文对象 **/
	Context context;
	/** 确定按钮 **/
	private Button confirmBtn;
	/** 取消按钮 **/
	private Button cancelBtn;
	/** 离线消息按钮 **/
	private RadioButton myRadioButton;
	/** 点击次数 **/
	private int check_count = 0;
	/** Toast时间 **/
	public static final int TOAST_TIME = 1000;
	
	
	private ImageView head_portrit;

	public LoginDialog(Context context)
	{
		super(context);
		this.context = context;
	}

	/**
	 * 自定义布局的构造方法
	 * 
	 * @param context
	 * @param resLayout
	 */
	public LoginDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方法
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public LoginDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 指定布局
		this.setContentView(layoutRes);

		// 根据id在布局中找到控件对象
		confirmBtn = (Button) findViewById(R.id.filesever_login);
		cancelBtn = (Button) findViewById(R.id.filesever_cancel);
		myRadioButton = (RadioButton) findViewById(R.id.bt_save_user_pass);

//		Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.round_imaget);
//		LinearInterpolator lin = new LinearInterpolator();
//		operatingAnim.setInterpolator(lin);
//		if (operatingAnim != null)
//		{
//			head_portrit.startAnimation(operatingAnim);
//		}

		// 设置按钮的文本颜色
		confirmBtn.setTextColor(0xff1E90FF);
		cancelBtn.setTextColor(0xff1E90FF);

		// 为按钮绑定点击事件监听器
		confirmBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		myRadioButton.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{

			case R.id.filesever_login:
				// 点击了确认按钮
				Toast.makeText(context, "您点击了确定按钮", Toast.LENGTH_LONG).show();
				break;

			case R.id.filesever_cancel:
				// 点击了取消按钮
				Toast.makeText(context, "您点击了取消按钮", Toast.LENGTH_LONG).show();
				dismiss();
				break;

			case R.id.bt_save_user_pass:
				// 点击了离线消息按钮
				check_count = check_count + 1;
				if (check_count % 2 == 0) {
				// no checked
				myRadioButton.setButtonDrawable(context.getResources()
						.getDrawable(R.mipmap.ui_loginout_radio));
				} 
				else 
				{
				// checked
				myRadioButton.setButtonDrawable(context.getResources()
						.getDrawable(R.mipmap.ui_loginout_radio_check));
				}
				break;

			default:
				head_portrit.clearAnimation();

			break;
		}
	}
 
}
