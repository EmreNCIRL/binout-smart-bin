/*
Emre Ketme
Distributed Systems CA
*/

package binout.discovery;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;

// this class registers services to the network using jmDNS
public class JmDNSServiceRegistrar {
  private final JmDNS jmdns;

  public JmDNSServiceRegistrar() throws Exception {
    // creates jmDNS on the local machine
    jmdns = JmDNS.create(InetAddress.getLocalHost());
  }

  // registers a service with name/type/port/info
  public void registerService(String type, String name, int port, String desc) throws Exception {
    ServiceInfo service = ServiceInfo.create(type, name, port, desc);
    jmdns.registerService(service);
  }

  // stops all registered services
  public void unregisterAll() { jmdns.unregisterAllServices(); }
}
