package com.searchengine.yalla.config;

import com.searchengine.yalla.entity.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;

@Configuration
public class RepositoryConfig  implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config)
    {
        // To expose the Id in the returned major, use this(Product.class) or the below
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(Type::getJavaType).toArray(Class[]::new));
    }
}
