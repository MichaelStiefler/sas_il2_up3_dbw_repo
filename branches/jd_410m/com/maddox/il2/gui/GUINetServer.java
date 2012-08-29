package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
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

public class GUINetServer extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bExit;
  private static GUIClient __client;
  private static boolean _bPopState;

  public static void exitServer(boolean paramBoolean)
  {
    _bPopState = paramBoolean;
    new GWindowMessageBox(__client.root, 20.0F, true, I18N.gui("main.ConfirmQuit"), I18N.gui("main.ReallyQuit"), 1, 0.0F)
    {
      public void result(int paramInt) {
        if (paramInt == 3)
          GUINetServer.access$100(GUINetServer._bPopState);
      }
    };
  }

  private static void _exitServer(boolean paramBoolean)
  {
    if (Mission.cur() != null)
      Mission.cur().destroy();
    if (Main.cur().netServerParams != null)
      Main.cur().netServerParams.destroy();
    if (Main.cur().chat != null)
      Main.cur().chat.destroy();
    if (NetEnv.cur().control != null) {
      NetEnv.cur().control.destroy();
    }
    ArrayList localArrayList = new ArrayList(NetEnv.channels());
    for (int i = 0; i < localArrayList.size(); i++) {
      NetChannel localNetChannel = (NetChannel)localArrayList.get(i);
      if (localNetChannel != null)
        localNetChannel.destroy();
    }
    CmdEnv.top().exec("socket LISTENER 0");

    i = NetEnv.socketsBlock().size();
    NetSocket localNetSocket;
    for (int j = 0; j < i; j++) {
      localNetSocket = (NetSocket)NetEnv.socketsBlock().get(j);
      localNetSocket.maxChannels = 0;
    }
    i = NetEnv.socketsNoBlock().size();
    for (j = 0; j < i; j++) {
      localNetSocket = (NetSocket)NetEnv.socketsNoBlock().get(j);
      localNetSocket.maxChannels = 0;
    }

    GUI.activate();
    if (paramBoolean)
      Main.stateStack().pop();
  }

  public void _enter() {
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUINetServer(GWindowRoot paramGWindowRoot)
  {
    super(37);
    __client = this.client = (GUIClient)paramGWindowRoot.create(new GUIClient());
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = "Server";
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUINetServer.this.bExit) {
        GUINetServer.exitServer(true);
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(320.0F), x1024(306.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(112.0F), y1024(336.0F), x1024(208.0F), y1024(48.0F), 0, "Back");
    }

    public void setPosSize()
    {
      set1024PosSize(368.0F, 207.0F, 368.0F, 416.0F);

      GUINetServer.this.bExit.setPosC(x1024(64.0F), y1024(360.0F));
    }
  }
}