package ua.com.foxminded.charcounter.model;

public class InputCacheFactory implements CacheFactory {
    
    private static InputCacheFactory cacheFactory;
    
    private InputCacheFactory() {}
    
    public static InputCacheFactory getInstance() {
        if (cacheFactory == null) {
            cacheFactory = new InputCacheFactory();
        }
        return cacheFactory;
    }
    
    public InputCache getCacheInstance() {
        return new InputCache();
    }
}
