package com.benchu.lu.entity.response;

import com.benchu.lu.network.protocol.AbstractResponse;

/**
 * @author benchu
 * @version 2016/11/1.
 */
public class VoteResponse extends AbstractResponse {
    private long replayNum;

    public VoteResponse() {
    }

    public VoteResponse(long replayNum) {
        this.replayNum = replayNum;
    }

    public long getReplayNum() {
        return replayNum;
    }

    public void setReplayNum(int replayNum) {
        this.replayNum = replayNum;
    }
}
