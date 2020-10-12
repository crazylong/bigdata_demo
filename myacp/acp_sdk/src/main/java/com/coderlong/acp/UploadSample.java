package com.coderlong.acp;

import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.ArrayRecord;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TunnelException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class UploadSample {
	private static String accessId;
	private static String accessKey;  
	private static String OdpsEndpoint;
	private static String TunnelEndpoint;
	private static String project;
	private static String table;
	private static String partition;
	private static String fieldDelimeter;
	private static String file;

	private static void printUsage(String msg) {
	  System.out.println(
	    "Usage: UploadSample -f file \\\n"
	      + "                  -t table\\\n"
	      + "                  -c config_file \\\n"
	      + "                  [-p partition] \\\n"
	      + "                  [-fd field_delimiter] \\\n" 
	      );
	  if (msg != null) {
		System.out.println(msg);
	  }
	}
	private static void parseArgument(String[] args) {
		for (int i = 0; i < args.length; i++) {
		  if ("-f".equals(args[i])) {
		    if (++i ==  args.length) {
		      throw new IllegalArgumentException("source file not specified in -f");
		    }
		    file = args[i];
		  }
		  else if ("-t".equals(args[i])) {
		    if (++i ==  args.length) {
		      throw new IllegalArgumentException("ODPS table not specified in -t");
		    }
		    table = args[i];
		  } 
		  else if ("-c".equals(args[i])) {
		    if (++i ==  args.length) {
		      throw new IllegalArgumentException(
		          "ODPS configuration file not specified in -c");
		    }
		    try {
		      InputStream is = new FileInputStream(args[i]);
		      Properties props = new Properties();
		      props.load(is);
		      accessId = props.getProperty("access_id");
		      accessKey = props.getProperty("access_key");
		      project = props.getProperty("project_name");
		      OdpsEndpoint = props.getProperty("end_point");
		      TunnelEndpoint = props.getProperty("tunnel_endpoint");
		    } catch (IOException e) {
		      throw new IllegalArgumentException(
		          "Error reading ODPS config file '" + args[i] + "'.");
		    }
		  }
		  else if ("-p".equals(args[i])){
		    if (++i ==  args.length) {
		      throw new IllegalArgumentException(
		          "odps table partition not specified in -p");
		    }
		    partition = args[i];
		  } 
		  else if ("-fd".equals(args[i])){
		    if (++i ==  args.length) {
		      throw new IllegalArgumentException(
		         "odps table partition not specified in -p");
		    }
		    fieldDelimeter = args[i];
		  } 
		} 
		if(file == null) {
		  throw new IllegalArgumentException(
		      "Missing argument -f file");
		}
		if (table == null) {
		  throw new IllegalArgumentException(
		      "Missing argument -t table");
		}  
		
		if (accessId == null || accessKey == null || 
		    project == null || OdpsEndpoint == null || TunnelEndpoint == null) {
		  throw new IllegalArgumentException(
		      "ODPS conf not set, please check -c odps.conf");
		  }
	}

	public static void main(String args[]) {
	 	try {
	     parseArgument(args);
	   } catch (IllegalArgumentException e) {
	     printUsage(e.getMessage());
	     System.exit(2);
	   }    
	   
	   Account account = new AliyunAccount(accessId, accessKey);
	   Odps odps = new Odps(account);
	   odps.setDefaultProject(project);
	   odps.setEndpoint(OdpsEndpoint);  
    
	   BufferedReader br = null;
	   try {      
	     TableTunnel tunnel = new TableTunnel(odps);
	     tunnel.setEndpoint(TunnelEndpoint);
	     TableTunnel.UploadSession uploadSession = null;
	     if(partition != null) {
	       PartitionSpec spec = new PartitionSpec(partition);
	       System.out.println(spec.toString());
	       uploadSession=tunnel.createUploadSession(project, table,spec);
	       System.out.println("Session Status is : " + uploadSession.getStatus().toString());
	     }
	     else
	     {
	       uploadSession= tunnel.createUploadSession(project, table);
	       //System.out.println("Session Status is : " + uploadSession.getStatus().toString());
	     }        
	     Long blockid = (long) 0;
	     RecordWriter recordWriter = uploadSession.openRecordWriter(blockid, true);
	     Record record = uploadSession.newRecord();
    
	     TableSchema schema = uploadSession.getSchema();      
	     RecordConverter converter = new RecordConverter(schema, "NULL", null, null);      
	     br = new BufferedReader(new FileReader(file));
	     Pattern pattern = Pattern.compile(fieldDelimeter);
	     String line = null;
	     while ((line = br.readLine()) != null) {
	       String[] items=pattern.split(line,0);
	       record = converter.parse(items);
	       recordWriter.write(record);
	     }       
	     recordWriter.close();      
	     Long[] blocks = {blockid};
	     uploadSession.commit(blocks);   
		 System.out.println("Upload succeed!");
	   } catch (TunnelException e) {
	     e.printStackTrace();
	   } catch (IOException e) {
	     e.printStackTrace();
	   }finally {
	     try {
	       if (br != null)
	         br.close();
	     } catch (IOException ex) {
	       ex.printStackTrace();
	     }
	   }
	} 
}
class RecordConverter {
	private TableSchema schema;
	private String nullTag;
	private SimpleDateFormat dateFormater;
    
