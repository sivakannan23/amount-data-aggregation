Amount Data Aggregation Service.

Implementation:
1. The application has 3 layers. The layers are Controller, service & Aggregation Engine (holds the data).
2. Api & service calls are thread safe.
3. Service calls invokes aggregation engine via thread pool and it has blocking queue for back pressure kind of data handling.
4. Scheduledthreadpool is used to remove expired values from the aggregation data.

Aggregation Engine:
 It has following data structures for holding related data,
    a. priority queue for storing incoming data.
    b. Treeset for holding buckets for min & max for aggregation data. The bucket size is configurable.








