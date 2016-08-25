
package me.smart.flyme.wifi.foregin;

/**
 * author：wypx on 2015/12/11 21:42
 * blog：smarting.me
 */
//FTP文件信息
public class FileInfo
{
    public String   fileName;       //文件名
    public String   filePath;       //文件路径
    public long     fileSize;       //文件大小
    public boolean  IsDir;          //是否是文件夹
    public int      Count;          //文件数量
    public long     ModifiedDate;   //修改时间
    public boolean  Selected;       //是否选择
    public boolean  canRead;        //是否可读
    public boolean  canWrite;       //是否可写
    public boolean  isHidden;       //是否隐藏
    public long     dbId;           // id in the database, if is from database
}
