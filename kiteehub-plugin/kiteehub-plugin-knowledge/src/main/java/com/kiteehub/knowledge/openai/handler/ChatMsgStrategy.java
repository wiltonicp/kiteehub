package com.kiteehub.knowledge.openai.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiteehub.knowledge.chain.vectorstore.MilvusVectorStore;
import com.kiteehub.knowledge.constant.ChatConstant;
import com.kiteehub.knowledge.modular.knowledge.service.EmbeddingService;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robotpreset.entity.KnowledgeRobotPreset;
import com.kiteehub.knowledge.openai.domain.MsgResult;
import com.kiteehub.knowledge.openai.domain.PromptRetriever;
import com.kiteehub.knowledge.openai.enums.MsgType;
import com.kiteehub.knowledge.openai.listener.KBSocketEventSourceListener;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.BaseMessage;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消息策略
 */
@Log4j2
@Component
public class ChatMsgStrategy implements MessageStrategy {

    private static OpenAiStreamClient openAiStreamClient;

    @Autowired
    public void setService(OpenAiStreamClient openAiStreamClient) {
        ChatMsgStrategy.openAiStreamClient = openAiStreamClient;
    }

    public ChatMsgStrategy() {
    }

    @SneakyThrows
    @Override
    public void sendOpenaiSocketText(Session session, String userId, KnowledgeRobot kbRobot, ChatCompletion.Model model, String msg, boolean useKT, PromptRetriever retriever) {
        List<String> nearestList = new ArrayList<>();

        List<KnowledgeRobotPreset> presets = kbRobot.getPresets();
        if(ObjectUtil.isNotEmpty(presets)){
            List<KnowledgeRobotPreset> collect = presets.stream().filter(preset -> preset.getQuestion().contains(msg)).collect(Collectors.toList());
            if(ObjectUtil.isNotEmpty(collect)){
                String answer = collect.stream().findFirst().get().getAnswer();
                char[] charArray = answer.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    //直接返回
                    ObjectMapper mapper = new ObjectMapper();
                    String msgResult = mapper.writeValueAsString(MsgResult.builder()
                            .msgType(MsgType.TEXT)
                            .content(String.valueOf(charArray[i]))
                            .isEnd(i == charArray.length - 1)
                            .robotId(kbRobot.getId())
                            .createdTime(LocalDateTime.now())
                            .uid(userId).build());
                    session.getBasicRemote().sendText(msgResult);
                }
                return;
            }
        }

        if (useKT) {
            //将文本转化为向量
            EmbeddingService embeddingService = SpringUtil.getBean(EmbeddingService.class);
            List<Double> queryVector = embeddingService.getQueryVector(msg);

            MilvusVectorStore vectorStore = SpringUtil.getBean(MilvusVectorStore.class);
            kbRobot.getKids().forEach(kid -> {
                List<String> nearest = vectorStore.nearest(queryVector, kid);
                nearestList.addAll(nearest);
            });
        }
        if (useKT && nearestList.isEmpty() && retriever.isStrict()) {
            //直接返回
            ObjectMapper mapper = new ObjectMapper();
            String msgResult = mapper.writeValueAsString(MsgResult.builder()
                    .msgType(MsgType.TEXT)
                    .content(StrUtil.isBlankIfStr(kbRobot.getEmptySearchReply()) ? ChatConstant.GOAL_KEEPER_WORDS : kbRobot.getEmptySearchReply())
                    .isEnd(true)
                    .robotId(kbRobot.getId())
                    .createdTime(LocalDateTime.now())
                    .uid(userId).build());
            session.getBasicRemote().sendText(msgResult);
        } else {
            List<Message> messages = assemblyMessage(kbRobot.getName(), nearestList, msg);
            KBSocketEventSourceListener eventSourceListener = new KBSocketEventSourceListener(session, userId, kbRobot.getId());
            ChatCompletion completion = ChatCompletion
                    .builder()
                    .temperature(0.7)
                    .maxTokens(2000)
                    .presencePenalty(0.0)
                    .messages(messages)
                    .model(model.getName())
                    .build();
            openAiStreamClient.streamChatCompletion(completion, eventSourceListener);
        }
    }

    /**
     * 组装历史数据
     *
     * @return
     */
    @SneakyThrows
    private List<Message> assemblyMessage(String robotName, List<String> nearestList, String msg) {
        List<Message> messages = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(nearestList)) {
            String information = String.join("\n\n", nearestList);
            String promptTemplate = String.format(ChatConstant.PROMPT_TEMPLATE, robotName, information);
            messages.add(Message.builder()
                    .content(promptTemplate)
                    .role(BaseMessage.Role.SYSTEM.getName())
                    .build());
            messages.add(Message.builder()
                    .content(msg)
                    .role(BaseMessage.Role.USER.getName())
                    .build());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("PROMPT消息内容：{}", objectMapper.writeValueAsString(messages));
        return messages;
    }
}
