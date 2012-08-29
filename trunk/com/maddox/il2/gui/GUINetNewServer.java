package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUSGSControl;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.USGS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetSocket;
import java.io.File;
import java.util.List;

public class GUINetNewServer extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GWindowEditControl wName;
  public GUIButton bPassword;
  public GWindowComboControl cGameType;
  public GWindowComboControl cPlayers;
  public GUIButton bStart;
  public GUIButton bExit;
  private String password;

  public void _enter()
  {
    if (USGS.isUsed()) {
      ((NetUser)NetEnv.host()).reset();
      CmdEnv.top().exec("socket LISTENER 1");
      i = 31;
      NetEnv.cur(); NetEnv.host().setShortName(USGS.name);
      i = USGS.maxclients - 1;

      String str = "socket udp CREATE LOCALPORT " + Config.cur.netLocalPort + " SPEED " + Config.cur.netSpeed + " CHANNELS " + i;

      if ((Config.cur.netLocalHost != null) && (Config.cur.netLocalHost.length() > 0))
        str = str + " LOCALHOST " + Config.cur.netLocalHost;
      CmdEnv.top().exec(str);

      new NetServerParams();
      Main.cur().netServerParams.setType(48);
      Main.cur().netServerParams.setServerName(USGS.room);
      if ("".equals(Main.cur().netServerParams.serverName()))
        Main.cur().netServerParams.setServerName(NetEnv.host().shortName());
      Main.cur().netServerParams.serverDescription = Config.cur.netServerDescription;
      Main.cur().netServerParams.setPassword(null);
      new NetUSGSControl();
      int j = 0;
      if (!USGS.bGameDfight)
        j = 1;
      Main.cur().netServerParams.setMode(j);
      Main.cur().netServerParams.setDifficulty(World.cur().diffUser.get());
      Main.cur().netServerParams.setMaxUsers(i + 1);
      new Chat();
      USGS.serverReady(Config.cur.netLocalPort);
      Main.cur().netServerParams.bNGEN = false;
      Main.stateStack().change(38);
      GUI.chatDlg.showWindow();
      return;
    }

    if (Main.cur().netGameSpy != null) {
      Config.cur.netServerChannels = (Main.cur().netGameSpy.maxClients - 1);
      if ("coop".equals(Main.cur().netGameSpy.gameType)) this.cGameType.setSelected(1, true, false); else {
        this.cGameType.setSelected(0, true, false);
      }
    }

    if (Config.cur.netServerChannels < 1)
      Config.cur.netServerChannels = 1;
    if (Config.cur.netServerChannels > 31) {
      Config.cur.netServerChannels = 31;
    }

    fillPlayers();
    int i = Config.cur.netServerChannels - 1;

    this.cPlayers.setSelected(i, true, false);

    if (Main.cur().netGameSpy != null) {
      this.wName.setValue(Main.cur().netGameSpy.roomName, false);
      this.wName.setEditable(false);
    }
    else if ("".equals(this.wName.getValue())) {
      if ("".equals(Config.cur.netServerName))
        this.wName.setValue(NetEnv.host().shortName(), false);
      else {
        this.wName.setValue(Config.cur.netServerName, false);
      }

    }

    ((NetUser)NetEnv.host()).reset();

    this.client.activateWindow();
  }
  public void _leave() {
    if (USGS.isUsing()) {
      return;
    }
    if (Main.cur().netGameSpy == null)
    {
      Config.cur.netServerName = this.wName.getValue();
    }
    this.client.hideWindow();
  }

  private void fillPlayers() {
    int i = this.cPlayers.getSelected();
    if (i < 0)
      i = 0;
    this.cPlayers.clear(false);
    int j = 32;

    for (int k = 2; k <= j; k++)
      this.cPlayers.add("" + k);
    this.cPlayers.setSelected(i, true, false);
  }

  private void doNewServer(int paramInt) {
    Config.cur.netServerChannels = (this.cPlayers.getSelected() + 1);
    CmdEnv.top().exec("socket LISTENER 1");
    NetEnv.cur(); NetEnv.host().setShortName(World.cur().userCfg.callsign);
    String str = "socket udp CREATE LOCALPORT " + Config.cur.netLocalPort + " SPEED " + Config.cur.netSpeed + " CHANNELS " + Config.cur.netServerChannels;

    if ((Config.cur.netLocalHost != null) && (Config.cur.netLocalHost.length() > 0))
      str = str + " LOCALHOST " + Config.cur.netLocalHost;
    CmdEnv.top().exec(str);

    new NetServerParams();

    if (Main.cur().netGameSpy != null) {
      List localList = NetEnv.socketsBlock();
      if ((localList != null) && (localList.size() > 0))
        Main.cur().netGameSpy.set(Main.cur().netGameSpy.roomName, (NetSocket)localList.get(0), Config.cur.netLocalPort);
      Main.cur().netServerParams.setType(32);
      Main.cur().netServerParams.setServerName(Main.cur().netGameSpy.roomName);
      NetEnv.cur(); NetEnv.host().setShortName(Main.cur().netGameSpy.userName);
    }
    else
    {
      Main.cur().netServerParams.setType(paramInt);
      Main.cur().netServerParams.setServerName(this.wName.getValue());
      if ("".equals(Main.cur().netServerParams.serverName())) {
        Main.cur().netServerParams.setServerName(NetEnv.host().shortName());
      }

    }

    Main.cur().netServerParams.setPassword(this.password);

    new NetLocalControl();
    int i = 0;
    if ((this.cGameType.getSelected() == 1) || (this.cGameType.getSelected() == 2))
      i = 1;
    Main.cur().netServerParams.setMode(i);
    Main.cur().netServerParams.setDifficulty(World.cur().diffUser.get());
    Main.cur().netServerParams.setMaxUsers(Config.cur.netServerChannels + 1);
    new Chat();
    if (this.cGameType.getSelected() == 2) {
      Main.cur().netServerParams.bNGEN = true;
      Main.stateStack().change(68);
    } else {
      Main.cur().netServerParams.bNGEN = false;
      Main.stateStack().change(38);
    }
    GUI.chatDlg.showWindow();
  }

  private void doNewLocal()
  {
    doNewServer(0);
  }

  private void doExit()
  {
    GUIMainMenu localGUIMainMenu = (GUIMainMenu)GameState.get(2);
    localGUIMainMenu.pPilotName.cap = new GCaption(World.cur().userCfg.name + " '" + World.cur().userCfg.callsign + "' " + World.cur().userCfg.surname);

    GUIInfoName.nickName = null;

    ((NetUser)NetEnv.host()).reset();
    Main.stateStack().pop();
  }

  private Object THIS()
  {
    return this;
  }
  private boolean isExistNGEN() {
    try {
      File localFile = new File(HomePath.get(0), "ngen");
      if (!localFile.isDirectory())
        return false;
      localFile = new File(HomePath.get(0), "ngen.exe");
      if (!localFile.exists())
        return false;
    } catch (Exception localException) {
      return false;
    }
    return true;
  }

  public GUINetNewServer(GWindowRoot paramGWindowRoot) {
    super(35);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netns.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wName = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wName.maxLength = 64;

    this.cGameType = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cGameType.setEditable(false);
    this.cGameType.add(i18n("netns.Dogfight"));

    this.cGameType.add(i18n("netns.Cooperative"));
    if (isExistNGEN())
      this.cGameType.add(i18n("netns.NGEN"));
    this.cGameType.setSelected(0, true, false);
    this.cPlayers = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cPlayers.setEditable(false);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bPassword = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bStart = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow(); } 
  public class DlgPassword extends GWindowFramed { GWindowEditControl pw0;
    GWindowEditControl pw1;
    GWindowButton bOk;
    GWindowButton bCancel;

    public boolean doOk() { String str1 = this.pw0.getValue();
      String str2 = this.pw1.getValue();
      if (str1.equals(str2)) {
        if ("".equals(str1)) GUINetNewServer.access$302(GUINetNewServer.this, null); else
          GUINetNewServer.access$302(GUINetNewServer.this, str1);
        return true;
      }
      new GWindowMessageBox(this.root, 22.0F, true, GUINetNewServer.this.i18n("netns.Pwd"), GUINetNewServer.this.i18n("netns.PwdIncorrect"), 3, 0.0F);

      return false; } 
    public void doCancel() {
    }

    public void afterCreated() {
      this.clientWindow = create(new GWindowDialogClient() {
        public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);
          if (paramGWindow == GUINetNewServer.DlgPassword.this.bOk) {
            if (GUINetNewServer.DlgPassword.this.doOk())
              close(false);
            return true;
          }if (paramGWindow == GUINetNewServer.DlgPassword.this.bCancel) {
            GUINetNewServer.DlgPassword.this.doCancel();
            close(false);
            return true;
          }
          return super.notify(paramGWindow, paramInt1, paramInt2);
        }
      });
      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 10.0F, 1.5F, GUINetNewServer.this.i18n("netns.Password_") + " ", null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 10.0F, 1.5F, GUINetNewServer.this.i18n("netns.ConfirmPassword") + " ", null));
      localGWindowDialogClient.addControl(this.pw0 = new GWindowEditControl(localGWindowDialogClient, 12.0F, 1.0F, 8.0F, 1.5F, null));
      localGWindowDialogClient.addControl(this.pw1 = new GWindowEditControl(localGWindowDialogClient, 12.0F, 3.0F, 8.0F, 1.5F, null));
      this.pw0.bPassword = (this.pw1.bPassword = 1);
      localGWindowDialogClient.addDefault(this.bOk = new GWindowButton(localGWindowDialogClient, 4.0F, 6.0F, 6.0F, 2.0F, GUINetNewServer.this.i18n("netns.Ok"), null));
      localGWindowDialogClient.addEscape(this.bCancel = new GWindowButton(localGWindowDialogClient, 12.0F, 6.0F, 6.0F, 2.0F, GUINetNewServer.this.i18n("netns.Cancel"), null));
      if (GUINetNewServer.this.password != null) {
        this.pw0.setValue(GUINetNewServer.this.password, false);
        this.pw1.setValue(GUINetNewServer.this.password, false);
      }
      super.afterCreated();
      resized();
      showModal();
    }

    public DlgPassword(GWindow arg2) {
      this.bSizable = false;
      this.title = GUINetNewServer.this.i18n("netns.EnterPassword");
      float f1 = 22.0F;
      float f2 = 12.0F;
      GWindow localGWindow;
      float f3 = localGWindow.win.dx / localGWindow.lookAndFeel().metric();
      float f4 = localGWindow.win.dy / localGWindow.lookAndFeel().metric();
      float f5 = (f3 - f1) / 2.0F;
      float f6 = (f4 - f2) / 2.0F;
      doNew(localGWindow, f5, f6, f1, f2, true);
    }
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUINetNewServer.this.bStart) {
        GUINetNewServer.this.doNewLocal();
        return true;
      }if (paramGWindow == GUINetNewServer.this.cGameType) {
        GUINetNewServer.this.fillPlayers();
        return true;
      }if (paramGWindow == GUINetNewServer.this.bPassword) {
        new GUINetNewServer.DlgPassword(GUINetNewServer.this, this.root);
        return true;
      }if (paramGWindow == GUINetNewServer.this.bExit) {
        GUINetNewServer.this.doExit();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(88.0F), x1024(800.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(248.0F), x1024(800.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(32.0F), y1024(32.0F), x1024(160.0F), y1024(32.0F), 2, GUINetNewServer.this.i18n("netns.Name"));

      draw(x1024(576.0F), y1024(32.0F), x1024(256.0F), y1024(32.0F), 0, GUINetNewServer.this.i18n("netns.Password"));

      draw(x1024(144.0F), y1024(120.0F), x1024(400.0F), y1024(32.0F), 2, GUINetNewServer.this.i18n("netns.GameType"));
      draw(x1024(144.0F), y1024(184.0F), x1024(400.0F), y1024(32.0F), 2, GUINetNewServer.this.i18n("netns.Max.Players"));

      if (Main.cur().netGameSpy != null) {
        draw(x1024(96.0F), y1024(280.0F), x1024(256.0F), y1024(48.0F), 0, GUINetNewServer.this.i18n("main.Quit"));
      }
      else {
        draw(x1024(96.0F), y1024(280.0F), x1024(256.0F), y1024(48.0F), 0, GUINetNewServer.this.i18n("netns.MainMenu"));
      }
      draw(x1024(464.0F), y1024(280.0F), x1024(304.0F), y1024(48.0F), 2, GUINetNewServer.this.i18n("netns.Create"));
    }

    public void setPosSize()
    {
      set1024PosSize(80.0F, 216.0F, 864.0F, 360.0F);

      GUINetNewServer.this.wName.setPosSize(x1024(208.0F), y1024(32.0F), x1024(288.0F), y1024(32.0F));

      GUINetNewServer.this.bPassword.setPosC(x1024(536.0F), y1024(48.0F));
      GUINetNewServer.this.cGameType.setPosSize(x1024(560.0F), y1024(120.0F), x1024(272.0F), y1024(32.0F));
      GUINetNewServer.this.cPlayers.setPosSize(x1024(560.0F), y1024(184.0F), x1024(272.0F), y1024(32.0F));
      GUINetNewServer.this.bExit.setPosC(x1024(56.0F), y1024(304.0F));
      GUINetNewServer.this.bStart.setPosC(x1024(808.0F), y1024(304.0F));
    }
  }
}