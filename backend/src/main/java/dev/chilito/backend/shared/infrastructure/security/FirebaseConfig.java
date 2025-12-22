package dev.chilito.backend.shared.infrastructure.security;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    /**
     * This method initializes the FirebaseApp instance with the credentials from
     * the
     * service account file (Private key)
     * 
     * @return FirebaseApp
     * @throws IOException
     */
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Obtain the service account file (Private key)
        ClassPathResource resource = new ClassPathResource(firebaseConfigPath);
        InputStream serviceAccount = resource.getInputStream();

        // Assign the credentials to the Firebase options
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Avoid errors if app is reloaded
        if (FirebaseApp.getApps().isEmpty()) {
            // Initialize Firebase with the options only if it is not already initialized
            return FirebaseApp.initializeApp(options);
        }

        // Return the instance if it is already initialized (Singleton)
        // The process of initializing Firebase is expensive, so it should be avoided
        // initializing it several times
        return FirebaseApp.getInstance();
    }

    /**
     * This method returns the FirebaseAuth instance
     * 
     * @param firebaseApp
     * @return FirebaseAuth
     */
    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance();
    }
}
