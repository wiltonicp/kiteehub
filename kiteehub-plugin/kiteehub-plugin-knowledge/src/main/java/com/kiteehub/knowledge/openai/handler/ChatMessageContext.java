package com.kiteehub.knowledge.openai.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.kiteehub.knowledge.modular.robot.entity.KnowledgeRobot;
import com.kiteehub.knowledge.modular.robot.service.KnowledgeRobotService;
import com.kiteehub.knowledge.modular.robot.service.impl.KnowledgeRobotServiceImpl;
import com.kiteehub.knowledge.openai.domain.PromptRetriever;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

/**
 * 消息策略
 */
@Component
public class ChatMessageContext {

    private MessageStrategy messageStrategy;

    private Session session;

    private String userId;

    private String robotId;

    private String msg;

    private ChatCompletion.Model model = ChatCompletion.Model.GPT_3_5_TURBO_16K_0613;

    private boolean useKT;

    private PromptRetriever retriever;

    private KnowledgeRobot kbRobot;

    public ChatMessageContext() {
    }

    public ChatMessageContext(Session session, String userId, String robotId, String msg,boolean useKT,PromptRetriever retriever) {
        this.session = session;
        this.userId = userId;
        this.robotId = robotId;
        this.msg = msg;
        this.useKT = useKT;
        this.retriever = retriever;

        if(useKT && StringUtils.isNotBlank(this.robotId)){
            KnowledgeRobotService robotService = SpringUtil.getBean(KnowledgeRobotServiceImpl.class);
            this.kbRobot = robotService.queryEntity(robotId);
        }

        messageStrategy = new ChatMsgStrategy();
    }

    public void sendSocketText(){
        messageStrategy.sendOpenaiSocketText(session,this.userId,this.kbRobot,this.model,this.msg,this.useKT,this.retriever);
    }

}
