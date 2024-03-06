# Kafka

## Why Kafka?
Kafka emerged as a solution to address the challenges of handling large-scale data streams, enabling organizations to build scalable, fault-tolerant, and real-time data pipelines for a wide range of use cases, including stream processing, data integration, event-driven architectures, and more.

## Features
- Kafka follows a publish-subscribe messaging model, where messages are published to topics and consumed by one or more subscribers (consumers).
- Producers publish messages to Kafka topics, and consumers subscribe to topics to receive messages, enabling decoupled and asynchronous communication.
- Kafka is built as a distributed system, allowing it to scale horizontally across multiple servers or nodes in a cluster.
- Kafka is optimized for high throughput and low latency, allowing it to process millions of messages per second with minimal delay.
- Kafka provides fault tolerance and data durability through data replication across multiple broker nodes.
- Kafka is highly scalable and elastic, allowing clusters to dynamically scale out or scale in based on workload and resource demands.
- Kafka supports exactly-once semantics for message delivery, ensuring that messages are processed exactly once even in the presence of failures or retries.

## Kafka Architecture
  - Topics: Topics are the core abstraction in Kafka, representing a stream of records or messages.
  - Partitions: Each topic is divided into one or more partitions, which are individual ordered and immutable sequences of messages.
  - Brokers:Brokers are the individual instances or servers that make up a Kafka cluster. Each broker is responsible for storing and serving data for one or more partitions of Kafka topics.
  - Producer: Producers are responsible for creating new messages and sending them to Kafka brokers for storage and processing.
  - Consumer: Consumers are client applications that subscribe to Kafka topics to receive and process messages. Consumers can consume messages from one or more partitions of a topic, enabling parallel message processing.
  - Consumer Groups: Consumer groups are logical groups of consumers that collectively consume messages from one or more topics.Kafka ensures that each message is consumed by only one consumer within a consumer group, enabling load balancing and fault tolerance.
  - ZooKeeper: ZooKeeper is used by Kafka for distributed coordination, configuration management, and cluster metadata storage. Kafka brokers register themselves with ZooKeeper, and ZooKeeper maintains the list of active brokers and cluster metadata. ZooKeeper is also used for leader election, partition reassignment, and handling broker failures.
  - Kafka Connect: Kafka Connect is a framework for building and running connectors that integrate Kafka with external systems and data sources.
  - Kafka Streams: Kafka Streams is a lightweight stream processing library that allows developers to build real-time stream processing applications using Kafka.
    
## How Kafka Handles Producers and Consumers
- Producers:

  - Producers are client applications responsible for creating and publishing messages to Kafka topics.
  - When a producer wants to publish a message, it first establishes a connection to one of the Kafka brokers in the cluster.
  - The producer can specify the target topic to which it wants to publish the message.
  - Optionally, the producer can also specify the partition and key for the message. If the partition is not specified, Kafka assigns the partition based on the message key or a round-robin strategy.
  - Once connected, the producer sends the message to the appropriate broker, which then appends the message to the end of the corresponding partition's log.

- Message Batching and Compression
  - Kafka producers can batch multiple messages into a single request before sending them to the broker.
  - Batching allows producers to amortize the cost of network round trips and achieve higher throughput by sending multiple messages in a single network request.
  - Producers typically accumulate messages in an internal buffer until a certain threshold or time limit is reached before sending a batch of messages to the broker.
  - The batched messages are sent as a single request to the broker, reducing the overhead of establishing and tearing down connections for each message.
  - Kafka supports message compression to reduce the size of messages before they are sent over the network.
  - Compression is applied at the producer level before sending messages to the broker, reducing the amount of data transmitted over the network and saving bandwidth.
  - When a producer compresses messages, it serializes the messages into a compressed binary format using the chosen compression codec. The compressed data is then sent to the broker for storage and processing.
  - Upon receiving compressed messages, the broker decompresses the messages before storing them in the topic's partition log. Consumers then read and decompress messages as needed for processing.
- Configuration:
  - Producers can configure the batch size and linger time to control how messages are batched before sending them to the broker.
  - The batch size specifies the maximum number of messages that can be batched together in a single request.
  - The linger time specifies the maximum amount of time the producer waits for additional messages to accumulate in the batch before sending the batch to the broker.
  -Additionally, producers can specify the compression codec and compression level to use for compressing messages before transmission.
- Acknowledgments and Reliability:
  - After sending a message, the producer can choose to wait for acknowledgment (ack) from the Kafka broker to confirm that the message has been successfully written to the partition's log.
  - Producers can configure the level of acknowledgment required for message delivery, including:
   - Acknowledgment:
     - acks=0: No acknowledgment is required. The producer assumes the message is successfully sent once it is buffered locally.
     - acks=1: Acknowledgment is required from the leader broker. The message is considered sent after being acknowledged by the leader.
     - acks=all: Acknowledgment is required from all in-sync replicas (ISR) of the partition. The message is considered sent only after being replicated to all ISR replicas.
    - Producers can retry sending messages in case of failures or timeouts, ensuring reliable message delivery even in the event of network issues or broker failures.
- Consumers:
  - Consumers are client applications that subscribe to Kafka topics to consume and process messages.
  - Consumers can be part of a consumer group, where each consumer in the group consumes messages from one or more partitions of the subscribed topics.
  - Kafka ensures that each message is consumed by only one consumer within a consumer group, enabling load balancing and fault tolerance.
  - Consumers maintain their own offset for each partition they consume, allowing them to track their progress and resume consumption from the last committed offset in case of failures or restarts.
    ### ZooKeeper
    
 
## Getting Started with Kafka

- Download and Install Kafka: Download [Apache Kafka](https://dlcdn.apache.org/kafka/3.7.0/kafka_2.13-3.7.0.tgz) and extract it.
- Start ZooKeeper: Navigate to the Kafka installation directory and start ZooKeeper by running the following command in a terminal:
```
   bin/zookeeper-server-start.sh config/zookeeper.properties
```
  When you run this command in your Kafka installation directory, it executes the zookeeper-server-start.sh script, which reads the configuration settings from the zookeeper.properties file and starts the ZooKeeper server using those settings.
  
- Start Kafka Broker(s): Open a new terminal window and start a Kafka broker by running the following command:
```
bin/kafka-server-start.sh config/server.properties
```
Repeat this step to start additional Kafka brokers if desired for a multi-broker setup.

- Create a Topic: To create a topic, open a new terminal window and run the following command:
```
bin/kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
```
Replace my-topic with the name of your topic and adjust replication factor and partition count as needed.

- Produce Messages to the Topic: Open a new terminal window and run the following command to start a producer and publish messages to the topic:
```
bin/kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092
```
Type messages into the producer terminal and press Enter to send them to the topic.

- Consume Messages from the Topic: Open a new terminal window and run the following command to start a consumer and subscribe to the topic:
```
bin/kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server localhost:9092
```








