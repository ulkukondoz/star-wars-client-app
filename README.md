## How to Use
The executable jar is available under the project folder.
```bash
cd star-wars-client-app
```

To start the client:
```bash
java -jar start-wars-client-app-1.0-SNAPSHOT-jar-with-dependencies.jar
```

To create an executable jar
```bash
mvn clean package
```

The executable jar can be found under the ```star-wars-client-app/target``` .
To run the application using the newly created jar:
```bash
java -jar target/start-wars-client-app-1.0-SNAPSHOT-jar-with-dependencies.jar
```

- Arguments  
Start Wars Client application works with clonardo/socketio-backend docker image.

| cmd arg    | default value         | 
|------------|-----------------------|
| server URI | http://localhost:3000 |


