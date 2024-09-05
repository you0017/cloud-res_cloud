package com.yc.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 文件上传的业务类
 */
public interface FileService {

    /**
     * 单文件上传
     * @param file
     * @return  因为是异步上传，所以如果要获取返回值的话，就需要用到Future对象
     */
    public CompletableFuture<String> upload(MultipartFile file);

    public List<String> upload(MultipartFile[] files);
}
