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
6. Verify Installation:
   Open a web browser and navigate to http://localhost:8888 to access the Druid console.
7. Shutting Down Druid
   ```
   ./bin/stop-micro-quickstart
   ```
