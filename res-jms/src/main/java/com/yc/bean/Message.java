package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    private String context;
    private String subject;
    private MessageBean messageBean;;
    private String time;
}
