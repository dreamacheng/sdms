package com.pro.sdms.DAOTest;

import com.pro.it.SdmsApplication;
import com.pro.it.sdms.dao.AccountDAO;
import com.pro.it.sdms.entity.dto.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SdmsApplication.class)
@Slf4j
public class CacheTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void testString() {
        String key = "name";
        String default_value = "master";
        log.info("======>   redisTemplate : [{}]", redisTemplate);
        if (redisTemplate.hasKey(key)) {
            log.info("======> 缓存命中");
            String value = (String) redisTemplate.opsForValue().get(key);
            log.info("======> value : [{}]", value);
        } else {
            log.info("======> 缓存未命中");
            redisTemplate.opsForValue().set(key, default_value);
            log.info("======> 设置 key ：[{}], value : [{}]", key, default_value);
        }
    }

    @Test
    public void testObj() {
        String accountNo = "201513071111";
        log.info("======>   redisTemplate : [{}]", redisTemplate);
        if (redisTemplate.opsForHash().hasKey("account", accountNo)) {
            log.info("======> 缓存命中");
            Account account_cache = (Account) redisTemplate.opsForHash().get("account", accountNo);
            log.info("======> obj : [{}]", account_cache);
        } else {
            log.info("======> 缓存未命中");
            Account accountByAccountNo = accountDAO.getAccountByAccountNo(accountNo);
            if (accountByAccountNo != null) {
                redisTemplate.opsForHash().put("account", accountByAccountNo.getAccountNo(), accountByAccountNo);
                log.info("======> 设置 key ：[{}], value : [{}]", "account:" + accountByAccountNo.getAccountNo(), accountByAccountNo);
            } else {
                log.info("======> 用户不存在");
            }
        }
    }
}
