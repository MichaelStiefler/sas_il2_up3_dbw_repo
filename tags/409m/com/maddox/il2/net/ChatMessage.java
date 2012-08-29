// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ChatMessage.java

package com.maddox.il2.net;

import com.maddox.rts.NetHost;
import java.util.List;

public class ChatMessage
{

    public ChatMessage()
    {
    }

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
    public com.maddox.rts.NetHost from;
    public java.lang.String msg;
    public java.lang.Object param0;
    public java.lang.Object param1;
    public java.util.List to;
}
