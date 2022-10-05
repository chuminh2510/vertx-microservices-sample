package com.minhcv.vertx.user;


import com.minhcv.vertx.user.config.DbConfig;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Using Vert.x Web
 */
public class UserVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new UserVerticle());
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Handler<AsyncResult<Void>> dbMigrationResultHandler = result -> this.handleMigrationResult(startPromise, result);
        Handler<Promise<Void>> dbMigrationHandler = result -> DbConfig.databaseMigration(result);
        vertx.executeBlocking(dbMigrationHandler, dbMigrationResultHandler);

        Router router = Router.router(vertx);
        router.route("/hello").handler(context -> {
            String address = context.request().connection().remoteAddress().toString();
            MultiMap queryParams = context.queryParams();
            String name = queryParams.contains("name") ? queryParams.get("name") : "unknow";
            context.json(new JsonObject()
                    .put("name", name)
                    .put("address", address)
                    .put("message", "Hello!!!!"));
        });
        vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }


    void handleMigrationResult(Promise<Void> start, AsyncResult<Void> result) {
        if (result.failed()) {
            System.out.println("handleMigrationResult::failed");
            start.fail(result.cause());
            return;
        }
        System.out.println("handleMigrationResult::successful");
    }
}
