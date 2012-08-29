package com.maddox.rts;

class NetChannelGMsgOutput
{
  public int sequenceNum;
  public int objIndex;
  public byte[] iObjects;
  public long timeLastSend;
  public NetMsgGuaranted msg;
}