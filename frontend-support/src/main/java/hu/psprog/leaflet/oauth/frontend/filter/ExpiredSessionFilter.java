package hu.psprog.leaflet.oauth.frontend.filter;

import hu.psprog.leaflet.oauth.frontend.logout.ForcedLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that forces the user to re-login when their session expires or gets invalidated on Leaflet's side.
 * User session gets invalidated if they are authenticated, though Leaflet returns with a response of status '401 Unauthorized'.
 * In this case, the SecurityContext will get cleared, session invalidated and session cookies deleted.
 *
 * @author Peter Smith
 */
@Component
@Order
public class ExpiredSessionFilter extends OncePerRequestFilter {

    private final ForcedLogoutHandler forcedLogoutHandler;

    @Autowired
    public ExpiredSessionFilter(ForcedLogoutHandler forcedLogoutHandler) {
        this.forcedLogoutHandler = forcedLogoutHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request, response);

        if (isAuthenticated() && isRequestUnauthorized(response)) {
            forcedLogoutHandler.forceLogout(request);
        }
    }

    private boolean isAuthenticated() {
        return isAuthenticatedByOAuthAccessToken(getAuthentication()) && getAuthentication().isAuthenticated();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isAuthenticatedByOAuthAccessToken(Authentication authentication) {
        return authentication instanceof OAuth2AuthenticationToken;
    }

    private boolean isRequestUnauthorized(HttpServletResponse response) {
        return response.getStatus() == HttpStatus.UNAUTHORIZED.value();
    }
}
