//package com.example
//
//import org.apache.spark.sql.{DataFrame, SparkSession}
//
//object readwrite {
//def main(args:Array[String]):Unit= {
//  val spark = SparkSession.builder()
//    .appName("BigFileCreator")
//    .master("local[*]") //configuring Spark to run in local mode with master("local[*]") means that Spark will run on the local machine using all available CPU cores, making it suitable for small to medium-sized datasets and for development and testing purposes.
//    .getOrCreate()
//
//  var numFiles = 10
//  val df1 = generateTextDataFrame(spark, numFiles)
// df1.write.text("/home/aminasajan/Documents/sample_inpu.txt")
////val existingdf1=spark.read.text("/home/aminasajan/Documents/sample_inpu.txt")
////  val combineddf1=df1.union(existingdf1)
////  combineddf1.write.mode("append").text("/home/aminasajan/Documents/sample_inpu.txt")
//  df1.write.mode("overwrite").text("/home/aminasajan/Documents/sample_inpu.txt")
//
//  // Read existing content of df1 file
//  val existingDf1 = spark.read.text("/home/aminasajan/Documents/sample_inpu.txt")
//
//  // Union existingDf1 with df1
//  val combinedDf1 = df1.union(existingDf1)
//
//  // Write the combined DataFrame back to the file in append mode
//  combinedDf1.write.mode("append").text("/home/aminasajan/Documents/sample_inpu.txt")
//
//  numFiles=5
//  val df2 =generateTextDataFrame(spark, numFiles)
//  df2.write.text("/home/aminasajan/Documents/sample_inp.txt")
//
//  spark.stop()
//}
//  def generateTextDataFrame(spark: SparkSession, numFiles: Int):DataFrame={
//   import spark.implicits._
//    val data=(1 to numFiles).map{ i=>
//      s"File $i: This is some text data. Hi"
//    }
//    val df=data.toDF("Text")
//    df
//  }
//
//
//}
