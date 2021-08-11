package com.coderlong.bigdata.springboot.aop.aop;

import com.coderlong.bigdata.springboot.aop.cache.CacheModel;
import javassist.ClassPool;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;

@Aspect
@Component
@Log4j2
public class CacheAspect {
    private ClassPool pool = ClassPool.getDefault();
    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.coderlong.bigdata.springboot.aop.controller.AopController.*(..)))")
    public void CacheAspect(){

    }

    @Around(value = "CacheAspect()")
    public Object cacheHandler(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        CacheModel cacheModel = new CacheModel();



        //String key =


        log.info("PathInfo={}", request.getPathInfo());
        log.info("PathTranslated={}", request.getPathTranslated());
        log.info("ContextPath={}", request.getContextPath());
        log.info("ServletPath={}", request.getServletPath());
        log.info("QueryString={}", request.getQueryString());
        log.info("bestMatchingPattern={}", request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"));


        log.info("uri={}", request.getRequestURI());
        //日志打印：url
        log.info("url={}", request.getRequestURL());

        //日志打印：method
        log.info("method={}", request.getMethod());

        //日志打印：类方法
        log.info("class_method={}", point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());

        //日志打印：参数
        log.info("args={}", point.getArgs());
        return point.proceed();
    }



//    private Object getFromCache(Method method, String objType, String id) {
//        if (method.getReturnType().equals(String.class)) {
//            return cacheService.getString(objType, id);
//        } else if (method.getReturnType().equals(byte[].class)) {
//            return cacheService.getData(objType, id);
//        } else {
//            try{
//                String json = cacheService.getString(objType,id);
//                return JSON.parseObject(json, method.getReturnType());
//            }catch(Exception ex){
//                return cacheService.getObject(method.getReturnType(),objType,id);
//            }
//        }
//    }

    private String getMethodParamValue(CtMethod cm, String name, Object[] args) {
        MethodInfo methodInfo = cm.getMethodInfo();
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        for (int i = 0; i < args.length; i++) {
            int index = i + pos;
            if (attr.variableName(index).equals(name)) {
                return args[i] == null ? null : args[i] + "";
            }
        }
        return "";
    }


}
