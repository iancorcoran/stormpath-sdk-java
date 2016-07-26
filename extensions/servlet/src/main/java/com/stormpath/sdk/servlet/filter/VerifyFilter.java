/*
 * Copyright 2015 Stormpath, Inc.
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
package com.stormpath.sdk.servlet.filter;

import com.stormpath.sdk.servlet.filter.mvc.ControllerFilter;
import com.stormpath.sdk.servlet.mvc.VerifyController;

import javax.servlet.ServletException;

/**
 * @since 1.0.RC3
 */
public class VerifyFilter extends ControllerFilter {

    @Override
    protected void onInit() throws ServletException {
        VerifyController controller = new VerifyController(
                getConfig().getVerifyControllerConfig(),
                getConfig().getLoginControllerConfig().getUri(),
                getConfig().getLoginControllerConfig().getNextUri(),
                getClient(),
                getConfig().getProducesMediaTypes(),
                getConfig().isRegisterAutoLoginEnabled(),
                getConfig().getAuthenticationResultSaver(),
                getConfig().getAccountStoreResolver()
        );

        setController(controller);

        super.onInit();
    }
}
