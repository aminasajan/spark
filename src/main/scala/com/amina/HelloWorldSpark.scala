package com.amina

import org.apache.spark.sql.SparkSession

object HelloWorldSpark extends App {
  println("Hello World")
val spark = SparkSession.builder()
  .appName("Hello World")
  .master("local")
  .getOrCreate()
val sourceRDD = spark.sparkContext.textFile("/home/aminasajan/Documents/sample_input.odt")

sourceRDD.take(1).foreach(println)
  spark.stop()
}
