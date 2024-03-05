package com.example.spark
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}

object KafkaSparkIntegration {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    // Initialize SparkSession
    val spark = SparkSession
      .builder()
      .appName("KafkaSparkIntegration")
      .master("local[*]")
      .getOrCreate()

    // Initialize StreamingContext with a batch interval of 5 seconds
    val streamingContext = new StreamingContext(spark.sparkContext, Seconds(5))

    // Kafka parameters
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "spark-streaming-consumer",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    // Subscribe to Kafka topic
    val topics = Array("my-topic")
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    // Process Kafka stream
    stream.foreachRDD { rdd =>
      rdd.foreach { record =>
        println(s"Received message: ${record.value}")
        // Add your custom processing logic here
      }
    }

    // Start StreamingContext
    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
