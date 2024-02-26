# Limit
## 介绍
如果需要对项目中某一个具体的接口进行限流并且不同的限流方式以及拓展性来说，该项目是你可以参考的点。
  
主体是用一个Interface定义所有方法，abstractClass implement Interface并且给可选方法默认实现，这样抽象类的子类强制实现Interface剩余的方法了。  
自定义注解放在需要限流的方法上，AOP拦截注解传过来的值封装成DTO，然后handler装配具体实现类，并调用处理逻辑。


## 使用

- 创建具体限流bean
```java
    @Bean
    public LimiterHandler limiterHandler(){
//        return new LimiterHandler(CounterByUserLimiter.getInstance()); 
//        return new LimiterHandler(new CounterLimiter()); 构造器中需要引入redis
//        return new LimiterHandler(new FunnelLimiter());
//        return new LimiterHandler(new SlideWindowsLimiter());  构造器中需要引入redis
//        return new LimiterHandler(TokenBucketLimiter.getInstance());
    }
```


- 在统一异常拦截中拦截
```java
    @ExceptionHandler(InterceptException.class)
    public R doInterceptException(InterceptException e){
        e.printStackTrace();
        return R.error().message(e.getMsg());
    }
```


