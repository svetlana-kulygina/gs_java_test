package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import rest.migrations.MongoDBMigrator;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private MongoDBMigrator migrator;

    @Autowired
    private ApplicationContext ctx;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        migrator.runScripts(ctx.getResources("classpath:db/*.js"));
    }
}