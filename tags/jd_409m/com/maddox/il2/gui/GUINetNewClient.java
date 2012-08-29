package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetChannelListener;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.USGS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Finger;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgInvokeMethod;
import com.maddox.rts.MsgNetExtListener;
import com.maddox.rts.MsgRemoveListener;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetControl;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Time;
import com.maddox.rts.net.IPAddress;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUINetNewClient extends GameState
  implements NetChannelListener, MsgNetExtListener
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Table wTable;
  public GWindowEditControl wEdit;
  public GUIButton bSearch;
  public GUIButton bJoin;
  public GUIButton bExit;
  public GWindow connectMessgeBox;
  public String serverAddress;
  NetChannel serverChannel;
  public boolean bExistSearch;
  private static NetAddress broadcastAdr;
  private static NetMsgInput _netMsgInput = new NetMsgInput();

  public void _enter()
  {
    this.bExistSearch = false;
    this.wTable.hideWindow();
    this.wTable.adrList.clear();
    this.wTable.serverMap.clear();
    Main.cur().netChannelListener = this;
    if ((USGS.isUsed()) || (Main.cur().netGameSpy != null))
    {
      this.bSearch.hideWindow();
      this.wEdit.hideWindow();
      this.bJoin.hideWindow();
    } else {
      this.bSearch.showWindow();
      this.wEdit.setValue(Config.cur.netRemoteHost + ":" + Config.cur.netRemotePort, false);
      this.wEdit.showWindow();
      this.bJoin.showWindow();
    }
    this.dialogClient.setPosSize();
    this.client.activateWindow();
    if ((USGS.isUsed()) || (Main.cur().netGameSpy != null))
    {
      new MsgAction(64, Time.real() + 500L) {
        public void doAction() {
          GUINetNewClient.this.doJoin();
        } } ;
    }
    ((NetUser)NetEnv.host()).reset();
  }
  public void _leave() {
    this.client.hideWindow();
    Main.cur().netChannelListener = null;
    if (this.bExistSearch)
      MsgRemoveListener.post(64, NetEnv.cur(), this, null);
  }

  public void netChannelCanceled(String paramString) {
    this.serverChannel = null;
    if (this.connectMessgeBox == null)
      return;
    this.connectMessgeBox.hideWindow();
    this.connectMessgeBox = new GWindowMessageBox(this.client.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("netnc.NotConnect"), paramString, 3, 0.0F)
    {
      public void result(int paramInt)
      {
        GUINetNewClient.this.connectMessgeBox = null;

        if ((USGS.isUsed()) || (Main.cur().netGameSpy != null))
        {
          GUINetNewClient.this.bJoin.showWindow();
        }
      } } ;
  }

  public void netChannelCreated(NetChannel paramNetChannel) {
    if (this.connectMessgeBox == null) return;
    this.serverChannel = paramNetChannel;

    onChannelCreated();
  }

  private void onChannelCreated()
  {
    this.connectMessgeBox.hideWindow();
    this.connectMessgeBox = new GWindowMessageBox(this.client.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("netnc.Connect"), i18n("netnc.ConnectSucc"), 3, 5.0F)
    {
      public void result(int paramInt)
      {
        GUINetNewClient.this.connectMessgeBox = null;
        ((NetUser)NetEnv.host()).onConnectReady(GUINetNewClient.this.serverChannel);
        Main.stateStack().change(36);
        GUI.chatDlg.showWindow();
      }
    };
  }

  public void netChannelRequest(String paramString)
  {
    if (this.connectMessgeBox == null)
      return;
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramString);
    String str1 = localNumberTokenizer.next("_");
    if ("SP".equals(str1)) {
      String str2 = localNumberTokenizer.next("0");
      this.connectMessgeBox.hideWindow();
      this.connectMessgeBox = new DlgServerPassword(this.client.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, str2);
    }
    else if ((USGS.isUsed()) && ("NM".equals(str1))) {
      ((NetControl)NetEnv.cur().control).doAnswer("NM \"" + NetEnv.host().shortName() + '"');
    }
  }

  public void netChannelDestroying(NetChannel paramNetChannel, String paramString) {
    netChannelCanceled(paramString);
  }

  public void onMsgTimeout() {
    if ((!this.bExistSearch) || (Main.state() != this))
      return;
    if (NetEnv.socketsBlock().size() + NetEnv.socketsNoBlock().size() <= 0) {
      localObject = "socket udp CREATE LOCALPORT " + Config.cur.netLocalPort;
      if ((Config.cur.netLocalHost != null) && (Config.cur.netLocalHost.length() > 0))
        localObject = (String)localObject + " LOCALHOST " + Config.cur.netLocalHost;
      CmdEnv.top().exec((String)localObject);
    }
    if (NetEnv.socketsBlock().size() + NetEnv.socketsNoBlock().size() <= 0) {
      return;
    }
    Object localObject = null;
    if (NetEnv.socketsNoBlock().size() > 0) localObject = (NetSocket)NetEnv.socketsNoBlock().get(0); else
      localObject = (NetSocket)NetEnv.socketsBlock().get(0);
    NetEnv.cur().postExtUTF(32, "rinfo " + Time.currentReal(), (NetSocket)localObject, broadcastAdr, Config.cur.netRemotePort);

    new MsgInvokeMethod("onMsgTimeout").post(64, this, 1.0D);
  }

  public void msgNetExt(byte[] paramArrayOfByte, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    if ((!this.bExistSearch) || (Main.state() != this)) {
      return;
    }
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 2)) return;
    if (paramArrayOfByte[0] != 32) return;
    String str = "";
    try {
      _netMsgInput.setData(null, false, paramArrayOfByte, 1, paramArrayOfByte.length - 1);
      str = _netMsgInput.readUTF();
    } catch (Exception localException1) {
      return;
    }

    NumberTokenizer localNumberTokenizer = new NumberTokenizer(str);
    if (!localNumberTokenizer.hasMoreTokens())
      return;
    if (!"ainfo".equals(localNumberTokenizer.next()))
      return;
    Item localItem = new Item();
    localItem.adr = paramNetAddress;
    localItem.port = paramInt;
    long l = -1L;
    try { l = Long.parseLong(localNumberTokenizer.next());
    } catch (Exception localException2) {
      return;
    }
    localItem.ping = (int)(Time.currentReal() - l);
    if (localItem.ping < 0)
      return;
    localItem.ver = localNumberTokenizer.next("");
    localItem.bServer = localNumberTokenizer.next(false);
    localItem.iServerType = localNumberTokenizer.next(0);
    localItem.bProtected = localNumberTokenizer.next(false);
    localItem.bDedicated = localNumberTokenizer.next(false);
    localItem.bCoop = localNumberTokenizer.next(false);
    localItem.bMissionStarted = localNumberTokenizer.next(false);
    localItem.maxChannels = localNumberTokenizer.next(0);
    localItem.usedChannels = localNumberTokenizer.next(0);
    localItem.maxUsers = localNumberTokenizer.next(0);
    localItem.existUsers = localNumberTokenizer.next(0);
    localItem.serverName = "";
    if (localNumberTokenizer.hasMoreTokens()) {
      localObject = new StringBuffer(localNumberTokenizer.next(""));
      while (localNumberTokenizer.hasMoreTokens()) {
        ((StringBuffer)localObject).append(' ');
        ((StringBuffer)localObject).append(localNumberTokenizer.next(""));
      }
      localItem.serverName = ((StringBuffer)localObject).toString();
    }
    Object localObject = "" + localItem.adr.getHostAddress() + ":" + localItem.port;
    boolean bool = this.wTable.serverMap.containsKey(localObject);
    this.wTable.serverMap.put(localObject, localItem);
    if (!bool) {
      this.wTable.adrList.add(localObject);
      this.wTable.resized();
    }
  }

  public void doJoin()
  {
    if (USGS.isUsed()) {
      NetEnv.cur(); NetEnv.host().setShortName(USGS.name);
      this.serverAddress = USGS.serverIP;
    }
    else if (Main.cur().netGameSpy != null) {
      NetEnv.cur(); NetEnv.host().setShortName(Main.cur().netGameSpy.userName);
      this.serverAddress = Main.cur().netGameSpy.serverIP;
    }
    else {
      NetEnv.cur(); NetEnv.host().setShortName(World.cur().userCfg.callsign);
      this.serverAddress = this.wEdit.getValue();
    }

    String str1 = this.serverAddress;
    if ((str1 == null) || (str1.length() == 0)) return;
    int i = Config.cur.netRemotePort;
    int j = str1.lastIndexOf(":");
    if ((j >= 0) && (j < str1.length() - 1)) {
      String str2 = str1.substring(j + 1);
      str1 = str1.substring(0, j);
      try {
        i = Integer.parseInt(str2);
      } catch (Exception localException) {
        str1 = this.serverAddress;
      }
    }
    CmdEnv.top().exec("socket LISTENER " + (Config.cur.netRouteChannels > 0 ? 1 : 0));
    int k = Config.cur.netRouteChannels;
    if (k <= 0) k = 1; else
      k++;
    if (this.bExistSearch)
      CmdEnv.top().exec("socket udp DESTROY LOCALPORT " + Config.cur.netLocalPort);
    String str3 = "socket udp JOIN LOCALPORT " + Config.cur.netLocalPort + " PORT " + i + " SPEED " + Config.cur.netSpeed + " CHANNELS " + k + " HOST " + str1;

    if ((Config.cur.netLocalHost != null) && (Config.cur.netLocalHost.length() > 0))
      str3 = str3 + " LOCALHOST " + Config.cur.netLocalHost;
    CmdEnv.top().exec(str3);
    Config.cur.netRemoteHost = str1;
    Config.cur.netRemotePort = i;

    doStartWaitDlg();
  }

  private void doStartWaitDlg() {
    if (this.connectMessgeBox != null)
      this.connectMessgeBox.close(false);
    this.connectMessgeBox = new GWindowMessageBox(this.dialogClient.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("netnc.Connect"), i18n("netnc.ConnectWait"), 5, 0.0F)
    {
      public void result(int paramInt)
      {
        if ((paramInt == 1) && 
          (GUINetNewClient.this.connectMessgeBox != null)) {
          GUINetNewClient.this.connectMessgeBox = null;
          NetEnv.cur().connect.joinBreak();

          if (GUINetNewClient.this.serverChannel != null) {
            GUINetNewClient.this.serverChannel.destroy();
            GUINetNewClient.this.serverChannel = null;
          }
          return;
        }
      }
    };
  }

  public GUINetNewClient(GWindowRoot paramGWindowRoot)
  {
    super(34);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netnc.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);
    this.wEdit = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bSearch = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bJoin = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow(); } 
  public class DlgServerPassword extends GWindowFramed { GWindowEditControl pw;
    GWindowButton bOk;
    GWindowButton bCancel;
    String publicKey;

    public void doOk() { long l = Finger.incLong(0L, this.publicKey);
      l = Finger.incLong(l, this.pw.getValue());
      ((NetControl)NetEnv.cur().control).doAnswer("SP " + l);
      GUINetNewClient.this.doStartWaitDlg(); }

    public void doCancel() {
      GUINetNewClient.this.connectMessgeBox = null;
      NetEnv.cur().connect.joinBreak();
    }

    public void afterCreated() {
      this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow = create(new GUINetNewClient.5(this));

      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow;
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 10.0F, 1.5F, GUINetNewClient.this.i18n("netnc.Password") + " ", null));
      localGWindowDialogClient.addControl(this.pw = new GWindowEditControl(localGWindowDialogClient, 12.0F, 1.0F, 8.0F, 1.5F, null));
      this.pw.bPassword = true;
      this.pw.bCanEditTab = false;
      localGWindowDialogClient.addDefault(this.bOk = new GWindowButton(localGWindowDialogClient, 4.0F, 4.0F, 6.0F, 2.0F, GUINetNewClient.this.i18n("netnc.Ok"), null));
      localGWindowDialogClient.addEscape(this.bCancel = new GWindowButton(localGWindowDialogClient, 12.0F, 4.0F, 6.0F, 2.0F, GUINetNewClient.this.i18n("netnc.Cancel"), null));
      super.afterCreated();
      resized();
      showModal();
    }

    public DlgServerPassword(GWindow paramString, String arg3) {
      this.bSizable = false;
      Object localObject;
      this.publicKey = localObject;
      this.title = GUINetNewClient.this.i18n("netnc.EnterPassword");
      float f1 = 22.0F;
      float f2 = 10.0F;
      float f3 = paramString.win.dx / paramString.lookAndFeel().metric();
      float f4 = paramString.win.dy / paramString.lookAndFeel().metric();
      float f5 = (f3 - f1) / 2.0F;
      float f6 = (f4 - f2) / 2.0F;
      doNew(paramString, f5, f6, f1, f2, true);
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

      if (paramGWindow == GUINetNewClient.this.bJoin) {
        GUINetNewClient.this.doJoin();
        return true;
      }
      if (paramGWindow == GUINetNewClient.this.bSearch) {
        if (!GUINetNewClient.this.bExistSearch) {
          CmdEnv.top().exec("socket LISTENER 0");
          String str = "socket udp CREATE LOCALPORT " + Config.cur.netLocalPort;
          if ((Config.cur.netLocalHost != null) && (Config.cur.netLocalHost.length() > 0))
            str = str + " LOCALHOST " + Config.cur.netLocalHost;
          CmdEnv.top().exec(str);
          if (NetEnv.socketsBlock().size() + NetEnv.socketsNoBlock().size() <= 0)
            return true;
          if (GUINetNewClient.broadcastAdr == null) {
            try {
              GUINetNewClient.access$002(new IPAddress());
              GUINetNewClient.broadcastAdr.create("255.255.255.255");
            } catch (Exception localException) {
              GUINetNewClient.access$002(null);
              System.out.println(localException.getMessage());
              localException.printStackTrace();
              return true;
            }
          }
          GUINetNewClient.this.wTable.showWindow();
          GUINetNewClient.this.bSearch.hideWindow();
          GUINetNewClient.this.bExistSearch = true;
          setPosSize();
          MsgAddListener.post(64, NetEnv.cur(), Main.state(), null);
          GUINetNewClient.this.onMsgTimeout();
        }
        return true;
      }if (paramGWindow == GUINetNewClient.this.bExit) {
        CmdEnv.top().exec("socket LISTENER 0");
        CmdEnv.top().exec("socket udp DESTROY LOCALPORT " + Config.cur.netLocalPort);
        ((NetUser)NetEnv.host()).reset();
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      if (GUINetNewClient.this.bExistSearch)
        GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(368.0F), x1024(896.0F), 2.0F);
      else {
        GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(208.0F), x1024(480.0F), 2.0F);
      }
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      if (GUINetNewClient.this.bExistSearch) {
        draw(x1024(304.0F), y1024(256.0F), x1024(352.0F), y1024(32.0F), 1, GUINetNewClient.this.i18n("netnc.Server"));
        draw(x1024(672.0F), y1024(400.0F), x1024(192.0F), y1024(48.0F), 2, GUINetNewClient.this.i18n("netnc.Join"));
        draw(x1024(96.0F), y1024(400.0F), x1024(160.0F), y1024(48.0F), 0, GUINetNewClient.this.i18n("netnc.Back"));
      } else {
        if (GUINetNewClient.this.bSearch.isVisible())
          draw(x1024(96.0F), y1024(32.0F), x1024(416.0F), y1024(48.0F), 0, GUINetNewClient.this.i18n("netnc.Search"));
        if ((!USGS.isUsed()) && (Main.cur().netGameSpy == null))
        {
          draw(x1024(96.0F), y1024(96.0F), x1024(352.0F), y1024(32.0F), 1, GUINetNewClient.this.i18n("netnc.Server"));
        }if (GUINetNewClient.this.bJoin.isVisible())
          draw(x1024(288.0F), y1024(240.0F), x1024(160.0F), y1024(48.0F), 2, GUINetNewClient.this.i18n("netnc.Join"));
        draw(x1024(96.0F), y1024(240.0F), x1024(136.0F), y1024(48.0F), 0, (USGS.isUsed()) || (Main.cur().netGameSpy != null) ? GUINetNewClient.this.i18n("main.Quit") : GUINetNewClient.this.i18n("netnc.MainMenu"));
      }
    }

    public void setPosSize()
    {
      if (GUINetNewClient.this.bExistSearch)
        set1024PosSize(32.0F, 144.0F, 960.0F, 480.0F);
      else {
        set1024PosSize(240.0F, 240.0F, 544.0F, 320.0F);
      }

      if (GUINetNewClient.this.bExistSearch) {
        GUINetNewClient.this.wTable.set1024PosSize(32.0F, 32.0F, 892.0F, 192.0F);
        GUINetNewClient.this.wEdit.setPosSize(x1024(352.0F), y1024(304.0F), x1024(256.0F), y1024(32.0F));
        GUINetNewClient.this.bJoin.setPosC(x1024(904.0F), y1024(424.0F));
        GUINetNewClient.this.bExit.setPosC(x1024(56.0F), y1024(424.0F));
      } else {
        GUINetNewClient.this.bSearch.setPosC(x1024(56.0F), y1024(56.0F));
        GUINetNewClient.this.wEdit.setPosSize(x1024(144.0F), y1024(144.0F), x1024(256.0F), y1024(32.0F));
        GUINetNewClient.this.bJoin.setPosC(x1024(488.0F), y1024(264.0F));
        GUINetNewClient.this.bExit.setPosC(x1024(56.0F), y1024(264.0F));
      }
    }
  }

  public class Table extends GWindowTable
  {
    public HashMap serverMap = new HashMap();
    public ArrayList adrList = new ArrayList();

    public int countRows() { return this.adrList != null ? this.adrList.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      String str1 = (String)this.adrList.get(paramInt1);
      GUINetNewClient.Item localItem = (GUINetNewClient.Item)this.serverMap.get(str1);
      String str2 = null;
      int i = 0;
      switch (paramInt2) { case 0:
        str2 = str1; break;
      case 1:
        str2 = localItem.serverName; break;
      case 2:
        str2 = "" + localItem.ping;
        i = 1;
        break;
      case 3:
        str2 = "" + localItem.existUsers + "/" + localItem.maxUsers;
        i = 1;
        break;
      case 4:
        if (localItem.bServer) {
          if (localItem.bCoop) str2 = (localItem.bProtected ? "* " : "  ") + GUINetNewClient.this.i18n("netnc.Cooperative"); else
            str2 = (localItem.bProtected ? "* " : "  ") + GUINetNewClient.this.i18n("netnc.Dogfight");
        }
        else if (localItem.bCoop) str2 = (localItem.bProtected ? "* " : "  ") + GUINetNewClient.this.i18n("netnc.Cooperative_routing"); else {
          str2 = (localItem.bProtected ? "* " : "  ") + GUINetNewClient.this.i18n("netnc.Dogfight_routing");
        }
      }

      if (str2 != null)
        if (paramBoolean) {
          setCanvasColorWHITE();
          draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str2);
        } else {
          setCanvasColorBLACK();
          draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str2);
        }
    }

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
      if (this.jdField_selectRow_of_type_Int >= 0) {
        String str = (String)this.adrList.get(this.jdField_selectRow_of_type_Int);
        GUINetNewClient.this.wEdit.setValue(str, false);
      }
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelectRow = true;
      addColumn(I18N.gui("netnc.Address"), null);

      addColumn(I18N.gui("netnc.Name"), null);
      addColumn(I18N.gui("netnc.Ping"), null);
      addColumn(I18N.gui("netnc.Users"), null);
      addColumn(I18N.gui("netnc.Type"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(8.0F);

      getColumn(1).setRelativeDx(10.0F);
      getColumn(2).setRelativeDx(4.0F);
      getColumn(3).setRelativeDx(4.0F);
      getColumn(4).setRelativeDx(8.0F);
      alignColumns();
      this.bNotify = true;
      this.wClient.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super();
    }
  }

  static class Item
  {
    public NetAddress adr;
    public int port;
    public int ping;
    public String ver;
    public boolean bServer;
    public int iServerType;
    public boolean bProtected;
    public boolean bDedicated;
    public boolean bCoop;
    public boolean bMissionStarted;
    public int maxChannels;
    public int usedChannels;
    public int maxUsers;
    public int existUsers;
    public String serverName;
  }
}