package com.defectscan.tools;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GetObjectRequest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssTool {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;


        log.info("文件上传到:{}", url);

        return url;
    }

    /**
     * 删除图片
     * @param aliyunUrl
     */
    public boolean deleteFile(String aliyunUrl) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try{
            int lastIndex = aliyunUrl.lastIndexOf('/');
            if (lastIndex != -1) {
                String objectName =  aliyunUrl.substring(lastIndex + 1);
                ossClient.deleteObject(bucketName, objectName);
                ossClient.shutdown();
                log.info("云端图片：{} 删除成功", objectName);
                return true;
            }
        } catch (OSSException oe) {
            log.info("云端图片删除失败：{}", oe.getMessage());
            return false;
        }
        return true;
    }

    public void restoreImage(String localDir,String backupUrl) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 从 OSS 下载文件到本地
            File file = new File(backupUrl);
            String fileName = file.getName();
            ossClient.getObject(new GetObjectRequest(bucketName, fileName), new File(localDir));
            System.out.println("图片恢复成功，路径：" + localDir);
        } catch (Exception e) {
            System.err.println("图片恢复失败：" + e.getMessage());
        } finally {
            // 关闭 OSSClient
            ossClient.shutdown();
        }
    }






}
