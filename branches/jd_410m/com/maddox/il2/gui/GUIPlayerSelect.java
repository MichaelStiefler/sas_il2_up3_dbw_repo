package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCellEdit;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class GUIPlayerSelect extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wNew;
  public GUIButton wDel;
  public GUIButton wSelect;
  public Table wTable;
  private TreeMap _sortMap = new TreeMap();

  public void enterPush(GameState paramGameState)
  {
    if (!loadUsersList()) {
      Main.stateStack().pop();
      return;
    }
    _enter();
  }
  public void _enter() {
    this.client.showWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private boolean loadUsersList() {
    this.wTable.users.clear();
    this.wTable.setSelect(-1, -1);
    SectFile localSectFile = new SectFile("users/all.ini", 0);
    int i = localSectFile.sectionIndex("list");
    int j = localSectFile.sectionIndex("current");
    if ((i < 0) || (j < 0))
      return false;
    int k = localSectFile.vars(i);
    if (k == 0)
      return false;
    String str1 = localSectFile.var(j, 0);
    int m = 0;
    try { m = Integer.parseInt(str1); } catch (Exception localException) {
    }
    for (int n = 0; n < k; n++) {
      String str2 = localSectFile.var(i, n);
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.value(i, n));
      UserCfg localUserCfg = new UserCfg(UnicodeTo8bit.load(localNumberTokenizer.next(UserCfg.defName)), UnicodeTo8bit.load(localNumberTokenizer.next(UserCfg.defCallsign)), UnicodeTo8bit.load(localNumberTokenizer.next(UserCfg.defSurname)));

      localUserCfg.sId = str2;
      if ((localUserCfg.existUserDir()) && (localUserCfg.existUserConf()) && (!existUserInList(localUserCfg)))
        this.wTable.users.add(localUserCfg);
      else
        m--;
    }
    if (this.wTable.users.size() == 0)
      return false;
    if (m < 0) m = 0;
    if (m >= this.wTable.users.size())
      m = this.wTable.users.size() - 1;
    this.wTable.setSelect(m, 0);
    return true;
  }

  private boolean existUserInList(UserCfg paramUserCfg) {
    for (int i = 0; i < this.wTable.users.size(); i++) {
      UserCfg localUserCfg = (UserCfg)this.wTable.users.get(i);
      if (paramUserCfg.sId.compareToIgnoreCase(localUserCfg.sId) == 0)
        return true;
    }
    return false;
  }

  private void saveUsersList() {
    SectFile localSectFile = new SectFile("users/all.ini", 1);
    localSectFile.clear();
    int i = localSectFile.sectionAdd("list");
    for (int j = 0; j < this.wTable.users.size(); j++) {
      UserCfg localUserCfg = (UserCfg)this.wTable.users.get(j);
      if (localUserCfg.sId == null) {
        localUserCfg.makeId();
        localUserCfg.createUserDir();
        localUserCfg.createUserConf();
      }
      localSectFile.lineAdd(i, localUserCfg.sId, UnicodeTo8bit.save(localUserCfg.name, true) + " " + UnicodeTo8bit.save(localUserCfg.callsign, true) + " " + UnicodeTo8bit.save(localUserCfg.surname, true));
    }

    j = localSectFile.sectionAdd("current");
    int k = this.wTable.selectRow;
    if (k < 0) k = 0;
    if (k >= this.wTable.users.size())
      k = this.wTable.users.size() - 1;
    localSectFile.lineAdd(j, "" + k);
    localSectFile.saveFile();
  }

  private void sort(int paramInt) {
    UserCfg localUserCfg1 = null;
    int i = this.wTable.selectRow;
    int j = this.wTable.selectCol;
    if (i >= 0)
      localUserCfg1 = (UserCfg)this.wTable.users.get(i);
    this.wTable.setSelect(-1, -1);
    Object localObject1;
    for (int k = 0; k < this.wTable.users.size(); k++) {
      UserCfg localUserCfg2 = (UserCfg)this.wTable.users.get(k);
      localObject1 = localUserCfg2.name;
      switch (paramInt) { case 1:
        localObject1 = localUserCfg2.callsign; break;
      case 2:
        localObject1 = localUserCfg2.surname;
      }
      Object localObject2 = localObject1; int n = 0;
      while (this._sortMap.containsKey(localObject2)) {
        localObject2 = (String)localObject1 + n++;
      }
      this._sortMap.put(localObject2, localUserCfg2);
    }
    this.wTable.users.clear();
    Iterator localIterator = this._sortMap.keySet().iterator();
    int m = 0;
    i = 0;
    while (localIterator.hasNext()) {
      localObject1 = (UserCfg)this._sortMap.get(localIterator.next());
      this.wTable.users.add(localObject1);
      if (localObject1 == localUserCfg1) m = i;
      i++;
    }
    if (localUserCfg1 != null)
      this.wTable.setSelect(m, j);
    this._sortMap.clear();
  }

  public GUIPlayerSelect(GWindowRoot paramGWindowRoot)
  {
    super(1);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("player.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wNew = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wDel = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wSelect = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUIPlayerSelect.this.wNew) {
        GUIPlayerSelect.this.wTable.addUser();
        return true;
      }if (paramGWindow == GUIPlayerSelect.this.wDel) {
        GUIPlayerSelect.this.wTable.removeUser();
        return true;
      }if (paramGWindow == GUIPlayerSelect.this.wSelect) {
        int i = GUIPlayerSelect.this.wTable.selectRow;
        if (i >= 0) {
          int j = (GUIPlayerSelect.this.wTable.selectCol + 1) % 3;
          GUIPlayerSelect.this.wTable.setSelect(i, j);
          UserCfg localUserCfg = (UserCfg)GUIPlayerSelect.this.wTable.users.get(i);
          World.cur().userCfg = localUserCfg;
          World.cur().setUserCovers();
        }
        GUIPlayerSelect.this.saveUsersList();
        World.cur().userCfg.loadConf();
        GUIMainMenu localGUIMainMenu = (GUIMainMenu)GameState.get(2);
        localGUIMainMenu.pPilotName.cap = new GCaption(World.cur().userCfg.name + " '" + World.cur().userCfg.callsign + "' " + World.cur().userCfg.surname);

        Main.stateStack().pop();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(608.0F), x1024(464.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(32.0F), y1024(32.0F), x1024(464.0F), y1024(32.0F), 1, GUIPlayerSelect.this.i18n("player.ListPlayers"));
      draw(x1024(96.0F), y1024(480.0F), x1024(400.0F), y1024(48.0F), 0, GUIPlayerSelect.this.i18n("player.NewPlayer"));
      draw(x1024(96.0F), y1024(544.0F), x1024(400.0F), y1024(48.0F), 0, GUIPlayerSelect.this.i18n("player.DeletePlayer"));
      draw(x1024(96.0F), y1024(624.0F), x1024(400.0F), y1024(48.0F), 0, GUIPlayerSelect.this.i18n("player.Select"));
    }

    public void setPosSize() {
      set1024PosSize(254.0F, 48.0F, 528.0F, 704.0F);
      GUIPlayerSelect.this.wNew.setPosC(x1024(56.0F), y1024(506.0F));
      GUIPlayerSelect.this.wDel.setPosC(x1024(56.0F), y1024(568.0F));
      GUIPlayerSelect.this.wSelect.setPosC(x1024(56.0F), y1024(648.0F));
      GUIPlayerSelect.this.wTable.setPosSize(x1024(32.0F), y1024(80.0F), x1024(464.0F), y1024(368.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList users = new ArrayList();

    public void addUser() {
      int i = this.selectRow + 1;
      this.users.add(i, new UserCfg(UserCfg.defName, UserCfg.defCallsign, UserCfg.defSurname));
      setSelect(i, 0);
      resized();
    }
    public void removeUser() {
      if (this.users.size() <= 1) return;
      int i = this.selectRow;
      if (i < 0) return;
      UserCfg localUserCfg = (UserCfg)this.users.get(i);
      if (localUserCfg.sId != null)
        localUserCfg.removeUserDir();
      this.users.remove(i);
      if (this.editor != null) {
        ((GWindow)this.editor).hideWindow();
        this.editor = null;
      }
      i--; if (i < 0) i = 0;
      setSelect(i, 0);
      resized();
    }
    public int countRows() {
      return this.users != null ? this.users.size() : 0;
    }
    public void columnClicked(int paramInt) { GUIPlayerSelect.this.sort(paramInt); } 
    public boolean isCellEditable(int paramInt1, int paramInt2) {
      return true;
    }
    public GWindowCellEdit getCellEdit(int paramInt1, int paramInt2) { GWindowCellEdit localGWindowCellEdit = super.getCellEdit(paramInt1, paramInt2);
      if ((localGWindowCellEdit != null) && ((localGWindowCellEdit instanceof GWindowEditControl))) {
        GWindowEditControl localGWindowEditControl = (GWindowEditControl)localGWindowCellEdit;
        localGWindowEditControl.maxLength = 32;
      }
      return localGWindowCellEdit; }

    public Object getValueAt(int paramInt1, int paramInt2) {
      UserCfg localUserCfg = (UserCfg)this.users.get(paramInt1);
      switch (paramInt2) { case 0:
        return localUserCfg.name;
      case 1:
        return localUserCfg.callsign;
      case 2:
        return localUserCfg.surname;
      }
      return null;
    }
    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
      if ((paramInt1 < 0) || (paramInt2 < 0)) return;
      if (paramInt1 >= this.users.size()) return;
      UserCfg localUserCfg = (UserCfg)this.users.get(paramInt1);
      String str = (String)paramObject;
      if ((str == null) || (str.length() == 0))
        str = " ";
      switch (paramInt2) { case 0:
        localUserCfg.name = str; break;
      case 1:
        localUserCfg.callsign = str; break;
      case 2:
        localUserCfg.surname = str; }
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("player.Name"), null);
      addColumn(I18N.gui("player.Callsign"), null);
      addColumn(I18N.gui("player.Surname"), null);
      this.vSB.scroll = rowHeight(0);
      resized();
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super(2.0F, 4.0F, 20.0F, 16.0F);
    }
  }
}