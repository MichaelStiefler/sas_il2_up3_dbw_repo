// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetClientGuard.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.il2.ai.Front;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetChannelListener;
import com.maddox.il2.net.NetMissionListener;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.gui:
//            GUI, GUIPad

public class GUINetClientGuard
    implements com.maddox.il2.net.NetChannelListener, com.maddox.il2.net.NetMissionListener
{
    public static class DestroyExec
    {

        public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard)
        {
        }

        public DestroyExec()
        {
        }
    }


    public void netChannelCanceled(java.lang.String s)
    {
    }

    public void netChannelCreated(com.maddox.rts.NetChannel netchannel)
    {
    }

    public void netChannelRequest(java.lang.String s)
    {
    }

    public void netChannelDestroying(com.maddox.rts.NetChannel netchannel, java.lang.String s)
    {
        if(curMessageBox != null)
        {
            curMessageBox.hideWindow();
            curMessageBox = null;
        }
        if(closeConnectionMessageBox != null)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.masterChannel() != netchannel)
            return;
        com.maddox.il2.gui.GUI.activate();
        com.maddox.il2.gui.GUI.pad.leave(true);
        com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
        com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
        closeConnectionMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, com.maddox.il2.game.I18N.gui("netcg.Close"), s, 3, 0.0F) {

            public void result(int i)
            {
                closeConnectionMessageBox = null;
                destroy(false);
            }

        }
