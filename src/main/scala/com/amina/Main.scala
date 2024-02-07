package com.amina

import org.apache.spark.sql.SparkSession
//SparkSession is the entry point to Spark SQL functionality in Spark, allowing you to create DataFrames and interact with Spark's structured data processing capabilities.
object Main {
  def main(args:Array[String]):Unit={
val spark=SparkSession.builder()
  .appName("spark-project1")
  .master("local[*]")
  .getOrCreate()
//This block of code creates a SparkSession object named spark.
// It uses the builder() method to create a SparkSession.Builder instance, which allows us to configure and customize the SparkSession.
// We specify the application name using appName("spark-project1"), and set the master URL to run Spark locally using .master("local[*]").
// Finally, getOrCreate() is called to either retrieve an existing SparkSession or create a new one if it doesn't already exist.
 val df= spark.read
   .option("header",value=true)
    .csv("data/AAPL.csv")
    //This block of code reads a CSV file named "AAPL.csv" located in the "data" directory (relative to the project's root directory) into a DataFrame named df.
    // It uses the read method of the spark SparkSession object to create a DataFrameReader, sets an option to treat the first row as a header using .option("header", value = true),
    //and then reads the CSV file using the csv method.

    df.show()
// The show() method is used to print the first 20 rows of the DataFrame to the console in a tabular format.
  }

}
