package com.example
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{SparkSession, functions}

import java.time.{Duration, Instant}

object BigTextFileProcessing {
def main(args:Array[String]):Unit={
  val spark=SparkSession.builder()
    .appName("Big Data File Processing")
    .master("local[*]")
    .getOrCreate()

  val filePath= "/home/aminasajan/Documents/bigdata.txt"
  val appendedFilePath= "/home/aminasajan/Documents/bigdataappended.txt"

  val initialText=generateText(10)
  writeTextToFile(spark,filePath,initialText)

  val moreText=generateText(5)
  appendTextToFile(spark,appendedFilePath,moreText,filePath)

  val startTime=Instant.now()
  val textDF=spark.read.text(filePath)
  val processedDF=textDF.withColumn("length",functions.length(col("value")))
  processedDF.show(truncate = false)
  val endTime=Instant.now()
  val durationSeconds=Duration.between(startTime,endTime).toMillis/1000.0
  println(s"Time taken to read and process the file: $durationSeconds seconds")
  spark.stop()
}

  def writeTextToFile(spark: SparkSession, filePath: String, text: String):Unit= {
    import spark.implicits._
    Seq(text).toDF("value").write.mode("overwrite").text(filePath)
  }

  def appendTextToFile(spark: SparkSession, afilePath: String, text: String, filePath: String): Unit = {
    import spark.implicits._
    val existingDF = spark.read.text(filePath)
    val appendedContent = existingDF.as[String].collect().mkString("\n") + "\n" + text
    writeTextToFile(spark, afilePath, appendedContent)
  }

  def generateText(i: Int):String={
      val text=(1 to i).map (i=> s"Line $i: This is some text data.").mkString("\n")
      text
    }
}
