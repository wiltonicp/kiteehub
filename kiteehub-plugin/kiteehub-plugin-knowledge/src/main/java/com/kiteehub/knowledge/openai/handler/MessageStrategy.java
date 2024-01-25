package com.kiteehub.knowledge.openai.handler;

import javax.websocket.Session;

import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.openai.domain.PromptRetriever;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;

import java.util.List;

/**
 * 消息策略
 */
public interface MessageStrategy {

    /**
     * 发送 WebSocket 消息
     *
     * @param userId
     * @param knowledgeRobot
     * @param model
     * @param msg
     * @return
     */
    void sendOpenaiSocketText(Session session, String userId, KnowledgeRobot knowledgeRobot, ChatCompletion.Model model, String msg, boolean useKT, PromptRetriever retriever);
}
