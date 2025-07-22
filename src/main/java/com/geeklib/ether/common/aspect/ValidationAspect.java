package com.geeklib.ether.common.aspect;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Deprecated
/**
 * 不再使用，推荐使用 Spring Boot 的 JSR-303/JSR-380 注解校验
 * 例如：@Validated、@Valid 等注解，推荐@Validated，可以分组校验
 */
public class ValidationAspect {

    //@Before("execution(* com.geeklib.ether..controller..*(..))")
    public void JsrProcess(JoinPoint joinPoint){
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null) {
                Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                Set<ConstraintViolation<Object>> violations = validator.validate(arg);
                if (!violations.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<Object> violation : violations) {
                        sb.append(violation.getMessage()).append("; ");
                    }
                    throw new ValidationException(sb.toString());
                }
            }
        }
    }
}
