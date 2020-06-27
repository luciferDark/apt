package com.ll.apt_process;

import com.google.auto.service.AutoService;
import com.ll.apt_annotation.LLBindView;
import com.ll.apt_annotation.LLOnClick;
import com.sun.java.browser.net.ProxyInfo;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

//使用注解代替复写getSupportedSourceVersion 和getSupportedAnnotationTypes方法
@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedAnnotationTypes({"com.ll.apt_annotation.LLOnClick","com.ll.apt_annotation.LLBindView"})
public class LLBindViewProcess extends AbstractProcessor {
    private Filer mFiler;//生成文件工具栏
    private Messager messager;//打印信息


    //元素相关
    private Elements elements;
    private Types types;
    private Map<String, ProxyInfo> proxyInfoMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        print("SIZE：" + set.size());
        print("start to process BindView：");
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith("")
        print("start to process OnClick：");

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(LLBindView.class.getCanonicalName());
        types.add(LLOnClick.class.getCanonicalName());
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    private void print(String msg){
        if (null != messager){
            messager.printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }
}