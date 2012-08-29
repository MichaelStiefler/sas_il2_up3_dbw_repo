// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Color3b.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3b

public class Color3b extends com.maddox.JGP.Tuple3b
    implements java.io.Serializable, java.lang.Cloneable
{

    public Color3b(byte byte0, byte byte1, byte byte2)
    {
        super(byte0, byte1, byte2);
    }

    public Color3b(byte abyte0[])
    {
        super(abyte0);
    }

    public Color3b(com.maddox.JGP.Color3b color3b)
    {
        super(color3b);
    }

    public Color3b(com.maddox.JGP.Tuple3b tuple3b)
    {
        super(tuple3b);
    }

    public Color3b()
    {
    }
}
