/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upnp.device;

import method.player.DoService;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.meta.Device;


/**
 *
 * @author Admins
 */
public abstract class DeviceObj {
    protected Device device;
    protected UpnpService upnpService;
    protected DoService doService;
    
    public DeviceObj(Device device, UpnpService upnpService)
    {
        this.device = device;
        this.upnpService = upnpService;
    }
    
    public Device getDevice()
    {
        return device;
    }
    
    public abstract void SwitchPower();
    public abstract boolean getStatus();
    
    public void remoteDevice(boolean b)
    {
        if(this instanceof TV)
        {
            TV tv = (TV) this;
            this.doService = tv;
            doService.DoService(b);
        }
    }

    public static boolean isBulb(Device device)
    {
        return device.getDetails().getFriendlyName().compareTo("Network Light (ADMIN-PC)") == 0;
    }

    public static boolean isTv(Device device)
    {
        return device.getDetails().getFriendlyName().compareTo("UPnP Television Emulator") == 0;
    }

    public static boolean isPhone(Device device)
    {
        return device.getDetails().getFriendlyName().compareTo("My phone") == 0;
    }
}
