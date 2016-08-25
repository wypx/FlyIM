package me.smart.flyme.utils.file;

	//FileFilter的使用（搜索指定后缀名的文件）
/**
 *  FileFilter 和 FilenameFilter 两个接口是在文件搜索中常用的
 *
 *  方法:目录中创建20000 个txt,分别用实现了以上两个接口的类来搜索这个目录中的所有txt文件查看时间。
 *  结果：FilenameFilter性能好于FileFilter。
 *
 *  public File[] listFiles(FileFilter filter)
 *  返回抽象路径名数组,路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录。
 *  除了返回数组中的路径名必须满足过滤器外，此方法的行为与 listFiles() 方法相同。
 *  如果给定 filter 为 null，则接受所有路径名。否则，当且仅当在路径名上调用过滤器的.
 *
 *  FileFilter.accept(java.io.File) 方法返回 true 时，该路径名才满足过滤器。
 *  boolean accept(File pathname)
 *  测试指定抽象路径名是否应该包含在某个路径名列表中。
 */


import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter

{
		public String  dat;     //定义的扩展名
		public String getDat()
		{
				return dat;
		}
		public void setDat(String dat)
		{
				this.dat = dat;
		}
		public FileFilter(String dat)
		{
				this.dat = dat;
		}
		/**
		 * 过滤的方法
		 * @return 是否符合指定文件
		 */
		@Override
		public boolean accept(File dir, String filename)
		{
				//对获取的文件全名进行拆分
				String[] arrName = filename.split("\\.");
				if(arrName[1].equalsIgnoreCase( this.getDat()))
				{
						return true;
				}
				return false;

		}
}
