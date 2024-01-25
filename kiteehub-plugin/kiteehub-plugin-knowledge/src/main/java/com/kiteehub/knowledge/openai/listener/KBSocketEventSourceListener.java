package com.kiteehub.knowledge.openai.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiteehub.common.cache.CommonCacheOperator;
import com.kiteehub.knowledge.constant.ChatConstant;
import com.kiteehub.knowledge.modular.article.util.StringUtil;
import com.kiteehub.knowledge.modular.chatai.entity.ChatRecord;
import com.kiteehub.knowledge.modular.chatai.service.ChatRecordService;
import com.kiteehub.knowledge.modular.chatai.service.impl.ChatRecordServiceImpl;
import com.kiteehub.knowledge.openai.enums.MsgUserType;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.kiteehub.knowledge.openai.domain.MsgResult;
import com.kiteehub.knowledge.openai.enums.MsgType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 描述：OpenAI流式输出Socket接收
 *
 * @author Ranger
 * @date 2023-03-23
 */
@Slf4j
public class KBSocketEventSourceListener extends EventSourceListener {

    private long tokens;

    private Session session;

    private String userId;

    private String robotId;

    private ChatRecordService chatRecordService;

    private CommonCacheOperator cacheOperator;

    public static ObjectMapper mapper = new ObjectMapper();

    public KBSocketEventSourceListener(Session session) {
        this.session = session;
    }

    public KBSocketEventSourceListener(Session session,String userId,String robotId) {
        this.session = session;
        this.userId = userId;
        this.robotId = robotId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response response) {
        log.info("OpenAI建立sse连接...");
    }


    /**
     * {@inheritDoc}
     */
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("OpenAI返回数据：{}", data);
        tokens += 1;
        cacheOperator = SpringUtil.getBean(CommonCacheOperator.class);

        if (data.equals("[DONE]") || data.contains("\"finish_reason\":\"stop\"")) {
            log.info("OpenAI返回数据结束了");
            return;
        }
        if(data.contains("\"choices\":[]")){
            return;
        }
        ChatCompletionResponse completionResponse = mapper.readValue(data, ChatCompletionResponse.class); // 读取Json
        String content = completionResponse.getChoices().get(0).getDelta().getContent();
        if(content != null){
            MsgResult build = MsgResult.builder().msgType(MsgType.TEXT).content(content).isEnd(false).robotId(robotId).uid(userId).build();
            cacheOperator.lSet(userId,content);
            String delta = mapper.writeValueAsString(build);
            session.getBasicRemote().sendText(delta);
        }
    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("流式输出返回值总共{}tokens", tokens() - 2);
        log.info("OpenAI关闭sse连接...");

        //保存AI聊天记录
        List<Object> objects = cacheOperator.lGet(userId, 0L, -1L);
        chatRecordService = SpringUtil.getBean(ChatRecordServiceImpl.class);
        ChatRecord build = ChatRecord.builder().message(StringUtil.listToString(objects)).msgType(MsgUserType.AI).robotId(robotId).build();
        build.created(userId);
        chatRecordService.save(build);

        String delta = null;
        try {
            delta = mapper.writeValueAsString(MsgResult.builder().msgType(MsgType.TEXT).content("").isEnd(true).robotId(robotId).uid(userId).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            session.getBasicRemote().sendText(delta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //清除缓存
        cacheOperator.del(userId);
    }


    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (Objects.isNull(response)) {
            return;
        }
        ResponseBody body = response.body();
        if (Objects.nonNull(body)) {
            String delta = mapper.writeValueAsString(MsgResult.builder().msgType(MsgType.TEXT).content(ChatConstant.GOAL_KEEPER_WORDS).isEnd(true).robotId(robotId).uid(userId).build());
            session.getBasicRemote().sendText(delta);
            log.error("OpenAI  sse连接异常data：{}，异常：{}", body.string(), t);
        } else {
            log.error("OpenAI  sse连接异常data：{}，异常：{}", response, t);
        }
        eventSource.cancel();
    }

    /**
     * tokens
     * @return
     */
    public long tokens() {
        return tokens;
    }
}
