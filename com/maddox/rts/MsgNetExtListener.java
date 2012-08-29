// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgNetExtListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetSocket, NetAddress

public interface MsgNetExtListener
{

    public abstract void msgNetExt(byte abyte0[], com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i);
}
