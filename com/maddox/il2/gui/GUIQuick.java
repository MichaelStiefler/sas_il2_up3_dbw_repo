package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.rts.IniFile;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class GUIQuick extends GameState
{
  private String russianMixedRules = "< ',' < '.' < '-' <а,А< a,A <б,Б< b,B <в,В< v,V <г,Г< g,G <д,Д< d,D <е,Е < ё,Ё < ж,Ж < з,З< z,Z <и,И< i,I <й,Й< j,J <к,К< k,K <л,Л< l,L <м,М< m,M <н,Н< n,N <о,О< o,O <п,П< p,P <р,Р< r,R <с,С< s,S <т,Т< t,T <у,У< u,U <ф,Ф< f,F <х,Х< h,H <ц,Ц< c,C <ч,Ч < ш,Ш < щ,Щ < ъ,Ъ < ы,Ы< i,I <ь,Ь < э,Э< e,E <ю,Ю < я,Я< q,Q < x,X < y,Y";
  private RuleBasedCollator collator;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public SectFile ssect;
  public GTexture cross;
  public GTexture star;
  public boolean OUR;
  public boolean bScramble;
  public GUIButton bArmy;
  public GUIButton bNext;
  public GUIButton bStat;
  public GUIButton bExit;
  public GUIButton bBack;
  public GUIButton bLoad;
  public GUIButton bSave;
  public GUIButton bFly;
  public GUIButton bDiff;
  public GUIButton bReset;
  public GWindowComboControl wSituation;
  public GWindowComboControl wMap;
  public GWindowComboControl wTarget;
  public GWindowComboControl wPos;
  public GWindowComboControl wDefence;
  public GWindowComboControl wAltitude;
  public GWindowComboControl wWeather;
  public GWindowComboControl wCldHeight;
  public GWindowComboControl wTimeHour;
  public GWindowComboControl wTimeMins;
  public GWindowComboControl wLevel;
  public GWindowComboControl wPlaneList;
  private String r01;
  private String r010;
  private String g01;
  private String g010;
  private String[] _mapKey;
  private String[] folderNames;
  private String[] _targetKey = { "None", "Armor", "Bridge", "Airbase", "Scramble" };
  private ArrayList playerPlane;
  private ArrayList aiPlane;
  private ArrayList playerPlaneC;
  private ArrayList aiPlaneC;
  private ItemWing[] wing;
  private ItemDlg[] dlg;
  private IOState ioState;
  private int indxAirArming;
  private boolean bPlaneArrestor;
  private boolean bFirst;
  static boolean bIsQuick;
  private static int pl;
  private static final String PREFIX = "Missions/Quick/";
  private boolean noneTarget;
  private String[] noneTargetSuffix = { "N", "A", "D" };
  private static final int NONE = 0;
  private static final int ARMOR = 1;
  private static final int BRIDGE = 2;
  private static final int AIRBASE = 3;
  private static final int SCRAMBLE = 4;
  private static final int ADVANTAGE = 1;
  private String currentMissionName = "";
  private boolean bNoAvailableMissions = false;
  static Class class$com$maddox$il2$objects$air$TypeTransport;

  private void defaultRegiments()
  {
    this.r01 = "r01";
    this.r010 = "r010";
    this.g01 = "g01";
    this.g010 = "g010";
    try {
      SectFile localSectFile = new SectFile(this.currentMissionName, 0);
      if (localSectFile.sectionIndex("r0100") < 0) {
        this.r01 = "usa01";
        this.r010 = "usa010";
        this.g01 = "ja01";
        this.g010 = "ja010";
      }
      for (int i = 0; i < 8; i++)
        if (this.OUR) {
          this.wing[i].regiment = this.r01;
          this.wing[(i + 8)].regiment = this.g01;
        }
        else {
          this.wing[i].regiment = this.g01;
          this.wing[(i + 8)].regiment = this.r01;
        }
    }
    catch (Exception localException) {
      System.out.println("WARNING: No quick missions in Missions folder.");
    }
  }

  private void mapChanged() {
    int i = this.wMap.getSelected();
    if ((i >= 0) && (i < this._mapKey.length) && (!this.bNoAvailableMissions)) {
      ArrayList localArrayList = getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + this._targetKey[this.wTarget.getSelected()] + (this.wTarget.getSelected() == 0 ? this.noneTargetSuffix[this.wLevel.getSelected()] : ""));
      Random localRandom = new Random();

      if (localArrayList.size() < 1) {
        return;
      }
      int j = localRandom.nextInt(localArrayList.size());
      String str1 = "Missions/Quick/" + getMapName() + "/" + (String)localArrayList.get(j);
      this.currentMissionName = str1;
      SectFile localSectFile = new SectFile(this.currentMissionName, 0);
      int k;
      if (localSectFile.sectionIndex("r0100") >= 0) {
        this.r01 = "r01";
        this.r010 = "r010";
        this.g01 = "g01";
        this.g010 = "g010";
        for (k = 0; k < 16; k++)
          if (k < 8) {
            if (this.wing[k].regiment.equals("usa01"))
              this.wing[k].regiment = "r01";
          }
          else if (this.wing[k].regiment.equals("ja01"))
            this.wing[k].regiment = "g01";
      }
      else
      {
        this.r01 = "usa01";
        this.r010 = "usa010";
        this.g01 = "ja01";
        this.g010 = "ja010";
        for (k = 0; k < 16; k++) {
          if (k < 8) {
            if (this.wing[k].regiment.equals("r01"))
              this.wing[k].regiment = "usa01";
          }
          else if (this.wing[k].regiment.equals("g01")) {
            this.wing[k].regiment = "ja01";
          }
        }

      }

      String str2 = localSectFile.get("MAIN", "MAP", (String)null);
      if (str2 != null) {
        IniFile localIniFile = new IniFile("maps/" + str2, 0);
        String str3 = localIniFile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");
        if (World.cur() != null)
          World.cur().setCamouflage(str3);
      }
    }
  }

  public void enterPush(GameState paramGameState) {
    World.cur().diffUser.set(World.cur().userCfg.singleDifficulty);
    if (exisstFile("quicks/.last.quick")) {
      GUIQuickLoad localGUIQuickLoad = (GUIQuickLoad)GameState.get(25);
      localGUIQuickLoad.execute(".last", true);
      load();
    }
    this.wing[0].fromUserCfg();
    mapChanged();
    if (this.r01.equals("usa01"))
      Main3D.menuMusicPlay(this.OUR ? "us" : "ja");
    else
      Main3D.menuMusicPlay(this.OUR ? "ru" : "de");
    _enter();
  }

  public void enterPop(GameState paramGameState) {
    if (paramGameState.id() == 17) {
      World.cur().userCfg.singleDifficulty = World.cur().diffUser.get();
      World.cur().userCfg.saveConf();
    } else if (paramGameState.id() == 55) {
      this.wing[this.indxAirArming].fromAirArming();
    } else if (paramGameState.id() == 25) {
      this.wPlaneList.setSelected(0, true, false);
      setPlaneList(this.wPlaneList.getSelected());
      fillArrayPlanes();
      for (int i = 0; i < 16; i++) {
        fillComboPlane(this.dlg[i].wPlane, i == 0);
        int j = this.dlg[i].wPlane.getSelected();
        if (j == 0)
          this.dlg[i].wPlane.setSelected(1, false, false);
        this.dlg[i].wPlane.setSelected(0, true, true);
      }
      load();
    }
    _enter();
  }

  public void _enter() {
    Main.cur().currentMissionFile = null;
    setQMB(true);
    String str = "users/" + World.cur().userCfg.sId + "/QMB.ini";
    if (!exisstFile(str)) {
      initStat();
    }
    this.client.activateWindow();
  }

  public void _leave() {
    World.cur().userCfg.saveConf();
    this.client.hideWindow();
  }

  private static int getPlaneList() {
    return pl;
  }

  private static void setPlaneList(int paramInt) {
    pl = paramInt;
  }

  static boolean isQMB() {
    return bIsQuick;
  }

  static void setQMB(boolean paramBoolean) {
    bIsQuick = paramBoolean;
  }

  static void initStat() {
    String str1 = "users/" + World.cur().userCfg.sId + "/QMB.ini";
    SectFile localSectFile = new SectFile(str1, 1, false, World.cur().userCfg.krypto());

    String str2 = "MAIN";
    localSectFile.sectionAdd(str2);
    int i = localSectFile.sectionIndex(str2);
    float f = 0.0F;
    int j = 0;

    localSectFile.lineAdd(i, "qmbTotalScore", "" + j);
    localSectFile.lineAdd(i, "qmbTotalAirKill", "" + j);

    localSectFile.lineAdd(i, "qmbTotalGroundKill", "" + j);

    localSectFile.lineAdd(i, "qmbTotalBulletsFired", "" + j);
    localSectFile.lineAdd(i, "qmbTotalBulletsHitAir", "" + j);
    localSectFile.lineAdd(i, "qmbTotalBulletsFiredHitGround", "" + j);

    localSectFile.lineAdd(i, "qmbTotalPctAir", "" + f);
    localSectFile.lineAdd(i, "qmbTotalPctGround", "" + f);

    localSectFile.lineAdd(i, "qmbTotalTankKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalCarKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalArtilleryKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalAAAKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalTrainKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalShipKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalAirStaticKill", "" + j);
    localSectFile.lineAdd(i, "qmbTotalBridgeKill", "" + j);

    localSectFile.lineAdd(i, "qmbTotalPara", "" + j);
    localSectFile.lineAdd(i, "qmbTotalDead", "" + j);

    localSectFile.lineAdd(i, "qmbTotalBombsFired", "" + j);
    localSectFile.lineAdd(i, "qmbTotalBombsHit", "" + j);

    localSectFile.lineAdd(i, "qmbTotalRocketsFired", "" + j);
    localSectFile.lineAdd(i, "qmbTotalRocketsHit", "" + j);

    localSectFile.lineAdd(i, "qmbTotalPctBomb", "" + f);
    localSectFile.lineAdd(i, "qmbTotalPctRocket", "" + f);

    localSectFile.saveFile();
  }

  private static boolean exisstFile(String paramString)
  {
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
      localSFSInputStream.close();
    } catch (Exception localException) {
      return false;
    }
    return true;
  }

  private void validateEditableComboControl(GWindowComboControl paramGWindowComboControl) {
    String str = paramGWindowComboControl.getValue();
    if (str.equals("")) {
      paramGWindowComboControl.setSelected(0, true, false);
      str = paramGWindowComboControl.get(0);
    }
    int i = Integer.parseInt(str);
    if (i < Integer.parseInt(paramGWindowComboControl.get(0))) {
      paramGWindowComboControl.setSelected(0, true, false);
      i = Integer.parseInt(paramGWindowComboControl.getValue());
    }
    if (i > Integer.parseInt(paramGWindowComboControl.get(paramGWindowComboControl.size() - 1))) {
      paramGWindowComboControl.setSelected(paramGWindowComboControl.size() - 1, true, false);
      i = Integer.parseInt(paramGWindowComboControl.getValue());
    }
    for (int j = 0; j < paramGWindowComboControl.size(); j++)
      if (i == Integer.parseInt(paramGWindowComboControl.get(j)))
        paramGWindowComboControl.setSelected(j, true, false);
  }

  public void startQuickMission()
  {
    ArrayList localArrayList = getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + this._targetKey[this.wTarget.getSelected()] + (this.wTarget.getSelected() == 0 ? this.noneTargetSuffix[this.wLevel.getSelected()] : ""));
    Random localRandom = new Random();
    int i = localRandom.nextInt(localArrayList.size());
    String str1 = "Missions/Quick/" + getMapName() + "/" + (String)localArrayList.get(i);
    try {
      SectFile localSectFile = new SectFile(str1, 0);
      localSectFile.set("MAIN", "TIME", this.wTimeHour.getValue() + "." + this.wTimeMins.getSelected() * 25);
      for (int j = 0; j < 8; j++)
      {
        if (j < 4)
          localObject2 = this.r010 + Character.forDigit(j, 10);
        else {
          localObject2 = this.r01 + "1" + Character.forDigit(j - 4, 10);
        }
        if (!localSectFile.exist("Wing", (String)localObject2)) {
          throw new Exception("Section Red " + (String)localObject2 + " not found");
        }
      }
      for (j = 0; j < 8; j++)
      {
        if (j < 4)
          localObject2 = this.g010 + Character.forDigit(j, 10);
        else {
          localObject2 = this.g01 + "1" + Character.forDigit(j - 4, 10);
        }
        if (!localSectFile.exist("Wing", (String)localObject2)) {
          throw new Exception("Section Blue " + (String)localObject2 + " not found");
        }
      }
      localSectFile.set("MAIN", "CloudType", this.wWeather.getSelected());
      localSectFile.set("MAIN", "CloudHeight", Integer.parseInt(this.wCldHeight.getValue()));
      localObject1 = new String[16];
      Object localObject2 = new String[16];

      for (int k = 0; k < 16; k++) {
        if (k < 8)
          localObject1[k] = ((this.OUR ? this.r01 : this.g01) + (k < 4 ? "0" : "1") + k);
        else {
          localObject1[k] = ((this.OUR ? this.g01 : this.r01) + (k < 12 ? "0" : "1") + k);
        }
        localObject2[k] = this.wing[k].prepareWing(localSectFile);
      }

      if (localObject2[0] != null)
        localSectFile.set("MAIN", "player", localObject2[0]);
      else
        localSectFile.set("MAIN", "player", localObject1[0]);
      for (k = 0; k < 16; k++) {
        if (localObject2[k] != null) {
          this.wing[k].prepereWay(localSectFile, localObject1, localObject2);
        }
      }
      if (this.wDefence.getSelected() == 0)
      {
        int n;
        int i1;
        String str5;
        String str8;
        for (k = 0; k < 2; k++) {
          String str2 = k != 0 ? "NStationary" : "Stationary";
          n = localSectFile.sectionIndex(str2);
          if (n >= 0) {
            localSectFile.sectionRename(n, "Stationary_Temp");
            localSectFile.sectionAdd(str2);
            i1 = localSectFile.sectionIndex(str2);
            int i2 = localSectFile.vars(n);
            for (int i3 = 0; i3 < i2; i3++) {
              SharedTokenizer.set(localSectFile.line(n, i3));
              str5 = null;
              if (k == 1)
                str5 = SharedTokenizer.next("");
              String str6 = SharedTokenizer.next("");
              int i5 = SharedTokenizer.next(0);
              str8 = null;
              if (str5 != null)
                str8 = str5 + " " + str6 + " " + i5 + " " + SharedTokenizer.getGap();
              else
                str8 = str6 + " " + i5 + " " + SharedTokenizer.getGap();
              if (i5 == 0)
                localSectFile.lineAdd(i1, str8);
              else if ((i5 == 1) && (this.OUR) && (!this.bScramble))
                localSectFile.lineAdd(i1, str8);
              else if ((i5 == 2) && (!this.OUR) && (!this.bScramble))
                localSectFile.lineAdd(i1, str8);
              else
                try {
                  Class localClass = ObjIO.classForName(str6);
                  if (!AAA.class.isAssignableFrom(localClass))
                    if (str6.startsWith("ships.")) {
                      SharedTokenizer.set(localSectFile.line(n, i3));
                      if (k == 1)
                        str5 = SharedTokenizer.next("");
                      String str9 = SharedTokenizer.next("");
                      String str10 = SharedTokenizer.next("");
                      String str11 = SharedTokenizer.next("");
                      String str12 = SharedTokenizer.next("");
                      String str13 = SharedTokenizer.next("");
                      String str14 = SharedTokenizer.next("");
                      String str15 = SharedTokenizer.next("");
                      String str16 = SharedTokenizer.next("");
                      if (k == 1)
                        localSectFile.lineAdd(i1, str5 + " " + str9 + " " + str10 + " " + str11 + " " + str12 + " " + str13 + " " + str14 + " " + 5940 + " " + str16);
                      else
                        localSectFile.lineAdd(i1, str9 + " " + str10 + " " + str11 + " " + str12 + " " + str13 + " " + str14 + " " + 5940 + " " + str16);
                    } else {
                      localSectFile.lineAdd(i1, str8);
                    }
                } catch (Throwable localThrowable) {
                }
            }
            localSectFile.sectionRemove(n);
          }
        }
        k = localSectFile.sectionIndex("Chiefs");
        if (k >= 0) {
          localSectFile.sectionRename(k, "Chiefs_Temp");
          localSectFile.sectionAdd("Chiefs");
          int m = localSectFile.sectionIndex("Chiefs");
          n = localSectFile.vars(k);
          for (i1 = 0; i1 < n; i1++) {
            String str3 = localSectFile.line(k, i1);
            SharedTokenizer.set(str3);
            String str4 = SharedTokenizer.next("");
            str5 = SharedTokenizer.next("");
            if (str5.startsWith("Ships.")) {
              int i4 = SharedTokenizer.next(0);
              if (i4 == 0) {
                localSectFile.lineAdd(m, str3);
              } else if ((i4 == 1) && (this.OUR) && (!this.bScramble)) {
                localSectFile.lineAdd(m, str3);
              } else if ((i4 == 2) && (!this.OUR) && (!this.bScramble)) {
                localSectFile.lineAdd(m, str3);
              } else {
                String str7 = SharedTokenizer.next("");
                str8 = SharedTokenizer.next("");
                localSectFile.lineAdd(m, str4 + " " + str5 + " " + i4 + " " + 5940 + " " + str8);
              }
            } else {
              localSectFile.lineAdd(m, str3);
            }
          }
          localSectFile.sectionRemove(k);
        }
      }

      GUIQuickStats.resetMissionStat();

      Main.cur().currentMissionFile = localSectFile;
      Main.stateStack().push(5);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      System.out.println(">> no file: " + str1 + "");
      Object localObject1 = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, "Error", "Data file corrupt 1", 3, 0.0F);
      return;
    }
  }

  public void save(boolean paramBoolean) {
    try {
      this.ssect = new SectFile();
      this.ssect.sectionAdd("states");
      this.ioState.save();
      this.ssect.set("states", "head", this.ioState, false);
      for (int i = 0; i < 16; i++)
        this.ssect.set("states", "wing" + i, this.wing[i], false);
    }
    catch (Exception localException) {
      System.out.println("sorry, cant save");
      GWindowMessageBox localGWindowMessageBox = new GWindowMessageBox(this.client, 20.0F, true, "Error", "Can't save data file", 3, 0.0F);
    }
    GUIQuickSave localGUIQuickSave = (GUIQuickSave)GameState.get(24);
    localGUIQuickSave.sect = this.ssect;
    if (paramBoolean)
      localGUIQuickSave.execute(".last", false);
  }

  public void load()
  {
    setDefaultValues();
    int i = 0;
    int j = 16;
    if (this.ssect == null)
      return;
    try {
      this.ssect.get("states", "head", this.ioState);
      if (this.ssect.get("states", "wing8", this.wing[8]) == null) {
        i = 4;
        j = 8;

        this.ioState.getMap();
      }

      for (int k = 0; k < j; k++) {
        int m = 0;
        if ((i > 0) && (k > 3))
          m = 4;
        this.ssect.get("states", "wing" + k, this.wing[(k + m)]);
      }
    }
    catch (Exception localException)
    {
      GWindowMessageBox localGWindowMessageBox = new GWindowMessageBox(this.client, 20.0F, true, "Error", "Data file corrupt 2", 3, 0.0F);
    }
    onArmyChange();
    setShow((this.bFirst) && (this.noneTarget), this.wLevel);
  }

  private boolean checkCustomAirIni(String paramString) {
    SectFile localSectFile1 = new SectFile(paramString);
    if (localSectFile1.sections() <= 0)
      return false;
    SectFile localSectFile2 = new SectFile("com/maddox/il2/objects/air.ini");
    int i = localSectFile1.vars(0);
    for (int j = 0; j < i; j++) {
      if (localSectFile2.varExist(0, localSectFile1.var(0, j)))
        return true;
    }
    return false;
  }
  public void fillArrayPlanes() {
    this.playerPlane.clear();
    this.playerPlaneC.clear();
    this.aiPlane.clear();
    this.aiPlaneC.clear();

    int i = 0;
    String str1;
    if (getPlaneList() < 2) {
      str1 = "com/maddox/il2/objects/air.ini";

      if (getPlaneList() == 1)
        i = 1;
    }
    else if (checkCustomAirIni("Missions/Quick/QMBair_" + getPlaneList() + ".ini")) {
      str1 = "Missions/Quick/QMBair_" + getPlaneList() + ".ini";
    } else {
      str1 = "com/maddox/il2/objects/air.ini";
      this.wPlaneList.setSelected(0, true, false);
    }

    SectFile localSectFile1 = new SectFile(str1, 0);
    SectFile localSectFile2 = new SectFile("com/maddox/il2/objects/air.ini");
    int j = 0;
    int k = localSectFile1.sections();
    if (k <= 0)
      throw new RuntimeException("GUIQuick: file '" + str1 + "' is empty");
    for (int m = 0; m < k; m++)
    {
      int n = localSectFile1.vars(m);
      for (int i1 = 0; i1 < n; i1++) {
        String str2 = localSectFile1.var(m, i1);
        if (localSectFile2.varExist(0, str2)) {
          j = localSectFile2.varIndex(0, str2);

          NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile2.value(m, j));
          String str3 = localNumberTokenizer.next((String)null);
          int i2 = 1;
          while (localNumberTokenizer.hasMoreTokens()) {
            if ("NOQUICK".equals(localNumberTokenizer.next())) {
              i2 = 0;
            }
          }
          if (i2 != 0) {
            Class localClass = null;
            try {
              localClass = ObjIO.classForName(str3);
            } catch (Exception localException) {
              System.out.println("GUIQuick: class '" + str3 + "' not found");
              break;
            }
            ItemAir localItemAir = new ItemAir(str2, localClass, str3);
            if (localItemAir.bEnablePlayer) {
              this.playerPlane.add(localItemAir);
              if (AirportCarrier.isPlaneContainsArrestor(localClass))
                this.playerPlaneC.add(localItemAir);
            }
            this.aiPlane.add(localItemAir);
            if (AirportCarrier.isPlaneContainsArrestor(localClass))
              this.aiPlaneC.add(localItemAir); 
          }
        }
      }
    }
    if (i != 0) {
      Collections.sort(this.playerPlane, new byI18N_name());
      Collections.sort(this.playerPlaneC, new byI18N_name());
      Collections.sort(this.aiPlane, new byI18N_name());
      Collections.sort(this.aiPlaneC, new byI18N_name());
    }
  }

  public void fillComboPlane(GWindowComboControl paramGWindowComboControl, boolean paramBoolean) {
    paramGWindowComboControl.clear();
    ArrayList localArrayList = null;
    if (this.bPlaneArrestor)
      localArrayList = paramBoolean ? this.playerPlaneC : this.aiPlaneC;
    else
      localArrayList = paramBoolean ? this.playerPlane : this.aiPlane;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      ItemAir localItemAir = (ItemAir)localArrayList.get(j);
      paramGWindowComboControl.add(I18N.plane(localItemAir.name));
    }
    paramGWindowComboControl.setSelected(0, true, false);
  }

  public String fillComboWeapon(GWindowComboControl paramGWindowComboControl, ItemAir paramItemAir, int paramInt) {
    paramGWindowComboControl.clear();
    Class localClass = paramItemAir.clazz;
    String[] arrayOfString = Aircraft.getWeaponsRegistered(localClass);
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      for (int i = 0; i < arrayOfString.length; i++) {
        String str = arrayOfString[i];
        paramGWindowComboControl.add(I18N.weapons(paramItemAir.name, str));
      }
      paramGWindowComboControl.setSelected(paramInt, true, false);
    }
    return arrayOfString[paramInt];
  }

  public String Localize(String paramString) {
    return I18N.gui(paramString);
  }

  public GUIQuick(GWindowRoot paramGWindowRoot) {
    super(14);
    try {
      this.collator = new RuleBasedCollator(this.russianMixedRules);
    } catch (Exception localException) {
      localException.printStackTrace();
    }

    this.bFirst = true;
    this.OUR = true;
    this.bScramble = false;
    this.noneTarget = true;
    pl = Config.cur.ini.get("QMB", "PlaneList", 0, 0, 3);
    if (Config.cur.ini.get("QMB", "DumpPlaneList", 0, 0, 1) > 0) {
      dumpFullPlaneList();
    }
    File localFile = new File("Missions/Quick/QMBair_" + pl + ".ini");
    if (pl > 1)
    {
      if (!localFile.exists()) {
        pl = 0;
      }
      else if (!checkCustomAirIni("Missions/Quick/QMBair_" + pl + ".ini")) {
        pl = 0;
      }

    }

    this.r01 = "r01";
    this.r010 = "r010";
    this.g01 = "g01";
    this.g010 = "g010";

    this.playerPlane = new ArrayList();
    this.aiPlane = new ArrayList();
    this.playerPlaneC = new ArrayList();
    this.aiPlaneC = new ArrayList();
    this.wing = new ItemWing[16];
    this.dlg = new ItemDlg[16];
    this.ioState = new IOState();
    this.bPlaneArrestor = false;
    fillArrayPlanes();
    for (int i = 0; i < 16; i++)
      this.wing[i] = new ItemWing(i);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = Localize("quick.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));
    GTexture localGTexture1 = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    GTexture localGTexture2 = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons;
    this.bArmy = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 0.0F, 48.0F, 48.0F)));

    this.bReset = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bLoad = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSave = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bFly = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bNext = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bStat = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bDiff = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wSituation = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wMap = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wPlaneList = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wTarget = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wPos = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wDefence = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wAltitude = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wWeather = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wCldHeight = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wTimeHour = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wTimeMins = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wLevel = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wLevel.add("=");
    this.wLevel.add("+");
    this.wLevel.add("-");
    this.wLevel.setEditable(false);
    this.wLevel.setSelected(0, true, false);
    this.wLevel.posEnable = new boolean[] { true, true, true };

    this.wAltitude.add("100");
    this.wAltitude.add("150");
    this.wAltitude.add("200");
    this.wAltitude.add("250");
    this.wAltitude.add("300");
    this.wAltitude.add("400");
    this.wAltitude.add("500");
    this.wAltitude.add("750");
    this.wAltitude.add("1000");
    this.wAltitude.add("1500");
    this.wAltitude.add("2000");
    this.wAltitude.add("3000");
    this.wAltitude.add("5000");
    this.wAltitude.add("7500");
    this.wAltitude.add("10000");
    this.wAltitude.setEditable(true);
    this.wAltitude.setNumericOnly(true);
    this.wAltitude.setSelected(8, true, false);

    this.wPos.add("0");
    this.wPos.add("500");
    this.wPos.add("1000");
    this.wPos.add("2000");
    this.wPos.add("3000");
    this.wPos.setEditable(true);
    this.wPos.setNumericOnly(true);
    this.wPos.setSelected(0, true, false);

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
    this.wTimeHour.setSelected(12, true, false);
    this.wTimeMins.add("00");
    this.wTimeMins.add("15");
    this.wTimeMins.add("30");
    this.wTimeMins.add("45");
    this.wTimeMins.setEditable(false);
    this.wTimeMins.setSelected(0, true, false);
    this.wWeather.add(Localize("quick.CLE"));
    this.wWeather.add(Localize("quick.GOO"));
    this.wWeather.add(Localize("quick.HAZ"));
    this.wWeather.add(Localize("quick.POO"));
    this.wWeather.add(Localize("quick.BLI"));
    this.wWeather.add(Localize("quick.RAI"));
    this.wWeather.add(Localize("quick.THU"));
    this.wWeather.setEditable(false);
    this.wWeather.setSelected(0, true, false);
    this.wCldHeight.add("500");
    this.wCldHeight.add("750");
    this.wCldHeight.add("1000");
    this.wCldHeight.add("1250");
    this.wCldHeight.add("1500");
    this.wCldHeight.add("1750");
    this.wCldHeight.add("2000");
    this.wCldHeight.add("2250");
    this.wCldHeight.add("2500");
    this.wCldHeight.add("2750");
    this.wCldHeight.add("3000");
    this.wCldHeight.setEditable(true);
    this.wCldHeight.setSelected(6, true, false);
    this.wCldHeight.setNumericOnly(true);

    this.wPlaneList.add(Localize("quick.STD"));
    this.wPlaneList.add(Localize("quick.ABC"));
    localFile = new File("Missions/Quick/QMBair_2.ini");
    if ((localFile.exists()) && (checkCustomAirIni("Missions/Quick/QMBair_2.ini")))
      this.wPlaneList.add(Localize("quick.CUS1"));
    localFile = new File("Missions/Quick/QMBair_3.ini");
    if ((localFile.exists()) && (checkCustomAirIni("Missions/Quick/QMBair_3.ini")))
      this.wPlaneList.add(Localize("quick.CUS2"));
    this.wPlaneList.setEditable(false);
    this.wPlaneList.setSelected(getPlaneList(), true, false);

    this.wSituation.add(Localize("quick.NON"));
    this.wSituation.add(Localize("quick.ADV"));
    this.wSituation.add(Localize("quick.DIS"));
    this.wSituation.setEditable(false);
    this.wSituation.setSelected(0, true, false);
    boolean[] arrayOfBoolean = new boolean[this._targetKey.length];
    for (int j = 0; j < this._targetKey.length; j++) {
      this.wTarget.add(Localize("quick." + this._targetKey[j]));
      arrayOfBoolean[j] = true;
    }
    this.wTarget.setEditable(false);
    this.wTarget.setSelected(0, true, false);
    this.wTarget.posEnable = arrayOfBoolean;
    this.wDefence.add(Localize("quick.NOND"));
    this.wDefence.add(Localize("quick.AAA"));
    this.wDefence.setEditable(false);
    this.wDefence.setSelected(1, true, false);
    this.star = GTexture.New("GUI/Game/QM/star.mat");
    this.cross = GTexture.New("GUI/Game/QM/cross.mat");
    for (j = 0; j < 16; j++) {
      this.dlg[j] = new ItemDlg();
      this.dlg[j].wNum = ((WComboNum)this.dialogClient.addControl(new WComboNum(j, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[j].wSkill = ((WComboSkill)this.dialogClient.addControl(new WComboSkill(j, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[j].wPlane = ((WComboPlane)this.dialogClient.addControl(new WComboPlane(j, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[j].wLoadout = ((WComboLoadout)this.dialogClient.addControl(new WComboLoadout(j, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[j].bArming = ((WButtonArming)this.dialogClient.addControl(new WButtonArming(j, this.dialogClient, localGTexture2, 0.0F, 48.0F, 32.0F, 32.0F)));
      this.dlg[j].wNum.setEditable(false);
      int k;
      if (j == 0)
        for (k = 1; k < 5; k++)
          this.dlg[j].wNum.add("" + k);
      else {
        for (k = 0; k < 5; k++)
          this.dlg[j].wNum.add("" + k);
      }
      this.dlg[j].wNum.setSelected(0, true, false);
      this.dlg[j].wSkill.setEditable(false);
      this.dlg[j].wSkill.add(Localize("quick.ROO"));
      this.dlg[j].wSkill.add(Localize("quick.EXP"));
      this.dlg[j].wSkill.add(Localize("quick.VET"));
      this.dlg[j].wSkill.add(Localize("quick.ACE"));
      this.dlg[j].wSkill.setSelected(1, true, false);
      this.dlg[j].wPlane.setEditable(false);
      this.dlg[j].wPlane.listVisibleLines = 16;
      fillComboPlane(this.dlg[j].wPlane, j == 0);
      this.dlg[j].wLoadout.setEditable(false);
      fillComboWeapon(this.dlg[j].wLoadout, this.wing[j].plane, 0);
    }

    fillMapKey();
    this.wMap.setSelected(0, true, false);
    onArmyChange();

    this.wMap.setEditable(false);
    mapChanged();

    this.dialogClient.activateWindow();
    showHide();
    this.client.hideWindow();
  }

  private void dumpFullPlaneList() {
    SectFile localSectFile1 = new SectFile("com/maddox/il2/objects/air.ini");
    SectFile localSectFile2 = new SectFile("./Missions/Quick/FullPlaneList.dump", 1);
    localSectFile2.sectionAdd("AIR");
    int i = localSectFile1.vars(localSectFile1.sectionIndex("AIR"));
    for (int j = 0; j < i; j++)
      localSectFile2.varAdd(0, localSectFile1.var(0, j));
  }

  private void fillMapKey() {
    DirFilter localDirFilter = new DirFilter();
    this.folderNames = new File("Missions/Quick/").list(localDirFilter);
    if (this.folderNames != null) {
      this._mapKey = new String[this.folderNames.length];
      for (int i = 0; i < this._mapKey.length; i++)
        this._mapKey[i] = this.folderNames[i];
    }
    else {
      this._mapKey = new String[1];
      this._mapKey[0] = Localize("quick.NOMISSIONS");
      this.bNoAvailableMissions = true;
    }
    resetwMap();
  }

  private ArrayList getAvailableMissionsS(String paramString1, String paramString2) {
    File localFile = new File("Missions/Quick/" + paramString1);
    String[] arrayOfString = localFile.list();
    ArrayList localArrayList = new ArrayList();
    localArrayList.clear();
    if (arrayOfString == null)
      return localArrayList;
    for (int i = 0; i < arrayOfString.length; i++) {
      if ((arrayOfString[i].startsWith(paramString2)) && (arrayOfString[i].endsWith(".mis"))) {
        localArrayList.add(arrayOfString[i]);
      }
    }
    return localArrayList;
  }

  private String getArmyString() {
    if (this.OUR) {
      return "Red";
    }
    return "Blue";
  }

  private void resetwMap() {
    this.wMap.clear();
    if (this.bNoAvailableMissions) {
      this.wMap.add(Localize("quick.NOMISSIONS"));
      return;
    }
    for (int i = 0; i < this._mapKey.length; i++)
      if (getAvailableMissionsS(this._mapKey[i], this._mapKey[i] + getArmyString()).size() > 0) {
        String str1 = this._mapKey[i];
        String str2 = I18N.map(str1);
        if (!str1.equals(str2))
          this.wMap.add(str2);
        else
          this.wMap.add(I18N.map(this._mapKey[i]));
      }
  }

  private String getMapName()
  {
    String str1 = this.wMap.getValue();
    for (int i = 0; i < this._mapKey.length; i++) {
      String str2 = this._mapKey[i];
      String str3 = I18N.map(str2);
      if (str3.equals(str1)) {
        return str2;
      }
    }
    return str1;
  }

  private void onArmyChange() {
    String str1 = getMapName();
    String str2 = this.wMap.getValue();
    resetwMap();
    int i = this.wMap.list.indexOf(str2);
    if (getAvailableMissionsS(str1, str1 + getArmyString()).size() > 0)
      this.wMap.setSelected(i, true, false);
    else {
      this.wMap.setSelected(0, true, false);
    }
    onTargetChange();
  }

  private void onTargetChange() {
    int i = this.wTarget.getSelected();
    int j = 0;
    for (int k = this.wTarget.size() - 1; k > -1; k--) {
      if (getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + this._targetKey[k]).size() > 0) {
        this.wTarget.posEnable[k] = true;
        j = k;
      } else {
        this.wTarget.posEnable[k] = false;
      }
    }
    if (this.wTarget.posEnable[i] != 0)
      this.wTarget.setSelected(i, true, false);
    else {
      this.wTarget.setSelected(j, true, false);
    }
    checkNoneTarget();
  }

  private void checkNoneTarget() {
    int i = 0;
    int j = 0;
    if (this.wTarget.getSelected() == 0) {
      this.noneTarget = true;
      i = this.wLevel.getSelected();
      for (int k = this.wLevel.size() - 1; k > -1; k--) {
        if (getAvailableMissionsS(getMapName(), getMapName() + getArmyString() + this._targetKey[0] + this.noneTargetSuffix[k]).size() > 0) {
          this.wLevel.posEnable[k] = true;
          j = k;
        } else {
          this.wLevel.posEnable[k] = false;
        }
      }
      if (this.wLevel.posEnable[i] != 0)
        this.wLevel.setSelected(i, true, false);
      else {
        this.wLevel.setSelected(j, true, false);
      }
      this.wLevel.showWindow();
    } else {
      this.noneTarget = false;
      this.wLevel.hideWindow();
    }
  }

  private void showHide()
  {
    for (int i = 0; i < 4; i++) {
      setShow(this.bFirst, this.dlg[i].wNum);
      setShow(this.bFirst, this.dlg[i].wSkill);
      setShow(this.bFirst, this.dlg[i].wPlane);
      setShow(this.bFirst, this.dlg[i].wLoadout);
      setShow(this.bFirst, this.dlg[i].bArming);
      setShow(this.bFirst, this.dlg[(i + 8)].wNum);
      setShow(this.bFirst, this.dlg[(i + 8)].wSkill);
      setShow(this.bFirst, this.dlg[(i + 8)].wPlane);
      setShow(this.bFirst, this.dlg[(i + 8)].wLoadout);
      setShow(this.bFirst, this.dlg[(i + 8)].bArming);
    }
    for (i = 4; i < 8; i++) {
      setShow(!this.bFirst, this.dlg[i].wNum);
      setShow(!this.bFirst, this.dlg[i].wSkill);
      setShow(!this.bFirst, this.dlg[i].wPlane);
      setShow(!this.bFirst, this.dlg[i].wLoadout);
      setShow(!this.bFirst, this.dlg[i].bArming);
      setShow(!this.bFirst, this.dlg[(i + 8)].wNum);
      setShow(!this.bFirst, this.dlg[(i + 8)].wSkill);
      setShow(!this.bFirst, this.dlg[(i + 8)].wPlane);
      setShow(!this.bFirst, this.dlg[(i + 8)].wLoadout);
      setShow(!this.bFirst, this.dlg[(i + 8)].bArming);
    }
    setShow(this.bFirst, this.wPlaneList);
    setShow(this.bFirst, this.wTarget);
    setShow(this.bFirst, this.wMap);
    setShow(this.bFirst, this.bNext);
    setShow(this.bFirst, this.bExit);
    setShow(this.bFirst, this.bArmy);
    setShow(this.bFirst, this.wAltitude);
    setShow(this.bFirst, this.wDefence);
    setShow(this.bFirst, this.wSituation);
    setShow(this.bFirst, this.wPos);
    setShow(this.bFirst, this.bReset);
    setShow((this.bFirst) && (this.noneTarget), this.wLevel);

    setShow(!this.bFirst, this.bBack);
    setShow(!this.bFirst, this.bStat);
    setShow(!this.bFirst, this.wWeather);
    setShow(!this.bFirst, this.wTimeHour);
    setShow(!this.bFirst, this.wTimeMins);
    setShow(!this.bFirst, this.wCldHeight);
    setShow(!this.bNoAvailableMissions, this.bFly);

    this.dialogClient.doResolutionChanged();
    this.dialogClient.setPosSize();
  }

  private void setShow(boolean paramBoolean, GWindow paramGWindow) {
    if (paramBoolean)
      paramGWindow.showWindow();
    else
      paramGWindow.hideWindow();
  }

  private void setDefaultValues() {
    this.wAltitude.setSelected(8, true, false);
    this.wPos.setSelected(0, true, false);
    this.wTimeHour.setSelected(12, true, false);
    this.wTimeMins.setSelected(0, true, false);
    this.wWeather.setSelected(0, true, false);
    this.wCldHeight.setSelected(6, true, false);
    this.wDefence.setSelected(1, true, false);
    this.wSituation.setSelected(0, true, false);
    for (int i = 0; i < 16; i++) {
      this.dlg[i].wNum.setSelected(0, true, false);
      this.dlg[i].wSkill.setSelected(1, true, false);
      this.dlg[i].wPlane.setSelected(0, true, false);
      this.dlg[i].wLoadout.setSelected(0, true, false);
      this.dlg[i].wNum.notify(2, 0);
      this.dlg[i].wSkill.notify(2, 0);
      this.dlg[i].wPlane.notify(2, 0);
      this.dlg[i].wLoadout.notify(2, 0);
    }
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    ObjIO.fields(class$com$maddox$il2$gui$GUIQuick$IOState, new String[] { "our", "situation", "map", "target", "defence", "altitude", "weather", "timeH", "timeM", "pos", "cldheight", "scramble", "noneTARGET" });

    ObjIO.validate(IOState.class, "loaded");
    ObjIO.fields(class$com$maddox$il2$gui$GUIQuick$ItemWing, new String[] { "planes", "weapon", "regiment", "skin", "noseart", "pilot", "numberOn", "fuel", "skill" });

    ObjIO.accessStr(ItemWing.class, "plane", "getPlane", "setPlane");
    ObjIO.validate(ItemWing.class, "loaded");
  }

  public static class DirFilter
    implements FilenameFilter
  {
    public boolean accept(File paramFile, String paramString)
    {
      File localFile = new File(paramFile, paramString);
      return localFile.isDirectory();
    }
  }

  public class IOState
  {
    public boolean our;
    public boolean scramble;
    public int situation;
    public String map;
    public int target;
    public int defence;
    public String altitude;
    public String pos;
    public int weather;
    public String cldheight;
    public int timeH;
    public int timeM;
    public int noneTARGET;

    public IOState()
    {
    }

    public void getMap()
    {
      String[] arrayOfString = { "Okinawa", "CoralSeaOnline", "Net8Islands", "Smolensk", "Moscow", "Crimea", "Kuban", "Bessarabia", "MTO", "Slovakia_winter", "Slovakia_summer" };

      int i = Integer.parseInt(this.map);
      String str = arrayOfString[i];
      this.map = str;
      GUIQuick.this.wMap.setValue(I18N.map(str));
      GUIQuick.this.mapChanged();
    }

    public void save()
    {
      this.our = GUIQuick.this.OUR;
      this.scramble = GUIQuick.this.bScramble;
      this.situation = GUIQuick.this.wSituation.getSelected();
      this.map = GUIQuick.this.getMapName();
      this.target = GUIQuick.this.wTarget.getSelected();
      this.defence = GUIQuick.this.wDefence.getSelected();
      this.altitude = GUIQuick.this.wAltitude.getValue();
      this.pos = GUIQuick.this.wPos.getValue();
      this.weather = GUIQuick.this.wWeather.getSelected();
      this.cldheight = GUIQuick.this.wCldHeight.getValue();
      this.timeH = GUIQuick.this.wTimeHour.getSelected();
      this.timeM = GUIQuick.this.wTimeMins.getSelected();
      this.noneTARGET = GUIQuick.this.wLevel.getSelected();
    }

    public void loaded() {
      GUIQuick.this.OUR = this.our;
      GUIQuick.this.wMap.setValue(I18N.map(this.map));

      GUIQuick.this.mapChanged();

      GUIQuick.this.wSituation.setSelected(this.situation, true, false);
      GUIQuick.this.wTarget.setSelected(this.target, true, false);
      GUIQuick.this.wDefence.setSelected(this.defence, true, false);

      GUIQuick.this.bScramble = this.scramble;
      GUIQuick.this.wPos.setValue(this.pos);
      GUIQuick.this.validateEditableComboControl(GUIQuick.this.wPos);

      GUIQuick.this.wAltitude.setValue(this.altitude);
      int i = Integer.parseInt(GUIQuick.this.wAltitude.getValue());
      if (i < 100) {
        if (this.situation != 0)
          this.pos = "700";
        else {
          this.pos = "0";
        }
        this.cldheight = "2000";
        this.altitude = GUIQuick.this.wAltitude.get(i);
        GUIQuick.this.wAltitude.setValue(this.altitude);
        this.scramble = false;
      }
      GUIQuick.this.validateEditableComboControl(GUIQuick.this.wAltitude);
      GUIQuick.this.wCldHeight.setValue(this.cldheight);
      GUIQuick.this.validateEditableComboControl(GUIQuick.this.wCldHeight);

      GUIQuick.this.wWeather.setSelected(this.weather, true, false);
      GUIQuick.this.wTimeHour.setSelected(this.timeH, true, false);
      GUIQuick.this.wTimeMins.setSelected(this.timeM, true, false);
      GUIQuick.this.wLevel.setSelected(this.noneTARGET, true, false);
      if (this.target == 0)
        GUIQuick.access$602(GUIQuick.this, true);
      else
        GUIQuick.access$602(GUIQuick.this, false);
    }
  }

  class WComboNum extends GWindowComboControl
  {
    private int indx;

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = getSelected();
        if (i < 0) {
          return true;
        }
        GUIQuick.this.wing[this.indx].planes = (this.indx != 0 ? i : i + 1);
        return true;
      }

      return super.notify(paramInt1, paramInt2);
    }

    public WComboNum(int paramGWindow, GWindow paramFloat1, float paramFloat2, float paramFloat3, float arg6)
    {
      super(paramFloat2, paramFloat3, localObject);
      this.indx = paramGWindow;
    }
  }

  class WComboSkill extends GWindowComboControl
  {
    private int indx;

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = getSelected();
        if (i < 0) {
          return true;
        }
        GUIQuick.this.wing[this.indx].skill = i;
        return true;
      }

      return super.notify(paramInt1, paramInt2);
    }

    public WComboSkill(int paramGWindow, GWindow paramFloat1, float paramFloat2, float paramFloat3, float arg6)
    {
      super(paramFloat2, paramFloat3, localObject);
      this.indx = paramGWindow;
    }
  }

  class WComboPlane extends GWindowComboControl
  {
    private int indx;

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = getSelected();
        if (i < 0)
          return true;
        GUIQuick.ItemAir localItemAir;
        if (this.indx == 0)
          localItemAir = (GUIQuick.ItemAir)(GUIQuick.this.bPlaneArrestor ? GUIQuick.this.playerPlaneC : GUIQuick.this.playerPlane).get(i);
        else
          localItemAir = (GUIQuick.ItemAir)(GUIQuick.this.bPlaneArrestor ? GUIQuick.this.aiPlaneC : GUIQuick.this.aiPlane).get(i);
        String str = GUIQuick.this.fillComboWeapon(GUIQuick.this.dlg[this.indx].wLoadout, localItemAir, 0);
        GUIQuick.this.wing[this.indx].setPlane(i);
        int j = 0;
        if (this.indx == 0) {
          GUIQuick.this.wing[this.indx].fromUserCfg();
          String[] arrayOfString = Aircraft.getWeaponsRegistered(GUIQuick.this.wing[this.indx].plane.clazz);
          for (int k = 0; k < arrayOfString.length; k++) {
            if (!arrayOfString[k].equals(GUIQuick.this.wing[this.indx].weapon))
              continue;
            GUIQuick.this.fillComboWeapon(GUIQuick.this.dlg[this.indx].wLoadout, GUIQuick.this.wing[this.indx].plane, k);
            j = 1;
            break;
          }
        }
        if (j == 0)
          GUIQuick.this.wing[this.indx].weapon = str;
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }

    public WComboPlane(int paramGWindow, GWindow paramFloat1, float paramFloat2, float paramFloat3, float arg6)
    {
      super(paramFloat2, paramFloat3, localObject);
      this.indx = paramGWindow;
    }
  }

  class WComboLoadout extends GWindowComboControl
  {
    private int indx;

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        int i = getSelected();
        if (i < 0)
          return true;
        GUIQuick.this.wing[this.indx].setWeapon(i);
        if (this.indx == 0)
          GUIQuick.this.wing[this.indx].toUserCfg();
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }

    public WComboLoadout(int paramGWindow, GWindow paramFloat1, float paramFloat2, float paramFloat3, float arg6)
    {
      super(paramFloat2, paramFloat3, localObject);
      this.indx = paramGWindow;
    }
  }

  class WButtonArming extends GUIButton
  {
    private int indx;

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        if (GUIQuick.this.wing[this.indx].planes == 0) {
          return true;
        }
        GUIQuick.access$1902(GUIQuick.this, this.indx);
        GUIQuick.this.wing[this.indx].toAirArming();
        GUIAirArming.stateId = 4;
        Main.stateStack().push(55);
        return true;
      }

      return super.notify(paramInt1, paramInt2);
    }

    public WButtonArming(int paramGWindow, GWindow paramGTexture, GTexture paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float arg8)
    {
      super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, localObject);
      this.indx = paramGWindow;
    }
  }

  static class ItemDlg
  {
    public GUIQuick.WComboNum wNum;
    public GUIQuick.WComboSkill wSkill;
    public GUIQuick.WComboPlane wPlane;
    public GUIQuick.WComboLoadout wLoadout;
    public GUIQuick.WButtonArming bArming;
  }

  public class ItemWing
  {
    public int indx = 0;
    public int planes = 0;
    public GUIQuick.ItemAir plane = null;
    public String weapon = "default";
    public String regiment = null;
    public int iwing = 0;

    public String[] skin = { null, null, null, null };

    public String[] noseart = { null, null, null, null };

    public String[] pilot = { null, null, null, null };

    public boolean[] numberOn = { true, true, true, true };

    public int fuel = 100;
    public int skill = 1;

    public ItemWing(int arg2)
    {
      int i;
      if (i == 0)
        this.planes = 1;
      this.indx = i;
      this.iwing = (i % 4);
      if (this.indx == 0)
        this.plane = ((GUIQuick.ItemAir)(GUIQuick.this.bPlaneArrestor ? GUIQuick.this.playerPlaneC : GUIQuick.this.playerPlane).get(0));
      else
        this.plane = ((GUIQuick.ItemAir)(GUIQuick.this.bPlaneArrestor ? GUIQuick.this.aiPlaneC : GUIQuick.this.aiPlane).get(0));
      if (this.indx <= 7)
        this.regiment = GUIQuick.this.r01;
      else
        this.regiment = GUIQuick.this.g01;
    }

    public String getPlane() {
      return this.plane.name;
    }

    public void setPlane(String paramString) {
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int i = 0; i < localArrayList.size(); i++) {
        this.plane = ((GUIQuick.ItemAir)localArrayList.get(i));
        if (this.plane.name.equals(paramString))
          break;
      }
    }

    public void loaded() {
      GUIQuick.this.dlg[this.indx].wNum.setSelected(this.indx != 0 ? this.planes : this.planes - 1, true, false);
      GUIQuick.this.dlg[this.indx].wSkill.setSelected(this.skill, true, false);
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int i = 0; i < localArrayList.size(); i++) {
        if (this.plane != localArrayList.get(i))
          continue;
        GUIQuick.this.dlg[this.indx].wPlane.setSelected(i, true, false);
        break;
      }
      String[] arrayOfString = Aircraft.getWeaponsRegistered(this.plane.clazz);
      for (int j = 0; j < arrayOfString.length; j++) {
        if (!arrayOfString[j].equals(this.weapon))
          continue;
        GUIQuick.this.fillComboWeapon(GUIQuick.this.dlg[this.indx].wLoadout, this.plane, j);
        break;
      }
      if (this.indx == 0)
        toUserCfg();
    }

    public void toUserCfg() {
      if (this.indx != 0) {
        return;
      }
      UserCfg localUserCfg = World.cur().userCfg;
      localUserCfg.setSkin(this.plane.name, this.skin[0]);
      localUserCfg.setNoseart(this.plane.name, this.noseart[0]);
      localUserCfg.netPilot = this.pilot[0];
      localUserCfg.setWeapon(this.plane.name, this.weapon);
      localUserCfg.netNumberOn = this.numberOn[0];
    }

    public void fromUserCfg()
    {
      if (this.indx != 0) {
        return;
      }
      UserCfg localUserCfg = World.cur().userCfg;
      this.skin[0] = localUserCfg.getSkin(this.plane.name);
      this.noseart[0] = localUserCfg.getNoseart(this.plane.name);
      this.pilot[0] = localUserCfg.netPilot;
      this.weapon = localUserCfg.getWeapon(this.plane.name);
      this.numberOn[0] = localUserCfg.netNumberOn;
    }

    public String prepareWing(SectFile paramSectFile)
    {
      String str1;
      if (this.indx < 8) {
        str1 = GUIQuick.this.OUR ? GUIQuick.this.r01 : GUIQuick.this.g01;
        if (this.indx < 4)
          str1 = str1 + "0" + this.iwing;
        else
          str1 = str1 + "1" + this.iwing;
      } else {
        str1 = GUIQuick.this.OUR ? GUIQuick.this.g01 : GUIQuick.this.r01;
        if (this.indx < 12)
          str1 = str1 + "0" + this.iwing;
        else
          str1 = str1 + "1" + this.iwing;
      }
      int i = paramSectFile.sectionIndex("Wing");

      if (this.planes == 0) {
        paramSectFile.lineRemove(i, paramSectFile.varIndex(i, str1));
        paramSectFile.sectionRemove(paramSectFile.sectionIndex(str1));
        paramSectFile.sectionRemove(paramSectFile.sectionIndex(str1 + "_Way"));
        return null;
      }
      String str2 = null;
      int j;
      if (this.regiment != null) {
        if (((this.indx > 3) && (this.indx < 8)) || (this.indx > 11))
          str2 = this.regiment + "1" + this.iwing;
        else {
          str2 = this.regiment + "0" + this.iwing;
        }

        paramSectFile.lineRemove(i, paramSectFile.varIndex(i, str1));
        paramSectFile.lineAdd(i, str2);
        paramSectFile.sectionRename(paramSectFile.sectionIndex(str1 + "_Way"), str2 + "_Way");
        paramSectFile.sectionRename(paramSectFile.sectionIndex(str1), str2);
        j = paramSectFile.sectionIndex(str2);
      } else {
        j = paramSectFile.sectionIndex(str1);
        str2 = str1;
      }
      paramSectFile.sectionClear(j);
      paramSectFile.lineAdd(j, "Planes " + this.planes);
      paramSectFile.lineAdd(j, "Skill " + this.skill);
      paramSectFile.lineAdd(j, "Class " + this.plane.className);
      paramSectFile.lineAdd(j, "Fuel " + this.fuel);
      if (this.weapon != null)
        paramSectFile.lineAdd(j, "weapons " + this.weapon);
      else
        paramSectFile.lineAdd(j, "weapons default");
      for (int k = 0; k < this.planes; k++) {
        if (this.skin[k] != null)
          paramSectFile.lineAdd(j, "skin" + k + " " + this.skin[k]);
        if (this.noseart[k] != null)
          paramSectFile.lineAdd(j, "noseart" + k + " " + this.noseart[k]);
        if (this.pilot[k] != null)
          paramSectFile.lineAdd(j, "pilot" + k + " " + this.pilot[k]);
        if (this.numberOn[k] == 0)
          paramSectFile.lineAdd(j, "numberOn" + k + " 0");
      }
      return str2;
    }

    public void prepereWay(SectFile paramSectFile, String[] paramArrayOfString1, String[] paramArrayOfString2) {
      int i = paramSectFile.sectionIndex(paramArrayOfString2[this.indx] + "_Way");
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        SharedTokenizer.set(paramSectFile.line(i, k));
        String str1 = SharedTokenizer.next("");
        String str2 = str1 + " " + SharedTokenizer.next("") + " " + SharedTokenizer.next("") + " ";
        String str3 = GUIQuick.this.wAltitude.getValue();
        if (GUIQuick.this.wSituation.getSelected() != 0) {
          int m = 500;
          try {
            m = Integer.parseInt(GUIQuick.this.wAltitude.getValue()); } catch (Exception localException) {
          }
          if (GUIQuick.this.wSituation.getSelected() == 1) {
            if (this.indx <= 7)
              m += Integer.parseInt(GUIQuick.this.wPos.getValue());
          } else if (this.indx > 7)
            m += Integer.parseInt(GUIQuick.this.wPos.getValue());
          str3 = "" + m;
        }
        SharedTokenizer.next("");
        float f = (float)SharedTokenizer.next((this.plane.speedMin + this.plane.speedMax) / 2.0D, this.plane.speedMin, this.plane.speedMax);
        if (("TAKEOFF".equals(str1)) || ("LANDING".equals(str1))) {
          str3 = "0";
          f = 0.0F;
        }
        String str4 = SharedTokenizer.next((String)null);
        String str5 = SharedTokenizer.next((String)null);
        if (str4 != null) if ((GUIQuick.class$com$maddox$il2$objects$air$TypeTransport = GUIQuick._mthclass$("com.maddox.il2.objects.air.TypeTransport")).isAssignableFrom(this.plane.clazz))
            str4 = null;
        if (str4 != null) {
          for (int n = 0; n < 8; n++) {
            if (!str4.equals(paramArrayOfString1[n]))
              continue;
            str4 = paramArrayOfString2[n];
            break;
          }
        }
        if (str4 != null) {
          if (str5 != null)
            paramSectFile.line(i, k, str2 + str3 + " " + f + " " + str4 + " " + str5);
          else
            paramSectFile.line(i, k, str2 + str3 + " " + f + " " + str4);
        }
        else paramSectFile.line(i, k, str2 + str3 + " " + f);
      }
    }

    public void setPlane(int paramInt)
    {
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      this.plane = ((GUIQuick.ItemAir)localArrayList.get(paramInt));
      for (int i = 0; i < 4; i++) {
        this.skin[i] = null;
        this.noseart[i] = null;
      }
    }

    public void setWeapon(int paramInt) {
      String[] arrayOfString = Aircraft.getWeaponsRegistered(this.plane.clazz);
      this.weapon = arrayOfString[paramInt];
    }

    public void toAirArming() {
      GUIAirArming localGUIAirArming = (GUIAirArming)GameState.get(55);
      localGUIAirArming.quikPlayer = (this.indx == 0);
      if (this.indx < 8)
        localGUIAirArming.quikArmy = (GUIQuick.this.OUR ? 1 : 2);
      else
        localGUIAirArming.quikArmy = (GUIQuick.this.OUR ? 2 : 1);
      localGUIAirArming.quikPlanes = this.planes;
      localGUIAirArming.quikPlane = this.plane.name;
      localGUIAirArming.quikWeapon = this.weapon;
      localGUIAirArming.quikCurPlane = 0;
      localGUIAirArming.quikRegiment = this.regiment;
      localGUIAirArming.quikWing = this.iwing;
      localGUIAirArming.quikFuel = this.fuel;
      for (int i = 0; i < 4; i++) {
        localGUIAirArming.quikSkin[i] = this.skin[i];
        localGUIAirArming.quikNoseart[i] = this.noseart[i];
        localGUIAirArming.quikPilot[i] = this.pilot[i];
        localGUIAirArming.quikNumberOn[i] = this.numberOn[i];
      }

      localGUIAirArming.quikListPlane.clear();
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int j = 0; j < localArrayList.size(); j++)
        localGUIAirArming.quikListPlane.add(((GUIQuick.ItemAir)localArrayList.get(j)).clazz);
    }

    public void fromAirArming() {
      GUIAirArming localGUIAirArming = (GUIAirArming)GameState.get(55);
      GUIQuick.ItemAir localItemAir = null;
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int i = 0; i < localArrayList.size(); i++) {
        localItemAir = (GUIQuick.ItemAir)localArrayList.get(i);
        if (localItemAir.name.equals(localGUIAirArming.quikPlane)) {
          break;
        }
      }
      this.plane = localItemAir;
      this.weapon = localGUIAirArming.quikWeapon;
      this.regiment = localGUIAirArming.quikRegiment;
      this.fuel = localGUIAirArming.quikFuel;
      for (i = 0; i < 4; i++) {
        this.skin[i] = localGUIAirArming.quikSkin[i];
        this.noseart[i] = localGUIAirArming.quikNoseart[i];
        this.pilot[i] = localGUIAirArming.quikPilot[i];
        this.numberOn[i] = localGUIAirArming.quikNumberOn[i];
      }
      loaded();
    }
  }

  static class ItemAir
  {
    public String name;
    public String className;
    public Class clazz;
    public boolean bEnablePlayer;
    public double speedMin;
    public double speedMax;

    public ItemAir(String paramString1, Class paramClass, String paramString2)
    {
      this.speedMin = 200.0D;
      this.speedMax = 500.0D;
      this.name = paramString1;
      this.clazz = paramClass;
      this.className = paramString2;
      this.bEnablePlayer = Property.containsValue(paramClass, "cockpitClass");
      String str = Property.stringValue(paramClass, "FlightModel", null);
      if (str != null) {
        SectFile localSectFile = FlightModelMain.sectFile(str);
        this.speedMin = localSectFile.get("Params", "Vmin", (float)this.speedMin);
        this.speedMax = localSectFile.get("Params", "VmaxH", (float)this.speedMax);
      }
    }
  }

  public class DialogClient extends GUIDialogClient
  {
    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      GUIQuick.this.validateEditableComboControl(GUIQuick.this.wCldHeight);
      GUIQuick.this.validateEditableComboControl(GUIQuick.this.wPos);
      GUIQuick.this.validateEditableComboControl(GUIQuick.this.wAltitude);
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);
      if (paramGWindow == GUIQuick.this.bArmy) {
        if (GUIQuick.this.OUR)
          GUIQuick.this.OUR = false;
        else
          GUIQuick.this.OUR = true;
        GUIQuick.this.onArmyChange();
        if (GUIQuick.this.r01.equals("usa01"))
          Main3D.menuMusicPlay(GUIQuick.this.OUR ? "us" : "ja");
        else
          Main3D.menuMusicPlay(GUIQuick.this.OUR ? "ru" : "de");
        for (int i = 0; i < 16; i++)
          if (i <= 7)
            GUIQuick.this.wing[i].regiment = (GUIQuick.this.OUR ? GUIQuick.this.r01 : GUIQuick.this.g01);
          else
            GUIQuick.this.wing[i].regiment = (GUIQuick.this.OUR ? GUIQuick.this.g01 : GUIQuick.this.r01);
        return true;
      }

      if (paramGWindow == GUIQuick.this.wTarget) {
        if (GUIQuick.this.wTarget.getSelected() == 4)
          GUIQuick.this.bScramble = true;
        else {
          GUIQuick.this.bScramble = false;
        }
        if (GUIQuick.this.wTarget.getSelected() == 0) {
          GUIQuick.access$602(GUIQuick.this, true);
          GUIQuick.this.wLevel.showWindow();
        } else {
          GUIQuick.access$602(GUIQuick.this, false);
          GUIQuick.this.wLevel.hideWindow();
        }
        return true;
      }

      if (paramGWindow == GUIQuick.this.bExit) {
        GUIQuick.setQMB(false);
        GUIQuick.this.save(true);
        Main.stateStack().pop();
        return true;
      }

      if (paramGWindow == GUIQuick.this.bDiff) {
        Main.stateStack().push(17);
        return true;
      }

      if (paramGWindow == GUIQuick.this.bFly) {
        if (GUIQuick.this.bNoAvailableMissions) {
          return true;
        }
        GUIQuick.this.validateEditableComboControl(GUIQuick.this.wCldHeight);
        GUIQuick.this.validateEditableComboControl(GUIQuick.this.wPos);
        GUIQuick.this.validateEditableComboControl(GUIQuick.this.wAltitude);
        GUIQuick.this.startQuickMission();
        return true;
      }

      if (paramGWindow == GUIQuick.this.bReset) {
        GUIQuick.this.setDefaultValues();
        return true;
      }

      if (paramGWindow == GUIQuick.this.bStat) {
        Main.stateStack().push(71);
        return true;
      }

      if (paramGWindow == GUIQuick.this.bNext) {
        GUIQuick.access$902(GUIQuick.this, false);
        GUIQuick.this.showHide();
        return true;
      }

      if (paramGWindow == GUIQuick.this.bBack) {
        GUIQuick.access$902(GUIQuick.this, true);
        GUIQuick.this.showHide();
        return true;
      }

      if (paramGWindow == GUIQuick.this.bLoad) {
        GUIQuick.this.ssect = null;
        Main.stateStack().push(25);
        return true;
      }

      if (paramGWindow == GUIQuick.this.bSave) {
        GUIQuick.this.validateEditableComboControl(GUIQuick.this.wCldHeight);
        GUIQuick.this.validateEditableComboControl(GUIQuick.this.wPos);
        GUIQuick.this.validateEditableComboControl(GUIQuick.this.wAltitude);
        GUIQuick.this.save(false);
        Main.stateStack().push(24);
        return true;
      }

      if (paramGWindow == GUIQuick.this.wPlaneList) {
        IniFile localIniFile = Config.cur.ini;
        localIniFile.set("QMB", "PlaneList", GUIQuick.this.wPlaneList.getSelected());
        GUIQuick.access$1100(GUIQuick.this.wPlaneList.getSelected());
        GUIQuick.this.fillArrayPlanes();
        for (int j = 0; j < 16; j++) {
          GUIQuick.this.fillComboPlane(GUIQuick.this.dlg[j].wPlane, j == 0);
          int k = GUIQuick.this.dlg[j].wPlane.getSelected();
          if (k == 0)
            GUIQuick.this.dlg[j].wPlane.setSelected(1, false, false);
          GUIQuick.this.dlg[j].wPlane.setSelected(0, true, true);
        }
        return true;
      }

      if (paramGWindow == GUIQuick.this.wMap) {
        GUIQuick.this.onArmyChange();
        GUIQuick.this.mapChanged();

        if (Main.cur() != null) {
          Main.cur();
          if (Main.stateStack() != null) {
            Main.cur();
            if (Main.stateStack().peek() != null) {
              Main.cur();
              if (Main.stateStack().peek().id() == 14)
                if (GUIQuick.this.r01.equals("usa01"))
                  Main3D.menuMusicPlay(GUIQuick.this.OUR ? "us" : "ja");
                else
                  Main3D.menuMusicPlay(GUIQuick.this.OUR ? "ru" : "de");
            }
          }
        }
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColorWHITE();

      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(630.0F), x1024(924.0F), 2.5F);
      GUISeparate.draw(this, GColor.Gray, x1024(150.0F), y1024(640.0F), 1.0F, x1024(80.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(720.0F), y1024(640.0F), 1.0F, x1024(80.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(875.0F), y1024(640.0F), 1.0F, x1024(80.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(457.0F), y1024(640.0F), 1.0F, x1024(46.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(567.0F), y1024(640.0F), 1.0F, x1024(46.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(457.0F), y1024(686.0F), x1024(30.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(537.0F), y1024(686.0F), x1024(30.0F), 2.0F);

      setCanvasFont(0);
      draw(x1024(0.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.BAC"));
      draw(x1024(143.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.LOD"));
      draw(x1024(285.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.SAV"));
      if (!GUIQuick.this.bNoAvailableMissions)
        draw(x1024(427.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.FLY"));
      draw(x1024(569.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.DIF"));
      if (GUIQuick.this.bFirst)
      {
        GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(500.0F), x1024(924.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(285.0F), x1024(924.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(864.0F), y1024(130.0F), x1024(94.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(864.0F), y1024(317.0F), x1024(94.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(958.0F), y1024(110.0F), 2.0F, x1024(56.0F));
        GUISeparate.draw(this, GColor.Gray, x1024(958.0F), y1024(317.0F), 2.0F, x1024(28.0F));

        draw(x1024(710.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.RES"));
        draw(x1024(853.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.GFC"));
        setCanvasFont(1);
        draw(x1024(38.0F), y1024(13.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.YOU"));
        draw(x1024(38.0F), y1024(108.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.FRI"));
        draw(x1024(38.0F), y1024(291.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.ENM"));
        setCanvasFont(0);
        draw(x1024(48.0F), y1024(38.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
        draw(x1024(144.0F), y1024(38.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
        draw(x1024(318.0F), y1024(38.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
        draw(x1024(606.0F), y1024(38.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

        draw(x1024(48.0F), y1024(143.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
        draw(x1024(144.0F), y1024(143.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
        draw(x1024(318.0F), y1024(143.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
        draw(x1024(606.0F), y1024(143.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

        draw(x1024(48.0F), y1024(320.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
        draw(x1024(144.0F), y1024(320.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
        draw(x1024(318.0F), y1024(320.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
        draw(x1024(606.0F), y1024(320.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

        draw(x1024(606.0F), y1024(508.0F), x1024(192.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.MAP"));
        draw(x1024(606.0F), y1024(542.0F), x1024(192.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLALST"));

        draw(x1024(318.0F), y1024(508.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.ALT"));
        draw(x1024(318.0F), y1024(542.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SIT"));
        draw(x1024(318.0F), y1024(576.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.POS"));

        draw(x1024(48.0F), y1024(508.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TAR"));
        draw(x1024(48.0F), y1024(576.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.DEF"));

        if (GUIQuick.this.noneTarget) {
          draw(x1024(48.0F), y1024(542.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.+/-"));
        }
        draw(x1024(320.0F), y1024(118.0F), x1024(528.0F), y1024(32.0F), 2, GUIQuick.this.Localize("quick.ASET"));
        draw(x1024(320.0F), y1024(298.0F), x1024(528.0F), y1024(32.0F), 2, GUIQuick.this.Localize("quick.ASET"));

        setCanvasFont(0);
        if (GUIQuick.this.OUR)
          draw(x1024(566.0F), y1024(21.0F), x1024(362.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.SEL_Allies"));
        else
          draw(x1024(566.0F), y1024(21.0F), x1024(362.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.SEL_Axis"));
      } else {
        GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(500.0F), x1024(924.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(215.0F), x1024(924.0F), 2.0F);

        GUISeparate.draw(this, GColor.Gray, x1024(864.0F), y1024(288.0F), x1024(94.0F), 2.0F);
        GUISeparate.draw(this, GColor.Gray, x1024(958.0F), y1024(232.0F), 2.0F, x1024(112.0F));

        setCanvasFont(0);
        draw(x1024(318.0F), y1024(508.0F), x1024(120.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.WEA"));
        draw(x1024(606.0F), y1024(508.0F), x1024(120.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.CLD"));

        draw(x1024(48.0F), y1024(508.0F), x1024(100.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TIM"));
        draw(x1024(213.0F), y1024(508.0F), x1024(8.0F), M(2.0F), 1, ":");

        draw(x1024(853.0F), y1024(633.0F), x1024(170.0F), M(2.0F), 1, GUIQuick.this.Localize("quick.STAT"));

        draw(x1024(48.0F), y1024(37.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
        draw(x1024(144.0F), y1024(37.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
        draw(x1024(318.0F), y1024(37.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
        draw(x1024(606.0F), y1024(37.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

        draw(x1024(48.0F), y1024(320.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
        draw(x1024(144.0F), y1024(320.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
        draw(x1024(318.0F), y1024(320.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
        draw(x1024(606.0F), y1024(320.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

        draw(x1024(320.0F), y1024(274.0F), x1024(528.0F), y1024(32.0F), 2, GUIQuick.this.Localize("quick.ASET"));

        setCanvasFont(1);
        draw(x1024(38.0F), y1024(13.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.ADDFRI"));
        draw(x1024(38.0F), y1024(291.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.ADDENM"));
      }
    }

    public void setPosSize() {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIQuick.this.bExit.setPosC(x1024(85.0F), y1024(689.0F));
      GUIQuick.this.bBack.setPosC(x1024(85.0F), y1024(689.0F));
      GUIQuick.this.bLoad.setPosC(x1024(228.0F), y1024(689.0F));
      GUIQuick.this.bSave.setPosC(x1024(370.0F), y1024(689.0F));
      GUIQuick.this.bFly.setPosC(x1024(512.0F), y1024(689.0F));
      GUIQuick.this.bStat.setPosC(x1024(939.0F), y1024(689.0F));
      GUIQuick.this.bReset.setPosC(x1024(796.0F), y1024(689.0F));
      GUIQuick.this.bArmy.setPosC(x1024(966.0F), y1024(39.0F));
      GUIQuick.this.bNext.setPosC(x1024(939.0F), y1024(689.0F));
      GUIQuick.this.bDiff.setPosC(x1024(654.0F), y1024(689.0F));
      GUIQuick.this.wAltitude.setPosSize(x1024(432.0F), y1024(508.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wSituation.setPosSize(x1024(432.0F), y1024(542.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wPos.setPosSize(x1024(432.0F), y1024(576.0F), x1024(160.0F), M(1.7F));

      GUIQuick.this.wTarget.setPosSize(x1024(142.0F), y1024(508.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wLevel.setPosSize(x1024(142.0F), y1024(542.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wDefence.setPosSize(x1024(142.0F), y1024(576.0F), x1024(160.0F), M(1.7F));

      GUIQuick.this.wMap.setPosSize(x1024(688.0F), y1024(508.0F), x1024(256.0F), M(1.7F));
      GUIQuick.this.wPlaneList.setPosSize(x1024(688.0F), y1024(542.0F), x1024(256.0F), M(1.7F));

      GUIQuick.this.wWeather.setPosSize(x1024(432.0F), y1024(508.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wCldHeight.setPosSize(x1024(784.0F), y1024(508.0F), x1024(160.0F), M(1.7F));

      GUIQuick.this.wTimeHour.setPosSize(x1024(132.0F), y1024(508.0F), x1024(80.0F), M(1.7F));
      GUIQuick.this.wTimeMins.setPosSize(x1024(222.0F), y1024(508.0F), x1024(80.0F), M(1.7F));

      GUIQuick.this.dlg[0].wNum.setPosSize(x1024(48.0F), y1024(70.0F), x1024(80.0F), M(1.7F));
      GUIQuick.this.dlg[0].wSkill.setPosSize(x1024(144.0F), y1024(70.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.dlg[0].wPlane.setPosSize(x1024(318.0F), y1024(70.0F), x1024(274.0F), M(1.7F));
      GUIQuick.this.dlg[0].wLoadout.setPosSize(x1024(609.0F), y1024(70.0F), x1024(332.0F), M(1.7F));
      GUIQuick.this.dlg[0].bArming.setPosC(x1024(959.0F), y1024(84.0F));
      for (int i = 0; i < 7; i++) {
        if (i < 3) {
          GUIQuick.this.dlg[(i + 1)].wNum.setPosSize(x1024(48.0F), y1024(175 + 34 * i), x1024(80.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].wSkill.setPosSize(x1024(144.0F), y1024(175 + 34 * i), x1024(160.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].wPlane.setPosSize(x1024(318.0F), y1024(175 + 34 * i), x1024(274.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].wLoadout.setPosSize(x1024(609.0F), y1024(175 + 34 * i), x1024(332.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].bArming.setPosC(x1024(959.0F), y1024(175 + 34 * i + 16 - 2));
        } else {
          GUIQuick.this.dlg[(i + 1)].wNum.setPosSize(x1024(48.0F), y1024(104 + 34 * (i - 4)), x1024(80.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].wSkill.setPosSize(x1024(144.0F), y1024(104 + 34 * (i - 4)), x1024(160.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].wPlane.setPosSize(x1024(318.0F), y1024(104 + 34 * (i - 4)), x1024(274.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].wLoadout.setPosSize(x1024(609.0F), y1024(104 + 34 * (i - 4)), x1024(332.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 1)].bArming.setPosC(x1024(959.0F), y1024(104 + 34 * (i - 4) + 16 - 2));
        }
      }
      for (i = 0; i < 8; i++)
        if (i < 4) {
          GUIQuick.this.dlg[(i + 8)].wNum.setPosSize(x1024(48.0F), y1024(352 + 34 * i), x1024(80.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].wSkill.setPosSize(x1024(144.0F), y1024(352 + 34 * i), x1024(160.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].wPlane.setPosSize(x1024(318.0F), y1024(352 + 34 * i), x1024(274.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].wLoadout.setPosSize(x1024(609.0F), y1024(352 + 34 * i), x1024(332.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].bArming.setPosC(x1024(959.0F), y1024(352 + 34 * i + 16 - 2));
        } else {
          GUIQuick.this.dlg[(i + 8)].wNum.setPosSize(x1024(48.0F), y1024(352 + 34 * (i - 4)), x1024(80.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].wSkill.setPosSize(x1024(144.0F), y1024(352 + 34 * (i - 4)), x1024(160.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].wPlane.setPosSize(x1024(318.0F), y1024(352 + 34 * (i - 4)), x1024(274.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].wLoadout.setPosSize(x1024(609.0F), y1024(352 + 34 * (i - 4)), x1024(332.0F), M(1.7F));
          GUIQuick.this.dlg[(i + 8)].bArming.setPosC(x1024(959.0F), y1024(352 + 34 * (i - 4) + 16 - 2));
        }
    }

    public DialogClient()
    {
    }
  }

  public class byI18N_name
    implements Comparator
  {
    public byI18N_name()
    {
    }

    public int compare(Object paramObject1, Object paramObject2)
    {
      if (RTSConf.cur.locale.getLanguage().equals("ru")) {
        return GUIQuick.this.collator.compare(I18N.plane(((GUIQuick.ItemAir)paramObject1).name), I18N.plane(((GUIQuick.ItemAir)paramObject2).name));
      }
      Collator localCollator = Collator.getInstance(RTSConf.cur.locale);
      localCollator.setStrength(1);
      localCollator.setDecomposition(2);
      return localCollator.compare(I18N.plane(((GUIQuick.ItemAir)paramObject1).name), I18N.plane(((GUIQuick.ItemAir)paramObject2).name));
    }
  }
}