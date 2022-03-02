package com.example;

import java.util.List;

public class App {

    public static S3Client s3 = new S3Client();

    private static AmazonS3 s3;

    public static void main(String[] args) {

        String access_key = "test";
        String secret_key = "test";
        String region = "us-east-1";
        String buck_name = "girbuck";
        String file_name = "example.yml";

        s3.setCredentials(access_key,secret_key,region);
        Map<String, Object> data = s3.readYamlFile(buck_name,file_name);
        System.out.println(data);
   
    }

}
