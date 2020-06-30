package com.ll.apt_process;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.xml.bind.ValidationEvent;

/**
 * @Auther kylin
 * @Data 2020/6/30 - 22:58
 * @Package com.ll.apt_process
 * @Description
 */
public class ProxyInfo {
    private static final String PORXY_INDEX = "_ViewBindind";
//用于保存该类中所有被注解的View变量
public Map<Integer, VariableElement> validationEventMap = new HashMap<>();
    public String proxyClassName;
    public String proxyClassPackageName;
    private TypeElement typeElement;

    public ProxyInfo(String proxyClassPackageName, TypeElement typeElement) {
        this.proxyClassPackageName = proxyClassPackageName;
        this.typeElement = typeElement;
        this.proxyClassName = getClassName(proxyClassPackageName,typeElement) + PORXY_INDEX;
    }
    /**
     * 获取生成的代理类的类名
     * 之所以用字符串截取、替换而没用clas.getSimpleName()的原因是为了处理内部类注解的情况，比如adapter.ViewHolder
     * 内部类反射之后的类名：例如MyAdapter$ContentViewHolder，中间是$，而不是.
     */
    private String getClassName(String proxyClassPackageName, TypeElement typeElement) {
        String fullClassNameWithPackage = typeElement.getQualifiedName().toString();
        int packageNameLen = proxyClassPackageName.length() + 1;
        return fullClassNameWithPackage.substring(packageNameLen)
                .replace(".","$");
    }

    public TypeSpec generateProxyClass(){
        TypeName typeName = TypeName.get(typeElement.asType());
        ClassName className = ClassName.get(proxyClassPackageName,proxyClassName);

        MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(typeName,"target", Modifier.FINAL)
                .addParameter(ClassName.get("android.view", "View"),
                        "source", Modifier.FINAL);



        return null;
    }
}
