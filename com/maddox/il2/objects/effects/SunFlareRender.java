// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SunFlare.java

package com.maddox.il2.objects.effects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.Main3D;

class SunFlareRender extends com.maddox.il2.engine.Render
{

    public void setActor(com.maddox.il2.engine.Actor actor1)
    {
        actor = actor1;
    }

    public boolean isShow()
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return false;
        if(_indx == 0)
            return super.isShow();
        else
            return com.maddox.il2.engine.Config.cur.isUse3Renders() && super.isShow();
    }

    public void preRender()
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
        {
            resPreRender = 0;
            return;
        } else
        {
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(_indx);
            resPreRender = actor.draw.preRender(actor);
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
            return;
        }
    }

    public void render()
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(resPreRender > 0)
        {
            com.maddox.il2.engine.Render.prepareStates();
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(_indx);
            actor.draw.render(actor);
            com.maddox.il2.game.Main3D.cur3D().setRenderIndx(0);
        }
    }

    public SunFlareRender(int i, float f)
    {
        super(f);
        resPreRender = 0;
        _indx = i;
        useClearDepth(false);
        useClearColor(false);
        if(i != 0)
            com.maddox.il2.game.Main3D.cur3D()._getAspectViewPort(i, viewPort);
    }

    private int _indx;
    private int resPreRender;
    private com.maddox.il2.engine.Actor actor;
}
