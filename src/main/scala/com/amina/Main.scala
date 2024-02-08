package com.amina

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, concat, current_timestamp, expr, lit, row_number, year}
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{DataFrame, SparkSession, functions}

//SparkSession is the entry point to Spark SQL functionality in Spark, allowing you to create DataFrames and interact with Spark's structured data processing capabilities.
object Main {
  type Frame = DataFrame

  def main(args:Array[String]):Unit={
val spark=SparkSession.builder()
  .appName("spark-project1")
  .master("local[*]")
  .getOrCreate()
//This block of code creates a SparkSession object named spark.
// It uses the builder() method to create a SparkSession.Builder instance, which allows us to configure and customize the SparkSession.
// We specify the application name using appName("spark-project1"), and set the master URL to run Spark locally using .master("local[*]").
// Finally, getOrCreate() is called to either retrieve an existing SparkSession or create a new one if it doesn't already exist.
 val df:DataFrame=spark.read
   .option("header",value=true)
   .option("inferSchema",value=true)
   //to infer the datatype of ea ch column
    .csv("data/AAPL.csv")
    //This block of code reads a CSV file named "AAPL.csv" located in the "data" directory (relative to the project's root directory) into a DataFrame named df.
    // It uses the read method of the spark SparkSession object to create a DataFrameReader, sets an option to treat the first row as a header using .option("header", value = true),
    //and then reads the CSV file using the csv method.

   df.show()
// The show() method is used to print the first 20 rows of the DataFrame to the console in a tabular format.
df.printSchema()
    //To print the column name
  val column=df("Open")
    val newColumn= (column+2.0).as("OpenIncreasedby2")
    val columnString=column.cast(StringType).as("OpenAsString")

    df.select(column,newColumn,columnString)
      .filter(newColumn>2.0)
      .filter(newColumn>column)
      .filter(newColumn===column)
      .show()

    val litColumn=lit(2.0)
    val newColumnString=concat(columnString,lit("Hello WOrld"))

    df.select(column,newColumn,columnString,litColumn,newColumnString).show(truncate=false)

val timeStampFromExpression=expr("cast(current_timestamp()as string) as timeStampExpression")
val timeStampFromFunction=current_timestamp().cast(StringType).as("timeStampFromExpression")
 df.select(timeStampFromExpression,timeStampFromFunction).show()
    df.selectExpr("cast(Date as String)","Open+1.0","current_timeStamp()").show()

    val renameColumns= List(
      col("Date").as("date"),
      col("Open").as("open"),
      col("High").as("high"),
      col("Low").as("low"),
      col("Close").as("close"),
      col("Adj Close").as("adjClose"),
      col("Volume").as("volume")
    )
    val stockData=df.select(renameColumns: _*)
      .withColumn("diff",col("close")-col("open"))
      .filter(col("close")>col("open")*1.1)
    stockData.show()

    import spark.implicits._
    stockData.groupBy(year($"date").as("year"))
      .agg(functions.max($"close").as("maxClose"),functions.avg($"close").as("avgClose"))
.sort($"maxClose".desc)
      .show()

    val window=Window.partitionBy(year($"date").as("year")).orderBy(($"close").desc)
    stockData
      .withColumn("rank",row_number().over(window))
      .filter($"rank"===1)
.sort($"close".desc)
  .show()
  }
}
