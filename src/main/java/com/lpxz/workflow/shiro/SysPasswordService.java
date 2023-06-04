package com.lpxz.workflow.shiro;

import com.lpxz.workflow.common.BaseException;
import com.lpxz.workflow.domain.SysUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LPxz
 * @date 2023/5/29
 */
@Service
public class SysPasswordService {

//    @Autowired
//    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

//    @Value(value = "${user.password.maxRetryCount}")
//    private String maxRetryCount;

//    @PostConstruct
//    public void init() {
//        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGIN_RECORD_CACHE);
//    }

    public void validate(SysUser user, String password) {
        String account = user.getUserAccount();
//        AtomicInteger retryCount = loginRecordCache.get(account);
////
//        if (retryCount == null) {
//            retryCount = new AtomicInteger(0);
//            loginRecordCache.put(account, retryCount);
//        }
//        if (retryCount.incrementAndGet() > Integer.parseInt(maxRetryCount)) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
//            throw new Exception(Integer.parseInt(maxRetryCount));
//        }

        if (!matches(user, password)) {
//            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
//            loginRecordCache.put(account, retryCount);
            throw new BaseException();
        } else {
//            clearLoginRecordCache(account);
        }
    }

    public boolean matches(SysUser user, String password) {
        return user.getUserPassword().equals(encryptPassword(user.getUserAccount(), password, user.getSalt()));
    }

    public void clearLoginRecordCache(String userAccount) {
        loginRecordCache.remove(userAccount);
    }

    public String encryptPassword(String userAccount, String password, String salt) {
        return new Md5Hash(userAccount + password + salt).toHex();
    }

    public void unlock(String loginName) {
        loginRecordCache.remove(loginName);
    }
}
