package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
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
import com.maddox.il2.net.USGS;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class GUINetServerMisSelect extends GameState
{
  public static final String HOME_DIR = "missions/net";
  public static final String HOME_DIR_DOGFIGHT = "missions/net/dogfight";
  public static final String HOME_DIR_COOP = "missions/net/coop";
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wDifficulty;
  public GUIButton wNext;
  public GWindowComboControl wDirs;
  public Table wTable;
  public WDescript wDescript;
  private GWindowMessageBox loadMessageBox;
  public TreeMap _scanMap = new TreeMap();

  private void doLoadMission()
  {
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
          new GUINetServerMisSelect.MissionListener(GUINetServerMisSelect.this);
          Mission.loadFromSect(Main.cur().currentMissionFile, true);
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
          GUINetServerMisSelect.this.missionBad(I18N.gui("miss.LoadBad"));
        }
      }
    };
  }

  public void missionLoaded()
  {
    new MsgAction(72, 0.0D) {
      public void doAction() { GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        if (GUINetServerMisSelect.this.loadMessageBox != null) {
          GUINetServerMisSelect.this.loadMessageBox.close(false);
          GUINetServerMisSelect.access$102(GUINetServerMisSelect.this, null);
        }
        if (Main.cur().netServerParams.isDogfight()) {
          ((NetUser)NetEnv.host()).setBornPlace(-1);
          CmdEnv.top().exec("mission BEGIN");
          Main.stateStack().change(39);
        } else if (Main.cur().netServerParams.isCoop()) {
          ((NetUser)NetEnv.host()).resetAllPlaces();
          CmdEnv.top().exec("mission BEGIN");
          int i = GUINetAircraft.serverPlace();
          if (i != -1)
            ((NetUser)NetEnv.host()).requestPlace(i);
          Main.stateStack().change(45);
        } } } ;
  }

  private void missionBad(String paramString) {
    this.loadMessageBox.close(false);
    this.loadMessageBox = null;
    new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("netsms.Error"), paramString, 3, 0.0F) {
      public void result(int paramInt) {
      }
    };
  }

  public String HOME_DIR() {
    if (Main.cur().netServerParams.isCoop())
      return "missions/net/coop";
    if (Main.cur().netServerParams.isDogfight()) {
      return "missions/net/dogfight";
    }
    return "missions/net";
  }

  public void enter(GameState paramGameState) {
    World.cur().diffCur.set(World.cur().userCfg.netDifficulty);
    Main.cur().netServerParams.setDifficulty(World.cur().diffCur.get());
    if (paramGameState.id() == 35) {
      _enter();
    } else {
      if (Main.cur().netServerParams.isCoop()) {
        NetEnv.cur().connect.bindEnable(true);

        Main.cur().netServerParams.USGSupdate();
      }
      this.client.activateWindow();
    }
  }

  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 41) {
      World.cur().userCfg.netDifficulty = World.cur().diffCur.get();
      World.cur().userCfg.saveConf();
      Main.cur().netServerParams.setDifficulty(World.cur().diffCur.get());
    }
    this.client.activateWindow();
  }

  public void _enter() {
    fillDirs();
    if (Main.cur().netServerParams.isCoop()) {
      this.infoMenu.info = i18n("netsms.infoC");
      NetEnv.cur().connect.bindEnable(true);

      Main.cur().netServerParams.USGSupdate();
    } else if (Main.cur().netServerParams.isDogfight()) {
      this.infoMenu.info = i18n("netsms.infoD");
    } else {
      this.infoMenu.info = i18n("netsms.infoM");
    }
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  public void fillDirs() {
    File localFile = new File(HomePath.get(0), HOME_DIR());
    File[] arrayOfFile = localFile.listFiles();
    this.wDirs.clear(false);
    if ((arrayOfFile == null) || (arrayOfFile.length == 0)) {
      this.wTable.files.clear();
      this.wTable.setSelect(-1, 0);
      return;
    }
    for (int i = 0; i < arrayOfFile.length; i++) {
      if ((!arrayOfFile[i].isDirectory()) || (arrayOfFile[i].isHidden()) || (".".equals(arrayOfFile[i].getName())) || ("..".equals(arrayOfFile[i].getName()))) {
        continue;
      }
      this._scanMap.put(arrayOfFile[i].getName(), null);
    }
    Iterator localIterator = this._scanMap.keySet().iterator();
    while (localIterator.hasNext())
      this.wDirs.add((String)localIterator.next());
    if (this._scanMap.size() > 0)
      this.wDirs.setSelected(0, true, false);
    this._scanMap.clear();
    fillFiles();
  }

  public void fillFiles()
  {
    this.wTable.files.clear();
    String str1 = this.wDirs.getValue();
    if (str1 != null) {
      String str2 = HOME_DIR() + "/" + str1;
      File localFile = new File(HomePath.get(0), str2);
      File[] arrayOfFile = localFile.listFiles();
      if ((arrayOfFile != null) && (arrayOfFile.length > 0)) {
        for (int i = 0; i < arrayOfFile.length; i++) {
          if ((arrayOfFile[i].isDirectory()) || (arrayOfFile[i].isHidden()) || (arrayOfFile[i].getName().toLowerCase().lastIndexOf(".properties") >= 0)) {
            continue;
          }
          localObject = new FileMission(str2, arrayOfFile[i].getName());
          this._scanMap.put(((FileMission)localObject).fileName, localObject);
        }

        Object localObject = this._scanMap.keySet().iterator();
        while (((Iterator)localObject).hasNext())
          this.wTable.files.add(this._scanMap.get(((Iterator)localObject).next()));
        if (this._scanMap.size() > 0)
          this.wTable.setSelect(0, 0);
        else
          this.wTable.setSelect(-1, 0);
        this._scanMap.clear();
      } else {
        this.wTable.setSelect(-1, 0);
      }
    } else {
      this.wTable.setSelect(-1, 0);
    }
    this.wTable.resized();
  }

  public GUINetServerMisSelect(GWindowRoot paramGWindowRoot)
  {
    super(38);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netsms.infoM");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wDirs = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wDirs.setEditable(false);
    this.wTable = new Table(this.dialogClient);

    this.dialogClient.create(this.wDescript = new WDescript());
    this.wDescript.bNotify = true;

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.wDifficulty = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wNext = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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
      if ((paramInt1 != 2) || (GUINetServerMisSelect.this.loadMessageBox != null)) {
        return super.notify(paramGWindow, paramInt1, paramInt2);
      }
      if (paramGWindow == GUINetServerMisSelect.this.wPrev) {
        GUINetServer.exitServer(true);
        return true;
      }if (paramGWindow == GUINetServerMisSelect.this.wDirs) {
        GUINetServerMisSelect.this.fillFiles();
        return true;
      }if (paramGWindow == GUINetServerMisSelect.this.wDifficulty) {
        Main.stateStack().push(41);
        return true;
      }if (paramGWindow == GUINetServerMisSelect.this.wNext) {
        if (GUINetServerMisSelect.this.wDirs.getValue() == null) return true;
        int i = GUINetServerMisSelect.this.wTable.selectRow;
        if ((i < 0) || (i >= GUINetServerMisSelect.this.wTable.files.size())) return true;
        GUINetServerMisSelect.FileMission localFileMission = (GUINetServerMisSelect.FileMission)GUINetServerMisSelect.this.wTable.files.get(i);

        Main.cur().currentMissionFile = new SectFile(GUINetServerMisSelect.this.HOME_DIR() + "/" + GUINetServerMisSelect.this.wDirs.getValue() + "/" + localFileMission.fileName, 0);

        GUINetServerMisSelect.this.doLoadMission();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(432.0F), y1024(546.0F), x1024(384.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(416.0F), y1024(32.0F), 2.0F, y1024(608.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(64.0F), y1024(156.0F), x1024(240.0F), y1024(32.0F), 0, GUINetServerMisSelect.this.i18n("netsms.MissionType"));
      draw(x1024(64.0F), y1024(264.0F), x1024(240.0F), y1024(32.0F), 0, GUINetServerMisSelect.this.i18n("netsms.Missions"));
      draw(x1024(464.0F), y1024(264.0F), x1024(248.0F), y1024(32.0F), 0, GUINetServerMisSelect.this.i18n("netsms.Description"));

      draw(x1024(104.0F), y1024(592.0F), x1024(192.0F), y1024(48.0F), 0, (USGS.isUsed()) || (Main.cur().netGameSpy != null) ? GUINetServerMisSelect.this.i18n("main.Quit") : GUINetServerMisSelect.this.i18n("netsms.MainMenu"));

      draw(x1024(496.0F), y1024(592.0F), x1024(128.0F), y1024(48.0F), 0, GUINetServerMisSelect.this.i18n("brief.Difficulty"));
      draw(x1024(528.0F), y1024(592.0F), x1024(216.0F), y1024(48.0F), 2, GUINetServerMisSelect.this.i18n("netsms.Load"));
    }

    public void setPosSize() {
      set1024PosSize(80.0F, 64.0F, 848.0F, 672.0F);
      GUINetServerMisSelect.this.wPrev.setPosC(x1024(56.0F), y1024(616.0F));
      GUINetServerMisSelect.this.wDifficulty.setPosC(x1024(456.0F), y1024(616.0F));
      GUINetServerMisSelect.this.wNext.setPosC(x1024(792.0F), y1024(616.0F));
      GUINetServerMisSelect.this.wDirs.setPosSize(x1024(48.0F), y1024(192.0F), x1024(336.0F), M(2.0F));
      GUINetServerMisSelect.this.wTable.setPosSize(x1024(48.0F), y1024(304.0F), x1024(336.0F), y1024(256.0F));
      GUINetServerMisSelect.this.wDescript.setPosSize(x1024(448.0F), y1024(312.0F), x1024(354.0F), y1024(212.0F));
    }
  }

  public class WDescript extends GWindow
  {
    public WDescript()
    {
    }

    public void render()
    {
      String str = null;
      if (GUINetServerMisSelect.this.wTable.jdField_selectRow_of_type_Int >= 0) {
        str = ((GUINetServerMisSelect.FileMission)GUINetServerMisSelect.this.wTable.files.get(GUINetServerMisSelect.this.wTable.jdField_selectRow_of_type_Int)).description;
        if ((str != null) && (str.length() == 0)) str = null;
      }
      if (str != null) {
        setCanvasFont(0);
        setCanvasColorBLACK();
        drawLines(0.0F, -this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.descender, str, 0, str.length(), this.win.dx, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height);
      }
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList files = new ArrayList();

    public int countRows() { return this.files != null ? this.files.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      String str = ((GUINetServerMisSelect.FileMission)this.files.get(paramInt1)).name;
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
      }
      else
      {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
      }
    }

    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("netsms.Mission_files"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      resized();
    }

    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public Table(GWindow arg2) {
      super(2.0F, 4.0F, 20.0F, 16.0F);
      this.bNotify = true;
      this.wClient.bNotify = true;
    }
  }

  static class FileMission
  {
    public String fileName;
    public String name;
    public String description;

    public FileMission(String paramString1, String paramString2)
    {
      this.fileName = paramString2;
      try {
        String str = paramString2;
        int i = str.lastIndexOf(".");
        if (i >= 0)
          str = str.substring(0, i);
        ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString1 + "/" + str, RTSConf.cur.locale);
        this.name = localResourceBundle.getString("Name");
        this.description = localResourceBundle.getString("Short");
      } catch (Exception localException) {
        this.name = paramString2;
        this.description = null;
      }
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
      GUINetServerMisSelect.this.loadMessageBox.message = ((int)paramBackgroundTask.percentComplete() + "% " + I18N.gui(paramBackgroundTask.messageComplete()));
    }

    public void msgBackgroundTaskStoped(BackgroundTask paramBackgroundTask)
    {
      BackgroundTask.removeListener(this);
      if (paramBackgroundTask.isComplete())
        GUINetServerMisSelect.this.missionLoaded();
      else
        GUINetServerMisSelect.this.missionBad(I18N.gui("miss.LoadBad") + " " + paramBackgroundTask.messageCancel());
    }

    public MissionListener() {
      BackgroundTask.addListener(this);
    }
  }
}