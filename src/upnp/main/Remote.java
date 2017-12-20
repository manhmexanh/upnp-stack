/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upnp.main;

import java.util.Map;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.*;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.model.types.UnsignedIntegerOneByte;

/**
 *
 * @author hoi luan phien
 */
public class Remote {
    UpnpService upnpService;
    SubscriptionCallback subscriptionCallback;
    Device device1=null;
    Device device2=null;
    Device device3=null;
    Device device4=null;
    LightControl control4=null;
    ControlDevice2 control=null;
    ControlDevice3 control3=null;

    public Remote(UpnpService upnpService){
        this.upnpService=upnpService;
    }
    public void setDevice4(Device d)
    {
        if(device4==null)
        {
            device4=d;
            control4=new LightControl(device4);
        }
    }

    public void setDevice3(Device d)
    {
        if(device3==null)
        {
            device3=d;
            control3=new ControlDevice3(device3);
        }
    }

    public void setDevice2(Device d)
    {
        if(device2==null)
        {
            device2=d;
            control=new ControlDevice2(device2);
        }
    }
    
    public void setDevice1(Device device)
    {
        if(device1==null)
        {
            System.out.println("device1");
            device1=device;
            subscriptionCallback = new SubscriptionCallback(device.findService(
                    new UDAServiceId("SwitchStatus")
                )) {
                    @Override
                    protected void failed(GENASubscription genaSubscription, UpnpResponse upnpResponse, Exception e, String s) {}

                    @Override
                    protected void established(GENASubscription genaSubscription) {}

                    @Override
                    protected void ended(GENASubscription genaSubscription, CancelReason cancelReason, UpnpResponse upnpResponse) {}

                    @Override
                    protected void eventReceived(GENASubscription genaSubscription) {
                        
                        Map<String, StateVariableValue> values = genaSubscription.getCurrentValues();
                        StateVariableValue status = values.get("Status");
                        int value = Integer.parseInt(status.toString());
                        System.out.println(value);
                        if(control != null&&upnpService!=null)
                        {
                            if(value == 1 ){
                                control.switchStatus(upnpService, true);
                                control3.switchStatus(upnpService, true);
                                control4.switchStatus(upnpService, true);
                            }
                            if(value == 0 ) {
                                control.switchStatus(upnpService, false);
                                control3.switchStatus(upnpService, false);
                                if(control4.getStatus(upnpService)){
                                    control4.setDimming(upnpService, new UnsignedIntegerOneByte(80));
                                }
                                else {
                                    control4.switchStatus(upnpService, true);
                                    control4.setDimming(upnpService,new UnsignedIntegerOneByte(20));
                                }
                            }
                        }
                    }

                    @Override
                    protected void eventsMissed(GENASubscription genaSubscription, int i) {}
                };
//            upnpService.getControlPoint().execute(subscriptionCallback);
                remote();
            }
        
    }
    
    public void setUPNP(UpnpService s)
    {
        upnpService =s;
    }
    
    public void remote()
    {
        
        System.out.println(upnpService==null);
        if(subscriptionCallback!=null&&upnpService!=null)
        {
            System.out.println("hhh");
            upnpService.getControlPoint().execute(subscriptionCallback);
        }
    }
    
    
    public static String DEVICE1="Door";
    public static String DEVICE2="Light";
    public static String DEVICE3="tivi";
    public static String DEVICE4="Network Light (ADMIN-PC)";
}
