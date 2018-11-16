///**
//  * Created by Administrator
//  */
//
//import java.io.Serializable
//import java.util.logging.Logger
//
//import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
//import org.apache.hadoop.hbase.mapreduce.TableInputFormat
//import org.apache.hadoop.hbase.util.Bytes
//import org.apache.spark.sql.SparkSession
//import org.apache.spark.{SparkConf, SparkContext}
//
//
//
//object MySparkSql extends Serializable {
//
//  case class Score(name: String,metric: String, value: String, timestamp: String, deviceTypeId: String, deviceNo: String, deviceId: String, gatewayId: String)
//
//  val logger = Logger.getLogger(MySparkSql.getClass.getName)
//
//  def main(args: Array[String]) {
//
//    /*val jars: Array[String] = Array("D:\\workspace\\mysparksql_2.10-1.0.jar")
//    System.setProperty("hadoop.home.dir", "E:\\Program Files\\hadoop-2.7.0") //win7环境下运行须加*/
//    val sconf = new SparkConf()
//      .setMaster("local[*]")
//      //      .setMaster("spark://h230:7077")//在集群测试下设置,h230是我的hostname，须自行修改
//      .setAppName("MySparkSql") //win7环境下设置
//      //.set("spark.executor.memory", "1g")
//     // .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//    //      .setJars(jars)//在集群测试下，设置应用所依赖的jar包
//    val sc = new SparkContext(sconf)
//
//    val conf = HBaseConfiguration.create()
//     conf.set("hbase.zookeeper.property.clientPort", "2181")
//     conf.set("hbase.zookeeper.quorum", "172.18.203.131,172.18.203.132,172.18.203.135")
//     //conf.set("hbase.master", "h230:60000")
//    //conf.addResource("hbase-site.xml")//替代以上三条配置信息
//
//    conf.set(TableInputFormat.INPUT_TABLE, "label:company_label_flat")
//
//    //    Scan操作
//    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
//      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
//      classOf[org.apache.hadoop.hbase.client.Result])
//
//
//    val score = hBaseRDD.map(m => m._2.listCells()).map(c =>
////      {
////        println(c.get(0).getValue)
////        println(CellUtil.cloneValue(c.get(0)))
////      }
//      Score(new String(c.get(0).getRowArray()),
//        Bytes.toString(c.get(0).getRowArray),
//        Bytes.toString(c.get(1).getRowArray),
//        Bytes.toString(c.get(2).getRowArray),
//        Bytes.toString(c.get(3).getRowArray),
//        Bytes.toString(c.get(4).getRowArray),
//        Bytes.toString(c.get(5).getRowArray),
//        Bytes.toString(c.get(6).getRowArray))
//    )
//    score.foreach(println)
//    SparkSession.builder
//    //val sqlContext = new org.apache.spark.sql.SQLContext(sc)
//    var sqlContext =  SparkSession.builder().getOrCreate().sqlContext
//
//    val scoreSchema = sqlContext.createDataFrame(score)
//
//
//    scoreSchema.createOrReplaceTempView("company_label_flat2")
//    var result = sqlContext.sql("SELECT * FROM company_label_flat2 limit 5")
//    result.collect().foreach(println)
//
//  }
//}