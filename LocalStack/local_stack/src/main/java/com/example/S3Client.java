package LocalStack.local_stack.src.main.java.com.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.flink.shaded.jackson2.org.yaml.snakeyaml.Yaml;

import java.io.FileOutputStream;
import java.util.Arrays;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class S3Client  {
    String accessKey;
    String secureAccessKey;
    String aws_region;


    public S3Client()
    {
        this.accessKey = "";
        this.secureAccessKey = "";
        this.aws_region = "";
    }

    public S3Client(String accessKey, String secureAccessKey, String aws_region)
    {
        this.accessKey = accessKey;
        this.secureAccessKey =secureAccessKey;
        this.aws_region = aws_region;
    }

    public void setCredentials(String accessKey, String secureAccessKey, String aws_region)
    {
        this.accessKey = accessKey;
        this.secureAccessKey =secureAccessKey;
        this.aws_region = aws_region;
    }

    void validate() throws  Exception
    {
        if (this.accessKey == null || this.accessKey == "" )
        {
            throw new Exception("Access Key must be provided");
        }

        if (this.secureAccessKey == null || this.secureAccessKey == "" )
        {
            throw new Exception("Secure Access Key must be provided");
        }

        if (this.aws_region == null || this.aws_region == "" )
        {
            throw new Exception("aws_region must be provided");
        }
    }

    public AmazonS3 getS3Client() throws Exception {
        this.validate();
        AWSCredentials auth = new BasicAWSCredentials(
                accessKey,
                secureAccessKey
        );
        AmazonS3 S3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(auth))
                .withRegion(aws_region)
                .build();

        return S3Client;
    }

    public void readFile1(String bucketName,String fileName) throws Exception
    {
        this.validate();
        FileOutputStream f_os = null;

        try
        {
            AmazonS3 S3Client = getS3Client();
            S3Object s3object = S3Client.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            byte[] arr = inputStream.readAllBytes();

            //byte array to file
            f_os = new FileOutputStream("./configs/output.yml");
            f_os.write(arr);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }
        finally{
            f_os.close();
        }
    }


    public Map<String,Object> readYamlFile(String bucketName,String fileName) throws Exception
    {
        this.validate();
        FileOutputStream f_os = null;

        try
        {
            AmazonS3 S3Client = getS3Client();
            S3Object s3object = S3Client.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            byte[] arr = inputStream.readAllBytes();

            InputStream test_ip = new ByteArrayInputStream(arr);
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(test_ip);
            return data;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }
        return null;
    }

    public byte[] readFile(String bucketName,String fileName) throws Exception
    {
        this.validate();

        try
        {
            AmazonS3 S3Client = getS3Client();
            S3Object s3object = S3Client.getObject(bucketName, fileName);
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            byte[] arr = inputStream.readAllBytes();
            return arr;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }
        return null;
    }

}