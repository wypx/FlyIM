package me.smart.flyme.upnp.ctlpt;

import android.util.Log;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.UPnPStatus;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * author：wypx on 2016/6/14 22:14
 * blog：smarting.me
 */
public class RemoteCtrl extends ControlPoint implements NotifyListener, EventListener, SearchResponseListener
{
		public  String LocaltionUrl = null;
		private final static String TV_DEVICE_TYPE = "urn:schemas-upnp-org:device:fileserver:2";
		private final static String TV_SERVICE_TYPE = "urn:schemas-upnp-org:service:remotecontrol:1";
		private  static int isFirst = 0;

		public  List<Device> getDevices()
		{
				return devices;
		}

		private  List<Device> devices = new ArrayList<Device>();

		public RemoteCtrl()
		{
				addNotifyListener(this);
				addSearchResponseListener(this);
				addEventListener(this);
				this.search();
		}

		@Override
		public void eventNotifyReceived(String uuid, long seq, String name, String value)
		{
//				Log.e("tt","event notify : uuid = " + uuid + ", seq = " + seq + ", " +
//								"name = " + name + ", value =" + value);
		}

		@Override
		public void deviceNotifyReceived(SSDPPacket ssdpPacket)
		{

				System.out.println(ssdpPacket.toString());
				Log.e("tt","数据包:"+ssdpPacket.toString());

				if (ssdpPacket.isDiscover() == true)
				{
						String st = ssdpPacket.getST();
				}
				else if (ssdpPacket.isAlive() == true)
				{
						String usn = ssdpPacket.getUSN();
						String nt = ssdpPacket.getNT();
						String url = ssdpPacket.getLocation();

//						Log.e("tt","deviceNotifyReceived:usn------------"+usn);
//						Log.e("tt","deviceNotifyReceived:nt-------------"+nt);
//						Log.e("tt","deviceNotifyReceived:url------------"+url);
				}
				else if (ssdpPacket.isByeBye() == true)
				{
						String usn = ssdpPacket.getUSN();
						String nt = ssdpPacket.getNT();

				}
				DeviceList devList = this.getDeviceList();
				int devCnt = devList.size();
				Log.e("tt","更新设备数量:----"+devCnt);
				devices.clear();
				for (int n = 0; n < devCnt; n++) {

						Device dev = devList.getDevice(n);
						devices.add(dev);
				}
				//	updateDeviceList();
		}

