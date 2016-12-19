package com.benchu.lu.entity;

import com.benchu.lu.network.protocol.AbstractRequest;

/**
 * @author benchu
 * @version 2016/10/28.
 */
public class VoteRequest extends AbstractRequest {

    private long senderNum;

    public VoteRequest() {
    }

    public VoteRequest(long id, long senderNum) {
        super(id);
        this.senderNum = senderNum;
    }

    public long getSenderNum() {
        return senderNum;
    }

    public void setSenderNum(long senderNum) {
        this.senderNum = senderNum;
    }
}
