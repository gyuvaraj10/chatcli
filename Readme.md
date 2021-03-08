To package the server jar, run the command:
```shell script
mvn clean compile assembly:single -DskipTests -Pserver
```

To package the client jar, run the command:

```shell script
mvn clean compile assembly:single -DskipTests -Pclient
```
