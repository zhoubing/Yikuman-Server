package com.example.springboot.demo.controller;

import com.example.springboot.demo.vo.Article;
import com.example.springboot.demo.vo.Version;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class HelloWorldController {

    private final
    MongoTemplate mongoTemplate;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String imgUrlHost = "http://192.168.0.115:8999/images/";

    @Autowired
    public HelloWorldController(MongoTemplate mongoTemplate, RedisTemplate<String, String> redisTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping("/main/{page}/{num}")
    public List<Article> hello(@PathVariable("page")Integer page, @PathVariable("num") Integer number) {
        redisTemplate.opsForValue().set("videoKey", "#@%^@%63273672");

        Query query = new Query();
        query.skip(page * number).limit(number);
        List<Article> articles = mongoTemplate.find(query, Article.class);
        for (Article article : articles) {
            String articleCover = article.getCover();
            if (articleCover == null) {
                continue;
            }
            int index = articleCover.lastIndexOf("/");
            String filename = articleCover.substring(index);
            article.setCoverUrl(imgUrlHost + article.getDate() + "/" + article.getIndex() + filename);
        }
        return articles;
    }
}
