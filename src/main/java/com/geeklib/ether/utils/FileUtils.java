package com.geeklib.ether.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.geeklib.ether.config.FileConfig;

@Component
public class FileUtils {

    static FileConfig fileConfig;

    @Autowired
    public void setFileConfig(FileConfig fileConfig) {
        FileUtils.fileConfig = fileConfig;
    }

    public static String getDataDir(){
        return fileConfig.getDataDir();
    }

    public static String getTemplate(String path){
        return FileUtils.class.getClassLoader().getResource(path).getFile();
    }

    // public static void transferTo(String data, File file){
    //     try (FileWriter fileWriter = new FileWriter(file)) {
    //         IOUtils.write(data, fileWriter);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * 将文本保存成文件
     * @param character 文本内容
     * @param path 文件路径
     * @return 文件对象
     */
    public static File toFile(String character, Path path){
        path.getParent().toFile().mkdirs();
        if ( !path.toFile().exists() ){
            try {
                path.toFile().createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try (FileWriter fileWriter = new FileWriter(path.toFile())) {
            IOUtils.write(character, fileWriter);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return path.toFile();
    }

    
}
