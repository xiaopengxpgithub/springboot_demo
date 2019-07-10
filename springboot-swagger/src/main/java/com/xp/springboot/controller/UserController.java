package com.xp.springboot.controller;

import com.xp.springboot.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户管理")
@RestController   //所有的方法都返回controller
@RequestMapping(value = "/user")
public class UserController {

    @ApiOperation(value = "根据ID查询用户信息")
    @GetMapping(value = "/getUserById")
    public Map<String, Object> getUserById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //执行查询操作
            User user = new User();
            user.setId(id);
            user.setUserName("模拟数据");
            user.setGender("男");
            user.setAddress("北京市昌平区xxx街xx号");
            user.setAge(27);

            map.put("data", user);
            map.put("msg", "查询成功!");
            map.put("code", "200");
        } catch (Exception ex) {
            ex.printStackTrace();

            map.put("data", null);
            map.put("msg", "查询失败!");
            map.put("code", "500");
        }

        return map;
    }

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/addUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("data", user);
            map.put("msg", "添加成功!");
            map.put("code", "200");
        } catch (Exception ex) {
            ex.printStackTrace();

            map.put("data", null);
            map.put("msg", "添加失败!");
            map.put("code", "500");
        }

        return map;
    }

    @ApiOperation(value = "根据ID删除用户")
    @DeleteMapping(value = "/deleteUser")
    public Map<String, Object> deleteUser(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            User user = new User();
            user.setId(id);
            user.setUserName("模拟数据");
            user.setGender("男");
            user.setAddress("北京市昌平区xxx街xx号");
            user.setAge(27);

            map.put("data", user);
            map.put("msg", "删除成功!");
            map.put("code", "200");
        } catch (Exception ex) {
            ex.printStackTrace();

            map.put("data", null);
            map.put("msg", "删除失败!");
            map.put("code", "500");
        }

        return map;
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/updateUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("data", user);
            map.put("msg", "修改成功!");
            map.put("code", "200");
        } catch (Exception ex) {
            ex.printStackTrace();

            map.put("data", null);
            map.put("msg", "修改失败!");
            map.put("code", "500");
        }

        return map;
    }
}
