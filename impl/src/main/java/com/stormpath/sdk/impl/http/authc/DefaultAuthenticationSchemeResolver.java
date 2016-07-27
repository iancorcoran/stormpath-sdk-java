/*
 * Copyright (c) 2016 Stormpath, Inc.  All rights reserved.
 */
package com.stormpath.sdk.impl.http.authc;

import com.stormpath.sdk.authc.AuthenticationSchemeResolver;
import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.client.AuthenticationSchemes;

public class DefaultAuthenticationSchemeResolver implements AuthenticationSchemeResolver {
    @Override
    public AuthenticationScheme resolveAuthenticationScheme(AuthenticationSchemes authenticationScheme) {
        switch (authenticationScheme){
            case BASIC:
                return new BasicAuthenticationScheme();
            case SAUTHC1:
            default:
                return new SAuthc1AuthenticationScheme();
        }
    }
}
