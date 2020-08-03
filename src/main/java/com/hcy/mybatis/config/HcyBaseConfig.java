package com.hcy.mybatis.config;

import lombok.Data;

/**
 * @auther hcy
 * @create 2020-08-03 13:57
 * @Description 基础配置，类似mybatis的全局配置文件中的内容
 */
@Data
public class HcyBaseConfig {

    private String scanPath;

    private HcyMapperRegistory mapperRegistory = new HcyMapperRegistory();

    public HcyBaseConfig scanPath(String scanPath){
        this.scanPath = scanPath;
        return this;
    }

    public void build(){
        if ( null == scanPath || scanPath.length() < 1 ){
            throw new RuntimeException("scan path is required .");
        }
    }

}
