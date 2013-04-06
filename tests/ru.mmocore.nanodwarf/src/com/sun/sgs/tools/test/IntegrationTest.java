/*LICENSE*/

package com.sun.sgs.tools.test;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

/**
 * Annotation type used to indicate that a test method or test class is an
 * integration level test. Note that if an annotation is specified on both a
 * method and that method's class, the one at the method level takes priority.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface IntegrationTest {

	/**
	 * Indicates which phase of testing a test should be run in.
	 */
	TestPhase value() default TestPhase.LONG;
}
