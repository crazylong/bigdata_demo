package com.coderlong.mybatisplus;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coderlong.mybatisplus.mapper.*;
import com.coderlong.mybatisplus.pojo.*;
import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.kit.index.MongoIndex;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mongodb.MongoClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OracleToMongodb {
    @Resource
    private TruckGpsMapper truckGpsMapper;

    @Resource
    private TruckSnapMapper truckSnapMapper;

    @Resource
    private GantryCraneGpsMapper gantryCraneGpsMapper;

    @Resource
    private GantryCraneSnapMapper gantryCraneSnapMapper;

    @Resource
    private BridgeCraneGpsMapper bridgeCraneGpsMapper;

    @Resource
    private BridgeCraneSnapMapper bridgeCraneSnapMapper;

    @Resource
    private EmptyContainerGpsMapper emptyContainerGpsMapper;

    @Resource
    private EmptyContainerSnapMapper emptyContainerSnapMapper;

    @Resource
    private ReachStackerGpsMapper reachStackerGpsMapper;

    @Resource
    private ReachStackerSnapMapper reachStackerSnapMapper;

    @Resource
    private YardSnapMapper yardSnapMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testTime(){
//        Long baseTime = 1564639862L;
//
//        Date date = new Date(baseTime*1000);
//        System.out.println(date);
        Double baseLng = 122.035485;
        System.out.println(1/1000000.0);
        BigDecimal b1 = new BigDecimal(1.0);
        BigDecimal b2 = new BigDecimal(1000000);
        System.out.println(b1.divide(b2));
        System.out.println(122.035485 + b1.divide(b2).doubleValue());
        System.out.println(new BigDecimal(122.035485).add(b1.divide(b2)));
        System.out.println(new BigDecimal(122.035485).add(b1.divide(b2)).setScale(6, 4));
        BigDecimal db = new BigDecimal(1.0/1000000);
        System.out.println(db);
        //db.doubleValue();
        System.out.println(baseLng + 1/1000000.0);
    }

    @Test
    public void createIndex(){
        createIndex("MC_TRUCK_GPS");
        createIndex("MC_GANTRYCRANE_GPS");
        createIndex("MC_BRIDGECRANE_GPS");
        createIndex("MC_EMPTYCONTAINER_GPS");
        createIndex("MC_REACHSTACKER_GPS");
    }

    @Test
    public void testMongoQuery(){
        MongoClient client = init();
        MongoQuery query=new MongoQuery();
        List<String> truckCodeList = new ArrayList<>();
        truckCodeList.add("FV6");
        truckCodeList.add("8XY");
        System.out.println("start time " + new Date());
        List<JSONObject> jsonObjects = query.use("mc_truck_gps").in("GPS_TRUCK_CODE", truckCodeList).find();
        System.out.println("end time " + new Date());
//        jsonObjects.forEach(json->{
//            System.out.println(json);
//        });
        client.close();
    }


    @Test
    public void testMongoQueryByIndex(){
        MongoClient client = init();
        MongoQuery query=new MongoQuery();
        List<String> truckCodeList = new ArrayList<>();
        truckCodeList.add("3740");
        truckCodeList.add("3741");
        System.out.println("==================start time " + new Date());
        List<JSONObject> jsonObjects = query.use("mc_truck_gps").in("GPS_ID", truckCodeList).find();
        System.out.println("================end time " + new Date());
//        jsonObjects.forEach(json->{
//            System.out.println(json);
//        });
        client.close();
    }

    @Test
    public void mockGpsDataTruck(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct truck_code as code from mc_truck");
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        List<String> listCode = new ArrayList<>();

        list.forEach(i -> {
            listCode.add(i.get("code").toString());
        });

        Random random = new Random();
        MongoClient client = init();
        MongoQuery query=new MongoQuery();
        query.use("MC_TRUCK_GPS");

        for(int i = 2000000; i<50000000; i++){
            Double baseLng = 122.0354852042;
            Double baseLat = 29.88762859849;
            //2019-08-01 14:11:02
            Long baseTime = 1564639862L;

            Date date = new Date((baseTime+i)*1000);

            TruckGpsMongo gpsMongo = new TruckGpsMongo((long)i,
                    listCode.get(random.nextInt(listCode.size())),
                    new Point(new BigDecimal(baseLng + (i%1000)/1000000.0).setScale(6, 4),
                    new BigDecimal(baseLat + (i%1000)/1000000.0).setScale(6, 4)),
                    0L,0L,"mapclass" + i,"mapid" + i,"mapnm" + i,
                    "remark" + i,date,"NPASQ", 0L, 0L, "");
            query.add(new MongoQuery().set(gpsMongo));

            if(i%1000==0){
                query.saveList();
                query=new MongoQuery();
                query.use("MC_TRUCK_GPS");
            }
        }
        client.close();
    }

    @Test
    public void mockGpsDataGantryCrane(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct equipment_code as code from mc_gantryCrane");
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        List<String> listCode = new ArrayList<>();

        list.forEach(i -> {
            listCode.add(i.get("code").toString());
        });


        Random random = new Random();

        MongoClient client = init();

        MongoQuery query=new MongoQuery();
        query.use("MC_GANTRYCRANE_GPS");

        for(int i = 0; i<100000; i++){
            Double baseLng = 122.035485;
            Double baseLat = 29.887628;
            //2019-08-01 14:11:02
            Long baseTime = 1564639862L;

            Date date = new Date((baseTime+i)*1000);

            GantryCraneGpsMongo gpsMongo = new GantryCraneGpsMongo((long)i, listCode.get(random.nextInt(listCode.size())),
                    new Point(new BigDecimal(baseLng + (i%1000)/1000000.0).setScale(6, 4),
                            new BigDecimal(baseLat + (i%1000)/1000000.0).setScale(6, 4)), -35L,0L, 0L, "mapclass" + i,"mapid" + i,"mapnm" + i,date,"NPASQ");
            query.add(new MongoQuery().set(gpsMongo));

            if(i%1000==0){
                query.saveList();
                query=new MongoQuery();
                query.use("MC_GANTRYCRANE_GPS");
            }
        }
        //query.saveList();
        client.close();
    }


    @Test
    public void mockGpsDataBridgeCrane(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct equipment_code as code from mc_bridgeCrane");
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        List<String> listCode = new ArrayList<>();

        list.forEach(i -> {
            listCode.add(i.get("code").toString());
        });


        Random random = new Random();

        MongoClient client = init();

        MongoQuery query=new MongoQuery();
        query.use("MC_BRIDGECRANE_GPS");

        for(int i = 3216460; i<3216480; i++){
            Double baseLng = 122.035485;
            Double baseLat = 29.887628;
            //2019-08-01 14:11:02
            Long baseTime = 1564639862L;

            Date date = new Date((baseTime+i)*1000);

            BridgeCraneGpsMongo gpsMongo = new BridgeCraneGpsMongo((long)i, listCode.get(random.nextInt(listCode.size())),
                    new Point(new BigDecimal(baseLng + (i%1000)/1000000.0).setScale(6, 4),
                            new BigDecimal(baseLat + (i%1000)/1000000.0).setScale(6, 4)), 0L,0L, 0L, "mapclass" + i,"mapid" + i,"mapnm" + i,date,"NPASQ");

            query.add(new MongoQuery().set(gpsMongo));

//            if(i%1000==0){
//                query.saveList();
//                query=new MongoQuery();
//                query.use("MC_BRIDGECRANE_GPS");
//            }
        }
        query.saveList();
        client.close();
    }


    @Test
    public void mockGpsDataEmptyContainer(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct equipment_code as code from mc_emptyContainer");
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        List<String> listCode = new ArrayList<>();

        list.forEach(i -> {
            listCode.add(i.get("code").toString());
        });


        Random random = new Random();

        MongoClient client = init();

        MongoQuery query=new MongoQuery();
        query.use("MC_EMPTYCONTAINER_GPS");

        for(int i = 0; i<100000; i++){
            Double baseLng = 122.035485;
            Double baseLat = 29.887628;
            //2019-08-01 14:11:02
            Long baseTime = 1564639862L;

            Date date = new Date((baseTime+i)*1000);

            EmptyContainerGpsMongo gpsMongo = new EmptyContainerGpsMongo(
                    (long)i,
                    listCode.get(random.nextInt(listCode.size())),
                    new Point(new BigDecimal(baseLng + (i%1000)/1000000.0).setScale(6, 4),
                            new BigDecimal(baseLat + (i%1000)/1000000.0).setScale(6, 4)), 0L, 0L, "mapclass" + i,"mapid" + i,"mapnm" + i, date, 0L, "NPASQ");

            query.add(new MongoQuery().set(gpsMongo));

            if(i%1000==0){
                query.saveList();
                query=new MongoQuery();
                query.use("MC_EMPTYCONTAINER_GPS");
            }
        }
        //query.saveList();
        client.close();
    }

    @Test
    public void mockGpsDataReachStacker(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct equipment_code as code from mc_reachStacker");
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        List<String> listCode = new ArrayList<>();

        list.forEach(i -> {
            listCode.add(i.get("code").toString());
        });


        Random random = new Random();

        MongoClient client = init();

        MongoQuery query=new MongoQuery();
        query.use("MC_REACHSTACKER_GPS");

        for(int i = 0; i<100000; i++){
            Double baseLng = 122.035485;
            Double baseLat = 29.887628;
            //2019-08-01 14:11:02
            Long baseTime = 1564639862L;

            Date date = new Date((baseTime+i)*1000);

            ReachStackerGpsMongo gpsMongo = new ReachStackerGpsMongo((long)i, listCode.get(random.nextInt(listCode.size())),
                    new Point(new BigDecimal(baseLng + (i%1000)/1000000.0).setScale(6, 4),
                            new BigDecimal(baseLat + (i%1000)/1000000.0).setScale(6, 4)), -35L,0L, 0L, "mapclass" + i,"mapid" + i,"mapnm" + i,date,"NPASQ");

            query.add(new MongoQuery().set(gpsMongo));

            if(i%1000==0){
                query.saveList();
                query=new MongoQuery();
                query.use("MC_REACHSTACKER_GPS");
            }
        }
        //query.saveList();
        client.close();
    }

    @Test
    public void mockGpsDataYard(){
        List<YardGpsMongo> list = jdbcTemplate.query("SELECT A.*, B.ID AS STATUS_ID FROM MC_YARD_GPS A \n" +
                "INNER JOIN MC_YARD_SNAP B ON A.ID = B.GPS_ID", new YardGpsMongoMapper());

//        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id as GPS_ID, yard_Code as GPS_YARD_CODE, mapclass as GPS_MAPCLASS, mapid as GPS_MAPID, mapnm as GPS_MAPNM, graph as GPS_GRAPH, \n" +
//                "coordinates as GPS_COORDINATES, insert_Time as GPS_INSERT_TIME, TERMINAL_CODE as GPS_TERMINAL_CODE \n" +
//                "from MC_YARD_gps");
        MongoClient client = init();

        MongoQuery query=new MongoQuery();
        query.use("MC_YARD_GPS");

        if(list != null){
            list.forEach(gpsMongo -> {
                query.add(new MongoQuery().set(gpsMongo));
                //query.saveList();
            });
            query.saveList();
        }

        client.close();
    }

    @Test
    public void mockGpsDataTruckFromOracle() {
        MongoClient client = init();

        for(int i = 1; i <= 10000; i++){
            MongoQuery query=new MongoQuery();
            query.use("MC_TRUCK_GPS");
            Page<TruckGps> page = new Page<>(i, 1000);
            IPage<TruckGps> truckGpsList = this.truckGpsMapper.selectPage(page, null);
            for(TruckGps truckGps : truckGpsList.getRecords()){
                TruckGpsMongo gpsMongo = new TruckGpsMongo(truckGps.getId(), truckGps.getTruckCode(), new Point(truckGps.getLongitude(), truckGps.getLatitude()), truckGps.getDirection(),truckGps.getSpeed(),truckGps.getMapclass(),truckGps.getMapid(),truckGps.getMapnm(),truckGps.getRemark(),truckGps.getInsertTime(),truckGps.getTerminalCode(), 0L, 0L, "");
                query.add(new MongoQuery().set(gpsMongo));
                //query.eq("GPS_ID", truckGps.getId()).delete();
            }
            query.saveList();
        }

        client.close();
    }

    @Test
    public void mockGpsDataGantryCraneFromOracle(){
        MongoClient client = init();

        for(int i = 1; i <= 40; i++){
            MongoQuery query=new MongoQuery();
            query.use("MC_GANTRYCRANE_GPS");
            Page<GantryCraneGps> page = new Page<>(i, 1000);
            IPage<GantryCraneGps> gpsList = this.gantryCraneGpsMapper.selectPage(page, null);
            for(GantryCraneGps gps : gpsList.getRecords()){

                GantryCraneGpsMongo gpsMongo = new GantryCraneGpsMongo(gps.getId(), gps.getEquipmentCode(), new Point(gps.getLongitude(), gps.getLatitude()), gps.getDirection(),gps.getSpeed(), gps.getHeight(),gps.getMapclass(),gps.getMapid(),gps.getMapnm(),gps.getInsertTime(),gps.getTerminalCode());
                query.add(new MongoQuery().set(gpsMongo));
            }
            query.saveList();
        }

        client.close();
    }

    @Test
    public void mockGpsDataBridgeCraneFromOracle(){
        MongoClient client = init();

        for(int i = 1; i <= 1; i++){
            MongoQuery query=new MongoQuery();
            query.use("MC_BRIDGECRANE_GPS");
            Page<BridgeCraneGps> page = new Page<>(i, 1000);
            IPage<BridgeCraneGps> gpsList = this.bridgeCraneGpsMapper.selectPage(page, null);
            for(BridgeCraneGps gps : gpsList.getRecords()){

                BridgeCraneGpsMongo gpsMongo = new BridgeCraneGpsMongo(gps.getId(), gps.getEquipmentCode(), new Point(gps.getLongitude(), gps.getLatitude()), gps.getDirection(),gps.getSpeed(), gps.getHeight(),gps.getMapclass(),gps.getMapid(),gps.getMapnm(),gps.getInsertTime(),gps.getTerminalCode());
                query.add(new MongoQuery().set(gpsMongo));
            }
            query.saveList();
        }

        client.close();
    }

    @Test
    public void mockGpsDataEmptyContainerFromOracle(){
        MongoClient client = init();

        for(int i = 1; i <= 4; i++){
            MongoQuery query=new MongoQuery();
            query.use("MC_EMPTYCONTAINER_GPS");
            Page<EmptyContainerGps> page = new Page<>(i, 1000);
            IPage<EmptyContainerGps> gpsList = this.emptyContainerGpsMapper.selectPage(page, null);
            for(EmptyContainerGps gps : gpsList.getRecords()){

                EmptyContainerGpsMongo gpsMongo = new EmptyContainerGpsMongo(gps.getId(), gps.getEquipmentCode(), new Point(gps.getLongitude(), gps.getLatitude()), gps.getDirection(),gps.getSpeed(), gps.getMapclass(),gps.getMapid(),gps.getMapnm(),gps.getInsertTime(),gps.getHeight(),gps.getTerminalCode());
                query.add(new MongoQuery().set(gpsMongo));
            }
            query.saveList();
        }

        client.close();
    }

    @Test
    public void mockGpsDataReachStackerFromOracle(){
        MongoClient client = init();

        for(int i = 1; i <= 1; i++){
            MongoQuery query=new MongoQuery();
            query.use("MC_REACHSTACKER_GPS");
            Page<ReachStackerGps> page = new Page<>(i, 1000);
            IPage<ReachStackerGps> gpsList = this.reachStackerGpsMapper.selectPage(page, null);
            for(ReachStackerGps gps : gpsList.getRecords()){

                ReachStackerGpsMongo gpsMongo = new ReachStackerGpsMongo(gps.getId(), gps.getEquipmentCode(), new Point(gps.getLongitude(), gps.getLatitude()), gps.getDirection(),gps.getSpeed(),gps.getHeight(), gps.getMapclass(),gps.getMapid(),gps.getMapnm(),gps.getInsertTime(),gps.getTerminalCode());
                query.add(new MongoQuery().set(gpsMongo));
            }
            query.saveList();
        }

        client.close();
    }

    @Test
    public void mockTruckSnap(){
        MongoClient client = init();

        for(int i = 1; i <= 2000; i++){
            Page<TruckSnap> page = new Page<>(i, 1000);
            IPage<TruckSnap> truckSnapList = this.truckSnapMapper.selectPage(page, null);
            for(TruckSnap truckSnap : truckSnapList.getRecords()){
                 MongoQuery query=new MongoQuery();
                query.use("MC_TRUCK_GPS");
                query.eq("ID", truckSnap.getGpsId())
                        .modify("STATUS_ID", truckSnap.getStatusId())
                        .modify("JOB_ID", truckSnap.getJobId())
                        .modify("DML", truckSnap.getDml()).update();
            }
        }

        client.close();
    }

    @Test
    public void mockGantryCraneSnap(){
        MongoClient client = init();

        for(int i = 1; i <= 40; i++){
            Page<GantryCraneSnap> page = new Page<>(i, 1000);
            IPage<GantryCraneSnap> snapList = this.gantryCraneSnapMapper.selectPage(page, null);
            for(GantryCraneSnap snap : snapList.getRecords()){
                MongoQuery query=new MongoQuery();
                query.use("MC_GANTRYCRANE_GPS");
                query.eq("ID", snap.getGpsId())
                        .modify("STATUS_ID", snap.getStatusId())
                        .modify("JOB_ID", snap.getJobId())
                        .modify("DML", snap.getDml()).update();
            }
        }

        client.close();
    }

    @Test
    public void mockBridgeCraneSnap(){
        MongoClient client = init();

        for(int i = 1; i <= 30; i++){
            Page<BridgeCraneSnap> page = new Page<>(i, 1000);
            IPage<BridgeCraneSnap> snapList = this.bridgeCraneSnapMapper.selectPage(page, null);
            for(BridgeCraneSnap snap : snapList.getRecords()){
                MongoQuery query=new MongoQuery();
                query.use("MC_BRIDGECRANE_GPS");
                query.eq("ID", snap.getGpsId())
                        .modify("STATUS_ID", snap.getStatusId())
                        .modify("JOB_ID", snap.getJobId())
                        .modify("DML", snap.getDml()).update();
            }
        }

        client.close();
    }


    @Test
    public void mockReachStackerSnap(){
        MongoClient client = init();

        for(int i = 1; i <= 1; i++){
            Page<ReachStackerSnap> page = new Page<>(i, 1000);
            IPage<ReachStackerSnap> snapList = this.reachStackerSnapMapper.selectPage(page, null);
            for(ReachStackerSnap snap : snapList.getRecords()){
                MongoQuery query=new MongoQuery();
                query.use("MC_REACHSTACKER_GPS");
                query.eq("ID", snap.getGpsId())
                        .modify("STATUS_ID", snap.getStatusId())
                        .modify("JOB_ID", snap.getJobId())
                        .modify("DML", snap.getDml()).update();
            }
        }

        client.close();
    }

    @Test
    public void mockEmptyContainerSnap(){
        MongoClient client = init();

        for(int i = 1; i <= 4; i++){
            Page<EmptyContainerSnap> page = new Page<>(i, 1000);
            IPage<EmptyContainerSnap> snapList = this.emptyContainerSnapMapper.selectPage(page, null);
            for(EmptyContainerSnap snap : snapList.getRecords()){
                MongoQuery query=new MongoQuery();
                query.use("MC_EMPTYCONTAINER_GPS");
                query.eq("ID", snap.getGpsId())
                        .modify("STATUS_ID", snap.getStatusId())
                        .modify("JOB_ID", snap.getJobId())
                        .modify("DML", snap.getDml()).update();
            }
        }

        client.close();
    }

    @Test
    public void mockYardSnap(){
        MongoClient client = init();




        for(int i = 1; i <= 650; i++){
            Page<YardSnap> page = new Page<>(i, 1000);
            IPage<YardSnap> snapList = this.yardSnapMapper.selectPage(page, null);
            for(YardSnap snap : snapList.getRecords()){
                MongoQuery query=new MongoQuery();
                query.use("MC_YARD_GPS");
                query.eq("ID", snap.getGpsId())
                        .modify("STATUS_ID", snap.getStatusId())
                        .update();
            }
        }

        client.close();
    }

    private MongoClient init(){
        MongoPlugin mongoPlugin=new MongoPlugin();
        //mongoPlugin.add("192.168.239.2",27017);
        mongoPlugin.add("hadoop01",6666);
        mongoPlugin.setDatabase("mc_gps");
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
        return client;
        //client.close();
    }

    @Test
    public void testQueryTruckGps(){
        MongoClient client = init();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct truck_code as code from mc_truck");
        if(CollectionUtils.isEmpty(list)){
            return;
        }

        List<String> codeList = new ArrayList<>();

        list.forEach(i -> {
            codeList.add(i.get("code").toString());
        });

        MongoQuery query=new MongoQuery();
        List<JSONObject> colList = query.use("MC_TRUCK_GPS")
                .in("TRUCK_CODE", codeList)
                .gte("INSERT_TIME", new Date(1505750408000L))
                 .lt("INSERT_TIME", new Date(1505750412000L))
                .find();

        System.out.println(colList);
        System.out.println(colList.size());
        client.close();
    }

    private void createIndex(String tableName){
        MongoClient client = init();
        MongoIndex index=new MongoIndex(tableName);
        //index.ascending("ID").save();
        if(tableName.equals("MC_TRUCK_GPS")){
            index.ascending("TRUCK_CODE", "INSERT_TIME").save();
        } else {
            index.ascending("EQUIPMENT_CODE", "INSERT_TIME").save();
        }

        //index.geo2dsphere("POINT").save();


        //index.ascending("GPS_ID", "GPS_TRUCK_CODE", "GPS_INSERT_TIME").geo2dsphere("point").save();
        //index.ascending("GPS_ID", "GPS_TRUCK_CODE", "GPS_INSERT_TIME").save();
        //index.ascending("GPS_ID").save();
        client.close();
    }
}
