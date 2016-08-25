package me.smart.flyme.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewHolderInfo {

	
	//获得文件的时间
		public String getFileTime(long filetime) {
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
			String ftime =  formatter.format(new Date(filetime));
			return ftime;
		}
	//获得文件的大小
		public static String getFileSize(long filesize) {
			//DecimalFormat 是 NumberFormat 的一个具体子类，用于格式化十进制数字。
			/*
			 * 符号含义：
			 * 0 一个数字        # 一个数字，不包括 0    . 小数的分隔符的占位符          , 分组分隔符的占位符           ; 分隔格式。 
	           - 缺省负数前缀        % 乘以 100 和作为百分比显示           
	           ? 乘以 1000 和作为千进制货币符显示；用货币符号代替；如果双写，用国际货币符号代替。
	                               如果出现在一个模式中，用货币十进制分隔符代 替十进制分隔符。 
	           X 前缀或后缀中使用的任何其它字符，用来引用前缀或后缀中的特殊字符。 
	           ----------------------example------------------------------
	           DecimalFormat df1 = new DecimalFormat("0.0");  //12.3 
	           DecimalFormat df2 = new DecimalFormat("#.#"); //12.3 
	           DecimalFormat df3 = new DecimalFormat("000.000"); //012.340 
	           DecimalFormat df4 = new DecimalFormat("###.###");//12.34     
	           System.out.println(df1.format(12.34)); //输出结果     
			 */
			DecimalFormat df = new DecimalFormat("#.00");
			StringBuffer mstrbuf = new StringBuffer();

			if (filesize < 1024) {
				mstrbuf.append(filesize);
				mstrbuf.append(" B");
			} else if (filesize < 1048576) {
				mstrbuf.append(df.format((double)filesize/1024));
				mstrbuf.append(" K");			
			} else if (filesize < 1073741824) {
				mstrbuf.append(df.format((double)filesize/1048576));
				mstrbuf.append(" M");			
			} else {
				mstrbuf.append(df.format((double)filesize/1073741824));
				mstrbuf.append(" G");
			}

			df = null;

			return mstrbuf.toString();
		}


}
