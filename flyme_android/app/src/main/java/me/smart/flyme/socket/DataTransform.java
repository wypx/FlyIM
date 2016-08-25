package me.smart.flyme.socket;

import java.io.UnsupportedEncodingException;

public class DataTransform {
	//http://blog.csdn.net/jiangxinyu/article/details/8211612
	//http://war-martin.iteye.com/blog/1654336
	//http://blog.csdn.net/kingfish/article/details/333635
	//http://blog.csdn.net/kingfish/article/details/334095
	//高低位互换(Big-Endian 大头在前 ; Little-Endian 小头在前)。
	/* big-endian也称高位在前、大端在前
	 * big-endian 最直观的字节序：内存地址从左到右与值由低到高的顺序相对应。
	 * 网络字节序与主机字节序   htons  htonl  ntohs ntohl
	 * 
	 * Java虚拟机遵循的是big-endian规则
	 * 
	 * 
	 * java和c/c++之间进行socket通信，socket通信是以字节流或者字节包进行的，
	 * socket发送方须将数据转换为字节流或者字节包，
	 * 而接收方则将字节流和字节包再转换回相应的数据类型。
	 * 如果发送方和接收方都是同种语言，则一般只涉及到字节序的调整。
	 * 
	 * 对于java和c/c++的通信，则情况就要复杂一些，主要是因为java中没有unsigned类型，
	 * 并且java和c在某些数据类型上的长度不一致。
	 * 
	 * 针对这种情况，整理了java数据类型和网络字节流或字节包
	 * (相当于java的byte数组)之间转换方法。
	 * 
	 * 
	 */
	
	public static final String coding="GB2312";//全局定义，系统其他的会用到
	public DataTransform(){
		
	}
	// 1.将int转为低字节在前，高字节在后的byte数组
	public byte[] IntToByteArray(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}
	public static void IntToByteArray( int n, byte[] array, int offset ){
			array[3+offset] = (byte) (n & 0xff);
        array[2+offset] = (byte) (n >> 8 & 0xff);  
        array[1+offset] = (byte) (n >> 16 & 0xff);  
        array[offset] = (byte) (n >> 24 & 0xff);  
    }    
	
	//2.byte数组转化为int，将低字节在前转为int，高字节在后的byte数组
	public int ByteArrayToInt(byte[] bArr) {
		int n = 0;
		for(int i=0;i<bArr.length&&i<4;i++){
			int left = i*8;
			n+= (bArr[i] << left);
		}
		return n;
		////下面的方法也可以
//		  return    b[3] & 0xff   
//	               | (b[2] & 0xff) << 8   
//	               | (b[1] & 0xff) << 16  
//	               | (b[0] & 0xff) << 24;  
	}
    public  int ByteArrayToInt(byte b[], int offset) {  
        return    b[offset+3] & 0xff   
               | (b[offset+2] & 0xff) << 8   
               | (b[offset+1] & 0xff) << 16  
               | (b[offset] & 0xff) << 24;  
    }  
    
