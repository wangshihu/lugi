package com.benchu.lu.handler;

import com.benchu.lu.entity.VoteRequest;
import com.benchu.lu.entity.response.VoteResponse;
import com.benchu.lu.network.netty.server.ServerMsgHandler;
import com.benchu.lu.utils.ThreadUtils;

/**
 * @author benchu
 * @version 2016/11/2.
 */
public class VoteMsgHandler implements ServerMsgHandler<VoteRequest, VoteResponse> {

    @Override
    public VoteResponse messageReceived(VoteRequest request) {
        long senderNum = request.getSenderNum();
        if (senderNum == -1) {
            ThreadUtils.sleepQuitely(20000);
        } else if (senderNum == -2) {
            throw new IllegalStateException("state error:" + senderNum);
        }
        return new VoteResponse(senderNum);
    }
}
