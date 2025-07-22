package com.geeklib.ether.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {


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
