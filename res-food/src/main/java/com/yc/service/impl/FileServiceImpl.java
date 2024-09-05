package com.yc.service.impl;

import com.yc.service.FileService;
import com.yc.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private AliOssUtil aliOssUtil;;

    @Async
    @Override
    public CompletableFuture<String> upload(MultipartFile file) {
        String url = null;
        try {
            url = aliOssUtil.upload(file);
        }catch (Exception e){
            log.error("文件上传失败",e);
        }
        return CompletableFuture.completedFuture(url);
    }

    @Override
    public List<String> upload(MultipartFile[] files) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            CompletableFuture<String> upload = upload(file);
            try {
                urls.add(upload.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return urls;
    }
}
