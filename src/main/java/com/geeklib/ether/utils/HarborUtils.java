package com.geeklib.ether.utils;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Resource;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.geeklib.ether.config.Properties;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

public class HarborUtils {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Resource
    Properties properties;

    UriComponentsBuilder harborContext = UriComponentsBuilder
            .fromUriString(properties.getDocker().getRegistry().getApi());

    public boolean ping() {
        HttpGet httpGet = new HttpGet(harborContext + "/ping");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            String entity = EntityUtils.toString(response.getEntity());
            int httpCode = response.getStatusLine().getStatusCode();

            if (200 == httpCode && "pong".equals(entity)) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void listRepositories() {
        String url = "/repositories";
        UriComponentsBuilder.fromUriString(url).build();
        // HttpUtils.get(URI.create(uri + "/repositories"), User.class);
    }

    public void listRepositories(String projectName) {
        String url = "/projects/{projectName}/repositories";

        harborContext.path(url).buildAndExpand(projectName);

        HttpUtils.getJSON(URI.create(url));

    }
}
