package com.example.scala.core
import com.example.spark.core.{CSVReader, SparkInitializer}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._

object CourseData {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkInitializer.initializeSpark()
    val filepath = "/home/aminasajan/Documents/coursedata.csv"
    val df = CSVReader.readcsv(spark, filepath)
    df.show(Int.MaxValue, truncate = false)
   // df.printSchema()
    val eligibleUsersDF = df.filter(col("completion percentage") > 45)
    eligibleUsersDF.show(truncate = false)
    val userOrgNames = eligibleUsersDF.select("userOrgName").distinct().collect()
    val outputPath = "/home/aminasajan/Documents/all_org_details/"
    userOrgNames.foreach { row =>
      val userOrgName = row.getString(0)
      val filteredDF = eligibleUsersDF.filter(s"userOrgName = '$userOrgName'")
      filteredDF.show()
      filteredDF.write.option("header", "true").csv(s"$outputPath$userOrgName.csv")
    }
  }
}
