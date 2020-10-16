package com.ip737.transportcompany.transportcompany.configs.token;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.configs.security.services.UserDetailsImpl;
import com.ip737.transportcompany.transportcompany.configs.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {
    @Autowired
    final private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Value("${transport-company.app.jwtSecret}")
    private String secret;

    public String provideToken(String username) {

        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.JWT_CLAIMS_ID, user.getId());
        claims.put(Constants.JWT_CLAIMS_FULL_NAME, user.getFullName());
        claims.put(Constants.JWT_CLAIMS_EMAIL, user.getEmail());
        claims.put(Constants.JWT_CLAIMS_ROLE, user.getRole());

        return Jwts.builder()
                .setSubject(user.getId())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
