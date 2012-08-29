// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Color4b.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4b

public class Color4b extends com.maddox.JGP.Tuple4b
    implements java.io.Serializable, java.lang.Cloneable
{

    public Color4b(byte byte0, byte byte1, byte byte2, byte byte3)
    {
        super(byte0, byte1, byte2, byte3);
    }

    public Color4b(byte abyte0[])
    {
        super(abyte0);
    }

    public Color4b(com.maddox.JGP.Color4b color4b)
    {
        super(color4b);
    }

    public Color4b(com.maddox.JGP.Tuple4b tuple4b)
    {
        super(tuple4b);
    }

    public Color4b()
    {
    }
}
