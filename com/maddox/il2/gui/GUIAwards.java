// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIAwards.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.RTSConf;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUIAwards extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bPrev)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
            setCanvasColorWHITE();
            setCanvasFont(0);
            float f = root.C.font.height;
            draw(x1024(96F), y1024(658F), x1024(320F), y1024(48F), 0, i18n("awards.Back"));
            if(texIcons != null)
            {
                com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
                com.maddox.gwindow.GBevel gbevel = guilookandfeel.bevelAward;
                float f1 = 40F;
                float f2 = 40F;
                float f3 = 208F;
                float f4 = 240F;
                float f5 = 288F;
                int i = 0;
                for(int j = 0; j < 2; j++)
                {
                    for(int k = 0; k < 4; k++)
                    {
                        if(i == texIcons.length)
                            return;
                        if(texIcons[i] != null)
                        {
                            guilookandfeel.drawBevel(this, x1024(f1 + (float)k * f4) - gbevel.L.dx, y1024(f2 + (float)j * f5) - gbevel.T.dy, x1024(224F) + gbevel.L.dx + gbevel.R.dx, y1024(192F) + gbevel.T.dy + gbevel.B.dy, gbevel, guilookandfeel.basicelements, false);
                            draw(x1024(f1 + (float)k * f4), y1024(f2 + (float)j * f5), x1024(224F), y1024(192F), texIcons[i]);
                        }
                        if(text[i] != null)
                            drawLines(x1024(f1 + (float)k * f4), y1024(f2 + (float)j * f5 + f3), text[i], 0, text[i].length(), x1024(224F), f, 4);
                        i++;
                    }

                }

            }
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bPrev.setPosC(x1024(56F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        if(indexIcons != null)
        {
            texIcons = new com.maddox.gwindow.GTexture[indexIcons.length];
            if(com.maddox.il2.game.Main.cur().campaign.branch().equals("de") && com.maddox.il2.ai.World.cur().isHakenAllowed())
            {
                for(int i = 0; i < indexIcons.length; i++)
                    texIcons[i] = com.maddox.gwindow.GTexture.New("missions/campaign/de/awardh" + indexIcons[i] + ".mat");

            } else
            if(com.maddox.il2.game.Main.cur().campaign.branch().equals("fi") && com.maddox.il2.ai.World.cur().isHakenAllowed())
            {
                for(int j = 0; j < indexIcons.length; j++)
                    texIcons[j] = com.maddox.gwindow.GTexture.New("missions/campaign/fi/awardh" + indexIcons[j] + ".mat");

            } else
            {
                for(int k = 0; k < indexIcons.length; k++)
                    texIcons[k] = com.maddox.gwindow.GTexture.New("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/award" + indexIcons[k] + ".mat");

            }
            text = new java.lang.String[indexIcons.length];
            try
            {
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("missions/campaign/" + com.maddox.il2.game.Main.cur().campaign.branch() + "/award", com.maddox.rts.RTSConf.cur.locale);
                for(int l = 0; l < indexIcons.length; l++)
                    text[l] = resourcebundle.getString("" + indexIcons[l]);

            }
            catch(java.lang.Exception exception) { }
        }
        client.activateWindow();
    }

    public void _leave()
    {
        texIcons = null;
        client.hideWindow();
    }

    public GUIAwards(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(32);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("awards.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bPrev;
    public static int indexIcons[];
    public com.maddox.gwindow.GTexture texIcons[];
    public java.lang.String text[];
}
