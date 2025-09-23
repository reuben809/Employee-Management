package com.example.Employee_management.service;

import com.example.Employee_management.entity.Employee;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakAdminClientService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public void createKeycloakUser(Employee employee) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType("client_credentials")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        // Create user representation
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(employee.getEmail());
        user.setFirstName(employee.getFirstName());
        user.setLastName(employee.getLastName());
        user.setEmail(employee.getEmail());

        // Set password
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("password");
        user.setCredentials(Collections.singletonList(credential));

        // Create user in Keycloak
        Response response = keycloak.realm(realm).users().create(user);
        if(response.getStatus() != 201) {
            String responseBody = response.readEntity(String.class);
            throw new RuntimeException("Failed to create user in Keycloak. Status: "
                    + response.getStatus() + ", Body: " + responseBody);
        }

        // Search user using the new method (with pagination)
        List<UserRepresentation> users = keycloak.realm(realm)
                .users()
                .search(employee.getEmail(), 0, 1); // search by username, get first result

        if (users.isEmpty()) {
            throw new RuntimeException("User not found after creation: " + employee.getEmail());
        }

        String userId = users.get(0).getId();
        String roleName = employee.getRole().name();
        RoleRepresentation roleToAssign = keycloak.realm(realm).roles().get(roleName).toRepresentation();

        // Assign role
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(roleToAssign));
    }
    /**
     * Deletes a user from Keycloak based on their email address.
     * @param email The email of the user to delete.
     */
    public void deleteKeycloakUser(String email) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl).realm(realm).grantType("client_credentials")
                .clientId(clientId).clientSecret(clientSecret)
                .build();

        List<UserRepresentation> users = keycloak.realm(realm).users().search(email, 0, 1);

        if(users.isEmpty()) {
            System.out.println("Could not find user in Keycloak with email:" + email + ". Nothing to delete.");
            return;
        }
        String userId = users.get(0).getId();
        keycloak.realm(realm).users().delete(userId);
    }

}
