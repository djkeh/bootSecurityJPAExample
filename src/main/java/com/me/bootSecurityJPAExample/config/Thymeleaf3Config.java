package com.me.bootSecurityJPAExample.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;


/**
 * <p>
 * Custom {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration} for Thymeleaf 3.
 *
 * @author Uno Kim
 */
@Configuration
@EnableConfigurationProperties(Thymeleaf3Config.Thymeleaf3Properties.class)
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class Thymeleaf3Config implements ApplicationContextAware {

    private ApplicationContext applicationContext = null;

    @Autowired
    private Thymeleaf3Properties properties;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setTemplateEngine(thymeleafTemplateEngine());
        resolver.setCharacterEncoding("UTF-8");

        return resolver;
    }

    @Bean
    public ISpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();

        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(thymeleafTemplateResolver());

        return engine;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix(this.properties.getPrefix());
        resolver.setSuffix(this.properties.getSuffix());
        resolver.setTemplateMode(this.properties.getMode());
        resolver.setUseDecoupledLogic(this.properties.isDecoupledLogic());
        if (this.properties.getEncoding() != null) {
            resolver.setCharacterEncoding(this.properties.getEncoding().name());
        }
        resolver.setCacheable(this.properties.isCache());
        Integer order = this.properties.getTemplateResolverOrder();
        if (order != null) {
            resolver.setOrder(order);
        }

        return resolver;
    }

    /**
     * <p>
     * Properties for Thymeleaf 3.
     * </p>
     *
     * @see org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
     * @author Uno Kim
     */
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public class Thymeleaf3Properties extends ThymeleafProperties {

        /**
         * Template mode to be applied to templates. Default value is {@code HTML} from
         * Thymeleaf 3.
         */
        private String mode = TemplateMode.HTML.name();

        /**
         * Enable decoupling template logic. Default value is {@code false}.
         */
        private boolean decoupledLogic = false;

        @Override
        public String getMode() {
            return mode;
        }

        @Override
        public void setMode(String mode) {
            this.mode = mode;
        }

        public boolean isDecoupledLogic() {
            return decoupledLogic;
        }

        public void setDecoupledLogic(boolean decoupledLogic) {
            this.decoupledLogic = decoupledLogic;
        }

    }

}
