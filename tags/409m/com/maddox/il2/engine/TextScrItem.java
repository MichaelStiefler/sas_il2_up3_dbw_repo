// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TextScr.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;

// Referenced classes of package com.maddox.il2.engine:
//            TTFont

class TextScrItem
{

    public TextScrItem(com.maddox.JGP.Color4f color4f, com.maddox.il2.engine.TTFont ttfont, java.lang.String s)
    {
        color = color4f;
        font = ttfont;
        str = s;
    }

    com.maddox.JGP.Color4f color;
    com.maddox.il2.engine.TTFont font;
    java.lang.String str;
}
