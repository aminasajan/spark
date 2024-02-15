package com.example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object CSVFileOperation {
  def main(args:Array[String]):Unit= {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder()
      .appName("Ramen Ratings")
      .master("local[*]")
      .getOrCreate()


    val filepath = "/home/aminasajan/Downloads/ramen-ratings.csv"

    val csvDF = spark.read.option("header", value = true).csv(filepath)
    csvDF.printSchema()
    csvDF.show(Int.MaxValue, truncate = false)
    csvDF.show(truncate = false)

    val rowCount = csvDF.count()
    println(s"\nTotal number of records: $rowCount")
    val japanCount = csvDF.filter(col("Country") === "Japan").count()
    println(s"Number of occurrences of 'Japan': $japanCount")

    val countryCount = csvDF.select("Country").distinct().na.drop().count()
    println(s"\nNumber of countries: $countryCount")

    val country = csvDF.select("Country").distinct().na.drop().collect()
    println("\nNames of all countries:")
    country.foreach(row => println(row.getString(0)))
    val ramenCount = csvDF.select("Brand").distinct().na.drop().count()
    println(s"\nNumber of brands: $ramenCount")
    val ramen = csvDF.select("Brand").distinct().na.drop().collect()
    println("\nName of all brands: ")
    ramen.foreach(row => println(row.getString(0)))
    val hollandCount = csvDF.columns.map(colName => csvDF.filter(col(colName) === "Holland").count()).sum
    val hollandExists = hollandCount > 0
    if (hollandExists) {
      println("'\nHolland' exists")
    }
    else {
      println("\n'Holland doesnt exist'")
    }
    import spark.implicits._
    def checkOccurrences(df:DataFrame):Long={
      val occurrences=df.columns.map(colName=>df.filter(col(colName)==="Maggi").count())
      occurrences.sum
    }
    val occurrences= checkOccurrences(csvDF)
    println(s"\nNumber of occurrences of 'Maggi': $occurrences")

    println("Brands who have highest ratings")
    val filteredDF=csvDF.filter(col("Stars")>3.5).select("Brand","Variety","Style","Country","Stars").orderBy(desc("Stars"))
    filteredDF.show(Int.MaxValue,truncate = false)

    val styleFilteredDF=csvDF.filter(col("Stars")>3.5 && col("Style")==="Cup").select("Brand","Variety","Style","Country","Stars").orderBy(desc("Stars"))
styleFilteredDF.show(Int.MaxValue,truncate = false)

  }
}
