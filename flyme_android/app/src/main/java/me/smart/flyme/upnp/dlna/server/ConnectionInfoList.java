/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File: ConnectionInfoList.java
*
*	Revision;
*
*	06/19/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server;

import java.util.Vector;

public class ConnectionInfoList extends Vector 
{

	public ConnectionInfoList() 
	{
	}
	public ConnectionInfo getConnectionInfo(int n)
	{
		return (ConnectionInfo)get(n);
	}


}

