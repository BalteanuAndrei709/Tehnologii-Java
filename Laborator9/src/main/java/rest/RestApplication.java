package rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@DeclareRoles({"admin", "teacher", "student"})
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(EvaluationResource.class);
        classes.add(filters.CacheFilter.class); // Register the cache filter
        return classes;
    }
}
