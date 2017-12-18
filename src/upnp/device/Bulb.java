package upnp.device;

import method.player.*;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.types.UDAServiceId;

public class Bulb extends DeviceObj implements DoService{
    org.fourthline.cling.model.meta.Service service;

    public boolean isOn;
    protected Services services;

    public Bulb(Device device, UpnpService upnpService) {
        super(device, upnpService);
        service  = device.findService(new UDAServiceId("SwitchPower.0001"));
        isOn = getStatus();
    }

    public void addService(boolean b)
    {
    }

    @Override
    public void SwitchPower() {
        ActionInvocation invocation = new ActionInvocation(service.getAction("GetStatus"));
        invocation.setInput("newTargetValue", !isOn);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
        isOn = !isOn;
    }

    @Override
    public boolean getStatus() {
        ActionInvocation invocation = new ActionInvocation(service.getAction("GetStatus"));
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
        Boolean status = (Boolean) invocation.getOutput("ResultStatus").getValue();
        return status.booleanValue();
    }

    @Override
    public void DoService(boolean b) {
        if(this instanceof LightController) {
            LightController light = (LightController) this;
            this.services = light;
            this.services.remote(b);
        }
    }
}