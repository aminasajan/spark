package com.example.spark.core

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.joda.time.{Days, LocalDate}


object SparkInitializer {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .appName("taxi trip")
      .master("local[*]")
      .getOrCreate()

    //Read CSV file
    val filepath = "/home/aminasajan/Downloads/taxi_tripdata.csv"
    val df = CSVReader.readcsv(spark, filepath)

    //Display Dataframe
    df.printSchema()
    df.show(truncate = false)

    //Start, end date and duration of the dataset
    val dfWithDates = df.withColumn("pickup_date", to_date(col("lpep_pickup_datetime")))
      .withColumn("dropoff_date", to_date(col("lpep_dropoff_datetime")))
    val minDate = dfWithDates.select(min("pickup_date")).first().getDate(0)
    val maxDate = dfWithDates.select(max("dropoff_date")).first().getDate(0)
    println(s"Start date: $minDate")
    println(s"End date: $maxDate")
    val duration = Days.daysBetween(new LocalDate(minDate), new LocalDate(maxDate)).getDays()
    println(s"Duration of the dataset: $duration days")

    //Most PU happened location
    val maxPULocationIDDF = df.groupBy("PULocationID").count().orderBy(desc("count")).limit(1).select("PULocationID")
    val maxPULocationID = maxPULocationIDDF.first().getInt(0)
    println(maxPULocationID)




  }
}
