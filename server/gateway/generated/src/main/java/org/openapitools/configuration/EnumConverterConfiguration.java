package org.openapitools.configuration;

import org.openapitools.model.InviteStatus;
import org.openapitools.model.Location;
import org.openapitools.model.RequestStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration(value = "org.openapitools.configuration.enumConverterConfiguration")
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.inviteStatusConverter")
    Converter<String, InviteStatus> inviteStatusConverter() {
        return new Converter<String, InviteStatus>() {
            @Override
            public InviteStatus convert(String source) {
                return InviteStatus.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.locationConverter")
    Converter<String, Location> locationConverter() {
        return new Converter<String, Location>() {
            @Override
            public Location convert(String source) {
                return Location.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.requestStatusConverter")
    Converter<String, RequestStatus> requestStatusConverter() {
        return new Converter<String, RequestStatus>() {
            @Override
            public RequestStatus convert(String source) {
                return RequestStatus.fromValue(source);
            }
        };
    }

}
