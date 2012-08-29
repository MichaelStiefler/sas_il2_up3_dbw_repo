// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISingleComplete.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CmdEnv;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUI, GUIDialogClient, GUISeparate

public class GUISingleComplete extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                com.maddox.rts.CmdEnv.top().exec("mission END");
                com.maddox.il2.game.Main.stateStack().pop();
                com.maddox.il2.game.Main.stateStack().change(6);
                return true;
            }
            if(gwindow == bBack)
            {
                client.hideWindow();
                com.maddox.il2.game.Main.stateStack().pop();
                com.maddox.il2.gui.GUI.unActivate();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(303F), x1024(288F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(416F), x1024(288F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            if(com.maddox.il2.ai.World.cur().targetsGuard.isTaskComplete())
                draw(x1024(96F), y1024(32F), x1024(224F), y1024(48F), 0, i18n("single.Complete"));
            else
                draw(x1024(96F), y1024(32F), x1024(224F), y1024(48F), 0, i18n("single.Failed"));
            draw(x1024(96F), y1024(336F), x1024(224F), y1024(48F), 0, i18n("single.Quit"));
            draw(x1024(96F), y1024(448F), x1024(224F), y1024(48F), 0, i18n("single.Return2Miss"));
        }

        public void setPosSize()
        {
            set1024PosSize(350F, 112F, 352F, 528F);
            bExit.setPosC(x1024(56F), y1024(360F));
            bBack.setPosC(x1024(56F), y1024(472F));
        }

        public DialogClient()
        {
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.gui.GUI.activate();
        client.showWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUISingleComplete(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(19);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("single.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bBack = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bExit;
    public com.maddox.il2.gui.GUIButton bBack;
}
