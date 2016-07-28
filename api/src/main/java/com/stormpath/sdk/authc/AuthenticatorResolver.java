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
package com.stormpath.sdk.authc;

import com.stormpath.sdk.client.AuthenticationScheme;
import com.stormpath.sdk.http.HttpAuthenticator;

/**
 * Interface to implemented by resolvers capable of creating an http authenticator for a given authentication scheme.
 * Such schemes define the way the communication with the Stormpath API server will be authenticated.
 * @see AuthenticationScheme
 * @see HttpAuthenticator
 * @param <T> Any implementation of {@link HttpAuthenticator}
 * @since 1.0.0
 */
public interface AuthenticatorResolver <T extends HttpAuthenticator> {
    /**
     * Resolve HttpAuthenticator for a given authentication scheme.
     * @param authenticationScheme Authentication scheme defining how communication with the Stormpath API server will be authenticated.
     * @return Concrete {@link HttpAuthenticator} implementation corresponding to the given authentication scheme.
     */
    T resolveAuthenticator(String authenticationScheme);
}
