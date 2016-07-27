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
package com.stormpath.sdk.client;


public enum AuthenticationSchemes {
/*
BASIC("com.stormpath.sdk.impl.http.authc.BasicRequestAuthenticator"), //HTTP Basic Authentication		+    HttpAuthenticator getAuthenticator();
-    SAUTHC1("com.stormpath.sdk.impl.http.authc.SAuthc1RequestAuthenticator"); //Digest Authentication
-
-    private final String requestAuthenticatorClassName;
-
-    private AuthenticationScheme(String requestAuthenticatorClassName) {
-        Assert.notNull(requestAuthenticatorClassName, "requestAuthenticatorClassName cannot be null");
-        this.requestAuthenticatorClassName = requestAuthenticatorClassName;
-    }
-
-    public String getRequestAuthenticatorClassName() {
-        return this.requestAuthenticatorClassName;
-    }
 */

    BASIC,
    SAUTHC1

}
