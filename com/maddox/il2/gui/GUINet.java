package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.MainWin32;
import com.maddox.rts.RTSConf;

public class GUINet extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bChannel;
  public GUIButton bUSGS;
  public GUIButton bClient;
  public GUIButton bServer;
  public GUIButton bExit;
  public String pathUSGSExe;

  public void _enter()
  {
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUINet(GWindowRoot paramGWindowRoot)
  {
    super(33);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("net.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.pathUSGSExe = MainWin32.RegistryGetStringLM("SOFTWARE\\Ubi Soft\\Game Service\\", "Directory");
    if (this.pathUSGSExe != null) {
      if (!this.pathUSGSExe.endsWith("\\")) this.pathUSGSExe += "\\";
      String str = MainWin32.RegistryGetStringLM("SOFTWARE\\Ubi Soft\\Game Service\\", "Exe");
      if (str != null) this.pathUSGSExe += str; else {
        this.pathUSGSExe = null;
      }
    }

    if (this.pathUSGSExe != null)
      this.bUSGS = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bClient = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bServer = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUINet.this.bClient) {
        Main.stateStack().change(34);
        return true;
      }
      if (paramGWindow == GUINet.this.bServer) {
        Main.stateStack().change(35);
        return true;
      }
      if (paramGWindow == GUINet.this.bExit) {
        Main.stateStack().pop();
        return true;
      }if (paramGWindow == GUINet.this.bChannel) {
        Main.stateStack().pop();
        String str = MainWin32.RegistryGetAppPath("BBGChan.exe");
        if (str != null)
          RTSConf.cur.execPostProcessCmd = (str + "\\BBGChan.exe LobbyIL2.ini");
        Main.doGameExit();
        return true;
      }if (paramGWindow == GUINet.this.bUSGS) {
        Main.stateStack().pop();

        RTSConf.cur.execPostProcessCmd = (GUINet.this.pathUSGSExe + " -l +sg \"IL2FB\"");
        Main.doGameExit();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      float f = GUINet.this.bChannel == null ? 0 : 80;
      if (GUINet.this.bUSGS != null) f += 80.0F;
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(192.0F + f), x1024(448.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      if (GUINet.this.bChannel != null)
        draw(x1024(96.0F), y1024(32.0F), x1024(384.0F), y1024(48.0F), 0, GUINet.this.i18n("net.BBGC"));
      if (GUINet.this.bUSGS != null)
        draw(x1024(96.0F), y1024(32.0F), x1024(384.0F), y1024(48.0F), 0, GUINet.this.i18n("net.USGS"));
      draw(x1024(96.0F), y1024(32.0F + f), x1024(384.0F), y1024(48.0F), 0, GUINet.this.i18n("net.Client"));
      draw(x1024(96.0F), y1024(112.0F + f), x1024(384.0F), y1024(48.0F), 0, GUINet.this.i18n("net.Server"));
      draw(x1024(96.0F), y1024(224.0F + f), x1024(384.0F), y1024(48.0F), 0, GUINet.this.i18n("net.MainMenu"));
    }

    public void setPosSize()
    {
      float f = GUINet.this.bChannel == null ? 0 : 80;
      if (GUINet.this.bUSGS != null) f += 80.0F;
      set1024PosSize(272.0F, 256.0F - f / 2.0F, 512.0F, 304.0F + f);

      if (GUINet.this.bChannel != null)
        GUINet.this.bChannel.setPosC(x1024(56.0F), y1024(56.0F));
      if (GUINet.this.bUSGS != null)
        GUINet.this.bUSGS.setPosC(x1024(56.0F), y1024(56.0F));
      GUINet.this.bClient.setPosC(x1024(56.0F), y1024(56.0F + f));
      GUINet.this.bServer.setPosC(x1024(56.0F), y1024(136.0F + f));
      GUINet.this.bExit.setPosC(x1024(56.0F), y1024(248.0F + f));
    }
  }
}