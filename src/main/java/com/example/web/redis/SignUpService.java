package com.example.web.redis;

import com.example.web.common.ClassToMapConvert;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.member.MemberSignUpDto;
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
public class SignUpService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final long EXPIRED_TIME = 5l;

    public void setData(String key, MemberSignUpDto memberSignUpDto){
        if(existData(key)) throw new RestApiException(ErrorCode.BAD_REQUEST, "이미 가입된 회원정보입니다.");
        Map<Object, Object> map = ClassToMapConvert.convert(memberSignUpDto);
        try {
            HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
            Iterator<Object> iterator = map.keySet().iterator();
            while(iterator.hasNext()){
                Object mapKey = iterator.next();
                Object mapValue = map.get(mapKey);
                stringObjectObjectHashOperations.put(key, mapKey, mapValue);
            }
            redisTemplate.expire(key, EXPIRED_TIME, TimeUnit.MINUTES);
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

    private boolean existData(String key){
        try{
            HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
            return stringObjectObjectHashOperations.hasKey(key, "email");
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
