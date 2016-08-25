/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File : MPEGPlugIn.java
*
*	Revision:
*
*	02/02/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object.format;

import org.cybergarage.util.Debug;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.AttributeList;

import java.io.File;

import me.smart.flyme.upnp.dlna.server.object.Format;
import me.smart.flyme.upnp.dlna.server.object.FormatObject;
import me.smart.flyme.upnp.dlna.server.object.item.ItemNode;


public class MPEGFormat implements Format, FormatObject
{
	////////////////////////////////////////////////
	// Member
	////////////////////////////////////////////////

	private File mpegFile;
		
	////////////////////////////////////////////////
	// Constroctor
	////////////////////////////////////////////////
	
	public MPEGFormat()
	{
	}
	
	public MPEGFormat(File file)
	{
		mpegFile = file;
	}

	////////////////////////////////////////////////
	// Abstract Methods
	////////////////////////////////////////////////
	
	public boolean equals(File file)
	{
		String ext = Header.getSuffix(file);
		if (ext == null)
			return false;
		if (ext.startsWith("mpeg") || ext.startsWith("mpg"))
			return true;
		return false;
	}
	
	public FormatObject createObject(File file)
	{
		return new MPEGFormat(file);
	}
	
	public String getMimeType()
	{
		return "video/mpeg";
	}

	public String getMediaClass()
	{
		return "object.item.videoItem.movie";
	}
	
	public AttributeList getAttributeList()
	{
		AttributeList attrList = new AttributeList();
		
		try {
			// Size 
			long fsize = mpegFile.length();
			Attribute sizeStr = new Attribute(ItemNode.SIZE, Long.toString(fsize));
			attrList.add(sizeStr);
		}
		catch (Exception e) {
			Debug.warning(e);
		}
		
		return attrList;	
	}
	
	public String getTitle()
	{
		String fname = mpegFile.getName();
		int idx = fname.lastIndexOf(".");
		if (idx < 0)
			return "";
		String title = fname.substring(0, idx);
		return title;
	}
	
	public String getCreator()
	{
		return "";
	}
}

