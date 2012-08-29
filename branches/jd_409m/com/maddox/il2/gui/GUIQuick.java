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
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.rts.IniFile;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class GUIQuick extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public SectFile ssect;
  public GTexture cross;
  public GTexture star;
  public boolean OUR;
  public boolean SCRAMBLE;
  public boolean LEVEL;
  public GUIButton bArmy;
  public GUIButton bExit;
  public GUIButton bLoad;
  public GUIButton bSave;
  public GUIButton bFly;
  public GUIButton bDiff;
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
  private String r01;
  private String r010;
  private String g01;
  private String g010;
  private String[] _mapKey = { "Okinawa", "NetCoralSea", "Net8Islands", "Smolensk", "Moscow", "Crimea", "Kuban", "Bessarabia", "MTO", "SlovakiaW", "SlovakiaS" };

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

  private void mapChanged()
  {
    int i = this.wMap.getSelected();
    if ((i >= 0) && (i < this._mapKey.length))
    {
      String str1 = this._mapKey[i];
      String str2 = "Missions/Quick/" + str1 + "RedNone00.mis";
      SectFile localSectFile1 = new SectFile(str2, 0);
      int j;
      if (localSectFile1.sectionIndex("r0100") >= 0)
      {
        this.r01 = "r01";
        this.r010 = "r010";
        this.g01 = "g01";
        this.g010 = "g010";
        for (j = 0; j < 8; j++)
        {
          if (this.wing[j].regiment.equals("usa01"))
            this.wing[j].regiment = "r01";
          if (this.wing[j].regiment.equals("ja01"))
            this.wing[j].regiment = "g01";
        }
      }
      else
      {
        this.r01 = "usa01";
        this.r010 = "usa010";
        this.g01 = "ja01";
        this.g010 = "ja010";
        for (j = 0; j < 8; j++)
        {
          if (this.wing[j].regiment.equals("r01"))
            this.wing[j].regiment = "usa01";
          if (this.wing[j].regiment.equals("g01")) {
            this.wing[j].regiment = "ja01";
          }
        }
      }
      String str3 = localSectFile1.get("MAIN", "MAP", (String)null);
      if (str3 != null)
      {
        SectFile localSectFile2 = new SectFile("maps/" + str3, 0);
        IniFile localIniFile = new IniFile("maps/" + str3, 0);
        String str4 = localIniFile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");
        if (World.cur() != null)
          World.cur().setCamouflage(str4);
      }
    }
  }

  public void enterPush(GameState paramGameState)
  {
    World.cur().diffUser.set(World.cur().userCfg.singleDifficulty);
    this.wing[0].fromUserCfg();
    mapChanged();
    if (this.r01.equals("usa01"))
      Main3D.menuMusicPlay(this.OUR ? "us" : "ja");
    else
      Main3D.menuMusicPlay(this.OUR ? "ru" : "de");
    _enter();
  }

  public void enterPop(GameState paramGameState)
  {
    if (paramGameState.id() == 17)
    {
      World.cur().userCfg.singleDifficulty = World.cur().diffUser.get();
      World.cur().userCfg.saveConf();
    }
    else if (paramGameState.id() == 55) {
      this.wing[this.indxAirArming].fromAirArming();
    }
    else if (paramGameState.id() == 25) {
      load();
    }_enter();
  }

  public void _enter() {
    Main.cur().currentMissionFile = null;
    this.client.activateWindow();
  }

  public void _leave()
  {
    World.cur().userCfg.saveConf();
    this.client.hideWindow();
  }

  private void validateCldHeight() {
    String str = this.wCldHeight.getValue();
    if (str.equals("")) {
      this.wCldHeight.setSelected(0, true, false);
      str = this.wCldHeight.get(0);
    }
    int i = Integer.parseInt(str);
    if (i < Integer.parseInt(this.wCldHeight.get(0))) this.wCldHeight.setSelected(0, true, false);
    if (i > Integer.parseInt(this.wCldHeight.get(this.wCldHeight.size() - 1))) this.wCldHeight.setSelected(this.wCldHeight.size() - 1, true, false); 
  }

  private void validatePos()
  {
    String str = this.wPos.getValue();
    if (str.equals("")) {
      this.wPos.setSelected(0, true, false);
      str = this.wPos.get(0);
    }
    int i = Integer.parseInt(str);
    if (i < Integer.parseInt(this.wPos.get(0))) this.wPos.setSelected(0, true, false);
    if (i > Integer.parseInt(this.wPos.get(this.wPos.size() - 1))) this.wPos.setSelected(this.wPos.size() - 1, true, false); 
  }

  private void validateAltitude()
  {
    String str = this.wAltitude.getValue();
    if (str.equals("")) {
      this.wPos.setSelected(0, true, false);
      str = this.wAltitude.get(0);
    }
    int i = Integer.parseInt(str);
    if (i < Integer.parseInt(this.wAltitude.get(0))) this.wAltitude.setSelected(0, true, false);
    if (i > Integer.parseInt(this.wAltitude.get(this.wAltitude.size() - 1))) this.wAltitude.setSelected(this.wAltitude.size() - 1, true, false);
  }

  public void startQuickMission()
  {
    String str1 = "Missions/Quick/" + this._mapKey[this.wMap.getSelected()];
    Random localRandom = new Random();
    int i = localRandom.nextInt(3);

    if (this.OUR)
      str1 = str1 + "Red";
    else
      str1 = str1 + "Blue";
    str1 = str1 + this._targetKey[this.wTarget.getSelected()];

    if (this.wTarget.getSelected() == 4)
    {
      str1 = str1 + "0" + i + ".mis";
    }
    else if (this.LEVEL)
    {
      str1 = str1 + this.wLevel.getSelected() + "0.mis";
    }
    else str1 = str1 + "00.mis";

    try
    {
      SectFile localSectFile = new SectFile(str1, 0);
      localSectFile.set("MAIN", "TIME", this.wTimeHour.getValue() + "." + this.wTimeMins.getSelected() * 25);
      for (int j = 0; j < 4; j++)
      {
        String str2 = this.r010 + Character.forDigit(j, 10);
        if (!localSectFile.exist("Wing", str2)) {
          throw new Exception("Section " + str2 + " not found");
        }
      }
      for (int k = 0; k < 4; k++)
      {
        localObject = this.g010 + Character.forDigit(k, 10);
        if (!localSectFile.exist("Wing", (String)localObject)) {
          throw new Exception("Section " + (String)localObject + " not found");
        }
      }
      localSectFile.set("MAIN", "CloudType", this.wWeather.getSelected());
      localSectFile.set("MAIN", "CloudHeight", this.wCldHeight.getValue());
      Object localObject = new String[8];
      String[] arrayOfString = new String[8];
      for (int m = 0; m < 8; m++)
      {
        if (m <= 3)
          localObject[m] = ((this.OUR ? this.r010 : this.g010) + m);
        else
          localObject[m] = ((this.OUR ? this.g010 : this.r010) + m);
        arrayOfString[m] = this.wing[m].prepareWing(localSectFile);
      }

      if (arrayOfString[0] != null)
        localSectFile.set("MAIN", "player", arrayOfString[0]);
      else
        localSectFile.set("MAIN", "player", localObject[0]);
      for (int n = 0; n < 8; n++)
        if (arrayOfString[n] != null)
          this.wing[n].prepereWay(localSectFile, localObject, arrayOfString);
      if (this.wDefence.getSelected() == 0)
      {
        int i3;
        int i4;
        int i5;
        String str5;
        String str6;
        int i7;
        String str7;
        for (int i1 = 0; i1 < 2; i1++)
        {
          String str3 = i1 != 0 ? "NStationary" : "Stationary";
          i3 = localSectFile.sectionIndex(str3);
          if (i3 < 0)
            continue;
          localSectFile.sectionRename(i3, "Stationary_Temp");
          localSectFile.sectionAdd(str3);
          i4 = localSectFile.sectionIndex(str3);
          i5 = localSectFile.vars(i3);
          for (int i6 = 0; i6 < i5; i6++)
          {
            SharedTokenizer.set(localSectFile.line(i3, i6));
            str5 = null;
            if (i1 == 1)
              str5 = SharedTokenizer.next("");
            str6 = SharedTokenizer.next("");
            i7 = SharedTokenizer.next(0);
            str7 = null;
            if (str5 != null)
              str7 = str5 + " " + str6 + " " + i7 + " " + SharedTokenizer.getGap();
            else
              str7 = str6 + " " + i7 + " " + SharedTokenizer.getGap();
            if (i7 == 0) {
              localSectFile.lineAdd(i4, str7);
            }
            else if ((i7 == 1) && (this.OUR) && (!this.SCRAMBLE)) {
              localSectFile.lineAdd(i4, str7);
            }
            else if ((i7 == 2) && (!this.OUR) && (!this.SCRAMBLE))
              localSectFile.lineAdd(i4, str7);
            else
              try
              {
                Class localClass = ObjIO.classForName(str6);
                if (!AAA.class.isAssignableFrom(localClass))
                  if (str6.startsWith("ships."))
                  {
                    SharedTokenizer.set(localSectFile.line(i3, i6));
                    if (i1 == 1)
                      str5 = SharedTokenizer.next("");
                    String str9 = SharedTokenizer.next("");
                    String str10 = SharedTokenizer.next("");
                    String str11 = SharedTokenizer.next("");
                    String str12 = SharedTokenizer.next("");
                    String str13 = SharedTokenizer.next("");
                    String str14 = SharedTokenizer.next("");
                    String str15 = SharedTokenizer.next("");
                    String str16 = SharedTokenizer.next("");
                    if (i1 == 1)
                      localSectFile.lineAdd(i4, str5 + " " + str9 + " " + str10 + " " + str11 + " " + str12 + " " + str13 + " " + str14 + " " + 5940 + " " + str16);
                    else
                      localSectFile.lineAdd(i4, str9 + " " + str10 + " " + str11 + " " + str12 + " " + str13 + " " + str14 + " " + 5940 + " " + str16);
                  }
                  else {
                    localSectFile.lineAdd(i4, str7);
                  }
              }
              catch (Throwable localThrowable) {
              }
          }
          localSectFile.sectionRemove(i3);
        }

        int i2 = localSectFile.sectionIndex("Chiefs");
        if (i2 >= 0)
        {
          localSectFile.sectionRename(i2, "Chiefs_Temp");
          localSectFile.sectionAdd("Chiefs");
          i3 = localSectFile.sectionIndex("Chiefs");
          i4 = localSectFile.vars(i2);
          for (i5 = 0; i5 < i4; i5++)
          {
            String str4 = localSectFile.line(i2, i5);
            SharedTokenizer.set(str4);
            str5 = SharedTokenizer.next("");
            str6 = SharedTokenizer.next("");
            if (str6.startsWith("Ships."))
            {
              i7 = SharedTokenizer.next(0);
              if (i7 == 0) {
                localSectFile.lineAdd(i3, str4);
              }
              else if ((i7 == 1) && (this.OUR) && (!this.SCRAMBLE)) {
                localSectFile.lineAdd(i3, str4);
              }
              else if ((i7 == 2) && (!this.OUR) && (!this.SCRAMBLE))
              {
                localSectFile.lineAdd(i3, str4);
              }
              else {
                str7 = SharedTokenizer.next("");
                String str8 = SharedTokenizer.next("");
                localSectFile.lineAdd(i3, str5 + " " + str6 + " " + i7 + " " + 5940 + " " + str8);
              }
            }
            else {
              localSectFile.lineAdd(i3, str4);
            }
          }

          localSectFile.sectionRemove(i2);
        }
      }
      Main.cur().currentMissionFile = localSectFile;
      Main.stateStack().push(5);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      System.out.println(">> no file: " + str1 + "");
      GWindowMessageBox localGWindowMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, "Error", "Data file corrupt", 3, 0.0F);
      return;
    }
  }

  public void save()
  {
    try
    {
      this.ssect = new SectFile();
      this.ssect.sectionAdd("states");
      this.ioState.save();
      this.ssect.set("states", "head", this.ioState, false);
      for (int i = 0; i < 8; i++) {
        this.ssect.set("states", "wing" + i, this.wing[i], false);
      }
    }
    catch (Exception localException)
    {
      System.out.println("sorry, cant save");
      GWindowMessageBox localGWindowMessageBox = new GWindowMessageBox(this.client, 20.0F, true, "Error", "Can't save data file", 3, 0.0F);
    }
    GUIQuickSave localGUIQuickSave = (GUIQuickSave)GameState.get(24);
    localGUIQuickSave.sect = this.ssect;
  }

  public void load()
  {
    if (this.ssect == null)
      return;
    try
    {
      this.ssect.get("states", "head", this.ioState);
      for (int i = 0; i < 8; i++) {
        this.ssect.get("states", "wing" + i, this.wing[i]);
      }
    }
    catch (Exception localException)
    {
      System.out.println("sorry, data corrupt");
      GWindowMessageBox localGWindowMessageBox = new GWindowMessageBox(this.client, 20.0F, true, "Error", "Data file corrupt", 3, 0.0F);
    }
  }

  public void fillArrayPlanes()
  {
    String str1 = "com/maddox/il2/objects/air.ini";
    SectFile localSectFile = new SectFile(str1, 0);
    int i = localSectFile.sections();
    if (i <= 0)
      throw new RuntimeException("GUIQuick: file '" + str1 + "' is empty");
    for (int j = 0; j < i; j++)
    {
      String str2 = localSectFile.sectionName(j);
      int k = localSectFile.vars(j);
      for (int m = 0; m < k; m++)
      {
        String str3 = localSectFile.var(j, m);
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.value(j, m));
        String str4 = localNumberTokenizer.next((String)null);
        int n = 1;
        while (localNumberTokenizer.hasMoreTokens()) {
          if (!"NOQUICK".equals(localNumberTokenizer.next()))
            continue;
          n = 0;
          break;
        }
        if (n == 0)
          continue;
        Class localClass = null;
        try
        {
          localClass = ObjIO.classForName(str4);
        }
        catch (Exception localException)
        {
          throw new RuntimeException("PlMisAir: class '" + str4 + "' not found");
        }
        ItemAir localItemAir = new ItemAir(str3, localClass, str4);
        if (localItemAir.bEnablePlayer)
        {
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

  public void fillComboPlane(GWindowComboControl paramGWindowComboControl, boolean paramBoolean)
  {
    paramGWindowComboControl.clear();
    ArrayList localArrayList = null;
    if (this.bPlaneArrestor)
      localArrayList = paramBoolean ? this.playerPlaneC : this.aiPlaneC;
    else
      localArrayList = paramBoolean ? this.playerPlane : this.aiPlane;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
    {
      ItemAir localItemAir = (ItemAir)localArrayList.get(j);
      paramGWindowComboControl.add(I18N.plane(localItemAir.name));
    }

    paramGWindowComboControl.setSelected(0, true, false);
  }

  public String fillComboWeapon(GWindowComboControl paramGWindowComboControl, ItemAir paramItemAir, int paramInt)
  {
    paramGWindowComboControl.clear();
    Class localClass = paramItemAir.clazz;
    String[] arrayOfString = Aircraft.getWeaponsRegistered(localClass);
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        String str = arrayOfString[i];
        paramGWindowComboControl.add(I18N.weapons(paramItemAir.name, str));
      }

      paramGWindowComboControl.setSelected(paramInt, true, false);
    }
    return arrayOfString[paramInt];
  }

  public String Localize(String paramString)
  {
    return I18N.gui(paramString);
  }

  public GUIQuick(GWindowRoot paramGWindowRoot)
  {
    super(14);
    this.OUR = true;
    this.SCRAMBLE = false;
    this.LEVEL = true;
    this.r01 = "r01";
    this.r010 = "r010";
    this.g01 = "g01";
    this.g010 = "g010";
    this.playerPlane = new ArrayList();
    this.aiPlane = new ArrayList();
    this.playerPlaneC = new ArrayList();
    this.aiPlaneC = new ArrayList();
    this.wing = new ItemWing[8];
    this.dlg = new ItemDlg[8];
    this.ioState = new IOState();
    this.bPlaneArrestor = false;
    fillArrayPlanes();
    for (int i = 0; i < 8; i++)
      this.wing[i] = new ItemWing(i);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = Localize("quick.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));
    GTexture localGTexture1 = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    GTexture localGTexture2 = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons;
    this.bArmy = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 0.0F, 48.0F, 48.0F)));

    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bLoad = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bSave = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bFly = ((GUIButton)this.dialogClient.addDefault(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 192.0F, 48.0F, 48.0F)));
    this.bDiff = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture1, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.wSituation = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wMap = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wTarget = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wPos = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wDefence = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wAltitude = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wWeather = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wCldHeight = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wTimeHour = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wTimeMins = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wLevel = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
    this.wLevel.add(Localize("quick.0"));
    this.wLevel.add(Localize("quick.+"));
    this.wLevel.add(Localize("quick.-"));
    this.wLevel.setEditable(false);
    this.wLevel.setSelected(0, true, false);

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
    this.wCldHeight.setEditable(true);
    this.wCldHeight.setSelected(6, true, false);
    this.wCldHeight.setNumericOnly(true);

    this.wSituation.add(Localize("quick.NON"));
    this.wSituation.add(Localize("quick.ADV"));
    this.wSituation.add(Localize("quick.DIS"));
    this.wSituation.setEditable(false);
    this.wSituation.setSelected(0, true, false);
    boolean[] arrayOfBoolean = new boolean[this._targetKey.length];
    for (int j = 0; j < this._targetKey.length; j++)
    {
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
    for (int k = 0; k < 8; k++)
    {
      this.dlg[k] = new ItemDlg();
      this.dlg[k].wNum = ((WComboNum)this.dialogClient.addControl(new WComboNum(k, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[k].wSkill = ((WComboSkill)this.dialogClient.addControl(new WComboSkill(k, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[k].wPlane = ((WComboPlane)this.dialogClient.addControl(new WComboPlane(k, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[k].wLoadout = ((WComboLoadout)this.dialogClient.addControl(new WComboLoadout(k, this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));
      this.dlg[k].bArming = ((WButtonArming)this.dialogClient.addControl(new WButtonArming(k, this.dialogClient, localGTexture2, 0.0F, 48.0F, 32.0F, 32.0F)));
      this.dlg[k].wNum.setEditable(false);
      if (k == 0)
      {
        for (m = 1; m < 5; m++)
          this.dlg[k].wNum.add("" + m);
      }
      else {
        for (m = 0; m < 5; m++)
          this.dlg[k].wNum.add("" + m);
      }
      this.dlg[k].wNum.setSelected(0, true, false);
      this.dlg[k].wSkill.setEditable(false);
      this.dlg[k].wSkill.add(Localize("quick.ROO"));
      this.dlg[k].wSkill.add(Localize("quick.EXP"));
      this.dlg[k].wSkill.add(Localize("quick.VET"));
      this.dlg[k].wSkill.add(Localize("quick.ACE"));
      this.dlg[k].wSkill.setSelected(1, true, false);
      this.dlg[k].wPlane.setEditable(false);
      this.dlg[k].wPlane.listVisibleLines = 16;
      fillComboPlane(this.dlg[k].wPlane, k == 0);
      this.dlg[k].wLoadout.setEditable(false);
      fillComboWeapon(this.dlg[k].wLoadout, this.wing[k].plane, 0);
    }
    for (int m = 0; m < this._mapKey.length; m++)
    {
      String str1 = "quick" + this._mapKey[m];
      String str2 = I18N.map(str1);
      if (!str1.equals(str2))
        this.wMap.add(I18N.map(str2));
      else
        this.wMap.add(I18N.map(this._mapKey[m]));
    }
    this.wMap.setEditable(false);
    this.wMap.setSelected(0, true, true);
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  static
  {
    ObjIO.fields(class$com$maddox$il2$gui$GUIQuick$IOState, new String[] { "our", "situation", "map", "target", "defence", "altitude", "weather", "timeH", "timeM", "pos", "cldheight", "scramble" });

    ObjIO.validate(IOState.class, "loaded");
    ObjIO.fields(class$com$maddox$il2$gui$GUIQuick$ItemWing, new String[] { "planes", "weapon", "regiment", "skin", "noseart", "pilot", "numberOn", "fuel", "skill" });

    ObjIO.accessStr(ItemWing.class, "plane", "getPlane", "setPlane");
    ObjIO.validate(ItemWing.class, "loaded");
  }

  public class IOState
  {
    public boolean our;
    public boolean scramble;
    public int situation;
    public int map;
    public int target;
    public int defence;
    public String altitude;
    public String pos;
    public int weather;
    public String cldheight;
    public int timeH;
    public int timeM;

    public void save()
    {
      this.our = GUIQuick.this.OUR;
      this.scramble = GUIQuick.this.SCRAMBLE;
      this.situation = GUIQuick.this.wSituation.getSelected();
      this.map = GUIQuick.this.wMap.getSelected();
      this.target = GUIQuick.this.wTarget.getSelected();
      this.defence = GUIQuick.this.wDefence.getSelected();
      this.altitude = GUIQuick.this.wAltitude.getValue();

      this.pos = GUIQuick.this.wPos.getValue();
      this.weather = GUIQuick.this.wWeather.getSelected();

      this.cldheight = GUIQuick.this.wCldHeight.getValue();
      this.timeH = GUIQuick.this.wTimeHour.getSelected();
      this.timeM = GUIQuick.this.wTimeMins.getSelected();
    }

    public void loaded()
    {
      GUIQuick.this.OUR = this.our;
      GUIQuick.this.wMap.setSelected(this.map, true, true);
      GUIQuick.this.wSituation.setSelected(this.situation, true, false);
      GUIQuick.this.wTarget.setSelected(this.target, true, false);
      GUIQuick.this.wDefence.setSelected(this.defence, true, false);
      if (this.pos == null) {
        if (this.situation != 0) {
          this.pos = "700";
        }
        else {
          this.pos = "0";
        }
        this.cldheight = "2000";
        int i = Integer.parseInt(this.altitude);
        this.altitude = GUIQuick.this.wAltitude.get(i);
        this.scramble = false;
      }
      GUIQuick.this.SCRAMBLE = this.scramble;
      GUIQuick.this.wPos.setValue(this.pos);
      GUIQuick.this.wAltitude.setValue(this.altitude);

      GUIQuick.this.wCldHeight.setValue(this.cldheight);
      GUIQuick.this.wWeather.setSelected(this.weather, true, false);
      GUIQuick.this.wTimeHour.setSelected(this.timeH, true, false);
      GUIQuick.this.wTimeMins.setSelected(this.timeM, true, false);
    }

    public IOState()
    {
    }
  }

  class WComboNum extends GWindowComboControl
  {
    private int indx;

    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2)
      {
        int i = getSelected();
        if (i < 0)
        {
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
      if (paramInt1 == 2)
      {
        int i = getSelected();
        if (i < 0)
        {
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
      if (paramInt1 == 2)
      {
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
        if (this.indx == 0)
        {
          GUIQuick.this.wing[this.indx].fromUserCfg();
          String[] arrayOfString = Aircraft.getWeaponsRegistered(GUIQuick.this.wing[this.indx].plane.clazz);
          for (int k = 0; k < arrayOfString.length; k++)
          {
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
      if (paramInt1 == 2)
      {
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
      if (paramInt1 == 2)
      {
        if (GUIQuick.this.wing[this.indx].planes == 0)
        {
          return true;
        }

        GUIQuick.access$1602(GUIQuick.this, this.indx);
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

  class ItemDlg
  {
    public GUIQuick.WComboNum wNum;
    public GUIQuick.WComboSkill wSkill;
    public GUIQuick.WComboPlane wPlane;
    public GUIQuick.WComboLoadout wLoadout;
    public GUIQuick.WButtonArming bArming;

    ItemDlg()
    {
    }
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

    public String getPlane()
    {
      return this.plane.name;
    }

    public void setPlane(String paramString)
    {
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int i = 0; i < localArrayList.size(); i++)
      {
        this.plane = ((GUIQuick.ItemAir)localArrayList.get(i));
        if (this.plane.name.equals(paramString))
          break;
      }
    }

    public void loaded()
    {
      GUIQuick.this.dlg[this.indx].wNum.setSelected(this.indx != 0 ? this.planes : this.planes - 1, true, false);
      GUIQuick.this.dlg[this.indx].wSkill.setSelected(this.skill, true, false);
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int i = 0; i < localArrayList.size(); i++)
      {
        if (this.plane != localArrayList.get(i))
          continue;
        GUIQuick.this.dlg[this.indx].wPlane.setSelected(i, true, false);
        break;
      }

      String[] arrayOfString = Aircraft.getWeaponsRegistered(this.plane.clazz);
      for (int j = 0; j < arrayOfString.length; j++)
      {
        if (!arrayOfString[j].equals(this.weapon))
          continue;
        GUIQuick.this.fillComboWeapon(GUIQuick.this.dlg[this.indx].wLoadout, this.plane, j);
        break;
      }

      if (this.indx == 0)
        toUserCfg();
    }

    public void toUserCfg()
    {
      if (this.indx != 0)
      {
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
      if (this.indx != 0)
      {
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
      if (this.indx <= 3)
        str1 = (GUIQuick.this.OUR ? GUIQuick.this.r010 : GUIQuick.this.g010) + this.iwing;
      else
        str1 = (GUIQuick.this.OUR ? GUIQuick.this.g010 : GUIQuick.this.r010) + this.iwing;
      int i = paramSectFile.sectionIndex("Wing");
      if (this.planes == 0)
      {
        paramSectFile.lineRemove(i, paramSectFile.varIndex(i, str1));
        paramSectFile.sectionRemove(paramSectFile.sectionIndex(str1));
        paramSectFile.sectionRemove(paramSectFile.sectionIndex(str1 + "_Way"));
        return null;
      }
      String str2 = null;
      int j;
      if (this.regiment != null)
      {
        str2 = this.regiment + "0" + this.iwing;
        paramSectFile.lineRemove(i, paramSectFile.varIndex(i, str1));
        paramSectFile.lineAdd(i, str2);
        paramSectFile.sectionRename(paramSectFile.sectionIndex(str1 + "_Way"), str2 + "_Way");
        paramSectFile.sectionRename(paramSectFile.sectionIndex(str1), str2);
        j = paramSectFile.sectionIndex(str2);
      }
      else {
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
      for (int k = 0; k < this.planes; k++)
      {
        if (this.skin[k] != null)
          paramSectFile.lineAdd(j, "skin" + k + " " + this.skin[k]);
        if (this.noseart[k] != null)
          paramSectFile.lineAdd(j, "noseart" + k + " " + this.noseart[k]);
        if (this.pilot[k] != null)
          paramSectFile.lineAdd(j, "pilot" + k + " " + this.pilot[k]);
        if (this.numberOn[k] == 0) {
          paramSectFile.lineAdd(j, "numberOn" + k + " 0");
        }
      }
      return str2;
    }

    public void prepereWay(SectFile paramSectFile, String[] paramArrayOfString1, String[] paramArrayOfString2)
    {
      int i = paramSectFile.sectionIndex(paramArrayOfString2[this.indx] + "_Way");
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++)
      {
        SharedTokenizer.set(paramSectFile.line(i, k));
        String str1 = SharedTokenizer.next("");
        String str2 = str1 + " " + SharedTokenizer.next("") + " " + SharedTokenizer.next("") + " ";
        String str3 = GUIQuick.this.wAltitude.getValue();
        if (GUIQuick.this.wSituation.getSelected() != 0)
        {
          int m = 500;
          try
          {
            m = Integer.parseInt(GUIQuick.this.wAltitude.getValue());
          } catch (Exception localException) {
          }
          if (GUIQuick.this.wSituation.getSelected() == 1)
          {
            if (this.indx <= 3)
              m += Integer.parseInt(GUIQuick.this.wPos.getValue());
          }
          else if (this.indx > 3)
            m += Integer.parseInt(GUIQuick.this.wPos.getValue());
          str3 = "" + m;
        }
        SharedTokenizer.next("");
        float f = (float)SharedTokenizer.next((this.plane.speedMin + this.plane.speedMax) / 2.0D, this.plane.speedMin, this.plane.speedMax);
        if (("TAKEOFF".equals(str1)) || ("LANDING".equals(str1)))
        {
          str3 = "0";
          f = 0.0F;
        }
        String str4 = SharedTokenizer.next((String)null);
        String str5 = SharedTokenizer.next((String)null);

        if ((str4 != null) && (!TypeTransport.class.isAssignableFrom(this.plane.clazz)))
          str4 = null;
        if (str4 != null)
        {
          for (int n = 0; n < 8; n++)
          {
            if (!str4.equals(paramArrayOfString1[n]))
              continue;
            str4 = paramArrayOfString2[n];
            break;
          }
        }

        if (str4 != null)
        {
          if (str5 != null)
            paramSectFile.line(i, k, str2 + str3 + " " + f + " " + str4 + " " + str5);
          else
            paramSectFile.line(i, k, str2 + str3 + " " + f + " " + str4);
        }
        else
          paramSectFile.line(i, k, str2 + str3 + " " + f);
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
      for (int i = 0; i < 4; i++)
      {
        this.skin[i] = null;
        this.noseart[i] = null;
      }
    }

    public void setWeapon(int paramInt)
    {
      String[] arrayOfString = Aircraft.getWeaponsRegistered(this.plane.clazz);
      this.weapon = arrayOfString[paramInt];
    }

    public void toAirArming()
    {
      GUIAirArming localGUIAirArming = (GUIAirArming)GameState.get(55);
      localGUIAirArming.quikPlayer = (this.indx == 0);
      if (this.indx <= 3)
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
      for (int i = 0; i < 4; i++)
      {
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

    public void fromAirArming()
    {
      GUIAirArming localGUIAirArming = (GUIAirArming)GameState.get(55);
      GUIQuick.ItemAir localItemAir = null;
      ArrayList localArrayList = null;
      if (GUIQuick.this.bPlaneArrestor)
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlaneC : GUIQuick.this.playerPlaneC;
      else
        localArrayList = this.indx != 0 ? GUIQuick.this.aiPlane : GUIQuick.this.playerPlane;
      for (int i = 0; i < localArrayList.size(); i++)
      {
        localItemAir = (GUIQuick.ItemAir)localArrayList.get(i);
        if (localItemAir.name.equals(localGUIAirArming.quikPlane)) {
          break;
        }
      }
      this.plane = localItemAir;
      this.weapon = localGUIAirArming.quikWeapon;
      this.regiment = localGUIAirArming.quikRegiment;
      this.fuel = localGUIAirArming.quikFuel;
      for (int j = 0; j < 4; j++)
      {
        this.skin[j] = localGUIAirArming.quikSkin[j];
        this.noseart[j] = localGUIAirArming.quikNoseart[j];
        this.pilot[j] = localGUIAirArming.quikPilot[j];
        this.numberOn[j] = localGUIAirArming.quikNumberOn[j];
      }

      loaded();
    }

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
      if (this.indx <= 3)
        this.regiment = GUIQuick.this.r01;
      else
        this.regiment = GUIQuick.this.g01;
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
      if (str != null)
      {
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
      GUIQuick.this.validateCldHeight();
      GUIQuick.this.validatePos();
      GUIQuick.this.validateAltitude();
      if (paramInt1 != 2)
        return super.notify(paramGWindow, paramInt1, paramInt2);
      int i;
      if (paramGWindow == GUIQuick.this.bArmy)
      {
        if (GUIQuick.this.OUR)
          GUIQuick.this.OUR = false;
        else
          GUIQuick.this.OUR = true;
        GUIQuick.this.mapChanged();
        if (GUIQuick.this.r01.equals("usa01"))
          Main3D.menuMusicPlay(GUIQuick.this.OUR ? "us" : "ja");
        else
          Main3D.menuMusicPlay(GUIQuick.this.OUR ? "ru" : "de");
        for (i = 0; i < 8; i++) {
          if (i <= 3)
            GUIQuick.this.wing[i].regiment = (GUIQuick.this.OUR ? GUIQuick.this.r01 : GUIQuick.this.g01);
          else
            GUIQuick.this.wing[i].regiment = (GUIQuick.this.OUR ? GUIQuick.this.g01 : GUIQuick.this.r01);
        }
        return true;
      }
      if (paramGWindow == GUIQuick.this.wTarget)
      {
        if (GUIQuick.this.wTarget.getSelected() == 4) {
          GUIQuick.this.SCRAMBLE = true;
        }
        else {
          GUIQuick.this.SCRAMBLE = false;
        }
        if (GUIQuick.this.wTarget.getSelected() == 0) {
          GUIQuick.this.LEVEL = true;
          GUIQuick.this.wLevel.showWindow();
        }
        else {
          GUIQuick.this.LEVEL = false;
          GUIQuick.this.wLevel.hideWindow();
        }
        return true;
      }
      if (paramGWindow == GUIQuick.this.bExit)
      {
        Main.stateStack().pop();
        return true;
      }
      if (paramGWindow == GUIQuick.this.bDiff)
      {
        Main.stateStack().push(17);
        return true;
      }
      if (paramGWindow == GUIQuick.this.bFly)
      {
        GUIQuick.this.validateCldHeight();
        GUIQuick.this.validatePos();
        GUIQuick.this.validateAltitude();
        GUIQuick.this.startQuickMission();
        return true;
      }

      if (paramGWindow == GUIQuick.this.bLoad)
      {
        GUIQuick.this.ssect = null;
        Main.stateStack().push(25);
        return true;
      }
      if (paramGWindow == GUIQuick.this.bSave)
      {
        GUIQuick.this.validateCldHeight();
        GUIQuick.this.validatePos();
        GUIQuick.this.validateAltitude();
        GUIQuick.this.save();
        Main.stateStack().push(24);
        return true;
      }
      if (paramGWindow == GUIQuick.this.wMap)
      {
        GUIQuick.this.mapChanged();
        if (Main.cur() != null)
        {
          Main.cur();
          if (Main.stateStack() != null)
          {
            Main.cur();
            if (Main.stateStack().peek() != null)
            {
              Main.cur();
              if (Main.stateStack().peek().id() == 14)
                if (GUIQuick.this.r01.equals("usa01"))
                  Main3D.menuMusicPlay(GUIQuick.this.OUR ? "us" : "ja");
                else
                  Main3D.menuMusicPlay(GUIQuick.this.OUR ? "ru" : "de");
            }
          }
        }
        i = GUIQuick.this.wMap.getSelected();

        boolean bool = "NetCoralSea".equals(GUIQuick.this._mapKey[i]);
        if (bool != GUIQuick.this.bPlaneArrestor)
        {
          GUIQuick.access$802(GUIQuick.this, bool);
          if (GUIQuick.this.bPlaneArrestor)
          {
            GUIQuick.this.wTarget.posEnable[1] = false;
            GUIQuick.this.wTarget.posEnable[2] = false;
            GUIQuick.this.wTarget.posEnable[4] = false;
          }
          else
          {
            GUIQuick.this.wTarget.posEnable[1] = true;
            GUIQuick.this.wTarget.posEnable[2] = true;
            GUIQuick.this.wTarget.posEnable[4] = true;
          }
          if ((GUIQuick.this.wTarget.getSelected() != 0) && (GUIQuick.this.wTarget.getSelected() != 3)) {
            GUIQuick.this.wTarget.setSelected(0, true, false);
          }

          for (int j = 0; j < 8; j++)
          {
            GUIQuick.this.fillComboPlane(GUIQuick.this.dlg[j].wPlane, j == 0);
            int k = GUIQuick.this.dlg[j].wPlane.getSelected();
            if (k == 0)
              GUIQuick.this.dlg[j].wPlane.setSelected(1, false, false);
            GUIQuick.this.dlg[j].wPlane.setSelected(0, true, true);
          }
        }

        if ("MTO".equals(GUIQuick.this._mapKey[i])) {
          GUIQuick.this.wTarget.posEnable[2] = false;
          if (GUIQuick.this.wTarget.getSelected() == 2) {
            GUIQuick.this.wTarget.setSelected(0, true, false);
          }
        }
        else if (!"NetCoralSea".equals(GUIQuick.this._mapKey[i])) {
          GUIQuick.this.wTarget.posEnable[2] = true;
        }
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render()
    {
      super.render();
      setCanvasColorWHITE();
      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(530.0F), x1024(924.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(48.0F), y1024(305.0F), x1024(924.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(864.0F), y1024(144.0F), x1024(94.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(864.0F), y1024(336.0F), x1024(94.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(958.0F), y1024(120.0F), 2.0F, x1024(56.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(958.0F), y1024(336.0F), 2.0F, x1024(32.0F));
      draw(x1024(90.0F), y1024(678.0F), x1024(170.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.BAC"));

      draw(x1024(170.0F), y1024(678.0F), x1024(170.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.LOD"));
      draw(x1024(492.0F), y1024(678.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SAV"));
      draw(x1024(846.0F), y1024(678.0F), x1024(86.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.FLY"));
      draw(x1024(735.0F), y1024(678.0F), x1024(112.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.DIF"));
      setCanvasFont(1);
      draw(x1024(80.0F), y1024(16.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.YOU"));
      draw(x1024(80.0F), y1024(128.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.FRI"));
      draw(x1024(80.0F), y1024(320.0F), x1024(602.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.ENM"));
      setCanvasFont(0);
      draw(x1024(48.0F), y1024(48.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
      draw(x1024(144.0F), y1024(48.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
      draw(x1024(318.0F), y1024(48.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
      draw(x1024(606.0F), y1024(48.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));
      draw(x1024(48.0F), y1024(160.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
      draw(x1024(144.0F), y1024(160.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
      draw(x1024(318.0F), y1024(160.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
      draw(x1024(606.0F), y1024(160.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

      draw(x1024(48.0F), y1024(350.0F), x1024(82.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.NUM"));
      draw(x1024(144.0F), y1024(350.0F), x1024(160.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.SKI"));
      draw(x1024(318.0F), y1024(350.0F), x1024(274.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.PLA"));
      draw(x1024(606.0F), y1024(350.0F), x1024(196.0F), M(2.0F), 0, GUIQuick.this.Localize("quick.TNT"));

      if (GUIQuick.this.LEVEL) {
        draw(x1024(847.0F), y1024(557.0F), x1024(132.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.+/-"));
      }

      draw(x1024(35.0F), y1024(538.0F), x1024(100.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.MAP"));
      draw(x1024(35.0F), y1024(588.0F), x1024(100.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.WEA"));
      draw(x1024(35.0F), y1024(624.0F), x1024(100.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.CLD"));

      draw(x1024(308.0F), y1024(538.0F), x1024(100.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.ALT"));
      draw(x1024(308.0F), y1024(588.0F), x1024(100.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.SIT"));
      draw(x1024(308.0F), y1024(624.0F), x1024(100.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.POS"));

      draw(x1024(548.0F), y1024(588.0F), x1024(192.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.TAR"));
      draw(x1024(548.0F), y1024(624.0F), x1024(192.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.DEF"));
      draw(x1024(608.0F), y1024(538.0F), x1024(132.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.TIM"));

      draw(x1024(320.0F), y1024(128.0F), x1024(528.0F), y1024(32.0F), 2, GUIQuick.this.Localize("quick.ASET"));
      draw(x1024(320.0F), y1024(320.0F), x1024(528.0F), y1024(32.0F), 2, GUIQuick.this.Localize("quick.ASET"));

      draw(x1024(856.0F), y1024(538.0F), x1024(8.0F), M(2.0F), 1, ":");
      setCanvasFont(0);
      if (GUIQuick.this.OUR)
        draw(x1024(566.0F), y1024(28.0F), x1024(362.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.SEL_Allies"));
      else
        draw(x1024(566.0F), y1024(28.0F), x1024(362.0F), M(2.0F), 2, GUIQuick.this.Localize("quick.SEL_Axis"));
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIQuick.this.bExit.setPosC(x1024(55.0F), y1024(696.0F));
      GUIQuick.this.bLoad.setPosC(x1024(374.0F), y1024(696.0F));
      GUIQuick.this.bSave.setPosC(x1024(456.0F), y1024(696.0F));
      GUIQuick.this.bFly.setPosC(x1024(966.0F), y1024(696.0F));
      GUIQuick.this.bArmy.setPosC(x1024(966.0F), y1024(48.0F));
      GUIQuick.this.bDiff.setPosC(x1024(700.0F), y1024(696.0F));

      GUIQuick.this.wMap.setPosSize(x1024(142.0F), y1024(542.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wWeather.setPosSize(x1024(142.0F), y1024(590.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wCldHeight.setPosSize(x1024(142.0F), y1024(624.0F), x1024(160.0F), M(1.7F));

      GUIQuick.this.wAltitude.setPosSize(x1024(432.0F), y1024(542.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wSituation.setPosSize(x1024(432.0F), y1024(590.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.wPos.setPosSize(x1024(432.0F), y1024(624.0F), x1024(160.0F), M(1.7F));

      GUIQuick.this.wTarget.setPosSize(x1024(753.0F), y1024(590.0F), x1024(190.0F), M(1.7F));
      GUIQuick.this.wDefence.setPosSize(x1024(753.0F), y1024(624.0F), x1024(190.0F), M(1.7F));
      GUIQuick.this.wTimeHour.setPosSize(x1024(753.0F), y1024(542.0F), x1024(80.0F), M(1.7F));
      GUIQuick.this.wTimeMins.setPosSize(x1024(859.0F), y1024(542.0F), x1024(80.0F), M(1.7F));

      GUIQuick.this.wLevel.setPosSize(x1024(952.0F), y1024(590.0F), x1024(60.0F), M(1.7F));

      GUIQuick.this.dlg[0].wNum.setPosSize(x1024(48.0F), y1024(80.0F), x1024(80.0F), M(1.7F));
      GUIQuick.this.dlg[0].wSkill.setPosSize(x1024(144.0F), y1024(80.0F), x1024(160.0F), M(1.7F));
      GUIQuick.this.dlg[0].wPlane.setPosSize(x1024(318.0F), y1024(80.0F), x1024(274.0F), M(1.7F));
      GUIQuick.this.dlg[0].wLoadout.setPosSize(x1024(609.0F), y1024(80.0F), x1024(332.0F), M(1.7F));
      GUIQuick.this.dlg[0].bArming.setPosC(x1024(959.0F), y1024(94.0F));
      for (int i = 0; i < 3; i++)
      {
        GUIQuick.this.dlg[(i + 1)].wNum.setPosSize(x1024(48.0F), y1024(192 + 34 * i), x1024(80.0F), M(1.7F));
        GUIQuick.this.dlg[(i + 1)].wSkill.setPosSize(x1024(144.0F), y1024(192 + 34 * i), x1024(160.0F), M(1.7F));
        GUIQuick.this.dlg[(i + 1)].wPlane.setPosSize(x1024(318.0F), y1024(192 + 34 * i), x1024(274.0F), M(1.7F));
        GUIQuick.this.dlg[(i + 1)].wLoadout.setPosSize(x1024(609.0F), y1024(192 + 34 * i), x1024(332.0F), M(1.7F));
        GUIQuick.this.dlg[(i + 1)].bArming.setPosC(x1024(959.0F), y1024(192 + 34 * i + 16 - 2));
      }

      for (int j = 0; j < 4; j++)
      {
        GUIQuick.this.dlg[(j + 4)].wNum.setPosSize(x1024(48.0F), y1024(384 + 34 * j), x1024(80.0F), M(1.7F));
        GUIQuick.this.dlg[(j + 4)].wSkill.setPosSize(x1024(144.0F), y1024(384 + 34 * j), x1024(160.0F), M(1.7F));
        GUIQuick.this.dlg[(j + 4)].wPlane.setPosSize(x1024(318.0F), y1024(384 + 34 * j), x1024(274.0F), M(1.7F));
        GUIQuick.this.dlg[(j + 4)].wLoadout.setPosSize(x1024(609.0F), y1024(384 + 34 * j), x1024(332.0F), M(1.7F));
        GUIQuick.this.dlg[(j + 4)].bArming.setPosC(x1024(959.0F), y1024(384 + 34 * j + 16 - 2));
      }
    }

    public DialogClient()
    {
    }
  }
}