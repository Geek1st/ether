package com.geeklib.ether.devops.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.geeklib.ether.devops.services.BuildService;
import com.geeklib.ether.devops.services.DockerService;
import com.github.dockerjava.api.model.Image;
import org.springframework.web.bind.annotation.RequestParam;




// import io.swagger.annotations.Api;

// @Api(tags = "简易CI")
@RestController
@RequestMapping("/api/devops/build")
public class BuildController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DockerService dockerService;

    @Resource
    BuildService buildService;

    @GetMapping("/{imageName}")
    public ResponseEntity<Object> getImageByName(String imageName){
        //DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
       // List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();

        return ResponseEntity.ok("hello");
    }
    // @GetMapping("/image")
    // public ResponseEntity<List<Image>> listImages(@Valid Build build){
    //     //DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
    //     //List<Image> images = dockerClient.listImagesCmd().exec();

    //     return ResponseEntity.ok(null);
    // }


    /**
     * 
     * @param file 上传war等成品文件
     * @param buildInfo 构建信息
     * @return
     * @throws Exception
     */
    // @PostMapping("/b2i/war")
    // public ResponseEntity<Object> createImage(@RequestParam MultipartFile file, @Validated(BuildInfo.create.class) BuildInfo buildInfo) {

    //     Path path = FileUtils.getRandomTemporaryPath();                                      //获取随机路径
    //     Path binaryPath = Paths.get(path.toString(), file.getOriginalFilename());            //获取binary路径
    //     Path dockerfilePath = Paths.get(path.toString(), "dockerfile");
    //     path.toFile().mkdir();

    //     logger.debug("临时目录：" + path.toString());

    //     BuildImageResultCallback resultCallback = null;

    //     try {
    //         file.transferTo(binaryPath);
    //         buildInfo.setFilename(binaryPath.toString());
        
    //         String dockerfile = TemplateEngineUtils.binding(FileUtils.getTemplate("template/docker/tomcat/8.5.57/dockerfile"), buildInfo);
    //         FileUtils.transferTo(dockerfile, dockerfilePath.toFile());

    //         resultCallback = dockerService.build(dockerfilePath, buildInfo);
    //         dockerService.push(buildInfo.getTag());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         path.toFile().deleteOnExit();
    //     }
        
    //     return ResponseEntity.ok().body(resultCallback.awaitImageId());

    // }

    @PostMapping("/s2i/{projectCode}/{applicationCode}")
    public ResponseEntity<Object> createImage(BuildInfoS2i buildinfo, @PathVariable String projectCode, @PathVariable String applicationCode){
        
        return ResponseEntity.ok(buildService.build(buildinfo, projectCode, applicationCode));
    }

    @PostMapping("/b2i/{projectCode}/{applicationCode}")
    public ResponseEntity<Object> createImage(){

        return ResponseEntity.ok(null);
    }
    
}