	//3.String和Byte数据的转换，GBK编码格式
	public String ByteArraytoString(byte[] valArr,int maxLen) {
		String result=null;
		int index = 0;
		while(index < valArr.length && index < maxLen) {
			if(valArr[index] == 0) {
				break;
			}
			index++;
		}
		byte[] temp = new byte[index];
		System.arraycopy(valArr, 0, temp, 0, index);
		try {
			result= new String(temp,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	public byte[] StringToByteArray(String str){
		byte[] temp = null;
	    try {
			temp = str.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	
	
	// 将float转为低字节在前，高字节在后的byte数组
	  @SuppressWarnings("unused")
	private static byte[] toLH(float f) {
	    return toLH(Float.floatToRawIntBits(f));
	  }
	  
	//4.Long和Byte数据的转换
	public static byte[] longToBytes(long n) {  
        byte[] b = new byte[8];  
        b[7] = (byte) (n & 0xff);  
        b[6] = (byte) (n >> 8  & 0xff);  
        b[5] = (byte) (n >> 16 & 0xff);  
        b[4] = (byte) (n >> 24 & 0xff);  
        b[3] = (byte) (n >> 32 & 0xff);  
        b[2] = (byte) (n >> 40 & 0xff);  
        b[1] = (byte) (n >> 48 & 0xff);  
        b[0] = (byte) (n >> 56 & 0xff);  
        return b;  
    }  	
	public static void longToBytes( long n, byte[] array, int offset ){  
	        array[7+offset] = (byte) (n & 0xff);  
	        array[6+offset] = (byte) (n >> 8 & 0xff);  
	        array[5+offset] = (byte) (n >> 16 & 0xff);  
	        array[4+offset] = (byte) (n >> 24 & 0xff);  
	        array[3+offset] = (byte) (n >> 32 & 0xff);  
	        array[2+offset] = (byte) (n >> 40 & 0xff);  
	        array[1+offset] = (byte) (n >> 48 & 0xff);  
	        array[0+offset] = (byte) (n >> 56 & 0xff);  
	    }  
	public static long bytesToLong( byte[] array )  
	    {  
	        return ((((long) array[ 0] & 0xff) << 56)  
	              | (((long) array[ 1] & 0xff) << 48)  
	              | (((long) array[ 2] & 0xff) << 40)  
	              | (((long) array[ 3] & 0xff) << 32)  
	              | (((long) array[ 4] & 0xff) << 24)  
	              | (((long) array[ 5] & 0xff) << 16)  
	              | (((long) array[ 6] & 0xff) << 8)   
	              | (((long) array[ 7] & 0xff) << 0));          
	    }     
	public static long bytesToLong( byte[] array, int offset )  
	    {  
	        return ((((long) array[offset + 0] & 0xff) << 56)  
	              | (((long) array[offset + 1] & 0xff) << 48)  
	              | (((long) array[offset + 2] & 0xff) << 40)  
	              | (((long) array[offset + 3] & 0xff) << 32)  
	              | (((long) array[offset + 4] & 0xff) << 24)  
	              | (((long) array[offset + 5] & 0xff) << 16)  
	              | (((long) array[offset + 6] & 0xff) << 8)   
	              | (((long) array[offset + 7] & 0xff) << 0));              
	    }  
	   
	//4.Unsign Int和Byte转换 
	public static byte[] uintToBytes( long n )  
	    {  
	        byte[] b = new byte[4];  
	        b[3] = (byte) (n & 0xff);  
	        b[2] = (byte) (n >> 8 & 0xff);  
	        b[1] = (byte) (n >> 16 & 0xff);  
	        b[0] = (byte) (n >> 24 & 0xff);  
	          
	        return b;  
	    }    
	public static void uintToBytes( long n, byte[] array, int offset ){  
	        array[3+offset] = (byte) (n );  
	        array[2+offset] = (byte) (n >> 8 & 0xff);  
	        array[1+offset] = (byte) (n >> 16 & 0xff);  
	        array[offset]   = (byte) (n >> 24 & 0xff);  
	    }    
	public static long bytesToUint(byte[] array) {    
	        return ((long) (array[3] & 0xff))    
	             | ((long) (array[2] & 0xff)) << 8    
	             | ((long) (array[1] & 0xff)) << 16    
	             | ((long) (array[0] & 0xff)) << 24;    
	    }  
	public static long bytesToUint(byte[] array, int offset) {     
	        return ((long) (array[offset+3] & 0xff))    
	              | ((long) (array[offset+2] & 0xff)) << 8    
	             | ((long) (array[offset+1] & 0xff)) << 16    
	             | ((long) (array[offset]   & 0xff)) << 24;    
	    }  
	
	 //5.Short和Byte的转换
	public static byte[] shortToBytes(short n) {  
	        byte[] b = new byte[2];  
	        b[1] = (byte) ( n       & 0xff);  
	        b[0] = (byte) ((n >> 8) & 0xff);  
	        return b;  
	    }  
    public static void shortToBytes(short n, byte[] array, int offset ) {          
	        array[offset+1] = (byte) ( n       & 0xff);  
	        array[offset] = (byte) ((n >> 8) & 0xff);  
	    }       
	public static short bytesToShort(byte[] b){  
	        return (short)( b[1] & 0xff  
	                      |(b[0] & 0xff) << 8 );   
	    }      
	public static short bytesToShort(byte[] b, int offset){  
	        return (short)( b[offset+1] & 0xff  
	                      |(b[offset]    & 0xff) << 8 );   
	    }  
	
	 //6.Unsign short 和Byte转换 
	public static byte[] ushortToBytes(int n) {  
	        byte[] b = new byte[2];  
	        b[1] = (byte) ( n       & 0xff);  
	        b[0] = (byte) ((n >> 8) & 0xff);  
	        return b;  
	    }      
	public static void ushortToBytes(int n, byte[] array, int offset ) {  
	        array[offset+1] = (byte) ( n       & 0xff);  
	        array[offset] = (byte)   ((n >> 8) & 0xff);  
	    }   
	public static int bytesToUshort(byte b[]) {  
	        return    b[1] & 0xff   
	               | (b[0] & 0xff) << 8;  
	    }      
    public static int bytesToUshort(byte b[], int offset) {  
	        return    b[offset+1] & 0xff   
	               | (b[offset]   & 0xff) << 8;  
	    }       
	public static byte[] ubyteToBytes( int n ){  
	        byte[] b = new byte[1];  
	        b[0] = (byte) (n & 0xff);  
	        return b;  
	    }  	  
    public static void ubyteToBytes( int n, byte[] array, int offset ){  
	        array[0] = (byte) (n & 0xff);  
	    }    
    
    //7.Byte  和 Unsigned Byte
	public static int bytesToUbyte( byte[] array ){              
	        return array[0] & 0xff;  
	    }            
	public static int bytesToUbyte( byte[] array, int offset ){              
	        return array[offset] & 0xff;  
	    }      
	
  // char 类型、 float、double 类型和 byte[] 数组之间的转换关系还需继续研究实现。   
	}  
	  
	 
	  
	
	      
	    
	/*  
	 * 
	   字串 String 转换成整数 int?
	   如何将整数 int 转换成字串 String ? 
   A. 有叁种方法:
	  1). int i = Integer.parseInt([String]); 或 
       i = Integer.parseInt([String],[int radix]);
       2). int i = Integer.valueOf(my_str).intValue(); 
            注: 字串转成 Double, Float, Long 的方法大同小异. 
            
            如何将整数 int 转换成字串 String ? 
   A. 有叁种方法:
       1.) String s = String.valueOf(i);
       2.) String s = Integer.toString(i); 
       3.) String s = "" + i; 
            注: Double, Float, Long 转成字串的方法大同小异. 
	 */
	/*
	 * char->string      Character static String to String (char c) 
	 * string->Short      Short static Short parseShort(String s) 
       Short->String      Short static String toString(Short s) 
       String->Integer    Integer static int parseInt(String s)
       Integer->String    Integer static String tostring(int i) 
       String->Long       Long static long parseLong(String s) 
       Long->String       Long static String toString(Long i) 
       String->Float      Float static float parseFloat(String s) 
       Float->String      Float static String toString(float f) 
       String->Double     Double static double parseDouble(String s)
       Double->String     Double static String toString(Double)
       
      数据类型
      基本类型有以下四种：
   int长度数据类型有：byte(8bits)、short(16bits)、int(32bits)、long(64bits)、
   float长度数据类型有：单精度（32bits float）、双精度（64bits double）
   boolean类型变量的取值有：ture、false
   char数据类型有：unicode字符,16位
     对应的类类型：Integer、Float、Boolean、Character、Double、Short、Byte、Long

	 */
	

