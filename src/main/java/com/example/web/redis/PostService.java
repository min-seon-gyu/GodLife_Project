package com.example.web.redis;

import com.example.web.common.RedisRock;
import com.example.web.exception.ErrorCode;
import com.example.web.exception.RestApiException;
import com.example.web.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PostService {
    private final static Long MIN_VALUE = 0l;
    private final static Long MAX_VALUE = 5l;
    private final RedisTemplate<String, Long> redisTemplate;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    @RedisRock
    public void decreaseWriteCount(String key) {
        Long remainingWrites = getCount(key);
        if(remainingWrites == null){
            ValueOperations<String, Long> stringIntegerValueOperations = redisTemplate.opsForValue();
            LocalDate localDate = LocalDate.parse(key.substring(0, key.indexOf("_")));
            String username = key.substring(key.indexOf("_") + 1);
            Long count = scheduleRepository.countByUsernameAndLocalDate(username, localDate);
            remainingWrites = MAX_VALUE - count;
            stringIntegerValueOperations.set(key, remainingWrites);
            redisTemplate.expire(key, 60, TimeUnit.MINUTES);
        }

        if (remainingWrites != null && remainingWrites > MIN_VALUE) {
            redisTemplate.opsForValue().decrement(key);
        }

        if(remainingWrites < 1) throw new RestApiException(ErrorCode.BAD_REQUEST, "최대 일정을 작성하였습니다.");
    }

    @Transactional
    @RedisRock
    public void incrementWriteCount(String key) {
        Long remainingWrites = getCount(key);
        if (remainingWrites != null && remainingWrites < MAX_VALUE) {
            redisTemplate.opsForValue().increment(key);
        }
    }

    @Transactional
    @RedisRock
    public Long getCount(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
