package com.jilaba.design;

import com.jilaba.design.DesignEnum.DesktopType;
import com.jilaba.design.DesignEnum.OperatingSystemType;

public class SystemProperties {
	private SystemProperties(){
		
	}
	public static OperatingSystemType getOsType(){
		OperatingSystemType operatingSystemType;
		if(System.getProperties().getProperty("os.name") != null && System.getProperties().getProperty("os.name").toLowerCase().contains("windows")){
			operatingSystemType = OperatingSystemType.WINDOWS;
		}else{
			operatingSystemType = OperatingSystemType.LINUX;
		}
		
		return operatingSystemType;
	}
	public static DesktopType getDesktopType(){
		DesktopType desktopType = DesktopType.GNOME;
		if(System.getProperties().getProperty("sun.desktop") == null){
			desktopType = DesktopType.NONE;
		}else if(System.getProperties().getProperty("sun.desktop").toLowerCase().contains("gnome")) {
			desktopType = DesktopType.GNOME;
		}else if(System.getProperties().getProperty("sun.desktop").toLowerCase().contains("windows")){
			desktopType = DesktopType.WINDOWS;
		}
		return desktopType;
	}
}
