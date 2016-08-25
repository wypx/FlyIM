/******************************************************************
*
*	MediaPlayer for CyberLink
*
*	Copyright (C) Satoshi Konno 2005
*
*	File : BrowseAction.java
*
*	09/26/05
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.player.action;

import java.io.InputStream;

import me.smart.flyme.upnp.dlna.server.object.item.ItemNode;

public class BrowseResultNode extends ItemNode
{
	////////////////////////////////////////////////
	// Constroctor
	////////////////////////////////////////////////

	public BrowseResultNode()
	{
	}

	////////////////////////////////////////////////
	// Abstract methods
	////////////////////////////////////////////////

	public long getContentLength()
	{
		return 0;
	}

	public InputStream getContentInputStream()
	{
		return null;
	}

	public String getMimeType()
	{
		return "*/*";
	}
}
