package com.coderlong.bigdata.java.spark.hbase;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Long Qiong
 * @create 2018/10/30
 */
public class HbaseDemo {
    public static void main(String[] args) {

        String label ="l:factory_area=均值";
        String grouping = "l:address_city=8601201";

        String[] split1 = label.split("=");
        String[] split2 = grouping.split("=");
        //查询字段
        String query_field = split1[0];
        //查询字段的条件(统计类型)
        String query_condition = split1[1];
        //分组字段
        String grouping_field = split2[0];
        //分组条件
        String grouping_condition = split2[1];


        String path = "C:/Users/Administrator/Desktop/h-rider.csv";


        SparkSession spark = SparkSession.builder()
                .appName("HebaseDemo")
                .master("local[2]")
                .getOrCreate();

        Dataset<Row> csv = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv(path);
        Dataset<Row> select = csv
                .select(query_field, grouping_field);


        JavaRDD<Row> Inside = insidRDD(select,grouping_condition);

        JavaRDD<Row> Outside = outsidRDD(select,grouping_condition);

        String re1 = typeed(query_field);
        String re2 = typeed(grouping_field);

        Double result = 0.00D;
        Double Insid_result = 0.00D;
        Double Outside_result = 0.00D;

        if (re1.equals("String")|| re1.equals("divide")){
            result = calculate(select, query_field, query_condition);
            Dataset<Row> InsidDF = rebuildDF(spark,query_field,grouping_field,Inside);
            Insid_result  = calculate(InsidDF,query_field,query_condition);
            Dataset<Row> OutsideDF = rebuildDF(spark,query_field,grouping_field,Outside);
            Outside_result =calculate(OutsideDF,query_field,query_condition);

        }

        if (re1.equals("Int")){
            result = calculate(select, query_field, query_condition);
            Dataset<Row> InsidDF = rebuildDF(spark,query_field,grouping_field,Inside);
            Insid_result  = calculate(InsidDF,query_field,query_condition);
            Dataset<Row> OutsideDF = rebuildDF(spark,query_field,grouping_field,Outside);
            Outside_result =calculate(OutsideDF,query_field,query_condition);



        }
        System.out.println(result);
        System.out.println("Insid_result=" +Insid_result);
        System.out.println("Outsid_result=" +Outside_result);











    }

    /**
     * 重构df
     * @param spark  sparksession
     * @param query_field  查询字段
     * @param grouping_field  分组字段
     * @param rdd  待转化rdd
     * @return
     */
    public static  Dataset<Row> rebuildDF(SparkSession spark,String query_field,String grouping_field, JavaRDD<Row> rdd ){
        List<StructField> structFields = new ArrayList<>();
        String re1 = typeed(query_field);
        String re2 = typeed(grouping_field);

        if(re1.equals("String")|| re1.equals("divide")){
            structFields.add(DataTypes.createStructField(query_field, DataTypes.StringType, true));
        }else {
            structFields.add(DataTypes.createStructField(query_field, DataTypes.IntegerType, true));
        }

        if(re2.equals("String")|| re2.equals("divide")){
            structFields.add(DataTypes.createStructField(grouping_field, DataTypes.StringType, true));
        }else {
            structFields.add(DataTypes.createStructField(grouping_field, DataTypes.IntegerType, true));
        }
        StructType structType = DataTypes.createStructType(structFields);

        Dataset<Row> rddDF = spark.createDataFrame(rdd, structType);



        return rddDF;

    }

    /**
     * 过滤数据 分组内
     * @param select
     * @param grouping_condition
     * @return
     */
    public static JavaRDD<Row> insidRDD(Dataset<Row> select,String grouping_condition){
        JavaRDD<Row> res = select.toJavaRDD().filter(new Function<Row, Boolean>() {
            @Override
            public Boolean call(Row row) throws Exception {

                if (row.get(1) != null) {
                    if (row.get(1).toString().equals(grouping_condition)) {
                        return true;
                    }
                }
                return false;
            }
        });
        return  res;
    }

    /**
     * 过滤数据 分组外
     * @param select
     * @param grouping_condition
     * @return
     */
    public static JavaRDD<Row> outsidRDD(Dataset<Row> select,String grouping_condition){
        JavaRDD<Row> res = select.toJavaRDD().filter(new Function<Row, Boolean>() {
            @Override
            public Boolean call(Row row) throws Exception {

                if (row.get(1) != null) {
                    if (row.get(1).toString().equals(grouping_condition)) {
                        return false;
                    }
                }
                return true;
            }
        });
        return  res;
    }



    /**
     * 判断计算方法
     * @param row  是基础数据
     * @param fields1  查询条件 如 厂房面积
     * @param fields2  计算方法  如 均值
     * @return
     */
    public  static Double calculate(Dataset<Row> row,String fields1,String fields2){
        Double re = 0.00D;
        switch (fields2){
            case "总和":
                re = sumed(row,fields1)  ;
                break;
            case "均值":
                re =avged(row,fields1);
                break;
            case "最大值":
                re = maxed(row,fields1);
                break;
            case "最小值":
                re = mined(row,fields1);
                break;
            case "数量":
                re = counted(row);
                break;
            case "去重数":
                re =  distinct_counted(row,fields1);
                break;

        }
        return re;

    }


    // 判断字段类型
    public  static  String typeed ( String fields){
        String re = null;
        switch (fields){
            case "l:address_detail":
                re ="String" ;
                break;
            case "l:factory_area":
            case "l:address_city":
                re="Int";
                break;
            case "":
                re="divide";
                break;
        }

        return re;
    }
    //计数
    public static  Double counted (Dataset<Row> row){
        Double re = Double.valueOf(row.count());
        return re;
    }

    //去重计数
    public static Double distinct_counted(Dataset<Row> row,String fields){
        Double re =  Double.valueOf(row.select(fields).distinct().count());
        return re;
    }

    // 求和
    public static Double sumed(Dataset<Row> row,String fields){
        Dataset<Row> n = row.select(functions.sum(fields));
        Row row1 = n.collectAsList().get(0);

        Double re = Double.valueOf(row1.get(0).toString());

        return re;
    }

    // 平均数
    public static Double avged(Dataset<Row> row,String fields){
        Dataset<Row> n = row.select(functions.avg(fields));
        Row row1 = n.collectAsList().get(0);

        Double re = Double.valueOf(row1.get(0).toString());

        return re;
    }


    // 最大值
    public static Double maxed(Dataset<Row> row,String fields){
        Dataset<Row> n = row.select(functions.max(fields));
        Row row1 = n.collectAsList().get(0);

        Double  re = Double.valueOf(row1.get(0).toString());

        return re;
    }

    // 最小值
    public static Double  mined(Dataset<Row> row,String fields){
        Dataset<Row> n = row.select(functions.min(fields));
        Row row1 = n.collectAsList().get(0);

        Double  re = Double .valueOf(row1.get(0).toString());

        return re;
    }

}
