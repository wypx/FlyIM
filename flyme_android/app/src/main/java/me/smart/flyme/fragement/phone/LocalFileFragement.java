package me.smart.flyme.fragement.phone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.smart.flyme.R;
import me.smart.flyme.adapter.phone.LocalFileAdapter;
import me.smart.flyme.utils.file.FileAttributeInfo;
import me.smart.flyme.utils.file.FileComparator;
import me.smart.flyme.utils.file.FileOperation;
import me.smart.flyme.utils.file.FileTypeInfo;
import me.smart.flyme.view.recyclerview.AutoLoadRecyclerView;
import me.smart.flyme.view.recyclerview.LoadFinishCallBack;

/**
 * author：wypx on 2016/8/10 22:26
 * blog：smarting.me
 */
public class LocalFileFragement extends Fragment implements LocalFileAdapter.OnItemClickListener
{
		private AutoLoadRecyclerView mRecyclerView;
		private RecyclerView.LayoutManager mLayoutManager;
		private LocalFileAdapter localFileAdapter;
		private static final int SPAN_COUNT = 1;
		private LoadFinishCallBack mLoadFinisCallBack;

		private View contentView;// 缓存Fragment view

		/*
		1. 插入一张外置SD卡后
				内置SD卡路径：/storage/emulated/0
				外置SD卡路径：/storage/extSdCard
		2. 取出外置SD卡后
				内置SD卡路径：/storage/emulated/0
		 */
		//获取的sd卡的路径
		private String PATH = Environment.getExternalStorageDirectory().getPath();
		private File root=Environment.getExternalStorageDirectory();


		boolean sdCardExist = Environment.getExternalStorageState()
						.equals(android.os.Environment.MEDIA_MOUNTED);  //判断sd卡是否存

		private String abroot="/storage";  //手机内存和Sd卡

		private List<String> Itemlist; //文件名集合
		private List<String> paths=null;//路径的集合

		private String inpath;          //当前路径
		private TextView tv_path;       //文件路径

		private String [] arrayL;       //菜单数组

		private LinearLayout copy_layout;//复制按钮布局
		private LinearLayout move_layout;//移动按钮布局
		private Button btn_paste;        //粘贴按钮
		private Button btn_paste_cancel; //粘贴取消
		private Button btn_move;         //移动按钮
		private Button btn_move_cancel;  //移动取消

		private View renView;            //自定义重命名View
		private TextInputLayout textInputLayout;
		private AppCompatEditText reEdiet;        //重命名框

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
				if (null == contentView)
				{
						contentView = inflater.inflate(R.layout.tab2_fragment_one, null);
				}
				ViewGroup parent = (ViewGroup) contentView.getParent();
				if (parent != null)
				{
						parent.removeView(contentView);
				}

				mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
				mRecyclerView =(AutoLoadRecyclerView)contentView.findViewById(R.id.show_sdcard);


				tv_path=(TextView)contentView.findViewById(R.id.filepath);
				arrayL=getResources().getStringArray(R.array.arrayM3);
				copy_layout=(LinearLayout)contentView.findViewById(R.id.copyfile);
				move_layout=(LinearLayout)contentView.findViewById(R.id.movefile);
				btn_paste=(Button)contentView.findViewById(R.id.btn_paste);
				btn_paste_cancel=(Button)contentView.findViewById(R.id.btn_paste_cancel);
				btn_move=(Button)contentView.findViewById(R.id.btn_move);
				btn_move_cancel=(Button)contentView.findViewById(R.id.btn_move_cancel);

				localFileAdapter = new LocalFileAdapter(getActivity(),Itemlist,paths);
				localFileAdapter.setOnItemClickListener(this);

				//设置adapter
				mRecyclerView.setAdapter(localFileAdapter);
				//设置布局管理器
				mRecyclerView.setLayoutManager(mLayoutManager);
				//如果item的内容不改变view布局大小，那使用这个设置可以提高RecyclerView的效率
				//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
				mRecyclerView.setHasFixedSize(true);
			//	mRecyclerView.addItemDecoration(new SpaceItemDecoration(1));
				//设置Item增加、移除动画
				mRecyclerView.setItemAnimator(new DefaultItemAnimator());

