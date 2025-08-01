/*
Emre Ketme
Distributed Systems CA
*/

package binout.discovery;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// this class finds services on the network using jmDNS
public class JmDNSServiceDiscovery implements AutoCloseable {
  private final JmDNS jmdns;

  public JmDNSServiceDiscovery() throws Exception {
    // starts up jmDNS
    jmdns = JmDNS.create(InetAddress.getLocalHost());
  }

  // this waits for service to show up for a bit and gets it if it can
  public ServiceInfo discoverService(String serviceType, int timeoutSeconds) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    final ServiceInfo[] found = new ServiceInfo[1];
    jmdns.addServiceListener(serviceType, new ServiceListener() {
      public void serviceAdded(ServiceEvent ev) {}
      public void serviceRemoved(ServiceEvent ev) {}
      public void serviceResolved(ServiceEvent ev) {
        found[0] = ev.getInfo();
        latch.countDown();
      }
    });
    latch.await(timeoutSeconds, TimeUnit.SECONDS);
    return found[0];
  }

  @Override
  public void close() throws Exception {
    // cleans up jmDNS
    jmdns.close();
  }
}
