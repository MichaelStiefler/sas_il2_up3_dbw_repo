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

public class GUINetClientGuard
  implements NetChannelListener, NetMissionListener
{
  public GWindowMessageBox closeConnectionMessageBox;
  public GWindowMessageBox curMessageBox;
  public int lastNetMissionState = -1;
  public float lastNetMissionPercent;
  public String lastNetMissionInfo;
  public String lastNetMissionName;
  private DestroyExec destroyExec;

  public void netChannelCanceled(String paramString)
  {
  }

  public void netChannelCreated(NetChannel paramNetChannel)
  {
  }

  public void netChannelRequest(String paramString)
  {
  }

  public void netChannelDestroying(NetChannel paramNetChannel, String paramString)
  {
    if (this.curMessageBox != null) {
      this.curMessageBox.hideWindow();
      this.curMessageBox = null;
    }
    if (this.closeConnectionMessageBox != null) return;
    if ((Main.cur().netServerParams != null) && 
      (Main.cur().netServerParams.masterChannel() != paramNetChannel)) {
      return;
    }
    GUI.activate();
    GUI.pad.leave(true);
    RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));

    GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
    this.closeConnectionMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, I18N.gui("netcg.Close"), paramString, 3, 0.0F)
    {
      public void result(int paramInt)
      {
        GUINetClientGuard.this.closeConnectionMessageBox = null;
        GUINetClientGuard.this.destroy(false);
      }
    };
    if (BackgroundTask.isExecuted())
      BackgroundTask.cancel(paramString);
  }

  public void netMissionState(int paramInt, float paramFloat, String paramString)
  {
    int i = this.lastNetMissionState;
    this.lastNetMissionState = paramInt;
    this.lastNetMissionPercent = paramFloat;
    paramString = I18N.gui(paramString);
    this.lastNetMissionInfo = paramString;

    switch (paramInt) { case -1:
    default:
      break;
    case 9:
      return;
    case 0:
      this.lastNetMissionName = paramString;
      this.lastNetMissionInfo = (I18N.gui("netcg.StartTransfer") + " " + paramString);
      if (Main.state().id() != 51) break;
      Main.stateStack().change(36); break;
    case 1:
      this.lastNetMissionInfo = I18N.gui("netcg.Transfer"); break;
    case 2:
      this.lastNetMissionInfo = I18N.gui("netcg.TransferObjects"); break;
    case 3:
      this.lastNetMissionInfo = ("" + paramFloat + "% " + paramString);
      break;
    case 4:
      this.lastNetMissionInfo = (I18N.gui("netcg.ERROR") + ": " + paramString);
      if (this.closeConnectionMessageBox != null) break;
      GUI.activate();
      GUI.pad.leave(true);
      RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));
      if (this.curMessageBox != null) {
        this.curMessageBox.hideWindow();
        this.curMessageBox = null;
      }
      this.closeConnectionMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, I18N.gui("netcg.Close"), I18N.gui("netcg.Mission_load_error") + " " + paramString, 3, 0.0F)
      {
        public void result(int paramInt)
        {
          GUINetClientGuard.this.closeConnectionMessageBox = null;
          GUINetClientGuard.this.destroy(false);
        }
      };
      break;
    case 5:
      this.lastNetMissionInfo = I18N.gui("netcg.Mission_loaded");
      break;
    case 6:
      this.lastNetMissionInfo = I18N.gui("netcg.Mission_started");
      if (this.curMessageBox != null) {
        this.curMessageBox.hideWindow();
        this.curMessageBox = null;
      }
      this.destroyExec = null;
      if (Main.cur().netServerParams.isDogfight()) {
        Time.setPause(false);
        ((NetUser)NetEnv.host()).setBornPlace(-1);
        Main.cur().currentMissionFile = Mission.cur().sectFile();
        if (Main.state().id() != 36) break;
        Main.stateStack().change(40); } else {
        if (!Main.cur().netServerParams.isCoop()) break;
        ((NetUser)NetEnv.host()).requestPlace(-1);
        Main.cur().currentMissionFile = Mission.cur().sectFile();
        if (Main.state().id() != 36) break;
        Main.stateStack().change(46); } break;
    case 7:
      this.lastNetMissionInfo = I18N.gui("netcg.Mission_stoped");
      if ((!Main.cur().netServerParams.isCoop()) || (Main.state().id() != 50))
        break;
      Front.checkAllCaptured();
      Mission.cur().doEnd();
      ((NetUser)NetEnv.host()).sendStatInc();
      GUI.activate();
      GUI.pad.leave(true);
      RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));
      Main.stateStack().change(51);
      return;
    case 8:
      return;
    }

    if ((paramInt != 4) && (paramInt != 5) && (paramInt != 6) && (Main.state().id() != 36))
    {
      ((NetUser)NetEnv.host()).sendStatInc();

      while ((Main.state().id() != 43) && (Main.state().id() != 40) && (Main.state().id() != 50) && (Main.state().id() != 48) && (Main.state().id() != 44) && (Main.state().id() != 46))
      {
        Main.stateStack().pop();
      }
      GUI.activate();
      GUI.pad.leave(true);
      RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));
      Main.stateStack().change(36);
    }
  }

  public void netMissionCoopEnter() {
    if (!(Main.cur() instanceof Main3D)) {
      return;
    }
    GUI.unActivate();
    HotKeyCmd.exec("aircraftView", "CockpitView");

    Main.stateStack().change(50);
  }

  public void destroy(boolean paramBoolean) {
    Main.cur().netChannelListener = null;
    Main.cur().netMissionListener = null;
    if (this.curMessageBox != null) {
      this.curMessageBox.hideWindow();
      this.curMessageBox = null;
    }
    if (paramBoolean) {
      CmdEnv.top().exec("socket udp DESTROY LOCALPORT " + Config.cur.netLocalPort);
    }
    CmdEnv.top().exec("socket LISTENER 0");
    Main.closeAllNetChannels();
    while (Main.state().id() != 2)
      Main.stateStack().pop();
    GUI.pad.leave(true);
    GUI.activate();
    this.destroyExec = null;
  }

  public void dlgDestroy(DestroyExec paramDestroyExec)
  {
    if (this.closeConnectionMessageBox != null) return;
    if (this.curMessageBox != null) {
      this.curMessageBox.hideWindow();
      this.curMessageBox = null;
    }
    this.destroyExec = paramDestroyExec;
    this.curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, I18N.gui("main.ConfirmQuit"), I18N.gui("main.ReallyQuit"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        GUINetClientGuard.this.curMessageBox = null;
        if (paramInt == 3) {
          if (GUINetClientGuard.this.destroyExec != null)
            GUINetClientGuard.this.destroyExec.destroy(GUINetClientGuard.this.THIS());
          if ((GUINetClientGuard.this.destroyExec != null) && (GUINetClientGuard.this.closeConnectionMessageBox == null)) {
            GUINetClientGuard.this.curMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, I18N.gui("main.ConfirmQuit"), I18N.gui("main.ReallyQuit"), 4, 0.0F);
          }

        }

        GUINetClientGuard.access$002(GUINetClientGuard.this, null);
      } } ;
  }

  private GUINetClientGuard THIS() {
    return this;
  }
  public GUINetClientGuard() {
    Main.cur().netChannelListener = this;
    Main.cur().netMissionListener = this;
    this.destroyExec = null;
  }

  public static class DestroyExec
  {
    public void destroy(GUINetClientGuard paramGUINetClientGuard)
    {
    }
  }
}