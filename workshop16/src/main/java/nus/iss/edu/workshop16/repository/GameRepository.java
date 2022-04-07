package nus.iss.edu.workshop16.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class GameRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(JsonObject v) {
        Integer gid = v.getInt("gid");
        redisTemplate.opsForHash().put("gamesMap", "%d".formatted(gid), v.toString());
    }
    
}
