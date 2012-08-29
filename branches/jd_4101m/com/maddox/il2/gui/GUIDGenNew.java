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
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.campaign.Awards;
import com.maddox.il2.game.campaign.AwardsRUfighter;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.game.campaign.CampaignDGen;
import com.maddox.rts.HomePath;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

public class GUIDGenNew extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GWindowEditControl wPBirth;
  public GWindowEditControl wYBirth;
  public GWindowComboControl wSquadron;
  public Table wTable;
  public GUIButton bBack;
  public GUIButton bStart;
  private CampaignDGen cdgen;
  private ArrayList campaigns = new ArrayList();
  private ArrayList regimentList = new ArrayList();

  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 58) {
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
      Main.stateStack().change(62);
      return;
    }
    this.client.activateWindow();
  }

  public void _enter() {
    this.cdgen = ((CampaignDGen)Main.cur().campaign);
    if (!fillTable()) {
      Main.stateStack().pop();
      return;
    }
    fillSquadrons();
    if (World.cur().userCfg.placeBirth != null)
      this.wPBirth.setValue(World.cur().userCfg.placeBirth, false);
    this.wYBirth.setValue("" + World.cur().userCfg.yearBirth, false);
    this.client.activateWindow();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  private boolean exestFile(String paramString) {
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
      localSFSInputStream.close();
    } catch (Exception localException) {
      return false;
    }
    return true;
  }

  private boolean fillTable() {
    this.campaigns.clear();
    String str1 = "dgen/" + this.cdgen.dgenFileName();
    if (!exestFile(str1))
      return false; BufferedReader localBufferedReader = null;
    int k;
    Camp localCamp;
    try { localBufferedReader = new BufferedReader(new SFSReader(str1, RTSConf.charEncoding));
      localBufferedReader.readLine();
      localBufferedReader.readLine();

      while (localBufferedReader.ready())
      {
        String str2 = localBufferedReader.readLine();
        if (str2 == null)
          break;
        str2 = str2.trim();
        int j = str2.length();
        if (j == 0)
          continue;
        str2 = UnicodeTo8bit.load(str2, false);
        k = str2.indexOf(" ");
        if ((k <= 0) || (k == str2.length() - 1))
          continue;
        localCamp = new Camp(null);
        localCamp.key = str2.substring(0, k);
        localCamp.info = str2.substring(k + 1);
        this.campaigns.add(localCamp);
      }
      localBufferedReader.close();
    } catch (Exception localException1) {
      if (localBufferedReader != null) try { localBufferedReader.close(); } catch (Exception localException2) {
        } return false;
    }
    int i = this.campaigns.size();
    if (i > 0) {
      SectFile localSectFile = new SectFile("dgen/planes" + this.cdgen.branch() + this.cdgen.prefix() + ".dat", 0);
      for (k = 0; k < i; k++) {
        localCamp = (Camp)this.campaigns.get(k);
        int m = localSectFile.sectionIndex(localCamp.key);
        if (m >= 0) {
          int n = localSectFile.vars(m);
          for (int i1 = 0; i1 < n; i1++) {
            String str3 = localSectFile.var(m, i1);
            try {
              Class localClass = ObjIO.classForName("air." + str3);
              localCamp.air.add(str3);
              String str4 = Property.stringValue(localClass, "keyName", null);
              localCamp.airInfo.add(I18N.plane(str4));
            } catch (Exception localException3) {
              System.out.println("Section [" + localCamp.key + "] in a file " + localSectFile.fileName() + " contains unknown aircraft " + str3);
            }
          }
        } else {
          System.out.println("The Section [" + localCamp.key + "] in a file " + localSectFile.fileName() + " is NOT found");
        }
        if (localCamp.air.size() == 0)
          return false;
      }
      this.wTable.setSelect(0, 0);
      this.wTable.resolutionChanged();
      return true;
    }
    return false; } 
  private void fillSquadrons() { this.wSquadron.clear(false);
    this.regimentList.clear();
    BufferedReader localBufferedReader = null;
    Object localObject;
    try { localBufferedReader = new BufferedReader(new SFSReader("dgen/squadrons" + this.cdgen.branch() + this.cdgen.prefix() + ".dat"));

      while (localBufferedReader.ready())
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        str = str.trim();
        int j = str.length();
        if (j == 0)
          continue;
        localObject = Actor.getByName(str);
        if ((localObject != null) && ((localObject instanceof Regiment)))
          this.regimentList.add(localObject);
      }
      localBufferedReader.close();
    } catch (Exception localException1) {
      if (localBufferedReader != null) try { localBufferedReader.close();
        } catch (Exception localException2) {
        } 
    }
    this.wSquadron.clear(false);
    this.wSquadron.setSelected(-1, false, false);
    int i = this.regimentList.size();
    for (int k = 0; k < i; k++) {
      localObject = (Regiment)this.regimentList.get(k);
      this.wSquadron.add(I18N.regimentShort(((Regiment)localObject).shortInfo()));
    }
    if (i > 0)
      this.wSquadron.setSelected(0, true, false);
    else
      this.wSquadron.setSelected(-1, true, false); }

  private String fullFileNameCampaignIni()
  {
    Camp localCamp = (Camp)this.campaigns.get(this.wTable.selectRow);
    return "missions/campaign/" + this.cdgen.branch() + "/" + dirNameCampaign() + "/campaign.ini";
  }
  private String dirNameCampaign() {
    Camp localCamp = (Camp)this.campaigns.get(this.wTable.selectRow);
    String str = "DGen_" + this.cdgen.prefix() + "_" + localCamp.key + World.cur().userCfg.sId + this.cdgen.rank();
    return str;
  }

  private String validString(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0) || (" ".equals(paramString)))
      return "_";
    while (true) {
      int i = paramString.indexOf(",");
      if (i < 0) break;
      if (i + 1 <= paramString.length() - 1)
        paramString = paramString.substring(0, i) + "_" + paramString.substring(i + 1);
      else
        paramString = paramString.substring(0, i) + "_";
    }
    return paramString;
  }

  private void doGenerateCampaign() {
    String str1 = "dgen/conf" + this.cdgen.branch() + ".ini";
    String str2 = validString(World.cur().userCfg.surname);
    String str3 = validString(World.cur().userCfg.name);
    String str4 = validString(this.wPBirth.getValue());
    World.cur().userCfg.placeBirth = str4;
    try {
      World.cur().userCfg.yearBirth = Integer.parseInt(this.wYBirth.getValue());
    } catch (Exception localException1) {
      World.cur().userCfg.yearBirth = 1910;
    }
    if (World.cur().userCfg.yearBirth < 1850) World.cur().userCfg.yearBirth = 1850;
    if (World.cur().userCfg.yearBirth > 2050) World.cur().userCfg.yearBirth = 2050; String str5;
    Object localObject2;
    try { PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(HomePath.toFileSystemName(str1, 0)), RTSConf.charEncoding)));

      localPrintWriter.println(str2 + "," + str3 + "," + str4 + "," + World.cur().userCfg.yearBirth);

      localPrintWriter.println(((Regiment)(Regiment)this.regimentList.get(this.wSquadron.getSelected())).name());
      localObject1 = RTSConf.cur.locale.getLanguage();
      str5 = "English";
      if ("ru".equalsIgnoreCase((String)localObject1)) str5 = "Russian";
      else if ("de".equalsIgnoreCase((String)localObject1)) str5 = "German";
      else if ("fr".equalsIgnoreCase((String)localObject1)) str5 = "French";
      else if ("cs".equalsIgnoreCase((String)localObject1)) str5 = "Czech";
      else if ("pl".equalsIgnoreCase((String)localObject1)) str5 = "Polish";
      else if ("hu".equalsIgnoreCase((String)localObject1)) str5 = "Hungarian";
      else if ("lt".equalsIgnoreCase((String)localObject1)) str5 = "Lithuanian";
      else if ("ja".equalsIgnoreCase((String)localObject1)) str5 = "Japanese";
      localPrintWriter.println("dir=missions\\campaign\\" + this.cdgen.branch() + "\\" + dirNameCampaign());
      localPrintWriter.println("Language=" + str5);
      localPrintWriter.println("instant=false");
      for (int i = this.wTable.selectRow; i < this.campaigns.size(); i++) {
        localObject2 = (Camp)this.campaigns.get(i);
        localPrintWriter.println(((Camp)localObject2).key + " " + ((Camp)localObject2).air.get(((Camp)localObject2).select));
      }
      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("File: " + str1 + " save failed: " + localIOException.getMessage());
      localIOException.printStackTrace();
      return;
    }

    World.cur().userCfg.saveConf();

    Camp localCamp = (Camp)this.campaigns.get(this.wTable.selectRow);
    this.cdgen.doExternalCampaignGenerator(localCamp.key);

    Object localObject1 = null;
    try {
      str5 = this.cdgen.branch() + dirNameCampaign();
      String str7 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
      localObject2 = new SectFile(str7, 1, false, World.cur().userCfg.krypto());
      SectFile localSectFile = new SectFile(fullFileNameCampaignIni(), 0);
      String str8 = localSectFile.get("Main", "Class", (String)null);
      Class localClass = ObjIO.classForName(str8);
      localObject1 = (Campaign)localClass.newInstance();
      try
      {
        localClass = ObjIO.classForName(localSectFile.get("Main", "awardsClass", (String)null));
      } catch (Exception localException3) {
        localClass = AwardsRUfighter.class;
      }
      Awards localAwards = (Awards)localClass.newInstance();
      ((Campaign)localObject1).init(localAwards, this.cdgen.branch(), dirNameCampaign(), this.cdgen.difficulty(), this.cdgen.rank());
      ((Campaign)localObject1)._nawards = 0;
      ((Campaign)localObject1)._epilogueTrack = localSectFile.get("Main", "EpilogueTrack", (String)null);
      ((SectFile)localObject2).set("list", str5, localObject1, true);
      ((Campaign)localObject1).clearSavedStatics((SectFile)localObject2);
      ((SectFile)localObject2).saveFile();
    } catch (Exception localException2) {
      System.out.println(localException2.getMessage());
      localException2.printStackTrace();
      return;
    }
    Main.cur().campaign = ((Campaign)localObject1);

    String str6 = Main.cur().campaign.nextIntro();
    if (str6 != null) {
      GUIBWDemoPlay.demoFile = str6;
      GUIBWDemoPlay.soundFile = null;
      Main.stateStack().push(58);
      return;
    }
    Main.cur().currentMissionFile = ((Campaign)localObject1).nextMission();
    if (Main.cur().currentMissionFile == null) {
      new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
      {
        public void result(int paramInt)
        {
        }
      };
      return;
    }
    Main.stateStack().change(62);
  }

  private void delCampaign() {
    try {
      String str1 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
      SectFile localSectFile = new SectFile(str1, 1, false, World.cur().userCfg.krypto());
      int i = localSectFile.sectionIndex("list");
      String str2 = this.cdgen.branch() + dirNameCampaign();
      int j = localSectFile.varIndex(i, str2);
      Campaign localCampaign = (Campaign)ObjIO.fromString(localSectFile.value(i, j));
      String str3 = "missions/campaign/" + localCampaign.branch() + "/" + localCampaign.missionsDir();
      File localFile1 = new File(HomePath.toFileSystemName(str3, 0));
      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null) {
        for (int k = 0; k < arrayOfFile.length; k++) {
          File localFile2 = arrayOfFile[k];
          String str4 = localFile2.getName();
          if ((".".equals(str4)) || ("..".equals(str4)))
            continue;
          localFile2.delete();
        }
      }
      localFile1.delete();
      localCampaign.clearSavedStatics(localSectFile);
      localSectFile.lineRemove(i, j);
      localSectFile.saveFile();
    }
    catch (Exception localException)
    {
    }
  }

  public GUIDGenNew(GWindowRoot paramGWindowRoot)
  {
    super(61);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("dgennew.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wPBirth = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wPBirth.maxLength = 74;
    if ("ja".equals(Locale.getDefault().getLanguage()))
      this.wPBirth.maxLength /= 6;
    this.wYBirth = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wYBirth.bNumericOnly = true;
    this.wYBirth.maxLength = 4;
    this.wSquadron = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.wSquadron.setEditable(false);

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUIDGenNew.this.bBack) {
        Main.cur().campaign = null;
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUIDGenNew.this.bStart) {
        String str = GUIDGenNew.this.fullFileNameCampaignIni();
        if (GUIDGenNew.this.exestFile(str)) {
          new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, GUIDGenNew.this.i18n("campnew.Confirm"), GUIDGenNew.this.i18n("campnew.Exist"), 1, 0.0F)
          {
            public void result(int paramInt)
            {
              if (paramInt == 3) {
                GUIDGenNew.this.delCampaign();
                GUIDGenNew.this.doGenerateCampaign();
              } else {
                GUIDGenNew.this.client.activateWindow();
              }
            }
          };
          return true;
        }
        GUIDGenNew.this.doGenerateCampaign();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(96.0F), y1024(32.0F), x1024(224.0F), y1024(32.0F), 1, GUIDGenNew.this.i18n("dgennew.You"));
      draw(x1024(64.0F), y1024(96.0F), x1024(306.0F), y1024(32.0F), 2, GUIDGenNew.this.i18n("dgennew.Place"));
      draw(x1024(64.0F), y1024(144.0F), x1024(306.0F), y1024(32.0F), 2, GUIDGenNew.this.i18n("dgennew.Year"));
      draw(x1024(64.0F), y1024(240.0F), x1024(306.0F), y1024(32.0F), 2, GUIDGenNew.this.i18n("dgennew.Squadron"));
      draw(x1024(96.0F), y1024(658.0F), x1024(240.0F), y1024(48.0F), 0, GUIDGenNew.this.i18n("dgennew.MainMenu"));
      draw(x1024(400.0F), y1024(658.0F), x1024(240.0F), y1024(48.0F), 2, GUIDGenNew.this.i18n("dgennew.Generate"));

      if ((GUIDGenNew.this.campaigns.size() > 0) && (GUIDGenNew.this.wTable.selectRow >= 0)) {
        draw(x1024(32.0F), y1024(576.0F), x1024(674.0F), y1024(32.0F), 0, GUIDGenNew.this.i18n("dgennew.First") + " " + ((GUIDGenNew.Camp)GUIDGenNew.this.campaigns.get(GUIDGenNew.this.wTable.selectRow)).info);
      }

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(674.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(48.0F), x1024(48.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(336.0F), y1024(48.0F), x1024(368.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(208.0F), x1024(674.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(48.0F), 2.0F, y1024(162.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(704.0F), y1024(48.0F), 2.0F, y1024(162.0F));
    }

    public void setPosSize() {
      set1024PosSize(144.0F, 32.0F, 736.0F, 736.0F);
      GUIDGenNew.this.wPBirth.set1024PosSize(384.0F, 96.0F, 288.0F, 32.0F);
      GUIDGenNew.this.wYBirth.set1024PosSize(384.0F, 144.0F, 288.0F, 32.0F);
      GUIDGenNew.this.wSquadron.set1024PosSize(384.0F, 240.0F, 288.0F, 32.0F);
      GUIDGenNew.this.bBack.setPosC(x1024(56.0F), y1024(682.0F));
      GUIDGenNew.this.bStart.setPosC(x1024(682.0F), y1024(682.0F));
      GUIDGenNew.this.wTable.set1024PosSize(32.0F, 304.0F, 674.0F, 240.0F);
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList campaignsList = GUIDGenNew.this.campaigns;
    int indxCamp;

    public int countRows()
    {
      return this.campaignsList != null ? GUIDGenNew.this.campaigns.size() : 0;
    }
    public boolean isCellEditable(int paramInt1, int paramInt2) {
      return paramInt2 == 1;
    }
    public float rowHeight(int paramInt) { return (int)(this.root.textFonts[0].height * 1.6F); }

    public GWindowCellEdit getCellEdit(int paramInt1, int paramInt2) {
      if (!isCellEditable(paramInt1, paramInt2)) return null;

      this.indxCamp = paramInt1;
      1 local1;
      GWindowCellEdit localGWindowCellEdit = (GWindowCellEdit)this.wClient.create(local1 = new GWindowComboControl() {
        GUIDGenNew.Camp camp = (GUIDGenNew.Camp)GUIDGenNew.this.campaigns.get(GUIDGenNew.Table.this.indxCamp);

        public boolean notify(int paramInt1, int paramInt2) { boolean bool = super.notify(paramInt1, paramInt2);
          if (paramInt1 == 2)
            this.camp.select = paramInt2;
          return bool;
        }
      });
      local1.setEditable(false);
      GUIDGenNew.Camp localCamp = (GUIDGenNew.Camp)GUIDGenNew.this.campaigns.get(paramInt1);
      for (int i = 0; i < localCamp.air.size(); i++)
        local1.add((String)localCamp.airInfo.get(i));
      local1.setSelected(localCamp.select, true, false);
      return localGWindowCellEdit;
    }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      GUIDGenNew.Camp localCamp = (GUIDGenNew.Camp)GUIDGenNew.this.campaigns.get(paramInt1);
      String str = null;
      int i = 0;
      switch (paramInt2) { case 0:
        str = localCamp.info;
        i = 0;
        break;
      case 1:
        str = (String)localCamp.airInfo.get(localCamp.select);
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
      addColumn(I18N.gui("dgennew.Operation"), null);
      addColumn(I18N.gui("dgennew.Plane"), null);
      this.vSB.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(20.0F);
      getColumn(1).setRelativeDx(10.0F);
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

  private static class Camp
  {
    String key;
    String info;
    ArrayList air = new ArrayList();
    ArrayList airInfo = new ArrayList();
    int select = 0;

    private Camp()
    {
    }

    Camp(GUIDGenNew.1 param1)
    {
      this();
    }
  }
}