/*
 * Copyright (c) 2016 Stormpath, Inc.  All rights reserved.
 */
package com.stormpath.sdk.impl.http.authc;

import com.stormpath.sdk.client.AuthenticationScheme;

public class BasicAuthenticationScheme implements AuthenticationScheme <RequestAuthenticator> {
    @Override
    public RequestAuthenticator getAuthenticator() {
        return new BasicRequestAuthenticator();
    }
}
