package me.smart.flyme.utils.stream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @ClassName: StreamUtils 
 * @Description: TODO(转化stream的工具) 
 * @author amor smarting.me
 * @date 2015-11-8 下午10:39:34 
 *  
 */
public class StreamUtils 
{
	//把String转化为Inputstream
	public InputStream StrToiStream(String str)
	{
		   ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		   return stream;
	}
	//把Inpiutstream转化为String
	public String iStreamToStr(InputStream is)
	{
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));
		   StringBuffer buffer = new StringBuffer();
		   String line = "";
		   try 
		   {
			   while ((line = in.readLine()) != null)
			   {
			     buffer.append(line);
			   }
		   } catch (IOException e)
		   {	
			   e.printStackTrace();
		   }
		   return buffer.toString();
		}
	//此两种方法上面一种更快，但是比较耗内存，后者速度慢，耗资源少
	public String iStream2String(InputStream is)
	{
		String all_content=null;
        try 
        {
        	all_content =new String();
        	InputStream ins = null ;//"获取的输入流";
        	ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        	byte[] str_b = new byte[1024];
        	int i = -1;
        	while ((i=ins.read(str_b)) > 0)
        	{
        		outputstream.write(str_b,0,i);
        	}
       	 all_content = outputstream.toString();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        return all_content;
	}
	
	//InputStream 转化为 File
	public void iStreamtofile(InputStream ins,File file)
	{
		OutputStream os = null;
		try
		{
			os = new FileOutputStream(file);
		} 
		catch (FileNotFoundException e)
		{			
			e.printStackTrace();
		}
		
		
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		try 
		{
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1)
			{
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		
		
	}
}
