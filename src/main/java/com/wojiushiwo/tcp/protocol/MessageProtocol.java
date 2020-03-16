package com.wojiushiwo.tcp.protocol;

import lombok.Data;

/**
 * Created by myk
 * 2020/1/29 下午9:47
 */
@Data
public class MessageProtocol {

    private int length;

    private byte[] content;

}
