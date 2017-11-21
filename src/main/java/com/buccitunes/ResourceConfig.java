package com.buccitunes;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.buccitunes.miscellaneous.FileManager;

@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String resourceDirectoryAlias = FileManager.getResourceAliasPath();
		String resourceDir = FileManager.getFilesPath();
        registry.addResourceHandler(resourceDirectoryAlias).addResourceLocations("file:" + resourceDir);
        super.addResourceHandlers(registry);
    }
}
