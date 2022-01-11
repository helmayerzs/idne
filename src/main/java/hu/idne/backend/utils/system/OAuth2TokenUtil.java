package hu.idne.backend.utils.system;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class OAuth2TokenUtil {

    public String getSupplierCode(Principal principal) {

        JwtAuthenticationToken jat = (JwtAuthenticationToken) principal;
        Jwt jwt = (Jwt) jat.getCredentials();
        jwt.getClaims();
        String supplierCode = (String) jwt.getClaims().get("supplierCode");

        return supplierCode;
    }
}
