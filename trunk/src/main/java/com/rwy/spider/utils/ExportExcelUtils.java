package com.rwy.spider.utils;

import com.rwy.spider.annotation.ExcelExportRuleAnnotation;
import com.rwy.spider.annotation.Merger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luocj on 2014/11/19.
 */
public class ExportExcelUtils {

    /**
     * 浏览器输出导出文件
     *
     * @param response
     * @param clazz    要导出的Dto类
     * @param list     结果集合
     * @param fileName 文件名
     * @param merger   是否合并单元格
     */
    public static void exportExcelToBrowser(HttpServletResponse response, Class clazz, List list, String fileName, boolean merger) {
        try {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ServletOutputStream ouputStream = response.getOutputStream();
            ExportExcelUtils.exportExcelToOutputStream(clazz, list, ouputStream, merger);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 浏览器输出导出文件
     *
     * @param response
     * @param clazz    要导出的Dto类
     * @param list     结果集合
     * @param fileName 文件名
     */
    public static void exportExcelToBrowser(HttpServletResponse response, Class clazz, List list, String fileName) {
        exportExcelToBrowser(response, clazz, list, fileName, false);
    }

    /*public static void exportExcelToOutputStream(Class clazz, List list, OutputStream os) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        if (list == null) {
            list = new ArrayList();
        }
        Field[] fields = clazz.getDeclaredFields();

        List<String> headNames = new ArrayList();
        List<Field> fieldList = new ArrayList();

        for (int i = 0; i < fields.length; i++) {

            String name = fields[i].getName();
            ExcelExportRuleAnnotation annotation = fields[i].getAnnotation(ExcelExportRuleAnnotation.class);
            if (annotation == null) {
                continue;
            }
            String label = annotation.label();
            String[] labels = label.split("\\|");
            for (int j = 0; j < labels.length; j++) {
                headNames.add(labels[j]);

            }
            fieldList.add(fields[i]);
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet s = wb.createSheet();
        s.setGridsPrinted(true);
        HSSFRow head = s.createRow(0);
        for (int j = 0; j < headNames.size(); j++) {
            HSSFCell cell = head.createCell((short) j);
            cell.setCellValue(new HSSFRichTextString((String) headNames.get(j)));

        }

        int rowindex = 1;
        for (int i = 0; i < list.size(); i++) {
            //excel限定的最大行数为65536

            if (i != 0 && i % 65530 == 0) {
                s = wb.createSheet();
                s.setGridsPrinted(true);
                HSSFRow head2 = s.createRow(0);
                for (int j = 0; j < headNames.size(); j++) {
                    HSSFCell cell = head2.createCell((short) j);
                    cell.setCellValue(new HSSFRichTextString((String) headNames.get(j)));

                }
                rowindex = 1;
            }
            Object obj = list.get(i);
            HSSFRow row = s.createRow(rowindex++);
            int idx = 0;
            for (int j = 0; j < fieldList.size(); j++) {

                Field field = (Field) fieldList.get(j);
                ExcelExportRuleAnnotation annotation = field.getAnnotation(ExcelExportRuleAnnotation.class);
                String getValueMethodName = annotation.getValueMethod();

                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = clazz.getMethod(methodName, null);

                Object object = method.invoke(obj, null);

                if (object != null) {
                    if (getValueMethodName == null || getValueMethodName.equals("")) {
                        String value = "";
                        value = object.toString();
                        HSSFCell cell = row.createCell((short) idx++);
                        cell.setCellValue(new HSSFRichTextString(value));
                    } else {
                        String[] getValueMethodNames = getValueMethodName.split("\\|");

                        for (int k = 0; k < getValueMethodNames.length; k++) {
                            String value = "";
                            Method getValueMethod = object.getClass().getMethod(getValueMethodNames[k], null);
                            Object o = (Object) getValueMethod.invoke(object, null);
                            if (o != null) {
                                value = o.toString();
                            }
                            HSSFCell cell = row.createCell((short) idx++);
                            cell.setCellValue(new HSSFRichTextString(value));
                        }
                    }

                } else {
                    HSSFCell cell = row.createCell((short) idx++);
                    cell.setCellValue(new HSSFRichTextString(""));
                }
            }
        }
        wb.write(os);
        os.flush();
        os.close();
    }*/

    public static void exportExcelToOutputStream(Class clazz, List list, OutputStream os, boolean merger) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        if (list == null) {
            list = new ArrayList();
        }
        Field[] fields = clazz.getDeclaredFields();

        List<String> headNames = new ArrayList();
        List<Field> fieldList = new ArrayList();
        List<Integer> mergerList = new ArrayList();


        for (int i = 0; i < fields.length; i++) {

            String name = fields[i].getName();
            ExcelExportRuleAnnotation annotation = fields[i].getAnnotation(ExcelExportRuleAnnotation.class);
            if (annotation == null) {
                continue;
            }
            String label = annotation.label();
            String[] labels = label.split("\\|");
            for (int j = 0; j < labels.length; j++) {
                headNames.add(labels[j]);

            }
            fieldList.add(fields[i]);
            if(merger){
                Merger mergerAnnotation = fields[i].getAnnotation(Merger.class);
                if (mergerAnnotation == null) {
                    continue;
                }
                boolean mer = mergerAnnotation.merger();
                if(mer){
                    mergerList.add(i);
                }
            }
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet s = wb.createSheet();
        s.setGridsPrinted(true);
        HSSFRow head = s.createRow(0);
        for (int j = 0; j < headNames.size(); j++) {
            HSSFCell cell = head.createCell((short) j);
            cell.setCellValue(new HSSFRichTextString((String) headNames.get(j)));

        }

        int rowindex = 1;
        for (int i = 0; i < list.size(); i++) {
            //excel限定的最大行数为65536

            if (i != 0 && i % 65530 == 0) {
                s = wb.createSheet();
                s.setGridsPrinted(true);
                HSSFRow head2 = s.createRow(0);
                for (int j = 0; j < headNames.size(); j++) {
                    HSSFCell cell = head2.createCell((short) j);
                    cell.setCellValue(new HSSFRichTextString((String) headNames.get(j)));

                }
                rowindex = 1;
            }
            Object obj = list.get(i);
            HSSFRow row = s.createRow(rowindex++);
            int idx = 0;
            for (int j = 0; j < fieldList.size(); j++) {

                Field field = (Field) fieldList.get(j);
                ExcelExportRuleAnnotation annotation = field.getAnnotation(ExcelExportRuleAnnotation.class);
                String getValueMethodName = annotation.getValueMethod();

                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = clazz.getMethod(methodName, null);

                Object object = method.invoke(obj, null);

                if (object != null) {
                    if (getValueMethodName == null || getValueMethodName.equals("")) {
                        String value = "";
                        value = object.toString();
                        HSSFCell cell = row.createCell((short) idx++);
                        cell.setCellValue(new HSSFRichTextString(value));
                    } else {
                        String[] getValueMethodNames = getValueMethodName.split("\\|");

                        for (int k = 0; k < getValueMethodNames.length; k++) {
                            String value = "";
                            Method getValueMethod = object.getClass().getMethod(getValueMethodNames[k], null);
                            Object o = (Object) getValueMethod.invoke(object, null);
                            if (o != null) {
                                value = o.toString();
                            }
                            HSSFCell cell = row.createCell((short) idx++);
                            cell.setCellValue(new HSSFRichTextString(value));
                        }
                    }

                } else {
                    HSSFCell cell = row.createCell((short) idx++);
                    cell.setCellValue(new HSSFRichTextString(""));
                }
            }
        }
        if(merger){
            for(int i=0;i<mergerList.size();i++){
                addMergedRegion(s, mergerList.get(i), 0, s.getLastRowNum(), wb);
            }
        }
        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * 合并单元格
     *
     * @param sheet    要合并单元格的excel 的sheet
     * @param cellLine 要合并的列
     * @param startRow 要合并列的开始行
     * @param endRow   要合并列的结束行
     */
    private static void addMergedRegion(HSSFSheet sheet, int cellLine, int startRow, int endRow, HSSFWorkbook workBook) {

        HSSFCellStyle style = workBook.createCellStyle(); // 样式对象

        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        //获取第一行的数据,以便后面进行比较
        String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();

        int count = 0;
        boolean flag = false;
        for (int i = 1; i <= endRow; i++) {
            String s_current = sheet.getRow(i).getCell(0).getStringCellValue();
            if (s_will.equals(s_current)) {
                s_will = s_current;
                if (flag) {
                    sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                    HSSFRow row = sheet.getRow(startRow - count);
                    String cellValueTemp = sheet.getRow(startRow - count).getCell(0).getStringCellValue();
                    HSSFCell cell = row.createCell(0);
                    cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                    cell.setCellStyle(style); // 样式
                    count = 0;
                    flag = false;
                }
                startRow = i;
                count++;
            } else {
                flag = true;
                s_will = s_current;
            }
            //由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
            if (i == endRow && count > 0) {
                sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
                String cellValueTemp = sheet.getRow(startRow - count).getCell(0).getStringCellValue();
                HSSFRow row = sheet.getRow(startRow - count);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
                cell.setCellStyle(style); // 样式
            }
        }
    }
}
