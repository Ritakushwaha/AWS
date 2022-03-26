# Collection 

### Real Time - Immediate Actions
```
Kinesis Data Streams (KDS)
Simple Queue Service (SQS)
Internet of things (IOT)
```
### Near Real Time - Immediate Actions
```
Kinesis Data Firehorse (KDF)
Data Migration Service (DMS)
```
### Batch - Historical Analysis
```
Snowball
Data Pipeline
```

## AWS Kinesis Overview
- alternative to Apache Kafka
- for logs, clicks, streaming data, metrics, IOT data
- real time big data
- stream processing framework

### 1. Kinesis Data Stream (Real Time)
<div>
<ol>
<li><a href = #stream>Kinesis Stream Records</a></li>
<li><a href = #producer>Kinesis Producer</a></li>
<li><a href=#consumer>Kinesis Consumer</a></li>
<li><a href=#scaling>Kinesis Scaling Operations</a></li>
<li><a href=#duplicate>Handling Duplicates for Producers</a></li>
<li><a href=#security>Kinesis Security</a></li>
</ol>

<div id="stream">
<h2>Kinesis Stream Records</h2>
    <ul>
        <li>Data Blob - upto 1MB </li>
        <li>Record Key </li>
        <li>Sequence number </li>
    </ul>
</div>

<div id="producer">
<h2>Kinesis Producer</h2>
<ul>
    <h3><li>Kinesis Producer SDK</li></h3>
    <ul>
        <li>PutRecord (one record)\PutRecords (many records)</li>
        <li>If limit exceeeds throws ProvisionedThroughputExceeded</li>
        <ul>
            <li>Solution: Retries with backoff, Increase shards (scaling), Ensure partition key is a good one</li>
        </ul>
    </ul>
    <h3><li>Kinesis Producer Library (KPL)</li></h3>
    <ul>
        <li>used for building high performance, long running producers</li>
        <li>automated and configurable retry mechanism</li>
        <li>Synchronous and Asynchronous APIs(better performance)</li>
        <li>Submit metrics to CloudWatch for monitoring</li>
        <li>Batching : we can influence batching by introducing some delay with RecordMaxBufferedTime (default 100ms)</li>
        <ul>
            <li>Collect</li>
            <li>Aggregate</li>
        </ul>
        <li>Compression should be implemented by user</li>
        <li>Records must be de-coded with KCl or other helper library</li>
    </ul>
    <h3><li>Kinesis Agent</li></h3>
    <ul>
        <li>monitor log files and send them to Kinesis Data Streams</li>
        <li>handles file rotation, checkpointing, and retry upon failures</li>
        <li>metrics to cloudwatch</li>
        <li>pre process data before sending to streams</li>
    </ul>
    <h3><li>Spark, Log4j, Kafka</li></h3>
</ul>
</div>

<div id="consumer">
<h2>Kinesis Consumer</h2>
<ul>
    <h3><li>Kinesis Consumers - Classic</li></h3>
    <ul>
        <li>Kinesis SDK</li>
        <ul>
            <li>GetRecords</li>
            <ul>
            <li>Classic Kinesis - records polled by consumers from a shard</li>
            <li>Each shard has 2MB total aggregate throughput</li>
            <li>Maximum of 5 GetRecords API calls per shard per second = 200 ms Latency</li>
            </ul>
        </ul>
        <li>Kinesis Client Library</li>
            <ul>
                <li>read records from Kinesis Produced with KPL (de-aggregated)</li>
                <li>Shard Discovery - share shards with multiple consumers in one "group"</li>
                <li>Checkpointing feature to resume progress</li>
                <li>ExpiredIteratorException, if incresed WCU(write capacity unit)</li>
            </ul>
        <li>Kinesis Connector Library</li>
        <ul>
            <li>older java library, write data to s3, dynamo db, redshift, elastic search etc.</li>
        </ul>
        <li>Spark, Log4j, Kafka etc.</li>
        <li>Kinesis Firehose</li>
        <li>AWS Lambda</li>
        <ul>
            <li>can source records from Kinesis Data Streams</li>
            <li>lambda consumer has a library to de-aggregate record from the KPL</li>
            <li>can used to run light-weight ETL to s3, dynamoDb,redshift,elastic search etc.</li>
            <li>used to trigger notifications/ send emails in real time.</li>
        </ul>
    </ul>
    <h3><li>Kinesis Enhanced Fan Out</li></h3>
    <ul>
        <li>works with KCL 2.0 and lambda (nov 2018)</li>
        <li>each consumer get 2MB/s of provisioned throughout per shard, means 20 consumer -> 40 MB/s per shard</li>
        <li>Kinesis pushes data to consumers over HTTP/2</li>
        <li>Reduced Latency (~70ms)</li>
    </ul>
