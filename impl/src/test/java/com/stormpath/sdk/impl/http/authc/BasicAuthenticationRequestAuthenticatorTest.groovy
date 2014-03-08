/*
 * Copyright 2014 Stormpath, Inc.
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
package com.stormpath.sdk.impl.http.authc

import com.stormpath.sdk.client.ApiKey
import com.stormpath.sdk.impl.http.HttpHeaders
import com.stormpath.sdk.impl.http.Request
import com.stormpath.sdk.impl.http.support.RequestAuthenticationException
import com.sun.org.apache.xml.internal.security.utils.Base64

import static org.testng.Assert.*
import static org.easymock.EasyMock.*
import org.junit.Test

import java.text.SimpleDateFormat

/**
 * @since 0.9.3
 */
class BasicAuthenticationRequestAuthenticatorTest {

    private static final String TIMESTAMP_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String STORMPATH_DATE_HEADER = "X-Stormpath-Date";
    private static final String TIME_ZONE = "UTC";

    @Test
    public void test(){

        def id = "fooId"
        def secret = "barKey"
        String authorizationHeader = com.stormpath.sdk.impl.util.Base64.encodeToString((id + ":" + secret).getBytes("UTF-8"), false);

        def request = createStrictMock(Request)
        def apiKey = createStrictMock(ApiKey)

        HttpHeaders headers = new HttpHeaders()

        assertNull(headers.get(AUTHORIZATION_HEADER))

        expect(request.getHeaders()).andReturn(headers) times(2)
        expect(apiKey.getId()).andReturn(id)
        expect(apiKey.getSecret()).andReturn(secret)

        replay(request, apiKey)

        def requestAuthenticator = new BasicAuthenticationRequestAuthenticator();
        requestAuthenticator.authenticate(request, apiKey)

        assertEquals(headers.get(AUTHORIZATION_HEADER).size(), 1)
        assertEquals(headers.get(AUTHORIZATION_HEADER).get(0), "Basic " + authorizationHeader)
        assertEquals(headers.get(STORMPATH_DATE_HEADER).size(), 1)

        try {
            def stormpathDateHeader = headers.get(STORMPATH_DATE_HEADER).get(0)
            SimpleDateFormat timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT)
            timestampFormat.setTimeZone(new SimpleTimeZone(0, TIME_ZONE))
            Date stormpathDate = timestampFormat.parse(stormpathDateHeader)
            def date = new Date()
            assertTrue(stormpathDate.before(date))
        } catch(Exception e) {
            fail("Exception validating generated date in header: " + e.getMessage())
        }

        verify(request, apiKey)

    }

}