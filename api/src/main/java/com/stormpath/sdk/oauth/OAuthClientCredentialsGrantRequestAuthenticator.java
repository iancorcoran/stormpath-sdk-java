/*
* Copyright 2015 Stormpath, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.stormpath.sdk.oauth;

/**
 * This class is used to obtain an OAuth 2.0 token created in Stormpath using client credentials. For example:
 * <pre>
 * Application app = obtainApplication();
 * OAuthClientCredentialsGrantRequestAuthentication request = <b>OAuthRequests.OAUTH_CLIENT_CREDENTIALS_GRANT_REQUEST.builder()</b>
 *      .setApiKey(apiKey)
 *      .build();
 * OAuthGrantRequestAuthenticationResult result = Authenticators.OAUTH_CLIENT_CREDENTIALS_REQUEST_AUTHENTICATOR.forApplication(app).authenticate(request);
 * </pre>
 *
 * @see OAuthPasswordGrantRequestAuthenticator
 * @see OAuthBearerRequestAuthenticator
 *
 * @since 1.0.0
 */
public interface OAuthClientCredentialsGrantRequestAuthenticator extends OAuthRequestAuthenticator<OAuthGrantRequestAuthenticationResult> {

    OAuthClientCredentialsGrantRequestAuthenticator withLocalValidation();
}
