package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;

public class GUISetup3D extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUISwitchN sTexQual;
  public GUISwitchN sTexMip;
  public SwitchSky sTexSky;
  public GUISwitchN sForest;
  public GUISwitchN sLandDetails;
  public GUISwitchN sLandShading;
  public GUISwitchN sDiffuseLight;
  public GUISwitchN sSpecularLight;
  public GUISwitchN sShadows;
  public GUISwitchN sSpecular;
  public GUISwitchN sDynamicalLights;
  public GUISwitchBox sSmothBorder;
  public GUIButton bExit;
  public GTexRegion tex3;
  public GTexRegion tex4;

  public void _enter()
  {
    this.sTexSky.refresh();
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUISetup3D(GWindowRoot paramGWindowRoot)
  {
    super(11);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("setup3d.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.sTexQual = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 2, 4, 6 }, (CfgInt)CfgTools.get("TexQual"), true)));

    this.sTexMip = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 2, 4, 6 }, (CfgInt)CfgTools.get("TexMipFilter"), true)));

    this.sTexSky = ((SwitchSky)this.dialogClient.addControl(new SwitchSky(this.dialogClient, new int[] { 0, 2, 4, 6 }, (CfgInt)CfgTools.get("Sky"), true)));

    this.sForest = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 3, 6 }, (CfgInt)CfgTools.get("Forest"), true)));

    this.sLandDetails = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 3, 6 }, (CfgInt)CfgTools.get("LandDetails"), true)));

    this.sLandShading = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 2, 4, 6 }, (CfgInt)CfgTools.get("LandShading"), true)));

    this.sDiffuseLight = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 3, 6 }, (CfgInt)CfgTools.get("DiffuseLight"), true)));

    this.sSpecularLight = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 3, 6 }, (CfgInt)CfgTools.get("SpecularLight"), true)));

    this.sShadows = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 2, 4, 6 }, (CfgInt)CfgTools.get("Shadows"), true)));

    this.sSpecular = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch7(this.dialogClient, new int[] { 0, 2, 4, 6 }, (CfgInt)CfgTools.get("Specular"), true)));

    this.sDynamicalLights = ((GUISwitchN)this.dialogClient.addControl(new GUISwitch3(this.dialogClient, new int[] { 0, 2 }, (CfgInt)CfgTools.get("DynamicalLights"), true)));

    this.sSmothBorder = ((GUISwitchBox)this.dialogClient.addControl(new GUISwitchBox(this.dialogClient, (CfgFlags)CfgTools.get("ShadowsFlags"), 0, true)));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.tex3 = new GTexRegion("GUI/game/staticelements.mat", 64.0F, 48.0F, 64.0F, 64.0F);
    this.tex4 = new GTexRegion("GUI/game/staticelements.mat", 0.0F, 48.0F, 64.0F, 64.0F);

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

      if (paramGWindow == GUISetup3D.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(52.0F), x1024(112.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(384.0F), y1024(52.0F), x1024(224.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(832.0F), y1024(52.0F), x1024(112.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(272.0F), y1024(308.0F), x1024(224.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(453.0F), x1024(224.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(688.0F), y1024(420.0F), x1024(256.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(476.0F), x1024(48.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(608.0F), y1024(476.0F), x1024(32.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(272.0F), y1024(532.0F), x1024(224.0F), 2.0F);

      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(52.0F), 2.0F, y1024(400.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(272.0F), y1024(308.0F), 2.0F, y1024(224.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(496.0F), y1024(52.0F), 2.0F, y1024(258.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(444.0F), 2.0F, y1024(34.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(496.0F), y1024(500.0F), 2.0F, y1024(34.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(640.0F), y1024(444.0F), 2.0F, y1024(34.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(943.0F), y1024(52.0F), 2.0F, y1024(370.0F));

      draw(x1024(176.0F), y1024(52.0F) - M(1.0F), x1024(192.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.TextureDetail"));
      draw(x1024(624.0F), y1024(52.0F) - M(1.0F), x1024(192.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Light"));

      setCanvasColorWHITE();
      draw(x1024(128.0F), y1024(84.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex4);
      draw(x1024(128.0F), y1024(228.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex4);
      draw(x1024(128.0F), y1024(340.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex4);
      draw(x1024(352.0F), y1024(84.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex3);
      draw(x1024(352.0F), y1024(180.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex3);
      draw(x1024(352.0F), y1024(340.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex4);
      draw(x1024(576.0F), y1024(84.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex3);
      draw(x1024(576.0F), y1024(192.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex3);
      draw(x1024(560.0F), y1024(340.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex4);
      draw(x1024(800.0F), y1024(196.0F), x1024(64.0F), y1024(64.0F), GUISetup3D.this.tex4);

      setCanvasColor(GColor.Gray);
      draw(x1024(96.0F), y1024(164.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.TextureQuality"));
      draw(x1024(320.0F), y1024(164.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Forest"));
      draw(x1024(96.0F), y1024(308.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.TextureFilter"));
      draw(x1024(320.0F), y1024(260.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Landscape"));
      draw(x1024(96.0F), y1024(420.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Sky"));
      draw(x1024(320.0F), y1024(420.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.LandLighting"));
      draw(x1024(544.0F), y1024(164.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Diffuse"));
      draw(x1024(544.0F), y1024(276.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.SpecularLight"));
      draw(x1024(768.0F), y1024(276.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Specular"));
      draw(x1024(528.0F), y1024(420.0F) - M(1.0F), x1024(128.0F), M(2.0F), 1, GUISetup3D.this.i18n("setup3d.Shadows"));

      draw(x1024(735.0F), y1024(128.0F), x1024(112.0F), y1024(32.0F), 2, GUISetup3D.this.i18n("setup3d.Dynamic"));
      draw(x1024(464.0F), y1024(464.0F), x1024(112.0F), y1024(32.0F), 2, GUISetup3D.this.i18n("setup3d.Smooth"));

      draw(x1024(112.0F), y1024(508.0F), x1024(208.0F), y1024(32.0F), 0, GUISetup3D.this.i18n("setup3d.Back"));
    }

    public void setPosSize()
    {
      set1024PosSize(16.0F, 124.0F, 992.0F, 580.0F);

      GUISetup3D.this.sTexQual.setPosC(x1024(160.0F), y1024(116.0F));
      GUISetup3D.this.sTexMip.setPosC(x1024(160.0F), y1024(260.0F));
      GUISetup3D.this.sTexSky.setPosC(x1024(160.0F), y1024(372.0F));
      GUISetup3D.this.sForest.setPosC(x1024(384.0F), y1024(116.0F));
      GUISetup3D.this.sLandDetails.setPosC(x1024(384.0F), y1024(212.0F));
      GUISetup3D.this.sLandShading.setPosC(x1024(384.0F), y1024(372.0F));
      GUISetup3D.this.sDiffuseLight.setPosC(x1024(608.0F), y1024(116.0F));
      GUISetup3D.this.sSpecularLight.setPosC(x1024(608.0F), y1024(228.0F));
      GUISetup3D.this.sShadows.setPosC(x1024(592.0F), y1024(372.0F));
      GUISetup3D.this.sSpecular.setPosC(x1024(832.0F), y1024(228.0F));
      GUISetup3D.this.sDynamicalLights.setPosC(x1024(886.0F), y1024(132.0F));
      GUISetup3D.this.sSmothBorder.setPosC(x1024(456.0F), y1024(476.0F));

      GUISetup3D.this.bExit.setPosC(x1024(64.0F), y1024(524.0F));
    }
  }

  public class SwitchSky extends GUISwitch7
  {
    public void refresh()
    {
      super.refresh();
      if (Mission.isNet())
        GUISetup3D.this.sTexSky.enable[0] = (!World.cur().diffCur.Clouds ? 1 : false); 
    }

    public SwitchSky(GWindow paramArrayOfInt, int[] paramCfgInt, CfgInt paramBoolean, boolean arg5) {
      super(paramCfgInt, paramBoolean, bool);
    }
  }
}