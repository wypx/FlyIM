/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003
**
*	File: ItemNodeList.java
*
*	Revision;
*
*	11/11/03
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object.item;

import java.util.Vector;

public class ResourceNodeList extends Vector 
{
	public ResourceNodeList() 
	{
	}
	
	public ResourceNode getResourceNode(int n)
	{
		return (ResourceNode)get(n);
	}
}


