/*
Copyright 2009 David Revell

This file is part of SwiFTP.

SwiFTP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SwiFTP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
*/

package me.smart.flyme.wifi.cmd;

import java.net.InetAddress;

import me.smart.flyme.wifi.foregin.SessionThread;

public class CmdPASV extends FtpCmd implements Runnable
{
	//public static final String message = "TEMPLATE!!";
	
	public CmdPASV(SessionThread sessionThread, String input)
	{
			super(sessionThread, CmdPASV.class.toString());
	}
	
	public void run()
	{
		String cantOpen = "502 Couldn't open a port\r\n";
		//myLog.l(Log.DEBUG, "PASV running");
		int port;
		if((port = sessionThread.onPasv()) == 0)
		{
				sessionThread.writeString(cantOpen);
				return;
		}
		InetAddress addr = sessionThread.getDataSocketPasvIp();
		
		if(addr == null)
		{
				sessionThread.writeString(cantOpen);
				return;
		}
		if(port < 1)
		{
				sessionThread.writeString(cantOpen);
				return;
		}
		StringBuilder response = new StringBuilder("227 Entering Passive Mode (");
		// Output our IP address in the format xxx,xxx,xxx,xxx
		response.append(addr.getHostAddress().replace('.', ','));
		response.append(",");
		
		// Output our port in the format p1,p2 where port=p1*256+p2 
		response.append(port / 256);
		response.append(",");
		response.append(port % 256);
		response.append(").\r\n");
		String responseString = response.toString();
		sessionThread.writeString(responseString);
	}
}
