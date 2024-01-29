package com.kiteehub.knowledge.openai.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiteehub.knowledge.constant.ChatConstant;
import com.kiteehub.knowledge.modular.chatai.entity.ChatRecord;
import com.kiteehub.knowledge.modular.chatai.service.ChatRecordService;
import com.kiteehub.knowledge.modular.chatai.service.impl.ChatRecordServiceImpl;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotService;
import com.kiteehub.knowledge.modular.robot.service.impl.KnowledgeRobotServiceImpl;
import com.kiteehub.knowledge.openai.domain.MsgResult;
import com.kiteehub.knowledge.openai.domain.PromptRetriever;
import com.kiteehub.knowledge.openai.enums.MsgType;
import com.kiteehub.knowledge.openai.enums.MsgUserType;
import com.kiteehub.knowledge.openai.handler.ChatMessageContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 描述：websocket 服务端
 *
 * @author Ranger
 */
@Slf4j
@Component
@ServerEndpoint("/kb/socket/{uid}/{robotId}")
public class ChatWebSocketServer {


    //在线总数
    private static int onlineCount;
    //当前会话
    private Session session;
    //用户id
    private String uid;

    //客服ID
    private String robotId;

    private static CopyOnWriteArraySet<ChatWebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 用来存放每个客户端对应的WebSocketServer对象
     */
    private static ConcurrentHashMap<String, ChatWebSocketServer> webSocketMap = new ConcurrentHashMap();

    /**
     * 为了保存在线用户信息，在方法中新建一个list存储一下【实际项目依据复杂度，可以存储到数据库或者缓存】
     */
    private final static List<Session> SESSIONS = Collections.synchronizedList(new ArrayList<>());


    /**
     * 建立连接
     *
     * @param session
     * @param uid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid, @PathParam("robotId") String robotId) {
        this.session = session;
        this.uid = uid;
        this.robotId = robotId;
        webSocketSet.add(this);
        SESSIONS.add(session);
        if (webSocketMap.containsKey(uid + "_" + robotId)) {
            webSocketMap.remove(uid + "_" + robotId);
            webSocketMap.put(uid + "_" + robotId, this);
        } else {
            webSocketMap.put(uid + "_" + robotId, this);
            addOnlineCount();
        }
        sendPrologue(session,robotId);
        log.info("[连接用户ID:{}] 建立连接, 当前连接数:{}", this.uid, getOnlineCount());
    }

    @SneakyThrows
    private void sendPrologue(Session session,String robotId) {
        KnowledgeRobotService robotService = SpringUtil.getBean(KnowledgeRobotServiceImpl.class);
        KnowledgeRobot knowledgeRobot = robotService.queryEntity(robotId);

        ObjectMapper mapper = new ObjectMapper();
        String msgResult = mapper.writeValueAsString(MsgResult.builder()
                .msgType(MsgType.TEXT)
                .content(StringUtils.isNotBlank(knowledgeRobot.getPrologue()) ? knowledgeRobot.getPrologue() : ChatConstant.DEFAULT_PROLOGUE)
                .isEnd(true)
                .createdTime(LocalDateTime.now())
                .robotId(robotId)
                .uid(this.uid).build());

        session.getBasicRemote().sendText(msgResult);
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        if (webSocketMap.containsKey(uid + "_" + robotId)) {
            webSocketMap.remove(uid + "_" + robotId);
            subOnlineCount();
        }
        log.info("[连接用户ID:{}] 断开连接, 当前连接数:{}", uid, getOnlineCount());
    }

    /**
     * 发送错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[连接ID:{}] 错误原因:{}", this.uid, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 接收到客户端消息
     *
     * @param data
     */
    @SneakyThrows
    @OnMessage
    public void onMessage(String data) {
        log.info("收到消息:{}", data);
        JSONObject msgJson = JSONObject.parseObject(data);
        String msg = msgJson.getString("msg");
        this.robotId = msgJson.getString("robotId");

        ChatRecordService chatRecordService = SpringUtil.getBean(ChatRecordServiceImpl.class);

        //保存用户聊天记录
        ChatRecord build = ChatRecord.builder().message(msg).msgType(MsgUserType.USER).robotId(robotId).build();
        build.created(uid);
        chatRecordService.save(build);

        PromptRetriever promptRetriever = new PromptRetriever();
        ChatMessageContext context = new ChatMessageContext(session, this.uid, this.robotId, msg,true,promptRetriever);
        context.sendSocketText();
    }


    /**
     * 获取当前连接数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 当前连接数加一
     */
    public static synchronized void addOnlineCount() {
        ChatWebSocketServer.onlineCount++;
    }

    /**
     * 当前连接数减一
     */
    public static synchronized void subOnlineCount() {
        ChatWebSocketServer.onlineCount--;
    }
}
