package com.maddox.rts.net;

import com.maddox.rts.NetSocket;

public class NetEmptySocket extends NetSocket
{
  public Class addressClass()
  {
    return NetEmptyAddress.class;
  }
}