// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIView.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUIObjectInspector, GUISeparate

public class GUIView extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bAir)
            {
                com.maddox.il2.gui.GUIObjectInspector.type = "air";
                com.maddox.il2.game.Main.stateStack().push(22);
                return true;
            }
            if(gwindow == bTank)
            {
                com.maddox.il2.gui.GUIObjectInspector.type = "tanks";
                com.maddox.il2.game.Main.stateStack().push(22);
                return true;
            }
            if(gwindow == bVechicle)
            {
                com.maddox.il2.gui.GUIObjectInspector.type = "vehicle";
                com.maddox.il2.game.Main.stateStack().push(22);
                return true;
            }
            if(gwindow == bChip)
            {
                com.maddox.il2.gui.GUIObjectInspector.type = "ship";
                com.maddox.il2.game.Main.stateStack().push(22);
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(368F), x1024(320F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(118F), y1024(56F) - M(1.0F), x1024(224F), M(2.0F), 0, i18n("view.Aircraft"));
            draw(x1024(118F), y1024(120F) - M(1.0F), x1024(224F), M(2.0F), 0, i18n("view.Tanks"));
            draw(x1024(118F), y1024(184F) - M(1.0F), x1024(224F), M(2.0F), 0, i18n("view.Vehicles"));
            draw(x1024(118F), y1024(248F) - M(1.0F), x1024(224F), M(2.0F), 0, i18n("view.Ships"));
            draw(x1024(118F), y1024(425F) - M(1.0F), x1024(224F), M(2.0F), 0, i18n("view.MainMenu"));
        }

        public void setPosSize()
        {
            set1024PosSize(334F, 176F, 384F, 480F);
            bAir.setPosC(x1024(70F), y1024(56F));
            bTank.setPosC(x1024(70F), y1024(120F));
            bVechicle.setPosC(x1024(70F), y1024(184F));
            bChip.setPosC(x1024(70F), y1024(248F));
            bExit.setPosC(x1024(70F), y1024(425F));
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

    public GUIView(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(15);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("view.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bAir = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bTank = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bVechicle = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bChip = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bAir;
    public com.maddox.il2.gui.GUIButton bTank;
    public com.maddox.il2.gui.GUIButton bVechicle;
    public com.maddox.il2.gui.GUIButton bChip;
    public com.maddox.il2.gui.GUIButton bExit;
}
