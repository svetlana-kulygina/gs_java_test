package rest.migrations;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.stereotype.Service;

@Service
public class MongoDBMigrator {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void runScripts(Resource[] resources) throws IOException {

        List<String> executedScriptNames = getExecutedScriptsFromDatabase();
        Map<String, String> allScripts = readAllScripts(resources);

        for (String scriptName : sortScriptNames(allScripts.keySet())) {
            if (!scriptInDatabase(executedScriptNames, scriptName)) {
                execute(scriptName, allScripts.get(scriptName));
            }
        }
    }

    private List<String> getExecutedScriptsFromDatabase() {
        List<DatabaseScript> databaseScripts = mongoTemplate.findAll(DatabaseScript.class);

        List<String> executedScriptNames = new ArrayList<>();
        for (DatabaseScript s : databaseScripts) {
            executedScriptNames.add(s.getName());
        }
        return executedScriptNames;
    }

    private Map<String, String> readAllScripts(Resource[] resources) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();

        for (Resource r : resources) {
            String scriptName = r.getFilename();
            String script = getResourceContent(r);
            script = appendFunctionBody(script);
            hashMap.put(scriptName, script);
        }
        return hashMap;
    }

    protected String getResourceContent(Resource r) throws IOException {
        try (InputStream is = r.getInputStream()) {
            return IOUtils.toString(is);
        }
    }

    private String appendFunctionBody(String string) {
        return "function() {" + string + "}";
    }

    private TreeSet<String> sortScriptNames(Set<String> allScriptNames) {
        return new TreeSet<>(allScriptNames);
    }

    private boolean scriptInDatabase(List<String> executedScriptNames, String scriptName) {
        return executedScriptNames.contains(scriptName);
    }

    private void execute(String scriptName, String scriptBody) {
        ScriptOperations scriptOps = mongoTemplate.scriptOps();
        scriptOps.execute(new ExecutableMongoScript(scriptBody));
        mongoTemplate.save(new DatabaseScript(scriptName, scriptBody));
    }
}
