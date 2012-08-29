package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.USGS;

public class GUINetClient extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bExit;

  public void _enter()
  {
    if (Main.cur().netChannelListener == null)
      new GUINetClientGuard();
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUINetClient(GWindowRoot paramGWindowRoot)
  {
    super(36);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netc.info");
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

      if (paramGWindow == GUINetClient.this.bExit) {
        GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
        localGUINetClientGuard.dlgDestroy(new GUINetClientGuard.DestroyExec() {
          public void destroy(GUINetClientGuard paramGUINetClientGuard) { if ((USGS.isUsed()) || (Main.cur().netGameSpy != null))
            {
              Main.doGameExit();
            }
            else paramGUINetClientGuard.destroy(true);
          }
        });
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(208.0F), x1024(480.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(32.0F), y1024(32.0F), x1024(480.0F), y1024(48.0F), 1, GUINetClient.this.i18n("netc.WaitLoading"));
      draw(x1024(32.0F), y1024(96.0F), x1024(480.0F), y1024(96.0F), guardMessage(), 3);
      draw(x1024(96.0F), y1024(240.0F), x1024(136.0F), y1024(48.0F), 0, (USGS.isUsed()) || (Main.cur().netGameSpy != null) ? GUINetClient.this.i18n("main.Quit") : GUINetClient.this.i18n("netc.Disconnect"));
    }

    private String guardMessage()
    {
      GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
      if ((localGUINetClientGuard == null) || (localGUINetClientGuard.lastNetMissionInfo == null)) return "";
      return localGUINetClientGuard.lastNetMissionInfo;
    }

    public void setPosSize() {
      set1024PosSize(240.0F, 240.0F, 544.0F, 320.0F);

      GUINetClient.this.bExit.setPosC(x1024(56.0F), y1024(264.0F));
    }
  }
}