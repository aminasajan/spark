package com.example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object TableUnion {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder()
      .appName("Union Operation")
      .master("local[*]")
      .getOrCreate()

    val filepath = "/home/aminasajan/Documents/VIM/UserCourseData.csv"
    val csvDF = spark.read.option("header", value = true).csv(filepath)
    csvDF.show(truncate = false)

    // Define JDBC URL and connection properties
    val url = "jdbc:mysql://localhost:3306/studyapp"
    val properties = new java.util.Properties()
    properties.setProperty("user", "root")
    properties.setProperty("password", "Welcome@123")

    val tableName = "user"
    val df: DataFrame = spark.read.jdbc(url, tableName, properties)
    df.show()

    val userDF = csvDF
      .withColumn("completedOn", from_unixtime(col("completedOn")))
      .withColumn("completionStatus",
        when(col("completionStatus") === "enrolled", 0)
          .when(col("completionStatus") === "in-progress", 1)
          .when(col("completionStatus") === "completed", 2)
          .otherwise(null)
      )
      .select("userID", "courseID", "courseName", "completionStatus", "completedOn")
    val tableName1 = "user_enrolments"
    userDF.show()
    userDF.write.jdbc(url, tableName1, properties)

    val joinedDF = csvDF.join(df, csvDF("userID") === df("id"))
      .select(
        csvDF("userID").as("userID"),
        df("name").as("userName"),
        df("orgid").as("userOrgID"),
        df("orgname").as("userOrgName"),
        when(df("status") === 0, "inactive").otherwise("active").as("userStatus"),
        csvDF("courseID").as("courseID"),
        csvDF("courseName").as("courseName"),
        csvDF("completionStatus").as("completionStatus"),
        from_unixtime(csvDF("completedOn")).as("completedOn")

      )
    joinedDF.show()
    joinedDF.write.option("header", true).csv("/home/aminasajan/Documents/VIM/UserData.csv")
  }
}
