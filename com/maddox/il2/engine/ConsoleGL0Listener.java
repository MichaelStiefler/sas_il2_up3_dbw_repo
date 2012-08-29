// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ConsoleGL0.java

package com.maddox.il2.engine;

import com.maddox.rts.ConsoleListener;

// Referenced classes of package com.maddox.il2.engine:
//            ConsoleGL0Render

class ConsoleGL0Listener
    implements com.maddox.rts.ConsoleListener
{

    public ConsoleGL0Listener(com.maddox.il2.engine.ConsoleGL0Render consolegl0render)
    {
        bActive = false;
        render = consolegl0render;
    }

    public void consoleChanged()
    {
        if(!bActive)
        {
            bActive = true;
            render.exlusiveDraw();
            bActive = false;
        }
    }

    com.maddox.il2.engine.ConsoleGL0Render render;
    boolean bActive;
}