		@Override
		public void deviceSearchResponseReceived(SSDPPacket ssdpPacket)
		{
				String uuid = ssdpPacket.getUSN();
				String st = ssdpPacket.getST();
				String url = ssdpPacket.getLocation();
				String ip  = ssdpPacket.getLocalAddress();
				String remoteIp = ssdpPacket.getRemoteAddress();
				int remoetePort = ssdpPacket.getRemotePort();
				long timestamp = ssdpPacket.getTimeStamp();
				String packtet = ssdpPacket.toString();

				String host = ssdpPacket.getHost();
				String cachect = ssdpPacket.getCacheControl();
				String location = ssdpPacket.getLocation();
				String Man = ssdpPacket.getMAN();
				String NT = ssdpPacket.getNT();
				String NTS= ssdpPacket.getNTS();
				String Server =ssdpPacket.getServer();
				int MX= ssdpPacket.getMX();

				boolean isRootDev = ssdpPacket.isRootDevice();
				boolean isDiscover = ssdpPacket.isDiscover();
				boolean isAlive = ssdpPacket.isAlive();
				boolean isByeBye = ssdpPacket.isByeBye();
				int LeaseTime = ssdpPacket.getLeaseTime();


				//	updateDeviceList();
//				Device gateWayDev = this.getDevice("urn:schemas-upnp-org:device:InternetGatewayDevice:1");
//				Log.e("tt","InterfaceAddress: "+gateWayDev.getInterfaceAddress());
//				Log.e("tt","PresentationURL: "+gateWayDev.getPresentationURL());
//				Log.e("tt","Location: "+gateWayDev.getLocation());
//
//				Log.e("tt","getLocalAddress: "+gateWayDev.getSSDPPacket().getLocalAddress());
//				Log.e("tt","getRemotePort: "+gateWayDev.getSSDPPacket().getRemotePort());
//				Log.e("tt","getRemoteAddress: "+gateWayDev.getSSDPPacket().getRemoteAddress());
//				Log.e("tt","getHost: "+gateWayDev.getSSDPPacket().getHost());
//				Log.e("tt","getServer: "+gateWayDev.getSSDPPacket().getServer());
//
//
//
////        http://192.168.31.170:4004/WANIPCn.xml
////				String regEx="[^0-9]";
//
//
//				int index = 0;
//				String location2 = gateWayDev.getLocation();
//
//			  String rootStr = null;
//
//				if(location2.startsWith("http"))
//				{
//						int index2 = location2.indexOf("/",7);
//						rootStr = location2.substring(0,index2);
//						Log.e("tt","rootStr1: "+rootStr);
//				}
//				else
//				{
//						int index2 = location2.indexOf("/",0);
//						rootStr = location2.substring(0,index2);
//						Log.e("tt","rootStr2: "+rootStr);
//				}
//
//
//				//控制点的API
//				boolean hasDevice =this.hasDevice("urn:schemas-upnp-org:device:WANConnectionDevice:1");
//				Device dev4 = this.getDevice("urn:schemas-upnp-org:device:WANConnectionDevice:1");
//
//
//
//				if(dev4 == null)
//				{
//						Log.e("tt","getDevice:WANConnectionDevice null");
//						return ;
//				}
//				Log.e("tt","##############设备API#######################");
//				Log.e("tt","UUID: "+dev4.getUUID());
//				Log.e("tt","httpport: "+dev4.getHTTPPort());
//				Log.e("tt","SSDPPort: "+dev4.getSSDPPort());
//				Log.e("tt","InterfaceAddress: "+dev4.getInterfaceAddress());
//				Log.e("tt","SSDPIPv4MulticastAddress: "+dev4.getSSDPIPv4MulticastAddress());
//
//				Log.e("tt","DescriptionFilePath: "+dev4.getDescriptionFilePath());
//				Log.e("tt","Location: "+dev4.getLocation());
//				Log.e("tt","isWirelessMode: "+dev4.isWirelessMode());
//				Log.e("tt","DeviceType: "+dev4.getDeviceType());
//				Log.e("tt","FriendlyName: "+dev4.getFriendlyName());
//				Log.e("tt","Manufacture: "+dev4.getManufacture());
//				Log.e("tt","ManufactureURL: "+dev4.getManufactureURL());
//				Log.e("tt","ModelDescription: "+dev4.getModelDescription());
//				Log.e("tt","ModelName: "+dev4.getModelName());
//				Log.e("tt","ModelNumber: "+dev4.getModelNumber());
//				Log.e("tt","SerialNumber: "+dev4.getSerialNumber());
//				Log.e("tt","UDN: "+dev4.getUDN());
//				Log.e("tt","UPC: "+dev4.getUPC());
//				Log.e("tt","Manufacture: "+dev4.getManufacture());
//				Log.e("tt","Manufacture: "+dev4.getManufacture());
//				boolean hasUDN = dev4.hasUDN();
//
//				Service service = dev4.getService("urn:schemas-upnp-org:service:WANIPConnection:1");
//
//				String ControlURL = service.getControlURL();
//				String EventSubURL = service.getEventSubURL();
//				String DescriptionURL = service.getDescriptionURL();
//				Log.e("tt","##############设备API#######################");
//
//				String aboslueteSCPDURL = null;
//				if(!service.getSCPDURL().contains(rootStr))
//						aboslueteSCPDURL = rootStr + service.getSCPDURL();
//				else
//						aboslueteSCPDURL = service.getSCPDURL();
//
//				Log.e("tt","aboslueteSCPDURL: "+aboslueteSCPDURL);
//
//					if(isFirst == 0)
//					{
//							service.setSCPDURL(aboslueteSCPDURL);
//							Log.e("tt","SCPDURL set: "+service.getSCPDURL());
//					}
//				 isFirst = 1;
//
//				Log.e("tt","-------------------AddPortMapping-----------------------");
//
//				Action addPortAct = service.getAction("AddPortMapping");
//
//				if (addPortAct == null)
//				{
//						Log.e("tt2","AddPortMapping is not found");
//				}
//				//dev4.getInterfaceAddress()
//				addPortAct.setArgumentValue("NewRemoteHost", "");
//				addPortAct.setArgumentValue("NewProtocol", "UDP");
//				addPortAct.setArgumentValue("NewPortMappingDescription","Smart" );
//				addPortAct.setArgumentValue("NewExternalPort", 9991);
//				addPortAct.setArgumentValue("NewInternalClient", "192.168.31.189");
//				addPortAct.setArgumentValue("NewInternalPort", "9999");
//				addPortAct.setArgumentValue("NewEnabled", 1);
//				addPortAct.setArgumentValue("NewLeaseDuration", 0);
//
//
//				if (addPortAct.postControlAction() == true)
//				{
//						UPnPStatus upnpStat = addPortAct.getStatus();
//						Log.e("tt","addPortMapping succ");
//				}
//				else
//				{
//						UPnPStatus upnpStat = addPortAct.getStatus();
//						Log.e("tt","addPortMapping failed");
//				}
//
//				Log.e("tt","-------------------AddPortMapping  end-----------------------");
//				//service.setSCPDURL("");
//				Log.e("tt","SCPDURL set null: "+service.getSCPDURL());
//				Log.e("tt","#####################################");

				////////////////////////////////
				DeviceList devList = this.getDeviceList();
				int devCnt = devList.size();
				Log.e("tt","搜索到设备数量:----"+devCnt);
				devices.clear();
				for (int n = 0; n < devCnt; n++)
				{
						Device dev = devList.getDevice(n);
						devices.add(dev);
						boolean isRootDevice = dev.isRootDevice();

						Log.e("tt","##############设备API#######################");
						int BootId = dev.getBootId();
						int ConfigId = dev.getConfigId();
						Device rootDev = dev.getRootDevice();
						Device parentDev = dev.getParentDevice();
						int LeaseTime2 = dev.getLeaseTime();
						long TimeStamp = dev.getTimeStamp();
						long ElapsedTime = dev.getElapsedTime();
						boolean isExpired = dev.isExpired();
						String URLBase = dev.getURLBase();
						Log.e("tt","URLBase-----------------------"+URLBase);
						boolean isRunning = dev.isRunning();

						String PresentationURL = dev.getPresentationURL();
						DeviceList deviceList = dev.getDeviceList();
//						String name = null;
//						boolean isDevice = dev.isDevice(name);
//						Device dev3 = dev.getDevice(name);

						if (dev.isDeviceType("urn:schemas-upnp-org:device:fileserver:2") == true
										|| dev.isDeviceType("urn:schemas-upnp-org:device:clock:1") == true)
						{
								Log.e("tt","friend--------"+dev.getFriendlyName()+"--UDN:-"+dev.getUDN());
						}
						Device dev2 ;
						if (dev.isDeviceType("urn:schemas-upnp-org:device:InternetGatewayDevice:1") == true)
						{
								Log.e("tt","friend--------"+dev.getFriendlyName()+"--UDN:-"+dev.getUDN());
						}
						Log.e("tt","##############设备API#######################");

				}
		}

