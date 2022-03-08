import boto3
import json
from datetime import datetime
import time

my_stream_name = 'teststream'

kinesis_client = boto3.client('kinesis',aws_access_key_id="temp",aws_secret_access_key="temp",region_name="ap-south-1",endpoint_url='http://127.0.0.1:4566')

response = kinesis_client.describe_stream(StreamName=my_stream_name)

print("response : "+str(response))

my_shard_id = response['StreamDescription']['Shards'][0]['ShardId']

print("Shard Id : "+str(my_shard_id))

shard_iterator = kinesis_client.get_shard_iterator(StreamName=my_stream_name,ShardId=my_shard_id, ShardIteratorType='LATEST')

print("Shard Iterator : "+str(shard_iterator))

my_shard_iterator = shard_iterator['ShardIterator']

print("My Shard Iterator : "+str(my_shard_iterator))

record_response = kinesis_client.get_records(ShardIterator=my_shard_iterator,
                                              Limit=2)
print("got record response : "+str(record_response))
while 'NextShardIterator' in record_response:
    l=[]
    record_response = kinesis_client.get_records(ShardIterator=record_response['NextShardIterator'], Limit=2)
    for data in record_response["Records"] :
        l.append(data['Data'].strip())
    print(l)

    # wait for 5 seconds
    time.sleep(1)
