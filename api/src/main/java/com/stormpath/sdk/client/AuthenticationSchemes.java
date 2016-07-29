/*
 * Copyright (c) 2016 Stormpath, Inc.  All rights reserved.
 */
package com.stormpath.sdk.client;

import com.stormpath.sdk.lang.Classes;

public class AuthenticationSchemes {

    public static final String SAUTHC1 = "SAUTHC1";
    public static final String BASIC = "BASIC";

    public static AuthenticationScheme getAuthenticationScheme(String schemeName) {
        if (schemeName == null || schemeName.isEmpty() || schemeName.equalsIgnoreCase(SAUTHC1)) {
            return (AuthenticationScheme) Classes.newInstance("com.stormpath.sdk.impl.http.authc.SAuthc1RequestAuthenticator");
        }
        else if(schemeName.equalsIgnoreCase(BASIC)){
            return (AuthenticationScheme) Classes.newInstance("com.stormpath.sdk.impl.http.authc.BasicRequestAuthenticator");
        }
        else{
            throw new IllegalArgumentException("Unrecognized authentication scheme '" + schemeName + "' specified.");
        }
    }

}
