// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowLookAndFeel.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GBevel, GRegion, GWindow, GColor, 
//            GWindowClient, GWindowTabDialogClient, GTexRegion, GTexture, 
//            GWindowTree, GWindowTable, GSize, GWindowComboControl, 
//            GWindowEditBox, GWindowButtonTexture, GWindowBoxSeparate, GWindowVSeparate, 
//            GWindowHSeparate, GWindowVSliderInt, GWindowHSliderInt, GWindowEditNumber, 
//            GWindowEditControl, GWindowEditText, GWindowEditTextControl, GWindowCheckBox, 
//            GWindowVScrollBar, GWindowHScrollBar, GWindowLabel, GWindowButton, 
//            GWindowDialogControl, GWindowFrameCloseBox, GWindowFramed, GWindowMenuItem, 
//            GWindowMenu, GWindowMenuBarItem, GWindowMenuBar, GWindowStatusBar, 
//            GWindowRoot

public abstract class GWindowLookAndFeel
{

    public GWindowLookAndFeel()
    {
    }

    public float metric()
    {
        return 12F;
    }

    public float metric(float f)
    {
        return metric() * f;
    }

    public void drawBevel(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, com.maddox.gwindow.GBevel gbevel, com.maddox.gwindow.GTexture gtexture)
    {
        drawBevel(gwindow, f, f1, f2, f3, gbevel, gtexture, true);
    }

