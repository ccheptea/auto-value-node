package com.ccheptea.auto.value.node;

import com.google.auto.value.processor.AutoValueProcessor;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Before;
import org.junit.Test;

import javax.tools.JavaFileObject;
import java.io.FileNotFoundException;
import java.util.Arrays;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

/**
 * Created by constantin.cheptea
 * on 08/03/2017.
 */
public class AutoValueNodeExtensionTest {

    AutoValueNodeProcessor autoValueNodeProcessor;
    AutoValueProcessor autoValueProcessor;

    @Before
    public void setup() {
        autoValueNodeProcessor = new AutoValueNodeProcessor();
        autoValueProcessor = new AutoValueProcessor();
    }

    @Test
    public void testFindNodeClasses() throws FileNotFoundException {
        JavaFileObject wand = JavaFileObjects.forResource("input/Wand.java");
        JavaFileObject owner = JavaFileObjects.forResource("input/Owner.java");
        JavaFileObject school = JavaFileObjects.forResource("input/School.java");

        JavaFileObject autoValueWand = JavaFileObjects.forResource("expected/AutoValue_Wand.java");
        JavaFileObject autoValueOwner = JavaFileObjects.forResource("expected/AutoValue_Owner.java");
        JavaFileObject autoValueSchool = JavaFileObjects.forResource("expected/AutoValue_School.java");

        JavaFileObject nodeWand = JavaFileObjects.forResource("expected/Node_Wand.java");
        JavaFileObject nodeOwner = JavaFileObjects.forResource("expected/Node_Owner.java");
        JavaFileObject nodeSchool = JavaFileObjects.forResource("expected/Node_School.java");
        assertAbout(javaSources())
                .that(Arrays.asList(school, owner, wand))
                .processedWith(autoValueNodeProcessor, autoValueProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(autoValueWand, autoValueOwner, autoValueSchool, nodeOwner, nodeSchool, nodeWand);
    }
}
