package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.il2.ai.Scores;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetMissionTrack;

public class GUIStat extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bSave;
  public GUIButton bNext;
  public GUIButton bRefly;
  public GUIButton bExit;
  GTexRegion texStat;
  public FixedClient airFixed;
  public FixedClient groundFixed;
  public ScrollClient airScroll;
  public ScrollClient groundScroll;
  public int iArmy = 0;
  GTexRegion[][] texAir = new GTexRegion[2][3];
  GTexRegion texTank;
  GTexRegion texCar;
  GTexRegion texAAA;
  GTexRegion texTrain;
  GTexRegion texSub;
  GTexRegion texShip;
  GTexRegion texArtillery;
  GTexRegion texBridge;
  GTexRegion texAirStatic;
  private GRegion _clipReg = new GRegion();

  protected void updateScrollSizes()
  {
    this.airFixed.draw(false);
    this.airScroll.updateScrollsPos();
    this.groundFixed.draw(false);
    this.groundScroll.updateScrollsPos();
  }

  public void _enter() {
    Scores.compute();
    updateScrollSizes();
    if (NetMissionTrack.countRecorded == 0)
      this.bSave.showWindow();
    else
      this.bSave.hideWindow();
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  protected void tryShowCapturedMessage() {
    if (!World.isPlayerCaptured()) return;
    new GWindowMessageBox(this.client.root, 20.0F, true, i18n("warning.Warning"), i18n("warning.PlayerCaptured"), 3, -1.0F);
  }

  protected void doRecordSave()
  {
    Main.stateStack().push(9);
  }

  protected void doRefly()
  {
  }

  protected void doNext()
  {
  }

  protected void doExit()
  {
  }

  protected void clientRender()
  {
  }

  protected void clientSetPosSize()
  {
  }

  protected void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = "Statistic";
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.texStat = new GTexRegion("GUI/game/staticelements.mat", 0.0F, 112.0F, 64.0F, 80.0F);

    this.airScroll = new ScrollClient(this.dialogClient);
    this.airScroll.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient = ((GWindowDialogClient)this.airScroll.create(this.airFixed = new FixedClient(true)));
    this.groundScroll = new ScrollClient(this.dialogClient);
    this.groundScroll.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient = ((GWindowDialogClient)this.groundScroll.create(this.groundFixed = new FixedClient(false)));

    this.bSave = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bRefly = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bNext = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));

    this.dialogClient.activateWindow();
    this.client.hideWindow();

    localGTexture = GTexture.New("GUI/game/score.mat");

    this.texAir[0][0] = new GTexRegion(localGTexture, 0.0F, 0.0F, 48.0F, 48.0F);
    this.texAir[0][1] = new GTexRegion(localGTexture, 48.0F, 0.0F, 48.0F, 48.0F);
    this.texAir[0][2] = new GTexRegion(localGTexture, 96.0F, 0.0F, 48.0F, 48.0F);
    this.texAir[1][0] = new GTexRegion(localGTexture, 0.0F, 48.0F, 48.0F, 48.0F);
    this.texAir[1][1] = new GTexRegion(localGTexture, 48.0F, 48.0F, 48.0F, 48.0F);
    this.texAir[1][2] = new GTexRegion(localGTexture, 96.0F, 48.0F, 48.0F, 48.0F);
    this.texTrain = new GTexRegion(localGTexture, 208.0F, 0.0F, 48.0F, 32.0F);
    this.texAAA = new GTexRegion(localGTexture, 208.0F, 32.0F, 48.0F, 32.0F);
    this.texCar = new GTexRegion(localGTexture, 208.0F, 64.0F, 48.0F, 32.0F);
    this.texTank = new GTexRegion(localGTexture, 208.0F, 96.0F, 48.0F, 32.0F);
    this.texSub = new GTexRegion(localGTexture, 208.0F, 128.0F, 48.0F, 32.0F);
    this.texShip = new GTexRegion(localGTexture, 208.0F, 160.0F, 48.0F, 32.0F);
    this.texArtillery = new GTexRegion(localGTexture, 208.0F, 192.0F, 48.0F, 32.0F);
    this.texBridge = new GTexRegion(localGTexture, 208.0F, 224.0F, 48.0F, 32.0F);
    this.texAirStatic = new GTexRegion(localGTexture, 160.0F, 224.0F, 48.0F, 32.0F);
  }

  public GUIStat(int paramInt) {
    super(paramInt);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUIStat.this.bSave) {
        GUIStat.this.doRecordSave();
        return true;
      }if (paramGWindow == GUIStat.this.bRefly) {
        GUIStat.this.doRefly();
        return true;
      }if (paramGWindow == GUIStat.this.bNext) {
        GUIStat.this.doNext();
        return true;
      }if (paramGWindow == GUIStat.this.bExit) {
        GUIStat.this.doExit();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUIStat.this.clientRender();
    }
    public void setPosSize() {
      GUIStat.this.clientSetPosSize();
    }
  }

  public class ScrollClient extends GWindowScrollingDialogClient
  {
    public void render()
    {
      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      setCanvasColorWHITE();
      localGUILookAndFeel.drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);
    }
    public void doChildrensRender(boolean paramBoolean) {
      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      GUIStat.this._clipReg.x = localGUILookAndFeel.bevelComboDown.L.dx;
      GUIStat.this._clipReg.y = localGUILookAndFeel.bevelComboDown.T.dy;
      GUIStat.this._clipReg.dx = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGUILookAndFeel.bevelComboDown.L.dx - localGUILookAndFeel.bevelComboDown.R.dx);
      GUIStat.this._clipReg.dy = (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGUILookAndFeel.bevelComboDown.T.dy - localGUILookAndFeel.bevelComboDown.B.dy);
      if (pushClipRegion(GUIStat.this._clipReg)) {
        super.doChildrensRender(paramBoolean);
        popClip();
      }
    }

    public ScrollClient(GWindow arg2) {
      super();
    }
  }

  public class FixedClient extends GWindowDialogClient
  {
    boolean bAir;

    public void draw(boolean paramBoolean)
    {
      setCanvasColorWHITE();
      float f1 = 16.0F;
      float f2 = 8.0F;
      float f3 = 16.0F;
      float f4 = f1;
      float f5 = f3;
      int i;
      int j;
      if (this.bAir) {
        i = Scores.enemyAirKill;
        j = 0;
        while (i >= 100) {
          i -= 100;
          if (paramBoolean) draw(x1024(f4), y1024(f5), x1024(48.0F), x1024(48.0F), GUIStat.this.texAir[GUIStat.this.iArmy][2]);
          if (i <= 0) continue; j++;
          if (j == 10) { f4 = f1; f5 += f3 + 48.0F; j = 0; } else {
            f4 += f2 + 48.0F;
          }
        }
        while (i >= 10) {
          i -= 10;
          if (paramBoolean) draw(x1024(f4), y1024(f5), x1024(48.0F), x1024(48.0F), GUIStat.this.texAir[GUIStat.this.iArmy][1]);
          if (i <= 0) continue; j++;
          if (j == 10) { f4 = f1; f5 += f3 + 48.0F; j = 0; } else {
            f4 += f2 + 48.0F;
          }
        }
        while (i > 0) {
          i--;
          if (paramBoolean) draw(x1024(f4), y1024(f5), x1024(48.0F), y1024(48.0F), GUIStat.this.texAir[GUIStat.this.iArmy][0]);
          if (i <= 0) continue; j++;
          if (j == 10) { f4 = f1; f5 += f3 + 48.0F; j = 0; } else {
            f4 += f2 + 48.0F;
          }
        }
      } else {
        i = Scores.enemyGroundKill;
        j = 0;
        if (Scores.arrayEnemyGroundKill != null)
          for (int k = 0; k < Scores.arrayEnemyGroundKill.length; k++) {
            GTexRegion localGTexRegion = null;
            switch (Scores.arrayEnemyGroundKill[k]) { case 1:
              localGTexRegion = GUIStat.this.texTank; break;
            case 2:
              localGTexRegion = GUIStat.this.texCar; break;
            case 3:
              localGTexRegion = GUIStat.this.texArtillery; break;
            case 4:
              localGTexRegion = GUIStat.this.texAAA; break;
            case 6:
              localGTexRegion = GUIStat.this.texTrain; break;
            case 7:
              localGTexRegion = GUIStat.this.texShip; break;
            case 5:
              localGTexRegion = GUIStat.this.texBridge; break;
            case 8:
              localGTexRegion = GUIStat.this.texAirStatic;
            }
            if (localGTexRegion != null) {
              i--;
              if (paramBoolean) draw(x1024(f4), y1024(f5), x1024(48.0F), y1024(32.0F), localGTexRegion);
              if (i <= 0) continue; j++;
              if (j == 10) { f4 = f1; f5 += f3 + 32.0F; j = 0; } else {
                f4 += f2 + 48.0F;
              }
            }
          }
      }
      if (!paramBoolean) {
        if (this.bAir) setSize(x1024(f1 + 480.0F + 9.0F * f2), y1024(f5 + 48.0F + f3)); else
          setSize(x1024(f1 + 480.0F + 9.0F * f2), y1024(f5 + 32.0F + f3));
        this.parentWindow.resized();
      }
    }

    public void render() {
      draw(true);
    }

    public FixedClient(boolean arg2)
    {
      boolean bool;
      this.bAir = bool;
    }
  }
}