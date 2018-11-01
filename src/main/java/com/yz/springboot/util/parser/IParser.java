package com.yz.springboot.util.parser;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author huiRan
 * @date 2018/9/12
 */
public interface IParser {

    /**
     * 文件解析
     *
     * @param multipartFile 文件
     * @param loader
     * @param <T>
     * @return
     */
    <T> List<T> parseFile(MultipartFile multipartFile, Class<T> loader);
}
