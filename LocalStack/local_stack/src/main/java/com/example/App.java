package com.example;

import java.util.List;
import java.util.Map;

public class App {

    public static S3Client s3 = new S3Client();

    public static void main(String[] args) throws Exception{

        String access_key = "test";
        String secret_key = "test";
        String region = "us-east-1";
        String buck_name = "girbuck";
        String file_name = "example.yml";
        
        /*
        s3.setCredentials(access_key,secret_key,region);
        byte[]arr = s3.readFile(buck_name,file_name);
        System.out.println(arr);
        */
   
    }

}
