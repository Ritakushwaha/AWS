package com.example;

/**
 * Hello world!
 *
 */
public class App 
{
    List<String> productList = new ArrayList<>();
    Random random = new Random();

    public static void main( String[] args )
    {
        App app = new App();
        app.populateProductList();
        //1. getClient
        AmazonKinesis kinesisClient = KinesisClient.getKinesisClient();
        //2. PutRecordsRequest
        List<PutRecordRequestEntry> requestEntryList = app.getRecordsRequestList();

        PutRecordsRequest recordsRequest = new PutRecordsRequest();
        recordsRequest.setStreamName("teststream1");
        recordsRequest.setRecords(requestEntryList);
        
        //3. putrecord(single record) or putrecords(multiple records upto 500 in single API call)
        PutRecordsResult results=kinesisClient.putRecords(recordsRequest);
        System.out.println(results);
    }

    private List<PutRecordRequestEntry> getRecordsRequestList(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<PutRecordRequestEntry> putRecordsRequestEntries = new ArrayList<>();
        for(Order order: getOrderList()){
            PutRecordsRequestEntry requestEntry = new PutRecordsRequestEntry();
            requestEntry.setData(ByteBuffer.wrap(gson.toJson(order).getBytes()));
            requestEntry.setPartitionKey(UUID.randomUUID().toString());
            putRecordsRequestEntries.add(requsetEntry);
        }
        return putRecordsRequestEntries;
    }

    private List<Order> getOrderList(){
        List<Order> orders = new ArrayList<>();
        for(int i=0;i<500;i++){
            Order order = new Order();
            order.setOrderId(random.nextInt());
            order.setProduct(productList.get(random.nextInt(productList.size())));
            order.setQuantity(random.nextInt(20));

            orders.add(order);
        }
        return orders;
    }

    private void populateProductList(){
        productList.add("shirt");
        productList.add("t-shirt");
    }
}
