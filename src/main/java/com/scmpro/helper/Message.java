package com.scmpro.helper;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Message {
    private String content;
    private MessageType type = MessageType.blue;

}
