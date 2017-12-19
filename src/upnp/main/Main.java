/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upnp.main;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

/**
 *
 * @author
 */
public class Main {

/**
 * Runs a simple UPnP discovery procedure.
 */
    UpnpService upnpService;
    public Main()
    {
        // UPnP discovery is asynchronous, we need a callback
        Remote remote = new Remote(upnpService);
        RegistryListener listener = new RegistryListener() {

            public void remoteDeviceDiscoveryStarted(Registry registry,
                                                     RemoteDevice device) {
                
            }

            public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {
                
            }

            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                System.out.println(device.getDetails().getFriendlyName());
                if(device.getDetails().getFriendlyName().compareTo(Remote.DEVICE1)==0)
                    remote.setDevice1(device);
                if(device.getDetails().getFriendlyName().compareTo(Remote.DEVICE2)==0)
                    remote.setDevice2(device);
                if(device.getDetails().getFriendlyName().compareTo(Remote.DEVICE3)==0)
                    remote.setDevice3(device);
            }

            public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
                
            }

            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                
            }

            public void localDeviceAdded(Registry registry, LocalDevice device) {
                
            }

            public void localDeviceRemoved(Registry registry, LocalDevice device) {
                
            }

            public void beforeShutdown(Registry registry) {
                
            }

            public void afterShutdown() {

            }
        };

        // This will create necessary network resources for UPnP right away
        System.out.println("Starting Cling...");
        upnpService = new UpnpServiceImpl(listener);
        remote.setUPNP(upnpService);
        
        // Send a search message to all devices and services, they should respond soon
        System.out.println("Sending SEARCH message to all devices...");
        upnpService.getControlPoint().search(new STAllHeader());
        while(true);
    }
    
    
    public static void main(String[] args) throws Exception {
        new Main();
    }
}


