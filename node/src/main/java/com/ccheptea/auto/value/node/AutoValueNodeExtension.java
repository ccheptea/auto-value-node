package com.ccheptea.auto.value.node;

import com.google.auto.service.AutoService;
import com.google.auto.value.extension.AutoValueExtension;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.*;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by constantin.cheptea
 * on 07/03/2017.
 */
@AutoService(AutoValueExtension.class)
public class AutoValueNodeExtension extends AutoValueExtension {

    @Override
    public boolean mustBeFinal(Context context) {
        return false;
    }

    @Override
    public Set<ExecutableElement> consumeMethods(Context context) {
        ImmutableSet.Builder<ExecutableElement> methods = new ImmutableSet.Builder<>();
        for (ExecutableElement element : context.abstractMethods()) {
            switch (element.getSimpleName().toString()) {
                case "node":
                    methods.add(element);
                    break;
            }
        }
        return methods.build();
    }

    @Override
    public boolean applicable(Context context) {
        TypeElement type = context.autoValueClass();

        for (ExecutableElement method : ElementFilter.methodsIn(type.getEnclosedElements())) {
            TypeMirror rType = method.getReturnType();
            TypeName returnType = TypeName.get(rType);

            if (returnType.toString().equals("Node_" + type.getSimpleName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String generateClass(Context context, String className, String classToExtend, boolean isFinal) {
        String packageName = context.packageName();
        Name superName = context.autoValueClass().getSimpleName();
        Map<String, ExecutableElement> properties = context.properties();
        TypeSpec subclass = TypeSpec.classBuilder(className)
                .addModifiers(isFinal ? Modifier.FINAL : Modifier.ABSTRACT)
                .superclass(ClassName.get(packageName, classToExtend))
                .addMethod(generateConstructor(properties))
                .addMethod(generateNodeMethod(superName, context.packageName()))
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, subclass).build();
        return javaFile.toString();
    }

    private static MethodSpec generateConstructor(Map<String, ExecutableElement> properties) {
        List<ParameterSpec> params = new ArrayList<>();
        for (Map.Entry<String, ExecutableElement> entry : properties.entrySet()) {
            TypeName typeName = TypeName.get(entry.getValue().getReturnType());
            params.add(ParameterSpec.builder(typeName, entry.getKey()).build());
        }

        StringBuilder body = new StringBuilder("super(");
        for (int i = properties.size(); i > 0; i--) {
            body.append("$N");
            if (i > 1) body.append(", ");
        }
        body.append(")");

        return MethodSpec.constructorBuilder()
                .addParameters(params)
                .addStatement(body.toString(), properties.keySet().toArray())
                .build();
    }

    private MethodSpec generateNodeMethod(Name superName, String packageName) {
        return MethodSpec.methodBuilder("node") //
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL) //
                .returns(ClassName.get(packageName, "Node_" + superName))
                .addCode("return new Node_$L(this);\n", superName)
                .build();
    }
}
