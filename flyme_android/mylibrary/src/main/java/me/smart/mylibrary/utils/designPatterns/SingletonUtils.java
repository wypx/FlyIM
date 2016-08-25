package me.smart.mylibrary.utils.designPatterns;

/**
* @ClassName: SingletonUtils 
* @Description: TODO(获取单例模式--通用类) 
* @author amor smarting.me
* @date 2015-11-5 下午10:58:33 
* 
* @param <T> 
*/ 
public abstract class SingletonUtils<T> 
{

    private T instance;

    protected abstract T newInstance();

    public final T getInstance() 
    {
        if (null == instance) 
        {
            synchronized (SingletonUtils.class) 
            {
                if (null == instance) 
                {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