    public void drawBevel(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, com.maddox.gwindow.GBevel gbevel, com.maddox.gwindow.GTexture gtexture, 
            boolean flag)
    {
        com.maddox.gwindow.GRegion gregion = gbevel.TL;
        gwindow.draw(f, f1, gregion.dx, gregion.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.T;
        gwindow.draw(f + gbevel.TL.dx, f1, f2 - gbevel.TL.dx - gbevel.TR.dx, gregion.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.TR;
        gwindow.draw((f + f2) - gregion.dx, f1, gregion.dx, gregion.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.L;
        gwindow.draw(f, f1 + gbevel.TL.dy, gregion.dx, f3 - gbevel.TL.dy - gbevel.BL.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.R;
        gwindow.draw((f + f2) - gregion.dx, f1 + gbevel.TL.dy, gregion.dx, f3 - gbevel.TL.dy - gbevel.BL.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.BL;
        gwindow.draw(f, (f1 + f3) - gregion.dy, gregion.dx, gregion.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.B;
        gwindow.draw(f + gbevel.BL.dx, (f1 + f3) - gregion.dy, f2 - gbevel.BL.dx - gbevel.BR.dx, gregion.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        gregion = gbevel.BR;
        gwindow.draw((f + f2) - gregion.dx, (f1 + f3) - gregion.dy, gregion.dx, gregion.dy, gtexture, gregion.x, gregion.y, gregion.dx, gregion.dy);
        if(flag)
        {
            com.maddox.gwindow.GRegion gregion1 = gbevel.Area;
            gwindow.draw(f + gbevel.TL.dx, f1 + gbevel.TL.dy, f2 - gbevel.BL.dx - gbevel.BR.dx, f3 - gbevel.TL.dy - gbevel.BL.dy, gtexture, gregion1.x, gregion1.y, gregion1.dx, gregion1.dy);
        }
    }

    public void fillRegion(com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GColor gcolor, com.maddox.gwindow.GRegion gregion)
    {
        fillRegion(gwindow, gcolor.color, gregion.x, gregion.y, gregion.dx, gregion.dy);
    }

    public void fillRegion(com.maddox.gwindow.GWindow gwindow, int i, com.maddox.gwindow.GRegion gregion)
    {
        fillRegion(gwindow, i, gregion.x, gregion.y, gregion.dx, gregion.dy);
    }

    public void fillRegion(com.maddox.gwindow.GWindow gwindow, com.maddox.gwindow.GColor gcolor, float f, float f1, float f2, float f3)
    {
        fillRegion(gwindow, gcolor.color, f, f1, f2, f3);
    }

    public void fillRegion(com.maddox.gwindow.GWindow gwindow, int i, float f, float f1, float f2, float f3)
    {
    }

    public void drawSeparateH(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
    }

    public void drawSeparateW(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
    }

    public void soundPlay(java.lang.String s)
    {
    }

    public void renderClient(com.maddox.gwindow.GWindowTree gwindowtree)
    {
    }

    public void render(com.maddox.gwindow.GWindowTree gwindowtree)
    {
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowTree gwindowtree, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowTable gwindowtable)
    {
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowTable gwindowtable, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowTabDialogClient gwindowtabdialogclient)
    {
    }

    public void render(com.maddox.gwindow.GWindowTabDialogClient.Tab tab)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowTabDialogClient.Tab tab, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public void render(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
    }

    public void renderComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
    }

    public void setupComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
    }

    public void setupComboEditBox(com.maddox.gwindow.GWindowEditBox gwindoweditbox)
    {
    }

    public void setupComboButton(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
    }

    public float getComboH()
    {
        return 14.4F;
    }

    public float getComboHmetric()
    {
        return 1.2F;
    }

    public float getComboHline()
    {
        return 14.4F;
    }

    public void render(com.maddox.gwindow.GWindowBoxSeparate gwindowboxseparate)
    {
    }

    public void render(com.maddox.gwindow.GWindowVSeparate gwindowvseparate)
    {
    }

    public float getVSeparateW()
    {
        return 2.0F;
    }

    public void render(com.maddox.gwindow.GWindowHSeparate gwindowhseparate)
    {
    }

    public float getHSeparateH()
    {
        return 2.0F;
    }

    public void render(com.maddox.gwindow.GWindowVSliderInt gwindowvsliderint)
    {
    }

    public void setupVSliderIntSizes(com.maddox.gwindow.GWindowVSliderInt gwindowvsliderint)
    {
    }

    public float getVSliderIntW()
    {
        return 12F;
    }

    public float getVSliderIntWmetric()
    {
        return 1.0F;
    }

    public void render(com.maddox.gwindow.GWindowHSliderInt gwindowhsliderint)
    {
    }

    public void setupHSliderIntSizes(com.maddox.gwindow.GWindowHSliderInt gwindowhsliderint)
    {
    }

    public float getHSliderIntH()
    {
        return 12F;
    }

    public float getHSliderIntHmetric()
    {
        return 1.0F;
    }

    public void render(com.maddox.gwindow.GWindowEditNumber gwindoweditnumber)
    {
    }

    public void setupEditNumber(com.maddox.gwindow.GWindowEditNumber gwindoweditnumber)
    {
    }

    public void render(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol)
    {
    }

    public void render(com.maddox.gwindow.GWindowEditBox gwindoweditbox, float f)
    {
    }

    public void render(com.maddox.gwindow.GWindowEditText gwindowedittext)
    {
    }

    public void render(com.maddox.gwindow.GWindowEditTextControl gwindowedittextcontrol)
    {
    }

    public float getBorderSizeEditTextControl()
    {
        return 0.0F;
    }

    public void render(com.maddox.gwindow.GWindowCheckBox gwindowcheckbox)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowCheckBox gwindowcheckbox, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public void render(com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar)
    {
    }

    public void setupVScrollBarSizes(com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar)
    {
    }

    public void render(com.maddox.gwindow.GWindowHScrollBar gwindowhscrollbar)
    {
    }

    public void setupHScrollBarSizes(com.maddox.gwindow.GWindowHScrollBar gwindowhscrollbar)
    {
    }

    public float getHScrollBarH()
    {
        return 12F;
    }

    public float getHScrollBarW()
    {
        return 12F;
    }

    public float getVScrollBarH()
    {
        return 12F;
    }

    public float getVScrollBarW()
    {
        return 12F;
    }

    public void setupScrollButtonUP(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
    }

    public void setupScrollButtonDOWN(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
    }

    public void setupScrollButtonLEFT(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
    }

    public void setupScrollButtonRIGHT(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
    }

    public void render(com.maddox.gwindow.GWindowLabel gwindowlabel)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowLabel gwindowlabel, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowLabel gwindowlabel, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowButton gwindowbutton)
    {
    }

    public void render(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowButton gwindowbutton, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowButton gwindowbutton, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void renderTextDialogControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol, float f, float f1, float f2, float f3, int i, boolean flag)
    {
    }

    public com.maddox.gwindow.GSize getMinSizeDialogControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegionDialogControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowClient gwindowclient)
    {
    }

    public void setMessageBoxTextColor(com.maddox.gwindow.GWindowClient gwindowclient)
    {
        gwindowclient.setCanvasColorBLACK();
    }

    public void setupFrameCloseBox(com.maddox.gwindow.GWindowFrameCloseBox gwindowframeclosebox)
    {
    }

    public void frameSetCloseBoxPos(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
    }

    public int frameHitTest(com.maddox.gwindow.GWindowFramed gwindowframed, float f, float f1)
    {
        return 0;
    }

    public void render(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowFramed gwindowframed, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowFramed gwindowframed, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenu gwindowmenu)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenu gwindowmenu, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenu gwindowmenu, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowMenuBar gwindowmenubar)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowMenuBar gwindowmenubar, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowMenuBar gwindowmenubar, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar)
    {
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar, com.maddox.gwindow.GSize gsize)
    {
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowStatusBar gwindowstatusbar, com.maddox.gwindow.GRegion gregion, float f)
    {
        return gregion;
    }

    public void resolutionChanged(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
    }

    public void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
    }

    public java.lang.String i18n(java.lang.String s)
    {
        if(s == null)
            return s;
        if(s.indexOf('_') < 0)
            return s;
        else
            return s.replace('_', ' ');
    }

    public com.maddox.gwindow.GBevel bevelTabDialogClient;
    public com.maddox.gwindow.GTexRegion regionWhite;
    public boolean bSoundEnable;
    public static final float metricConst = 12F;
}
