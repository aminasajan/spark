
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

object demo {
  def main(args:Array[String]):Unit={
    val spark=SparkSession.builder
      .appName("spark-project1")
      .master("local[*]")
      .getOrCreate()
    val df:DataFrame=spark.read
      .option("header",value=true)
      .option("inferSchema",value=true)
      .csv("data/AAPL.csv")
    df.show()
    df.select("Date","Open","Close").show()
    val column= df("Date")
    //    val columnDF = df.select(column)

    df.select(column).show()

    col("Date")
    import spark.implicits._
    $"Date"
    df.select(col("Date"),$"Open",df("Close")).show()

  }



}

