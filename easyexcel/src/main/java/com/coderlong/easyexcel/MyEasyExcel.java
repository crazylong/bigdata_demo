package com.coderlong.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author LongQiong
 * @date 2021/2/5
 */
@Component
public class MyEasyExcel {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    final static String[] TABLE_LIST = {"1_床位费住院天数", "2_护理费住院天数","3_抢救数住院天数","4_多收纤维食管镜","5_腔镜手术检查","6_透明敷料","7_结肠镜局部麻醉","8_局部浸润麻醉监测","9_肛门镜检查","10_产科产检宫颈内口探查","11_组织病理学检查","12_静脉留置针","13_偏瘫肢体综合训练","14_糖化血红蛋白","15_","16_重症监护","17_气管切开护理吸痰护理","18_甲状腺","19_胸腔","20_椎间盘手术","21_康复综合评定","22_B型钠尿肽前体测定","22_丙型肝炎抗体测定","22_抗缪勒氏管激素检测","22_降钙素原检测","23_红光治疗","24_引导式教育训练","25_脑功能检查","26_关节粘连传统松解术","27_计算机图文报告","28_单纤维肌电图","29_低频脉冲电治疗","30_吸功能监测","31_肠粘连松解术","32_超声刀使用","33_日常生活能力评定","34_疲劳度测定","35_心肌酶谱无指症常规检查","36_超敏C反应蛋白","37_血浆D二聚体测定","38_网织红细胞计数","39_喜炎平注射液","40_注射用鼠神经生长因子"};

    //@PostConstruct
    public void getFileNames(){
        File dir = new File("D:\\code\\bigdata_demo\\easyexcel\\src\\main\\resources\\excel");
       // Object[] files = Arrays.stream(dir.listFiles()).sorted((i, j)-> i.getName().compareTo(i.getName())).toArray();

        File[] files = dir.listFiles();

        for(int i=0; i < files.length; i++){
            final File f = (File)files[i];
            readExcel(f.getAbsolutePath(), f.getName(), TABLE_LIST[i]);
        }
    }



    private void readExcel(String path, String fileName, String tableName){
        fileName = fileName.substring(3).replace(".xls", "");
        EasyExcel.read(path, new NoModelDataListener(jdbcTemplate, fileName, tableName)).sheet(1).doRead();
    }



}
