# spark

### 1. What is Big Data?
Big Data refers to datasets that are too large and complex to be processed using traditional data processing applications. 

It encompasses three main dimensions:

- Volume: The sheer amount of data generated from various sources, including social media, sensors, transactions, and more.

- Velocity: The speed at which data is generated and needs to be processed in real-time or near-real-time.

- Variety: The diverse types of data, including structured, semi-structured, and unstructured data, such as text, images, videos, and sensor data.

### 2. Challenges of Big Data
- Storage: Storing large volumes of data efficiently and cost-effectively.
- Processing: Processing and analyzing data quickly to extract valuable insights.
- Scalability: Scaling infrastructure to handle increasing data volumes and processing demands.
- Complexity: Dealing with diverse data types and formats, as well as integrating data from various sources.
- Security and Privacy: Ensuring the security and privacy of sensitive data while maintaining compliance with regulations.

### 3. Distributed Computing
Distributed computing is a computing paradigm that involves multiple computers working together on a network to achieve a common goal. 

- Parallelism: Distributing data and computation across multiple nodes in a cluster to perform tasks concurrently, thereby reducing processing time.
- Fault Tolerance: Building resilience into distributed systems to ensure uninterrupted operation in the event of hardware failures or network issues.
- Scalability: Adding or removing resources dynamically to accommodate changing workloads and data volumes.
- Data Partitioning: Dividing datasets into smaller partitions and distributing them across nodes for parallel processing.
- Communication: Facilitating communication and coordination among nodes in the cluster to execute tasks efficiently.






It is not necessary that main function needs to be inside an object.


## Core Concepts of Spark
### RDD

- RDDs are the fundamental data structure in Spark, representing immutable distributed collections of objects.
- They allow parallel processing of data across a cluster in a fault-tolerant manner.
- RDDs can be created from external data sources or by transforming existing RDDs through operations.

### Properties
- Resilience:
They can recover from failures automatically and it is achieved through lineage information, which tracks the transformations applied to the base data.
- Distributed:
RDDs distribute data across multiple nodes in a cluster, allowing for parallel processing.
- Immutable:
Instead of modifying an RDD in place, transformations create new RDDs with the desired modifications.
- Lazily Evaluated:
Transformations on RDDs are not executed immediately, Spark waits until an action is called before executing the transformations, optimizing computation.

### Creating RDDs
- Loading external datasets (text files, CSV files, etc.)
- Parallelizing existing collections in memory
- Transforming existing RDDs through operations like map, filter, etc.

 ###  Transformations on RDDs
 - Transformations are operations that create new RDDs from existing RDDs.
 - Common transformations include map, filter, flatMap, reduceByKey, sortByKey, etc.
 - Each transformation produces a new RDD, preserving the immutability of RDDs.

### Actions on RDDs
- Actions are operations that trigger computation and produce results.
- Examples of actions include count, collect, reduce, take, saveAsTextFile, etc.
- Actions are necessary to materialize the lazy transformations and obtain actual results.
