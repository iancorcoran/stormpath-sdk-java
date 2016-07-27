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
package com.stormpath.sdk.servlet.mvc;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.lang.Assert;
import com.stormpath.sdk.provider.ProviderAccountRequest;
import com.stormpath.sdk.provider.ProviderAccountResult;
import com.stormpath.sdk.servlet.application.ApplicationResolver;
import com.stormpath.sdk.servlet.authc.impl.DefaultSuccessfulAuthenticationRequestEvent;
import com.stormpath.sdk.servlet.authc.impl.TransientAuthenticationResult;
import com.stormpath.sdk.servlet.config.Config;
import com.stormpath.sdk.servlet.event.RequestEvent;
import com.stormpath.sdk.servlet.event.impl.Publisher;
import com.stormpath.sdk.servlet.http.Saver;
import com.stormpath.sdk.servlet.mvc.provider.SocialUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @since 1.0.0
 */
public abstract class AbstractSocialCallbackController extends AbstractController {

    protected Saver<AuthenticationResult> authenticationResultSaver;
    private final Publisher<RequestEvent> eventPublisher;
    private final SocialUtils socialUtils;

    public AbstractSocialCallbackController(String loginNextUri,
                                            Saver<AuthenticationResult> authenticationResultSaver,
                                            Publisher<RequestEvent> eventPublisher) {
        this.nextUri = loginNextUri;
        this.authenticationResultSaver = authenticationResultSaver;
        this.eventPublisher = eventPublisher;

        Assert.notNull(this.authenticationResultSaver, "authenticationResultSaver cannot be null.");
        Assert.hasLength(this.nextUri, "nextUri cannot be null.");
        Assert.notNull(this.eventPublisher, "eventPublish cannot be null.");
        this.socialUtils = new SocialUtils();
    }

    @Override
    public boolean isNotAllowedIfAuthenticated() {
        return true;
    }

    protected Application getApplication(HttpServletRequest request) {
        return ApplicationResolver.INSTANCE.getApplication(request);
    }

    protected ProviderAccountRequest getAccountProviderRequest(HttpServletRequest request) {
        return socialUtils.getAccountProviderRequest(request);
    }

    @Override
    protected ViewModel doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProviderAccountRequest providerRequest = getAccountProviderRequest(request);
        ProviderAccountResult result = getApplication(request).getAccount(providerRequest);

        // 751: Check if account is unverified and redirect to verifyUri if true
        Account account = result.getAccount();
        if (account.getStatus().equals(AccountStatus.UNVERIFIED)) {
            Config config = (Config) request.getServletContext().getAttribute(Config.class.getName());
            String loginUri = config.getLoginControllerConfig().getUri();
            return new DefaultViewModel(loginUri + "?status=unverified").setRedirect(true);
        }

        AuthenticationResult authcResult = new TransientAuthenticationResult(result.getAccount());
        authenticationResultSaver.set(request, response, authcResult);

        eventPublisher.publish(new DefaultSuccessfulAuthenticationRequestEvent(request, response, null, authcResult));

        return new DefaultViewModel(nextUri).setRedirect(true);
    }
}
