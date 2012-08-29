// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServer.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSocket;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUI, GUIDialogClient, GUISeparate

public class GUINetServer extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                com.maddox.il2.gui.GUINetServer.exitServer(true);
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
            draw(x1024(112F), y1024(336F), x1024(208F), y1024(48F), 0, "Back");
        }

        public void setPosSize()
        {
            set1024PosSize(368F, 207F, 368F, 416F);
            bExit.setPosC(x1024(64F), y1024(360F));
        }

        public DialogClient()
        {
        }
    }


    public static void exitServer(boolean flag)
    {
        _bPopState = flag;
        new com.maddox.gwindow.GWindowMessageBox(__client.root, 20F, true, com.maddox.il2.game.I18N.gui("main.ConfirmQuit"), com.maddox.il2.game.I18N.gui("main.ReallyQuit"), 1, 0.0F) {

            public void result(int i)
            {
                if(i == 3)
                    com.maddox.il2.gui.GUINetServer._exitServer(com.maddox.il2.gui.GUINetServer._bPopState);
            }

        }
;
    }

    private static void _exitServer(boolean flag)
    {
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().destroy();
        if(com.maddox.il2.game.Main.cur().netServerParams != null)
            com.maddox.il2.game.Main.cur().netServerParams.destroy();
        if(com.maddox.il2.game.Main.cur().chat != null)
            com.maddox.il2.game.Main.cur().chat.destroy();
        if(com.maddox.rts.NetEnv.cur().control != null)
            com.maddox.rts.NetEnv.cur().control.destroy();
        java.util.ArrayList arraylist = new ArrayList(com.maddox.rts.NetEnv.channels());
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)arraylist.get(i);
            if(netchannel != null)
                netchannel.destroy();
        }

        com.maddox.rts.CmdEnv.top().exec("socket LISTENER 0");
        int j = com.maddox.rts.NetEnv.socketsBlock().size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.rts.NetSocket netsocket = (com.maddox.rts.NetSocket)com.maddox.rts.NetEnv.socketsBlock().get(k);
            netsocket.maxChannels = 0;
        }

        j = com.maddox.rts.NetEnv.socketsNoBlock().size();
        for(int l = 0; l < j; l++)
        {
            com.maddox.rts.NetSocket netsocket1 = (com.maddox.rts.NetSocket)com.maddox.rts.NetEnv.socketsNoBlock().get(l);
            netsocket1.maxChannels = 0;
        }

        com.maddox.il2.gui.GUI.activate();
        if(flag)
            com.maddox.il2.game.Main.stateStack().pop();
    }

    public void _enter()
    {
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUINetServer(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(37);
        __client = client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = "Server";
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bExit;
    private static com.maddox.il2.gui.GUIClient __client;
    private static boolean _bPopState;


}
