package com.chipswu.intelligentcollaborativecloudimagerepository.manager.websocket.disruptor;

import com.chipswu.intelligentcollaborativecloudimagerepository.manager.websocket.model.PictureEditRequestMessage;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 编辑编辑事件生产者
 *
 * @author WuJiaJun
 */
@Component
@Slf4j
public class PictureEditEventProducer {

    @Resource
    Disruptor<PictureEditEvent> pictureEditEventDisruptor;

    /**
     * 发布事件
     *
     * @param pictureEditRequestMessage 图片编辑请求信息
     * @param session                   会话信息
     * @param user                      当前编辑用户信息
     * @param pictureId                 图片 ID
     */
    public void publishEvent(PictureEditRequestMessage pictureEditRequestMessage,
                             WebSocketSession session,
                             User user,
                             Long pictureId) {
        RingBuffer<PictureEditEvent> ringBuffer = pictureEditEventDisruptor.getRingBuffer();
        // 获取可以生成的位置
        long next = ringBuffer.next();
        PictureEditEvent pictureEditEvent = ringBuffer.get(next);
        pictureEditEvent.setSession(session);
        pictureEditEvent.setPictureEditRequestMessage(pictureEditRequestMessage);
        pictureEditEvent.setUser(user);
        pictureEditEvent.setPictureId(pictureId);
        // 发布事件
        ringBuffer.publish(next);
    }

    /**
     * 优雅停机
     */
    @PreDestroy
    public void close() {
        pictureEditEventDisruptor.shutdown();
    }
}
