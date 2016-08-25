/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File: SortCap.java
*
*	Revision;
*
*	02/03/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object;

public interface SortCap
{
	public abstract String getType();
	public abstract int compare(ContentNode conNode1, ContentNode conNode2);
}



