package com.stormpath.sdk.servlet.mvc.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationAccountStoreMapping;
import com.stormpath.sdk.directory.AccountStore;
import com.stormpath.sdk.directory.AccountStoreVisitor;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.impl.provider.DefaultGithubProvider;
import com.stormpath.sdk.lang.Assert;
import com.stormpath.sdk.organization.Organization;
import com.stormpath.sdk.provider.ProviderAccountRequest;
import com.stormpath.sdk.provider.Providers;
import com.stormpath.sdk.servlet.application.ApplicationResolver;
import com.stormpath.sdk.servlet.util.ServletUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.stormpath.sdk.servlet.filter.DefaultFilter.accessToken;
import static com.stormpath.sdk.servlet.mvc.JacksonFieldValueResolver.MARSHALLED_OBJECT;

/**
 * Utility class to extract social parameters from requests to create a ProviderAccountRequest.
 * @since 1.0.0
 */
public class SocialUtils {
    private static final Logger log = LoggerFactory.getLogger(SocialUtils.class);
    private static final String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_ACCESS_TOKEN_FIELD = "access_token";

    @SuppressWarnings("unchecked")
    public ProviderAccountRequest getAccountProviderRequest(HttpServletRequest request) {
        Map<String, Object> map = (Map<String, Object>) request.getAttribute(MARSHALLED_OBJECT);
        Map<String, String> providerData = (Map<String, String>) map.get("providerData");

        if (providerData != null) {
            String providerId = providerData.get("providerId");
            ProviderAccountRequest accountRequest = null;
            switch (providerId) {
                case "facebook": {
                    String accessToken = ServletUtils.getCleanParam(request, "accessToken");
                    if (accessToken == null) {
                        accessToken = providerData.get("accessToken");
                    }
                    accountRequest = Providers.FACEBOOK.account().setAccessToken(accessToken).build();
                    break;
                }
                case "github": {
                    String code = ServletUtils.getCleanParam(request, "code");
                    if (code == null) {
                        code = providerData.get("code");
                    }
                    accountRequest = Providers.GITHUB.account().setAccessToken(exchangeCodeForAccessToken(code, request)).build();
                    break;
                }
                case "google": {
                    String code = ServletUtils.getCleanParam(request, "code");
                    if (code == null) {
                        code = providerData.get("code");
                    }
                    accountRequest = Providers.GOOGLE.account().setCode(code).build();
                    break;
                }
                case "linkedin": {
                    String code = ServletUtils.getCleanParam(request, "code");
                    if (code == null) {
                        code = providerData.get("code");
                    }
                    accountRequest = Providers.LINKEDIN.account().setCode(code).build();
                    break;
                }
                default: {
                    log.error("No provider configured for " + providerId);
                }
            }

            return accountRequest;
        }

        log.warn("Provider data not found in request.");
        return null;
    }

    protected Application getApplication(HttpServletRequest request) {
        return ApplicationResolver.INSTANCE.getApplication(request);
    }

    private String exchangeCodeForAccessToken(String code, HttpServletRequest request) {
        final DefaultGithubProvider[] githubProvider = new DefaultGithubProvider[1];

        for (ApplicationAccountStoreMapping mapping : getApplication(request).getAccountStoreMappings()) {
            AccountStore accountStore = mapping.getAccountStore();

            AccountStoreVisitor accountStoreVisitor = new AccountStoreVisitor() {
                @Override
                public void visit(Group group) {
                }

                @Override
                public void visit(Directory directory) {
                    if ("github".equals(directory.getProvider().getProviderId())) {
                        githubProvider[0] = (DefaultGithubProvider) directory.getProvider();
                    }
                }

                @Override
                public void visit(Organization organization) {

                }
            };
            accountStore.accept(accountStoreVisitor);
        }

        Assert.notNull(githubProvider[0], "githubProvider cannot be null.");

        HttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost httpPost = new HttpPost(GITHUB_ACCESS_TOKEN_URL);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("code", code));
            nvps.add(new BasicNameValuePair("client_id", githubProvider[0].getClientId()));
            nvps.add(new BasicNameValuePair("client_secret", githubProvider[0].getClientSecret()));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpPost.addHeader("Accept", "application/json");

            HttpResponse response = client.execute(httpPost);
            ObjectMapper objectMapper = new ObjectMapper();

            //noinspection unchecked
            Map<String, String> result = objectMapper.readValue(response.getEntity().getContent(), Map.class);
            return result.get(GITHUB_ACCESS_TOKEN_FIELD);
        } catch (Exception e) {
            log.error("Couldn't exchange GitHub oAuth code for an access token", e);
            throw new RuntimeException(e);
        }
    }
}
