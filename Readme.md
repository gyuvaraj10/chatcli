To package the server jar, run the command:
```shell script
mvn clean compile assembly:single -DskipTests -Pserver
```

To package the client jar, run the command:

```shell script
mvn clean compile assembly:single -DskipTests -Pclient
```


To start the sever, run the following command:
```shell script
java -jar server.jar
```

To start the client, run the following command:
```shell script
java -jar client.jar localhost 8888 <name>
```
example1:
```shell script
java -jar client.jar localhost 8888 Martin
```

example2:
```shell script
java -jar client.jar localhost 8888 Martin2
```