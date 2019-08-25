package it.unitn.disi.wp.cup.model.health_service;

import it.unitn.disi.wp.cup.config.AuthConfig;
import it.unitn.disi.wp.cup.persistence.entity.HealthService;
import it.unitn.disi.wp.cup.model.Model;

/**
 * Credentials Model for authenticating the {@link HealthService Health Service}
 *
 * @author Carlo Corradini
 */
public class CredentialsModel implements Model {
    private long id;
    private String password;

    CredentialsModel() {
        id = 0L;
        password = "";
    }

    /**
     * Return the id of the {@link HealthService Health Service}
     *
     * @return id of the {@link HealthService Health Service}
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the {@link HealthService Health Service}
     *
     * @param id The id of the {@link HealthService Health Service}
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return the password of the {@link HealthService Health Service}
     *
     * @return The password of the {@link HealthService Health Service}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the {@link HealthService Health Service}
     *
     * @param password The password of the {@link HealthService Health Service}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isValid() {
        return id != 0L
                && !password.isEmpty()
                && password.length() >= AuthConfig.getPasswordMinLength()
                && password.length() <= AuthConfig.getPasswordMaxLength();
    }
}
