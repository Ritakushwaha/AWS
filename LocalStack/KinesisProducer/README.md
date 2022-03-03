## 1. Added dependencies

```
<!-- https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-kinesis -->
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-kinesis</artifactId>
    <version>1.12.169</version>
</dependency>

<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.9.0</version>
</dependency>
```

## 2. Build the project
```
mvn clean install
```

### If you get Error 
```
[ERROR] Source option 5 is no longer supported. Use 6 or later.
[ERROR] Target option 1.5 is no longer supported. Use 1.6 or later.
```
### Add the dependency
```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

## 3. Try again Step 2

## 4. Create new folder under src/main/java/com/example
```
aws
```

## 5. Now under src/main/java/com/example/aws create java class
```
AWSKinesisClient.java
```
