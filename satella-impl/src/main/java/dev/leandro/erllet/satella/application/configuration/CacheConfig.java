package dev.leandro.erllet.satella.application.configuration;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

 	@Bean
    public CacheManager concurrentMapCacheManager() {
        return new ConcurrentMapCacheManager();
    }
	
 	@Bean
 	public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
 	    return cacheManager -> cacheManager.setAllowNullValues(false);
 	}
}
