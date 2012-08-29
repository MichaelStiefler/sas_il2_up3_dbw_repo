// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Renders.java

package com.maddox.il2.engine;

import java.util.Comparator;

// Referenced classes of package com.maddox.il2.engine:
//            Render

class RendersComparator
    implements java.util.Comparator
{

    RendersComparator()
    {
    }

    public int compare(java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.il2.engine.Render render = (com.maddox.il2.engine.Render)obj;
        com.maddox.il2.engine.Render render1 = (com.maddox.il2.engine.Render)obj1;
        if(render == render1)
            return 0;
        return render1.zOrder - render.zOrder >= 0.0F ? 1 : -1;
    }
}
