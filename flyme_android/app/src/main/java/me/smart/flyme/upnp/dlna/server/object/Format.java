/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File: Format.java
*
*	Revision;
*
*	01/12/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object;

import java.io.File;

public interface Format
{
	public abstract FormatObject createObject(File file);
	public abstract boolean equals(File file);
	public abstract String getMimeType();
	public abstract String getMediaClass();
}



