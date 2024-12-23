package com.geeklib.ether.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.aspectj.apache.bcel.classfile.Field;
import org.codehaus.groovy.control.CompilationFailedException;

import groovy.text.GStringTemplateEngine;

public class TemplateEngineUtils {

    public static String binding(String filePath, Map<String, Object> params) throws CompilationFailedException, ClassNotFoundException, IOException {

        GStringTemplateEngine engine = new GStringTemplateEngine();
        String content = null;
        FileReader fileReader = new FileReader(filePath);
        StringWriter stringWriter = new StringWriter();

        engine.createTemplate(fileReader).make(params).writeTo(stringWriter);
        content = stringWriter.toString();

        fileReader.close();
        stringWriter.close();

        return content;
    }

    public static String binding(String filePath, Object obj) throws CompilationFailedException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException{
        return binding(filePath, BeanUtils.toMap(obj));
    }

}
