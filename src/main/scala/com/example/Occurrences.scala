package com.example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object Occurrences {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder()
      .appName("word occurance")
      .master("local[*]")
      .getOrCreate()

    val filepath = "/home/aminasajan/Downloads/ramen-ratings.csv"

    val csvDF = spark.read.option("header", value = true).csv(filepath)
    csvDF.printSchema()
    csvDF.show(truncate = false)
    //var status=" "
    //do {
    //  println(s"Enter the word to be count: ")
    //
    //  val word = scala.io.StdIn.readLine()
    //  val wordCount = countWord(word, csvDF)
    //  println(s"No. of occurrences of '$word': '$wordCount'")
    //  println("Do you want to continue(Y/N)")
    //  status = scala.io.StdIn.readLine()
    //}while(status == "Y")

    val brandWordsDF = csvDF.withColumn("words", split(lower(col("Brand")), "\\s+")).select(explode(col("words")).as("word"))
    val varietyWordsDF = csvDF.withColumn("words", split(lower(col("Variety")), "\\s+")).select(explode(col("words")).as("word"))
    val allWordsDF = brandWordsDF.union(varietyWordsDF)
    val wordCountDF = allWordsDF.groupBy("word").count().orderBy(desc("count"))
    wordCountDF.show(Int.MaxValue)


  }


  //
  //  def countWord(word:String,csvDF:DataFrame): Long = {
  //    val wordCount=csvDF.columns.map(colName=>csvDF.filter(col(colName)===word).count())
  //    wordCount.sum
  //  }
}

