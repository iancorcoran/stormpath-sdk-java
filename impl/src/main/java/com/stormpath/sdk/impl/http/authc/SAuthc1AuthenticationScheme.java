/*
 * Copyright (c) 2016 Stormpath, Inc.  All rights reserved.
 */
package com.stormpath.sdk.impl.http.authc;

import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.http.HttpAuthenticator;

public class SAuthc1AuthenticationScheme implements AuthenticationScheme <RequestAuthenticator> {
    @Override
    public RequestAuthenticator getAuthenticator() {
        return new SAuthc1RequestAuthenticator();
    }
}
