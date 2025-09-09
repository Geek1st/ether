package com.geeklib.ether.common.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class PrettyPrintHandler extends MappingJackson2HttpMessageConverter {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        String prettyParam = WebUtils.findParameterValue(request, "pretty");
        boolean pretty = Boolean.parseBoolean(prettyParam);
        ObjectMapper mapper = pretty
                ? objectMapper.copy().enable(SerializationFeature.INDENT_OUTPUT)
                : objectMapper;

        try (OutputStream out = outputMessage.getBody()) {
            mapper.writeValue(out, object);
        }
    }
}
