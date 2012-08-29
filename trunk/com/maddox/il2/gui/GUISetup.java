package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;

public class GUISetup extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bVideo;
  public GUIButton b3d;
  public GUIButton bSound;
  public GUIButton bInput;
  public GUIButton bNet;
  public GUIButton bExit;

  public void _enter()
  {
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUISetup(GWindowRoot paramGWindowRoot)
  {
    super(10);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("setup.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bVideo = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.b3d = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSound = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bInput = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNet = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUISetup.this.bVideo) {
        Main.stateStack().push(12);
        return true;
      }
      if (paramGWindow == GUISetup.this.b3d) {
        Main.stateStack().push(11);
        return true;
      }
      if (paramGWindow == GUISetup.this.bSound) {
        Main.stateStack().push(13);
        return true;
      }
      if (paramGWindow == GUISetup.this.bInput) {
        Main.stateStack().push(53);
        return true;
      }if (paramGWindow == GUISetup.this.bNet) {
        Main.stateStack().push(52);
        return true;
      }if (paramGWindow == GUISetup.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(320.0F), x1024(306.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(112.0F), y1024(64.0F), x1024(208.0F), y1024(48.0F), 0, GUISetup.this.i18n("setup.VideoModes"));
      draw(x1024(112.0F), y1024(112.0F), x1024(208.0F), y1024(48.0F), 0, GUISetup.this.i18n("setup.VideoOptions"));
      draw(x1024(112.0F), y1024(160.0F), x1024(208.0F), y1024(48.0F), 0, GUISetup.this.i18n("setup.SoundSetup"));
      draw(x1024(112.0F), y1024(208.0F), x1024(208.0F), y1024(48.0F), 0, GUISetup.this.i18n("setup.Input"));
      draw(x1024(112.0F), y1024(256.0F), x1024(208.0F), y1024(48.0F), 0, GUISetup.this.i18n("setup.Network"));
      draw(x1024(112.0F), y1024(336.0F), x1024(208.0F), y1024(48.0F), 0, GUISetup.this.i18n("setup.Back"));
    }

    public void setPosSize()
    {
      set1024PosSize(368.0F, 207.0F, 368.0F, 416.0F);

      GUISetup.this.bVideo.setPosC(x1024(64.0F), y1024(88.0F));
      GUISetup.this.b3d.setPosC(x1024(64.0F), y1024(136.0F));
      GUISetup.this.bSound.setPosC(x1024(64.0F), y1024(184.0F));
      GUISetup.this.bInput.setPosC(x1024(64.0F), y1024(232.0F));
      GUISetup.this.bNet.setPosC(x1024(64.0F), y1024(280.0F));
      GUISetup.this.bExit.setPosC(x1024(64.0F), y1024(360.0F));
    }
  }
}