/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package method.player;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.types.UDAServiceId;
import upnp.device.Bulb;

/**
 *
 * @author Admins
 */
public class LightController extends Bulb implements Services.LightService, Services{

    org.fourthline.cling.model.meta.Service service;

    protected boolean status;
    protected boolean isOn= false;

    public LightController(Device device, UpnpService upnpService) {
        super(device, upnpService);
        service = device.findService(new UDAServiceId("SwitchPower"));

    }

//    public int getVolume() {
//        ActionInvocation invocation = new ActionInvocation(service.getAction("GetAudio"));
//        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
//        Integer volume = (Integer) invocation.getOutput("CurrentVolume").getValue();
//        return volume.intValue();
//    }
    
    @Override
    public void remote(boolean b) {
        if(!getStatus()) return;
//        if(b)
//        {
            setTarget(b);
//        } else {
//            setTarget(true);
//        }
    }

    public void setTarget(boolean b) {
        status = b;
        ActionInvocation invocation = new ActionInvocation(service.getAction("setTarget"));
        invocation.setInput("newTargetValue", b);
        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
    }
}
