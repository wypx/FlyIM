package me.smart.flyme.socket;

/**
 * author：wypx on 2015/12/26 22:11
 * blog：smarting.me
 */
//实现的是C++中的Command结构体对应
public class Command {
		//C++的char就是一个字节，java的char是两个字节，所以统一用byte
		//主要思路是：将所有的数据类型都转化为byte流，对byte流进行传输。
		//在这里，c++服务器用char类型的数组，android用byte数组进行文件传输。
		private static final int IDLen=4;
		private static final int LparamLen=1024;
		private static final int CommandLen=1028;
		public byte []byteArrayData=new byte[CommandLen];

		//Command类有用于接收byte数组和 服务端结构体对应的ID 和Lparam
		private int ID;
		private String lparam;
		private DataTransform mDataTypeTransform=new DataTransform();

		public byte[] getByteArrayData(){
				return byteArrayData;
		}
		public Command(){

		}

		// 把ID  和lparam  转化成 Byte数组 ,用于发送时候用
		public Command(int ID,String lparam) {

				//ID         IntToByteArray
				//lparam     StringToByteArray

				this.ID=ID;
				this.lparam=lparam;
				byte[] IDbyte = mDataTypeTransform.IntToByteArray(ID);
				System.arraycopy(IDbyte,0, byteArrayData, 0, IDbyte.length);
				byte[] Strbyte = mDataTypeTransform.StringToByteArray(lparam);
				System.arraycopy(Strbyte,0,byteArrayData,IDbyte.length,Strbyte.length);
		}

		//把接收的Byte数组转化为相应的ID  和  Lparam参数
		public Command(byte[] dataArray){
				int id=1;
				String lpString="";
				//接收的字节赋值给类成员byteArrayData字节数组
				System.arraycopy(dataArray,0, byteArrayData,0,CommandLen);

				//存放ID的临时字节数组，并把它转化成int
				byte[] forIntID = new byte[IDLen];
				System.arraycopy(dataArray,0,forIntID,0,forIntID.length);
				id=mDataTypeTransform.ByteArrayToInt(forIntID);

				//存放Lparam的临时字节数组，并把它转化成Sring
				byte[] StrTemp=new byte[LparamLen];
				System.arraycopy(dataArray,IDLen,StrTemp,0,StrTemp.length);
				lpString=mDataTypeTransform.ByteArraytoString(StrTemp, StrTemp.length);
				//lpString=StrTemp.toString();
				//赋值给类成员
				ID=id;
				lparam=lpString;
		}
		public int getID(){
				return ID;
		}
		public String getLparam(){
				return lparam;
		}
		public void setID(int id) {
				this.ID=id;
		}
		public void setLparam(String str){
				this.lparam=str;
		}
		public static final String [] reomte_commnd = {
						"ASSOC",
						"ATTRIB",
						"BREAK",
						"BCDEDIT",
						"CACLS",
						"CALL",
						"CD",
						"CHCP",
						"CHDIR",
						"CHKDSK",
						"CHKNTFS",
						"CLS",
						"CMD",
						"COLOR",
						"COMP",
						"COMPACT",
						"CONVERT",
						"COPY",
						"DATE",
						"DEL",
						"DIR",
						"DISKCOMP",
						"DISKCOPY",
						"DISKPART",
						"DOSKEY",
						"DRIVERQUERY",
						"ECHO",
						"ENDLOCAL",
						"ERASE",
						"EXIT",
						"FC",
						"FIND",
						"FINDSTR",
						"FOR",
						"FORMAT",
						"FSUTIL",
						"FTYPE",
						"GOTO",
						"GPRESULT",
						"GRAFTABL",
						"HELP",
						"ICACLS",
						"ACL",
						"IF",
						"LABEL",
						"MD",
						"MKDIR",
						"MKLINK",
						"MODE",
						"MORE",
						"MOVE",
						"OPENFILES",
						"PATH",
						"PAUSE",
						"POPD",
						"PRINT",
						"PROMPT",
						"PUSHD",
						"RD",
						"RECOVER",
						"REM",
						"REN",
						"RENAME",
						"REPLACE",
						"RMDIR",
						"ROBOCOPY",
						"SET",
						"SETLOCAL",
						"SC",
						"SCHTASKS",
						"SHIFT",
						"SHUTDOWN",
						"SORT",
						"START",
						"SUBST",
						"SYSTEMINFO",
						"TASKLIST",
						"TASKKILL",
						"TIME",
						"TITLE",
						"TREE",
						"TYPE",
						"VER",
						"VERIFY",
						"VOL",
						"XCOPY",
						"WMIC",
						"ipconfig",
						"netstat -all",
						"date",
						"ping"
		};
}
