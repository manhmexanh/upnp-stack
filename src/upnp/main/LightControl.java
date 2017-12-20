package upnp.main;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.model.types.UnsignedIntegerOneByte;

public class LightControl {

    Device device;
    public LightControl(Device device)
    {
        this.device=device;
    }

    public boolean getStatus(UpnpService upnpService)
    {
        Service s = device.findService(new UDAServiceId("SwitchPower.0001"));
        Action action = s.getAction("GetStatus");
        ActionInvocation invocation = new ActionInvocation(action);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
        Boolean status = (Boolean) invocation.getOutput("ResultStatus").getValue();
        boolean value = status.booleanValue();
        return value;
    }

    public void switchStatus(UpnpService upnpService, boolean value)
    {
        Service s = device.findService(new UDAServiceId("SwitchPower.0001"));
        Action action = s.getAction("SetTarget");
        ActionInvocation invocation = new ActionInvocation(action);
        invocation.setInput("NewTargetValue", value);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
    }

    public void setDimming(UpnpService upnpService, UnsignedIntegerOneByte value)
    {
        Service s = device.findService(new UDAServiceId("DimmingService.0001"));
        Action action = s.getAction("SetLoadLevelTarget");
        ActionInvocation invocation = new ActionInvocation(action);
        invocation.setInput("NewLoadLevelTarget", value);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
    }


}
