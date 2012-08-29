// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUISetupNet.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUISwitchBox2, GUIButton, GUIDialogClient, GUISeparate

public class GUISetupNet extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == comboSpeed)
            {
                int k = comboSpeed.getSelected();
                if(k < 0)
                {
                    return true;
                } else
                {
                    int l = speed[k];
                    setSpeed(l);
                    return true;
                }
            }
            if(gwindow == bExit)
            {
                boolean flag = sSkinOn.isChecked();
                if(com.maddox.il2.engine.Config.cur.netSkinDownload != flag)
                    com.maddox.il2.engine.Config.cur.netSkinDownload = flag;
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
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(176F), x1024(512F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(32F), y1024(32F), x1024(208F), y1024(32F), 0, i18n("setupNet.Internet"));
            draw(x1024(32F), y1024(96F), x1024(432F), y1024(48F), 2, i18n("setupNet.Skin"));
            draw(x1024(88F), y1024(208F), x1024(136F), y1024(48F), 0, i18n("setupNet.Back"));
        }

        public void setPosSize()
        {
            set1024PosSize(224F, 176F, 576F, 288F);
            comboSpeed.set1024PosSize(256F, 32F, 272F, 32F);
            sSkinOn.setPosC(x1024(520F), y1024(120F));
            bExit.setPosC(x1024(56F), y1024(232F));
        }

        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        setComboSpeed();
        sSkinOn.setChecked(com.maddox.il2.engine.Config.cur.netSkinDownload, false);
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private void setComboSpeed()
    {
        int i = com.maddox.il2.engine.Config.cur.netSpeed;
        int j;
        for(j = 0; j < speed.length - 1; j++)
            if(i < (speed[j] + speed[j + 1]) / 2)
                break;

        if(j == speed.length - 1)
            j = speed.length - 2;
        comboSpeed.setSelected(j, true, false);
    }

    private void setSpeed(int i)
    {
        if(com.maddox.il2.engine.Config.cur.netSpeed == i)
            return;
        com.maddox.il2.engine.Config.cur.netSpeed = i;
        java.util.List list = com.maddox.rts.NetEnv.socketsBlock();
        for(int j = 0; j < list.size(); j++)
        {
            com.maddox.rts.NetSocket netsocket = (com.maddox.rts.NetSocket)list.get(j);
            netsocket.setMaxSpeed((float)i / 1000F);
        }

        list = com.maddox.rts.NetEnv.socketsNoBlock();
        for(int k = 0; k < list.size(); k++)
        {
            com.maddox.rts.NetSocket netsocket1 = (com.maddox.rts.NetSocket)list.get(k);
            netsocket1.setMaxSpeed((float)i / 1000F);
        }

        list = com.maddox.rts.NetEnv.channels();
        for(int l = 0; l < list.size(); l++)
        {
            com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(l);
            if(netchannel.isReady())
                netchannel.setMaxSpeed((float)i / 1000F);
        }

    }

    public GUISetupNet(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(52);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("setupNet.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        dialogClient.addControl(comboSpeed = new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        comboSpeed.setEditable(false);
        comboSpeed.resized();
        comboSpeed.add(i18n("setupNet.Modem(9.6K)"));
        comboSpeed.add(i18n("setupNet.Modem(14.4K)"));
        comboSpeed.add(i18n("setupNet.Modem(28.8K)"));
        comboSpeed.add(i18n("setupNet.Modem(56K)"));
        comboSpeed.add(i18n("setupNet.ISDN"));
        comboSpeed.add(i18n("setupNet.Cable,xDSL"));
        comboSpeed.add(i18n("setupNet.LAN"));
        sSkinOn = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GWindowComboControl comboSpeed;
    public com.maddox.il2.gui.GUISwitchBox2 sSkinOn;
    public com.maddox.il2.gui.GUIButton bExit;
    private int speed[] = {
        900, 1500, 3000, 5000, 10000, 25000, 0x186a0, 0x30d40
    };


}
