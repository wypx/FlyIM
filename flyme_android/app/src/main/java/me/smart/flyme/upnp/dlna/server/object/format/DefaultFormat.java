/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File : DefaultPlugIn.java
*
*	Revision:
*
*	02/12/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object.format;

import org.cybergarage.xml.AttributeList;

import java.io.File;

import me.smart.flyme.upnp.dlna.server.object.Format;
import me.smart.flyme.upnp.dlna.server.object.FormatObject;


public class DefaultFormat implements Format, FormatObject
{
	////////////////////////////////////////////////
	// Constroctor
	////////////////////////////////////////////////
	
	public DefaultFormat()
	{
	}
	
	////////////////////////////////////////////////
	// Abstract Methods
	////////////////////////////////////////////////
	
	public boolean equals(File file)
	{
		return true;
	}
	
	public FormatObject createObject(File file)
	{
		return new DefaultFormat();
	}
	
	public String getMimeType()
	{
		return "*/*";
	}

	public String getMediaClass()
	{
		return "object.item";
	}
	
	public AttributeList getAttributeList()
	{
		return new AttributeList();
	}
	
	public String getTitle()
	{
		return "";
	}
	
	public String getCreator()
	{
		return "";
	}
}

