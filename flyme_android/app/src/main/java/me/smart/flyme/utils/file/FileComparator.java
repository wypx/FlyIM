package me.smart.flyme.utils.file;

import java.io.File;
import java.util.Comparator;

/** 对集合对象或数组对象进行排序
		1.Comparatable接口和Comparator接口
				Comparable接口的排序依据：     Arrays.sort(intArray)
				单元素类排序:直接排序：指Java中已经实现Comparable接口的类
		                常用的有：Integer、Float、Double、String、Character等）
			 多元素类排序：必须实现Comparable<T>接口，且T为该多元素类的类名
    2.Comparator接口的排序依据：      在程序中指定排序依据元素（不管是单元素，还是多元素）
 */
public class FileComparator implements Comparator<File>
{

	public int compare(File file1, File file2)
	{
		// 文件夹排在前面
		if (file1.isDirectory() && !file2.isDirectory())
		{
			return -1000;
		}
		else if (!file1.isDirectory() && file2.isDirectory())
		{
			return 1000;
		}
		// 相同类型按名称排列
		return (file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
	}
}
/*
 *   Comparatable接口：
    优点是，对于单元素集合可以实现直接自然排序；
    缺点是，对于多元素排序，它的排序依据元素是固定的（compareTo()抽象方法只能实现�?次），因此排序方式固定�??
     Comparator接口：
   优点是，元素的排序依据元素是可变的，因为可以定义N多个外部类，每个外部类实现一种排序�?�在不同�?求下，�?�择不同的排序�??
   缺点是，无论是多元素，还是单元素，都必须自己创建�?个外部类来实现排序�??
         以在实际运用当中，可以用Comparable的compareTo()方法来定义默认排序方式，用Comparator定义其他排序方式�?
 */

