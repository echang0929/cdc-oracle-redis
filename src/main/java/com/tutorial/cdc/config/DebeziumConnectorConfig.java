package com.tutorial.cdc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConnectorConfig {

    @Value("${oracle.datasource.hostname}")
    private String dbHostname;

    @Value("${oracle.datasource.port}")
    private String dbPort;

    @Value("${oracle.datasource.user}")
    private String dbUser;

    @Value("${oracle.datasource.password}")
    private String dbPassword;

    @Value("${oracle.datasource.dbname}")
    private String dbDbname;

    @Value("${oracle.datasource.pdb.name}")
    private String dbPdbName;

    @Bean
    public io.debezium.config.Configuration customerConnector() {
        String CUSTOMER_TABLE_NAME = "C##DBZUSER.CUSTOMERS";
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.oracle.OracleConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "customer-offset.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("include.schema.changes", "false")
                .with("schema.history.internal","io.debezium.storage.file.history.FileSchemaHistory")
                .with("schema.history.internal.file.filename","schema_history.dat")
                .with("name", "customer-oracle-connector")
                .with("database.server.name", dbHostname + "-" + dbDbname)
                .with("database.server.id", "12345")
                .with("database.hostname", dbHostname)
                .with("database.port", dbPort)
                .with("database.user", dbUser)
                .with("database.password", dbPassword)
                .with("database.dbname", dbDbname)
                .with("database.pdb.name", dbPdbName)
                .with("topic.prefix", "test")
                .with("table.include.list", CUSTOMER_TABLE_NAME)
                .build();
    }
}
