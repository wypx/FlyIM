/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File: SortCriterionList.java
*
*	Revision:
*
*	04/06/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object;

import java.util.Vector;

public class SortCriterionList extends Vector 
{
	public SortCriterionList() 
	{
	}
	
	public String getSortCriterion(int n)
	{
		return (String)get(n);
	}
}

