package com.geeklib.ether.devops.handler;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.sse.Sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.config.WorkspaceProperties;
import com.geeklib.ether.devops.entity.BuildInfo;
import com.hazelcast.client.impl.protocol.codec.ScheduledExecutorSubmitToMemberCodec;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

/**
 * 处理构建日志的类
 * 该类负责读取、存储、解析和格式化构建日志
 */
@Component
public class BuildLogHandler {

    final String BUILD_FINISHED_ERROR = "# exit 1 : build finished with error";
    final String BUILD_FINISHED_SUCCESS = "# exit 0 : build finished successfully";
    private final String loggerName = "build.log";

    final String BUILD_LOG_TOPIC_PREFIX = "build-log-";

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Resource
    WorkspaceProperties workspaceProperties;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 发布构建日志到 Hazelcast 主题
     * @param projectName 项目名
     * @param applicationName 应用名
     * @param buildNumber 构建编号
     * @param log 日志内容
     */
    private void publishLog(String projectName, String applicationName, Long buildNumber, String log) {
        String topicName = getTopicName(projectName, applicationName, buildNumber);
        ITopic<String> topic = hazelcastInstance.getTopic(topicName);
        topic.publish(log);
    }

    /**
     * 发布构建日志到 Hazelcast 主题
     * @param buildInfo 构建信息对象
     * @param log 日志内容
     */
    private void publishLog(BuildInfo buildInfo, String log) {
        String projectName = buildInfo.getProjectName();
        String applicationName = buildInfo.getApplicationName();
        Long buildNumber = buildInfo.getBuildNumber();
        publishLog(projectName, applicationName, buildNumber, log);
    }

    /**
     * 订阅构建日志
     * 该方法返回一个 SseEmitter 对象，用于实时接收构建日志
     * @param projectName 项目名
     * @param applicationName 应用名
     * @param buildNumber   构建编号
     * @return SseEmitter 对象，用于接收构建日志
     */
    public SseEmitter subscribeLog(String projectName, String applicationName, Long buildNumber) {
        String topicName = getTopicName(projectName, applicationName, buildNumber);

        SseEmitter sseEmitter = new SseEmitter();
        
        ITopic<String> topic = hazelcastInstance.getTopic(topicName);
        topic.addMessageListener(message -> {
            try {
                sseEmitter.send(message.getMessageObject());
            } catch (IOException e) {
                logger.error("Error sending log message to SSE emitter", e);
                sseEmitter.completeWithError(e);
            }
        });
        return sseEmitter;
    }

    

    /**
     * 获取构建日志的主题名称
     * 主题名称格式为 "build-log-{projectName}-{applicationName}-{buildNumber}"
     * @param projectName 项目名
     * @param applicationName 应用名
     * @param buildNumber 构建编号
     * @return 构建日志的主题名称
     */ 
    private String getTopicName(String projectName, String applicationName, Long buildNumber) {
        return String.format("%s-%s-%s-%d", BUILD_LOG_TOPIC_PREFIX ,projectName, applicationName, buildNumber);
    }

    /**
     * 获取构建日志文件的路径
     * @param projectName 项目名
     * @param applicationName 应用名
     * @param buildNumber 构建编号
     * @return 本次构建日志文件的完整路径
     */
    private Path getBuildLogPath(String projectName, String applicationName, Long buildNumber) {
        return Paths.get(workspaceProperties.getBuildPath(), projectName, applicationName, buildNumber.toString(), loggerName);
    }


    /**
     * 保存构建日志到文件
     * @param projectName 项目名
     * @param applicationName 应用名
     * @param buildNumber 构建编号
     * @param log 日志内容
     * @throws IOException 
     */
    public void writeLog(String projectName, String applicationName, Long buildNumber, String log) {
        Path buildLogPath = getBuildLogPath(projectName, applicationName, buildNumber);

        try {
            Files.createDirectories(buildLogPath.getParent()); // 确保目录存在
            Files.write(buildLogPath, (log + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            publishLog(projectName, applicationName, buildNumber, log);
        } catch (IOException e) {
            logger.error("构建日志写入失败: {}", buildLogPath, e);
            return; 
        }
        
    }

    public void writeLog(BuildInfo buildInfo, String log) {
        String projectName = buildInfo.getProjectName();
        String applicationName = buildInfo.getApplicationName();
        Long buildNumber = buildInfo.getBuildNumber();
        writeLog(projectName, applicationName, buildNumber, log);
    }


    /**
     * 读取构建日志文件
     * @param projectName 项目名
     * @param applicationName 应用名
     * @param buildNumber 构建编号
     * @return 构建日志内容的列表
     * @throws IOException 
     */
    public List<String> readLog(String projectName, String applicationName, Long buildNumber) throws IOException {
        Path buildLogPath = getBuildLogPath(projectName, applicationName, buildNumber);
        List<String> log = Files.readAllLines(buildLogPath, StandardCharsets.UTF_8);
        return log;
    }
    
    // public List<String> readLog(Path buildLogPath) {
    
        

        
    //     return null; 
    // }

    // public List<String> readLog(String projectName, String applicationName, Long buildNumber) throws IOException {

    //     Path buildLogPath = getBuildLogPath(projectName, applicationName, buildNumber);
    //     List<String> log = Collections.emptyList();

 
    //     log = Files.readAllLines(buildLogPath, StandardCharsets.UTF_8);


    //     if (!log.isEmpty()) {
    //        String lastLine = log.get(log.size() - 1);           
    //     } 

    //     return log;
    // }

    // public SseEmitter readLogStream(String projectName, String applicationName, long buildNumber) throws IOException {

    //     Path buildLogPath = getBuildLogPath(projectName, applicationName, buildNumber);
    //     List<String> log = Collections.emptyList();
    //     SseEmitter sseEmitter = new SseEmitter();
    //     RandomAccessFile file = new RandomAccessFile(buildLogPath.toFile(), "r");
    //     long position = 0;
    //     while (true) {
    //         file.seek(position);
    //         String line;
    //         boolean isEndOfFile = false;
    //         while ( (line = file.readLine()) != null) {
    //             sseEmitter.send(line);
    //             position += line.length() + System.lineSeparator().length(); // 更新位置
    //         }
    //         position = file.getFilePointer();
    //     }

    //     return null;
    // }

    // public void writeLog(String projectName, String applicationName, Long buildNumber, String log) throws IOException {

    //     Path path = getBuildLogPath(projectName, applicationName, buildNumber);
    //     Files.write(path, log.getBytes(), StandardOpenOption.APPEND);
    // }

}
