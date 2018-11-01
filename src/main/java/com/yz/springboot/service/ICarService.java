package com.yz.springboot.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author huiRan
 * @date 2018/9/11
 */
public interface ICarService {

    /**
     * 导入excel到数据库
     * @param multipartFile
     */
    void importExcelToDb(MultipartFile multipartFile);
}
