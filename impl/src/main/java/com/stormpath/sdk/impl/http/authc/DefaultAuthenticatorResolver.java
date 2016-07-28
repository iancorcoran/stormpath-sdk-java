/*
 * Copyright 2016 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.sdk.impl.http.authc;

import com.stormpath.sdk.authc.AuthenticatorResolver;
import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.http.HttpAuthenticator;

/**
 * Default implementation of {@link AuthenticatorResolver}
 *
 * Supports authentication schemes of SAUTHC1 and BASIC as defined by {@link AuthenticationScheme}.
 * Defaults to SAUTHC1 if supplied authentication is null/empty.
 *
 * @since 1.0.0
 */
public class DefaultAuthenticatorResolver implements AuthenticatorResolver {
    @Override
    public HttpAuthenticator resolveAuthenticator(String authenticationScheme) {
        if (authenticationScheme == null || authenticationScheme.isEmpty()) {
            authenticationScheme = AuthenticationScheme.SAUTHC1;
        }

        if (authenticationScheme.equalsIgnoreCase(AuthenticationScheme.SAUTHC1)) {
            return new SAuthc1RequestAuthenticator();
        } else if (authenticationScheme.equalsIgnoreCase(AuthenticationScheme.BASIC)) {
            return new BasicRequestAuthenticator();
        } else {
            throw new IllegalArgumentException("Invalid authentication scheme: " + authenticationScheme + " specified.");
        }
    }
}
