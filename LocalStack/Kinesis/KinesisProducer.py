import json
from time import sleep
import random
import datetime
import boto3

KinesisClient = boto3.client('kinesis',aws_access_key_id="temp",aws_secret_access_key="temp",region_name="ap-south-1",endpoint_url='http://127.0.0.1:4566')

thing_id = 'random'

def getData(count):
    if count % 10:
        return {
            'TIME': datetime.datetime.now().isoformat(),
            'LEVEL': random.choice(['INFO', 'ERROR', 'DEBUG']),
            'POD': 'kubernetes' + str(random.randint(1, 5)),
            'APP': 'users',
            'class': "discovery.commons.logging.AccessLoggerHandle",
            'path': "users/me",
            'MESSAGE': 'It is just a test kubernetes , no panic asd !',
            "msg": "[min_validation_flow] received error json: cannot unmarshal number 342 into Go struct field Ad.breakIndex of type uint8 when unmarshalling/publishing event: {\n \"payload\": {\n  \"action\": \"start\",\n  \"adId\": \"\",\n  \"adIndex\": 0,\n  \"adUnitId\": \"59149953\",\n  \"assetId\": \"0c646f3bd06946b8b25f2cecbf94ca38\",\n  \"breakIndex\": 342,\n  \"breakType\": \"mid\",\n  \"campaignId\": \"\",\n  \"clientAttributes\": {\n   \"advertisingId\": \"8f0e8895-964e-5f21-aee8-4507a3c7876c\",\n   \"browser\": {\n    \"name\": null,\n    \"version\": null\n   },\n   \"device\": {\n    \"brand\": \"roku\",\n    \"manufacturer\": \"roku\",\n    \"model\": \"32S331\",\n    \"version\": \"8000X\"\n   },\n   \"id\": \"15da8aca-7505-5f0d-8fec-a36fb24700fd\",\n   \"limitAdTracking\": false,\n   \"os\": {\n    \"name\": \"roku\",\n    \"version\": \"9.99 build 99999\"\n   },\n   \"type\": \"settop\"\n  },\n  \"content\": {\n   \"streamType\": \"live\",\n   \"videoId\": \"55c51d636b66d1c17a1ffb9e\"\n  },\n  \"contentPosition\": 114525,\n  \"creativeId\": \"\",\n  \"duration\": 30,\n  \"isPaused\": false,\n  \"playbackType\": \"userInitiated\",\n  \"playerName\": \"Roku Scene Graph Player\",\n  \"productAttributes\": {\n   \"buildNumber\": \"0\",\n   \"name\": \"dsc\",\n   \"version\": \"4.2.0\"\n  },\n  \"streamPosition\": 114537,\n  \"streamProviderSessionId\": \"41daf408aabd45f78b9e0f404143738a\",\n  \"streamTimer\": 114536712,\n  \"thirdPartyCreativeId\": \"29879250\",\n  \"uuid\": \"f7d8f4b9-0686-4544-bd79-5a1f9058e36d\"\n },\n \"sessionid\": \"a6facbfe-de31-4387-94c6-b3e931d3297f\",\n \"sessiontimer\": 114544888,\n \"timeoffset\": -420,\n \"timestamp\": \"2021-12-31T07:43:23.221Z\",\n \"type\": \"ad\",\n \"uuid\": \"90ef794c-10f9-451f-88fe-59d776f034a9\",\n \"version\": \"v2\"\n}"
                }
    if count % 24 :
        sleep(random.randint(0,1))
        return {
            'TIME': datetime.datetime.now().isoformat(),
            'LEVEL': random.choice(['INFO', 'ERROR', 'DEBUG']),
            'POD': 'kubernetes' + str(random.randint(1, 5)),
            'AGE': str(random.randint(10, 30)),
            'MESSAGE': 'It is just a test, no panic!'}


def putData(count):
    data =getData(count)
    response = KinesisClient.put_record( StreamName='teststream',Data=json.dumps(data),PartitionKey=thing_id)
    print(data)
    print(response)


count=1
while True :
    putData(count)
    count+=1
    sleep(random.uniform(0.1, 1.2))