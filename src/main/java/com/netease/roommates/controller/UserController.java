package com.netease.roommates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.roommates.po.User;
import com.netease.roommates.service.IUserInfoService;

@Controller
public class UserController {
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired  
    private RedisTemplate<String, String> redisTemplate;
	
	@RequestMapping("/hello")
	public String hello() {
		return "index";
	}

	@RequestMapping("/user/{id}")
	@ResponseBody
	public User getUserById(@PathVariable int id) {
		return userInfoService.getUserById(id);
	}

	@RequestMapping("/getUserById")
	@ResponseBody
	public User getUserById2(@RequestParam("id") int id) {
		return userInfoService.getUserById(id);
	}

	@RequestMapping("/addUser")
	public void insertUser(@RequestParam("id") int id, @RequestParam("name") String name) {
		userInfoService.insertUser(new User(id, name));
	}
	
	@RequestMapping("/addUserV2")
    public void insertUser(@RequestBody User user) {
		System.out.println(user);
		userInfoService.insertUser(user);
	}
	
	@RequestMapping("/addToRedis")
	public void addToRedis(String key, String value) {
		redisTemplate.opsForList().leftPush(key, value);
	}
	
	@RequestMapping("/getFromRedis")
	@ResponseBody
	public String getFromRedis(String key) {
		return redisTemplate.boundListOps(key).leftPop();
	}
}
