// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DarkerNight.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.MsgGLContextListener;
import com.maddox.opengl.gl;

// Referenced classes of package com.maddox.il2.objects.effects:
//            OverLoad

public class DarkerNight extends com.maddox.il2.engine.Render
    implements com.maddox.opengl.MsgGLContextListener
{

    public void msgGLContext(int i)
    {
    }

    protected void contextResize(int i, int j)
    {
    }

    public void preRender()
    {
        if(com.maddox.il2.ai.World.Sun().ToSun.z < 0.0F)
        {
            alpha = com.maddox.il2.ai.World.Sun().sunMultiplier;
            if(alpha < 0.35F)
                alpha = 0.35F;
        } else
        {
            alpha = 1.0F;
        }
    }

    public DarkerNight(int i, float f)
    {
        super(f);
        _indx = 0;
        alpha = 1.0F;
        _indx = i;
        useClearDepth(false);
        useClearColor(false);
        if(_indx == 0)
            setName("DarkerNight");
        com.maddox.opengl.GLContext.getCurrent().msgAddListener(this, null);
        if(i != 0)
            com.maddox.il2.game.Main3D.cur3D()._getAspectViewPort(i, viewPort);
    }

    public void render()
    {
        if((double)alpha < 1.0D)
        {
            com.maddox.il2.engine.Render.clearStates();
            com.maddox.opengl.gl.ShadeModel(7425);
            com.maddox.opengl.gl.Disable(2929);
            com.maddox.opengl.gl.Enable(3553);
            com.maddox.opengl.gl.Enable(3042);
            com.maddox.opengl.gl.AlphaFunc(516, 0.0F);
            com.maddox.opengl.gl.BlendFunc(774, 770);
            com.maddox.opengl.gl.Begin(6);
            com.maddox.opengl.gl.Color4f(0.0F, 0.0F, 0.0F, alpha);
            com.maddox.opengl.gl.Vertex2f(0.0F, 3F);
            com.maddox.opengl.gl.Vertex2f(-3F, -3F);
            com.maddox.opengl.gl.Vertex2f(3F, -3F);
            com.maddox.opengl.gl.End();
            com.maddox.opengl.gl.BlendFunc(770, 771);
        }
    }

    public void setShow(boolean flag)
    {
        if(_indx == 0)
            super.setShow(flag);
    }

    public boolean isShow()
    {
        if(_indx == 0)
            return super.isShow();
        else
            return com.maddox.il2.engine.Config.cur.isUse3Renders() && com.maddox.il2.game.Main3D.cur3D().overLoad.isShow();
    }

    int _indx;
    private float alpha;
}
