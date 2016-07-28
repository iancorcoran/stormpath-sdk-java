package com.stormpath.sdk.authc;

import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.http.HttpAuthenticator;

/**
 * Interface to implemented by resolvers capable of creating an http authenticator for a given authentication scheme.
 * Such schemes define the way the communication with the Stormpath API server will be authenticated.
 * @see AuthenticationScheme
 * @see HttpAuthenticator
 * @param <T> Any implementation of {@link HttpAuthenticator}
 * @since 1.0.0
 */
public interface AuthenticatorResolver <T extends HttpAuthenticator> {
    /**
     * Resolve HttpAuthenticator for a given authentication scheme.
     * @param authenticationScheme Authentication scheme defining how communication with the Stormpath API server will be authenticated.
     * @return Concrete {@link HttpAuthenticator} implementation corresponding to the given authentication scheme.
     */
    T resolveAuthenticator(String authenticationScheme);
}
