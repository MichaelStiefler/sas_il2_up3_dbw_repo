package com.maddox.il2.gui;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerReg;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserRegiment;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Aircraft._WeaponSlot;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.rts.HomePath;
import com.maddox.rts.LDRres;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class GUIAirArming extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GWindowEditControl wMashineGun;
  public GWindowEditControl wCannon;
  public GWindowEditControl wRocket;
  public GWindowEditControl wRocketDelay;
  public GWindowEditControl wBombDelay;
  public GWindowComboControl cFuel;
  public GWindowComboControl cAircraft;
  public GWindowComboControl cWeapon;
  public GWindowComboControl cCountry;
  public GWindowComboControl cRegiment;
  public GWindowComboControl cSkin;
  public GWindowComboControl cNoseart;
  public GWindowComboControl cPilot;
  public GWindowEditControl wNumber;
  public GWindowComboControl cSquadron;
  public GUISwitchBox3 sNumberOn;
  public GWindowComboControl cPlane;
  public GUIButton bBack;
  public static final int SINGLE = 0;
  public static final int CAMPAIGN = 1;
  public static final int DFIGHT = 2;
  public static final int COOP = 3;
  public static final int QUIK = 4;
  public static int stateId = 2;

  public ArrayList airNames = new ArrayList();
  public ArrayList weaponNames = new ArrayList();

  public HashMapExt regList = new HashMapExt();

  public HashMapExt regHash = new HashMapExt();
  public ResourceBundle resCountry;
  public ArrayList countryLst = new ArrayList();

  private boolean bEnableWeaponChange = true;

  protected boolean quikPlayer = true;
  protected int quikArmy = 1;
  protected int quikPlanes = 4;
  protected String quikPlane = "Il-2_M3";
  protected String quikWeapon = "default";
  protected int quikCurPlane = 0;
  protected String quikRegiment = "r01";
  protected int quikSquadron = 0;
  protected int quikWing = 0;
  protected String[] quikSkin = { null, null, null, null };
  protected String[] quikNoseart = { null, null, null, null };
  protected String[] quikPilot = { null, null, null, null };
  protected boolean[] quikNumberOn = { true, true, true, true };
  protected int quikFuel = 100;
  protected ArrayList quikListPlane = new ArrayList();
  private String planeName;
  private int playerNum = -1;
  public GUIRenders renders;
  public Camera3D camera3D;
  public _Render3D render3D;
  public ActorHMesh actorMesh;
  public ArrayList weaponMeshs = new ArrayList();
  public float animateMeshA = 0.0F;
  public float animateMeshT = 0.0F;

  private Orient _orient = new Orient();

  private boolean isNet()
  {
    return (stateId == 2) || (stateId == 3);
  }
  private String airName() {
    return (String)this.airNames.get(this.cAircraft.getSelected());
  }

  public void _enter()
  {
    try
    {
      if (this.resCountry == null)
        this.resCountry = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
      this.bEnableWeaponChange = true;
      this.cFuel.setEnable(true);
      UserCfg localUserCfg = World.cur().userCfg;
      this.wMashineGun.setValue("" + localUserCfg.coverMashineGun, false);
      this.wCannon.setValue("" + localUserCfg.coverCannon, false);
      this.wRocket.setValue("" + localUserCfg.coverRocket, false);
      this.wRocketDelay.setValue("" + localUserCfg.rocketDelay, false);
      this.wBombDelay.setValue("" + localUserCfg.bombDelay, false);

      SectFile localSectFile = Main.cur().currentMissionFile;
      if (localSectFile == null) {
        World.cur().setWeaponsConstant(false);
      } else {
        int j = localSectFile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
        World.cur().setWeaponsConstant(j == 1);
      }
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      Object localObject8;
      Object localObject10;
      Object localObject1;
      int k;
      int i1;
      Object localObject12;
      Object localObject9;
      switch (stateId) {
      case 0:
      case 1:
        this.wMashineGun.showWindow();
        this.wCannon.showWindow();
        this.wRocket.showWindow();
        this.wRocketDelay.showWindow();
        this.wBombDelay.showWindow();
        this.cPlane.hideWindow();
        this.wNumber.hideWindow();
        this.cSquadron.hideWindow();
        this.sNumberOn.showWindow();
        this.sNumberOn.setChecked(localUserCfg.netNumberOn, false);
        localSectFile = Main.cur().currentMissionFile;
        localObject2 = localSectFile.get("MAIN", "player", (String)null);
        this.planeName = ((String)localObject2);
        localObject3 = ((String)localObject2).substring(0, ((String)localObject2).length() - 1);
        localObject4 = ((String)localObject3).substring(0, ((String)localObject3).length() - 1);
        Regiment localRegiment = (Regiment)Actor.getByName((String)localObject4);
        localObject5 = localSectFile.get(this.planeName, "Class", (String)null);
        localObject6 = ObjIO.classForName((String)localObject5);
        localObject8 = Property.stringValue((Class)localObject6, "keyName", null);
        this.airNames.add(localObject8);
        this.cAircraft.add(I18N.plane((String)localObject8));
        this.cAircraft.setSelected(0, true, false);
        this.countryLst.add(localRegiment.branch());
        this.cCountry.add(this.resCountry.getString(localRegiment.branch()));
        this.cCountry.setSelected(0, true, false);
        localObject10 = new ArrayList();
        ((ArrayList)localObject10).add(localRegiment);
        this.regList.put(localRegiment.branch(), localObject10);
        this.cRegiment.add(localRegiment.shortInfo());
        this.cRegiment.setSelected(0, true, false);
        int i6 = localSectFile.get(this.planeName, "Fuel", 100, 0, 100);
        if (i6 <= 25) this.cFuel.setSelected(0, true, false);
        else if (i6 <= 50) this.cFuel.setSelected(1, true, false);
        else if (i6 <= 75) this.cFuel.setSelected(2, true, false); else
          this.cFuel.setSelected(3, true, false);
        this.playerNum = localSectFile.get("Main", "playerNum", 0);
        if (stateId == 1)
          this.bEnableWeaponChange = ((this.playerNum == 0) && (!World.cur().isWeaponsConstant()));
        else
          this.bEnableWeaponChange = (!World.cur().isWeaponsConstant());
        this.cFuel.setEnable(this.bEnableWeaponChange);

        break;
      case 3:
        this.playerNum = -1;
        this.wMashineGun.showWindow();
        this.wCannon.showWindow();
        this.wRocket.showWindow();
        this.wRocketDelay.showWindow();
        this.wBombDelay.showWindow();
        this.cPlane.hideWindow();
        this.wNumber.hideWindow();
        this.cSquadron.hideWindow();
        this.sNumberOn.showWindow();
        this.sNumberOn.setChecked(localUserCfg.netNumberOn, false);
        this.planeName = GUINetAircraft.selectedWingName();
        this.bEnableWeaponChange = (!World.cur().isWeaponsConstant());
        int i = (int)localUserCfg.fuel;
        if (!this.bEnableWeaponChange) {
          localObject2 = Main.cur().currentMissionFile;
          i = ((SectFile)localObject2).get(this.planeName, "Fuel", 100, 0, 100);
        }
        if (i <= 25) this.cFuel.setSelected(0, true, false);
        else if (i <= 50) this.cFuel.setSelected(1, true, false);
        else if (i <= 75) this.cFuel.setSelected(2, true, false); else
          this.cFuel.setSelected(3, true, false);
        this.cFuel.setEnable(this.bEnableWeaponChange);
        this.airNames.add(GUINetAircraft.selectedAircraftKeyName());
        this.cAircraft.add(GUINetAircraft.selectedAircraftName());
        this.cAircraft.setSelected(0, true, false);
        this.countryLst.add(GUINetAircraft.selectedRegiment().branch());
        this.cCountry.add(this.resCountry.getString(GUINetAircraft.selectedRegiment().branch()));
        this.cCountry.setSelected(0, true, false);
        localObject2 = new ArrayList();
        ((ArrayList)localObject2).add(GUINetAircraft.selectedRegiment());
        this.regList.put(GUINetAircraft.selectedRegiment().branch(), localObject2);
        this.cRegiment.add(GUINetAircraft.selectedRegiment().shortInfo());
        this.cRegiment.setSelected(0, true, false);

        break;
      case 2:
        this.playerNum = -1;
        this.wMashineGun.showWindow();
        this.wCannon.showWindow();
        this.wRocket.showWindow();
        this.wRocketDelay.showWindow();
        this.wBombDelay.showWindow();
        this.cPlane.hideWindow();
        this.wNumber.showWindow();
        this.cSquadron.showWindow();
        this.sNumberOn.hideWindow();
        if (localUserCfg.fuel <= 25.0F) this.cFuel.setSelected(0, true, false);
        else if (localUserCfg.fuel <= 50.0F) this.cFuel.setSelected(1, true, false);
        else if (localUserCfg.fuel <= 75.0F) this.cFuel.setSelected(2, true, false); else {
          this.cFuel.setSelected(3, true, false);
        }

        localObject1 = (NetUser)NetEnv.host();
        k = ((NetUser)localObject1).getBornPlace();
        localObject3 = (BornPlace)World.cur().bornPlaces.get(k);
        int m;
        if (((BornPlace)localObject3).airNames != null) {
          localObject4 = ((BornPlace)localObject3).airNames;
          for (m = 0; m < ((ArrayList)localObject4).size(); m++) {
            localObject5 = (String)((ArrayList)localObject4).get(m);
            localObject6 = (Class)Property.value(localObject5, "airClass", null);
            if (localObject6 == null)
              continue;
            this.airNames.add(localObject5);
            this.cAircraft.add(I18N.plane((String)localObject5));
          }
        }
        if (this.airNames.size() == 0) {
          localObject4 = Main.cur().airClasses;
          for (m = 0; m < ((ArrayList)localObject4).size(); m++) {
            localObject5 = (Class)((ArrayList)localObject4).get(m);
            localObject6 = Property.stringValue((Class)localObject5, "keyName");
            if (Property.containsValue((Class)localObject5, "cockpitClass")) {
              this.airNames.add(localObject6);
              this.cAircraft.add(I18N.plane((String)localObject6));
            }
          }
        }

        localObject4 = Regiment.getAll();
        TreeMap localTreeMap = new TreeMap();
        i1 = ((List)localObject4).size();
        for (int i2 = 0; i2 < i1; i2++) {
          localObject8 = (Regiment)((List)localObject4).get(i2);
          localObject10 = ((Regiment)localObject8).name();

          if (!this.regHash.containsKey(localObject10)) {
            this.regHash.put(localObject10, localObject8);
            localObject12 = (ArrayList)this.regList.get(((Regiment)localObject8).branch());
            if (localObject12 == null) {
              String str1 = null;
              try {
                str1 = this.resCountry.getString(((Regiment)localObject8).branch());
              } catch (Exception localException6) {
                continue;
              }
              localObject12 = new ArrayList();
              this.regList.put(((Regiment)localObject8).branch(), localObject12);
              localTreeMap.put(str1, ((Regiment)localObject8).branch());
            }
            ((ArrayList)localObject12).add(localObject8);
          }
        }int i8;
        ArrayList localArrayList2;
        try { localObject8 = Main.cur().netFileServerReg.primaryPath();
          localObject10 = new File(HomePath.toFileSystemName((String)localObject8, 0));
          localObject12 = ((File)localObject10).listFiles();
          if (localObject12 != null)
            for (i8 = 0; i8 < localObject12.length; i8++) {
              localArrayList2 = localObject12[i8];
              if (localArrayList2.isFile()) {
                String str2 = localArrayList2.getName();
                if (!this.regHash.containsKey(str2)) {
                  String str3 = str2.toLowerCase();
                  if ((str3.endsWith(".bmp")) || (str3.endsWith(".tga")) || 
                    (str3.length() > 123)) continue;
                  int i9 = BmpUtils.squareSizeBMP8Pal((String)localObject8 + "/" + str3 + ".bmp");
                  if ((i9 != 64) && (i9 != 128))
                    System.out.println("File " + (String)localObject8 + "/" + str3 + ".bmp NOT loaded");
                  else
                    try
                    {
                      UserRegiment localUserRegiment = new UserRegiment(str2);

                      this.regHash.put(str2, localUserRegiment);
                      ArrayList localArrayList3 = (ArrayList)this.regList.get(localUserRegiment.branch);
                      if (localArrayList3 == null) {
                        String str4 = null;
                        try {
                          str4 = this.resCountry.getString(localUserRegiment.branch);
                        } catch (Exception localException8) {
                          continue;
                        }
                        localArrayList3 = new ArrayList();
                        this.regList.put(localUserRegiment.branch, localArrayList3);
                        localTreeMap.put(str4, localUserRegiment.branch);
                      }
                      localArrayList3.add(localUserRegiment);
                    }
                    catch (Exception localException7)
                    {
                      System.out.println(localException7.getMessage());
                      System.out.println("Regiment " + str2 + " NOT loaded");
                    }
                }
              }
            } } catch (Exception localException2) {
          System.out.println(localException2.getMessage());
          localException2.printStackTrace();
        }

        localObject9 = localTreeMap.keySet().iterator();
        while (((Iterator)localObject9).hasNext()) {
          localObject10 = (String)((Iterator)localObject9).next();
          this.countryLst.add(localTreeMap.get(localObject10));
          this.cCountry.add((String)localObject10);
        }
        localTreeMap.clear();

        this.cCountry.setSelected(0, true, false);
        fillRegiments();
        this.wNumber.setValue("" + localUserCfg.netTacticalNumber, false);
        this.cSquadron.setSelected(localUserCfg.netSquadron, true, false);
        if (localUserCfg.netRegiment != null) {
          localObject10 = this.regHash.get(localUserCfg.netRegiment);
          if (localObject10 != null) {
            localObject12 = null;
            if ((localObject10 instanceof Regiment))
              localObject12 = ((Regiment)localObject10).branch();
            else
              localObject12 = ((UserRegiment)localObject10).branch;
            i8 = 0;
            for (; i8 < this.countryLst.size(); i8++) {
              if (((String)localObject12).equals(this.countryLst.get(i8)))
                break;
            }
            if (i8 < this.countryLst.size()) {
              this.cCountry.setSelected(i8, true, false);
              fillRegiments();
              localArrayList2 = (ArrayList)this.regList.get(this.countryLst.get(i8));
              if (localArrayList2 != null) {
                for (i8 = 0; i8 < localArrayList2.size(); i8++) {
                  if (localObject10.equals(localArrayList2.get(i8))) {
                    this.cRegiment.setSelected(i8, true, false);
                    break;
                  }
                }
              }
            }
          }
        }
        this.cAircraft.setSelected(-1, false, false);
        try {
          for (int i5 = 0; i5 < this.airNames.size(); i5++)
            if (localUserCfg.netAirName.equals(this.airNames.get(i5))) {
              this.cAircraft.setSelected(i5, true, false);
              break;
            }
        } catch (Exception localException4) {
        }
        if (this.cAircraft.getSelected() < 0) {
          this.cAircraft.setSelected(0, true, false);
          localUserCfg.netAirName = ((String)this.airNames.get(0));
        }
        if ((localUserCfg.netRegiment != null) || (this.cRegiment.size() <= 0)) break;
        this.cRegiment.setSelected(-1, false, false);
        this.cRegiment.setSelected(0, true, true); break;
      case 4:
        if (this.quikPlayer) {
          this.wMashineGun.showWindow();
          this.wCannon.showWindow();
          this.wRocket.showWindow();
          this.wRocketDelay.showWindow();
          this.wBombDelay.showWindow();
        } else {
          this.wMashineGun.hideWindow();
          this.wCannon.hideWindow();
          this.wRocket.hideWindow();
          this.wRocketDelay.hideWindow();
          this.wBombDelay.hideWindow();
        }
        this.cPlane.showWindow();
        this.wNumber.hideWindow();
        this.cSquadron.hideWindow();
        this.sNumberOn.showWindow();
        this.quikCurPlane = 0;
        this.sNumberOn.setChecked(this.quikNumberOn[this.quikCurPlane], false);
        if (this.quikFuel <= 25) this.cFuel.setSelected(0, true, false);
        else if (this.quikFuel <= 50) this.cFuel.setSelected(1, true, false);
        else if (this.quikFuel <= 75) this.cFuel.setSelected(2, true, false); else {
          this.cFuel.setSelected(3, true, false);
        }
        localObject1 = this.quikListPlane;
        for (k = 0; k < ((ArrayList)localObject1).size(); k++) {
          localObject3 = (Class)((ArrayList)localObject1).get(k);
          localObject4 = Property.stringValue((Class)localObject3, "keyName");
          if ((!this.quikPlayer) || (Property.containsValue((Class)localObject3, "cockpitClass"))) {
            this.airNames.add(localObject4);
            this.cAircraft.add(I18N.plane((String)localObject4));
          }
        }
        localObject3 = Regiment.getAll();
        localObject4 = new TreeMap();
        int n = ((List)localObject3).size();
        Object localObject11;
        for (i1 = 0; i1 < n; i1++) {
          localObject7 = (Regiment)((List)localObject3).get(i1);
          if (((Regiment)localObject7).getArmy() == this.quikArmy) {
            localObject9 = ((Regiment)localObject7).name();

            if (!this.regHash.containsKey(localObject9)) {
              this.regHash.put(localObject9, localObject7);
              localObject11 = (ArrayList)this.regList.get(((Regiment)localObject7).branch());
              if (localObject11 == null) {
                localObject12 = null;
                try {
                  localObject12 = this.resCountry.getString(((Regiment)localObject7).branch());
                } catch (Exception localException5) {
                  continue;
                }
                localObject11 = new ArrayList();
                this.regList.put(((Regiment)localObject7).branch(), localObject11);
                ((TreeMap)localObject4).put(localObject12, ((Regiment)localObject7).branch());
              }
              ((ArrayList)localObject11).add(localObject7);
            }
          }
        }
        Object localObject7 = ((TreeMap)localObject4).keySet().iterator();
        while (((Iterator)localObject7).hasNext()) {
          localObject9 = (String)((Iterator)localObject7).next();
          this.countryLst.add(((TreeMap)localObject4).get(localObject9));
          this.cCountry.add((String)localObject9);
        }
        ((TreeMap)localObject4).clear();

        this.cCountry.setSelected(0, true, false);
        fillRegiments();

        if (this.quikRegiment != null) {
          localObject9 = this.regHash.get(this.quikRegiment);
          if (localObject9 != null) {
            localObject11 = ((Regiment)localObject9).branch();
            int i7 = 0;
            for (; i7 < this.countryLst.size(); i7++) {
              if (((String)localObject11).equals(this.countryLst.get(i7)))
                break;
            }
            if (i7 < this.countryLst.size()) {
              this.cCountry.setSelected(i7, true, false);
              fillRegiments();
              ArrayList localArrayList1 = (ArrayList)this.regList.get(this.countryLst.get(i7));
              if (localArrayList1 != null) {
                for (i7 = 0; i7 < localArrayList1.size(); i7++) {
                  if (localObject9.equals(localArrayList1.get(i7))) {
                    this.cRegiment.setSelected(i7, true, false);
                    break;
                  }
                }
              }
            }
          }
        }
        this.cAircraft.setSelected(-1, false, false);
        try {
          for (int i3 = 0; i3 < this.airNames.size(); i3++)
            if (this.quikPlane.equals(this.airNames.get(i3))) {
              this.cAircraft.setSelected(i3, true, false);
              break;
            }
        } catch (Exception localException3) {
        }
        if (this.cAircraft.getSelected() < 0) {
          this.cAircraft.setSelected(0, true, false);
          this.quikPlane = ((String)this.airNames.get(0));
        }
        if ((this.quikRegiment == null) && (this.cRegiment.size() > 0)) {
          this.cRegiment.setSelected(-1, false, false);
          this.cRegiment.setSelected(0, true, true);
        }
        this.cPlane.clear(false);
        for (int i4 = 0; i4 < this.quikPlanes; i4++)
          this.cPlane.add(" " + (i4 + 1));
        this.cPlane.setSelected(this.quikCurPlane, true, false);
      }

      fillWeapons();
      selectWeapon();

      fillSkins();
      selectSkin();

      fillPilots();
      selectPilot();

      fillNoseart();
      selectNoseart();

      setMesh();
      prepareMesh();
      prepareWeapons();
      prepareSkin();
      preparePilot();
      prepareNoseart();
    } catch (Exception localException1) {
      System.out.println(localException1.getMessage());
      localException1.printStackTrace();
      Main.stateStack().pop();
      return;
    }
    this.dialogClient.setPosSize();
    this.client.activateWindow();
  }
  public void _leave() {
    World.cur().setUserCovers();
    this.client.hideWindow();
  }

  private void fillRegiments() {
    if ((stateId != 2) && (stateId != 4)) return;
    this.cRegiment.clear();
    int i = this.cCountry.getSelected();
    if (i < 0)
      return;
    String str = (String)this.countryLst.get(i);
    ArrayList localArrayList = (ArrayList)this.regList.get(str);
    if (localArrayList.size() > 0) {
      for (int j = 0; j < localArrayList.size(); j++) {
        Object localObject = localArrayList.get(j);
        if ((localObject instanceof Regiment))
          this.cRegiment.add(((Regiment)localObject).shortInfo());
        else {
          this.cRegiment.add(((UserRegiment)localObject).shortInfo);
        }
      }
      this.cRegiment.setSelected(0, true, false);
    }
  }

  private void fillWeapons() {
    this.cWeapon.clear();
    this.weaponNames.clear();
    int i = this.cAircraft.getSelected();
    if (i < 0)
      return;
    Class localClass = (Class)Property.value(this.airNames.get(i), "airClass", null);
    String[] arrayOfString = Aircraft.getWeaponsRegistered(localClass);
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      String str1 = (String)this.airNames.get(i);
      for (int j = 0; j < arrayOfString.length; j++) {
        String str2 = arrayOfString[j];
        if (!this.bEnableWeaponChange) {
          String str3 = Main.cur().currentMissionFile.get(this.planeName, "weapons", (String)null);
          if (!str2.equals(str3))
            continue;
        }
        this.weaponNames.add(str2);
        this.cWeapon.add(I18N.weapons(str1, str2));
      }
      if (this.weaponNames.size() == 0) {
        this.weaponNames.add(arrayOfString[0]);
        this.cWeapon.add(I18N.weapons(str1, arrayOfString[0]));
      }
      this.cWeapon.setSelected(0, true, false);
    }
  }

  private void selectWeapon() {
    if (this.bEnableWeaponChange) {
      UserCfg localUserCfg = World.cur().userCfg;
      String str1 = null;
      if (isNet()) str1 = localUserCfg.getWeapon(airName());
      else if (stateId == 4) str1 = this.quikWeapon; else
        str1 = Main.cur().currentMissionFile.get(this.planeName, "weapons", (String)null);
      this.cWeapon.setSelected(-1, false, false);
      for (int i = 0; i < this.weaponNames.size(); i++) {
        String str2 = (String)this.weaponNames.get(i);
        if (str2.equals(str1)) {
          this.cWeapon.setSelected(i, true, false);
          break;
        }
      }
      if (this.cWeapon.getSelected() < 0) {
        this.cWeapon.setSelected(0, true, false);
        if (isNet())
          localUserCfg.setWeapon(airName(), (String)this.weaponNames.get(0));
        else if (stateId == 4)
          this.quikWeapon = ((String)this.weaponNames.get(0));
        else
          Main.cur().currentMissionFile.set(this.planeName, "weapons", (String)this.weaponNames.get(0));
      }
    } else {
      this.cWeapon.setSelected(0, true, false);
    }
  }

  public static String validateFileName(String paramString) {
    if (paramString.indexOf('\\') >= 0)
      paramString = paramString.replace('\\', '_');
    if (paramString.indexOf('/') >= 0)
      paramString = paramString.replace('/', '_');
    if (paramString.indexOf('?') >= 0)
      paramString = paramString.replace('?', '_');
    return paramString;
  }

  private void fillSkins() {
    this.cSkin.clear();
    this.cSkin.add(i18n("neta.Default"));
    try {
      int i = this.cAircraft.getSelected();
      String str1 = Main.cur().netFileServerSkin.primaryPath();
      String str2 = validateFileName((String)this.airNames.get(i));
      File localFile1 = new File(HomePath.toFileSystemName(str1 + "/" + str2, 0));

      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
        for (int j = 0; j < arrayOfFile.length; j++) {
          File localFile2 = arrayOfFile[j];
          if (localFile2.isFile()) {
            String str3 = localFile2.getName();
            String str4 = str3.toLowerCase();
            if ((!str4.endsWith(".bmp")) || 
              (str4.length() + str2.length() > 122)) continue;
            int k = BmpUtils.squareSizeBMP8Pal(str1 + "/" + str2 + "/" + str3);
            if ((k == 512) || (k == 1024))
              this.cSkin.add(str3);
            else
              System.out.println("Skin " + str1 + "/" + str2 + "/" + str3 + " NOT loaded");
          }
        }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    this.cSkin.setSelected(0, true, false);
  }
  private void selectSkin() {
    UserCfg localUserCfg = World.cur().userCfg;
    this.cSkin.setSelected(-1, false, false);
    String str1 = localUserCfg.getSkin(airName());
    if (stateId == 4)
      str1 = this.quikSkin[this.quikCurPlane];
    for (int i = 1; i < this.cSkin.size(); i++) {
      String str2 = this.cSkin.get(i);
      if (str2.equals(str1)) {
        this.cSkin.setSelected(i, true, false);
        break;
      }
    }
    if (this.cSkin.getSelected() < 0) {
      this.cSkin.setSelected(0, true, false);
      if (stateId == 4)
        this.quikSkin[this.quikCurPlane] = null;
      else
        localUserCfg.setSkin(airName(), null);
    }
  }

  private void fillPilots() {
    this.cPilot.clear();
    this.cPilot.add(i18n("neta.Default"));
    try {
      String str1 = Main.cur().netFileServerPilot.primaryPath();
      File localFile1 = new File(HomePath.toFileSystemName(str1, 0));

      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
        for (int i = 0; i < arrayOfFile.length; i++) {
          File localFile2 = arrayOfFile[i];
          if (localFile2.isFile()) {
            String str2 = localFile2.getName();
            String str3 = str2.toLowerCase();
            if ((!str3.endsWith(".bmp")) || 
              (str3.length() > 122)) continue;
            if (BmpUtils.checkBMP8Pal(str1 + "/" + str2, 256, 256))
              this.cPilot.add(str2);
            else
              System.out.println("Pilot " + str1 + "/" + str2 + " NOT loaded");
          }
        }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    this.cPilot.setSelected(0, true, false);
  }
  private void selectPilot() {
    UserCfg localUserCfg = World.cur().userCfg;
    this.cPilot.setSelected(-1, false, false);
    String str1 = localUserCfg.netPilot;
    if (stateId == 4)
      str1 = this.quikPilot[this.quikCurPlane];
    for (int i = 1; i < this.cPilot.size(); i++) {
      String str2 = this.cPilot.get(i);
      if (str2.equals(str1)) {
        this.cPilot.setSelected(i, true, false);
        break;
      }
    }
    if (this.cPilot.getSelected() < 0) {
      this.cPilot.setSelected(0, true, false);
      if (stateId == 4)
        this.quikPilot[this.quikCurPlane] = null;
      else
        localUserCfg.netPilot = null;
    }
  }

  private void fillNoseart() {
    this.cNoseart.clear();
    this.cNoseart.add(i18n("neta.None"));
    try {
      String str1 = Main.cur().netFileServerNoseart.primaryPath();
      File localFile1 = new File(HomePath.toFileSystemName(str1, 0));

      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
        for (int i = 0; i < arrayOfFile.length; i++) {
          File localFile2 = arrayOfFile[i];
          if (localFile2.isFile()) {
            String str2 = localFile2.getName();
            String str3 = str2.toLowerCase();
            if ((!str3.endsWith(".bmp")) || 
              (str3.length() > 122)) continue;
            if (BmpUtils.checkBMP8Pal(str1 + "/" + str2, 256, 512))
              this.cNoseart.add(str2);
            else
              System.out.println("Noseart " + str1 + "/" + str2 + " NOT loaded");
          }
        }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    this.cNoseart.setSelected(0, true, false);
  }
  private void selectNoseart() {
    UserCfg localUserCfg = World.cur().userCfg;
    this.cNoseart.setSelected(-1, false, false);

    boolean bool = true;

    int i = this.cAircraft.getSelected();
    if (i < 0) {
      bool = false;
    }
    else {
      Class localClass = (Class)Property.value(this.airNames.get(i), "airClass", null);
      bool = Property.intValue(localClass, "noseart", 0) == 1;
      String str2;
      String str1;
      int j;
      if (bool)
      {
        i = this.cCountry.getSelected();
        if (i < 0) {
          bool = false;
        }
        else {
          str2 = (String)this.countryLst.get(i);
          String str3 = Regiment.getCountryFromBranch(str2);
          bool = "us".equals(str3);
        }
      }
    }
    if (bool) {
      str1 = localUserCfg.getNoseart(airName());
      if (stateId == 4)
        str1 = this.quikNoseart[this.quikCurPlane];
      for (j = 1; j < this.cNoseart.size(); j++) {
        str2 = this.cNoseart.get(j);
        if (str2.equals(str1)) {
          this.cNoseart.setSelected(j, true, false);
          break;
        }
      }
      this.cNoseart.showWindow();
    } else {
      this.cNoseart.hideWindow();
    }

    if (this.cNoseart.getSelected() < 0) {
      this.cNoseart.setSelected(0, true, false);
      if (stateId == 4)
        this.quikNoseart[this.quikCurPlane] = null;
      else
        localUserCfg.setNoseart(airName(), null);
    }
  }

  private void createRender()
  {
    this.renders = new GUIRenders(this.dialogClient) {
      public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
        super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
        if (!paramBoolean) return;
        if (paramInt == 1) {
          GUIAirArming.this.animateMeshA = (GUIAirArming.this.animateMeshT = 0.0F);
          if (Actor.isValid(GUIAirArming.this.actorMesh))
            GUIAirArming.this.actorMesh.pos.setAbs(new Orient(90.0F, 0.0F, 0.0F));
        }
        else if (paramInt == 0) {
          paramFloat1 -= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 2.0F;
          if (Math.abs(paramFloat1) < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 16.0F) GUIAirArming.this.animateMeshA = 0.0F; else
            GUIAirArming.this.animateMeshA = (-128.0F * paramFloat1 / this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
          paramFloat2 -= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F;
          if (Math.abs(paramFloat2) < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 16.0F) GUIAirArming.this.animateMeshT = 0.0F; else
            GUIAirArming.this.animateMeshT = (-128.0F * paramFloat2 / this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
        }
      }
    };
    this.camera3D = new Camera3D();
    this.camera3D.set(50.0F, 1.0F, 100.0F);
    this.render3D = new _Render3D(this.renders.renders, 1.0F);
    this.render3D.setCamera(this.camera3D);
    LightEnvXY localLightEnvXY = new LightEnvXY();
    this.render3D.setLightEnv(localLightEnvXY);
    localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    Vector3f localVector3f = new Vector3f(-2.0F, 1.0F, -1.0F); localVector3f.normalize();
    localLightEnvXY.sun().set(localVector3f);
  }

  private void setMesh() {
    destroyMesh();
    int i = this.cAircraft.getSelected();
    if (i < 0)
      return;
    try {
      Class localClass = (Class)Property.value(this.airNames.get(i), "airClass", null);
      String str1 = (String)this.countryLst.get(this.cCountry.getSelected());
      String str2 = Regiment.getCountryFromBranch(str1);
      String str3 = Aircraft.getPropertyMesh(localClass, str2);
      this.actorMesh = new ActorSimpleHMesh(str3);
      double d = this.actorMesh.hierMesh().visibilityR();

      Aircraft.prepareMeshCamouflage(str3, this.actorMesh.hierMesh());

      this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(new Orient(90.0F, 0.0F, 0.0F));
      this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      d *= Math.cos(0.2617993877991494D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
      this.camera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(new Point3d(d, 0.0D, 0.0D), new Orient(180.0F, 0.0F, 0.0F));

      this.camera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void destroyMesh() {
    if (Actor.isValid(this.actorMesh))
      this.actorMesh.destroy();
    this.actorMesh = null;
    destroyWeaponMeshs();
  }
  private void destroyWeaponMeshs() {
    for (int i = 0; i < this.weaponMeshs.size(); i++) {
      ActorMesh localActorMesh = (ActorMesh)this.weaponMeshs.get(i);
      if (Actor.isValid(localActorMesh))
        localActorMesh.destroy();
    }
    this.weaponMeshs.clear();
  }

  private void prepareMesh() {
    if (!Actor.isValid(this.actorMesh)) return;
    int i = this.cAircraft.getSelected();
    if (i < 0) return;
    Class localClass;
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    switch (stateId) {
    case 3:
      localClass = GUINetAircraft.selectedAircraftClass();
      localObject1 = GUINetAircraft.selectedRegiment();
      String str1 = ((Regiment)localObject1).country();
      localObject2 = GUINetAircraft.selectedWingName();
      localObject3 = Aircraft.getPropertyPaintScheme(localClass, str1);
      if (localObject3 == null) return;
      int m = ((String)localObject2).charAt(((String)localObject2).length() - 2) - '0';
      int n = ((String)localObject2).charAt(((String)localObject2).length() - 1) - '0';
      int i1 = GUINetAircraft.selectedAircraftNumInWing();
      UserCfg localUserCfg1 = World.cur().userCfg;
      ((PaintScheme)localObject3).prepare(localClass, this.actorMesh.hierMesh(), (Regiment)localObject1, m, n, i1, localUserCfg1.netNumberOn);

      break;
    case 0:
    case 1:
    case 2:
      localClass = (Class)Property.value(this.airNames.get(i), "airClass", null);
      localObject1 = Aircraft.getPropertyPaintScheme(localClass, (String)this.countryLst.get(this.cCountry.getSelected()));
      if (localObject1 == null) return;
      int k = this.cCountry.getSelected();
      if (k < 0) return;
      localObject2 = (String)this.countryLst.get(k);
      localObject3 = (ArrayList)this.regList.get(localObject2);
      if (localObject3 == null) return;
      localObject4 = ((ArrayList)localObject3).get(this.cRegiment.getSelected());
      Object localObject5 = null;
      Object localObject6;
      if ((localObject4 instanceof Regiment)) {
        localObject5 = (Regiment)localObject4;
        localObject6 = ((NetUser)NetEnv.host()).netUserRegiment;
        ((NetUser)NetEnv.host()).setUserRegiment(((NetUserRegiment)localObject6).branch(), "", ((NetUserRegiment)localObject6).aid(), ((NetUserRegiment)localObject6).gruppeNumber());
      }
      else {
        localObject6 = (UserRegiment)localObject4;
        ((NetUser)NetEnv.host()).setUserRegiment(((UserRegiment)localObject6).country, ((UserRegiment)localObject6).fileName + ".bmp", ((UserRegiment)localObject6).id, ((UserRegiment)localObject6).gruppeNumber);

        localObject5 = ((NetUser)NetEnv.host()).netUserRegiment;
      }
      if (localObject5 == null) return;
      if (isNet()) {
        if (this.cSquadron.getSelected() < 0) return;
        localObject6 = World.cur().userCfg;
        boolean bool = ((UserCfg)localObject6).netNumberOn;
        if (stateId == 2) bool = true;
        ((PaintScheme)localObject1).prepareNum(localClass, this.actorMesh.hierMesh(), (Regiment)localObject5, this.cSquadron.getSelected(), 0, ((UserCfg)localObject6).netTacticalNumber, bool);
      } else {
        int i2 = this.planeName.charAt(this.planeName.length() - 2) - '0';
        int i3 = this.planeName.charAt(this.planeName.length() - 1) - '0';
        int i4 = Main.cur().currentMissionFile.get("Main", "playerNum", 0);
        UserCfg localUserCfg2 = World.cur().userCfg;
        ((PaintScheme)localObject1).prepare(localClass, this.actorMesh.hierMesh(), (Regiment)localObject5, i2, i3, i4, localUserCfg2.netNumberOn);
      }

      break;
    case 4:
      localClass = (Class)Property.value(this.airNames.get(i), "airClass", null);
      int j = this.cCountry.getSelected();
      if (j < 0) return;
      String str2 = (String)this.countryLst.get(j);
      localObject2 = (ArrayList)this.regList.get(str2);
      if (localObject2 == null) return;
      localObject3 = (Regiment)((ArrayList)localObject2).get(this.cRegiment.getSelected());
      if (localObject3 == null) return;
      localObject4 = Aircraft.getPropertyPaintScheme(localClass, ((Regiment)localObject3).country());
      if (localObject4 == null) return;
      ((PaintScheme)localObject4).prepare(localClass, this.actorMesh.hierMesh(), (Regiment)localObject3, this.quikSquadron, this.quikWing, this.quikCurPlane, this.quikNumberOn[this.quikCurPlane]);
    }
  }

  private void prepareWeapons()
  {
    destroyWeaponMeshs();
    if (!Actor.isValid(this.actorMesh)) return;
    int i = this.cAircraft.getSelected();
    if (i < 0) return;
    Class localClass1 = (Class)Property.value(this.airNames.get(i), "airClass", null);
    String[] arrayOfString1 = Aircraft.getWeaponsRegistered(localClass1);
    if ((arrayOfString1 == null) || (arrayOfString1.length == 0)) return;
    i = this.cWeapon.getSelected();
    if ((i < 0) || (i >= arrayOfString1.length)) return;
    String[] arrayOfString2 = Aircraft.getWeaponHooksRegistered(localClass1);
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = Aircraft.getWeaponSlotsRegistered(localClass1, arrayOfString1[i]);
    if ((arrayOfString2 == null) || (arrayOf_WeaponSlot == null)) return;
    for (int j = 0; j < arrayOfString2.length; j++)
      if ((arrayOfString2[j] != null) && (arrayOf_WeaponSlot[j] != null)) {
        Class localClass2 = arrayOf_WeaponSlot[j].clazz;
        if ((BombGun.class.isAssignableFrom(localClass2)) && 
          (!Property.containsValue(localClass2, "external"))) {
          continue;
        }
        String str = Property.stringValue(localClass2, "mesh", null);
        Object localObject;
        if (str == null) {
          localObject = (Class)Property.value(localClass2, "bulletClass", null);
          if (localObject != null)
            str = Property.stringValue((Class)localObject, "mesh", null);
        }
        if (str == null) continue;
        try {
          localObject = new ActorSimpleMesh(str);
          ((ActorSimpleMesh)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this.actorMesh, new HookNamed(this.actorMesh, arrayOfString2[j]), false);
          ((ActorSimpleMesh)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.changeHookToRel();
          ((ActorSimpleMesh)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
          this.weaponMeshs.add(localObject);
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
        }
      }
  }

  private void prepareSkin()
  {
    int i = this.cSkin.getSelected();
    if (i < 0) return;

    Class localClass = (Class)Property.value(this.airNames.get(this.cAircraft.getSelected()), "airClass", null);
    String str1 = (String)this.countryLst.get(this.cCountry.getSelected());
    String str2 = Regiment.getCountryFromBranch(str1);
    String str3 = Aircraft.getPropertyMesh(localClass, str2);
    if (i == 0)
    {
      ((NetUser)NetEnv.host()).setSkin(null);
      Aircraft.prepareMeshCamouflage(str3, this.actorMesh.hierMesh());
    } else {
      String str4 = validateFileName(airName());
      String str5 = str4 + "/" + this.cSkin.get(i);

      String str6 = str3;
      int j = str6.lastIndexOf('/');
      if (j >= 0)
        str6 = str6.substring(0, j + 1) + "summer";
      else {
        str6 = str6 + "summer";
      }
      String str7 = "PaintSchemes/Cache/" + str4;
      try {
        File localFile = new File(HomePath.toFileSystemName(str7, 0));
        if (!localFile.isDirectory()) {
          localFile.mkdir();
        } else {
          File[] arrayOfFile = localFile.listFiles();
          if (arrayOfFile != null)
            for (int k = 0; k < arrayOfFile.length; k++)
              if (arrayOfFile[k] != null) {
                String str9 = arrayOfFile[k].getName();
                if (str9.regionMatches(true, str9.length() - 4, ".tg", 0, 3))
                  arrayOfFile[k].delete();
              }
        }
      }
      catch (Exception localException)
      {
        return;
      }
      String str8 = Main.cur().netFileServerSkin.primaryPath();
      if (!BmpUtils.bmp8PalTo4TGA4(str8 + "/" + str5, str6, str7))
        return;
      Aircraft.prepareMeshCamouflage(str3, this.actorMesh.hierMesh(), str7);
      ((NetUser)NetEnv.host()).setSkin(str5);
    }
  }

  private void preparePilot() {
    int i = this.cPilot.getSelected();
    if (i < 0) return;
    Object localObject;
    String str1;
    String str2;
    String str3;
    String str4;
    if (i == 0)
    {
      localObject = (Class)Property.value(this.airNames.get(this.cAircraft.getSelected()), "airClass", null);
      str1 = (String)this.countryLst.get(this.cCountry.getSelected());
      str2 = Regiment.getCountryFromBranch(str1);
      str3 = Aircraft.getPropertyMesh((Class)localObject, str2);
      str4 = HomePath.concatNames(str3, "pilot1.mat");
      Aircraft.prepareMeshPilot(this.actorMesh.hierMesh(), 0, str4, "3do/plane/textures/pilot1.tga");
      ((NetUser)NetEnv.host()).setPilot(null);
    } else {
      localObject = Main.cur().netFileServerPilot.primaryPath();
      str1 = this.cPilot.get(i);
      str2 = str1.substring(0, str1.length() - 4);
      str3 = "PaintSchemes/Cache/Pilot" + str2 + ".mat";
      str4 = "PaintSchemes/Cache/Pilot" + str2 + ".tga";
      if (!BmpUtils.bmp8PalToTGA3((String)localObject + "/" + str1, str4))
        return;
      Aircraft.prepareMeshPilot(this.actorMesh.hierMesh(), 0, str3, str4);
      ((NetUser)NetEnv.host()).setPilot(str1);
    }
  }

  private void prepareNoseart() {
    int i = this.cNoseart.getSelected();
    if (i > 0) {
      String str1 = Main.cur().netFileServerNoseart.primaryPath();
      String str2 = this.cNoseart.get(i);
      String str3 = str2.substring(0, str2.length() - 4);
      String str4 = "PaintSchemes/Cache/Noseart0" + str3 + ".mat";
      String str5 = "PaintSchemes/Cache/Noseart0" + str3 + ".tga";
      String str6 = "PaintSchemes/Cache/Noseart1" + str3 + ".mat";
      String str7 = "PaintSchemes/Cache/Noseart1" + str3 + ".tga";
      if (!BmpUtils.bmp8PalTo2TGA4(str1 + "/" + str2, str5, str7))
        return;
      Aircraft.prepareMeshNoseart(this.actorMesh.hierMesh(), str4, str6, str5, str7);
      ((NetUser)NetEnv.host()).setNoseart(str2);
    }
  }

  private float clampValue(GWindowEditControl paramGWindowEditControl, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    String str = paramGWindowEditControl.getValue();
    try { paramFloat1 = Float.parseFloat(str); } catch (Exception localException) {
    }
    if (paramFloat1 < paramFloat2) paramFloat1 = paramFloat2;
    if (paramFloat1 > paramFloat3) paramFloat1 = paramFloat3;
    paramGWindowEditControl.setValue("" + paramFloat1, false);
    return paramFloat1;
  }

  public GUIAirArming(GWindowRoot paramGWindowRoot)
  {
    super(55);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("neta.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.wMashineGun = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wMashineGun.jdField_bNumericOnly_of_type_Boolean = (this.wMashineGun.jdField_bNumericFloat_of_type_Boolean = 1);
    this.wMashineGun.jdField_bDelayedNotify_of_type_Boolean = true;

    this.wCannon = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wCannon.jdField_bNumericOnly_of_type_Boolean = (this.wCannon.jdField_bNumericFloat_of_type_Boolean = 1);
    this.wCannon.jdField_bDelayedNotify_of_type_Boolean = true;

    this.wRocket = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wRocket.jdField_bNumericOnly_of_type_Boolean = (this.wRocket.jdField_bNumericFloat_of_type_Boolean = 1);
    this.wRocket.jdField_bDelayedNotify_of_type_Boolean = true;

    this.wRocketDelay = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wRocketDelay.jdField_bNumericOnly_of_type_Boolean = (this.wRocketDelay.jdField_bNumericFloat_of_type_Boolean = 1);
    this.wRocketDelay.jdField_bDelayedNotify_of_type_Boolean = true;

    this.wBombDelay = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null)));
    this.wBombDelay.jdField_bNumericOnly_of_type_Boolean = (this.wBombDelay.jdField_bNumericFloat_of_type_Boolean = 1);
    this.wBombDelay.jdField_bDelayedNotify_of_type_Boolean = true;

    this.wNumber = ((GWindowEditControl)this.dialogClient.addControl(new GWindowEditControl(this.dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null) {
      public void keyboardKey(int paramInt, boolean paramBoolean) {
        super.keyboardKey(paramInt, paramBoolean);
        if ((paramInt == 10) && (paramBoolean))
          notify(2, 0);
      }
    }));
    this.wNumber.jdField_bNumericOnly_of_type_Boolean = true;
    this.wNumber.jdField_bDelayedNotify_of_type_Boolean = true;
    this.wNumber.align = 1;

    this.sNumberOn = ((GUISwitchBox3)this.dialogClient.addControl(new GUISwitchBox3(this.dialogClient)));

    this.cFuel = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cFuel.setEditable(false);
    this.cFuel.add("25");
    this.cFuel.add("50");
    this.cFuel.add("75");
    this.cFuel.add("100");

    this.cAircraft = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cAircraft.setEditable(false);

    this.cWeapon = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cWeapon.setEditable(false);

    this.cCountry = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cCountry.setEditable(false);

    this.cRegiment = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cRegiment.setEditable(false);

    this.cSkin = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cSkin.setEditable(false);

    this.cPilot = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cPilot.setEditable(false);

    this.cSquadron = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cSquadron.setEditable(false);
    this.cSquadron.editBox.align = (this.cSquadron.align = 1);
    this.cSquadron.add("1");
    this.cSquadron.add("2");
    this.cSquadron.add("3");
    this.cSquadron.add("4");

    this.cPlane = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cPlane.setEditable(false);

    this.cNoseart = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
    this.cNoseart.setEditable(false);

    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    createRender();

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  static class UserRegiment
  {
    protected String country;
    protected String branch;
    protected String fileName;
    protected char[] id = new char[2];
    protected String shortInfo;
    protected int gruppeNumber = 1;

    public UserRegiment(String paramString) throws Exception {
      this.fileName = paramString;

      String str1 = Main.cur().netFileServerReg.primaryPath();
      PropertyResourceBundle localPropertyResourceBundle = new PropertyResourceBundle(new SFSInputStream(str1 + "/" + paramString));

      this.country = localPropertyResourceBundle.getString("country");
      this.country = this.country.toLowerCase().intern();
      this.branch = this.country;
      this.country = Regiment.getCountryFromBranch(this.branch);
      String str2 = localPropertyResourceBundle.getString("id");
      this.id[0] = str2.charAt(0);
      this.id[1] = str2.charAt(1);
      if (((this.id[0] < '0') || (this.id[0] > '9')) && ((this.id[0] < 'A') || (this.id[0] > 'Z')))
      {
        throw new RuntimeException("Bad regiment id[0]");
      }if (((this.id[1] < '0') || (this.id[1] > '9')) && ((this.id[1] < 'A') || (this.id[1] > 'Z')))
      {
        throw new RuntimeException("Bad regiment id[1]");
      }try {
        String str3 = localPropertyResourceBundle.getString("short");
        if ((str3 == null) || (str3.length() == 0))
          str3 = paramString;
        this.shortInfo = str3;
      } catch (Exception localException1) {
        this.shortInfo = paramString;
      }
      try {
        String str4 = localPropertyResourceBundle.getString("gruppeNumber");
        if (str4 != null) {
          try { this.gruppeNumber = Integer.parseInt(str4); } catch (Exception localException3) {
          }
          if (this.gruppeNumber < 1) this.gruppeNumber = 1;
          if (this.gruppeNumber > 5) this.gruppeNumber = 5;
        }
      }
      catch (Exception localException2)
      {
      }
    }
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      UserCfg localUserCfg = World.cur().userCfg;
      if (paramGWindow == GUIAirArming.this.cAircraft) {
        if ((GUIAirArming.stateId != 2) && (GUIAirArming.stateId != 4))
          return true;
        if (GUIAirArming.stateId == 2)
          localUserCfg.netAirName = GUIAirArming.this.airName();
        else if (GUIAirArming.stateId == 4)
          GUIAirArming.this.quikPlane = GUIAirArming.this.airName();
        GUIAirArming.this.fillWeapons();
        GUIAirArming.this.selectWeapon();
        GUIAirArming.this.fillSkins();
        GUIAirArming.this.selectSkin();
        GUIAirArming.this.selectNoseart();
        GUIAirArming.this.setMesh();
        GUIAirArming.this.prepareMesh();
        GUIAirArming.this.prepareWeapons();
        GUIAirArming.this.prepareSkin();
        GUIAirArming.this.preparePilot();
        GUIAirArming.this.prepareNoseart();
        return true;
      }
      String str1;
      ArrayList localArrayList;
      Object localObject;
      if (paramGWindow == GUIAirArming.this.cCountry) {
        if ((GUIAirArming.stateId != 2) && (GUIAirArming.stateId != 4))
          return true;
        GUIAirArming.this.fillRegiments();
        str1 = (String)GUIAirArming.this.countryLst.get(GUIAirArming.this.cCountry.getSelected());
        localArrayList = (ArrayList)GUIAirArming.this.regList.get(str1);
        localObject = localArrayList.get(GUIAirArming.this.cRegiment.getSelected());
        if (GUIAirArming.stateId == 2) {
          if ((localObject instanceof Regiment))
            localUserCfg.netRegiment = ((Regiment)localObject).name();
          else
            localUserCfg.netRegiment = ((GUIAirArming.UserRegiment)localObject).fileName;
        } else if (GUIAirArming.stateId == 4) {
          GUIAirArming.this.quikRegiment = ((Regiment)localObject).name();
        }
        GUIAirArming.this.selectNoseart();
        GUIAirArming.this.setMesh();
        GUIAirArming.this.prepareMesh();
        GUIAirArming.this.prepareWeapons();
        GUIAirArming.this.prepareSkin();
        GUIAirArming.this.preparePilot();
        GUIAirArming.this.prepareNoseart();
        return true;
      }
      if (paramGWindow == GUIAirArming.this.cRegiment) {
        if ((GUIAirArming.stateId != 2) && (GUIAirArming.stateId != 4))
          return true;
        str1 = (String)GUIAirArming.this.countryLst.get(GUIAirArming.this.cCountry.getSelected());
        localArrayList = (ArrayList)GUIAirArming.this.regList.get(str1);
        localObject = localArrayList.get(GUIAirArming.this.cRegiment.getSelected());
        if (GUIAirArming.stateId == 2) {
          if ((localObject instanceof Regiment))
            localUserCfg.netRegiment = ((Regiment)localObject).name();
          else
            localUserCfg.netRegiment = ((GUIAirArming.UserRegiment)localObject).fileName;
        } else if (GUIAirArming.stateId == 4) {
          GUIAirArming.this.quikRegiment = ((Regiment)localObject).name();
        }
        GUIAirArming.this.prepareMesh();
        return true;
      }
      if (paramGWindow == GUIAirArming.this.cWeapon) {
        if (!GUIAirArming.this.bEnableWeaponChange)
          return true;
        if (GUIAirArming.this.isNet())
          localUserCfg.setWeapon(GUIAirArming.this.airName(), (String)GUIAirArming.this.weaponNames.get(GUIAirArming.this.cWeapon.getSelected()));
        else if (GUIAirArming.stateId == 4)
          GUIAirArming.this.quikWeapon = ((String)GUIAirArming.this.weaponNames.get(GUIAirArming.this.cWeapon.getSelected()));
        else
          Main.cur().currentMissionFile.set(GUIAirArming.this.planeName, "weapons", (String)GUIAirArming.this.weaponNames.get(GUIAirArming.this.cWeapon.getSelected()));
        GUIAirArming.this.prepareWeapons();
        return true;
      }
      int i;
      if (paramGWindow == GUIAirArming.this.cSkin) {
        i = GUIAirArming.this.cSkin.getSelected();
        if (GUIAirArming.stateId == 4) {
          if (i == 0) GUIAirArming.this.quikSkin[GUIAirArming.this.quikCurPlane] = null; else
            GUIAirArming.this.quikSkin[GUIAirArming.this.quikCurPlane] = GUIAirArming.this.cSkin.get(i);
        }
        else if (i == 0) localUserCfg.setSkin(GUIAirArming.this.airName(), null); else {
          localUserCfg.setSkin(GUIAirArming.this.airName(), GUIAirArming.this.cSkin.get(i));
        }
        GUIAirArming.this.prepareSkin();
        return true;
      }
      if (paramGWindow == GUIAirArming.this.cNoseart) {
        i = GUIAirArming.this.cNoseart.getSelected();
        if (GUIAirArming.stateId == 4) {
          if (i == 0) GUIAirArming.this.quikNoseart[GUIAirArming.this.quikCurPlane] = null; else
            GUIAirArming.this.quikNoseart[GUIAirArming.this.quikCurPlane] = GUIAirArming.this.cNoseart.get(i);
        }
        else if (i == 0) localUserCfg.setNoseart(GUIAirArming.this.airName(), null); else {
          localUserCfg.setNoseart(GUIAirArming.this.airName(), GUIAirArming.this.cNoseart.get(i));
        }
        if (i == 0) {
          GUIAirArming.this.setMesh();
          GUIAirArming.this.prepareMesh();
          GUIAirArming.this.prepareWeapons();
          GUIAirArming.this.prepareSkin();
          GUIAirArming.this.preparePilot();
        }
        GUIAirArming.this.prepareNoseart();
        return true;
      }
      if (paramGWindow == GUIAirArming.this.cPilot) {
        if (GUIAirArming.stateId == 4) {
          if (GUIAirArming.this.cPilot.getSelected() == 0) GUIAirArming.this.quikPilot[GUIAirArming.this.quikCurPlane] = null; else
            GUIAirArming.this.quikPilot[GUIAirArming.this.quikCurPlane] = GUIAirArming.this.cPilot.getValue();
        }
        else if (GUIAirArming.this.cPilot.getSelected() == 0) localUserCfg.netPilot = null; else {
          localUserCfg.netPilot = GUIAirArming.this.cPilot.getValue();
        }
        GUIAirArming.this.preparePilot();
        return true;
      }
      if (paramGWindow == GUIAirArming.this.wMashineGun) {
        localUserCfg.coverMashineGun = GUIAirArming.this.clampValue(GUIAirArming.this.wMashineGun, localUserCfg.coverMashineGun, 100.0F, 1000.0F);

        return true;
      }
      if (paramGWindow == GUIAirArming.this.wCannon) {
        localUserCfg.coverCannon = GUIAirArming.this.clampValue(GUIAirArming.this.wCannon, localUserCfg.coverCannon, 100.0F, 1000.0F);

        return true;
      }
      if (paramGWindow == GUIAirArming.this.wRocket) {
        localUserCfg.coverRocket = GUIAirArming.this.clampValue(GUIAirArming.this.wRocket, localUserCfg.coverRocket, 100.0F, 1000.0F);

        return true;
      }
      if (paramGWindow == GUIAirArming.this.wRocketDelay) {
        localUserCfg.rocketDelay = GUIAirArming.this.clampValue(GUIAirArming.this.wRocketDelay, localUserCfg.rocketDelay, 1.0F, 60.0F);

        return true;
      }
      if (paramGWindow == GUIAirArming.this.wBombDelay) {
        localUserCfg.bombDelay = GUIAirArming.this.clampValue(GUIAirArming.this.wBombDelay, localUserCfg.bombDelay, 0.0F, 10.0F);

        return true;
      }
      if (paramGWindow == GUIAirArming.this.cFuel) {
        if (GUIAirArming.this.bEnableWeaponChange)
          if (GUIAirArming.this.isNet()) localUserCfg.fuel = ((GUIAirArming.this.cFuel.getSelected() + 1) * 25);
          else if (GUIAirArming.stateId == 4) GUIAirArming.this.quikFuel = ((GUIAirArming.this.cFuel.getSelected() + 1) * 25); else
            Main.cur().currentMissionFile.set(GUIAirArming.this.planeName, "Fuel", (GUIAirArming.this.cFuel.getSelected() + 1) * 25);
      }
      else {
        if (paramGWindow == GUIAirArming.this.cSquadron) {
          if (GUIAirArming.stateId != 2)
            return true;
          localUserCfg.netSquadron = GUIAirArming.this.cSquadron.getSelected();
          GUIAirArming.this.prepareMesh();
          return true;
        }
        if (paramGWindow == GUIAirArming.this.wNumber) {
          if (GUIAirArming.stateId != 2)
            return true;
          String str2 = GUIAirArming.this.wNumber.getValue();
          int j = localUserCfg.netTacticalNumber;
          try { j = Integer.parseInt(str2); } catch (Exception localException) {
          }
          if (j < 1) j = 1;
          if (j > 99) j = 99;
          GUIAirArming.this.wNumber.setValue("" + j, false);
          localUserCfg.netTacticalNumber = j;
          GUIAirArming.this.prepareMesh();
          return true;
        }
        if (paramGWindow == GUIAirArming.this.sNumberOn) {
          if (GUIAirArming.stateId == 4)
            GUIAirArming.this.quikNumberOn[GUIAirArming.this.quikCurPlane] = GUIAirArming.this.sNumberOn.isChecked();
          else
            localUserCfg.netNumberOn = GUIAirArming.this.sNumberOn.isChecked();
          GUIAirArming.this.prepareMesh();
        } else {
          if (paramGWindow == GUIAirArming.this.cPlane) {
            if (GUIAirArming.stateId != 4) return true;
            GUIAirArming.this.quikCurPlane = GUIAirArming.this.cPlane.getSelected();
            if ((GUIAirArming.this.quikPlayer) && (GUIAirArming.this.quikCurPlane == 0)) {
              GUIAirArming.this.wMashineGun.showWindow();
              GUIAirArming.this.wCannon.showWindow();
              GUIAirArming.this.wRocket.showWindow();
              GUIAirArming.this.wRocketDelay.showWindow();
              GUIAirArming.this.wBombDelay.showWindow();
            } else {
              GUIAirArming.this.wMashineGun.hideWindow();
              GUIAirArming.this.wCannon.hideWindow();
              GUIAirArming.this.wRocket.hideWindow();
              GUIAirArming.this.wRocketDelay.hideWindow();
              GUIAirArming.this.wBombDelay.hideWindow();
            }
            GUIAirArming.this.sNumberOn.setChecked(GUIAirArming.this.quikNumberOn[GUIAirArming.this.quikCurPlane], false);
            GUIAirArming.this.fillSkins();
            GUIAirArming.this.selectSkin();
            GUIAirArming.this.selectNoseart();

            GUIAirArming.this.fillPilots();
            GUIAirArming.this.selectPilot();

            GUIAirArming.this.setMesh();
            GUIAirArming.this.prepareMesh();
            GUIAirArming.this.prepareWeapons();
            GUIAirArming.this.prepareSkin();
            GUIAirArming.this.preparePilot();
            GUIAirArming.this.prepareNoseart();
            return true;
          }
          if (paramGWindow == GUIAirArming.this.bBack) {
            switch (GUIAirArming.stateId) {
            case 2:
              ((NetUser)NetEnv.host()).replicateNetUserRegiment();
            case 3:
              ((NetUser)NetEnv.host()).replicateSkin();
              ((NetUser)NetEnv.host()).replicateNoseart();
              ((NetUser)NetEnv.host()).replicatePilot();
              break;
            case 4:
            }

            localUserCfg.saveConf();
            GUIAirArming.this.destroyMesh();

            GUIAirArming.this.airNames.clear();
            GUIAirArming.this.cAircraft.clear(false);
            GUIAirArming.this.regList.clear();
            GUIAirArming.this.regHash.clear();
            GUIAirArming.this.cRegiment.clear(false);
            GUIAirArming.this.countryLst.clear();
            GUIAirArming.this.cCountry.clear(false);
            GUIAirArming.this.cWeapon.clear();
            GUIAirArming.this.weaponNames.clear();
            GUIAirArming.this.cSkin.clear(false);
            GUIAirArming.this.cPilot.clear(false);
            GUIAirArming.this.cNoseart.clear(false);
            Main.stateStack().pop();
            return true;
          }
        }
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(628.0F), y1024(176.0F), x1024(364.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(628.0F), y1024(320.0F), x1024(364.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(640.0F), x1024(962.0F), 2.0F);
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(644.0F), y1024(32.0F), x1024(332.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.Aircraft"));
      draw(x1024(644.0F), y1024(96.0F), x1024(332.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.WeaponLoadout"));
      draw(x1024(644.0F), y1024(176.0F), x1024(332.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.Country"));
      draw(x1024(644.0F), y1024(240.0F), x1024(332.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.Regiment"));
      draw(x1024(644.0F), y1024(320.0F), x1024(332.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.Skin"));
      draw(x1024(644.0F), y1024(384.0F), x1024(332.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.Pilot"));
      if (GUIAirArming.stateId == 2) {
        draw(x1024(628.0F), y1024(448.0F), x1024(220.0F), y1024(32.0F), 0, GUIAirArming.this.i18n("neta.Number"));
        draw(x1024(864.0F), y1024(448.0F), x1024(128.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.Squadron"));
      } else {
        draw(x1024(628.0F), y1024(480.0F), x1024(220.0F), y1024(32.0F), 0, GUIAirArming.this.i18n("neta.NumberOn"));
      }

      GUILookAndFeel localGUILookAndFeel = (GUILookAndFeel)lookAndFeel();
      if ((GUIAirArming.stateId != 4) || ((GUIAirArming.this.quikPlayer) && (GUIAirArming.this.quikCurPlane == 0))) {
        draw(x1024(32.0F), y1024(496.0F), x1024(576.0F), y1024(32.0F), 1, GUIAirArming.this.i18n("neta.WeaponConver"));
        draw(x1024(32.0F), y1024(544.0F), x1024(160.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.MachineGuns") + " ");
        draw(x1024(32.0F), y1024(592.0F), x1024(160.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.Cannons") + " ");
        draw(x1024(272.0F), y1024(544.0F), x1024(48.0F), y1024(32.0F), 0, " " + GUIAirArming.this.i18n("neta.m."));
        draw(x1024(272.0F), y1024(592.0F), x1024(48.0F), y1024(32.0F), 0, " " + GUIAirArming.this.i18n("neta.m."));
        draw(x1024(320.0F), y1024(544.0F), x1024(160.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.Rockets") + " ");
        draw(x1024(320.0F), y1024(592.0F), x1024(160.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.RocketDelay") + " ");
        draw(x1024(560.0F), y1024(544.0F), x1024(48.0F), y1024(32.0F), 0, " " + GUIAirArming.this.i18n("neta.m."));
        draw(x1024(560.0F), y1024(592.0F), x1024(48.0F), y1024(32.0F), 0, " " + GUIAirArming.this.i18n("neta.sec."));
        draw(x1024(608.0F), y1024(544.0F), x1024(224.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.BombDelay") + " ");
        draw(x1024(928.0F) - localGUILookAndFeel.getVScrollBarW(), y1024(544.0F), x1024(48.0F), y1024(32.0F), 0, " " + GUIAirArming.this.i18n("neta.sec."));
      }

      draw(x1024(608.0F), y1024(592.0F), x1024(224.0F), y1024(32.0F), 2, GUIAirArming.this.i18n("neta.FuelQuantity") + " ");
      draw(x1024(928.0F), y1024(592.0F), x1024(48.0F), y1024(32.0F), 0, " %");

      draw(x1024(96.0F), y1024(656.0F), x1024(320.0F), y1024(48.0F), 0, GUIAirArming.this.i18n("neta.Apply"));

      if (GUIAirArming.this.cNoseart.isVisible()) {
        draw(x1024(292.0F), y1024(656.0F), x1024(320.0F), y1024(48.0F), 2, GUIAirArming.this.i18n("neta.Noseart"));
      }

      setCanvasColorWHITE();
      localGUILookAndFeel.drawBevel(this, x1024(32.0F), y1024(32.0F), x1024(564.0F), y1024(432.0F), localGUILookAndFeel.bevelComboDown, localGUILookAndFeel.basicelements);
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);

      GUIAirArming.this.wMashineGun.set1024PosSize(192.0F, 544.0F, 80.0F, 32.0F);
      GUIAirArming.this.wCannon.set1024PosSize(192.0F, 592.0F, 80.0F, 32.0F);
      GUIAirArming.this.wRocket.set1024PosSize(480.0F, 544.0F, 80.0F, 32.0F);
      GUIAirArming.this.wRocketDelay.set1024PosSize(480.0F, 592.0F, 80.0F, 32.0F);
      GUILookAndFeel localGUILookAndFeel1 = (GUILookAndFeel)lookAndFeel();
      GUIAirArming.this.wBombDelay.setPosSize(x1024(832.0F), y1024(544.0F), x1024(96.0F) - localGUILookAndFeel1.getVScrollBarW(), y1024(32.0F));
      GUIAirArming.this.cFuel.set1024PosSize(832.0F, 592.0F, 96.0F, 32.0F);
      if (GUIAirArming.stateId == 4)
        GUIAirArming.this.cAircraft.set1024PosSize(628.0F, 64.0F, 298.0F, 32.0F);
      else
        GUIAirArming.this.cAircraft.set1024PosSize(628.0F, 64.0F, 364.0F, 32.0F);
      GUIAirArming.this.cPlane.set1024PosSize(932.0F, 64.0F, 60.0F, 32.0F);
      GUIAirArming.this.cWeapon.set1024PosSize(628.0F, 128.0F, 364.0F, 32.0F);
      GUIAirArming.this.cCountry.set1024PosSize(628.0F, 208.0F, 364.0F, 32.0F);
      GUIAirArming.this.cRegiment.set1024PosSize(628.0F, 272.0F, 364.0F, 32.0F);
      GUIAirArming.this.cSkin.set1024PosSize(628.0F, 352.0F, 364.0F, 32.0F);
      GUIAirArming.this.cPilot.set1024PosSize(628.0F, 416.0F, 364.0F, 32.0F);
      GUIAirArming.this.wNumber.set1024PosSize(628.0F, 480.0F, 112.0F, 32.0F);
      GUIAirArming.this.cSquadron.set1024PosSize(896.0F, 480.0F, 96.0F, 32.0F);
      GUIAirArming.this.sNumberOn.setPosC(x1024(944.0F), y1024(496.0F));
      GUIAirArming.this.cNoseart.set1024PosSize(628.0F, 664.0F, 364.0F, 32.0F);

      GUILookAndFeel localGUILookAndFeel2 = (GUILookAndFeel)lookAndFeel();
      GBevel localGBevel = localGUILookAndFeel2.bevelComboDown;
      GUIAirArming.this.renders.setPosSize(x1024(32.0F) + localGBevel.L.dx, y1024(32.0F) + localGBevel.T.dy, x1024(564.0F) - localGBevel.L.dx - localGBevel.R.dx, y1024(432.0F) - localGBevel.T.dy - localGBevel.B.dy);

      GUIAirArming.this.bBack.setPosC(x1024(56.0F), y1024(680.0F));
    }
  }

  class _Render3D extends Render
  {
    public void preRender()
    {
      if (Actor.isValid(GUIAirArming.this.actorMesh)) {
        if ((GUIAirArming.this.animateMeshA != 0.0F) || (GUIAirArming.this.animateMeshT != 0.0F)) {
          GUIAirArming.this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(GUIAirArming.this._orient);
          GUIAirArming.this._orient.set(GUIAirArming.this._orient.azimut() + GUIAirArming.this.animateMeshA * GUIAirArming.this.client.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec, GUIAirArming.this._orient.tangage() + GUIAirArming.this.animateMeshT * GUIAirArming.this.client.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec, 0.0F);

          GUIAirArming.this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(GUIAirArming.this._orient);
          GUIAirArming.this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        }
        GUIAirArming.this.actorMesh.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.preRender(GUIAirArming.this.actorMesh);
        for (int i = 0; i < GUIAirArming.this.weaponMeshs.size(); i++) {
          ActorMesh localActorMesh = (ActorMesh)GUIAirArming.this.weaponMeshs.get(i);
          if (Actor.isValid(localActorMesh))
            localActorMesh.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.preRender(localActorMesh); 
        }
      }
    }

    public void render() {
      if (Actor.isValid(GUIAirArming.this.actorMesh)) {
        Render.prepareStates();
        GUIAirArming.this.actorMesh.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.render(GUIAirArming.this.actorMesh);
        for (int i = 0; i < GUIAirArming.this.weaponMeshs.size(); i++) {
          ActorMesh localActorMesh = (ActorMesh)GUIAirArming.this.weaponMeshs.get(i);
          if (Actor.isValid(localActorMesh))
            localActorMesh.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.render(localActorMesh); 
        }
      }
    }

    public _Render3D(Renders paramFloat, float arg3) {
      super(localObject);
      setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
      useClearStencil(true);
    }
  }
}