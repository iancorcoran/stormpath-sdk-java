/*
 * Copyright (c) 2016 Stormpath, Inc.  All rights reserved.
 */
package com.stormpath.sdk.impl.http.authc;

import com.stormpath.sdk.authc.AuthenticatorResolver;
import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.http.HttpAuthenticator;

public class DefaultAuthenticatorResolver implements AuthenticatorResolver {
    @Override
    public HttpAuthenticator resolveAuthenticator(AuthenticationScheme authenticationScheme) {
        if (authenticationScheme == null) {
            authenticationScheme = AuthenticationScheme.SAUTHC1;
        }

        switch (authenticationScheme) {
            case BASIC:
                return new BasicRequestAuthenticator();
            case SAUTHC1:
            default:
                return new SAuthc1RequestAuthenticator();
        }
    }
}
