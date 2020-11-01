package com.zl52074.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/1 22:59
 */
public interface UploadService {
    /**
     * @description 图片上传
     * @param file
     * @return java.lang.String
     * @author zl52074
     * @time 2020/11/1 23:06
     */
    public String uploadImage(MultipartFile file);
}
