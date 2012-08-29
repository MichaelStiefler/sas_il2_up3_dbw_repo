package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.GUITexture;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.campaign.Campaign;
import java.util.ArrayList;

public class GUIDGenPilot extends GameState
{
  public GUIDGenRoster.Pilot pilot = null;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Scroll wScroll;
  public Info wInfo;
  public WAwardButton bViewAward;
  public GUIButton bBack;
  public GUIButton bDetailProfile;
  private GUITexture matPhoto;
  private boolean bMatPhotoValid = false;
  private GTexture lastAward;

  public void enterPush(GameState paramGameState)
  {
    this.pilot = ((GUIDGenRoster)paramGameState).pilotCur;
    if (this.pilot.bPlayer) this.bDetailProfile.showWindow(); else
      this.bDetailProfile.hideWindow();
    this.wInfo.doComputeDy();
    this.wScroll.resized();
    String str = this.pilot.photo;
    if ((str != null) && (!str.endsWith(".bmp"))) {
      str = str + ".bmp";
    }
    if ((str != null) && (BmpUtils.bmp8Pal192x256ToTGA3(str, "PaintSchemes/Cache/photo.tga"))) {
      this.matPhoto.mat.set('\000', "PaintSchemes/Cache/photo.tga");
      this.bMatPhotoValid = true;
    } else {
      this.bMatPhotoValid = false;
    }
    this.lastAward = null;
    if (this.pilot.medals != null) {
      int i = this.pilot.medals.length - 1;
      if ((Main.cur().campaign.branch().equals("de")) && (World.cur().isHakenAllowed()))
        this.lastAward = GTexture.New("missions/campaign/de/awardh" + this.pilot.medals[i] + ".mat");
      else if ((Main.cur().campaign.branch().equals("fi")) && (World.cur().isHakenAllowed()))
        this.lastAward = GTexture.New("missions/campaign/fi/awardh" + this.pilot.medals[i] + ".mat");
      else
        this.lastAward = GTexture.New("missions/campaign/" + Main.cur().campaign.branch() + "/award" + this.pilot.medals[i] + ".mat");
      this.bViewAward.showWindow();
    } else {
      this.bViewAward.hideWindow();
    }

    this.client.activateWindow();
  }
  public void enterPop(GameState paramGameState) {
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUIDGenPilot(GWindowRoot paramGWindowRoot)
  {
    super(66);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("dgenpilot.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.matPhoto = ((GUITexture)GUITexture.New("gui/game/photo.mat"));
    this.matPhoto.mat.setLayer(0);

    this.dialogClient.create(this.wScroll = new Scroll());
    this.bViewAward = ((WAwardButton)this.wInfo.addControl(new WAwardButton(this.wInfo)));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bDetailProfile = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUIDGenPilot.this.bBack) {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUIDGenPilot.this.bDetailProfile) {
        if (GUIDGenPilot.this.pilot.bPlayer)
          Main.stateStack().push(67);
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(96.0F), y1024(656.0F), x1024(288.0F), y1024(48.0F), 0, GUIDGenPilot.this.i18n("camps.Back"));
      if (GUIDGenPilot.this.bDetailProfile.isVisible()) {
        draw(x1024(464.0F), y1024(656.0F), x1024(460.0F), y1024(48.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Detail"));
      }
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIDGenPilot.this.bBack.setPosC(x1024(56.0F), y1024(680.0F));
      GUIDGenPilot.this.bDetailProfile.setPosC(x1024(968.0F), y1024(680.0F));
      GUIDGenPilot.this.wScroll.set1024PosSize(32.0F, 32.0F, 962.0F, 560.0F);

      GUIDGenPilot.this.bViewAward.set1024PosSize(32.0F, 304.0F, 192.0F, 164.0F);
    }
  }

  public class Scroll extends GWindowScrollingDialogClient
  {
    public Scroll()
    {
    }

    public void created()
    {
      this.fixed = (GUIDGenPilot.this.wInfo = (GUIDGenPilot.Info)create(new GUIDGenPilot.Info(GUIDGenPilot.this)));
      this.fixed.bNotify = true;
      this.bNotify = true;
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void resized() {
      if (GUIDGenPilot.this.wInfo != null) {
        GUIDGenPilot.this.wInfo.computeSize();
      }
      super.resized();
      if (this.vScroll.isVisible()) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        this.vScroll.setPos(this.win.dx - lookAndFeel().getVScrollBarW() - localGBevel.R.dx, localGBevel.T.dy);
        this.vScroll.setSize(lookAndFeel().getVScrollBarW(), this.win.dy - localGBevel.T.dy - localGBevel.B.dy);
      }
    }

    public void render() {
      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.win.dx, this.win.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
    }
  }

  public class Info extends GWindowDialogClient
  {
    private GFont fnt;
    private GColor myBrass = new GColor(99, 89, 74);
    private GRegion clipRegion = new GRegion();
    private int dy = 0;

    public Info() {  }

    public void afterCreated() { super.afterCreated();
      this.fnt = GFont.New("courSmall"); }

    public void render() {
      if (GUIDGenPilot.this.pilot == null) return;
      this.clipRegion.set(this.root.C.clip);
      pushClip();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      this.clipRegion.y += localGBevel.T.dy + 2.0F;
      this.clipRegion.dy -= localGBevel.T.dy + localGBevel.B.dy + 4.0F;
      this.root.C.clip.set(this.clipRegion);

      if (GUIDGenPilot.this.bMatPhotoValid) {
        i = this.root.C.alpha;
        this.root.C.alpha = 255;
        setCanvasColorWHITE();
        draw(x1024(32.0F), y1024(32.0F), x1024(192.0F), y1024(256.0F), GUIDGenPilot.this.matPhoto, 0.0F, 0.0F, 192.0F, 256.0F);
        this.root.C.alpha = i;
      }

      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(128.0F), x1024(480.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(160.0F), x1024(480.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(192.0F), x1024(224.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(800.0F), y1024(192.0F), x1024(96.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(224.0F), x1024(480.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(256.0F), x1024(112.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(288.0F), x1024(112.0F), 1.0F);
      GUISeparate.draw(this, this.myBrass, x1024(416.0F), y1024(320.0F), x1024(112.0F), 1.0F);

      setCanvasColorBLACK();
      this.root.C.font = this.fnt;
      draw(x1024(272.0F), y1024(112.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Name"));
      draw(x1024(272.0F), y1024(144.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Surname"));
      draw(x1024(272.0F), y1024(176.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Rank"));
      draw(x1024(272.0F), y1024(208.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Place"));
      draw(x1024(272.0F), y1024(240.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Sorties"));
      draw(x1024(272.0F), y1024(272.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Kills"));
      draw(x1024(272.0F), y1024(304.0F), x1024(128.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.GroundKills"));
      draw(x1024(648.0F), y1024(176.0F), x1024(144.0F), y1024(16.0F), 2, GUIDGenPilot.this.i18n("dgenpilot.Year"));

      setCanvasFont(0);
      draw(x1024(400.0F), y1024(16.0F), x1024(368.0F), y1024(32.0F), 1, GUIDGenPilot.this.i18n("dgenpilot.Title"));
      draw(x1024(416.0F), y1024(100.0F), x1024(480.0F), y1024(32.0F), 0, GUIDGenPilot.this.pilot.firstName);
      draw(x1024(416.0F), y1024(132.0F), x1024(480.0F), y1024(32.0F), 0, GUIDGenPilot.this.pilot.lastName);
      draw(x1024(416.0F), y1024(164.0F), x1024(224.0F), y1024(32.0F), 0, "" + GUIDGenPilot.this.pilot.sRank);
      draw(x1024(800.0F), y1024(164.0F), x1024(96.0F), y1024(32.0F), 0, GUIDGenPilot.this.pilot.dateBirth);
      draw(x1024(416.0F), y1024(196.0F), x1024(480.0F), y1024(32.0F), 0, GUIDGenPilot.this.pilot.placeBirth);
      draw(x1024(416.0F), y1024(228.0F), x1024(112.0F), y1024(32.0F), 0, "" + GUIDGenPilot.this.pilot.sorties);
      draw(x1024(416.0F), y1024(260.0F), x1024(112.0F), y1024(32.0F), 0, "" + GUIDGenPilot.this.pilot.kills);
      draw(x1024(416.0F), y1024(292.0F), x1024(112.0F), y1024(32.0F), 0, "" + GUIDGenPilot.this.pilot.ground);

      int i = (int)y1024(352.0F);
      for (int k = 0; k < GUIDGenPilot.this.pilot.events.size(); k++) {
        String str = (String)GUIDGenPilot.this.pilot.events.get(k);
        int m = drawLines(x1024(288.0F), i, str, 0, str.length(), x1024(590.0F), this.root.C.font.height - this.root.C.font.descender);

        int j = (int)(i + m * (this.root.C.font.height - this.root.C.font.descender));
      }

      popClip();
    }
    public void doComputeDy() {
      this.dy = (int)y1024(352.0F);
      setCanvasFont(0);
      for (int i = 0; i < GUIDGenPilot.this.pilot.events.size(); i++) {
        String str = (String)GUIDGenPilot.this.pilot.events.get(i);
        int j = computeLines(str, 0, str.length(), x1024(590.0F));
        this.dy = (int)(this.dy + j * (this.root.C.font.height - this.root.C.font.descender));
      }
      this.dy = (int)(this.dy - 2.0F * this.root.C.font.descender);
      computeSize();
    }
    public void computeSize() {
      this.win.dx = (this.parentWindow.win.dx - lookAndFeel().getVScrollBarW());
      this.win.dy = this.parentWindow.win.dy;
      if (this.dy > this.win.dy)
        this.win.dy = this.dy;
    }
  }

  class WAwardButton extends GWindowButton
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramInt1, paramInt2);
      GUIAwards.indexIcons = GUIDGenPilot.this.pilot.medals;
      Main.stateStack().push(32);
      return true;
    }
    public void render() {
      super.render();
      if (GUIDGenPilot.this.lastAward != null) {
        setCanvasColorWHITE();
        int i = this.root.C.alpha;
        this.root.C.alpha = 255;
        if (this.bDown) draw(this.win.dx / 5.0F + 1.0F, this.win.dy / 5.0F + 1.0F, 3.0F * this.win.dx / 5.0F, 3.0F * this.win.dy / 5.0F, GUIDGenPilot.this.lastAward); else
          draw(this.win.dx / 5.0F, this.win.dy / 5.0F, 3.0F * this.win.dx / 5.0F, 3.0F * this.win.dy / 5.0F, GUIDGenPilot.this.lastAward);
        this.root.C.alpha = i;
      }
    }

    public WAwardButton(GWindow arg2) {
      super();
    }
  }
}