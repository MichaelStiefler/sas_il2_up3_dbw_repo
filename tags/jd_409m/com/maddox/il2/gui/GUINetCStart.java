package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetEnv;
import java.util.List;

public class GUINetCStart extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bPrev;
  public GUIButton bKick;
  public GUIButton bFly;
  public Table wTable;

  public void _enter()
  {
    if (this.bFly != null)
      this.bFly.hideWindow();
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public GUINetCStart(int paramInt, GWindowRoot paramGWindowRoot)
  {
    super(paramInt);

    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netcstart.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    if (paramInt == 47) {
      this.bKick = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
      this.bFly = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    }

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

      if (paramGWindow == GUINetCStart.this.bPrev) {
        if (GUINetCStart.this.bKick == null) {
          GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
          localGUINetClientGuard.dlgDestroy(new GUINetCStart.1(this));
        }
        else
        {
          Mission.cur().destroy();
          Main.stateStack().change(Main.cur().netServerParams.bNGEN ? 69 : 38);
        }
        return true;
      }if (paramGWindow == GUINetCStart.this.bKick) {
        int i = GUINetCStart.this.wTable.selectRow;
        if ((i >= 0) && (i < NetEnv.hosts().size())) {
          NetUser localNetUser = (NetUser)NetEnv.hosts().get(i);
          ((NetUser)NetEnv.host()).kick(localNetUser);
        }
        return true;
      }
      if (paramGWindow == GUINetCStart.this.bFly)
      {
        Main.cur().netServerParams.doMissionCoopEnter();

        GUI.unActivate();
        HotKeyCmd.exec("aircraftView", "CockpitView");

        Main.stateStack().change(49);
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void preRender() {
      if ((GUINetCStart.this.bFly != null) && 
        (!GUINetCStart.this.bFly.isVisible())) {
        int i = 1;
        if (NetEnv.hosts() != null) {
          List localList = NetEnv.hosts();
          for (int j = 0; j < localList.size(); j++) {
            NetUser localNetUser = (NetUser)localList.get(j);
            if (!localNetUser.isWaitStartCoopMission()) {
              i = 0;
              break;
            }
          }
        }
        if (i != 0) {
          GUINetCStart.this.bFly.showWindow();
        }
      }
      super.preRender();
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(544.0F), x1024(512.0F), 2.0F);

      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      if (GUINetCStart.this.bKick != null) {
        draw(x1024(96.0F), y1024(656.0F), x1024(192.0F), y1024(48.0F), 0, GUINetCStart.this.i18n("netcstart.EndMission"));
        draw(x1024(96.0F), y1024(576.0F), x1024(240.0F), y1024(48.0F), 0, GUINetCStart.this.i18n("netcstart.KickPlayer"));
        if (GUINetCStart.this.bFly.isVisible())
          draw(x1024(304.0F), y1024(656.0F), x1024(176.0F), y1024(48.0F), 2, GUINetCStart.this.i18n("netcstart.Fly"));
        else
          draw(x1024(304.0F), y1024(656.0F), x1024(208.0F), y1024(48.0F), 2, GUINetCStart.this.i18n("netcstart.Wait"));
      } else {
        draw(x1024(96.0F), y1024(656.0F), x1024(192.0F), y1024(48.0F), 0, GUINetCStart.this.i18n("netcstart.Disconnect"));
        draw(x1024(304.0F), y1024(656.0F), x1024(208.0F), y1024(48.0F), 2, GUINetCStart.this.i18n("netcstart.Wait"));
      }
    }

    public void setPosSize() {
      set1024PosSize(224.0F, 32.0F, 576.0F, 736.0F);
      GUINetCStart.this.wTable.set1024PosSize(32.0F, 32.0F, 512.0F, 480.0F);
      GUINetCStart.this.bPrev.setPosC(x1024(56.0F), y1024(680.0F));
      if (GUINetCStart.this.bKick != null) {
        GUINetCStart.this.bKick.setPosC(x1024(56.0F), y1024(600.0F));
        GUINetCStart.this.bFly.setPosC(x1024(520.0F), y1024(680.0F));
      }
    }
  }

  public class Table extends GWindowTable
  {
    public int countRows()
    {
      return NetEnv.hosts() == null ? 0 : NetEnv.hosts().size();
    }
    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      if (paramBoolean) setCanvasColorWHITE(); else
        setCanvasColorBLACK();
      NetUser localNetUser = (NetUser)NetEnv.hosts().get(paramInt1);
      String str = null;
      int i = 0;
      switch (paramInt2) {
      case 0:
        str = localNetUser.uniqueName();
        break;
      case 1:
        i = 1;
        str = "" + localNetUser.ping;
        break;
      case 2:
        if (!localNetUser.isWaitStartCoopMission()) break;
        str = I18N.gui("netcstart.Ready");
      }

      if (str != null)
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str); 
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelecting = true;
      this.bSelectRow = true;
      addColumn(I18N.gui("netcstart.Player"), null);
      addColumn(I18N.gui("netcstart.Ping"), null);
      addColumn(I18N.gui("netcstart.State"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(5.0F);
      getColumn(1).setRelativeDx(2.0F);
      getColumn(2).setRelativeDx(3.0F);
      alignColumns();
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super();
    }
  }
}