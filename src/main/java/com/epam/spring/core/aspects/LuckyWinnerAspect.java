package com.epam.spring.core.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.epam.spring.core.model.Order;

@Aspect
@Component
public class LuckyWinnerAspect {

	
	@Pointcut("execution(* com.epam.spring.core.controller.discount.*.setDiscount(..))")
	private void countAllDiscount() {
	}

	@Around("countAllDiscount()")
	public void countGetPrice(final ProceedingJoinPoint jp) {
		if (generator()){
			for (final Object argument : jp.getArgs()) {
				if (argument.getClass().equals(Order.class)) {
					((Order)argument).setPrice(0);
					System.out.println("You are lucky price is 0");
				}
			}
		}else{
			try {
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
	}

	private boolean generator(){
	  int num = (int)(Math.random() * 20) + 1;
      return (num%4==0);
	}
	
}
