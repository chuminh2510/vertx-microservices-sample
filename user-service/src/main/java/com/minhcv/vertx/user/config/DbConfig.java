package com.minhcv.vertx.user.config;

import io.vertx.core.Promise;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

public class DbConfig {
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 5432;
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";


    public static void databaseMigration(Promise<Void> promise) {
        try {
            Flyway fl = Flyway.configure().dataSource(String.join("", "jdbc:postgresql://", DB_HOST, ":", String.valueOf(DB_PORT), "/", DB_NAME), DB_USER, DB_PASSWORD).load();
            fl.migrate();
            promise.complete();
        } catch (FlywayException ex) {
            ex.printStackTrace();
            promise.fail(ex);
        }

    }


}
