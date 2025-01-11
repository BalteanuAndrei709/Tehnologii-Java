package filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Provider
public class CacheFilter implements ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(CacheFilter.class.getName());
    private static final Map<String, Object> responseCache = new HashMap<>();

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String cacheKey = createCacheKey(requestContext);

        // Log cache activity
        if (responseCache.containsKey(cacheKey)) {
            LOGGER.info("Cache hit for key: " + cacheKey);
            responseContext.setEntity(responseCache.get(cacheKey));
        } else {
            LOGGER.info("Cache miss for key: " + cacheKey);
            if (responseContext.getStatus() == 200) {
                responseCache.put(cacheKey, responseContext.getEntity());
                LOGGER.info("Response cached for key: " + cacheKey);
            }
        }
    }

    private String createCacheKey(ContainerRequestContext requestContext) {
        return requestContext.getUriInfo().getRequestUri().toString();
    }

    public static void resetCache() {
        responseCache.clear();
        LOGGER.info("Cache cleared");
    }
}
