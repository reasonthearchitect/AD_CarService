package com.rta.carstore.generated.web;

import com.rta.carstore.generated.web.filter.CaosFilter;
import com.rta.carstore.generated.web.filter.LifeTimeSessionAndRequestTokenFilter;
import com.rta.carstore.generated.web.filter.RolesFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by rparry on 10/13/15.
 */
@Component
public class FilterRegistration {

    @Bean
    public FilterRegistrationBean lifeTimeSessionAndRequestTokenFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(lifeTimeSessionAndRequestTokenFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("lifeTimeSessionAndRequestTokenFilter");
        return registration;
    }

    @Bean(name = "lifeTimeSessionAndRequestTokenFilter")
    public LifeTimeSessionAndRequestTokenFilter lifeTimeSessionAndRequestTokenFilter() {
        return new LifeTimeSessionAndRequestTokenFilter();
    }

    @Bean(name = "rolesFilter")
    public RolesFilter regesterRolesFilter() {
        return new RolesFilter();
    }

    @Bean(name = "caosFilter")
    public CaosFilter regesterCaosFilter() {
        return new CaosFilter();
    }
}
