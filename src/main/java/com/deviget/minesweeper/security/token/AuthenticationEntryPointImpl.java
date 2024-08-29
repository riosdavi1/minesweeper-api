package com.deviget.minesweeper.security.token;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuthenticationEntryPoint interface to handle Authentication Exception.
 * 
 * @author david.rios
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);

    /**
     * Returns HttpServletResponse.SC_UNAUTHORIZED (401 Status code) any time an unauthenticated User requests a secured
     * HTTP resource and an AuthenticationException is thrown.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
