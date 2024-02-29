package com.example.web.redis;

import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, Map<Object, Object> map, Long expiredTime){
        try {
            HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
            Iterator<Object> iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                Object mapKey = iterator.next();
                Object mapValue = map.get(mapKey);
                stringObjectObjectHashOperations.put(key, mapKey, mapValue);
            }
            redisTemplate.expire(key, expiredTime, TimeUnit.MINUTES);
        }catch (RedisConnectionFailureException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Redis 에러가 발생하였습니다.");
        }catch (RedisConnectionException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Redis 에러가 발생하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Redis 에러가 발생하였습니다.");
        }
    }

    public Map<Object, Object> getData(String key){
        try{
            HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
            return stringObjectObjectHashOperations.entries(key);
        }catch (RedisConnectionFailureException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Redis 에러가 발생하였습니다.");
        }catch (RedisConnectionException e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Redis 에러가 발생하였습니다.");
        }catch (Exception e){
            e.printStackTrace();
            throw new RestApiException(ErrorCode.INTERNAL_SERVER_ERROR, "Redis 에러가 발생하였습니다.");
        }
    }
}
