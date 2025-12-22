package dev.chilito.backend.shared.infrastructure.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import dev.chilito.backend.identity.application.input.IUserInPort;
import dev.chilito.backend.identity.domain.models.User;

import com.google.firebase.auth.FirebaseAuthException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final FirebaseAuth firebaseAuth;
    private final IUserInPort userInPort;

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
            // Obtain the instance of the context through FirebaseAuth
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
            String uid = decodedToken.getUid();
            // TODO:For now this attribute will not be used until the identity package is
            // implemented
            String email = decodedToken.getEmail();

            List<SimpleGrantedAuthority> authorities = getAuthorities(uid);

            // Create the Spring authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uid, null,
                    authorities);

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

    private List<SimpleGrantedAuthority> getAuthorities(String uid) {
        try {
            User user = userInPort.getUserByFirebaseUid(uid);
            String roleName = "ROLE_" + user.getRole().name();
            return List.of(new SimpleGrantedAuthority(roleName));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
