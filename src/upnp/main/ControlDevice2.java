/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upnp.main;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UDAServiceId;

/**
 *
 * @author
 */
public class ControlDevice2 {
    Device device;
    public ControlDevice2(Device device)
    {
        this.device=device;
    }
    
    public boolean getStatus(UpnpService upnpService)
    {
        Service s = device.findService(new UDAServiceId("SwitchStatus"));
        Action action = s.getAction("GetStatus");
        ActionInvocation invocation = new ActionInvocation(action);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
        Boolean status = (Boolean) invocation.getOutput("ResultStatus").getValue();
        boolean value = status.booleanValue();
        return value;
    }
    
    public void switchStatus(UpnpService upnpService, boolean value)
    {
        Service s = device.findService(new UDAServiceId("SwitchStatus"));
        Action action = s.getAction("SetTarget");
        ActionInvocation invocation = new ActionInvocation(action);
        invocation.setInput("NewTargetValue", value);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
    }
}
