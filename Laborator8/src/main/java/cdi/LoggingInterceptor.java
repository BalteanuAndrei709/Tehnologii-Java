package cdi;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Logged
public class LoggingInterceptor {
    @AroundInvoke
    public Object logMethod(InvocationContext ctx) throws Exception {
        System.out.println("Executing: " + ctx.getMethod().getName());
        return ctx.proceed();
    }
}
