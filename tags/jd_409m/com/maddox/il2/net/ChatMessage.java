package com.maddox.il2.net;

import com.maddox.rts.NetHost;
import java.util.List;

public class ChatMessage
{
  public static final int MAX_LENGHT = 80;
  public static final byte ORDERS = 1;
  public static final byte LOGMASK = 14;
  public static final int LOGSHIFT = 1;
  public static final int LOG0OBJ = 1;
  public static final int LOG1OBJ = 2;
  public static final int LOG2OBJ = 3;
  public static final int LOG1STR = 6;
  public static final int LOG2STR = 7;
  public byte flags;
  public NetHost from;
  public String msg;
  public Object param0;
  public Object param1;
  public List to;
}