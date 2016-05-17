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
package com.stormpath.sdk.impl.oauth;

import com.stormpath.sdk.directory.AccountStore;
import com.stormpath.sdk.lang.Assert;
import com.stormpath.sdk.oauth.OAuthClientCredentialsGrantRequestAuthentication;

/**
 * @since 1.0.0
 */
public class DefaultOAuthClientCredentialsGrantRequestAuthentication implements OAuthClientCredentialsGrantRequestAuthentication {

    private String apiKeyId;
    private String apiKeySecret;
    private String jwt;
    private AccountStore accountStore;
    private final static String grantType = "client_credentials";

    public DefaultOAuthClientCredentialsGrantRequestAuthentication(String apiKeyId, String apiKeySecret) {
        Assert.notNull(apiKeyId, "apiKeyId argument cannot be null.");
        Assert.notNull(apiKeySecret, "apiKeySecret argument cannot be null.");

        this.apiKeyId = apiKeyId;
        this.apiKeySecret = apiKeySecret;
    }

    public DefaultOAuthClientCredentialsGrantRequestAuthentication(String jwt) {
        Assert.notNull(jwt, "jwt argument cannot be null.");

        this.jwt = jwt;
    }

    public OAuthClientCredentialsGrantRequestAuthentication setAccountStore(AccountStore accountStore) {
        this.accountStore = accountStore;
        return this;
    }

    public String getApiKeySecret() {
        return apiKeySecret;
    }

    public String getApiKeyId() {
        return apiKeyId;
    }

    @Override
    public AccountStore getAccountStore() {
        return accountStore;
    }

    @Override
    public String getJwt() {
        return jwt;
    }

    @Override
    public String getGrantType() {
        return grantType;
    }
}
