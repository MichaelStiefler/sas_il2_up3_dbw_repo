package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
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
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.rts.HomePath;
import com.maddox.rts.ObjIO;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUICampaigns extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public Table wTable;
  public GUIButton bDiff;
  public GUIButton bStat;
  public GUIButton bDel;
  public GUIButton bNew;
  public GUIButton bExit;
  public GUIButton bStart;

  public void enterPush(GameState paramGameState)
  {
    enter(paramGameState); } 
  public void enterPop(GameState paramGameState) { enter(paramGameState); } 
  public void enter(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 58)) {
      Main.cur().currentMissionFile = Main.cur().campaign.nextMission();
      if (Main.cur().currentMissionFile == null) {
        new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
        {
          public void result(int paramInt)
          {
          }
        };
        return;
      }
      if (Main.cur().campaign.isDGen())
        Main.stateStack().change(62);
      else
        Main.stateStack().change(28);
      return;
    }
    _enter();
  }

  public void _enter() {
    fillCampList();
    if (this.wTable.campList.size() == 0)
    {
      Main.stateStack().change(26);

      return;
    }
    this.wTable.resized();
    this.client.activateWindow();
  }
  public void _leave() {
    this.wTable.campList.clear();
    this.client.hideWindow();
  }

  private void fillCampList() {
    this.wTable.campList.clear();
    DifficultySettings localDifficultySettings = new DifficultySettings();
    SectFile localSectFile = null;
    int i = 0;
    try {
      String str = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
      localSectFile = new SectFile(str, 0, false, World.cur().userCfg.krypto());
      i = localSectFile.sectionIndex("list");
      if (i < 0)
        return;
      int j = localSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        Item localItem = new Item();
        localItem.key = localSectFile.var(i, k);
        localItem.camp = ((Campaign)ObjIO.fromString(localSectFile.value(i, k)));
        localItem.icon = GTexture.New("missions/campaign/" + localItem.camp.branch() + "/icon.mat");
        ResourceBundle localResourceBundle1 = ResourceBundle.getBundle("missions/campaign/" + localItem.camp.branch() + "/" + localItem.camp.missionsDir() + "/info", RTSConf.cur.locale);
        localItem.name = localResourceBundle1.getString("Name");
        ResourceBundle localResourceBundle2 = ResourceBundle.getBundle("missions/campaign/" + localItem.camp.branch() + "/" + "rank", RTSConf.cur.locale);
        localItem.rank = localResourceBundle2.getString("" + localItem.camp.rank());
        localDifficultySettings.set(localItem.camp.difficulty());
        if (localDifficultySettings.isRealistic()) localItem.diff = i18n("camps.realistic");
        else if (localDifficultySettings.isNormal()) localItem.diff = i18n("camps.normal");
        else if (localDifficultySettings.isEasy()) localItem.diff = i18n("camps.easy"); else
          localItem.diff = i18n("camps.custom");
        this.wTable.campList.add(localItem);
      }
      if (this.wTable.campList.size() > 0)
        this.wTable.setSelect(0, 0);
    }
    catch (Exception localException1) {
      this.wTable.campList.clear();
      System.out.println(localException1.getMessage());
      localException1.printStackTrace();
      try {
        localSectFile.sectionClear(i);
        localSectFile.saveFile(localSectFile.fileName()); } catch (Exception localException2) {
      }
    }
  }

  private void removeItem() {
    int i = this.wTable.selectRow;
    if (i < 0) return;
    String str1 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
    SectFile localSectFile = new SectFile(str1, 1, true, World.cur().userCfg.krypto());
    int j = localSectFile.sectionIndex("list");
    int k = localSectFile.varIndex(j, ((Item)this.wTable.campList.get(i)).key);
    try {
      Campaign localCampaign = (Campaign)ObjIO.fromString(localSectFile.value(j, k));
      if (localCampaign.isDGen()) {
        String str2 = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir();
        File localFile1 = new File(HomePath.toFileSystemName(str2, 0));
        File[] arrayOfFile = localFile1.listFiles();
        if (arrayOfFile != null) {
          for (int n = 0; n < arrayOfFile.length; n++) {
            File localFile2 = arrayOfFile[n];
            String str3 = localFile2.getName();
            if ((".".equals(str3)) || ("..".equals(str3)))
              continue;
            localFile2.delete();
          }
        }
        localFile1.delete();
      }
      localCampaign.clearSavedStatics(localSectFile); } catch (Exception localException) {
    }
    localSectFile.lineRemove(j, k);
    localSectFile.saveFile();
    this.wTable.campList.remove(i);
    int m = this.wTable.campList.size();
    if (m == 0) {
      this.wTable.setSelect(-1, 0);
      Main.stateStack().change(26);
    } else if (i == m) {
      this.wTable.setSelect(i - 1, 0);
    }
  }

  private void doStart() {
    int i = this.wTable.selectRow;
    if (i < 0) return;
    Item localItem = (Item)this.wTable.campList.get(i);
    if (localItem.camp.isComplete()) {
      return;
    }
    this.wTable.campList.clear();
    Main.cur().campaign = localItem.camp;

    Main3D.menuMusicPlay(Main.cur().campaign.country());

    String str = Main.cur().campaign.nextIntro();
    if (str != null) {
      GUIBWDemoPlay.demoFile = str;
      GUIBWDemoPlay.soundFile = null;
      Main.stateStack().push(58);
      return;
    }

    Main.cur().currentMissionFile = localItem.camp.nextMission();
    if (Main.cur().currentMissionFile == null) {
      new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
      {
        public void result(int paramInt)
        {
        }
      };
      return;
    }
    ((GUIRoot)this.dialogClient.root).setBackCountry("campaign", Main.cur().campaign.branch());
    if (Main.cur().campaign.isDGen())
      Main.stateStack().change(62);
    else
      Main.stateStack().change(28);
  }

  public GUICampaigns(GWindowRoot paramGWindowRoot)
  {
    super(27);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("camps.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bStat = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bDiff = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bDel = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bNew = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bStart = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture, 0.0F, 192.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUICampaigns.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUICampaigns.this.bNew) {
        Main.stateStack().change(26);
        return true;
      }
      int i;
      GUICampaigns.Item localItem;
      if (paramGWindow == GUICampaigns.this.bStat) {
        i = GUICampaigns.this.wTable.selectRow;
        if (i < 0) return true;
        localItem = (GUICampaigns.Item)GUICampaigns.this.wTable.campList.get(i);
        Main.cur().campaign = localItem.camp;
        if (localItem.camp.isDGen())
          Main.stateStack().push(65);
        else
          Main.stateStack().push(31);
        return true;
      }
      if (paramGWindow == GUICampaigns.this.bDiff) {
        i = GUICampaigns.this.wTable.selectRow;
        if (i < 0) return true;
        localItem = (GUICampaigns.Item)GUICampaigns.this.wTable.campList.get(i);
        World.cur().diffUser.set(localItem.camp.difficulty());
        Main.stateStack().push(17);
        return true;
      }
      if (paramGWindow == GUICampaigns.this.bDel) {
        GUICampaigns.this.removeItem();
        return true;
      }
      if (paramGWindow == GUICampaigns.this.bStart) {
        GUICampaigns.this.doStart();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      if ((GUICampaigns.this.wTable.selectRow >= 0) && (((GUICampaigns.Item)GUICampaigns.this.wTable.campList.get(GUICampaigns.this.wTable.selectRow)).camp.isDGen()))
      {
        draw(x1024(80.0F), y1024(544.0F), x1024(336.0F), y1024(48.0F), 2, GUICampaigns.this.i18n("camps.Roster"));
      }
      else draw(x1024(80.0F), y1024(544.0F), x1024(336.0F), y1024(48.0F), 2, GUICampaigns.this.i18n("camps.Statistics"));
      draw(x1024(728.0F), y1024(544.0F), x1024(200.0F), y1024(48.0F), 2, GUICampaigns.this.i18n("camps.View_Difficulty"));

      draw(x1024(96.0F), y1024(658.0F), x1024(128.0F), y1024(48.0F), 0, GUICampaigns.this.i18n("camps.Back"));
      draw(x1024(256.0F), y1024(658.0F), x1024(160.0F), y1024(48.0F), 2, GUICampaigns.this.i18n("camps.Delete"));
      draw(x1024(496.0F), y1024(658.0F), x1024(160.0F), y1024(48.0F), 2, GUICampaigns.this.i18n("camps.New"));
      draw(x1024(766.0F), y1024(658.0F), x1024(160.0F), y1024(48.0F), 2, GUICampaigns.this.i18n("camps.Load"));

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUICampaigns.this.bStat.setPosC(x1024(456.0F), y1024(568.0F));
      GUICampaigns.this.bDiff.setPosC(x1024(968.0F), y1024(568.0F));
      GUICampaigns.this.bExit.setPosC(x1024(56.0F), y1024(682.0F));
      GUICampaigns.this.bDel.setPosC(x1024(456.0F), y1024(682.0F));
      GUICampaigns.this.bNew.setPosC(x1024(696.0F), y1024(682.0F));
      GUICampaigns.this.bStart.setPosC(x1024(968.0F), y1024(682.0F));
      GUICampaigns.this.wTable.set1024PosSize(32.0F, 32.0F, 960.0F, 480.0F);
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList campList = new ArrayList();

    public int countRows() { return this.campList != null ? this.campList.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      GUICampaigns.Item localItem = (GUICampaigns.Item)this.campList.get(paramInt1);
      float f = 0.0F;
      String str = null;
      int i = 0;
      switch (paramInt2) { case 0:
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat2, paramFloat2, localItem.icon);
        f = 1.5F * paramFloat2; paramFloat1 -= f;
        str = localItem.name; break;
      case 1:
        str = localItem.rank; break;
      case 2:
        if (localItem.camp._nawards >= 0)
          str = "" + localItem.camp._nawards;
        else
          str = "" + localItem.camp.awards(localItem.camp.score());
        i = 1; break;
      case 3:
        if (localItem.camp.isComplete()) str = "100%"; else
          str = "" + localItem.camp.completeMissions();
        i = 1; break;
      case 4:
        str = localItem.diff;
      }
      if (paramBoolean) {
        setCanvasColorWHITE();
        draw(f, 0.0F, paramFloat1, paramFloat2, i, str);
      } else {
        setCanvasColorBLACK();
        draw(f, 0.0F, paramFloat1, paramFloat2, i, str);
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
      addColumn(I18N.gui("camps.Career"), null);
      addColumn(I18N.gui("camps.Rank"), null);
      addColumn(I18N.gui("camps.Awards"), null);
      addColumn(I18N.gui("camps.Completed"), null);
      addColumn(I18N.gui("camps.Difficulty"), null);
      this.vSB.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(11.0F);
      getColumn(1).setRelativeDx(6.0F);
      getColumn(2).setRelativeDx(5.0F);
      getColumn(3).setRelativeDx(5.0F);
      getColumn(4).setRelativeDx(6.0F);
      alignColumns();
      this.bNotify = true;
      this.wClient.bNotify = true;

      resized();
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super();
    }
  }

  static class Item
  {
    public String key;
    public Campaign camp;
    public GTexture icon;
    public String name;
    public String rank;
    public String diff;
  }
}