package com.deviget.minesweeper.security.token;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.deviget.minesweeper.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;

/**
 * Utility class for token generation and management.
 * 
 * @author david.rios
 */
@Component
public class TokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    @Value("${minesweeper.token.secret}")
    private String tokenSecret;

    @Value("${minesweeper.token.expirationMs}")
    private int tokenExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + tokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid token signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid token claims string: {}", e.getMessage());
        }

        return false;
    }
}