	private DecimalFormat doubleFormat;
	private String DEFAULT_DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";
	public RecordConverter(TableSchema schema, String nullTag, String dateFormat,
	    String tz) {
	  this.schema = schema;
	  this.nullTag = nullTag;
	  if (dateFormat == null) {
	    this.dateFormater = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN);
	  } else {
	    dateFormater = new SimpleDateFormat(dateFormat);
	  }
	  dateFormater.setLenient(false);
	  dateFormater.setTimeZone(TimeZone.getTimeZone(tz == null ? "GMT" : tz));
    
	  doubleFormat = new DecimalFormat();
	  doubleFormat.setMinimumFractionDigits(0);
	  doubleFormat.setMaximumFractionDigits(20);
	}
    
	/**
	 * record to String array
	 * */
	public String[] format(Record r) {
	  int cols = schema.getColumns().size();
	  String[] line = new String[cols];
	  String colValue = null;
	  for (int i = 0; i < cols; i++) {
	    Column column = schema.getColumn(i);
	    OdpsType t = column.getType();
	    switch (t) {
	    case BIGINT: {
	      Long v = r.getBigint(i);
	      colValue = v == null ? null : v.toString();
	      break;
	    }
	    case DOUBLE: {
	      Double v = r.getDouble(i);
	      if (v == null) {
	        colValue = null;
	      } else {
	        colValue = doubleFormat.format(v).replaceAll(",", "");
	      }
	      break;
	    }
	    case DATETIME: {
	      Date v = r.getDatetime(i);
	      if (v == null) {
	        colValue = null;
	      } else {
	        colValue = dateFormater.format(v);
	      }
	      break;
	    }
	    case BOOLEAN: {
	      Boolean v = r.getBoolean(i);
	      colValue = v == null ? null : v.toString();
	      break;
	    }
	    case STRING: {
	      String v = r.getString(i);
	      colValue = (v == null ? null : v.toString());
	      break;
	    }
	    default:
	      throw new RuntimeException("Unknown column type: " + t);
	    }
    
	    if (colValue == null) {
	      line[i] = nullTag;
	    } else {
	      line[i] = colValue;
	    }
	  }
	  return line;
	}
    
	/**
	 * String array to record
	 * @throws ParseException 
	 * */
	public Record parse(String[] line){
    
	  if (line == null) {
	    return null;
	  }
    
	  int columnCnt = schema.getColumns().size();
	  Column[] cols = new Column[columnCnt];
	  for (int i = 0; i < columnCnt; ++i) {
	    Column c = new Column(schema.getColumn(i).getName(), 
	        schema.getColumn(i).getType());          
	    cols[i] = c;
	  }
	  ArrayRecord r = new ArrayRecord(cols);
	  int i = 0;
	  for (String v : line) {
	    if (v.equals(nullTag)) {
	      i++;
	      continue;
	    }
	    if (i >= columnCnt) {
	      break;
	    }
	    OdpsType type = schema.getColumn(i).getType();
	    switch (type) {
	    case BIGINT:
	      r.setBigint(i, Long.valueOf(v));
	      break;
	    case DOUBLE:
	      r.setDouble(i, Double.valueOf(v));
	      break;
	    case DATETIME:
	      try {
	        r.setDatetime(i, dateFormater.parse(v));
	      } catch (ParseException e) {
	        throw new RuntimeException(e.getMessage());
	      }
	      break;
	    case BOOLEAN:
	      v = v.trim().toLowerCase();
	      if (v.equals("true") || v.equals("false")) {
	        r.setBoolean(i, v.equals("true") ? true : false);
	      } else if (v.equals("0") || v.equals("1")) {
	        r.setBoolean(i, v.equals("1") ? true : false);
	      } else {
	        throw new RuntimeException(
	            "Invalid boolean type, expect: true|false|0|1");
	      }
	      break;
	    case STRING:
	      r.setString(i, v);
	      break;
	    default:
	      throw new RuntimeException("Unknown column type");
	    }
	    i++;
	  }
	  return r;
	}
}
