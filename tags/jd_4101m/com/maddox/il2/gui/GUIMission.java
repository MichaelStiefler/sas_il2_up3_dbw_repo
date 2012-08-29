package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetObj;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class GUIMission extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bVideo;
  public GUIButton b3d;
  public GUIButton bSound;
  public GUIButton bControls;
  public GUIButton bExit;
  public GUIButton bBack;
  public GUIButton bTrack;
  private GWindowMessageBox loadMessageBox;

  public void enterPush(GameState paramGameState)
  {
    enter(paramGameState);
  }
  public void enter(GameState paramGameState) {
    if (Main.cur().netServerParams == null) {
      new NetServerParams();
      Main.cur().netServerParams.setMode(2);
      new NetLocalControl();
    }
    this.loadMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.StandBy"), i18n("miss.Loading"), 5, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 1)
          BackgroundTask.cancel(GUIMission.this.i18n("miss.UserCancel"));
      }
    };
    new MsgAction(72, 0.0D) {
      public void doAction() { Main.closeAllNetChannels();
        if (Mission.cur() != null) Mission.cur().destroy();
        World.cur().diffCur.set(World.cur().diffUser);
        Main.cur().netServerParams.setDifficulty(World.cur().diffCur.get());
        GUIMission.MissionListener localMissionListener = new GUIMission.MissionListener(GUIMission.this);
        try {
          Mission.preparePlayerNumberOn(Main.cur().currentMissionFile);
          Mission.loadFromSect(Main.cur().currentMissionFile, true);
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
          BackgroundTask.removeListener(localMissionListener);
          GUIMission.this.missionBad(GUIMission.this.i18n("miss.LoadFailed"));
        }
      }
    };
  }

  private void missionBad(String paramString)
  {
    this.loadMessageBox.close(false);
    new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), paramString, 3, 0.0F)
    {
      public void result(int paramInt) {
        Main.cur().netServerParams.destroy();
        RTSConf.cur.netEnv.control.destroy();
        Main.stateStack().pop();
      } } ;
  }

  public void gameBegin() {
    new MsgAction(72, 0.0D) {
      public void doAction() { GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        GUIMission.this.loadMessageBox.close(false);
        localGUIWindowManager.setTimeGameActive(false);
        GUI.unActivate();
        CmdEnv.top().exec("mission BEGIN");
        HotKeyCmd.exec("aircraftView", "CockpitView"); } } ;
  }

  public void _leave() {
    this.client.hideWindow();
  }

  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 59) {
      this.client.hideWindow();
      GUI.unActivate();
      Time.setPause(false);
    } else {
      this.client.activateWindow();
    }
  }

  public void doQuitMission() {
    GUI.activate();
    this.client.activateWindow();
  }

  protected void doExit()
  {
  }

  protected void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = "";

    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bVideo = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.b3d = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSound = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bControls = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bTrack = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public GUIMission(int paramInt) {
    super(paramInt);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIMission.this.bVideo) {
        Main.stateStack().push(12);
        return true;
      }
      if (paramGWindow == GUIMission.this.b3d) {
        Main.stateStack().push(11);
        return true;
      }
      if (paramGWindow == GUIMission.this.bSound) {
        Main.stateStack().push(13);
        return true;
      }
      if (paramGWindow == GUIMission.this.bControls) {
        Main.stateStack().push(20);
        return true;
      }
      if (paramGWindow == GUIMission.this.bExit) {
        if (NetMissionTrack.isRecording()) {
          NetMissionTrack.stopRecording();
          GUI.activate();
        }
        try
        {
          Main.cur().netServerParams.destroy();
          RTSConf.cur.netEnv.control.destroy();
        }
        catch (NullPointerException localNullPointerException)
        {
        }

        GUIMission.this.doExit();
        return true;
      }
      if (paramGWindow == GUIMission.this.bBack) {
        GUIMission.this.client.hideWindow();
        GUI.unActivate();
        return true;
      }
      if (paramGWindow == GUIMission.this.bTrack) {
        if (NetMissionTrack.isRecording()) {
          NetMissionTrack.stopRecording();
          GUIMission.this.client.hideWindow();
          GUI.unActivate();
        } else {
          Main.stateStack().push(59);
        }
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(303.0F), x1024(288.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(416.0F), x1024(288.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.VideoModes"));
      draw(x1024(96.0F), y1024(96.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.VideoOptions"));
      draw(x1024(96.0F), y1024(160.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.SoundSetup"));
      draw(x1024(96.0F), y1024(224.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.Controls"));
      draw(x1024(96.0F), y1024(336.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.QuitMiss"));
      draw(x1024(96.0F), y1024(448.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.Return2Miss"));
      if (NetMissionTrack.isRecording())
        draw(x1024(96.0F), y1024(512.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.StopRecording"));
      else
        draw(x1024(96.0F), y1024(512.0F), x1024(224.0F), y1024(48.0F), 0, GUIMission.this.i18n("miss.StartRecording"));
    }

    public void setPosSize()
    {
      set1024PosSize(350.0F, 80.0F, 352.0F, 592.0F);

      GUIMission.this.bVideo.setPosC(x1024(56.0F), y1024(56.0F));
      GUIMission.this.b3d.setPosC(x1024(56.0F), y1024(120.0F));
      GUIMission.this.bSound.setPosC(x1024(56.0F), y1024(184.0F));
      GUIMission.this.bControls.setPosC(x1024(56.0F), y1024(248.0F));
      GUIMission.this.bExit.setPosC(x1024(56.0F), y1024(360.0F));
      GUIMission.this.bBack.setPosC(x1024(56.0F), y1024(472.0F));
      GUIMission.this.bTrack.setPosC(x1024(56.0F), y1024(536.0F));
    }
  }

  class MissionListener
    implements MsgBackgroundTaskListener
  {
    public void msgBackgroundTaskStarted(BackgroundTask paramBackgroundTask)
    {
    }

    public void msgBackgroundTaskStep(BackgroundTask paramBackgroundTask)
    {
      GUIMission.this.loadMessageBox.message = ((int)paramBackgroundTask.percentComplete() + "% " + GUIMission.this.i18n(paramBackgroundTask.messageComplete()));
    }

    public void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask)
    {
      BackgroundTask.removeListener(this);
      if (paramBackgroundTask.isComplete())
        GUIMission.this.gameBegin();
      else
        GUIMission.this.missionBad(GUIMission.this.i18n("miss.LoadBad") + " " + paramBackgroundTask.messageCancel());
    }

    public MissionListener() {
      BackgroundTask.addListener(this);
    }
  }
}