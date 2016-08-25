/******************************************************************
*
*	MediaServer for CyberLink
*
*	Copyright (C) Satoshi Konno 2003-2004
*
*	File: IdSearchCap.java
*
*	Revision;
*
*	08/16/04
*		- first revision.
*
******************************************************************/

package me.smart.flyme.upnp.dlna.server.object.search;

import me.smart.flyme.upnp.dlna.server.object.ContentNode;
import me.smart.flyme.upnp.dlna.server.object.SearchCap;
import me.smart.flyme.upnp.dlna.server.object.SearchCriteria;

public class IdSearchCap implements SearchCap
{
	public IdSearchCap() 
	{
	}
	
	public String getPropertyName() 
	{
		return SearchCriteria.ID;
	}

	public boolean compare(SearchCriteria searchCri, ContentNode conNode)
	{
		String searchCriID = searchCri.getValue();
		String conID = conNode.getID();
		if (searchCriID == null|| conID == null)
				return false;
		if (searchCri.isEQ() == true)
			return (searchCriID.compareTo(conID) == 0) ? true : false;
		return false;
		
	}
}

