package dev.chilito.backend.shared.infrastructure.security;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Obtain the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Validate that the token comes (Format: "Bearer eyJhb...")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Let it pass (Spring Security will block later if necessary)
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        try {
            // Validate with Firebase
            // FirebaseAuth.getInstance() obtained the credentials of FirebaseApp
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            // TODO:For now this attribute will not be used until the identity package is
            // implemented
            String email = decodedToken.getEmail();

            // Create the Spring authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uid, null,
                    // TODO: For now this attribute will not be used until the identity package is
                    // implemented
                    new ArrayList<>());

            // Set the security in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (FirebaseAuthException e) {
            // If the token is false or expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.error("Invalid token: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
