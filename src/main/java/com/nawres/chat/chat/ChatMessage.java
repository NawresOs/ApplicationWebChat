package com.nawres.chat.chat;

import lombok.Builder;
import lombok.Data;

import java.awt.*;
@Builder
@Data
public class ChatMessage {
    private String content;

    private String sender;
    private  MessageType type;
}
