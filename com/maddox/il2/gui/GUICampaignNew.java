package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.campaign.Awards;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.game.campaign.CampaignDGen;
import com.maddox.rts.HomePath;
import com.maddox.rts.LDRres;
import com.maddox.rts.ObjIO;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class GUICampaignNew extends GameState
{
  public static final String HOME_DIR = "missions/campaign";
  public static final String RANK_FILE = "rank";
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public WComboCountry wCountry;
  public GTexture countryIcon;
  public WComboCampaign wCampaign;
  public GWindowComboControl wRank;
  public ScrollDescript wScrollDescription;
  public Descript wDescript;
  public GUIButton bExit;
  public GUIButton bDifficulty;
  public GUIButton bStart;
  public String country;
  public String campaign;
  public String dgenCampaignPrefix = null;
  public String dgenCampaignFileName = null;
  public String textDescription;
  public int difficulty;
  public boolean bInited = false;
  public ResourceBundle resRank;
  public ResourceBundle resCountry;
  public ArrayList countryLst = new ArrayList();
  public ArrayList campaignLst = new ArrayList();

  private TreeMap _scanMap = new TreeMap();

  public void enterPush(GameState paramGameState)
  {
    this.dgenCampaignPrefix = null;
    World.cur().diffUser.set(World.cur().userCfg.singleDifficulty);
    enter(paramGameState);
  }
  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 17) {
      World.cur().userCfg.singleDifficulty = World.cur().diffUser.get();
      World.cur().userCfg.saveConf();
    }
    enter(paramGameState);
  }
  public void enter(GameState paramGameState) {
    if ((paramGameState != null) && (paramGameState.id() == 58)) {
      Main.cur().currentMissionFile = Main.cur().campaign.nextMission();
      if (Main.cur().currentMissionFile == null) {
        new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
        {
          public void result(int paramInt)
          {
          }
        };
        return;
      }
      Main.stateStack().change(28);
      return;
    }
    this.difficulty = World.cur().diffUser.get();
    _enter();
  }

  public void _enter() {
    init();
    int i = this.wCountry.getSelected();
    if (i >= 0) Main3D.menuMusicPlay((String)this.countryLst.get(i));
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

  private void init() {
    if (this.bInited) return;
    this.resCountry = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());

    this._scanMap.put(this.resCountry.getString("ru"), "ru");
    this._scanMap.put(this.resCountry.getString("de"), "de");

    File localFile = new File(HomePath.get(0), "missions/campaign");
    if (localFile != null) {
      localObject = localFile.listFiles();
      if (localObject != null) {
        for (int i = 0; i < localObject.length; i++) {
          if ((localObject[i].isDirectory()) && (!localObject[i].isHidden())) {
            String str2 = localObject[i].getName().toLowerCase();
            String str3 = null;
            try {
              str3 = this.resCountry.getString(str2);
            } catch (Exception localException) {
              continue;
            }
            if (this._scanMap.containsKey(str3))
              continue;
            this._scanMap.put(str3, str2);
          }
        }
      }
    }

    Object localObject = this._scanMap.keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      String str1 = (String)((Iterator)localObject).next();
      this.countryLst.add(this._scanMap.get(str1));
      this.wCountry.add(str1);
    }
    this._scanMap.clear();

    this.wCountry.setSelected(-1, false, true);
    if (this.countryLst.size() > 0) {
      this.wCountry.setSelected(0, true, true);
    }
    this.bInited = true;
  }

  private boolean fillRank()
  {
    try {
      this.resRank = ResourceBundle.getBundle("missions/campaign/" + this.country + "/" + "rank", RTSConf.cur.locale);
      this.wRank.add(this.resRank.getString("0"));
      this.wRank.add(this.resRank.getString("1"));
      this.wRank.add(this.resRank.getString("2"));
      this.wRank.add(this.resRank.getString("3"));
      this.wRank.add(this.resRank.getString("4"));
      this.wRank.add(this.resRank.getString("5"));
      this.wRank.add(this.resRank.getString("6"));
    } catch (Exception localException) {
      return false;
    }
    this.wRank.setSelected(0, true, false);
    return true;
  }

  private boolean fillCampaign() {
    this.campaignLst.clear();
    this.wCampaign.clear(false);
    fillDGen();
    this._scanMap.clear();
    String str1 = "missions/campaign/" + this.country + "/all.ini";
    int j;
    String str3;
    if (exestFile(str1)) {
      localObject1 = new SectFile(str1, 0);
      int i = ((SectFile)localObject1).sectionIndex("list");
      if (i >= 0) {
        j = ((SectFile)localObject1).vars(i);
        for (int k = 0; k < j; k++) {
          str3 = ((SectFile)localObject1).var(i, k);
          this._scanMap.put(str3.toLowerCase(), null);
        }
      }
    }
    Object localObject1 = new File(HomePath.get(0), "missions/campaign/" + this.country);
    Object localObject2;
    Object localObject3;
    if (localObject1 != null) {
      localObject2 = ((File)localObject1).listFiles();
      if (localObject2 != null) {
        for (j = 0; j < localObject2.length; j++) {
          if ((localObject2[j].isDirectory()) && (!localObject2[j].isHidden())) {
            localObject3 = localObject2[j].getName();
            if (((String)localObject3).indexOf(" ") < 0)
              this._scanMap.put(((String)localObject3).toLowerCase(), null);
          }
        }
      }
    }
    if (this._scanMap.size() > 0) {
      localObject2 = this._scanMap.keySet().iterator();
      while (((Iterator)localObject2).hasNext()) {
        String str2 = (String)((Iterator)localObject2).next();
        try {
          localObject3 = ResourceBundle.getBundle("missions/campaign/" + this.country + "/" + str2 + "/info", RTSConf.cur.locale);
          str3 = ((ResourceBundle)localObject3).getString("Name");
          SectFile localSectFile = new SectFile("missions/campaign/" + this.country + "/" + str2 + "/campaign.ini", 0);
          int m = localSectFile.sectionIndex("list");
          if ((m >= 0) && (localSectFile.vars(m) > 0) && (localSectFile.get("Main", "ExecGenerator", (String)null) == null))
          {
            this.campaignLst.add(str2);
            this.wCampaign.add(str3);
          }
        } catch (Exception localException) {
        }
      }
      this._scanMap.clear();
    }
    if (this.campaignLst.size() == 0)
      return false;
    this.wCampaign.setSelected(-1, false, true);
    this.wCampaign.setSelected(0, true, true);

    return true;
  }

  private void fillDGen() {
    String str1 = RTSConf.cur.locale.getLanguage();
    String str2 = "campaigns" + this.country;
    String str3 = null;
    if (!"us".equals(str1))
      str3 = "_" + str1 + ".dat";
    String str4 = ".dat";

    File localFile = new File(HomePath.toFileSystemName("dgen", 0));
    String[] arrayOfString = localFile.list();
    if ((arrayOfString == null) || (arrayOfString.length == 0))
      return;
    HashMap localHashMap = new HashMap();
    String str5;
    DGenCampaign localDGenCampaign;
    if (str3 != null) {
      for (i = 0; i < arrayOfString.length; i++) {
        localObject = arrayOfString[i];
        if ((localObject == null) || 
          (((String)localObject).length() != str2.length() + 1 + str3.length()) || 
          (!((String)localObject).regionMatches(true, 0, str2, 0, str2.length())) || 
          (!((String)localObject).regionMatches(true, ((String)localObject).length() - str3.length(), str3, 0, str3.length()))) continue;
        str5 = ((String)localObject).substring(str2.length(), str2.length() + 1);
        localDGenCampaign = new DGenCampaign();
        localDGenCampaign.fileName = ((String)localObject);
        localDGenCampaign.prefix = str5;
        localHashMap.put(str5, localDGenCampaign);
      }
    }
    for (int i = 0; i < arrayOfString.length; i++) {
      localObject = arrayOfString[i];
      if ((localObject == null) || 
        (((String)localObject).length() != str2.length() + 1 + str4.length()) || 
        (!((String)localObject).regionMatches(true, 0, str2, 0, str2.length())) || 
        (!((String)localObject).regionMatches(true, ((String)localObject).length() - str4.length(), str4, 0, str4.length()))) continue;
      str5 = ((String)localObject).substring(str2.length(), str2.length() + 1);
      if (!localHashMap.containsKey(str5)) {
        localDGenCampaign = new DGenCampaign();
        localDGenCampaign.fileName = ((String)localObject);
        localDGenCampaign.prefix = str5;
        localHashMap.put(str5, localDGenCampaign);
      }

    }

    if (localHashMap.size() == 0) {
      return;
    }
    this._scanMap.clear();
    Object localObject = localHashMap.keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      str5 = (String)((Iterator)localObject).next();
      localDGenCampaign = (DGenCampaign)localHashMap.get(str5);
      try {
        BufferedReader localBufferedReader = new BufferedReader(new SFSReader("dgen/" + localDGenCampaign.fileName, RTSConf.charEncoding));
        localDGenCampaign.name = UnicodeTo8bit.load(localBufferedReader.readLine(), false);
        localDGenCampaign.description = UnicodeTo8bit.load(localBufferedReader.readLine(), false);
        localBufferedReader.close();
        if ((localDGenCampaign.name != null) && (localDGenCampaign.name.length() > 0) && (localDGenCampaign.description != null) && (localDGenCampaign.description.length() > 0))
        {
          this._scanMap.put(localDGenCampaign.name, localDGenCampaign);
        } } catch (Exception localException) {
      }
    }
    localHashMap.clear();
    localObject = this._scanMap.keySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      str5 = (String)((Iterator)localObject).next();
      localDGenCampaign = (DGenCampaign)this._scanMap.get(str5);
      this.campaignLst.add(localDGenCampaign);
      this.wCampaign.add(localDGenCampaign.name);
    }
    this._scanMap.clear();
  }

  private void fillInfo() {
    this.textDescription = null;
    try {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle("missions/campaign/" + this.country + "/" + this.campaign + "/info", RTSConf.cur.locale);
      this.textDescription = localResourceBundle.getString("Description"); } catch (Exception localException) {
    }
    this.wScrollDescription.resized();
  }

  private void fillCountry(int paramInt) {
    this.wRank.clear(false);
    this.wCampaign.clear(false);
    this.country = ((String)this.countryLst.get(paramInt));
    if (!fillRank()) {
      this.wRank.clear(false);
      this.country = null;
      return;
    }
    if (!fillCampaign()) {
      this.wRank.clear(false);
      this.wCampaign.clear(false);
      this.country = null;
      return;
    }
    this.countryIcon = GTexture.New("missions/campaign/" + this.country + "/icon.mat");
  }

  private void doStartDGenCampaign()
  {
    Main.cur().campaign = new CampaignDGen(this.dgenCampaignFileName, this.country, this.difficulty, this.wRank.getSelected(), this.dgenCampaignPrefix);
    Main.stateStack().change(61);
  }

  private void doStartCampaign() {
    Campaign localCampaign = null;
    try {
      String str1 = this.country + this.campaign;
      String str3 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
      SectFile localSectFile1 = new SectFile(str3, 1, false, World.cur().userCfg.krypto());
      SectFile localSectFile2 = new SectFile("missions/campaign/" + this.country + "/" + this.campaign + "/campaign.ini", 0);
      String str4 = localSectFile2.get("Main", "Class", (String)null);
      Class localClass = ObjIO.classForName(str4);
      localCampaign = (Campaign)localClass.newInstance();
      localClass = ObjIO.classForName(localSectFile2.get("Main", "awardsClass", (String)null));
      Awards localAwards = (Awards)localClass.newInstance();
      localCampaign.init(localAwards, this.country, this.campaign, this.difficulty, this.wRank.getSelected());
      localCampaign._epilogueTrack = localSectFile2.get("Main", "EpilogueTrack", (String)null);
      localSectFile1.set("list", str1, localCampaign, true);
      localCampaign.clearSavedStatics(localSectFile1);
      localSectFile1.saveFile();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      return;
    }
    Main.cur().campaign = localCampaign;

    String str2 = Main.cur().campaign.nextIntro();
    if (str2 != null) {
      GUIBWDemoPlay.demoFile = str2;
      GUIBWDemoPlay.soundFile = null;
      Main.stateStack().push(58);
      return;
    }
    Main.cur().currentMissionFile = localCampaign.nextMission();
    if (Main.cur().currentMissionFile == null) {
      new GWindowMessageBox(Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, i18n("miss.Error"), i18n("miss.LoadFailed"), 3, 0.0F)
      {
        public void result(int paramInt)
        {
        }
      };
      return;
    }
    Main.stateStack().change(28);
  }

  public GUICampaignNew(GWindowRoot paramGWindowRoot)
  {
    super(26);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("campnew.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wCountry = ((WComboCountry)this.dialogClient.addControl(new WComboCountry(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wCountry.setEditable(false);
    this.wCampaign = ((WComboCampaign)this.dialogClient.addControl(new WComboCampaign(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wCampaign.setEditable(false);
    this.wRank = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wRank.setEditable(false);
    this.dialogClient.create(this.wScrollDescription = new ScrollDescript());

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bDifficulty = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
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

      if (paramGWindow == GUICampaignNew.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUICampaignNew.this.bDifficulty) {
        World.cur().diffUser.set(GUICampaignNew.this.difficulty);
        Main.stateStack().push(17);
        return true;
      }
      if (paramGWindow == GUICampaignNew.this.bStart) {
        if (GUICampaignNew.this.dgenCampaignPrefix == null) {
          String str1 = GUICampaignNew.this.country + GUICampaignNew.this.campaign;
          String str2 = "users/" + World.cur().userCfg.sId + "/campaigns.ini";
          if (GUICampaignNew.this.exestFile(str2)) {
            SectFile localSectFile = new SectFile(str2, 0, true, World.cur().userCfg.krypto());
            int i = localSectFile.sectionIndex("list");
            if ((i >= 0) && 
              (localSectFile.varExist(i, str1))) {
              new GUICampaignNew.3(this, Main3D.cur3D().guiManager.root, 20.0F, true, GUICampaignNew.this.i18n("campnew.Confirm"), GUICampaignNew.this.i18n("campnew.Exist"), 1, 0.0F);

              return true;
            }
          }
        }

        if (GUICampaignNew.this.dgenCampaignPrefix == null)
          GUICampaignNew.this.doStartCampaign();
        else
          GUICampaignNew.this.doStartDGenCampaign();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(112.0F), y1024(32.0F), x1024(272.0F), y1024(32.0F), 0, GUICampaignNew.this.i18n("campnew.Country"));
      draw(x1024(112.0F), y1024(144.0F), x1024(272.0F), y1024(32.0F), 0, GUICampaignNew.this.i18n("campnew.Rank"));
      draw(x1024(464.0F), y1024(32.0F), x1024(432.0F), y1024(32.0F), 0, GUICampaignNew.this.i18n("campnew.Career"));
      draw(x1024(464.0F), y1024(208.0F), x1024(432.0F), y1024(32.0F), 0, GUICampaignNew.this.i18n("campnew.Description"));
      draw(x1024(32.0F), y1024(292.0F), x1024(304.0F), y1024(48.0F), 2, GUICampaignNew.this.i18n("campnew.Difficulty"));
      draw(x1024(96.0F), y1024(658.0F), x1024(224.0F), y1024(48.0F), 0, GUICampaignNew.this.i18n("campnew.MainMenu"));
      draw(x1024(718.0F), y1024(658.0F), x1024(208.0F), y1024(48.0F), 2, GUICampaignNew.this.i18n("campnew.Start"));

      if (GUICampaignNew.this.countryIcon != null) {
        draw(x1024(32.0F), y1024(64.0F), x1024(64.0F), y1024(64.0F), GUICampaignNew.this.countryIcon);
      }
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(256.0F), x1024(368.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(960.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(448.0F), y1024(192.0F), x1024(544.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(432.0F), y1024(32.0F), 2.0F, y1024(576.0F));
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUICampaignNew.this.wCountry.setPosSize(x1024(112.0F), y1024(80.0F), x1024(288.0F), M(1.7F));
      GUICampaignNew.this.wCampaign.setPosSize(x1024(464.0F), y1024(80.0F), x1024(528.0F), M(1.7F));
      GUICampaignNew.this.wRank.setPosSize(x1024(112.0F), y1024(192.0F), x1024(288.0F), M(1.7F));

      GUICampaignNew.this.wScrollDescription.setPosSize(x1024(464.0F), y1024(256.0F), x1024(528.0F), y1024(336.0F));

      GUICampaignNew.this.bExit.setPosC(x1024(56.0F), y1024(682.0F));
      GUICampaignNew.this.bDifficulty.setPosC(x1024(375.0F), y1024(313.0F));
      GUICampaignNew.this.bStart.setPosC(x1024(968.0F), y1024(682.0F));
    }
  }

  public class WComboCampaign extends GWindowComboControl
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = getSelected();
        if (i < 0)
          return true;
        Object localObject = GUICampaignNew.this.campaignLst.get(i);
        if ((localObject instanceof String)) {
          GUICampaignNew.this.campaign = ((String)localObject);
          GUICampaignNew.this.dgenCampaignPrefix = null;
          GUICampaignNew.this.fillInfo();
        } else {
          GUICampaignNew.DGenCampaign localDGenCampaign = (GUICampaignNew.DGenCampaign)localObject;
          GUICampaignNew.this.campaign = localDGenCampaign.name;
          GUICampaignNew.this.dgenCampaignPrefix = localDGenCampaign.prefix;
          GUICampaignNew.this.dgenCampaignFileName = localDGenCampaign.fileName;
          GUICampaignNew.this.textDescription = localDGenCampaign.description;
          GUICampaignNew.this.wScrollDescription.resized();
        }
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }

    public WComboCampaign(GWindow paramFloat1, float paramFloat2, float paramFloat3, float arg5) {
      super(paramFloat2, paramFloat3, localObject);
    }
  }

  public class WComboCountry extends GWindowComboControl
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = getSelected();
        if (i < 0)
          return true;
        GUICampaignNew.this.fillCountry(i);
        Main3D.menuMusicPlay((String)GUICampaignNew.this.countryLst.get(i));
        ((GUIRoot)this.root).setBackCountry("campaign", (String)GUICampaignNew.this.countryLst.get(i));
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }

    public WComboCountry(GWindow paramFloat1, float paramFloat2, float paramFloat3, float arg5) {
      super(paramFloat2, paramFloat3, localObject);
    }
  }

  public class Descript extends GWindowDialogClient
  {
    public Descript()
    {
    }

    public void render()
    {
      String str = GUICampaignNew.this.textDescription;
      if (str != null) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        setCanvasColorBLACK();
        drawLines(localGBevel.L.dx + 2.0F, localGBevel.T.dy + 2.0F, str, 0, str.length(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F, this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height);
      }
    }

    public void computeSize() {
      String str = GUICampaignNew.this.textDescription;
      if (str != null) {
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        setCanvasFont(0);
        int i = computeLines(str, 0, str.length(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        if (this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy > this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) {
          this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = (this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - lookAndFeel().getVScrollBarW());
          i = computeLines(str, 0, str.length(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx - 4.0F);
          this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height * i + localGBevel.T.dy + localGBevel.B.dy + 4.0F);
        }
      } else {
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
        this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = this.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
      }
    }
  }

  public class ScrollDescript extends GWindowScrollingDialogClient
  {
    public ScrollDescript()
    {
    }

    public void created()
    {
      this.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient = (GUICampaignNew.this.wDescript = (GUICampaignNew.Descript)create(new GUICampaignNew.Descript(GUICampaignNew.this)));
      this.jdField_fixed_of_type_ComMaddoxGwindowGWindowDialogClient.bNotify = true;
      this.bNotify = true;
    }
    public void resized() {
      if (GUICampaignNew.this.wDescript != null) {
        GUICampaignNew.this.wDescript.computeSize();
      }
      super.resized();
      if (this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible()) {
        GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
        this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - lookAndFeel().getVScrollBarW() - localGBevel.R.dx, localGBevel.T.dy);
        this.jdField_vScroll_of_type_ComMaddoxGwindowGWindowVScrollBar.setSize(lookAndFeel().getVScrollBarW(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy);
      }
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void render() {
      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, 0.0F, 0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, true);
    }
  }

  static class DGenCampaign
  {
    String fileName;
    String prefix;
    String name;
    String description;
  }
}