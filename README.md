# AWS Local Stack

## Make directory
```
mkdir local_stack
cd local_stack
```

## Visit the below link for docker-compose.yml file
```
https://github.com/localstack/localstack/blob/master/docker-compose.yml
```

## Make changes as per the required services

## Create a temporary storage
```
env_TMPDIR=/private$TMPDIR docker-compose up
```

## awscli-local for localstack
```
https://github.com/localstack/awscli-local
```

## Making loclstack maven project

```
mvn archetype:generate -DgroupId=com.example -DartifactId=local_stack -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

## AWS CLI Documentation
```
https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html
```

### AWS configuration
```
aws configure
```

```
AWS Access Key ID [****************test]: temp
AWS Secret Access Key [****************test]: temp
Default region name [us-east-1]: ap-south-1
Default output format [json]: json
```

### AWS Kinesis Commands
```
https://docs.aws.amazon.com/streams/latest/dev/fundamental-stream.html
```

#### Create a Kinesis Stream
```
https://docs.aws.amazon.com/cli/latest/reference/kinesis/create-stream.html
```

```
aws --endpoint-url=http://localhost:4566 kinesis create-stream --stream-name teststream --shard-count 1
```

#### List the Kinesis stream
```
aws --endpoint-url=http://localhost:4566 kinesis list-streams

#output
{
    "StreamNames": [
        "teststream"
    ]
}
```
