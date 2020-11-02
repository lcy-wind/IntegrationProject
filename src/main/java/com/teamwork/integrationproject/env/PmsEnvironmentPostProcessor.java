package com.teamwork.integrationproject.env;

import com.teamwork.integrationproject.utils.log.LogHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实现应用配置的自定义加载，方便配置文件可以自定义，不仅仅局限于application
 * Author anyho
 * Time   2020/3/5 21:35 星期四
 */
public class PmsEnvironmentPostProcessor implements EnvironmentPostProcessor
{
    /**
     * 默认启用dev环境
     */
    private static final String DEFAULT_ACTIVE_PROFILE = "dev";
    /**
     * 默认配置目录
     */
    private static final String DEFAULT_CONFIG_DIR = ResourceUtils.CLASSPATH_URL_PREFIX + "/env/";

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private final List<PropertySourceLoader> propertySourceLoaders;

    public PmsEnvironmentPostProcessor()
    {
        propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application)
    {
        List<String> activeProfiles = new ArrayList<>(Arrays.asList(environment.getActiveProfiles()));
        if (activeProfiles.size() < 1)
        {
            activeProfiles.add(DEFAULT_ACTIVE_PROFILE);
        }

        //classpath:config/dev/*-dev.yml
        List<String> locationPrefixs = new ArrayList<>();
        //global config file for all profile
        locationPrefixs.add(DEFAULT_CONFIG_DIR + "*");
        //profile config file
        activeProfiles.stream().forEach(profile -> {
            locationPrefixs.add(DEFAULT_CONFIG_DIR + profile + "/*");
        });

        for (String locationPrefix : locationPrefixs)
        {
            try
            {
                load(locationPrefix).stream().forEach(environment.getPropertySources()::addLast);
            }
            catch (IOException e)
            {
                LogHelper.debug(this, "load config file from {} failed!", locationPrefix, e);
            }
        }
    }

    private List<PropertySource<?>> load(String prefix) throws IOException
    {
        List<PropertySource<?>> propertySources = new ArrayList<>();
        for (PropertySourceLoader loader : propertySourceLoaders)
        {
            String[] fileExtensions = loader.getFileExtensions();
            for (String fileExtension : fileExtensions)
            {
                String locationPattern = prefix + "." + fileExtension;
                Resource[] resources = resourcePatternResolver.getResources(locationPattern);
                for (Resource resource : resources)
                {
                    propertySources.addAll(loader.load(resource.getFilename(), resource));
                }

                LogHelper.debug(this, "load config file {} success: {}", locationPattern, resources.length);
            }
        }

        return propertySources;
    }

}
