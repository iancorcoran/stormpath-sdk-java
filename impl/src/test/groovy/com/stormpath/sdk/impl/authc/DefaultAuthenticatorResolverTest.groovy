package com.stormpath.sdk.impl.authc

import com.stormpath.sdk.client.AuthenticationScheme
import com.stormpath.sdk.http.HttpAuthenticator
import com.stormpath.sdk.impl.http.authc.BasicRequestAuthenticator
import com.stormpath.sdk.impl.http.authc.DefaultAuthenticatorResolver
import com.stormpath.sdk.impl.http.authc.SAuthc1RequestAuthenticator
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.testng.Assert.assertEquals
import static org.testng.Assert.assertTrue

class DefaultAuthenticatorResolverTest {


    DefaultAuthenticatorResolver defaultAuthenticatorResolver;

    @BeforeMethod
    public void setup(){
        defaultAuthenticatorResolver = new DefaultAuthenticatorResolver();
    }

    @Test
    public void defaultsToSAuthC1(){
        HttpAuthenticator authenticator = defaultAuthenticatorResolver.resolveAuthenticator(null);
        assertTrue(authenticator instanceof SAuthc1RequestAuthenticator)

        authenticator = defaultAuthenticatorResolver.resolveAuthenticator("");
        assertTrue(authenticator instanceof SAuthc1RequestAuthenticator)
    }

    @Test
    public void returnsAppropriateAuthenticator(){
        HttpAuthenticator authenticator = defaultAuthenticatorResolver.resolveAuthenticator(AuthenticationScheme.BASIC);
        assertTrue(authenticator instanceof BasicRequestAuthenticator)

        authenticator = defaultAuthenticatorResolver.resolveAuthenticator(AuthenticationScheme.SAUTHC1);
        assertTrue(authenticator instanceof SAuthc1RequestAuthenticator)
    }

    @Test(expectedExceptions = [IllegalArgumentException.class])
    public void invalidAuthenticationSchemeThrowsIllegalArgumentException(){
        defaultAuthenticatorResolver.resolveAuthenticator("not a real auth scheme");
    }
}
