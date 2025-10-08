package com.JKSv2.Controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.JKSv2.Model.UserRedis;
import com.anil.square.Repository.UserRedisRepository;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserRedisRepository userRedisRepository;

    // ✅ Get all keys matching pattern (e.g., UserRedis:*)
    @GetMapping("/keys")
    public ResponseEntity<Set<String>> keys(@RequestParam(defaultValue = "UserRedis:*") String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return ResponseEntity.ok(keys);
    }

    // ✅ Get full hash for a user (UserRedis:<id>)
    @GetMapping("/hash")
    public ResponseEntity<Map<Object, Object>> getHash(@RequestParam String key) {
        if (!redisTemplate.hasKey(key)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Key not found: " + key));
        }
        Map<Object, Object> hash = redisTemplate.opsForHash().entries(key);
        return ResponseEntity.ok(hash);
    }

    // ✅ Update or add a field in user hash
    @PostMapping("/hash")
    public ResponseEntity<String> updateHashField(
            @RequestParam String key,
            @RequestParam String field,
            @RequestParam String value) {
        redisTemplate.opsForHash().put(key, field, value);
        return ResponseEntity.ok("Field updated");
    }

    // ✅ Get count of keys matching a pattern (e.g., UserRedis:*)
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getKeyCount(
            @RequestParam(defaultValue = "UserRedis:*") String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        int count = (keys != null) ? keys.size() : 0;
        return ResponseEntity.ok(Map.of(
                "pattern", pattern,
                "count", count));
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getPaginatedRedisRecords(
            @RequestParam(defaultValue = "MasterDataViewRedis:*") String pattern,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        Set<String> allKeys = redisTemplate.keys(pattern);
        if (allKeys == null || allKeys.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "total", 0,
                    "data", List.of()));
        }

        // Sort keys (optional but often useful)
        List<String> sortedKeys = allKeys.stream().sorted().toList();

        // Apply pagination
        int end = Math.min(offset + limit, sortedKeys.size());
        List<String> paginatedKeys = sortedKeys.subList(offset, end);

        // Fetch full data for each key
        List<Map<Object, Object>> records = paginatedKeys.stream()
                .map(key -> redisTemplate.opsForHash().entries(key))
                .toList();

        return ResponseEntity.ok(Map.of(
                "total", sortedKeys.size(),
                "offset", offset,
                "limit", limit,
                "data", records));
    }

    // ===== CRUD using Spring Data Redis Repository (UserRedis) =====

    // Create or Update a user (upsert)
    @PostMapping("/user")
    public ResponseEntity<?> upsertUser(@RequestBody UserRedis user) {
        try {
            if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username is required"));
            }
            UserRedis saved = userRedisRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to save user: " + e.getMessage()));
        }
    }

    // Read a user by username
    @GetMapping("/user")
    public ResponseEntity<UserRedis> getUser(@RequestParam String username) {
        return userRedisRepository.findById(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // List all users (careful on big datasets)
    @GetMapping("/users")
    public ResponseEntity<List<UserRedis>> listUsers() {
        Iterable<UserRedis> all = userRedisRepository.findAll();
        List<UserRedis> result = new ArrayList<>();
        all.forEach(result::add);
        return ResponseEntity.ok(result);
    }

    // Delete a user by username
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@RequestParam String username) {
        if (!userRedisRepository.existsById(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userRedisRepository.deleteById(username);
        return ResponseEntity.noContent().build();
    }

    // ✅ Delete a user hash (entire record)
    @DeleteMapping("/del")
    public ResponseEntity<Boolean> delete(@RequestParam String key) {
        Boolean result = redisTemplate.delete(key);
        return ResponseEntity.ok(result);
    }

    // ✅ Set TTL on a key
    @PostMapping("/expire")
    public ResponseEntity<Boolean> expire(@RequestParam String key, @RequestParam long seconds) {
        Boolean result = redisTemplate.expire(key, Duration.ofSeconds(seconds));
        return ResponseEntity.ok(result);
    }

    // ✅ Get TTL for a key
    @GetMapping("/ttl")
    public ResponseEntity<Long> getTTL(@RequestParam String key) {
        Long ttl = redisTemplate.getExpire(key);
        return ResponseEntity.ok(ttl);
    }

    // ✅ Get type of a key (should return "hash")
    @GetMapping("/type")
    public ResponseEntity<String> getType(@RequestParam String key) {
        return ResponseEntity.ok(redisTemplate.type(key).code());
    }

    // ✅ Optional: Run low-level Redis commands (internal use only)
    @PostMapping("/command")
    public ResponseEntity<Object> runCommand(@RequestBody Map<String, Object> request) {
        String command = (String) request.get("command");
        @SuppressWarnings("unchecked")
        List<String> args = (List<String>) request.get("args");

        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            byte[][] byteArgs = args.stream()
                    .map(String::getBytes)
                    .toArray(byte[][]::new);
            Object result = connection.execute(command, byteArgs);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } finally {
            connection.close();
        }
    }
}