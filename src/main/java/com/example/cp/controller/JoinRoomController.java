package com.example.cp.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.cp.entity.ResponseVO;
import com.example.cp.entity.RoomResp;
import com.example.cp.entity.User;
import com.example.cp.common.tool.RedisUtil;
import com.example.cp.common.tool.StringUtils;
import com.example.cp.mapper.UserMapper;
import com.example.cp.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;


/**
 * @Description: 玩家加入游戏
 * @Author: chenping
 * @Date: 2020-05-21
 */
@Slf4j
@RestController
public class JoinRoomController extends BaseController {

    @Autowired
    MatchService service;
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserMapper userMapper;

    /** 
     * @Description:  匹配
     * @param:  
     * @return: com.alibaba.fastjson.JSONObject 
     * @Author: chenping
     * @Date: 2020/5/24
     */
    @PostMapping("/match")
    public JSONObject match() {
        try {
            JSONObject user = decryptToken(request);

            String roomId = service.match(user.getString("userId"));
            RoomResp resp = new RoomResp();
            if (!StringUtils.isEmpty(roomId)) {
                resp.setRoomId(roomId);
                resSuccessResult(resp);
            } else {
                resFailResult("无匹配数据");
            }
        } catch (Exception e) {
            log.error("匹配异常: ", e);
            resFailResult("匹配异常");
        }
        return result;
    }

    //响应加密注解
//    @encryption(responseAes = true)
    @RequestMapping("/test")
    public Object ggg() {
        ResponseVO vo = new ResponseVO();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "aaa");
        vo.setData(jsonObject);
        Set<Tuple> set =  redisUtil.zrangeWithScores("debug:game:free_room_list",0,-1);
        for (Tuple t: set) {
            System.out.println(t.getElement());
        }

//        User user = new User();
//        user.setUid(255);
//        User u = userMapper.list(user).get(0);
        List<User> u = userMapper.selectAll();
        System.out.println(u);
        //error日志文件测试
        log.error("error------------------------");
        return vo;
//        System.out.println(request.getHeader("aa"));
    }

    /** 
     * @Description:  邀请
     * @param:  
     * @return: com.alibaba.fastjson.JSONObject 
     * @Author: chenping
     * @Date: 2020/5/24
     */
    @RequestMapping("/invite")
    public JSONObject invite() {
        try {
            JSONObject user = decryptToken(request);
            String userId = user.getString("userId");
            String roomId = user.getString("roomId");

            String result = service.invite(userId, roomId);
            if (StringUtils.isEmpty(result)) {
                resSuccessResult();
            } else {

            }
        } catch (Exception e) {
            resFailResult(e.getMessage());
        }
        resSuccessResult();
        return result;
    }

}
