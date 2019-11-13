package com.swagger.provider;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

import com.swagger.config.SwaggerServiceConfig;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Controller
@Primary
class PropertyResourceProvider implements SwaggerResourcesProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(
                PropertyResourceProvider.class);

    @Autowired
    private SwaggerServiceConfig swaggerServiceConfig;

    @Override
    public List<SwaggerResource> get() {
        try {
            return swaggerServiceConfig.getSwaggerConfigObject().stream().map(
                        serviceObject -> {
                            SwaggerResource swaggerResource = new SwaggerResource();
                            swaggerResource.setName(serviceObject.getName());
                            swaggerResource.setLocation(serviceObject.getUrl());
                            swaggerResource.setSwaggerVersion(
                                        serviceObject.getVersion());
                            return swaggerResource;

                        }).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return Collections.emptyList();
    }

}
