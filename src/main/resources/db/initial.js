var myDb = db.getSiblingDB('gs_java_test');
myDb.dropDatabase();
myDb.user.save({"name": "Alice", "email": "test1@example.com"});
myDb.user.save({"name": "Bob", "email": "test2@example.com"});