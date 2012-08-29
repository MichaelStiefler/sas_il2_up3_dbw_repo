package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;

public class GUIView extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bAir;
  public GUIButton bTank;
  public GUIButton bVechicle;
  public GUIButton bChip;
  public GUIButton bExit;

  public void _enter()
  {
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUIView(GWindowRoot paramGWindowRoot)
  {
    super(15);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("view.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bAir = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bTank = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bVechicle = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bChip = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

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

      if (paramGWindow == GUIView.this.bAir) {
        GUIObjectInspector.type = "air";
        Main.stateStack().push(22);
        return true;
      }
      if (paramGWindow == GUIView.this.bTank) {
        GUIObjectInspector.type = "tanks";
        Main.stateStack().push(22);
        return true;
      }
      if (paramGWindow == GUIView.this.bVechicle) {
        GUIObjectInspector.type = "vehicle";
        Main.stateStack().push(22);
        return true;
      }
      if (paramGWindow == GUIView.this.bChip) {
        GUIObjectInspector.type = "ship";
        Main.stateStack().push(22);

        return true;
      }

      if (paramGWindow == GUIView.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(368.0F), x1024(320.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(118.0F), y1024(56.0F) - M(1.0F), x1024(224.0F), M(2.0F), 0, GUIView.this.i18n("view.Aircraft"));
      draw(x1024(118.0F), y1024(120.0F) - M(1.0F), x1024(224.0F), M(2.0F), 0, GUIView.this.i18n("view.Tanks"));
      draw(x1024(118.0F), y1024(184.0F) - M(1.0F), x1024(224.0F), M(2.0F), 0, GUIView.this.i18n("view.Vehicles"));
      draw(x1024(118.0F), y1024(248.0F) - M(1.0F), x1024(224.0F), M(2.0F), 0, GUIView.this.i18n("view.Ships"));

      draw(x1024(118.0F), y1024(425.0F) - M(1.0F), x1024(224.0F), M(2.0F), 0, GUIView.this.i18n("view.MainMenu"));
    }

    public void setPosSize() {
      set1024PosSize(334.0F, 176.0F, 384.0F, 480.0F);
      GUIView.this.bAir.setPosC(x1024(70.0F), y1024(56.0F));
      GUIView.this.bTank.setPosC(x1024(70.0F), y1024(120.0F));
      GUIView.this.bVechicle.setPosC(x1024(70.0F), y1024(184.0F));
      GUIView.this.bChip.setPosC(x1024(70.0F), y1024(248.0F));

      GUIView.this.bExit.setPosC(x1024(70.0F), y1024(425.0F));
    }
  }
}