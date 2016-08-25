package me.smart.flyme.constant;

/**
 * author：wypx on 2016/8/1 22:38
 * blog：smarting.me
 */
public class COMMON
{
		public static int CMD_DRIVER_LIST = 1;
		public enum CMD_TYPE
		{
				CMD_SOCK_CLOSE	,   //关闭连接

				CMD_DRIVER_LIST2,		//磁盘信息
				CMD_FILE_LIST,			//目录信息
				CMD_FILE_EXE,			  //执行文件
				CMD_FILE_DEL,			  //删除文件
				CMD_FILE_RENAME,		//文件重名
				CMD_FILE_MOVE,			//文件移动
				CMD_FILE_COPY,			//文件复制
				CMD_FILE_INFO,			//文件信息
				CMD_FILE_DOWNLOAD,	//文件下载
				CMD_DIR_DELETE,			//删除目录
				CMD_DIR_CREATE,			//创建目录



				CMD_SHELL_EXCUTE,
				CMD_SCREEN_SHOT	,
				CMD_SCREEN_LOCK,
				CMD_SCREEN_CONTROL,
				CMD_VIDEO	,
				CMD_KEYLOG,

				CMD_SYS_EVENT,
				CMD_SYS_LOGOFF,			   //注销
				CMD_SYS_SHUTDOWN,		   //关机
				CMD_SYS_REBOOT,			   //重启
				CMD_SYS_SHOWWINDOW,		 //显示窗口
				CMD_SYS_HIDEWINDOW,		 //隐藏窗口


				CMD_MOUSE_LOCK,
				CMD_MOUSE_DOUBLE,
				CMD_MOUSE_SINGLE,
				CMD_MOUSE_RIGHT,
				CMD_MOUSE_LEFT,
				CMD_MOUSE_MOVE,

				CMD_KEY_EVENT,
				CMD_KEY_WINDOW,			            //模拟windows键
				CMD_KEY_ESC,			              //模拟Esc键
				CMD_KEY_DELETE,			            //Delete键
				CMD_KEY_PAGEUP,			            //PageUp键
				CMD_KEY_PAGEDOWN,		            //PageDown键
				CMD_KEY_PLAY,			              //Play键
				CMD_KEY_PLAY_NOWPAGE,	          //Shift+F5键
				CMD_KEY_PLAY_PAGEONE,	          //F5键
				CMD_KEY_CLOSE_ALLPAGE,	        //关闭当前的窗口应用 ALT+F4

				CMD_PROCESS_LIST,		            //进程列表
				CMD_PROCESS_KILL,		            //杀掉进程

				CMD_REMOTE_CMDLINE,		          //远程CMD
				CDM_REMOTE_PPT,			            //远程PPT
		};
}

