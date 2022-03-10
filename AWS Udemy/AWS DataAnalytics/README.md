## Collection 

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

### 1. Kinesis Data Stream
<ol>
<li>Kinesis Stream Records</li>
<ul>
<li>Data Blob - upto 1MB </li>
<li>Record Key </li>
<li>Sequence number </li>
</ul>

<li>Kinesis Producer</li>
<ul>
    <li>Kinesis Producer SDK</li>
    <ul>
        <li>PutRecord (one record)\PutRecords (many records)</li>
        <li>If limit exceeeds throws ProvisionedThroughputExceeded</li>
        <ul>
            <li>Solution: Retries with backoff, Increase shards (scaling), Ensure partition key is a good one</li>
        </ul>
    </ul>
    <li>Kinesis Producer Library (KPL)</li>
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
    <li>Kinesis Agent</li>
    <ul>
        <li>monitor log files and send them to Kinesis Data Streams</li>
        <li>handles file rotation, checkpointing, and retry upon failures</li>
        <li>metrics to cloudwatch</li>
        <li>pre process data before sending to streams</li>
    </ul>
    <li>Spark, Log4j, Kafka</li>
</ul>
<li>Kinesis Consumer</li>
<ul>
    <li>Kinesis Consumers - Classic</li>
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
    <li>Kinesis Enhanced Fan Out</li>
<ol>

### 2. Kinesis Data Analytics
### 3. Kinesis Firehorse