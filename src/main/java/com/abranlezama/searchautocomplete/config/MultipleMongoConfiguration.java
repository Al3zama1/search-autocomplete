package com.abranlezama.searchautocomplete.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MultipleMongoConfiguration {

    @Primary
    @Bean(name = "querydbProperties")
    @ConfigurationProperties(prefix = "mongodb.querydb")
    public MongoProperties getQueryDatabaseProperties() {
        return new MongoProperties();
    }

    @Bean(name = "triedbProperties")
    @ConfigurationProperties(prefix = "mongodb.triedb")
    public MongoProperties getTrieDatabaseProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "querydbMongoTemplate")
    public MongoTemplate querydbMongoTemplate() {
        return new MongoTemplate(querydbFactory(getQueryDatabaseProperties()));
    }

    @Bean(name = "triedbMongoTemplate")
    public MongoTemplate triedbMongoTemplate() {
        return new MongoTemplate(triedbFactory(getTrieDatabaseProperties()));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory querydbFactory(final MongoProperties properties) {
        String connectionString = String.format("mongodb://%s:%s@%s:%d/%s?authSource=%s",
                properties.getUsername(), new String(properties.getPassword()), properties.getHost(), properties.getPort(), properties.getDatabase(), properties.getAuthenticationDatabase());

        return new SimpleMongoClientDatabaseFactory(connectionString);
    }

    @Bean
    public MongoDatabaseFactory triedbFactory(final MongoProperties properties) {
        String connectionString = String.format("mongodb://%s:%s@%s:%d/%s?authSource=%s",
                properties.getUsername(), new String(properties.getPassword()), properties.getHost(), properties.getPort(), properties.getDatabase(), properties.getAuthenticationDatabase());

        return new SimpleMongoClientDatabaseFactory(connectionString);
    }
}
