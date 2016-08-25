/******************************************************************
*
*	CyberSQL for Java
*
*	Copyright (C) Satoshi Konno 2004
*
*	File: MySQL.java
*
*	Revision;
*
*	02/10/04
*		- first revision.
*	
******************************************************************/

package me.smart.flyme.upnp.dlna.sql;

import org.cybergarage.util.Debug;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL extends Database
{
	public MySQL()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception e)
		{
			Debug.warning(e);
		}
	}

	public boolean open(String host, String dbname, String user, String passwd)
	{	
		try
		{
			String dburl = "jdbc:mysql://" + host + "/" + dbname;
			Connection con = DriverManager.getConnection(dburl, user, passwd);
			setConnection(con);
		}
		catch (Exception e)
		{
			Debug.warning(e);
			return false;
		}
		
		return true;
	}

}