		public void powerOn(String deviceType)
		{
				Device dev = this.getDevice(deviceType);
				if (dev == null)
				{
						return;
				}

				Action getPowerAct = dev.getAction("PowerOn");
				if (getPowerAct.postControlAction() == false)
						return;

				ArgumentList outArgList = getPowerAct.getOutputArgumentList();
				String powerState = outArgList.getArgument(0).getValue();
				String newPowerState = (powerState.compareTo("1") == 0) ? "0" : "1";

				Action setPowerAct = dev.getAction("PowerOff");
				setPowerAct.setArgumentValue("Power", newPowerState);
				setPowerAct.postControlAction();
		}

		public void tvPowerOn()
		{
				powerOn(TV_DEVICE_TYPE);
		}
		//获取本地ip地址---------获取本地内网IP


		private String getExternalIPAddress(String devComboName,Device dev)
		{
				String ExternalIPAddress = null;
				Action addPortAct = dev.getAction("GetExternalIPAddress");
				if (addPortAct == null)
				{
						Log.e("tt","GetExternalIPAddress is not found");
						return "";
				}
				if (addPortAct.postControlAction() == true)
				{
						ExternalIPAddress = addPortAct.getArgumentValue("NewExternalIPAddress");
						Log.e("tt","ExternalIPAddress:"+ExternalIPAddress);
						return ExternalIPAddress;
				}

				return "";
		}

		public void addPortMapping(Device dev)
		{
				//获取网关设备                      AddPortMapping
				Action addPortAct = dev.getAction("AddPortMapping");
				if (addPortAct == null)
				{
						Log.e("tt","AddPortMapping is not found");
						//	return;
				}

				addPortAct.setArgumentValue("NewRemoteHost", "");
				addPortAct.setArgumentValue("NewProtocol", "TCP");
				addPortAct.setArgumentValue("NewPortMappingDescription", "cybergarage");
				addPortAct.setArgumentValue("NewExternalPort", 9991);
				addPortAct.setArgumentValue("NewInternalClient", "192.168.31.120");
				addPortAct.setArgumentValue("NewInternalPort", "9990");
				addPortAct.setArgumentValue("NewEnabled", 1);
				addPortAct.setArgumentValue("NewLeaseDuration", 0);


				if (addPortAct.postControlAction() == true)
				{
						UPnPStatus upnpStat = addPortAct.getStatus();
						Log.e("tt","addPortMapping succ");
				}
				else
				{
						UPnPStatus upnpStat = addPortAct.getStatus();
						Log.e("tt","addPortMapping failed");
				}

		}
}
