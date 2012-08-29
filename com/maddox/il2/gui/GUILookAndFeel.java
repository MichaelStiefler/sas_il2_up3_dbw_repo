// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUILookAndFeel.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GCursorTexRegion;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWin95LookAndFeel;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowButtonTexture;
import com.maddox.gwindow.GWindowClient;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditBox;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFrameCloseBox;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHScrollBar;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.I18N;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;
import java.util.HashMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUISeparate

public class GUILookAndFeel extends com.maddox.gwindow.GWin95LookAndFeel
{

    public GUILookAndFeel()
    {
        sndKey1 = new Sample("key_01.wav");
        sndKey2 = new Sample("key_02.wav");
        guiSound = new SoundFX(new SoundPreset("interface"));
        sounds = new HashMap();
        sounds.put("comboShow", sndKey1);
        sounds.put("comboHide", sndKey2);
        sounds.put("clickCheckBox", sndKey2);
        sounds.put("clickButton", sndKey1);
        sounds.put("clickSwitch", sndKey2);
        spaceComboList = 3F;
        minScrollMSize = 0.5F;
        spaceButton = 0.0F;
        spaceFramedTitle = 0.25F;
        _titleRegion = new GRegion();
    }

    public void soundPlay(java.lang.String s)
    {
        if(bSoundEnable)
            guiSound.play((com.maddox.sound.Sample)sounds.get(s));
    }

    public void drawSeparateH(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        com.maddox.il2.gui.GUISeparate.draw(gwindow, com.maddox.gwindow.GColor.Gray, f, f1, f2, 2.0F);
    }

    public void drawSeparateW(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2)
    {
        com.maddox.il2.gui.GUISeparate.draw(gwindow, com.maddox.gwindow.GColor.Gray, f, f1, 2.0F, f2);
    }

    public void render(com.maddox.gwindow.GWindowTable gwindowtable)
    {
        gwindowtable.setCanvasColorWHITE();
        drawBevel(gwindowtable, 0.0F, 0.0F, gwindowtable.win.dx, gwindowtable.win.dy, bevelComboDown, basicelements, true);
        com.maddox.gwindow.GRegion gregion = gwindowtable.root.C.clip;
        gregion.x += bevelComboDown.L.dx;
        gregion.y += bevelComboDown.T.dy;
        gregion.dx -= bevelComboDown.L.dx + bevelComboDown.R.dx;
        gregion.dy -= bevelComboDown.T.dy + bevelComboDown.B.dy;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowTable gwindowtable, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelComboDown.L.dx + f;
        gregion.y = bevelComboDown.T.dy + f;
        gregion.dx = gwindowtable.win.dx - gregion.x - bevelComboDown.R.dx - f;
        gregion.dy = gwindowtable.win.dy - gregion.y - bevelComboDown.B.dy - f;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowEditControl gwindoweditcontrol)
    {
        gwindoweditcontrol.setCanvasColorWHITE();
        drawBevel(gwindoweditcontrol, 0.0F, 0.0F, gwindoweditcontrol.win.dx, gwindoweditcontrol.win.dy, bevelComboDown, basicelements, true);
        render(((com.maddox.gwindow.GWindowEditBox) (gwindoweditcontrol)), bevelComboDown.L.dx);
    }

    public float xComboListItem(int i, float f, float f1)
    {
        if(i == 0 || f1 >= f)
            return 0.0F;
        if(i == 2)
            return f - f1;
        if(i == 1)
            return (f - f1) / 2.0F;
        else
            return 0.0F;
    }

