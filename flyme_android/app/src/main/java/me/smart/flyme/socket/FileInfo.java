package me.smart.flyme.socket;

public class FileInfo {
	
	//构造一个和服务器结构体一样的
	 /*使用System.arraycopy()实现数组之间的复制
   arraycopy(Object src,int srcPos,Object dest,int destPos,int length) 
	src:源数组                    srcPos:源数组要复制的起始位置
	dest:目的数组；           destPos:目的数组放置的起始位置
	length:复制的长度
	*/
	private int Filelen;           //文件大小    4个字节
	private String Filename;       //文件名称    260字节 
	private String FileTime;           //时间信息    50个字节
	//boolean IsDir;              //为目录否    4
   // 为说明问题，定死大小，事件中可以灵活处理
	private static final int FileLength=4;
	private static final int FilenameLength=260;
	private static final int FileTimeLength=50;
	private static final int FileTotalLength=314;
    DataTransform mDataTypeTransform=new DataTransform();
    public byte []byteArrayData=new byte[314];
    
    
    /**
	 * 构造并转换  发送文件的信息，用于发送手机sd卡文件
	 */
	public FileInfo(int len, String filename, String time) {
		
		this.Filelen = len;
		this.Filename = filename;
		this.FileTime =time;
	    //文件大小int转为byte数组，并且复制到byteArrayData中，赋值长度是0-(4+0)
		byte[] Filelenbyte = mDataTypeTransform.IntToByteArray(len);
		System.arraycopy(Filelenbyte,0, byteArrayData, 0, Filelenbyte.length);
		
		//文件名字转化为byte数组，并且复制到byteArrayData中，长度是4-(260+4)
		//byte[] Filenamebyte = mDataTypeTransform.StringToByteArray(filename);
		byte[] Filenamebyte=filename.getBytes();
		System.arraycopy(Filenamebyte,0,byteArrayData,4,Filenamebyte.length);
		
		//文件时间转为byte数组，并且复制byteArrayData中，长度是264-(264+50)314
		//byte[] FileTimebyte = mDataTypeTransform.StringToByteArray(time);
		byte[] FileTimebyte =FileTime.getBytes();
		System.arraycopy(FileTimebyte,0, byteArrayData, 264,FileTimebyte.length);
	}
	
	//把接收的Byte数组转化为相应的filelen,filename,filetime
	//         对应的是  int  String   String 
		public FileInfo(byte[] dataArray){
			int filelen=0;
		    String filename="";
		    String filetime="";
		    //接收的字节赋值给类成员byteArrayData字节数  
			System.arraycopy(dataArray,0, byteArrayData,0,FileTotalLength);
			
			//存放filelen的临时字节数组，并把它转化成int
			byte[] filelenbyte= new byte[FileLength];//4个字节
			//复制前面四个字节到filelen的字节数组
			System.arraycopy(dataArray,0,filelenbyte,0,4);
			//filelen的字节数组转化为int
			filelen=mDataTypeTransform.ByteArrayToInt(filelenbyte);
			
			//存放Strfilename的临时字节数组，并把它转化成Sring
			byte[] Strfilename=new byte[FilenameLength];//260个字节
			System.arraycopy(dataArray,4,Strfilename,0,260);
			filename=mDataTypeTransform.ByteArraytoString(Strfilename, 260);

			
			//存放Strfilename的临时字节数组，并把它转化成Sring
			byte[] Strfiletime=new byte[FileTimeLength];//50个字节
			System.arraycopy(dataArray,264,Strfiletime,0,50);
			filetime=mDataTypeTransform.ByteArraytoString(Strfiletime,50);
			//赋值给类成员
			Filelen=filelen;
			Filename=filename;
			FileTime=filetime;
		}
	
	/**
	 * 返回要发送的数组
	 */
	public byte[] getbuf() {
		return byteArrayData;
	}
	public int getFilelen() {
		return Filelen;
	}
	public void setFilelen(int filelen) {
		Filelen = filelen;
	}
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}
	public String getTime() {
		return FileTime;
	}
	public void setTime(String time) {
		FileTime = time;
	}
	
}
