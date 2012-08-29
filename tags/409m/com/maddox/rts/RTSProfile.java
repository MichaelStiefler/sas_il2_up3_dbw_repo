// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RTSProfile.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            RTSConf

public class RTSProfile
{

    public RTSProfile()
    {
    }

    public static void get(com.maddox.rts.RTSProfile rtsprofile)
    {
        rtsprofile.frames = com.maddox.rts.RTSConf.cur.profile.frames;
        rtsprofile.messages = com.maddox.rts.RTSConf.cur.profile.messages;
        com.maddox.rts.RTSConf.cur.profile.frames = 0;
        com.maddox.rts.RTSConf.cur.profile.messages = 0;
    }

    protected void endFrame()
    {
        frames++;
        messages += countMessages;
        countMessages = 0;
    }

    public int frames;
    public int messages;
    protected int countMessages;
}
