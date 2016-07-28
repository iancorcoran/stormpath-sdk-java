package com.stormpath.sdk.authc;

import com.stormpath.sdk.http.HttpAuthenticator;

public interface AuthenticatorResolver <T extends HttpAuthenticator> {

    T resolveAuthenticator(String authenticationScheme);
}
