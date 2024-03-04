package com.example.spark.core

import org.apache.spark.sql.{DataFrame, SparkSession}

object CSVReader {
  def readcsv(spark:SparkSession,filepath:String):DataFrame={
    spark.read.option("header","true").option("inferSchema","true").csv(filepath)
  }
}