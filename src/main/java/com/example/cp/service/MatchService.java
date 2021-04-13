package com.example.cp.service;

import com.example.cp.entity.Room;
import com.example.cp.common.config.YmlPropers;
import com.example.cp.common.exception.CustomException;
import com.example.cp.common.tool.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * @Description: 匹配业务类
 * @Author: chenping
 * @Date: 2020-05-21
 */
@Slf4j
@Service
public class MatchService {

    @Autowired
    YmlPropers propers;

    @Autowired
    RedisUtil redisUtil;

    public String match(String id) {
        try {
            String matchKey = propers.matchKey;

//            Set<String> roomList = redisUtil.zrangebyscore(redisKey, "7", "-1");//降序列表  roomId 列表
            Set<Tuple> roomList =  redisUtil.zrangeWithScores(matchKey,0,-1);

            if (null == roomList) {
                throw new CustomException("没有找到队列");
            }

            List<Room> list = new ArrayList<>();

            for (Tuple tuple : roomList) {
                Room room = new Room();
                room.setRoomId(tuple.getElement());
                Integer rankScore = transfromData(new Double(tuple.getScore()).intValue());
                room.setScore(rankScore);

                list.add(room);
            }

            //根据score排序
            Collections.sort(list, new Comparator<Room>() {

                @Override
                public int compare(Room r1, Room r2) {
                    return r1.getScore().compareTo(r2.getScore());//按number字段降序，若是r1在前为升序
                }
            });

            Room resRoom = list.get(0);

            //发布消息
            String channels = "debug:game:room_sub_" + resRoom.getRoomId();
            String message = "match_" + id;
            log.info("channel:{},message:{}", channels, message);
            Long publishRes = redisUtil.publishMsg(channels, message);

            log.info("发布消息结果：{}", publishRes);
            String roomId = resRoom.getRoomId();

            return roomId;
        } catch (Exception e) {
            log.error("服务器异常: ", e);

        }
        return null;

//        String waitKey = MessageFormat.format(RedisKey.READY_JOIN, propers.readyKey);

    }

    /**
     * @Description: 转换score为排名
     * @param: score
     * @return: java.lang.Integer
     * @Author: chenping
     * @Date: 2020/5/24
     */
    private Integer transfromData(int score) {
        if (3 == score) {
            return 0;
        }
        if (2 == score) {
            return 1;
        }
        if (1 == score) {
            return 2;
        }
        if (4 == score) {
            return 3;
        }
        if (5 == score) {
            return 4;
        }
        if (0 == score) {
            return 5;
        }
        return score;
    }


    public String invite(String userId, String roomId) {
        String redisKey = propers.inviteKey;
        Integer peopleNum = redisUtil.getScore(redisKey, roomId).intValue();
        if (peopleNum >= 20) {
            log.info("房间已满");
            throw new CustomException("房间已满");
        }

        //发布消息
        String channels = "debug:game:room_sub_" + roomId;
        String message = "invite_" + userId;
        log.info("channel:{},message:{}", channels, message);
        Long returs = redisUtil.publishMsg(channels, message);

        log.info("发布消息结果：{}", returs);
        return null;
    }
}
