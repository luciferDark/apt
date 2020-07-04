package com.ll.apt_process;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * @Auther kylin
 * @Data 2020/6/30 - 22:58
 * @Package com.ll.apt_process
 * @Description
 */
public class ProxyInfo {
    private static final String PORXY_INDEX = "_ViewBinding";
    public Map<Integer, VariableElement> validationEventMap_BindView = new HashMap<>();
    public Map<Integer, VariableElement> validationEventMap_OnClick = new HashMap<>();
    public String proxyClassName;
    public String proxyClassNameNew;
    public String proxyClassPackageName;
    private TypeElement typeElement;

    public ProxyInfo(String proxyClassPackageName, TypeElement typeElement) {
        this.proxyClassPackageName = proxyClassPackageName;
        this.typeElement = typeElement;
        this.proxyClassName = getClassName(proxyClassPackageName, typeElement);
        this.proxyClassNameNew = this.proxyClassName+ PORXY_INDEX;
    }


    private String getClassName(String proxyClassPackageName, TypeElement typeElement) {
        String fullClassNameWithPackage = typeElement.getQualifiedName().toString();
        int packageNameLen = proxyClassPackageName.length() + 1;
        return fullClassNameWithPackage.substring(packageNameLen)
                .replace(".", "$");
    }

    //    public void inject(MainActivity target, Object source) {
//        if (source instanceof android.app.Activity) {
//            target.textView2 = ((android.app.Activity) source).findViewById(2131230920);
//        } else {
//            target.textView2 = ((android.view.View) source).findViewById(2131230920);
//        }
//    }
    public TypeSpec generateProxyClass() {
        TypeName typeName = TypeName.get(typeElement.asType());
        ClassName className = ClassName.get(proxyClassPackageName, proxyClassName);
        //创建方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)//添加复写方法注解
                .addParameter(typeName, "target", Modifier.FINAL)
                .addParameter(ClassName.get("java.lang", "Object"),
                        "source", Modifier.FINAL);
        for (int id : validationEventMap_BindView.keySet()) {
            VariableElement element = validationEventMap_BindView.get(id);
            String fieldName = element.getSimpleName().toString();
            methodBuilder.addStatement(" if(source instanceof android.app.Activity){\n" +
                            "\ttarget.$L = ((android.app.Activity) source).findViewById( $L); " +
                            "} else {\n" +
                            "\ttarget.$L = ((android.view.View) source).findViewById( $L);\n" +
                            "}",
                    fieldName, id,
                    fieldName, id);
        }

        for (int id : validationEventMap_OnClick.keySet()) {
            VariableElement element = validationEventMap_BindView.get(id);
            String fieldName = element.getSimpleName().toString();

            methodBuilder.addStatement(" if(source instanceof android.app.Activity){\n" +
                    "\ttarget.$L = ((android.app.Activity) source).findViewById( $L);\n " +
                    "} else {\n" +
                    "\ttarget.$L = ((android.view.View) source).findViewById( $L);\n" +
                    "}");
        }

        MethodSpec bindMethodSpec = methodBuilder.build();

        ClassName viewBindInterface = ClassName.get(
                "com.ll.apt_library",
                "IViewBind");
        // implement IViewBind<T>
        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(viewBindInterface, className);

        TypeSpec typeSpec = TypeSpec.classBuilder(proxyClassNameNew)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(parameterizedTypeName)
                .addMethod(bindMethodSpec)
                .build();

        return typeSpec;
    }
}
