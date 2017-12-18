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
import upnp.device.TV;

/**
 *
 * @author Admins
 */
public class PowerController extends TV implements Services.PowerService, Services{

    org.fourthline.cling.model.meta.Service service;

    protected boolean currentStatus;

    public PowerController(Device device, UpnpService upnpService) {
        super(device, upnpService);
        service = device.findService(new UDAServiceId("tvcontrolSCPD.0001"));
        currentStatus = getStatus();
    }
    
    @Override
    public void remote(boolean b) {
//        if(!getStatus()) return;
//        if(b)
//        {
//            setVolume(currentVolume);
//            isMute = false;
//        } else {
//            setVolume(0);
//            isMute = true;
//        }
        if(b){
            PowerOff();
            currentStatus = false;
        } else {
            PowerOn();
            currentStatus = true;
        }
    }

//    @Override
//    public void setVolume(int value) {
//        if(!isMute) currentVolume = getVolume();
//        ActionInvocation invocation = new ActionInvocation(service.getAction("SetVolume"));
//        invocation.setInput("NewVolume", value);
//        new ActionCallback.Default(invocation, upnpService.getControlPoint()).run();
//    }
}
