package com.rwy.spider.utils;

import com.rwy.spider.annotation.ExcelExportRuleAnnotation;
import org.apache.poi.hssf.usermodel.*;

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

    public static void exportExcelToBrowser(HttpServletResponse response, Class clazz, List list, String fileName) {
        try {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ServletOutputStream ouputStream = response.getOutputStream();
            ExportExcelUtils.exportExcelToOutputStream(clazz, list, ouputStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportExcelToOutputStream(Class clazz, List list, OutputStream os) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
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
    }
}