				mLoadFinisCallBack = mRecyclerView;
				mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {
						@Override
						public void loadMore() {
								//mAdapter.loadNextPage();
						}
				});

				//初始化根目录root.getPath()
				//getFilesDir(root.getPath());

				getFilesDir(abroot);
				//点击事件
				return contentView;
		}
		public void refresh()
		{
				//mRecyclerView.setAdapter(localFileAdapter);
				localFileAdapter.items = Itemlist;
				localFileAdapter.Itempaths  = paths;
				localFileAdapter.notifyDataSetChanged();
		}
		@Override
		public void onItemClick(View view, int position) {
				File file=new File(paths.get(position));
				if(file.isDirectory())
				{
						//进入文件夹
						getFilesDir(paths.get(position));
				}
				else
				{
						//打开文件
						open(file);
				}
		}

		@Override
		public void onItemLongClick(View view, int position) {
				final File file=new File(paths.get(position));
				//长按的是返回，直接返回根目录
				if(Itemlist.get(position).equals("up"))
				{
						getFilesDir(file.getPath());
				}
				else {
						new AlertDialog.Builder(getActivity())
										.setTitle("                文件操作")
										.setItems(arrayL, new DialogInterface.OnClickListener() {

												public void onClick(DialogInterface dialog, int which) {
														switch (which) {
																case 0:


																		//上传
//  						Context context;
//  	                    context = getActivity().getApplicationContext();
//  	                    Intent intent = new Intent(context, UploadActivity.class);
//  	                    //传递参数给Activity
//  	                    Bundle bundle = new Bundle();
//  	                    bundle.putString("path", file.getPath());
//  	                    intent.putExtras(bundle);
//  	                    startActivity(intent);
														//	startActivity(new Intent(getActivity(),DownActivity.class));


														//传递文件路径
														//			 mylistener.Sendfilepath(file.getPath());
																		break;
																case 1://重命名
																		reName(file);
																		break;
																case 2://删除

																		delDir(file);
																		getFilesDir(inpath);
																		break;
																case 3://复制
																		copy_layout.setVisibility(View.VISIBLE);
																		fileCopy(file);
																		break;
																case 4://移动
																		move_layout.setVisibility(View.VISIBLE);
																		removeFile(file);
																		break;
																case 5://属性
																		FileAttributeInfo fileattr = new FileAttributeInfo();
																		try {
																				fileattr.attribute(getActivity(), file);
																		} catch (IOException e) {

																				e.printStackTrace();
																		}

																		break;
														}

												}

										})
										.setNegativeButton("取消", new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface arg0, int arg1) {
														Toast.makeText(getActivity(), "取消了文件操作!", Toast.LENGTH_LONG).show();
												}
										}).show();
				}
		}




		///////////////////////////////////////////////////////////////
		//文件遍历并显示
		public void getFilesDir(String filePath) {

				Itemlist=new ArrayList<String>();
				paths=new ArrayList<String>();
				tv_path.setText(filePath);
				inpath=filePath;
				File file=new File(filePath);
				File [] listFile=file.listFiles();
				//文件排序
				if(null == listFile)
						return;
				Arrays.sort(listFile, new FileComparator());
				String upPath=file.getParent();
				if(!filePath.equals(abroot))
				{
						//根目录
						Itemlist.add("up");
						paths.add(upPath);
				}
				for(File f:listFile)
				{
						if(!f.getName().equals(".android_secure")
										&&!f.isHidden()
										 && f.canRead()
										) //&& f.canWrite()
						{
								Itemlist.add(f.getName());
								paths.add(f.getPath());
						}
				}
  		/*for(int i=0;i<listFile.length;i++){
  		File f=listFile[i];
  		items.add(f.getName());
  		paths.add(f.getPath());
  	}
  */
				if(null == localFileAdapter)
				{
						localFileAdapter = new LocalFileAdapter(getActivity(),Itemlist,paths);
						mRecyclerView.setAdapter(localFileAdapter);
				}
				localFileAdapter.Itempaths = paths;
				localFileAdapter.items = Itemlist;
				localFileAdapter.notifyDataSetChanged();

		}


		/**调用系统的方法，来打开文件的方法*/
		private void open(File f){
				Log.i("TAG","open");

				if(f == null)
						return;

				if(!f.exists())
						return;

				if(!f.canRead())
						return;

				if(f.isFile()){
						try {
								Intent intent = new Intent();
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setAction(Intent.ACTION_VIEW);
								String type = FileTypeInfo.getMIMEType(f);
								intent.setDataAndType(Uri.fromFile(f), type);
								startActivity(intent);
						} catch (ActivityNotFoundException e) {
								Toast.makeText(getActivity(), "未知类型文件？",
												Toast.LENGTH_SHORT).show();
								e.printStackTrace();
						}

				}
		}

		//复制文件

		@SuppressLint("SdCardPath")
		private void fileCopy(final File file)
		{
				btn_paste.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
								File plasPath=new File(inpath+"/"+file.getName());
								if(plasPath.exists()&&!file.getPath().equals(plasPath.getPath())){
										Toast.makeText(getActivity(), "文件名重复，请重新操作",
														Toast.LENGTH_LONG).show();

										copy_layout.setVisibility(View.GONE);
								}
								else {

										//复制文件夹
										if(file.isDirectory()){

												String[] cutFile=file.getPath().split("/");
												String[] cutPlasPath=plasPath.getPath().split("/");
												String eCutFile=file.getPath().substring(0, 8)+cutFile[2];
												String pCutPlasPath=plasPath.getPath().substring(0, 8)+cutPlasPath[2];
												if(eCutFile.equals(pCutPlasPath)){
														//临时
														File newplasPath=new File("/sdcard/1234567890_qwertyuiopasdfghjklzxcvbnm0987654321");
														newplasPath.mkdir();
														File nPlasPath=new File(newplasPath.getPath()+"/"+file.getName());
														FileOperation.copyDir(file, nPlasPath);
														FileOperation.copyDir(nPlasPath, plasPath);
														FileOperation.deleteFolder(newplasPath);


														getFilesDir(inpath);
												}else {
														FileOperation.copyDir(file, plasPath);
														getFilesDir(inpath);
												}

										}else {

												FileOperation.copyFile(file, plasPath);
												getFilesDir(inpath);

										}
										copy_layout.setVisibility(View.GONE);
								}

						}
				});

				btn_paste_cancel.setOnClickListener(new View.OnClickListener()
				{

						public void onClick(View v)
						{
								copy_layout.setVisibility(View.GONE);
						}
				});

		}


		//文件移动
		@SuppressLint("SdCardPath")
		private void removeFile(final File file) {
				btn_move.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {
								// TODO Auto-generated method stub
								File plasPath=new File(inpath+"/"+file.getName());
    				/*int i=file.getPath().length();
    				System.out.println("file"+file.getPath());
    				System.out.println("plasPath"+plasPath.getParent());
    			//	System.out.println("截取"+file.getPath().substring(0, file.getPath()));
    */				if(plasPath.exists()&&!file.getPath().equals(plasPath.getPath())){
										Toast.makeText(getActivity(), "文件名重复，请重新操作",
														Toast.LENGTH_LONG).show();

										move_layout.setVisibility(View.GONE);
								}else {
										if(file.isDirectory()){
												String[] cutFile=file.getPath().split("/");
												String[] cutPlasPath=plasPath.getPath().split("/");
												String eCutFile=file.getPath().substring(0, 8)+cutFile[2];
												String pCutPlasPath=plasPath.getPath().substring(0, 8)+cutPlasPath[2];
    						/*判断是否在同一个文件夹中进行文件夹移动，
    						 * 如果是将使用中间文件夹作为过度，文件夹移动，否则将出现错误
    						 */
												if(eCutFile.equals(pCutPlasPath))
												{
														int fl=file.getPath().length();
														int pl=plasPath.getPath().length();
														//新建中间文件，存放要移动的文件夹
														File newplasPath=new File("/sdcard/1234567890_qwertyuiopasdfghjklzxcvbnm0987654321");
														newplasPath.mkdir();
														File nPlasPath=new File(newplasPath.getPath()+"/"+file.getName());
														//把要移动的文件夹复制到中间文件夹中
														FileOperation.copyDir(file, nPlasPath);
														//从中间文件夹中复制目标文件夹到所指定路径，到此完成了目标文件夹复制到指定路径中
														FileOperation.copyDir(nPlasPath, plasPath);
														//删除中间文件夹

														FileOperation.deleteFolder(newplasPath);

    							/*如果源文件夹路径比指定路径短，也就是说此时的源文件夹路径是指定路径的父路径
    							 * 此时删除源文件夹，将连移动文件夹一同删除*/
														if(fl>pl)
														{
																//删除源文件夹

																FileOperation.deleteFolder(file);

																getFilesDir(inpath);

														}
														else
														{
																FileOperation.copyDir(file, plasPath);
																getFilesDir(inpath);
														}


												}
												else
												{

														FileOperation.copyFile(file, plasPath);
														file.delete();
														getFilesDir(plasPath.getParent());

												}
												move_layout.setVisibility(View.GONE);
										}

								}}
				});

				btn_move_cancel.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {
								move_layout.setVisibility(View.GONE);
						}
				});

		}
		//删除文件
		private void delDir(final File file) {
				new AlertDialog.Builder(getActivity())
								.setTitle("警告")
								.setMessage("是否确定删除")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												File delFile=file;
												if(file.isDirectory()){
														FileOperation.deleteFolder(delFile);
												}
												else{
														file.delete();
												}
												getFilesDir(inpath);
										}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog, int which) {

										}
								}).show();
		}
		//文件重命名
		private void reName(final File file) {
				// TODO Auto-generated method stub
				LayoutInflater inflater=LayoutInflater.from(getActivity());
				renView=inflater.inflate(R.layout.tab2_file_rename_dilog, null);
				textInputLayout =(TextInputLayout)renView.findViewById(R.id.textrnInput);
				textInputLayout.setHint("请输入重命名的名字?");
				reEdiet = (AppCompatEditText)textInputLayout.getEditText();
				reEdiet.addTextChangedListener(new TextWatcher()
				{
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after)
						{
						}

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count)
						{
								if (s.length()<= 0 && s.equals(""))
								{
										//执行有顺序
										textInputLayout.setError("输入的名字不能为空!");
										textInputLayout.setErrorEnabled(true);
								}
								else
								{
										//当设置成false的时候 错误信息不显示 反之显示
										textInputLayout.setErrorEnabled(false);
								}
						}

						@Override
						public void afterTextChanged(Editable s)
						{
						}
				});
				new AlertDialog.Builder(getActivity())
								.setTitle("                  重命名")
								.setView(renView)
								.setPositiveButton("确定", new DialogInterface.OnClickListener()
								{
										public void onClick(DialogInterface dialog, int which)
										{

												String name=reEdiet.getText().toString();
												//取得要命名文件的路径
												String pFile=file.getParent()+"/";
												String newPath=pFile+name;
												if(name.equals(""))
												{
														Toast.makeText(getActivity(), "文件名不能为空",
																		Toast.LENGTH_SHORT).show();
												}
												else
												{
														//判断是否为文件夹
														if(file.isDirectory())
														{
																//判断文件夹名是否重名
																if(new File(newPath).exists())
																{
																		new AlertDialog.Builder(getActivity())
																						.setTitle("警告")
																						.setMessage("文件名重复，请重新命名")
																						.setPositiveButton("确定",new DialogInterface.OnClickListener()
																						{

																								public void onClick(DialogInterface dialog, int which)
																								{

																								}
																						})
																						.show();
																}
																//对文件夹重命名
																else{
																		file.renameTo(new File(newPath));
																		getFilesDir(inpath);
																}
														}
														//对文件重命名
														else{
																String fistName= file.getName();
																String lastName=fistName.substring(fistName.indexOf("."),
																				fistName.length());
																String pa=pFile+name;
																//判断文件是否重名
																if(new File(pa+lastName).exists()){
																		new AlertDialog.Builder(getActivity())
																						.setTitle("警告")
																						.setMessage("文件名重复，请重新命名?")
																						.setPositiveButton("确定",new DialogInterface.OnClickListener() {

																								public void onClick(DialogInterface dialog, int which) {
																										// TODO Auto-generated method stub

																								}
																						})
																						.show();
																}else{
																		file.renameTo(new File(pa+lastName));
																		getFilesDir(inpath);
																}
														}

												}
										}

								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub

										}
								})
								.show();
		}

}
