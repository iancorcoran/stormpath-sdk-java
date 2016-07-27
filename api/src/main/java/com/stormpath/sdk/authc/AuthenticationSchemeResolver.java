package com.stormpath.sdk.authc;

import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.client.AuthenticationSchemes;

public interface AuthenticationSchemeResolver {
    AuthenticationScheme resolveAuthenticationScheme(AuthenticationSchemes authenticationScheme);
}
