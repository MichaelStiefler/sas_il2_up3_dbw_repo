package com.maddox.rts.net;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public abstract interface NetFileServer
{
  public static final int ANSWER_NONE = 0;
  public static final int ANSWER_DATA = 1;
  public static final int ANSWER_EXT_DATA = 2;
  public static final int ANSWER_SUCCESS = 3;
  public static final int ANSWER_IO = 4;

  public abstract void doRequest(NetFileRequest paramNetFileRequest);

  public abstract void doCancel(NetFileRequest paramNetFileRequest);

  public abstract void doAnswer(NetFileRequest paramNetFileRequest);

  public abstract String getStateInInfo(NetFileRequest paramNetFileRequest);

  public abstract String getStateOutInfo(NetFileRequest paramNetFileRequest);

  public abstract boolean isStateDataTransfer(NetFileRequest paramNetFileRequest);

  public abstract void destroyIn(NetFileRequest paramNetFileRequest);

  public abstract void destroyOut(NetFileRequest paramNetFileRequest);

  public abstract boolean sendRequestData(NetFileRequest paramNetFileRequest, NetMsgGuaranted paramNetMsgGuaranted, int paramInt1, int paramInt2)
    throws IOException;

  public abstract void getRequestData(NetFileRequest paramNetFileRequest, NetMsgInput paramNetMsgInput)
    throws IOException;

  public abstract int getAnswerState(NetFileRequest paramNetFileRequest, int paramInt);

  public abstract boolean sendAnswerData(NetFileRequest paramNetFileRequest, NetMsgGuaranted paramNetMsgGuaranted, int paramInt1, int paramInt2)
    throws IOException;

  public abstract int getAnswerData(NetFileRequest paramNetFileRequest, NetMsgInput paramNetMsgInput)
    throws IOException;

  public abstract int getAnswerExtData(NetFileRequest paramNetFileRequest, NetMsgInput paramNetMsgInput)
    throws IOException;
}