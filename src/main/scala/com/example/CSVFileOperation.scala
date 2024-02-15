package com.example

import org.apache.spark.sql.{Dataset, SparkSession, Row}



object CSVFileOperation {
  def main(args:Array[String]):Unit={

    val spark=SparkSession.builder()
      .appName("Ramen Ratings")
      .master("local[*]")
      .getOrCreate()
  val filepath="/home/aminasajan/Downloads/ramen-ratings.csv"
  try {
    val csvDF:Dataset[Row] = spark.read.option("header", value=true).csv(filepath)
    csvDF.printSchema()
    csvDF.show()
  }
  catch {
    case e: Exception =>
      println(s"Error reading CSV file: ${e.getMessage}")
  }finally {
    spark.stop()
  }

}
}
