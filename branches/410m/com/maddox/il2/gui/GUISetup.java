// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetup.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUISetup extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bVideo)
            {
                com.maddox.il2.game.Main.stateStack().push(12);
                return true;
            }
            if(gwindow == b3d)
            {
                com.maddox.il2.game.Main.stateStack().push(11);
                return true;
            }
            if(gwindow == bSound)
            {
                com.maddox.il2.game.Main.stateStack().push(13);
                return true;
            }
            if(gwindow == bInput)
            {
                com.maddox.il2.game.Main.stateStack().push(53);
                return true;
            }
            if(gwindow == bNet)
            {
                com.maddox.il2.game.Main.stateStack().push(52);
                return true;
            }
            if(gwindow == bExit)
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(320F), x1024(306F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(112F), y1024(64F), x1024(208F), y1024(48F), 0, i18n("setup.VideoModes"));
            draw(x1024(112F), y1024(112F), x1024(208F), y1024(48F), 0, i18n("setup.VideoOptions"));
            draw(x1024(112F), y1024(160F), x1024(208F), y1024(48F), 0, i18n("setup.SoundSetup"));
            draw(x1024(112F), y1024(208F), x1024(208F), y1024(48F), 0, i18n("setup.Input"));
            draw(x1024(112F), y1024(256F), x1024(208F), y1024(48F), 0, i18n("setup.Network"));
            draw(x1024(112F), y1024(336F), x1024(208F), y1024(48F), 0, i18n("setup.Back"));
        }

        public void setPosSize()
        {
            set1024PosSize(368F, 207F, 368F, 416F);
            bVideo.setPosC(x1024(64F), y1024(88F));
            b3d.setPosC(x1024(64F), y1024(136F));
            bSound.setPosC(x1024(64F), y1024(184F));
            bInput.setPosC(x1024(64F), y1024(232F));
            bNet.setPosC(x1024(64F), y1024(280F));
            bExit.setPosC(x1024(64F), y1024(360F));
        }

        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUISetup(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(10);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setup.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bVideo = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        b3d = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bSound = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bInput = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNet = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bVideo;
    public com.maddox.il2.gui.GUIButton b3d;
    public com.maddox.il2.gui.GUIButton bSound;
    public com.maddox.il2.gui.GUIButton bInput;
    public com.maddox.il2.gui.GUIButton bNet;
    public com.maddox.il2.gui.GUIButton bExit;
}
