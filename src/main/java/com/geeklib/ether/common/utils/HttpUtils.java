package com.geeklib.ether.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.hc.core5.http.protocol.ResponseDate;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.Data;

public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static String url;

    
    public static <T> T getEntity(URI uri, Class<T> type){
        HttpGet httpGet = new HttpGet(uri);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            int httpCode = httpResponse.getStatusLine().getStatusCode();

            if(httpCode == HttpStatus.OK.value()){
                return new ObjectMapper().readValue(httpResponse.getEntity().getContent(), type);
            }

        } catch (IOException e) {
           
            logger.error("HTTP请求失败,类型:GET,URL:" + uri, e);
        } 

        return null;
    }

    public static JsonNode getJSON(URI uri){
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)){
            int httpCode = httpResponse.getStatusLine().getStatusCode();

            if(httpCode == HttpStatus.OK.value()){

                try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                    return new ObjectMapper().readTree(inputStream);
                } catch (Exception e) {
                    
                }
                
            }
        }catch (IOException e) {
           
            logger.error("HTTP请求失败,类型:GET,URL:" + uri, e);
        } 

        return null;
    }
    
}
