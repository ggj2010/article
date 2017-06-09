package com.ggj.article.module.common.utils;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.PutObjectResult;

import lombok.Getter;

/**
 * author:gaoguangjin
 * Description:阿里云sdk一些操作
 * url:https://help.aliyun.com/document_detail/oss/sdk/java-sdk/get-start.html?spm=5176.docoss/sdk/java-sdk/init.6.218.aPV3k1
 * Email:335424093@qq.com
 * Date 2015/12/29 15:37
 */
@Service
public class AliyunUtil implements InitializingBean {
    @Value("${access_Key_ID}")
    private String accessKeyId;
    @Value("${access_Key_Secret}")
    private String accessKeySecret;
    @Getter
    @Value("${endpoint}")
    public String endpoint;

    @Getter
    @Value("${bucket}")
    public String bucket;
    @Value("${bucket_imgkey}")
    private String bucketImgkey;

    private OSSClient client;

    public synchronized OSSClient   getClient() {
        if(null==client) {
            client= new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
        return client;
    }

    /**
     * 获取所有的bucket
     * @return
     */
    public List<Bucket> listBuckets()throws Exception {
        return  getClient().listBuckets();
    }


    public PutObjectResult putObject(String bucketName, String key, InputStream inputStream )throws Exception {
        return getClient().putObject(bucketName==null?bucket:bucketName, key, inputStream);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
