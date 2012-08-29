// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRoot.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import java.util.Locale;

public class GUIRoot extends com.maddox.gwindow.GWindowRoot
{

    public GUIRoot()
    {
        backgroundCountry = null;
    }

    public void setBackCountry(java.lang.String s, java.lang.String s1)
    {
        if(s == null || s1 == null)
        {
            backgroundCountry = null;
        } else
        {
            java.lang.String s2 = com.maddox.rts.RTSConf.cur.locale.getLanguage();
            java.lang.String s3 = null;
            if(!"us".equalsIgnoreCase(s2) && !"en".equalsIgnoreCase(s2))
            {
                s3 = "missions/" + s + "/" + s1 + "/background_" + s2 + ".mat";
                if(!existSFSFile(s3))
                    s3 = null;
            }
            if(s3 == null)
                s3 = "missions/" + s + "/" + s1 + "/background.mat";
            java.lang.Object obj = com.maddox.il2.engine.FObj.Get(s3);
            if(obj != null)
                backgroundCountry = com.maddox.gwindow.GTexture.New(s3);
        }
    }

    public void render()
    {
        if(com.maddox.il2.engine.RendersMain.getRenderFocus() == (com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"))
        {
            setCanvasColorWHITE();
            if(backgroundCountry != null)
                draw(0.0F, 0.0F, win.dx, win.dy, backgroundCountry);
            else
                draw(0.0F, 0.0F, win.dx, win.dy, background);
        }
    }

    public void created()
    {
        background = com.maddox.gwindow.GTexture.New("GUI/background.mat");
        java.lang.String s = null;
        java.lang.String s1 = com.maddox.rts.RTSConf.cur.locale.getLanguage();
        if(!"us".equalsIgnoreCase(s1) && !"en".equalsIgnoreCase(s1))
        {
            s = "missions/background_" + com.maddox.rts.RTSConf.cur.locale.getLanguage() + ".mat";
            if(!existSFSFile(s))
                s = null;
        }
        if(s == null)
            s = "missions/background.mat";
        java.lang.Object obj = com.maddox.il2.engine.FObj.Get(s);
        if(obj != null)
            background = com.maddox.gwindow.GTexture.New(s);
        super.created();
    }

    private boolean existSFSFile(java.lang.String s)
    {
        com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(s);
        sfsinputstream.close();
        return true;
        java.lang.Exception exception;
        exception;
        return false;
    }

    com.maddox.gwindow.GTexture background;
    com.maddox.gwindow.GTexture backgroundCountry;
}
