/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upnp.device;

import method.player.*;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.types.UDAServiceId;

/**
 *
 * @author Admins
 */
public class TV extends DeviceObj implements DoService{
    org.fourthline.cling.model.meta.Service service;
    
    public boolean status;
    protected Services services;
    
    public TV(Device device, UpnpService upnpService) {
        super(device, upnpService);
        service  = device.findService(new UDAServiceId("tvcontrolSCPD.0001"));
        status = false;
    }
    
    public void addService(boolean b)
    {
    }

    public void SwitchPower() {
        if(status)
            PowerOff();
        else
            PowerOn();
        status = !status;
    }

    public void PowerOn() {
        ActionInvocation invocation = new ActionInvocation(service.getAction("PowerOn"));
        invocation.setInput("Power", true);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
        status = true;
    }

    public void PowerOff() {
        ActionInvocation invocation = new ActionInvocation(service.getAction("PowerOff"));
        invocation.setInput("Power", false);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
        status = false;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public void DoService(boolean b) {
        if(this instanceof Services.PowerService){
            PowerController power = (PowerController) this;
            this.services = power;
            this.services.remote(b);
        }
//        if(this instanceof AudioController)
//        {
//            AudioController audio = (AudioController) this;
//            this.services = audio;
//            this.services.remote(b);
//        }
//        if(this instanceof PlayController)
//        {
//            PlayController player = (PlayController) this;
//            this.services = player;
//            this.services.remote(b);
//        }
    }
}
