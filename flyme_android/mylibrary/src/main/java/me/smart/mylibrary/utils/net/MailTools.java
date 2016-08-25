/**   
* @Title: MailTools.java 
* @Package src.wrap 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author amor smarting.me  
* @date 2015-11-14 ����8:43:26 
* @version V1.0   
*/
package me.smart.mylibrary.utils.net;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import me.smart.mylibrary.utils.encoder.Base64Utils;


/**
 * @ClassName: MailTools
 * @Description: TODO(封装发送邮件相关的Item)
 * @author amor smarting.me
 * @date 2015-11-14 下午8:43:26
 *
 *  用法:
 *  	MailTools mailTools = new MailTools();
mailTools.setProperties(2, 0, true);
mailTools.setSession(true);
mailTools.setMimeMultipart("这是测试邮件正文,芝麻猪!");//amor@smarting.me
mailTools.setMimeMessage("14789983604@163.com", "1067939689@qq.com", "674287450@qq.com", "问题反馈");
mailTools.sendEmail("14789983604@163.com", "tt@123000");
 */

public class MailTools
{
		//发件人真实的账户名
		private String username = "14789983604@163.com";
		//发件人密码
		private String password = "tt@123000";

		//接收方邮件显示发送方名称
		private String from = "amor@smarting.me";

		//收件人的邮件地址,必须是真实地址
		private String to = "1067939689@qq.com";

		//抄送邮箱地址
		private String copyto = "674287450@qq.com";

		//邮件标题
		private String subject = "邮件测试";

		//smtp服务器 (pop3等)
		private String host = "smtp.163.com";

		// 系统属性,即SMTP服务器
		public Properties props;

		//邮件会话对象
		public Session session;

		//邮件对象
		public MimeMessage mimeMsg;

		public Multipart multipart; // 对象用于封装邮件内容

		private String HOST[] =
						{
										"smtp.qq.com",//QQ的邮箱服务器 465端口
										"pop.qq.com",
										"smtp.163.com",
										"pop.163.com",
										"smtp.gmail.com",//Gmail邮箱
										"pop.gmail.com    ",
										"pop.126.com",
										"smtp.126.com",

										//yahoo邮箱  smtp.mail.yahoo.com.cn
										//Sohu邮箱    smtp.sohu.com
						};

		//Set和Get函数


		public MailTools()
		{
				super();

		}

		// 1.设置系统属性 :授权,用户名和密码的校验
		public void setProperties(int HostId,int HostType, boolean isAuth)
		{
				if(null == props)
				{
						props = System.getProperties();
				}
				if(HostId >= 0 && HostId <=5)
				{
						this.host = HOST[HostId];//序号判断是哪个服务器
						if(0 == HostType)//smtp
						{
								props.put("mail.smtp.host", host);
						}
						else if(1 == HostType)
						{
								props.put("mail.pop.host", host);//猜测
						}

				}
				//是否需要验证
				if(isAuth)
				{
						props.put("mail.smtp.auth", "true");
				}
				else
				{
						props.put("mail.smtp.auth", "false");
				}

		}

		//2.获取邮件会话对象
		public void setSession(boolean isDebug)
		{
				if(null == session)
				{
						//用刚刚设置好的props对象构建一个session
						session = Session.getDefaultInstance(props);
				}
				if(isDebug)
				{
						//在发送邮件的过程中在console处显示过程信息，供调试使用
						//用(你可以在控制台(console)上看到发送邮件的过程)
						session.setDebug(true);
				}

		}

