package org.example2;

import com.fastcampus.innercircle.datamasking.DataMasker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MaskingAspect {

    @Around("@annotation(ApplyMasking)")
    public Object maskReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();
        DataMasker.mask(returnValue);
        return returnValue;
    }
}
