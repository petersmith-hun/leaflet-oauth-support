package hu.psprog.leaflet.oauth.frontend.filter.device;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import jakarta.annotation.PostConstruct;
import java.util.UUID;

/**
 * Generates a session-scope device ID.
 *
 * @author Peter Smith
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
class DeviceIDGenerator {

    private UUID deviceID;

    @PostConstruct
    public void setup() {
        deviceID = UUID.randomUUID();
    }

    UUID getID() {
        return deviceID;
    }
}
