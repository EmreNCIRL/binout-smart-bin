/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binout.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author emurekun
 */
public class ServiceRegistry {

    // In-memory map to hold service name and its endpoint (e.g., host:port)
    private final Map<String, String> services = new ConcurrentHashMap<>();

    // Register a service with its name and endpoint
    public boolean registerService(String serviceName, String endpoint) {
        if (serviceName == null || serviceName.isEmpty() || endpoint == null || endpoint.isEmpty()) {
            return false; // invalid input
        }
        services.put(serviceName, endpoint);
        return true;
    }

    // Discover a service endpoint by name
    public String discoverService(String serviceName) {
        return services.get(serviceName); // returns null if not found
    }

    // For testing/debugging: get all registered services
    public Map<String, String> getAllServices() {
        return services;
    }
}

