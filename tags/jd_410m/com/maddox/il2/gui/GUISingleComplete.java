package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.CmdEnv;

public class GUISingleComplete extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bExit;
  public GUIButton bBack;

  public void enterPush(GameState paramGameState)
  {
    GUI.activate();
    this.client.showWindow();
  }

  public void _leave() {
    this.client.hideWindow();
  }

  public GUISingleComplete(GWindowRoot paramGWindowRoot)
  {
    super(19);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("single.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bExit = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));

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

      if (paramGWindow == GUISingleComplete.this.bExit) {
        CmdEnv.top().exec("mission END");
        Main.stateStack().pop();
        Main.stateStack().change(6);
        return true;
      }
      if (paramGWindow == GUISingleComplete.this.bBack) {
        GUISingleComplete.this.client.hideWindow();
        Main.stateStack().pop();
        GUI.unActivate();
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
      if (World.cur().targetsGuard.isTaskComplete())
        draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(48.0F), 0, GUISingleComplete.this.i18n("single.Complete"));
      else
        draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(48.0F), 0, GUISingleComplete.this.i18n("single.Failed"));
      draw(x1024(96.0F), y1024(336.0F), x1024(224.0F), y1024(48.0F), 0, GUISingleComplete.this.i18n("single.Quit"));
      draw(x1024(96.0F), y1024(448.0F), x1024(224.0F), y1024(48.0F), 0, GUISingleComplete.this.i18n("single.Return2Miss"));
    }

    public void setPosSize()
    {
      set1024PosSize(350.0F, 112.0F, 352.0F, 528.0F);

      GUISingleComplete.this.bExit.setPosC(x1024(56.0F), y1024(360.0F));
      GUISingleComplete.this.bBack.setPosC(x1024(56.0F), y1024(472.0F));
    }
  }
}