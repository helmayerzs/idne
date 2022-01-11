package hu.idne.backend.configurations;

import hu.idne.backend.models.system.AuditUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class IdneAuditUserProvider implements AuditUserProvider {

    @Override
    public String getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if ( isNull( authentication ) ) {
            return "__ANONYMOUS_USER__";
        }
        if ( authentication instanceof JwtAuthenticationToken ) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            username = (String) ( token ).getTokenAttributes().get( "preferred_username" );
        } else {
            username = authentication.getName();
        }
        return username;
    }

}