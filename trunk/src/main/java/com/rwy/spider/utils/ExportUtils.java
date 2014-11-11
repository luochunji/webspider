package com.rwy.spider.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.csvreader.CsvWriter;

public class ExportUtils {
	public static CsvWriter cw;  
	public static File csv;  

	public static void ExportCSV(String csv_path,List list,String[] heads) throws Exception{
		initCsvFile(csv_path,heads);
		writeCsvFile(list);
	}
	public static void initCsvFile(String csv_path,String[] heads) throws Exception{
		   csv = new File(csv_path);  
			  
	       if (csv.isFile()) {  
	           csv.delete();  
	       }  
	       cw = new CsvWriter(new FileWriter(csv, true), ',');  
		   for(String head : heads){
               cw.write(head);
           }
	       cw.endRecord();  
	       cw.close();  	
	}
	public static void writeCsvFile(List list) throws Exception{
		cw = new CsvWriter(new FileWriter(csv, true), ',');  
		for(int i=0;i<list.size();i++){
			String[] strs = (String[]) list.get(i);
			for(String value:strs){
				cw.write(value);  
			}
			cw.endRecord();
		}
		cw.close();  
	}
}