;
        if(com.maddox.rts.BackgroundTask.isExecuted())
            com.maddox.rts.BackgroundTask.cancel(s);
    }

    public void netMissionState(int i, float f, java.lang.String s)
    {
        int j = lastNetMissionState;
        lastNetMissionState = i;
        lastNetMissionPercent = f;
        s = com.maddox.il2.game.I18N.gui(s);
        lastNetMissionInfo = s;
        switch(i)
        {
        case 9: // '\t'
            return;

        case 0: // '\0'
            lastNetMissionName = s;
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.StartTransfer") + " " + s;
            if(com.maddox.il2.game.Main.state().id() == 51)
                com.maddox.il2.game.Main.stateStack().change(36);
            break;

        case 1: // '\001'
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.Transfer");
            break;

        case 2: // '\002'
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.TransferObjects");
            break;

        case 3: // '\003'
            lastNetMissionInfo = "" + f + "% " + s;
            break;

        case 4: // '\004'
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.ERROR") + ": " + s;
            if(closeConnectionMessageBox == null)
            {
                com.maddox.il2.gui.GUI.activate();
                com.maddox.il2.gui.GUI.pad.leave(true);
                com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
                if(curMessageBox != null)
                {
                    curMessageBox.hideWindow();
                    curMessageBox = null;
                }
                closeConnectionMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, com.maddox.il2.game.I18N.gui("netcg.Close"), com.maddox.il2.game.I18N.gui("netcg.Mission_load_error") + " " + s, 3, 0.0F) {

                    public void result(int k)
                    {
                        closeConnectionMessageBox = null;
                        destroy(false);
                    }

                }
;
            }
            break;

        case 5: // '\005'
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.Mission_loaded");
            break;

        case 6: // '\006'
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.Mission_started");
            if(curMessageBox != null)
            {
                curMessageBox.hideWindow();
                curMessageBox = null;
            }
            destroyExec = null;
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            {
                com.maddox.rts.Time.setPause(false);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setBornPlace(-1);
                com.maddox.il2.game.Main.cur().currentMissionFile = com.maddox.il2.game.Mission.cur().sectFile();
                if(com.maddox.il2.game.Main.state().id() == 36)
                    com.maddox.il2.game.Main.stateStack().change(40);
            } else
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).requestPlace(-1);
                com.maddox.il2.game.Main.cur().currentMissionFile = com.maddox.il2.game.Mission.cur().sectFile();
                if(com.maddox.il2.game.Main.state().id() == 36)
                    com.maddox.il2.game.Main.stateStack().change(46);
            }
            break;

        case 7: // '\007'
            lastNetMissionInfo = com.maddox.il2.game.I18N.gui("netcg.Mission_stoped");
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop() && com.maddox.il2.game.Main.state().id() == 50)
            {
                com.maddox.il2.ai.Front.checkAllCaptured();
                com.maddox.il2.game.Mission.cur().doEnd();
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).sendStatInc();
                com.maddox.il2.gui.GUI.activate();
                com.maddox.il2.gui.GUI.pad.leave(true);
                com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
                com.maddox.il2.game.Main.stateStack().change(51);
                return;
            }
            break;

        case 8: // '\b'
            return;
        }
        if(i != 4 && i != 5 && i != 6 && com.maddox.il2.game.Main.state().id() != 36)
        {
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).sendStatInc();
            for(; com.maddox.il2.game.Main.state().id() != 43 && com.maddox.il2.game.Main.state().id() != 40 && com.maddox.il2.game.Main.state().id() != 50 && com.maddox.il2.game.Main.state().id() != 48 && com.maddox.il2.game.Main.state().id() != 44 && com.maddox.il2.game.Main.state().id() != 46; com.maddox.il2.game.Main.stateStack().pop());
            com.maddox.il2.gui.GUI.activate();
            com.maddox.il2.gui.GUI.pad.leave(true);
            com.maddox.il2.engine.RendersMain.setRenderFocus((com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderGUI"));
            com.maddox.il2.game.Main.stateStack().change(36);
        }
    }

    public void netMissionCoopEnter()
    {
        if(!(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D))
        {
            return;
        } else
        {
            com.maddox.il2.gui.GUI.unActivate();
            com.maddox.rts.HotKeyCmd.exec("aircraftView", "CockpitView");
            com.maddox.il2.game.Main.stateStack().change(50);
            return;
        }
    }

    public void destroy(boolean flag)
    {
        com.maddox.il2.game.Main.cur().netChannelListener = null;
        com.maddox.il2.game.Main.cur().netMissionListener = null;
        if(curMessageBox != null)
        {
            curMessageBox.hideWindow();
            curMessageBox = null;
        }
        if(flag)
            com.maddox.rts.CmdEnv.top().exec("socket udp DESTROY LOCALPORT " + com.maddox.il2.engine.Config.cur.netLocalPort);
        com.maddox.rts.CmdEnv.top().exec("socket LISTENER 0");
        com.maddox.il2.game.Main.closeAllNetChannels();
        for(; com.maddox.il2.game.Main.state().id() != 2; com.maddox.il2.game.Main.stateStack().pop());
        com.maddox.il2.gui.GUI.pad.leave(true);
        com.maddox.il2.gui.GUI.activate();
        destroyExec = null;
    }

    public void dlgDestroy(com.maddox.il2.gui.DestroyExec destroyexec)
    {
        if(closeConnectionMessageBox != null)
            return;
        if(curMessageBox != null)
        {
            curMessageBox.hideWindow();
            curMessageBox = null;
        }
        destroyExec = destroyexec;
        curMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, com.maddox.il2.game.I18N.gui("main.ConfirmQuit"), com.maddox.il2.game.I18N.gui("main.ReallyQuit"), 1, 0.0F) {

            public void result(int i)
            {
                curMessageBox = null;
                if(i == 3)
                {
                    if(destroyExec != null)
                        destroyExec.destroy(THIS());
                    if(destroyExec != null && closeConnectionMessageBox == null)
                        curMessageBox = new GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, com.maddox.il2.game.I18N.gui("main.ConfirmQuit"), com.maddox.il2.game.I18N.gui("main.ReallyQuit"), 4, 0.0F);
                }
                destroyExec = null;
            }

        }
;
    }

    private com.maddox.il2.gui.GUINetClientGuard THIS()
    {
        return this;
    }

    public GUINetClientGuard()
    {
        lastNetMissionState = -1;
        com.maddox.il2.game.Main.cur().netChannelListener = this;
        com.maddox.il2.game.Main.cur().netMissionListener = this;
        destroyExec = null;
    }

    public com.maddox.gwindow.GWindowMessageBox closeConnectionMessageBox;
    public com.maddox.gwindow.GWindowMessageBox curMessageBox;
    public int lastNetMissionState;
    public float lastNetMissionPercent;
    public java.lang.String lastNetMissionInfo;
    public java.lang.String lastNetMissionName;
    private com.maddox.il2.gui.DestroyExec destroyExec;



}
