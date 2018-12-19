package com.me.bootSecurityJPAExample.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;


/**
 * <p>
 * A basic annotation to get common test annotations of this project together.
 *
 * <p>
 * The goal of this annotation is to reduce lines which are produced by mandatory test annotations of Spring framework
 * and unify test configurations so the test classes under this annotation would never corrupt
 * test context cache, thereby having same single Spring test context
 * while the tests are conducted at once.
 *
 * <p>
 * This annotation is equivalent to applying following annotations:
 *
 * <ul>
 *   <li>{@code @ActiveProfiles("test")}</li>
 *   <li>{@code @ExtendWith(SpringExtension.class)}</li>
 *   <li>{@code @SpringBootTest}</li>
 * </ul>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public @interface BasicTestAnnotations {

}
