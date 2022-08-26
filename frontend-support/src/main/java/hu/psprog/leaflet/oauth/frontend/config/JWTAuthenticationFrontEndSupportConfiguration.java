package hu.psprog.leaflet.oauth.frontend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for JWT authentication front-end support module.
 *
 * @author Peter Smith
 */
@Configuration
@ComponentScan(basePackages = {
        "hu.psprog.leaflet.oauth.frontend.filter",
        "hu.psprog.leaflet.oauth.frontend.logout"})
public class JWTAuthenticationFrontEndSupportConfiguration {
}
