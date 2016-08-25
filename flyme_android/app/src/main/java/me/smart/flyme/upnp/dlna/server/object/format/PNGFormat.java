/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File : PNGPlugIn.java
*
*	Revision:
*
*	01/25/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object.format;

import java.io.File;

import me.smart.flyme.upnp.dlna.server.object.FormatObject;


public class PNGFormat extends ImageIOFormat
{
	////////////////////////////////////////////////
	// Constroctor
	////////////////////////////////////////////////
	
	public PNGFormat()
	{
	}
	
	public PNGFormat(File file)
	{
		super(file);
	}

	////////////////////////////////////////////////
	// Abstract Methods
	////////////////////////////////////////////////
	
	public boolean equals(File file)
	{
		String headerID = Header.getIDString(file, 1, 3);
		if (headerID.startsWith("PNG") == true)
			return true;		
		return false;
	}
	
	public FormatObject createObject(File file)
	{
		return new PNGFormat(file);
	}
	
	public String getMimeType()
	{
		return "image/png";
	}

}

