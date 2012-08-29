package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCellEdit;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Client;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.util.ArrayList;

public class GUINetServerNGenProp extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GWindowEditControl wNote;
  public Menu wMenu;
  public GUIButton bList;
  public GUIButton bDiff;
  public GUIButton bLast;
  public GUIButton bNew;
  public GWindowComboControl wTimeHour;
  public GWindowComboControl wTimeMins;
  public GUISwitchBox3 sLand;
  private GWindowMessageBox loadMessageBox;

  private void doLoadMission()
  {
    int i = this.wTimeHour.getSelected();
    int j = this.wTimeMins.getSelected() * 15;
    long l = i * 60L * 60L * 1000L + j * 60L * 1000L;
    Main.cur().netServerParams.timeoutNGEN = l;
    Main.cur().netServerParams.bLandedNGEN = this.sLand.isChecked();
    this.loadMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("netsms.StandBy"), i18n("netsms.Loading_simulation"), 5, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 1)
          BackgroundTask.cancel(I18N.gui("miss.UserCancel"));
      }
    };
    new MsgAction(72, 0.0D) {
      public void doAction() { if (Mission.cur() != null) Mission.cur().destroy(); try
        {
          new GUINetServerNGenProp.MissionListener(GUINetServerNGenProp.this);
          Mission.loadFromSect(Main.cur().currentMissionFile, true);
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
          GUINetServerNGenProp.this.missionBad(I18N.gui("miss.LoadBad"));
        }
      }
    };
  }

  public void missionLoaded()
  {
    new MsgAction(72, 0.0D) {
      public void doAction() { GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        if (GUINetServerNGenProp.this.loadMessageBox != null) {
          GUINetServerNGenProp.this.loadMessageBox.close(false);
          GUINetServerNGenProp.access$102(GUINetServerNGenProp.this, null);
        }
        ((NetUser)NetEnv.host()).resetAllPlaces();
        CmdEnv.top().exec("mission BEGIN");
        int i = GUINetAircraft.serverPlace();
        if (i != -1)
          ((NetUser)NetEnv.host()).requestPlace(i);
        Main.stateStack().change(45); } } ;
  }

  private void missionBad(String paramString) {
    this.loadMessageBox.close(false);
    this.loadMessageBox = null;
    new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("netsms.Error"), paramString, 3, 0.0F) {
      public void result(int paramInt) {
      }
    };
  }

  public void enter(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 51)) {
      localObject = "NGen.exe";
      try {
        String str = GUINetServerNGenSelect.cur.fileName;
        Runtime localRuntime = Runtime.getRuntime();

        Process localProcess = localRuntime.exec((String)localObject + " -ended " + str);

        localProcess.waitFor();
      }
      catch (Throwable localThrowable) {
        System.out.println(localThrowable.getMessage());
        localThrowable.printStackTrace();
      }
    }
    Object localObject = new SectFile(GUINetServerNGenSelect.cur.fileName, 4, true, null, RTSConf.charEncoding, true);
    int i = ((SectFile)localObject).get("$select", "difficulty", -1);
    if (i == -1)
      i = World.cur().userCfg.netDifficulty;
    else
      World.cur().userCfg.netDifficulty = i;
    World.cur().diffCur.set(i);
    Main.cur().netServerParams.setDifficulty(World.cur().diffCur.get());

    NetEnv.cur().connect.bindEnable(true);

    Main.cur().netServerParams.USGSupdate();
    _enter();
  }

  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 41) {
      World.cur().userCfg.netDifficulty = World.cur().diffCur.get();
      World.cur().userCfg.saveConf();
      Main.cur().netServerParams.setDifficulty(World.cur().diffCur.get());
      SectFile localSectFile = new SectFile(GUINetServerNGenSelect.cur.fileName, 5, true, null, RTSConf.charEncoding, true);
      localSectFile.set("$select", "difficulty", World.cur().userCfg.netDifficulty);
      localSectFile.saveFile();
    }
    this.client.activateWindow();
  }

  public void _enter() {
    fillNote();
    fillMenu();
    this.wMenu.resized();
    this.client.activateWindow();
    if (GUINetServerNGenSelect.cur.missions > 0)
      this.bLast.showWindow();
    else
      this.bLast.hideWindow(); 
  }

  public void _leave() {
    this.client.hideWindow();
  }

  private void fillNote()
  {
    if (GUINetServerNGenSelect.cur != null)
      this.wNote.setValue(GUINetServerNGenSelect.cur.note, false);
    else
      this.wNote.setValue("", false);
  }

  private void fillMenu() {
    this.wMenu.lst.clear();
    if (GUINetServerNGenSelect.cur == null)
      return;
    SectFile localSectFile = new SectFile(GUINetServerNGenSelect.cur.fileName, 4, true, null, RTSConf.charEncoding, true);
    int i = localSectFile.sections();
    for (int j = 0; j < i; j++) {
      String str1 = localSectFile.sectionName(j);
      if (str1.startsWith("$"))
        continue;
      int k = localSectFile.vars(j);
      if (k == 0)
        continue;
      MenuItem localMenuItem = new MenuItem();
      localMenuItem.key = str1;
      localMenuItem.name = localSectFile.get("$locale", str1, str1);
      for (int m = 0; m < k; m++) {
        str2 = localSectFile.var(j, m);
        String str3 = localSectFile.value(j, m);
        localMenuItem.keys.add(str2);
        localMenuItem.names.add(str3);
      }
      String str2 = localSectFile.get("$select", str1, (String)null);
      if (str2 != null)
        localMenuItem.select(str2);
      this.wMenu.lst.add(localMenuItem);
    }
    if (this.wMenu.lst.size() > 0)
      this.wMenu.setSelect(0, 0);
  }

  private void getChanges() {
    if (GUINetServerNGenSelect.cur == null)
      return;
    SectFile localSectFile = new SectFile(GUINetServerNGenSelect.cur.fileName, 5, true, null, RTSConf.charEncoding, true);
    int i = 0;
    String str1 = this.wNote.getValue();
    if (!str1.equals(GUINetServerNGenSelect.cur.note)) {
      GUINetServerNGenSelect.cur.note = str1;
      localSectFile.set("$locale", "note", str1);
      i = 1;
    }
    for (int j = 0; j < this.wMenu.lst.size(); j++) {
      MenuItem localMenuItem = (MenuItem)this.wMenu.lst.get(j);
      String str2 = (String)localMenuItem.keys.get(localMenuItem.select);
      if (!str2.equals(localSectFile.get("$select", localMenuItem.key, (String)null))) {
        localSectFile.set("$select", localMenuItem.key, str2);
        i = 1;
      }
    }
    if (i != 0)
      localSectFile.saveFile();
  }

  public GUINetServerNGenProp(GWindowRoot paramGWindowRoot)
  {
    super(69);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("ngenp.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wNote = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wMenu = new Menu(this.dialogClient);

    this.wTimeHour = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wTimeMins = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wTimeHour.add("00");
    this.wTimeHour.add("01");
    this.wTimeHour.add("02");
    this.wTimeHour.add("03");
    this.wTimeHour.add("04");
    this.wTimeHour.add("05");
    this.wTimeHour.add("06");
    this.wTimeHour.add("07");
    this.wTimeHour.add("08");
    this.wTimeHour.add("09");
    this.wTimeHour.add("10");
    this.wTimeHour.add("11");
    this.wTimeHour.add("12");
    this.wTimeHour.add("13");
    this.wTimeHour.add("14");
    this.wTimeHour.add("15");
    this.wTimeHour.add("16");
    this.wTimeHour.add("17");
    this.wTimeHour.add("18");
    this.wTimeHour.add("19");
    this.wTimeHour.add("20");
    this.wTimeHour.add("21");
    this.wTimeHour.add("22");
    this.wTimeHour.add("23");
    this.wTimeHour.setEditable(false);
    this.wTimeHour.setSelected(0, true, false);

    this.wTimeMins.add("00");
    this.wTimeMins.add("15");
    this.wTimeMins.add("30");
    this.wTimeMins.add("45");
    this.wTimeMins.setEditable(false);
    this.wTimeMins.setSelected(0, true, false);

    this.sLand = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bList = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bDiff = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bLast = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNew = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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
      if (paramInt1 != 2) {
        return super.notify(paramGWindow, paramInt1, paramInt2);
      }
      if (paramGWindow == GUINetServerNGenProp.this.bList) {
        GUINetServerNGenProp.this.getChanges();
        Main.stateStack().change(68);
        return true;
      }if (paramGWindow == GUINetServerNGenProp.this.bDiff) {
        Main.stateStack().push(41);
        return true;
      }
      Object localObject1;
      String str1;
      Object localObject2;
      if (paramGWindow == GUINetServerNGenProp.this.bLast) {
        localObject1 = new SectFile(GUINetServerNGenSelect.cur.fileName, 4, true, null, RTSConf.charEncoding, true);
        str1 = ((SectFile)localObject1).line(((SectFile)localObject1).sectionIndex("$missions"), GUINetServerNGenSelect.cur.missions - 1);
        localObject2 = GUINetServerNGenSelect.cur.fileName;
        int j = ((String)localObject2).lastIndexOf("conf.dat");
        if (j >= 0)
          localObject2 = ((String)localObject2).substring(0, j);
        Main.cur().currentMissionFile = new SectFile((String)localObject2 + str1, 0);
        GUINetServerNGenProp.this.doLoadMission();
        return true;
      }if (paramGWindow == GUINetServerNGenProp.this.bNew) {
        GUINetServerNGenProp.this.getChanges();
        localObject1 = "NGen.exe";
        try {
          str1 = GUINetServerNGenSelect.cur.fileName;
          localObject2 = Runtime.getRuntime();

          localObject3 = ((Runtime)localObject2).exec((String)localObject1 + " " + str1);

          ((Process)localObject3).waitFor();
        }
        catch (Throwable localThrowable) {
          System.out.println(localThrowable.getMessage());
          localThrowable.printStackTrace();
          return true;
        }
        SectFile localSectFile = new SectFile(GUINetServerNGenSelect.cur.fileName, 4, true, null, RTSConf.charEncoding, true);
        int i = localSectFile.sectionIndex("$missions");
        if (i < 0)
          return true;
        GUINetServerNGenSelect.cur.missions = localSectFile.vars(i);
        Object localObject3 = localSectFile.line(i, GUINetServerNGenSelect.cur.missions - 1);
        String str2 = GUINetServerNGenSelect.cur.fileName;
        int k = str2.lastIndexOf("conf.dat");
        if (k >= 0)
          str2 = str2.substring(0, k);
        Main.cur().currentMissionFile = new SectFile(str2 + (String)localObject3, 0);
        GUINetServerNGenProp.this.doLoadMission();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(432.0F), x1024(672.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(672.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(32.0F), y1024(32.0F), x1024(672.0F), y1024(32.0F), 1, GUINetServerNGenSelect.cur.name);
      draw(x1024(32.0F), y1024(80.0F), x1024(320.0F), y1024(32.0F), 0, GUINetServerNGenProp.this.i18n("ngenp.note"));
      draw(x1024(32.0F), y1024(160.0F), x1024(320.0F), y1024(32.0F), 0, GUINetServerNGenProp.this.i18n("ngenp.properties"));
      draw(x1024(96.0F), y1024(656.0F), x1024(96.0F), y1024(48.0F), 0, GUINetServerNGenProp.this.i18n("ngenp.list"));
      draw(x1024(176.0F), y1024(656.0F), x1024(112.0F), y1024(48.0F), 2, GUINetServerNGenProp.this.i18n("ngenp.difficulty"));
      if ((GUINetServerNGenSelect.cur.missions > 0) && (
        (GUINetServerNGenProp.this.loadMessageBox == null) || (GUINetServerNGenSelect.cur.missions > 1))) {
        draw(x1024(352.0F), y1024(656.0F), x1024(112.0F), y1024(48.0F), 2, GUINetServerNGenProp.this.i18n("ngenp.last"));
      }
      draw(x1024(528.0F), y1024(656.0F), x1024(112.0F), y1024(48.0F), 2, GUINetServerNGenProp.this.i18n("ngenp.new"));

      draw(x1024(32.0F), y1024(448.0F), x1024(672.0F), y1024(32.0F), 0, GUINetServerNGenProp.this.i18n("ngenp.end_mission"));
      draw(x1024(384.0F), y1024(496.0F), x1024(226.0F), y1024(32.0F), 0, GUINetServerNGenProp.this.i18n("ngenp.timeout"));
      draw(x1024(264.0F), y1024(544.0F), x1024(346.0F), y1024(48.0F), 0, GUINetServerNGenProp.this.i18n("ngenp.landed"));
    }

    public void setPosSize() {
      set1024PosSize(144.0F, 32.0F, 736.0F, 736.0F);
      GUINetServerNGenProp.this.wNote.set1024PosSize(48.0F, 112.0F, 640.0F, 32.0F);
      GUINetServerNGenProp.this.wMenu.set1024PosSize(48.0F, 192.0F, 640.0F, 224.0F);
      GUINetServerNGenProp.this.wTimeHour.set1024PosSize(176.0F, 496.0F, 80.0F, 32.0F);
      GUINetServerNGenProp.this.wTimeMins.set1024PosSize(272.0F, 496.0F, 80.0F, 32.0F);
      GUINetServerNGenProp.this.sLand.setPosC(x1024(216.0F), y1024(568.0F));
      GUINetServerNGenProp.this.bList.setPosC(x1024(56.0F), y1024(680.0F));
      GUINetServerNGenProp.this.bDiff.setPosC(x1024(328.0F), y1024(680.0F));
      GUINetServerNGenProp.this.bLast.setPosC(x1024(504.0F), y1024(680.0F));
      GUINetServerNGenProp.this.bNew.setPosC(x1024(680.0F), y1024(680.0F));
    }
  }

  public class Menu extends GWindowTable
  {
    public ArrayList lst = new ArrayList();
    int indxMenu;

    public int countRows()
    {
      return this.lst != null ? this.lst.size() : 0;
    }
    public boolean isCellEditable(int paramInt1, int paramInt2) {
      return paramInt2 == 1;
    }
    public float rowHeight(int paramInt) { return (int)(this.root.textFonts[0].height * 1.6F); }

    public GWindowCellEdit getCellEdit(int paramInt1, int paramInt2) {
      if (!isCellEditable(paramInt1, paramInt2)) return null;

      this.indxMenu = paramInt1;
      GUINetServerNGenProp.5 local5;
      GWindowCellEdit localGWindowCellEdit = (GWindowCellEdit)this.jdField_wClient_of_type_ComMaddoxGwindowGWindowTable$Client.create(local5 = new GUINetServerNGenProp.5(this));

      local5.setEditable(false);
      GUINetServerNGenProp.MenuItem localMenuItem = (GUINetServerNGenProp.MenuItem)this.lst.get(this.indxMenu);
      for (int i = 0; i < localMenuItem.keys.size(); i++)
        local5.add((String)localMenuItem.names.get(i));
      local5.setSelected(localMenuItem.select, true, false);
      return localGWindowCellEdit;
    }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      GUINetServerNGenProp.MenuItem localMenuItem = (GUINetServerNGenProp.MenuItem)this.lst.get(paramInt1);
      String str = null;
      int i = 0;
      switch (paramInt2) { case 0:
        str = localMenuItem.name;
        i = 0;
        break;
      case 1:
        str = (String)localMenuItem.names.get(localMenuItem.select);
        i = 0;
      }

      if (paramBoolean) {
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      } else {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str);
      }
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelectRow = true;
      addColumn(I18N.gui("ngenp.name"), null);
      addColumn(I18N.gui("ngenp.state"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(10.0F);
      getColumn(1).setRelativeDx(12.0F);
      alignColumns();
      this.bNotify = true;
      this.jdField_wClient_of_type_ComMaddoxGwindowGWindowTable$Client.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Menu(GWindow arg2) {
      super();
    }
  }

  static class MenuItem
  {
    String key;
    String name;
    ArrayList keys = new ArrayList();
    ArrayList names = new ArrayList();
    int select = 0;

    public void select(String paramString) {
      this.select = 0;
      for (; this.select < this.keys.size(); this.select += 1)
        if (paramString.equals(this.keys.get(this.select)))
          return;
      this.select = 0;
    }
  }

  class MissionListener
    implements MsgBackgroundTaskListener
  {
    public void msgBackgroundTaskStarted(BackgroundTask paramBackgroundTask)
    {
    }

    public void msgBackgroundTaskStep(BackgroundTask paramBackgroundTask)
    {
      GUINetServerNGenProp.this.loadMessageBox.message = ((int)paramBackgroundTask.percentComplete() + "% " + I18N.gui(paramBackgroundTask.messageComplete()));
    }

    public void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask)
    {
      BackgroundTask.removeListener(this);
      if (paramBackgroundTask.isComplete())
        GUINetServerNGenProp.this.missionLoaded();
      else
        GUINetServerNGenProp.this.missionBad(I18N.gui("miss.LoadBad") + " " + paramBackgroundTask.messageCancel());
    }

    public MissionListener() {
      BackgroundTask.addListener(this);
    }
  }
}