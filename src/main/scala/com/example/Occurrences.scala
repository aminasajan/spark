package com.example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object Occurrences {
def main(args:Array[String]):Unit= {
  Logger.getLogger("org").setLevel(Level.ERROR)
  val spark= SparkSession.builder()
    .appName("word occurance")
    .master("local[*]")
    .getOrCreate()

  val filepath = "/home/aminasajan/Downloads/ramen-ratings.csv"

  val csvDF= spark.read.option("header",value = true).csv(filepath)
  csvDF.printSchema()
  csvDF.show(truncate=false)
var status=" "
do {
  println(s"Enter the word to be count: ")

  val word = scala.io.StdIn.readLine()
  val wordCount = countWord(word, csvDF)
  println(s"No. of occurrences of '$word': '$wordCount'")
  println("Do you want to continue(Y/N)")
  status = scala.io.StdIn.readLine()
}while(status == "Y")
}
  def countWord(word:String,csvDF:DataFrame): Long = {
    val wordCount=csvDF.columns.map(colName=>csvDF.filter(col(colName)===word).count())
    wordCount.sum
  }
}

