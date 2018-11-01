package com.yz.springboot.util.parser;

import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;
import com.yz.springboot.util.CloseUtils;
import com.yz.springboot.util.FormatUtils;
import com.yz.springboot.util.TextUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huiRan
 * @date 2018/9/12
 */
public class ExcelParserImpl implements IParser {

    private static final String SUFFIX_EXCEL_2003 = ".xls";
    private static final String SUFFIX_EXCEL_2007 = ".xlsx";

    @Override
    public <T> List<T> parseFile(MultipartFile multipartFile, Class<T> loader) {
        //创建Workbook
        Workbook workbook = generateWorkbook(multipartFile);

        List<T> list = new ArrayList<>();
        //获取excel中的工作薄的个数
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            //获取工作簿
            Sheet sheet = workbook.getSheetAt(i);
            //从第一行开始遍历,第一行一般是标题
            int firstRowNum = sheet.getFirstRowNum() + 1;
            //获取工作薄的最后一行
            int lastRowNum = sheet.getLastRowNum();
            for (int j = firstRowNum; j <= lastRowNum; j++) {
                Row row = sheet.getRow(j);
                //获取第一列  +1是因为excel中第一列通常是序号,不需要
                int firstCellNum = row.getFirstCellNum() + 1;

                //反射创建对象
                T instance = null;
                try {
                    instance = loader.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (instance == null) {
                    throw new CommonException(ErrorEnum.PARSE_EXCEL_ERROR);
                }

                //获取对象中所有的变量
                Field[] fieldArray = loader.getDeclaredFields();
                int length = fieldArray.length;

                for (int k = firstCellNum; k < length; k++) {
                    Cell cell = row.getCell(k);
                    if (cell == null) {
                        continue;
                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String cellValue = cell.getStringCellValue();
                    if (!TextUtils.isEmpty(cellValue)) {
                        //给对象的成员变量赋值
                        Field field = fieldArray[k];
                        field.setAccessible(true);
                        try {
                            field.set(instance, getFieldValue(field, cellValue));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                list.add(instance);
            }
        }
        return list;
    }

    private Workbook generateWorkbook(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new CommonException(ErrorEnum.FILE_NULL_ERROR);
        }
        //获取文件的名字
        String originalFilename = multipartFile.getOriginalFilename();
        if (TextUtils.isEmpty(originalFilename)) {
            throw new CommonException(ErrorEnum.FILE_FORMAT_ERROR);
        }
        InputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream = multipartFile.getInputStream();
            workbook
                    //2003版excel
                    = originalFilename.endsWith(SUFFIX_EXCEL_2003) ? new HSSFWorkbook(inputStream)
                    //2007版excel
                    : originalFilename.endsWith(SUFFIX_EXCEL_2007) ? new XSSFWorkbook(inputStream)
                    : null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(inputStream);
        }
        if (workbook == null) {
            throw new CommonException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        return workbook;
    }

    private Object getFieldValue(Field field, String cellValue) {
        Object fieldValue;
        Class<?> type = field.getType();
        if (type == Integer.class) {
            fieldValue = FormatUtils.parseInt(cellValue);
        } else if (type == Long.class) {
            fieldValue = FormatUtils.parseLong(cellValue);
        } else {
            fieldValue = cellValue;
        }
        return fieldValue;
    }
}
