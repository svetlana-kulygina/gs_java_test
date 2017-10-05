sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
echo "deb http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
sudo apt-get update
sudo apt-get install -y mongodb-org
sudo apt-get -y install openjdk-8-jdk
export JAVA_HOME=/usr/lib/jvm/export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
sudo mkdir ../../data
sudo mkdir ../../data/db
sudo chmod -R 777 ../../data/db
sudo service mongod start
cd ../../opt/vm-project
sudo chmod -R 777 ./
./gradlew clean build
java -jar build/libs/gs-java-test-1.0.jar