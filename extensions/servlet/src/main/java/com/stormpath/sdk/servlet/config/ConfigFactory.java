package com.stormpath.sdk.servlet.config;

import javax.servlet.ServletContext;

public interface ConfigFactory {

    Config createConfig(ServletContext servletContext);

}