		//3.向multipart对象中添加邮件的各个部分内容,包括文本内容和附件
		public void setMimeMultipart(String MainText)
		{
				if (null == multipart)
				{
						multipart = new MimeMultipart();
				}
				// 设置邮件的文本内容
				BodyPart contentPart = new MimeBodyPart();
				try
				{
						contentPart.setContent("" + MainText, "text/html;charset=UTF-8");
						multipart.addBodyPart(contentPart);



				}
				catch (MessagingException e)
				{
						e.printStackTrace();
				}


		}
		//3.向multipart对象中添加邮件的各个部分内容,包括文本内容和附件
		@SuppressWarnings("static-access")
		public void setMimeMultipartWithAttach(String MainText,String filepath) throws UnsupportedEncodingException
		{
				if (null == multipart)
				{
						multipart = new MimeMultipart();
				}
				// 设置邮件的文本内容
				BodyPart contentPart = new MimeBodyPart();
				try
				{
						contentPart.setContent("" + MainText, "text/html;charset=UTF-8");
						multipart.addBodyPart(contentPart);
						//**************************************
						//构造MimeMessage 并设定基本的值
						String content = "";//邮件正文
						String filename = "";//附件文件名
						MimeMessage msg = new MimeMessage(session);
						msg.setFrom(new InternetAddress(from));
						InternetAddress[] address={new InternetAddress(to)};
						msg.setRecipients(Message.RecipientType.TO,address);
						subject = MimeUtility.encodeText(new String(subject.getBytes(), "GB2312"), "GB2312", "B");
						msg.setSubject(subject);

						//构造Multipart
						Multipart mp = new MimeMultipart();

						//向Multipart添加正文
						MimeBodyPart mbpContent = new MimeBodyPart();
						mbpContent.setText(content);
						//向MimeMessage添加（Multipart代表正文）
						mp.addBodyPart(mbpContent);
						Vector file = new Vector();//附件文件集合
						//向Multipart添加附件
						Enumeration efile=file.elements();
						while(efile.hasMoreElements())
						{

								MimeBodyPart mbpFile = new MimeBodyPart();
								filename=efile.nextElement().toString();
								FileDataSource fds = new FileDataSource(filename);
								mbpFile.setDataHandler(new DataHandler(fds));
								mbpFile.setFileName(fds.getName());
								//向MimeMessage添加（Multipart代表附件）
								mp.addBodyPart(mbpFile);

						}

						file.removeAllElements();
						//向Multipart添加MimeMessage
						msg.setContent(mp);
						msg.setSentDate(new Date());
						//发送邮件
						Transport.send(msg);
						//****************************************************
						// 添加附件
						DataSource source = new FileDataSource(filepath);
						// 添加附件的内容
						contentPart.setDataHandler(new DataHandler(source));
						// 添加附件的标题
						// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
						Base64Utils enc = new Base64Utils();

						contentPart.setFileName("=?GBK?B?"+ enc.encode(filepath.getBytes()) + "?=");
						multipart.addBodyPart(contentPart);

				}
				catch (MessagingException e)
				{
						e.printStackTrace();
				}


		}
		//4.创建MIME邮件对象
		public void setMimeMessage(String From,String To,String Copy,String Sub)
		{
				this.from = From;
				this.to   = To;
				this.copyto = Copy;
				this.subject = Sub;
				//用session为参数定义消息对象
				if(null == mimeMsg)
				{
						mimeMsg = new MimeMessage(session);
				}
				try
				{
						mimeMsg.setFrom(new InternetAddress(from));//发件人地址
		/*
			InternetAddress[] addressTo = new InternetAddress[_to.length];
			for (int i = 0; i < _to.length; i++)
			{
				addressTo[i] = new InternetAddress(_to[i]);
			}
		*/
						mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));//收件人地址
						mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto));
						mimeMsg.setSubject(subject);//标题

						mimeMsg.setContent(multipart);  //将multipart对象放到message中
						mimeMsg.saveChanges();  // 保存邮件

				}
				catch (AddressException e)
				{
						e.printStackTrace();
				}
				catch (MessagingException e)
				{
						e.printStackTrace();
				}

		}

		//5.发送邮件
		public void sendEmail(String user, String pass)
		{
				this.username = user;
				this.password = pass;
				setMailcapCommandMap();//注意:必须设置
				// 方法一：使用transport对象发送邮件
				Transport transport;
				try
				{
						transport = session.getTransport("smtp");
						// 发送邮件
						transport.connect(host, username, password);// 连接服务器的邮箱
						transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());// 把邮件发送出去
						transport.close();

						// 方法二：使用Transport类静态方法发送邮件
						// 直接发送，message通过已经授权的Session生成
						// Transport.send(mimeMsg);
				}
				catch (NoSuchProviderException e)
				{
						e.printStackTrace();
				}
				catch (MessagingException e)
				{
						e.printStackTrace();
				}
		}

		/**
		 * @Title: setMailcap
		 * @Description: TODO(解决web发送邮件异常情况(垃圾邮件),必须在发送前调用 )
		 * @param
		 * @return JavaMail发送HTML邮件 no object DCH for MIME type multipart/mixed异常的异常
		 * @throws
		 */
		private void setMailcapCommandMap()
		{
				MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
				mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
				mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
				mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
				mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
				mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
				CommandMap.setDefaultCommandMap(mc);
		}

}
