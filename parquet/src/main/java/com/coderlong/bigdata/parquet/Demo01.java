package com.coderlong.bigdata.parquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo01 {
    @Test
    public void test01(){
        List<Schema.Field> fields = new ArrayList<>(2);
        fields.add(new Schema.Field("id", Schema.create(Schema.Type.LONG), "id", null));
        fields.add(new Schema.Field("name", Schema.create(Schema.Type.STRING), "name", null));
        Schema schema = Schema.createRecord(fields);

        String path = "D:\\myparquet.parquet";

        try {
            ParquetWriter<GenericData.Record> writer = AvroParquetWriter.<GenericData.Record>builder(new Path(path))
                    .withSchema(schema)
                    .build();

            GenericData.Record record = new GenericData.Record(schema);
            record.put(0, 0);
            record.put(1, "this is a test.");
            writer.write(record);

            writer.close();



            /*ParquetReader<GenericData.Record> reader = AvroParquetReader.<GenericData.Record>builder(new Path(path)).build();
            GenericData.Record record2 = reader.read();
            System.out.println(record2);
            reader.close();*/
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void WriteParquet(){
        List<Schema.Field> fields = new ArrayList<Schema.Field>();
        Object defaultValue = null;
        fields.add(new Schema.Field("x", Schema.create(Schema.Type.INT), "x", defaultValue));
        fields.add(new Schema.Field("y", Schema.create(Schema.Type.INT), "y", defaultValue));

        Schema schema = Schema.createRecord("name", "doc", "namespace", false, fields);

        try (ParquetWriter<GenericData.Record> writer = AvroParquetWriter.<GenericData.Record>builder(
                new Path("my-file.parquet")).withSchema(schema).withCompressionCodec(CompressionCodecName.SNAPPY)
                .build()) {

            // 模拟10000行数据
            for (int r = 0; r < 10000; ++r) {
                Record record = new Record(schema);
                record.put(0, r);
                record.put(1, r * 3);
                writer.write(record);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void ReadParquet(){
        try {
            ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(new Path("my-file.parquet"))
                    .build();

            GenericRecord record;

            while ((record = reader.read()) != null) {
                System.out.println(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
