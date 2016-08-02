package com.stormpath.sdk.impl.client

import com.stormpath.sdk.client.AuthenticationScheme
import com.stormpath.sdk.client.AuthenticationSchemes
import com.stormpath.sdk.impl.http.authc.BasicRequestAuthenticator
import com.stormpath.sdk.impl.http.authc.SAuthc1RequestAuthenticator
import org.testng.annotations.Test

import static org.testng.AssertJUnit.assertTrue

class AuthorizationSchemesTest {

    @Test
    void testDefaultIsSAuthC1(){
        AuthenticationScheme authenticationScheme = AuthenticationSchemes.getAuthenticationScheme(null);
        assertTrue(authenticationScheme instanceof SAuthc1RequestAuthenticator)
        authenticationScheme = AuthenticationSchemes.getAuthenticationScheme("")
        assertTrue(authenticationScheme instanceof SAuthc1RequestAuthenticator)
    }

    @Test
    void testSAuthC1SchemeNameReturnsCorrectAuthenticator(){
        AuthenticationScheme authenticationScheme = AuthenticationSchemes.getAuthenticationScheme("SAUTHC1");
        assertTrue(authenticationScheme instanceof SAuthc1RequestAuthenticator)
    }

    @Test
    void testBasicSchemeNameReturnsCorrectAuthenticator(){
        AuthenticationScheme authenticationScheme = AuthenticationSchemes.getAuthenticationScheme("BASIC");
        assertTrue(authenticationScheme instanceof BasicRequestAuthenticator)
    }

}
