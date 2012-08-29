// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TexCoord3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3f

public class TexCoord3f extends com.maddox.JGP.Tuple3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public TexCoord3f(float f, float f1, float f2)
    {
        super(f, f1, f2);
    }

    public TexCoord3f(float af[])
    {
        super(af);
    }

    public TexCoord3f(com.maddox.JGP.TexCoord3f texcoord3f)
    {
        super(texcoord3f);
    }

    public TexCoord3f()
    {
    }
}
