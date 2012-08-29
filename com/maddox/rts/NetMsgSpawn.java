// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetMsgSpawn.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetMsgGuaranted, NetSpawn, NetException, NetObj, 
//            Finger, Spawn

public class NetMsgSpawn extends com.maddox.rts.NetMsgGuaranted
{

    public NetMsgSpawn(com.maddox.rts.NetObj netobj)
    {
        setFingerClass(netobj);
    }

    public NetMsgSpawn(byte abyte0[], com.maddox.rts.NetObj netobj)
    {
        super(abyte0);
        setFingerClass(netobj);
    }

    public NetMsgSpawn(int i, com.maddox.rts.NetObj netobj)
    {
        super(i);
        setFingerClass(netobj);
    }

    private void setFingerClass(com.maddox.rts.NetObj netobj)
    {
        int i = 0;
        java.lang.Object obj = netobj.superObj();
        if(obj != null)
        {
            i = com.maddox.rts.Finger.Int(obj.getClass().getName());
            java.lang.Object obj1 = com.maddox.rts.Spawn.get(i);
            if(obj1 != null)
            {
                if(!(obj1 instanceof com.maddox.rts.NetSpawn))
                    i = 0;
            } else
            {
                i = 0;
            }
        }
        if(i == 0)
        {
            i = com.maddox.rts.Finger.Int(netobj.getClass().getName());
            java.lang.Object obj2 = com.maddox.rts.Spawn.get(i);
            if(obj2 != null)
            {
                if(!(obj2 instanceof com.maddox.rts.NetSpawn))
                    i = 0;
            } else
            {
                i = 0;
            }
        }
        if(i == 0)
            throw new NetException("NetSpawn interface NOT found");
        try
        {
            writeInt(i);
            writeShort(netobj.idLocal & 0x7fff);
        }
        catch(java.lang.Exception exception) { }
    }
}
