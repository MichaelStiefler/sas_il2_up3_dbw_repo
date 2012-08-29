// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SpritesFog.java

package com.maddox.il2.objects.effects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;

// Referenced classes of package com.maddox.il2.objects.effects:
//            SpritesFog

class SpritesFogRender extends com.maddox.il2.engine.Render
{

    public void preRender()
    {
        if(!com.maddox.il2.engine.Actor.isValid(com.maddox.il2.objects.effects.SpritesFog.actor))
        {
            resPreRender = 0;
            return;
        } else
        {
            resPreRender = com.maddox.il2.objects.effects.SpritesFog.actor.draw.preRender(com.maddox.il2.objects.effects.SpritesFog.actor);
            return;
        }
    }

    public void render()
    {
        if(resPreRender > 0)
        {
            com.maddox.il2.engine.Render.prepareStates();
            com.maddox.il2.objects.effects.SpritesFog.actor.draw.render(com.maddox.il2.objects.effects.SpritesFog.actor);
        }
    }

    public void getViewPort(int ai[])
    {
        super.getViewPort(ai);
    }

    public SpritesFogRender(float f)
    {
        super(f);
        resPreRender = 0;
        setSaveAspect(com.maddox.il2.engine.Config.cur.windowSaveAspect);
        useClearDepth(false);
        useClearColor(false);
        contextResized();
    }

    private int resPreRender;
}
