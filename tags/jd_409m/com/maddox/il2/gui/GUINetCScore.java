package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserLeft;
import com.maddox.il2.net.NetUserStat;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.List;

public class GUINetCScore extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bPrev;
  public Table wTable;

  private void fillListItems()
  {
    ArrayList localArrayList = this.wTable.items;
    localArrayList.clear();
    int i = 0;
    int j = 0;
    double d = 0.0D;
    while (true) {
      GUINetAircraft.Item localItem = GUINetAircraft.getItem(i);
      if (localItem == null) {
        if (j == 0) break;
        localItem1 = new Item();
        localItem1.score = d;
        localItem1.army = j;
        localArrayList.add(localItem1); break;
      }

      if ((localItem.reg.getArmy() != j) && (j != 0)) {
        localItem1 = new Item();
        localItem1.score = d;
        localItem1.army = j;
        localArrayList.add(localItem1);
        d = 0.0D;
      }
      Item localItem1 = new Item();
      localItem1.entry = localItem;
      localItem1.user = findPlayer(i);
      if (localItem1.user != null)
        localItem1.score = localItem1.user.stat().score;
      localItem1.army = localItem.reg.getArmy();
      localArrayList.add(localItem1);
      d += localItem1.score;
      j = localItem1.army;
      i++;
    }
    this.wTable.setSelect(-1, -1);
  }
  private void checkListItems() {
    for (int i = 0; i < this.wTable.items.size(); i++) {
      Item localItem = (Item)this.wTable.items.get(i);
      if ((localItem.entry == null) || 
        (localItem.user == null)) continue;
      if (localItem.user.isDestroyed()) {
        localItem.user = null;
      }
      else if (localItem.user.stat().score != localItem.score) {
        fillListItems();
        return;
      }
    }
  }

  private NetUser findPlayer(int paramInt) {
    NetUser localNetUser = (NetUser)NetEnv.host();
    if (localNetUser.getPlace() == paramInt)
      return localNetUser;
    List localList = NetEnv.hosts();
    for (int i = 0; i < localList.size(); i++) {
      localNetUser = (NetUser)localList.get(i);
      if (localNetUser.getPlace() == paramInt)
        return localNetUser;
    }
    return null;
  }

  public void _enter() {
    fillListItems();
    this.wTable.resized();
    this.client.activateWindow();
  }
  public void _leave() {
    this.wTable.items.clear();
    CmdEnv.top().exec("user STAT EVENTLOG");
    NetUserLeft.all.clear();
    this.client.hideWindow();
  }

  protected void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netcs.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public GUINetCScore(GWindowRoot paramGWindowRoot) {
    super(51);
    init(paramGWindowRoot);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUINetCScore.this.bPrev) {
        if (Main.cur().netServerParams.isMaster()) {
          Mission.cur().destroy();
          Main.stateStack().change(Main.cur().netServerParams.bNGEN ? 69 : 38);
        } else {
          GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
          if (localGUINetClientGuard != null) {
            localGUINetClientGuard.dlgDestroy(new GUINetCScore.1(this));
          }

        }

        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);

      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()))
      {
        draw(x1024(96.0F), y1024(656.0F), x1024(128.0F), y1024(48.0F), 0, GUINetCScore.this.i18n("netcs.New_mission"));
      }
      else draw(x1024(96.0F), y1024(656.0F), x1024(128.0F), y1024(48.0F), 0, GUINetCScore.this.i18n("netcs.Disconnect")); 
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUINetCScore.this.wTable.set1024PosSize(32.0F, 32.0F, 960.0F, 560.0F);
      GUINetCScore.this.bPrev.setPosC(x1024(56.0F), y1024(680.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList items = new ArrayList();
    private GRegion region = new GRegion();

    public int countRows() { if (this.items == null) return 0;
      return this.items.size() + 1; }

    public void preRender() {
      GUINetCScore.this.checkListItems();
      super.preRender();
    }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if ((paramBoolean) && (this.items != null) && (paramInt1 != this.items.size())) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      if (paramBoolean) setCanvasColorWHITE(); else
        setCanvasColorBLACK();
      GUINetCScore.Item localItem = null;
      if (paramInt1 < this.items.size())
        localItem = (GUINetCScore.Item)this.items.get(paramInt1);
      else
        localItem = (GUINetCScore.Item)this.items.get(paramInt1 - 1);
      String str = null;
      int i = 1;
      switch (paramInt2) {
      case 0:
        if (localItem.entry == null) break;
        str = "" + localItem.entry.indexInArmy;
        setCanvasColor(Army.color(localItem.entry.reg.getArmy())); break;
      case 1:
        if (localItem.entry != null) {
          float f = paramFloat2;
          setCanvasColorWHITE();
          draw(0.0F, 0.0F, f, f, localItem.entry.texture);
          if (paramBoolean) setCanvasColorWHITE(); else
            setCanvasColorBLACK();
          draw(1.5F * f, 0.0F, paramFloat1 - 1.5F * f, paramFloat2, 0, localItem.entry.reg.shortInfo()); } else {
          if ((paramInt1 != this.items.size()) || 
            (NetUser.getArmyCoopWinner() <= 0)) break;
          setCanvasColor(Army.color(NetUser.getArmyCoopWinner()));
          setCanvasFont(1);
          if (NetUser.getArmyCoopWinner() == 1)
            str = I18N.hud_log("RedWon");
          else
            str = I18N.hud_log("BlueWon");
          this.region.x = (this.region.y = 0.0F);
          this.region.dx = paramFloat1; this.region.dy = paramFloat2;
          pushClipRegion(this.region, false, 0.0F);
          draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
          popClip();
          return;
        }

      case 2:
        if (localItem.entry == null) break;
        i = 0;
        str = I18N.plane(localItem.entry.keyName); break;
      case 3:
        if (localItem.entry == null) break;
        str = localItem.entry.number; break;
      case 4:
        if (localItem.entry == null)
        {
          break;
        }

        str = localItem.entry.cocName; break;
      case 5:
        i = 0;
        if (localItem.user == null) break;
        str = localItem.user.uniqueName(); break;
      case 6:
        i = 2;
        if (localItem.user == null) break;
        str = "" + localItem.score + "  ";
      }

      if (str != null)
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str); 
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelecting = true;
      this.bSelectRow = true;
      addColumn("N", null);
      addColumn(I18N.gui("netcs.Regiment"), null);
      addColumn(I18N.gui("netcs.Plane"), null);
      addColumn(I18N.gui("netcs.Number"), null);
      addColumn(I18N.gui("netcs.Position"), null);
      addColumn(I18N.gui("netcs.Player"), null);
      addColumn(I18N.gui("netcs.Score"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(1.0F);
      getColumn(1).setRelativeDx(4.0F);
      getColumn(2).setRelativeDx(4.0F);
      getColumn(3).setRelativeDx(4.0F);
      getColumn(4).setRelativeDx(4.0F);
      getColumn(5).setRelativeDx(4.0F);
      getColumn(6).setRelativeDx(2.0F);
      alignColumns();

      this.wClient.bNotify = true;
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

  static class Item
  {
    public GUINetAircraft.Item entry;
    public NetUser user;
    public double score;
    public int army;
  }
}