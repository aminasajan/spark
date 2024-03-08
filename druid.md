# Druid
Apache Druid is an open-source, distributed, column-oriented database designed for high-performance analytical (OLAP) queries on large datasets. 
## Installation and Setup of Apache Druid:
1. Prerequisites:
   Java Development Kit (JDK): Apache Druid requires Java 8 or higher to be installed on your system.
2. Download Apache Druid:
   Download [Apache Druid](https://druid.apache.org) in either tar.gz or zip format.
3. Extract Druid Archive:
  ```
  tar -xzf apache-druid-x.x.x.tar.gz
  ```
 Replace x.x.x with the version number of Apache Druid.

4. Configure Druid:
  Navigate to the conf directory inside the extracted Druid directory and make changes accordingly. 
 
5. Start Druid Services:
   ```
   ./bin/start-micro-quickstart
   ```
   If there is an error showing the port is already in use, then go to conf -> zk in the terminal and edit the file named 'zoo.cfg' and change the client port and then execute an export command which is already in the terminal and then again start druid. 
6. Verify Installation:
   Open a web browser and navigate to http://localhost:8888 to access the Druid console.
7. Shutting Down Druid
   ```
   ./bin/stop-micro-quickstart
   ```
   or
   ctrl+C
## Data Ingestion
   Data ingestion is the process of collecting, transforming, and loading data from various sources into a storage system or data lake for further processing and analysis.
   
## Methods of Data Ingestion:
   
### Batch Ingestion:
   Batch ingestion involves collecting and processing data in large batches at regular intervals. Commonly used tools for batch ingestion include Apache Hadoop, Apache Spark, and Apache Nifi.
- Tools and Technologies for Batch Ingestion:
   - Apache Hadoop
   - Apache Spark
   - Apache Nifi
    
### Stream Ingestion:
   Stream ingestion involves processing data in real-time as it is generated. Commonly used tools for stream ingestion include Apache Kafka, Apache Flink, and Apache Storm.

## Components of Data Ingestion:
   - Data Sources: Identify and source data from various locations such as local files, databases, cloud storage, etc.
   - Data Processing: Perform transformations, cleansing, and enrichment on the data as necessary.
   - Data Loading: Load the processed data into a storage system or data warehouse for analysis.
## Data Schema:
- A data schema in Apache Druid defines the structure of the data ingested into the system. It specifies the dimensions, metrics, and other metadata associated with the dataset.
- Dimensions are categorical attributes used to filter, group, and aggregate data. Examples include timestamps, user IDs, geographic locations, and product categories.
- Metrics are numerical values that can be aggregated over dimensions.
Examples include counts, sums, averages, and maximum/minimum values.
- Granularity defines the level of detail at which data is stored and queried.
Common granularities include seconds, minutes, hours, days, and months.

## Dimension Filtering
- Equality Filter:
  ```
  SELECT * FROM table_name WHERE country = 'USA'
  ```
- Range Filter:
  ```
  SELECT * FROM table_name WHERE age BETWEEN 18 AND 30
  ```
- In Filter:
  ```
  SELECT * FROM table_name WHERE product_category IN ('Electronics', 'Clothing')
  ```
- Not Filter:
  ```
  SELECT * FROM table_name WHERE customer_type NOT IN ('Premium', 'VIP')
  ```
- Regex Filter:
  ```
  SELECT * FROM table_name WHERE regex_match(product_name, '^[A-Z]')
  ```
## Aggregating Metrics
- Sum Aggregator:
  ```
  SELECT SUM(sales_revenue) AS total_revenue FROM sales_data
  ```
- Count Aggregator:
  ```
  SELECT COUNT(order_id) AS total_orders FROM orders
  ```
- Min/Max Aggregator:
   ```
  SELECT MIN(temperature) AS min_temp, MAX(temperature) AS max_temp FROM weather_data
   ```
- Unique Aggregator:
   ```
  SELECT COUNT(DISTINCT user_id) AS unique_users FROM website_traffic
   ```
- Approximate Histogram Aggregator:
  ```
  SELECT APPROX_HISTOGRAM(page_load_time) AS load_time_distribution FROM web_performance
  ```
## Sort
- GROUP BY
  ```
  SELECT region, product_category, SUM(quantity_sold) AS total_quantity
  FROM sales_data
  GROUP BY region, product_category
  ```
- ORDER BY
  ```
  SELECT city, SUM(sales) AS total_sales, AVG(sales) AS avg_sales
  FROM sales_data
  GROUP BY city
  ORDER BY total_sales DESC, avg_sales ASC
  ```
  


   
   
