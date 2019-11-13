package com.swagger.config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.swagger.models.SwaggerConfigObject;
import com.swagger.utils.JsonParser;

@Component
public class SwaggerServiceConfig {

    @Value("${configuration_file_location}")
    private String swaggerConfigFile;

    private static final String DEFAULT_SWAGGER_CONFIG_FILE_NAME = "SwaggerConfigFile.json";

    public List<SwaggerConfigObject> getSwaggerConfigObject()
        throws IOException {

        File configFile = new File(swaggerConfigFile);
        if (!configFile.exists()) {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            configFile = new File(classLoader.getResource(
                        DEFAULT_SWAGGER_CONFIG_FILE_NAME).getFile());
        }
        SwaggerConfigObject[] swaggerServiceConfigs = JsonParser.convertFileContentToJavaObject(
                    configFile, SwaggerConfigObject[].class);

        return Arrays.asList(swaggerServiceConfigs);
    }
}
