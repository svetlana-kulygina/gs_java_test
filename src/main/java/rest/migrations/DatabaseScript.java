package rest.migrations;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DatabaseScript {

    private String name;
    private String body;

    public DatabaseScript() {
    }

    public DatabaseScript(String scriptName, String scriptBody) {
        this.name = scriptName;
        this.body = scriptBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
