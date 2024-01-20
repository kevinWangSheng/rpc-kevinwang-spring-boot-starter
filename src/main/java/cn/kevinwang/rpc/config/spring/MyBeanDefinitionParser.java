package cn.kevinwang.rpc.config.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author wang
 * @create 2024-01-20-17:35
 */
public class MyBeanDefinitionParser implements BeanDefinitionParser {
    private final Logger logger = LoggerFactory.getLogger(MyBeanDefinitionParser.class);

    private final Class<?> beanClass;

    public MyBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(beanClass);
        rootBeanDefinition.setLazyInit(false);
        String beanName = element.getAttribute("id");
        parserContext.getRegistry().registerBeanDefinition(beanName, rootBeanDefinition);

        for (Method method : beanClass.getMethods()) {
            if(!isProperty(method,beanClass)){
                String name = method.getName();
                String methodName = name.substring(3, 4).toLowerCase() + name.substring(4);
                String value = element.getAttribute(methodName);
                rootBeanDefinition.getPropertyValues().addPropertyValue(methodName, value);
            }
        }
        return rootBeanDefinition;
    }

    private boolean isProperty(Method method,Class<?> beanClass) {
        String name = method.getName();
        boolean flag = name.length()>3 && name.startsWith("set") && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 1;
        if(!flag){
            return false;
        }

        Class<?> parameterType = method.getParameterTypes()[0];
        Method getter;
        try {
            getter = beanClass.getMethod("get"+name.substring(3));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if(null == getter){
            try {
                getter = beanClass.getMethod("is"+name.substring(3));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return null != getter && Modifier.isPublic(getter.getModifiers()) && parameterType.equals(getter.getReturnType());
    }
}
