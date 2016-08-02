/*
 * Copyright (c) 2016 Stormpath, Inc.  All rights reserved.
 */
package com.stormpath.sdk.client;

import com.stormpath.sdk.lang.Classes;

/**
 * Static utility/helper class for working with {@link AuthenticationScheme} resources.
 *
 * @since 1.0.0
 */
public class AuthenticationSchemes {

    /**
     * Instance of a <a href="http://docs.stormpath.com/rest/product-guide/#authentication-digest">Stormpath SAuthc1
     * Digest Authentication Scheme</a>, which is the default {@link AuthenticationScheme} implementation used to
     * authenticate every request sent to the Stormpath API server.
     */
    public static final AuthenticationScheme SAUTHC1 = (AuthenticationScheme) Classes.newInstance("com.stormpath.sdk.impl.http.authc.SAuthc1RequestAuthenticator");

    /**
     * Instance of a <a href="http://docs.stormpath.com/rest/product-guide/#authentication-basic">HTTP Basic
     * Authentication</a> {@link AuthenticationScheme} implementation.
     *
     * <p>Usage of the more secure SAUTHC1 AuthenticationScheme is strongly recommended <em>unless</em> your
     * application is deployed in an environment that - outside of your application's control - manipulates request
     * headers on outgoing HTTP requests. Google App Engine is one such environment, for example.</p>
     *
     * <p>As such, in these environments only, an alternative authentication mechanism is necessary, such as
     * Basic Authentication.</p>
     */
    public static final AuthenticationScheme BASIC = (AuthenticationScheme) Classes.newInstance("com.stormpath.sdk.impl.http.authc.BasicRequestAuthenticator");

    private static final String SAUTHC1_SCHEME_NAME = "SAUTHC1";
    private static final String BASIC_SCHEME_NAME = "BASIC";

    /**
     * Retrieves an authentication scheme instance based on the supplied Authentication Scheme name.
     *
     * Expected name values of "SAUTHC1" and "BASIC" are supported, corresponding to the
     * {@link AuthenticationSchemes#SAUTHC1} and {@link AuthenticationSchemes#BASIC} static instances respectively.
     *
     * @param schemeName Authentication Scheme name
     * @return Concrete instance of an {@link AuthenticationScheme}
     */
    public static AuthenticationScheme getAuthenticationScheme(String schemeName) {
        if (schemeName == null || schemeName.isEmpty() || schemeName.equalsIgnoreCase(SAUTHC1_SCHEME_NAME)) {
            return SAUTHC1;
        } else if (schemeName.equalsIgnoreCase(BASIC_SCHEME_NAME)) {
            return BASIC;
        } else {
            throw new IllegalArgumentException("Unrecognized authentication scheme '" + schemeName + "' specified.");
        }
    }

}
