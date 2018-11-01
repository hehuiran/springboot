package com.yz.springboot.controller;

import com.yz.springboot.entity.BaseResponse;
import com.yz.springboot.service.ICarService;
import com.yz.springboot.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huiRan
 * @date 2018/9/10
 */
@RestController
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private ICarService carService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    public BaseResponse<String> uploadExcel(@RequestParam(value = "file") MultipartFile multipartFile) {

        carService.importExcelToDb(multipartFile);

        return ResponseUtils.successNoData();
    }
}