    public void renderComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        com.maddox.gwindow.GWindowComboControl.ListArea listarea = gwindowcombocontrol.listArea;
        int i = ((com.maddox.gwindow.GWindow) (listarea)).root.C.alpha;
        ((com.maddox.gwindow.GWindow) (listarea)).root.C.alpha = 255;
        listarea.setCanvasColorWHITE();
        drawBevel(listarea, 0.0F, 0.0F, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, ((com.maddox.gwindow.GWindow) (listarea)).win.dy, bevelComboUp, basicelements);
        com.maddox.gwindow.GRegion gregion = listarea.getClientRegion();
        gregion.x += bevelComboUp.L.dx + spaceComboList;
        gregion.y += bevelComboUp.T.dy;
        gregion.dx -= bevelComboUp.L.dx + bevelComboUp.R.dx + 2.0F * spaceComboList;
        gregion.dy -= bevelComboUp.T.dy + bevelComboUp.B.dy;
        if(listarea.pushClipRegion(gregion, true, 0.0F))
        {
            listarea.setCanvasColorBLACK();
            listarea.setCanvasFont(gwindowcombocontrol.font);
            com.maddox.gwindow.GFont gfont = gwindowcombocontrol.root.C.font;
            float f = (getComboHline() - gfont.height) / 2.0F;
            int j = gwindowcombocontrol.listStartLine;
            for(int k = 0; k < gwindowcombocontrol.listCountLines; k++)
            {
                com.maddox.gwindow.GSize gsize = gfont.size(gwindowcombocontrol.get(j));
                if(j == gwindowcombocontrol.listSelected)
                {
                    listarea.draw(0.0F, f, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, gsize.dy, elements, 4F, 98F, 1.0F, 1.0F);
                    listarea.setCanvasColorWHITE();
                    listarea.draw(xComboListItem(gwindowcombocontrol.align, gregion.dx, gsize.dx), f, gwindowcombocontrol.get(j));
                    listarea.setCanvasColorBLACK();
                } else
                if(gwindowcombocontrol.posEnable != null && !gwindowcombocontrol.posEnable[j])
                {
                    listarea.setCanvasColorWHITE();
                    listarea.draw(0.0F, f, ((com.maddox.gwindow.GWindow) (listarea)).win.dx, gsize.dy, elements, 2.0F, 16F, 1.0F, 1.0F);
                    listarea.draw(xComboListItem(gwindowcombocontrol.align, gregion.dx, gsize.dx) + 1.0F, f + 1.0F, gwindowcombocontrol.get(j));
                    listarea.setCanvasColor(0x7f7f7f);
                    listarea.draw(xComboListItem(gwindowcombocontrol.align, gregion.dx, gsize.dx), f, gwindowcombocontrol.get(j));
                    listarea.setCanvasColorBLACK();
                } else
                {
                    listarea.draw(xComboListItem(gwindowcombocontrol.align, gregion.dx, gsize.dx), f, gwindowcombocontrol.get(j));
                }
                j++;
                f += getComboHline();
            }

            listarea.popClip();
        }
        ((com.maddox.gwindow.GWindow) (listarea)).root.C.alpha = i;
    }

    public void setupComboList(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        gwindowcombocontrol.listArea.win.dx = gwindowcombocontrol.win.dx;
        gwindowcombocontrol.listCountLines = gwindowcombocontrol.listVisibleLines;
        if(gwindowcombocontrol.listCountLines > gwindowcombocontrol.size())
            gwindowcombocontrol.listCountLines = gwindowcombocontrol.size();
        gwindowcombocontrol.listArea.win.dy = (float)gwindowcombocontrol.listCountLines * getComboHline() + bevelComboUp.B.dy + bevelComboUp.T.dy;
        com.maddox.gwindow.GPoint gpoint = gwindowcombocontrol.windowToGlobal(0.0F, gwindowcombocontrol.win.dy);
        if(gpoint.y + gwindowcombocontrol.listArea.win.dy > gwindowcombocontrol.root.win.dy)
            gwindowcombocontrol.listArea.win.y = -gwindowcombocontrol.listArea.win.dy;
        else
            gwindowcombocontrol.listArea.win.y = gwindowcombocontrol.win.dy;
        gwindowcombocontrol.listArea.win.x = 0.0F;
        if(gwindowcombocontrol.listCountLines < gwindowcombocontrol.size())
        {
            gwindowcombocontrol.scrollBar.win.dx = getVScrollBarW();
            gwindowcombocontrol.scrollBar.win.dy = gwindowcombocontrol.listArea.win.dy;
            gwindowcombocontrol.scrollBar.win.x = gwindowcombocontrol.listArea.win.dx - gwindowcombocontrol.scrollBar.win.dx;
            gwindowcombocontrol.scrollBar.win.y = 0.0F;
            if(gwindowcombocontrol.iSelected >= 0)
            {
                gwindowcombocontrol.listStartLine = gwindowcombocontrol.iSelected;
                if(gwindowcombocontrol.listStartLine + gwindowcombocontrol.listCountLines > gwindowcombocontrol.size())
                    gwindowcombocontrol.listStartLine = gwindowcombocontrol.size() - gwindowcombocontrol.listCountLines;
            } else
            {
                gwindowcombocontrol.listStartLine = 0;
            }
            gwindowcombocontrol.scrollBar.setRange(0.0F, gwindowcombocontrol.size(), gwindowcombocontrol.listCountLines, 1.0F, gwindowcombocontrol.listStartLine);
            gwindowcombocontrol.scrollBar.showWindow();
        } else
        {
            gwindowcombocontrol.scrollBar.hideWindow();
            gwindowcombocontrol.listStartLine = 0;
        }
        gwindowcombocontrol.listSelected = gwindowcombocontrol.iSelected;
        gwindowcombocontrol.listArea.win.dx -= getVScrollBarW();
    }

    public void render(com.maddox.gwindow.GWindowComboControl gwindowcombocontrol)
    {
        int i = gwindowcombocontrol.root.C.alpha;
        gwindowcombocontrol.root.C.alpha = 255;
        gwindowcombocontrol.setCanvasColorWHITE();
        drawBevel(gwindowcombocontrol, 0.0F, 0.0F, gwindowcombocontrol.win.dx - getVScrollBarW(), gwindowcombocontrol.win.dy, bevelComboDown, basicelements);
        gwindowcombocontrol.root.C.alpha = i;
    }

    public void setupComboEditBox(com.maddox.gwindow.GWindowEditBox gwindoweditbox)
    {
        gwindoweditbox.win.x = bevelComboDown.L.dx;
        gwindoweditbox.win.y = bevelComboDown.T.dy;
        gwindoweditbox.win.dx = gwindoweditbox.parentWindow.win.dx - bevelComboDown.L.dx - bevelComboDown.R.dx - getVScrollBarW();
        gwindoweditbox.win.dy = gwindoweditbox.parentWindow.win.dy - bevelComboDown.T.dy - bevelComboDown.B.dy;
    }

    public void setupComboButton(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = CBButtonUP;
        gwindowbuttontexture.texDOWN = CBButtonDOWN;
        gwindowbuttontexture.texDISABLE = CBButtonUP;
        gwindowbuttontexture.texOVER = CBButtonUP;
        gwindowbuttontexture.win.dx = getVScrollBarW();
        gwindowbuttontexture.win.dy = getVScrollBarH();
        gwindowbuttontexture.win.x = gwindowbuttontexture.parentWindow.win.dx - gwindowbuttontexture.win.dx;
        gwindowbuttontexture.win.y = 0.0F;
    }

    public float getComboH()
    {
        return getVScrollBarH() + bevelComboDown.B.dy + bevelComboDown.T.dy;
    }

    public float getComboHmetric()
    {
        return getComboH() / metric();
    }

    public float getComboHline()
    {
        return metric(1.2F);
    }

    public void render(com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar)
    {
        float f = gwindowvscrollbar.yM - gwindowvscrollbar.uButton.win.dy;
        float f1 = gwindowvscrollbar.dButton.win.y - gwindowvscrollbar.yM - gwindowvscrollbar.dyM;
        if(f < 0.0F && f1 < 0.0F)
        {
            return;
        } else
        {
            int i = gwindowvscrollbar.root.C.alpha;
            gwindowvscrollbar.root.C.alpha = 255;
            gwindowvscrollbar.setCanvasColorWHITE();
            gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.uButton.win.dy, gwindowvscrollbar.win.dx, gwindowvscrollbar.win.dy - gwindowvscrollbar.uButton.win.dy - gwindowvscrollbar.dButton.win.dy, buttons, 0.0F, 143F, 32F, 2.0F);
            gwindowvscrollbar.draw(0.0F, gwindowvscrollbar.yM, gwindowvscrollbar.win.dx, gwindowvscrollbar.dyM, buttons, 192F, 112F, 32F, 64F);
            gwindowvscrollbar.root.C.alpha = i;
            return;
        }
    }

    public void setupVScrollBarSizes(com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar)
    {
        float f = gwindowvscrollbar.win.dy - 2.0F * getVScrollBarH();
        boolean flag = f >= 2.0F * metric(minScrollMSize);
        float f1 = getVScrollBarH();
        if(f <= 0.0F)
            f1 = gwindowvscrollbar.win.dy / 2.0F;
        gwindowvscrollbar.uButton.setSize(getVScrollBarW(), f1);
        gwindowvscrollbar.uButton.setPos(0.0F, 0.0F);
        gwindowvscrollbar.dButton.setSize(getVScrollBarW(), f1);
        gwindowvscrollbar.dButton.setPos(0.0F, gwindowvscrollbar.win.dy - f1);
        float f2 = 2.0F * f1;
        if(f2 > f)
        {
            gwindowvscrollbar.dyM = 0.0F;
            gwindowvscrollbar.yM = (gwindowvscrollbar.pos / (gwindowvscrollbar.posMax - gwindowvscrollbar.posMin)) * (gwindowvscrollbar.win.dy - f1);
            if(gwindowvscrollbar.yM < 0.0F)
                gwindowvscrollbar.yM = -gwindowvscrollbar.yM;
            gwindowvscrollbar.mButton.hideWindow();
        } else
        {
            gwindowvscrollbar.dyM = 2.0F * f1;
            gwindowvscrollbar.yM = (gwindowvscrollbar.pos / (gwindowvscrollbar.posMax - gwindowvscrollbar.posMin)) * (gwindowvscrollbar.win.dy - 4F * f1);
            if(gwindowvscrollbar.yM < 0.0F)
                gwindowvscrollbar.yM = -gwindowvscrollbar.yM;
            gwindowvscrollbar.yM += f1;
            gwindowvscrollbar.mButton.showWindow();
            gwindowvscrollbar.mButton.setSize(getVScrollBarW(), 2.0F * f1);
            gwindowvscrollbar.mButton.setPos(0.0F, gwindowvscrollbar.yM);
            gwindowvscrollbar.mButton._bNoRender = true;
        }
    }

    public void render(com.maddox.gwindow.GWindowHScrollBar gwindowhscrollbar)
    {
        float f = gwindowhscrollbar.xM - gwindowhscrollbar.lButton.win.dx;
        float f1 = gwindowhscrollbar.rButton.win.x - gwindowhscrollbar.xM - gwindowhscrollbar.dxM;
        if(f < 0.0F && f1 < 0.0F)
        {
            return;
        } else
        {
            int i = gwindowhscrollbar.root.C.alpha;
            gwindowhscrollbar.root.C.alpha = 255;
            gwindowhscrollbar.setCanvasColorWHITE();
            gwindowhscrollbar.draw(gwindowhscrollbar.lButton.win.dx, 0.0F, gwindowhscrollbar.win.dx - gwindowhscrollbar.lButton.win.dx - gwindowhscrollbar.rButton.win.dx, gwindowhscrollbar.win.dy, buttons, 95F, 144F, 1.0F, 32F);
            gwindowhscrollbar.draw(gwindowhscrollbar.xM, 0.0F, gwindowhscrollbar.dxM, gwindowhscrollbar.win.dy, buttons, 128F, 112F, 64F, 32F);
            gwindowhscrollbar.root.C.alpha = i;
            return;
        }
    }

    public void setupHScrollBarSizes(com.maddox.gwindow.GWindowHScrollBar gwindowhscrollbar)
    {
        float f = gwindowhscrollbar.win.dx - 2.0F * getHScrollBarW();
        boolean flag = f >= 2.0F * metric(minScrollMSize);
        float f1 = getHScrollBarW();
        if(f <= 0.0F)
            f1 = gwindowhscrollbar.win.dx / 2.0F;
        gwindowhscrollbar.lButton.setSize(f1, getHScrollBarH());
        gwindowhscrollbar.lButton.setPos(0.0F, 0.0F);
        gwindowhscrollbar.rButton.setSize(f1, getHScrollBarH());
        gwindowhscrollbar.rButton.setPos(gwindowhscrollbar.win.dx - f1, 0.0F);
        gwindowhscrollbar.mButton._bNoRender = true;
        gwindowhscrollbar.dxM = 2.0F * f1;
        gwindowhscrollbar.xM = (gwindowhscrollbar.pos / (gwindowhscrollbar.posMax - gwindowhscrollbar.posMin)) * (gwindowhscrollbar.win.dx - 4F * f1);
        if(gwindowhscrollbar.xM < 0.0F)
            gwindowhscrollbar.xM = -gwindowhscrollbar.xM;
        gwindowhscrollbar.xM += f1;
        gwindowhscrollbar.mButton.setSize(2.0F * f1, getHScrollBarH());
        gwindowhscrollbar.mButton.setPos(gwindowhscrollbar.xM, 0.0F);
        gwindowhscrollbar.mButton._bNoRender = true;
    }

    public float getHScrollBarW()
    {
        return metric(1.6F);
    }

    public float getHScrollBarH()
    {
        return metric(1.6F);
    }

    public float getVScrollBarW()
    {
        return metric(1.6F);
    }

    public float getVScrollBarH()
    {
        return metric(1.6F);
    }

    public void setupScrollButtonUP(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBupButtonUP;
        gwindowbuttontexture.texDOWN = SBupButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBupButtonDISABLE;
        gwindowbuttontexture.texOVER = SBupButtonOVER;
    }

    public void setupScrollButtonDOWN(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBdownButtonUP;
        gwindowbuttontexture.texDOWN = SBdownButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBdownButtonDISABLE;
        gwindowbuttontexture.texOVER = SBdownButtonOVER;
    }

    public void setupScrollButtonLEFT(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBleftButtonUP;
        gwindowbuttontexture.texDOWN = SBleftButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBleftButtonDISABLE;
        gwindowbuttontexture.texOVER = SBleftButtonOVER;
    }

    public void setupScrollButtonRIGHT(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        gwindowbuttontexture.texUP = SBrightButtonUP;
        gwindowbuttontexture.texDOWN = SBrightButtonDOWN;
        gwindowbuttontexture.texDISABLE = SBrightButtonDISABLE;
        gwindowbuttontexture.texOVER = SBrightButtonOVER;
    }

    public void render(com.maddox.gwindow.GWindowButton gwindowbutton)
    {
        int i = gwindowbutton.root.C.alpha;
        gwindowbutton.root.C.alpha = 255;
        gwindowbutton.setCanvasColorWHITE();
        if(!gwindowbutton.bEnable)
        {
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonUP, basicelements);
            if(gwindowbutton.cap != null)
            {
                com.maddox.gwindow.GRegion gregion = gwindowbutton.getClientRegion();
                float f = gregion.dx;
                float f2 = gregion.dy;
                if(gwindowbutton.pushClipRegion(gregion, gwindowbutton.bClip, 0.0F))
                {
                    renderTextDialogControl(gwindowbutton, 1.0F, 1.0F, f, f2, 0xffffff, false);
                    renderTextDialogControl(gwindowbutton, 0.0F, 0.0F, f, f2, 0x7f7f7f, false);
                    gwindowbutton.popClip();
                }
            }
            gwindowbutton.root.C.alpha = i;
            return;
        }
        if(gwindowbutton.bDown && !gwindowbutton.bDrawOnlyUP)
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonDOWN, basicelements);
        else
            drawBevel(gwindowbutton, 0.0F, 0.0F, gwindowbutton.win.dx, gwindowbutton.win.dy, bevelButtonUP, basicelements);
        if(gwindowbutton.cap != null)
        {
            com.maddox.gwindow.GRegion gregion1 = gwindowbutton.getClientRegion();
            float f1 = gregion1.dx;
            float f3 = gregion1.dy;
            if(gwindowbutton.pushClipRegion(gregion1, gwindowbutton.bClip, 0.0F))
            {
                if(gwindowbutton.bDown)
                    renderTextDialogControl(gwindowbutton, 1.0F, 1.0F, f1, f3, gwindowbutton.color, gwindowbutton.isDefault());
                else
                    renderTextDialogControl(gwindowbutton, 0.0F, 0.0F, f1, f3, gwindowbutton.color, gwindowbutton.isDefault());
                gwindowbutton.popClip();
            }
        }
        gwindowbutton.root.C.alpha = i;
    }

    public void render(com.maddox.gwindow.GWindowButtonTexture gwindowbuttontexture)
    {
        int i = gwindowbuttontexture.root.C.alpha;
        gwindowbuttontexture.root.C.alpha = 255;
        super.render(gwindowbuttontexture);
        gwindowbuttontexture.root.C.alpha = i;
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowButton gwindowbutton, com.maddox.gwindow.GSize gsize)
    {
        gsize = getMinSizeDialogControl(gwindowbutton, gsize);
        gsize.dx += bevelButtonUP.L.dx / 2.0F + bevelButtonUP.R.dx / 2.0F + 2.0F * metric(spaceButton);
        gsize.dy += bevelButtonUP.T.dy / 2.0F + bevelButtonUP.B.dy / 2.0F + 2.0F * metric(spaceButton);
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowButton gwindowbutton, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion = getClientRegionDialogControl(gwindowbutton, gregion, f);
        gregion.x += bevelButtonUP.L.dx / 2.0F;
        gregion.y += bevelButtonUP.T.dy / 2.0F;
        gregion.dx -= bevelButtonUP.L.dx / 2.0F + bevelButtonUP.R.dx / 2.0F;
        gregion.dy -= bevelButtonUP.T.dy / 2.0F + bevelButtonUP.B.dy / 2.0F;
        return gregion;
    }

    public void render(com.maddox.gwindow.GWindowClient gwindowclient)
    {
        gwindowclient.setCanvasColorWHITE();
        gwindowclient.draw(0.0F, 0.0F, gwindowclient.win.dx, gwindowclient.win.dy, basicelements, bevelComboUp.Area);
    }

    public void setupFrameCloseBox(com.maddox.gwindow.GWindowFrameCloseBox gwindowframeclosebox)
    {
        gwindowframeclosebox.texUP = closeBoxUP;
        gwindowframeclosebox.texDOWN = closeBoxDOWN;
        gwindowframeclosebox.texDISABLE = closeBoxDISABLE;
        gwindowframeclosebox.texOVER = closeBoxOVER;
    }

    public void frameSetCloseBoxPos(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        gwindowframed.closeBox.hideWindow();
    }

    public int frameHitTest(com.maddox.gwindow.GWindowFramed gwindowframed, float f, float f1)
    {
        com.maddox.gwindow.GRegion gregion = gwindowframed.getClientRegion();
        if(f < 0.0F || f > gwindowframed.win.dx || f1 < 0.0F || f1 > gwindowframed.win.dy)
            return 0;
        if(f >= gregion.x && f < gregion.x + gregion.dx && f1 >= gregion.y && f1 < gregion.y + gregion.dy)
            return 0;
        if(f < gregion.x)
        {
            if(f1 < bevelButtonUP.T.dy)
                return 1;
            return f1 < gregion.y + gregion.dy ? 4 : 6;
        }
        if(f >= gregion.x + gregion.dx)
        {
            if(f1 < bevelButtonUP.T.dy)
                return 3;
            return f1 < gregion.y + gregion.dy ? 5 : 8;
        }
        if(f1 < bevelButtonUP.T.dy)
            return 2;
        return f1 < gregion.y + gregion.dy ? 9 : 7;
    }

    public void render(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        float f = framedTitleHeight(gwindowframed);
        gwindowframed.setCanvasColorWHITE();
        drawBevel(gwindowframed, 0.0F, f, gwindowframed.win.dx, gwindowframed.win.dy - f, bevelComboUp, basicelements, false);
        com.maddox.gwindow.GBevel gbevel = bevelButtonUP;
        drawBevel(gwindowframed, 0.0F, 0.0F, gwindowframed.win.dx, f, gbevel, basicelements);
        if(gwindowframed.title != null)
        {
            if(!gwindowframed.isActivated())
                gwindowframed.setCanvasColor(0xc0c0c0);
            gwindowframed.setCanvasFont(1);
            _titleRegion.x = bevelButtonUP.L.dx;
            _titleRegion.y = bevelButtonUP.T.dy;
            if(gwindowframed.closeBox != null)
                _titleRegion.dx = gwindowframed.win.dx - bevelButtonUP.L.dx - bevelButtonUP.R.dx - metric();
            else
                _titleRegion.dx = gwindowframed.win.dx - bevelButtonUP.L.dx - bevelButtonUP.R.dx;
            _titleRegion.dy = f - bevelButtonUP.T.dy - bevelButtonUP.B.dy;
            if(gwindowframed.pushClipRegion(_titleRegion, gwindowframed.bClip, metric(spaceFramedTitle)))
            {
                gwindowframed.draw(0.0F, 0.0F, gwindowframed.title);
                gwindowframed.popClip();
            }
        }
    }

    private float framedTitleHeight(com.maddox.gwindow.GWindowFramed gwindowframed)
    {
        float f = metric();
        if(f > gwindowframed.root.textFonts[1].height)
            f = gwindowframed.root.textFonts[1].height;
        if(gwindowframed.title != null && gwindowframed.title.length() > 0)
            return f + 2.0F * metric(spaceFramedTitle) + bevelButtonUP.T.dy + bevelButtonUP.B.dy;
        else
            return bevelButtonUP.T.dy + bevelButtonUP.B.dy;
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GWindowFramed gwindowframed, com.maddox.gwindow.GSize gsize)
    {
        float f = 50F;
        float f1 = 50F;
        if(gwindowframed.clientWindow != null)
        {
            com.maddox.gwindow.GSize gsize1 = gwindowframed.clientWindow.getMinSize();
            f = gsize1.dx;
            f1 = gsize1.dy;
        }
        if(gwindowframed.menuBar != null)
        {
            com.maddox.gwindow.GSize gsize2 = gwindowframed.menuBar.getMinSize();
            f1 += gsize2.dy;
        }
        f += bevelComboUp.L.dx + bevelComboUp.R.dx;
        f1 += bevelComboUp.T.dy + bevelComboUp.B.dy + framedTitleHeight(gwindowframed);
        gsize.set(f, f1);
        return gsize;
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GWindowFramed gwindowframed, com.maddox.gwindow.GRegion gregion, float f)
    {
        gregion.x = bevelComboUp.L.dx + f;
        gregion.y = framedTitleHeight(gwindowframed) + bevelFW.T.dy + f;
        if(gwindowframed.menuBar != null)
            gregion.y += gwindowframed.menuBar.win.dy;
        gregion.dx = gwindowframed.win.dx - gregion.x - bevelComboUp.R.dx - f;
        gregion.dy = gwindowframed.win.dy - gregion.y - bevelComboUp.B.dy - f;
        return gregion;
    }

    public java.lang.String i18n(java.lang.String s)
    {
        return com.maddox.il2.game.I18N.gwindow(s);
    }

    public void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        buttons = com.maddox.gwindow.GTexture.New("GUI/game/buttons.mat");
        buttons2 = com.maddox.gwindow.GTexture.New("GUI/game/buttons2.mat");
        basicelements = com.maddox.gwindow.GTexture.New("GUI/game/basicelements.mat");
        bevelGreen = new GBevel();
        bevelGreen.set(new GRegion(160F, 192F, 64F, 64F), new GRegion(191F, 223F, 2.0F, 2.0F));
        bevelRed = new GBevel();
        bevelRed.set(new GRegion(64F, 192F, 32F, 32F), new GRegion(69F, 197F, 22F, 22F));
        bevelBlacked = new GBevel();
        bevelBlacked.set(new GRegion(32F, 192F, 32F, 32F), new GRegion(41F, 201F, 14F, 14F));
        bevelComboUp = new GBevel();
        bevelComboUp.set(new GRegion(32F, 224F, 16F, 16F), new GRegion(36F, 228F, 8F, 8F));
        bevelComboDown = new GBevel();
        bevelComboDown.set(new GRegion(32F, 240F, 16F, 16F), new GRegion(35F, 243F, 10F, 10F));
        bevelButtonUP = new GBevel();
        bevelButtonUP.set(new GRegion(160F, 160F, 32F, 32F), new GRegion(172F, 172F, 8F, 8F));
        bevelButtonDOWN = new GBevel();
        bevelButtonDOWN.set(new GRegion(192F, 160F, 32F, 32F), new GRegion(204F, 172F, 8F, 8F));
        bevelAward = new GBevel();
        bevelAward.set(new GRegion(160F, 160F, 32F, 32F), new GRegion(165F, 166F, 21F, 20F));
        elements = com.maddox.gwindow.GTexture.New("GUI/game/elements.mat");
        elementsStreched = com.maddox.gwindow.GTexture.New("GUI/game/elementss.mat");
        cursors = com.maddox.gwindow.GTexture.New("GUI/game/cursors.mat");
        cursorsStreched = com.maddox.gwindow.GTexture.New("GUI/game/cursorss.mat");
        regionWhite = new GTexRegion(elements, 5F, 17F, 1.0F, 1.0F);
        bevelUP.set(new GRegion(4F, 16F, 16F, 16F), new GRegion(6F, 18F, 12F, 12F));
        bevelTabDialogClient = bevelUP;
        bevelDOWN.set(new GRegion(52F, 37F, 16F, 16F), new GRegion(54F, 39F, 12F, 12F));
        bevelUPsmall.set(new GRegion(19F, 60F, 11F, 10F), new GRegion(20F, 61F, 9F, 8F));
        bevelDOWNsmall.set(new GRegion(19F, 71F, 10F, 9F), new GRegion(20F, 72F, 8F, 7F));
        bevelFW.set(new GRegion(0.0F, 16F, 128F, 112F), new GRegion(4F, 16F, 120F, 108F));
        bevelTitleActive.set(new GRegion(0.0F, 0.0F, 128F, 16F), new GRegion(4F, 4F, 120F, 11F));
        bevelTitleInactive.set(new GRegion(70F, 18F, 53F, 16F), new GRegion(74F, 22F, 45F, 11F));
        bevelFW.Area.dx = 0.0F;
        bevelBlack.set(new GRegion(4F, 98F, 4F, 4F), new GRegion(5F, 99F, 2.0F, 2.0F));
        bevelBlack.Area.dx = 0.0F;
        bevelBlack50.set(new GRegion(32F, 16F, 12F, 10F), new GRegion(33F, 17F, 10F, 8F));
        bevelBlack50.Area.dx = 0.0F;
        bevelSeparate.set(new GRegion(87F, 69F, 8F, 7F), new GRegion(89F, 71F, 4F, 3F));
        bevelSeparate.Area.dx = 0.0F;
        bevelTabCUR.set(new GRegion(4F, 82F, 53F, 15F), new GRegion(8F, 86F, 45F, 8F));
        bevelTabCUR.Area.dx = 0.0F;
        bevelTab.set(new GRegion(57F, 82F, 54F, 13F), new GRegion(61F, 87F, 46F, 8F));
        bevelTab.Area.dx = 0.0F;
        closeBoxUP = new GTexRegion(elementsStreched, 4F, 32F, 10F, 9F);
        closeBoxDOWN = new GTexRegion(elementsStreched, 4F, 43F, 10F, 9F);
        closeBoxDISABLE = closeBoxOVER = closeBoxUP;
        SBupButtonUP = new GTexRegion(buttons, 0.0F, 112F, 32F, 32F);
        SBupButtonDOWN = new GTexRegion(buttons, 32F, 112F, 32F, 32F);
        SBupButtonDISABLE = SBupButtonOVER = SBupButtonUP;
        SBdownButtonUP = new GTexRegion(buttons, 0.0F, 144F, 32F, 32F);
        SBdownButtonDOWN = new GTexRegion(buttons, 32F, 144F, 32F, 32F);
        SBdownButtonDISABLE = SBdownButtonOVER = SBdownButtonUP;
        SBleftButtonUP = new GTexRegion(buttons, 64F, 144F, 32F, 32F);
        SBleftButtonDOWN = new GTexRegion(buttons, 96F, 144F, 32F, 32F);
        SBleftButtonDISABLE = SBleftButtonOVER = SBleftButtonUP;
        SBrightButtonUP = new GTexRegion(buttons, 64F, 112F, 32F, 32F);
        SBrightButtonDOWN = new GTexRegion(buttons, 96F, 112F, 32F, 32F);
        SBrightButtonDISABLE = SBrightButtonOVER = SBrightButtonUP;
        CBButtonUP = new GTexRegion(buttons, 128F, 144F, 32F, 32F);
        CBButtonDOWN = new GTexRegion(buttons, 160F, 144F, 32F, 32F);
        checkBoxCheckEnable = new GTexRegion(elementsStreched, 32F, 64F, 13F, 13F);
        checkBoxCheckDisable = new GTexRegion(elementsStreched, 45F, 64F, 13F, 13F);
        checkBoxUnCheckEnable = new GTexRegion(elementsStreched, 58F, 64F, 13F, 13F);
        checkBoxUnCheckDisable = new GTexRegion(elementsStreched, 71F, 64F, 13F, 13F);
        gwindowroot.textFonts[0] = com.maddox.gwindow.GFont.New("arial10");
        gwindowroot.textFonts[1] = com.maddox.gwindow.GFont.New("arialb10");
        metric = (int)(gwindowroot.textFonts[0].height + 0.5F);
        gwindowroot.mouseCursors[0] = new GCursorTexRegion(cursors, 32F, 96F, 0.0F, 0.0F, 0.0F, 0.0F, 0);
        gwindowroot.mouseCursors[1] = new GCursorTexRegion(cursors, 0.0F, 0.0F, 32F, 32F, 5F, 4F, 1);
        gwindowroot.mouseCursors[2] = new GCursorTexRegion(cursors, 32F, 0.0F, 32F, 32F, 15F, 15F, 2);
        gwindowroot.mouseCursors[3] = new GCursorTexRegion(cursors, 64F, 0.0F, 32F, 32F, 5F, 4F, 3);
        gwindowroot.mouseCursors[4] = new GCursorTexRegion(cursors, 96F, 0.0F, 32F, 32F, 5F, 4F, 4);
        gwindowroot.mouseCursors[5] = new GCursorTexRegion(cursors, 0.0F, 32F, 32F, 32F, 14F, 15F, 5);
        gwindowroot.mouseCursors[6] = new GCursorTexRegion(cursors, 32F, 32F, 32F, 32F, 5F, 4F, 6);
        gwindowroot.mouseCursors[7] = new GCursorTexRegion(cursors, 64F, 32F, 32F, 32F, 5F, 4F, 7);
        gwindowroot.mouseCursors[8] = new GCursorTexRegion(cursors, 96F, 32F, 32F, 32F, 5F, 4F, 8);
        gwindowroot.mouseCursors[9] = new GCursorTexRegion(cursors, 0.0F, 64F, 32F, 32F, 5F, 4F, 9);
        gwindowroot.mouseCursors[10] = new GCursorTexRegion(cursors, 32F, 64F, 32F, 32F, 5F, 4F, 10);
        gwindowroot.mouseCursors[11] = new GCursorTexRegion(cursors, 64F, 64F, 32F, 32F, 5F, 4F, 11);
        gwindowroot.mouseCursors[12] = new GCursorTexRegion(cursors, 96F, 64F, 32F, 32F, 5F, 4F, 12);
        gwindowroot.mouseCursors[13] = new GCursorTexRegion(cursors, 0.0F, 96F, 32F, 32F, 5F, 4F, 13);
        selectMenuIcon = new GTexRegion(cursorsStreched, 116F, 104F, 12F, 12F);
        subMenuIcon = new GTexRegion(cursorsStreched, 116F, 116F, 12F, 12F);
    }

    public com.maddox.gwindow.GTexture buttons;
    public com.maddox.gwindow.GTexture buttons2;
    public com.maddox.gwindow.GTexture basicelements;
    public com.maddox.gwindow.GBevel bevelGreen;
    public com.maddox.gwindow.GBevel bevelRed;
    public com.maddox.gwindow.GBevel bevelBlacked;
    public com.maddox.gwindow.GBevel bevelComboDown;
    public com.maddox.gwindow.GBevel bevelComboUp;
    public com.maddox.gwindow.GBevel bevelButtonDOWN;
    public com.maddox.gwindow.GBevel bevelButtonUP;
    public com.maddox.gwindow.GBevel bevelAward;
    protected com.maddox.sound.Sample sndKey1;
    protected com.maddox.sound.Sample sndKey2;
    protected com.maddox.sound.SoundFX guiSound;
    public java.util.HashMap sounds;
    public com.maddox.gwindow.GTexRegion CBButtonUP;
    public com.maddox.gwindow.GTexRegion CBButtonDOWN;
    float spaceComboList;
    float minScrollMSize;
    float spaceButton;
    float spaceFramedTitle;
    private com.maddox.gwindow.GRegion _titleRegion;
}
