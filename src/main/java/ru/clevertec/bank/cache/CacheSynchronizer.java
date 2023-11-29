package ru.clevertec.bank.cache;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ru.clevertec.bank.cache.factory.CacheFactoryImpl;
import ru.clevertec.bank.entity.User;

@Aspect
public class CacheSynchronizer {

    private final Cache<UUID, User> cache;

    public CacheSynchronizer() {
        this.cache = new CacheFactoryImpl<UUID, User>().createCache();
    }

    @Around("execution(* ru.clevertec.bank.dao.UserDao.getUserById(java.util.UUID)) && args(id)")
    public Object syncGetUserById(ProceedingJoinPoint joinPoint, UUID id) throws Throwable {
        User user = cache.get(id);
        if (Objects.nonNull(user)) {
            return Optional.of(user);
        }
        Optional<User> result = (Optional<User>) joinPoint.proceed();
        result.ifPresent(u -> cache.put(u.getId(), u));
        return result;
    }

    @Around("execution(* ru.clevertec.bank.dao.UserDao.createUser(ru.clevertec.bank.entity.User)) && args(user)")
    public Object syncCreateUser(ProceedingJoinPoint joinPoint, User user) throws Throwable {
        UUID userId = (UUID) joinPoint.proceed();
        user.setId(userId);
        cache.put(userId, user);
        return userId;
    }

    @Around("execution(* ru.clevertec.bank.dao.UserDao.updateUser(ru.clevertec.bank.entity.User)) && args(user)")
    public void syncUpdateUser(ProceedingJoinPoint joinPoint, User user) throws Throwable {
        joinPoint.proceed();
        cache.put(user.getId(), user);
    }

    @Around("execution(* ru.clevertec.bank.dao.UserDao.deleteUser(java.util.UUID)) && args(id)")
    public void syncDeleteUser(ProceedingJoinPoint joinPoint, UUID id) throws Throwable {
        joinPoint.proceed();
        User user = cache.get(id);
        if (Objects.nonNull(user)) {
            cache.remove(user.getId());
        }
    }

}
