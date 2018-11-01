package com.yz.springboot.service.impl;

import com.yz.springboot.entity.Car;
import com.yz.springboot.exception.CommonException;
import com.yz.springboot.exception.ErrorEnum;
import com.yz.springboot.mapper.CarMapper;
import com.yz.springboot.service.ICarService;
import com.yz.springboot.util.parser.IParser;
import com.yz.springboot.util.parser.ParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author huiRan
 * @date 2018/9/11
 */
@Service
public class ICarServiceImpl implements ICarService {

    @Autowired
    private CarMapper carMapper;

    @Override
    public void importExcelToDb(MultipartFile multipartFile) {
        long startTime = System.currentTimeMillis();

        IParser parser = ParserFactory.getParser(ParserFactory.ParserEnum.EXCEL);
        if (parser == null) {
            throw new CommonException(ErrorEnum.PARSE_EXCEL_ERROR);
        }
        List<Car> list = parser.parseFile(multipartFile, Car.class);

        long parseTime = System.currentTimeMillis();

        System.out.println("解析耗时为" + ((parseTime - startTime) / 1000L) + "s");

        carMapper.batchInsert(list);

        long insertTime = System.currentTimeMillis();

        System.out.println("插入数据库耗时为" + ((insertTime - parseTime) / 1000L) + "s");
        System.out.println("总耗时为" + ((insertTime - startTime) / 1000L) + "s");
    }
}
