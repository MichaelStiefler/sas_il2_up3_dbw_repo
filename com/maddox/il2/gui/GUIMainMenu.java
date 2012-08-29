package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.USGS;

public class GUIMainMenu extends GameState
{
  GWindowFramed setup;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GTexRegion texReg12B;
  public GUIPocket pPilotName;
  public GUIButton bInfo;
  public GUIButton bPilot;
  public GUIButton bControls;
  public GUIButton bSingle;
  public GUIButton bCampaigns;
  public GUIButton bQuick;
  public GUIButton bTraining;
  public GUIButton bRecord;
  public GUIButton bBuilder;
  public GUIButton bMultiplay;
  public GUIButton bSetup;
  public GUIButton bCredits;
  public GUIButton bExit;

  public void enterPop(GameState paramGameState)
  {
    if (USGS.isUsing()) {
      Main.doGameExit();
      return;
    }

    if (Main.cur().netGameSpy != null) {
      Main.cur().netGameSpy.sendExiting();
      Main.doGameExit();
      return;
    }

    _enter();
  }

  public void _enter() {
    ((GUIRoot)this.dialogClient.root).setBackCountry(null, null);
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUIMainMenu(GWindowRoot paramGWindowRoot)
  {
    super(2);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("main.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bCampaigns = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSingle = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bQuick = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bMultiplay = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.bBuilder = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.bTraining = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bRecord = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.bPilot = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bControls = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.bInfo = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bCredits = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.bSetup = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

    this.setup = WindowPreferences.create(paramGWindowRoot);
    this.setup.hideWindow();

    this.texReg12B = new GTexRegion("GUI/game/staticelements.mat", 128.0F, 32.0F, 96.0F, 48.0F);
    this.pPilotName = new GUIPocket(this.dialogClient, "Demo 'demo' Player");

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

      if (paramGWindow == GUIMainMenu.this.bInfo) {
        Main.stateStack().push(15);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bPilot) {
        Main.stateStack().push(1);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bControls) {
        Main.stateStack().push(20);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bSingle)
      {
        Main.stateStack().push(3);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bCampaigns)
      {
        Main.stateStack().push(27);

        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bQuick) {
        Main.stateStack().push(14);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bTraining)
      {
        Main3D.cur3D().viewSet_Save();
        Main.stateStack().push(56);

        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bRecord) {
        Main3D.cur3D().viewSet_Save();
        Main.stateStack().push(7);
        return true;
      }

      if (paramGWindow == GUIMainMenu.this.bBuilder)
      {
        Main.stateStack().push(18);

        return true;
      }

      if (paramGWindow == GUIMainMenu.this.bMultiplay) {
        Main.stateStack().push(33);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bSetup) {
        Main.stateStack().push(10);
        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bCredits) {
        Main.stateStack().push(16);

        return true;
      }
      if (paramGWindow == GUIMainMenu.this.bExit) {
        new GUIMainMenu.1(this, this.root, 20.0F, true, GUIMainMenu.this.i18n("main.ConfirmQuit"), GUIMainMenu.this.i18n("main.ReallyQuit"), 1, 0.0F);

        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(336.0F), x1024(336.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(496.0F), x1024(336.0F), 2.0F);

      GUISeparate.draw(this, GColor.Gray, x1024(384.0F), y1024(32.0F), 2.0F, y1024(560.0F));

      GUISeparate.draw(this, GColor.Gray, x1024(400.0F), y1024(336.0F), x1024(288.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(400.0F), y1024(496.0F), x1024(288.0F), 2.0F);

      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(104.0F), y1024(64.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.SingleMissions"));
      draw(x1024(104.0F), y1024(120.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.PilotCareer"));
      draw(x1024(104.0F), y1024(176.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.Multiplay"));
      draw(x1024(104.0F), y1024(232.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.Quick"));

      draw(x1024(104.0F), y1024(288.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.Builder"));

      draw(x1024(104.0F), y1024(376.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.Training"));
      draw(x1024(104.0F), y1024(457.0F) - M(1.0F), x1024(256.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.PlayTrack"));

      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      localGUILookAndFeel.drawBevel(this, x1024(152.0F), y1024(560.0F) - M(1.0F), x1024(128.0F), y1024(16.0F) + M(2.0F), localGUILookAndFeel.bevelRed, localGUILookAndFeel.basicelements);
      draw(x1024(168.0F), y1024(568.0F) - M(1.0F), x1024(112.0F), M(2.0F), 0, GUIMainMenu.this.i18n("main.Quit"));

      localGUILookAndFeel.drawBevel(this, x1024(416.0F), y1024(40.0F), x1024(264.0F), y1024(64.0F), localGUILookAndFeel.bevelBlacked, localGUILookAndFeel.basicelements);
      setCanvasFont(1);
      draw(x1024(416.0F), y1024(40.0F), x1024(272.0F), y1024(64.0F), 1, GUIMainMenu.this.i18n("main.PilotSelector"));
      setCanvasFont(0);

      draw(x1024(416.0F), y1024(148.0F) - M(1.0F), x1024(200.0F), M(2.0F), 2, GUIMainMenu.this.i18n("main.Pilot"));
      draw(x1024(416.0F), y1024(288.0F) - M(1.0F), x1024(200.0F), M(2.0F), 2, GUIMainMenu.this.i18n("main.Controls"));

      draw(x1024(416.0F), y1024(376.0F) - M(1.0F), x1024(200.0F), M(2.0F), 2, GUIMainMenu.this.i18n("main.ViewObjects"));
      draw(x1024(416.0F), y1024(457.0F) - M(1.0F), x1024(200.0F), M(2.0F), 2, GUIMainMenu.this.i18n("main.Credits"));
      draw(x1024(416.0F), y1024(568.0F) - M(1.0F), x1024(200.0F), M(2.0F), 2, GUIMainMenu.this.i18n("main.Setup"));
    }

    public void setPosSize()
    {
      set1024PosSize(144.0F, 80.0F, 720.0F, 624.0F);

      GUIMainMenu.this.bSingle.setPosC(x1024(56.0F), y1024(64.0F));
      GUIMainMenu.this.bCampaigns.setPosC(x1024(56.0F), y1024(120.0F));
      GUIMainMenu.this.bMultiplay.setPosC(x1024(56.0F), y1024(176.0F));
      GUIMainMenu.this.bQuick.setPosC(x1024(56.0F), y1024(232.0F));

      GUIMainMenu.this.bBuilder.setPosC(x1024(56.0F), y1024(288.0F));
      GUIMainMenu.this.bTraining.setPosC(x1024(56.0F), y1024(376.0F));
      GUIMainMenu.this.bRecord.setPosC(x1024(56.0F), y1024(457.0F));
      GUIMainMenu.this.bExit.setPosC(x1024(120.0F), y1024(568.0F));

      GUIMainMenu.this.bPilot.setPosC(x1024(664.0F), y1024(148.0F));
      GUIMainMenu.this.pPilotName.setPosSize(x1024(424.0F), y1024(204.0F), x1024(264.0F), y1024(32.0F));
      GUIMainMenu.this.bControls.setPosC(x1024(664.0F), y1024(288.0F));

      GUIMainMenu.this.bInfo.setPosC(x1024(664.0F), y1024(376.0F));
      GUIMainMenu.this.bCredits.setPosC(x1024(664.0F), y1024(457.0F));
      GUIMainMenu.this.bSetup.setPosC(x1024(664.0F), y1024(568.0F));
    }
  }
}