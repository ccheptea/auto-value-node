package com.ccheptea.auto.value.node;

import com.google.auto.service.AutoService;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.AutoValueExtension;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static com.google.auto.common.MoreElements.getLocalAndInheritedMethods;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * Created by constantin.cheptea
 * on 07/03/2017.
 */
@AutoService(Processor.class)
public class AutoValueNodeProcessor extends AbstractProcessor {
    AutoValueNodeExtension extension = new AutoValueNodeExtension();
    private Types typeUtils;
    private Elements elementUtils;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, TypeElement> typeElements = new HashMap<>();

        System.out.println("Processing nodes...");
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoValue.class)) {
            AutoValueExtension.Context context = new LimitedContext(processingEnv, (TypeElement) element);
            if (extension.applicable(context)) {
                TypeElement typeElement = (TypeElement) element;
                typeElements.put(typeElement.getQualifiedName().toString(), typeElement);
            }
        }

        System.out.println("Found " + typeElements.size() + " node classes");

        for (TypeElement element : typeElements.values()) {
            System.out.println("Node class => " + element);

            String packageName = packageNameOf(element);
            TypeSpec nodeClass = createNodeClass(element, packageName, typeElements);
            JavaFile file = JavaFile.builder(packageName, nodeClass).build();
            try {
                file.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(ERROR, "Failed to write Node_" + element.getSimpleName() + ": " + e.getLocalizedMessage());
            }
        }

        return false;
    }

    private TypeSpec createNodeClass(TypeElement typeElement, String packageName, Map<String, TypeElement> typeElements) {
        String autoValueClass = classNameOf(typeElement);
        TypeSpec.Builder builder = TypeSpec
                .classBuilder(ClassName.get(packageName, "Node_" + autoValueClass))
                .addModifiers(PUBLIC, FINAL)
                .superclass(ParameterizedTypeName.get(ClassName.get(Node.class), ClassName.get(typeElement)))
                .addMethod(generateConstructor(typeElement));

        ImmutableSet<ExecutableElement> properties = getProperties(typeElement);
        System.out.println("\nListing properties " + properties.size() + " for " + autoValueClass + ":");
        for (ExecutableElement property : properties) {
            Name propertyName = property.getSimpleName();

            String propertyTypeQualifiedName = property.getReturnType().toString();

            System.out.println(propertyTypeQualifiedName);

            if (typeElements.keySet().contains(propertyTypeQualifiedName)) {
                String propertyTypePackage = packageNameOf(typeElements.get(propertyTypeQualifiedName));
                String propertyTypeSimpleName = classNameOf(typeElements.get(propertyTypeQualifiedName));

                builder.addMethod(generateNodeablePropertyMethod(propertyTypePackage, propertyTypeSimpleName, propertyName.toString()));
            } else {
                builder.addMethod(generateSimplePropertyMethod(property.getReturnType(), property.getReturnType().toString(), propertyName.toString()));
            }

            System.out.println(propertyName);
        }
        return builder.build();
    }

    private MethodSpec generateNodeablePropertyMethod(String propertyTypePackage, String returnTypeSuffix, String methodName) {
        return MethodSpec.methodBuilder(methodName)
                .returns(ClassName.get(propertyTypePackage, "Node_" + returnTypeSuffix))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addStatement("return new Node_$L(value == null ? null : value.$L())", returnTypeSuffix, methodName)
                .build();
    }

    private MethodSpec generateSimplePropertyMethod(TypeMirror returnType, String returnTypeSuffix, String methodName) {
        return MethodSpec.methodBuilder(methodName)
                .returns(ParameterizedTypeName.get(ClassName.get(Node_Wrapper.class), ClassName.get(returnType)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addStatement("return new Node_Wrapper<>(value == null ? null : value.$L())", methodName)
                .build();
    }

    private ImmutableSet<ExecutableElement> getProperties(TypeElement element) {

        ImmutableSet<ExecutableElement> methods = getLocalAndInheritedMethods(element, elementUtils);
        ImmutableSet<ExecutableElement> abstractMethods = abstractMethodsIn(methods);
        ImmutableSet<ExecutableElement> propertyMethods = propertyMethodsIn(abstractMethods);

        return propertyMethods;
    }

    private static MethodSpec generateConstructor(TypeElement autoValueClass) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(autoValueClass.asType()), "value")
                .addStatement("super(value)")
                .build();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(AutoValue.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    private static String classNameOf(TypeElement type) {
        String name = type.getQualifiedName().toString();
        String pkgName = packageNameOf(type);
        return pkgName.isEmpty() ? name : name.substring(pkgName.length() + 1);
    }

    private static String packageNameOf(TypeElement type) {
        while (true) {
            Element enclosing = type.getEnclosingElement();
            if (enclosing instanceof PackageElement) {
                return ((PackageElement) enclosing).getQualifiedName().toString();
            }
            type = (TypeElement) enclosing;
        }
    }

    private ImmutableSet<ExecutableElement> abstractMethodsIn(ImmutableSet<ExecutableElement> methods) {
        Set<Name> noArgMethods = Sets.newHashSet();
        ImmutableSet.Builder<ExecutableElement> abstracts = ImmutableSet.builder();
        for (ExecutableElement method : methods) {
            if (method.getModifiers().contains(Modifier.ABSTRACT)) {
                boolean hasArgs = !method.getParameters().isEmpty();
                if (hasArgs || noArgMethods.add(method.getSimpleName())) {
                    // If an abstract method with the same signature is inherited on more than one path,
                    // we only add it once. At the moment we only do this check for no-arg methods. All
                    // methods that AutoValue will implement are either no-arg methods or equals(Object).
                    // The former is covered by this check and the latter will lead to vars.equals being
                    // set to true, regardless of how many times it appears. So the only case that is
                    // covered imperfectly here is that of a method that is inherited on more than one path
                    // and that will be consumed by an extension. We could check parameters as well, but that
                    // can be a bit tricky if any of the parameters are generic.
                    abstracts.add(method);
                }
            }
        }
        return abstracts.build();
    }

    private ImmutableSet<ExecutableElement> propertyMethodsIn(ImmutableSet<ExecutableElement> abstractMethods) {
        ImmutableSet.Builder<ExecutableElement> properties = ImmutableSet.builder();
        for (ExecutableElement method : abstractMethods) {
            if (method.getParameters().isEmpty()
                    && !method.getSimpleName().toString().equals("node")
                    && method.getReturnType().getKind() != TypeKind.VOID
//                    && objectMethodToOverride(method) == ObjectMethodToOverride.NONE
                    ) {
                properties.add(method);
            }
        }
        return properties.build();
    }

    private static ObjectMethodToOverride objectMethodToOverride(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        switch (method.getParameters().size()) {
            case 0:
                if (name.equals("toString")) {
                    return ObjectMethodToOverride.TO_STRING;
                } else if (name.equals("hashCode")) {
                    return ObjectMethodToOverride.HASH_CODE;
                }
                break;
            case 1:
                if (name.equals("equals")
                        && method.getParameters().get(0).asType().toString().equals("java.lang.Object")) {
                    return ObjectMethodToOverride.EQUALS;
                }
                break;
            default:
                // No relevant Object methods have more than one parameter.
        }
        return ObjectMethodToOverride.NONE;
    }

    private enum ObjectMethodToOverride {
        NONE, TO_STRING, EQUALS, HASH_CODE
    }

    private static class LimitedContext implements AutoValueExtension.Context {
        private final ProcessingEnvironment processingEnvironment;
        private final TypeElement autoValueClass;

        public LimitedContext(ProcessingEnvironment processingEnvironment, TypeElement autoValueClass) {
            this.processingEnvironment = processingEnvironment;
            this.autoValueClass = autoValueClass;
        }

        @Override
        public ProcessingEnvironment processingEnvironment() {
            return processingEnvironment;
        }

        @Override
        public String packageName() {
            return processingEnvironment().getElementUtils().getPackageOf(autoValueClass).getQualifiedName().toString();
        }

        @Override
        public TypeElement autoValueClass() {
            return autoValueClass;
        }

        @Override
        public Map<String, ExecutableElement> properties() {
            return null;
        }

        @Override
        public Set<ExecutableElement> abstractMethods() {
            return null;
        }
    }

}
