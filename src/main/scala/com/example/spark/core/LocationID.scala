package com.example.spark.core

import org.apache.spark.sql.functions._
import org.joda.time.{Days, LocalDate}

import java.text.SimpleDateFormat


object LocationID {

  def main(args: Array[String]): Unit = {

    val spark = SparkInitializer.initializeSpark()

    //Read CSV file
    val filepath = "/home/aminasajan/Downloads/taxi_tripdata.csv"
    val df = CSVReader.readcsv(spark, filepath)

    //Display Dataframe
    df.printSchema()
    df.show(truncate = false)

    //Start, end date and duration of the dataset
    val dfWithDates = df
      .withColumn("pickup_date", to_date(col("lpep_pickup_datetime")))
      .withColumn("dropoff_date", to_date(col("lpep_dropoff_datetime")))
    val minDate = dfWithDates
      .select(min("pickup_date"))
      .first()
      .getDate(0)
    val maxDate = dfWithDates
      .select(max("dropoff_date"))
      .first()
      .getDate(0)
    println(s"Start date: $minDate")
    println(s"End date: $maxDate")
    val duration = Days.daysBetween(new LocalDate(minDate), new LocalDate(maxDate)).getDays()
    println(s"Duration of the dataset: $duration days")

    //Most chosen PU location
    val maxPULocationIDDF = df.groupBy("PULocationID")
      .count()
      .orderBy(desc("count"))
      .limit(1)
      .select("PULocationID")
    val maxPULocationID = maxPULocationIDDF.first().getInt(0)
    println(s"Maximum no. of pickup happened at Location ID: $maxPULocationID")

    //Most chosen DO location
    val maxDOLocationIDDF = df
      .groupBy("DOLocationID")
      .count()
      .orderBy(desc("count"))
      .limit(1)
      .select("DOLocationID")
    val maxDOLocation = maxDOLocationIDDF.first().getInt(0)
    println(s"Maximum no. of drop off happened at LocationID: $maxDOLocation")

    //top 5 PU locations
    val topPULocationIDDF = df
      .groupBy("PULocationID")
      .count()
      .orderBy(desc("count"))
      .limit(5)

    val topPULocationIDs = topPULocationIDDF
      .select("PULocationID")
      .collect()
      .map(_.getInt(0))
      .toList

    println(s"Top 5 pickup location ID: $topPULocationIDs")

    //top 5 DO locations
    val topDOLocationIDDF = df
      .groupBy("DOLocationID")
      .count()
      .orderBy(desc("count"))
      .limit(5)
    val topDOLocationIDs = topDOLocationIDDF
      .select("DOLocationID")
      .collect()
      .map(_.getInt(0))
      .toList
    println(s"Top 5 drop off location ID: $topDOLocationIDs")

    var targetdate = "2021-07-01"
    //    println("Enter the date")
    //    targetdate= scala.io.StdIn.readLine()
    var filteredDF = df.filter(col("lpep_dropoff_datetime").substr(1, 10) === targetdate)

    val maxPULocationIDdayDF = filteredDF.groupBy("PULocationID").count().orderBy("count").limit(1)
    val maxPULocationIDday = maxPULocationIDdayDF.select("PULocationID").first().getInt(0)

    println(s"Maximum  no. of rides on $targetdate happened at locationID $maxPULocationIDday")

    val rideCount = filteredDF.count()
    println(s"No. of rides on $targetdate: $rideCount ")

    val startdate=LocalDate.parse("2021-08-01")
    val enddate=startdate.plusDays(6)
    filteredDF=df.filter(col("lpep_pickup_datetime").substr(1,10).between(startdate.toString(),enddate.toString))
    val rideCountsByDate=filteredDF.groupBy("lpep_pickup_datetime").count().orderBy("count").limit(1)
    filteredDF.show(Int.MaxValue)
    val maxRideTimestamp=rideCountsByDate.first().getTimestamp(0)
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val maxRideDate = dateFormat.format(maxRideTimestamp)

    println(s"Maximum number of rides happen between $startdate and $enddate is on $maxRideDate")
  }
}
