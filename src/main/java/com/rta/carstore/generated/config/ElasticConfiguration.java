package com.rta.carstore.generated.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories("com.rta.carstore.repository.search")
public class ElasticConfiguration {

    private final Logger log = LoggerFactory.getLogger(ElasticConfiguration.class);
}
