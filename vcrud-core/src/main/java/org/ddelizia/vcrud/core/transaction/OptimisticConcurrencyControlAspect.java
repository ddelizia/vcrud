package org.ddelizia.vcrud.core.transaction;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author ddelizia
 * @since 09/03/14 13:34
 */

@Aspect
public class OptimisticConcurrencyControlAspect {

	private static final Logger LOGGER = Logger.getLogger(OptimisticConcurrencyControlAspect.class);

	@Around("@annotation(org.ddelizia.vcrud.core.transaction.Retry)")
	public Object retry(ProceedingJoinPoint pjp) throws Throwable {
		Retry retryAnnotation = getRetryAnnotation(pjp);
		return (retryAnnotation != null) ? proceed(pjp, retryAnnotation) : proceed(pjp);
	}

	private Retry getRetryAnnotation(ProceedingJoinPoint pjp) throws NoSuchMethodException {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Retry retry = AnnotationUtils.findAnnotation(method, Retry.class);
		return retry;
	}

	private Object proceed(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}

	private Object proceed(ProceedingJoinPoint pjp, Retry retryAnnotation) throws Throwable {
		int times = retryAnnotation.times();
		Class<? extends Throwable>[] retryOn = retryAnnotation.on();
		Assert.isTrue(times > 0, "@Retry{times} should be greater than 0!");
		Assert.isTrue(retryOn.length > 0, "@Retry{on} should have at least one Throwable!");
		LOGGER.info("Proceed with "+times+" retries on "+ Arrays.toString(retryOn));
		return tryProceeding(pjp, times, retryOn);
	}


	private Object tryProceeding(ProceedingJoinPoint pjp, int times, Class<? extends Throwable>[] retryOn) throws Throwable {
		try {
			return proceed(pjp);
		} catch (Throwable throwable) {
			if(isRetryThrowable(throwable, retryOn) && times-- > 0) {
				LOGGER.info("Optimistic locking detected, "+times+" remaining retries on "+ Arrays.toString(retryOn));
				tryProceeding(pjp, times, retryOn);
			}
			throw throwable;
		}
	}

	private boolean isRetryThrowable(Throwable throwable, Class<? extends Throwable>[] retryOn) {
		Throwable[] causes = ExceptionUtils.getThrowables(throwable);
		for(Throwable cause : causes) {
			for(Class<? extends Throwable> retryThrowable : retryOn) {
				if(retryThrowable.isAssignableFrom(cause.getClass())) {
					return true;
				}
			}
		}
		return false;
	}
}
