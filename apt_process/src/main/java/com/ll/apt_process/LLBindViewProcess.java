package com.ll.apt_process;

import com.google.auto.service.AutoService;
import com.ll.apt_annotation.LLBindView;
import com.ll.apt_annotation.LLOnClick;

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
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
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
    private Elements elementUtils;
    private Types types;
    private Map<String, ProxyInfo> proxyInfoMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        print("process start set SIZE：" + set.size());
        print("start to process BindView：");
        processBindView(roundEnvironment);
        processOnClick(roundEnvironment);
//        processWriteClass(roundEnvironment);
        return false;
    }

    private void processBindView(RoundEnvironment roundEnvironment) {
        Set<? extends Element> mLLBindViewElements = roundEnvironment.getElementsAnnotatedWith(LLBindView.class);
        print("start to processBindView：");
        if (null == mLLBindViewElements || mLLBindViewElements.size() <= 0){
            print("not element with bindView annotation");
            return;
        }
        for (Element element : mLLBindViewElements){
            if (element.getKind() != ElementKind.FIELD){
                continue;
            }
            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            String className = typeElement.getQualifiedName().toString();
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            int resourceIdParam = element.getAnnotation(LLBindView.class).value();

            ProxyInfo proxyInfo = proxyInfoMap.get(className);
            if (null == proxyInfo){
                proxyInfo = new ProxyInfo(packageName, typeElement);
                proxyInfoMap.put(className, proxyInfo);
            }
            proxyInfo.validationEventMap.put(resourceIdParam, variableElement);
        }

    }
    private void processOnClick(RoundEnvironment roundEnvironment) {
        print("start to processOnClick：");
        Set<? extends Element> mLLOnClickElements = roundEnvironment.getElementsAnnotatedWith(LLBindView.class);

        if (null == mLLOnClickElements || mLLOnClickElements.size() <= 0){
            print("not element with bindView annotation");
            return;
        }

        for (Element element : mLLOnClickElements){
            if (element.getKind() != ElementKind.FIELD){
                continue;
            }
            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            String className = typeElement.getQualifiedName().toString();
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            int[] resourceIdParam = element.getAnnotation(LLOnClick.class).value();

            ProxyInfo proxyInfo = proxyInfoMap.get(className);
            if (null == proxyInfo){
                proxyInfo = new ProxyInfo(packageName, typeElement);
                proxyInfoMap.put(className, proxyInfo);
            }
            for (int id : resourceIdParam){
                proxyInfo.validationEventMap.put(id, variableElement);
            }
        }
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