</div>

<div id="scaling">
<h2>Kinesis Scaling Operations</h2>
    <ul>
        <h3><li><b>Adding Shards known as Shard Splitting</b></li></h3>
        <li>increase the stream capacity</li>
        <li>used to divide hot shard</li>
        <li>old shard is closed and deleted once data in it expired.</li>
        <br>
        <h3><li><b>Merging Shards</b></li></h3>
        <li>decrease the stream capacity and saves cost</li>
        <li>group two shards with low traffic</li>
        <li>old shards are closed and deleted based on data expiration.</li>
        <br>
        <h3><li><b>Out of Order records after resharding</b></li></h3>
        <li>If you start reading the child before completing reading the parent, you could read data for a particular hash key out of order </li>
        <li>in order to prevetn that hapenning - after reshard read entire data from parent shard first until you don't have new records.</li>
        <li>KCL has this logic already built in</li>
        <br>
        <h3><li><b>Auto Scaling</b></li></h3>
        <li>not native feature</li>
        <li>UpdateShardCount is the API call</li>
        <li>we can implement autoscaling with AWS Lambda</li>
        <br>
        <h3><li><b>Limitation</b></li></h3>
        <li>resharding cannot be done in parallel, plan capacity in advance.</li>
        <li>can only perform one resharding operation at a time and it takes a few seconds (1000 shards takes 30k seconds)
    </ul>
</div>
<div id="duplicate">
<h2>Handling Duplicates for Producers</h2>
<ul>
<li>producer retries can create duplicates due to network timeouts</li>
<li>Fix: Embed unique record ID in the data to de-duplicate on the consumer side</li>
<br>
<li>consumer retries can make your app read the same data twice, it cna happen when worker terminates unexpectedly, worker instances are added or removed, shards merged or split, or at the time of app deployment.</li>
<li>Fix: make consumer app idempotent. Or handle the duplicate data at final end.</li>
</ul>
</div>

<div id = "security">
<h2>Kinesis Security</h2>
<ul>
<li>control access/authorization using IAM policies</li>
<li>encryption in flight using HTTPS endpoints</li>
<li>encryption at rest using KMS</li>
<li>client side encryption should be manually done</li>
<li>VPC endpoints available for Kinesis to access within VPC</li>
</ul>
</div>
</div>

### 2. Kinesis Data Analytics
### 3. Kinesis Data Firehorse (Near Real Time)
<div id="kdf">
    <ul>
        <li>fully managed service, no administration</li>
        <li>near real time (latency 60 seconds minimum for non full batches)</li>
        <li>load data into Redshift/S3/Elastic Search/Splunk</li>
        <li>automatic scaling</li>
        <li>data conversion - json to Parquet/ORC(only for s3)</li>
        <li>data transformation - csv to json (using Lambda)</li>
        <li>supports compression when target is S3 (gzip, zip and snappy)</li>
        <li>Spark/KCL do not read from KDF</li>
        [KDF Flow](./images/KDF%20flow.png)
    </ul>
</div>
<div id="buffer">
<h2>Firehose Buffer Sizing</h2>
    <ul>
        <li>accumulates records in buffer</li>
        <li>flushed based on time(min 1 minute) and size(few mb) rules</li>
        <li>automatically increase buffer size to increase throughput</li>
    </ul>
<h2>Kinesis Data Stream v/s Kinesis Firehose</h2>
<h3>Streams</h3>
    <ul>
        <li>custom code for producer/consumer</li>
        <li>real time (~200 ms for classic, ~70 ms for enhaced fan-out)</li>
        <li>must manage scaling (shard splitting/merging)</li>
        <li>data storage for 1-365 days, replay capability, multi consumers</li>
        <li>use with lambda to insert data in real-time to Elastic Search</li>
    </ul>
<h3>Firehose</h3>
    <ul>
        <li>fully managed, send to s3. redshift, elastic search, splunk</li>
        <li>serverless data transformations with lambda</li>
        <li>near real time (lowest buffer time is 1 minute)</li>
        <li>automated scaling</li>
        <li>no data storage</li>
    </ul>
</div>

# Storage
## S3 (Simple Storage Service)

