package com.rta.carstore.generated.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories("com.rta.carstore.repository.search")
public class ElasticConfiguration { }
