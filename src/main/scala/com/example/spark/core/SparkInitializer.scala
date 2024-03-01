package com.example.spark.core
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession


object SparkInitializer {
  def initializeSpark(): SparkSession = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    SparkSession.builder()
      .appName("taxi trip")
      .master("local[*]")
      .getOrCreate()
  }
}

