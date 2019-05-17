package com.me.bootSecurityJPAExample.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;


/**
 * <p>
 * A transactional basic annotation to get common test annotations of this project together.
 *
 * <p>
 * The goal of this annotation is to reduce lines which are produced by mandatory test annotations of Spring framework
 * and unify test configurations so the test classes under this annotation would never corrupt
 * test context cache, thereby having same single Spring test context
 * while the tests are conducted at once.
 *
 * <p>
 * Plus, this annotation strategy is considered more efficient to reduce lines for each test classes
 * when using nested testing feature on JUnit 5,
 * as the Spring TestContext Framework currently(~2.1.1) doesn't support annotation inheritance.
 *
 * <p>
 * Every test class that accesses to datasource is recommended to apply this annotation.
 *
 * <p>
 * This annotation is equivalent to applying following annotations:
 *
 * <ul>
 *   <li>{@code @Transactional}</li>
 *   <li>
 *     <p>{@code @BasicTestAnnotations}</p>
 *     <ul>
 *       <li>{@code @ActiveProfiles("test")}</li>
 *       <li>{@code @ExtendWith(SpringExtension.class)}</li>
 *       <li>{@code @SpringBootTest}</li>
 *     </ul>
 *   </li>
 * </ul>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional
@BasicTestAnnotations
public @interface TransactionalBasicTestAnnotations {

}
