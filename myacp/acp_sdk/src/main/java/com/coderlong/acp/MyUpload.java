package com.coderlong.acp;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.Partition;
import com.aliyun.odps.PartitionSpec;
import com.aliyun.odps.Project;
import com.aliyun.odps.Resource;
import com.aliyun.odps.Table;
import com.aliyun.odps.TableResource;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;

import java.util.Date;
import java.util.Map;

public class MyUpload {
    public static void main(String[] args) {
        Account account = new AliyunAccount("my_access_id", "my_access_key");
        Odps odps = new Odps(account);
        String odpsUrl = "<your odps endpoint>";
        odps.setEndpoint(odpsUrl);
        odps.setDefaultProject("my_project");

        //Projects是MaxCompute中所有项目空间的集合。集合中的元素为Project。
        //Project是对项目空间信息的描述。您可以通过Projects获取相应的项目空间。
        Project p = null;
        try {
            p = odps.projects().get("my_exists");
            p.reload();
        } catch (OdpsException e) {
            e.printStackTrace();
        }
        assert p != null;
        Map<String, String> properties = p.getProperties();

        //Tables是MaxCompute中所有表的集合。集合中的元素为Table。
        for (Table t : odps.tables()) {

        }

        //Table是对表信息的描述。
        Table t = odps.tables().get("table name");
        try {
            t.reload();
        } catch (OdpsException e) {
            e.printStackTrace();
        }

//        Partition part = t.getPartition(new PartitionSpec(tableSpec[1]));
//        try {
//            part.reload();
//        } catch (OdpsException e) {
//            e.printStackTrace();
//        }


        //Instances是MaxCompute中所有实例（Instance）的集合。集合中的元素为Instance
        for (Instance i : odps.instances()) {

        }


        //instance
        Instance instance= odps.instances().get("instance id");
        Date startTime = instance.getStartTime();
        Date endTime = instance.getEndTime();

        Instance.Status instanceStatus = instance.getStatus();
        String instanceStatusStr = null;
        if (instanceStatus == Instance.Status.TERMINATED) {
            instanceStatusStr = Instance.TaskStatus.Status.SUCCESS.toString();
            Map<String, Instance.TaskStatus> taskStatus = null;
            try {
                taskStatus = instance.getTaskStatus();
            } catch (OdpsException e) {
                e.printStackTrace();
            }
            for (Map.Entry<String, Instance.TaskStatus> status : taskStatus.entrySet()) {
                if (status.getValue().getStatus() != Instance.TaskStatus.Status.SUCCESS) {
                    instanceStatusStr = status.getValue().getStatus().toString();
                    break;
                }
            }
        } else {
            instanceStatusStr = instanceStatus.toString();
        }

        Instance.TaskSummary summary = null;
        try {
            summary = instance.getTaskSummary("task name");
        } catch (OdpsException e) {
            e.printStackTrace();
        }
        String s = summary.getSummaryText();


        //Resources是MaxCompute中所有资源的集合。集合中的元素为Resource
        for (Resource r : odps.resources()) {

        }

        //Resource是对资源信息的描述。您可以通过Resources获取相应的资源。
        Resource r = odps.resources().get("resource name");
        try {
            r.reload();
        } catch (OdpsException e) {
            e.printStackTrace();
        }
        if (r.getType() == Resource.Type.TABLE) {
            TableResource tr = new TableResource(r);
            String tableSource = tr.getSourceTable().getProject() + "." + tr.getSourceTable().getName();
            if (tr.getSourceTablePartition() != null) {
                tableSource += " partition(" + tr.getSourceTablePartition().toString() + ")";
            }
        }
    }
}
