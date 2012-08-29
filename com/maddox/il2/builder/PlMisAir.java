package com.maddox.il2.builder;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlMisAir extends Plugin
{
  protected Type[] type;
  double defaultHeight;
  double defaultSpeed;
  private DefaultArmy[] defaultArmy;
  private int iArmyRegimentList;
  private ArrayList regimentList;
  private PlMission pluginMission;
  private int startComboBox1;
  ViewItem[] viewType;
  HashMapInt viewMap;
  private String[] _actorInfo;
  GWindowTabDialogClient.Tab tabActor;
  GWindowTabDialogClient.Tab tabWay;
  GWindowTabDialogClient.Tab[] tabSkin;
  GWindowComboControl wArmy;
  GWindowComboControl wRegiment;
  GWindowComboControl wSquadron;
  GWindowComboControl wWing;
  GWindowComboControl wWeapons;
  ArrayList lWeapons;
  GWindowEditControl wFuel;
  GWindowComboControl wPlanes;
  GWindowComboControl wSkill;
  GWindowCheckBox wOnlyAI;
  GWindowCheckBox wParachute;
  private Object[] _squads;
  private Object[] _wings;
  GWindowButton wPrev;
  GWindowButton wNext;
  GWindowLabel wCur;
  GWindowEditControl wHeight;
  GWindowEditControl wSpeed;
  GWindowEditControl wTimeH;
  GWindowEditControl wTimeM;
  GWindowComboControl wType;
  GWindowCheckBox wRadioSilence;
  GWindowLabel wTarget;
  GWindowButton wTargetSet;
  GWindowButton wTargetClear;
  private int _curPointType;
  private static Loc nearestRunway = new Loc();
  GWindowCheckBox[] wPlayer;
  GWindowComboControl[] wSkills;
  GWindowComboControl[] wSkins;
  GWindowComboControl[] wNoseart;
  GWindowComboControl[] wPilots;
  GUIRenders[] renders;
  Camera3D[] camera3D;
  _Render3D[] render3D;
  String meshName;
  ActorSimpleHMesh[] actorMesh;
  float[] animateMeshA;
  float[] animateMeshT;
  private Orient _orient;
  private int _planeIndx;

  public PlMisAir()
  {
    this.defaultHeight = 500.0D;
    this.defaultSpeed = 300.0D;

    int i = Army.amountSingle(); if (i < Army.amountNet()) i = Army.amountNet();
    this.defaultArmy = new DefaultArmy[i];
    for (int j = 0; j < i; j++) this.defaultArmy[j] = new DefaultArmy();

    this.iArmyRegimentList = 0;
    this.regimentList = new ArrayList();

    this.viewMap = new HashMapInt();

    this._actorInfo = new String[2];

    this.tabSkin = new GWindowTabDialogClient.Tab[4];

    this._squads = new Object[4];

    this._wings = new Object[4];

    this.wPlayer = new GWindowCheckBox[4];
    this.wSkills = new GWindowComboControl[4];
    this.wSkins = new GWindowComboControl[4];
    this.wNoseart = new GWindowComboControl[4];
    this.wPilots = new GWindowComboControl[4];

    this.renders = new GUIRenders[4];
    this.camera3D = new Camera3D[4];
    this.render3D = new _Render3D[4];
    this.meshName = null;
    this.actorMesh = new ActorSimpleHMesh[4];
    this.animateMeshA = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
    this.animateMeshT = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };

    this._orient = new Orient();
  }

  private void makeRegimentList(int paramInt)
  {
    if (this.iArmyRegimentList == paramInt) return;
    this.regimentList.clear();
    List localList = Regiment.getAll();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      Regiment localRegiment1 = (Regiment)localList.get(j);
      if (localRegiment1.getArmy() == paramInt)
        this.regimentList.add(localRegiment1);
    }
    this.iArmyRegimentList = paramInt;

    this.wRegiment.clear(false);
    this.wRegiment.setSelected(-1, false, false);
    i = this.regimentList.size();
    if ((this.wRegiment.posEnable == null) || (this.wRegiment.posEnable.length < i))
      this.wRegiment.posEnable = new boolean[i];
    for (int k = 0; k < i; k++) {
      Regiment localRegiment2 = (Regiment)this.regimentList.get(k);
      String str = I18N.regimentShort(localRegiment2.shortInfo());
      if ((str != null) && (str.length() > 0) && (str.charAt(0) == '<'))
        str = I18N.regimentInfo(localRegiment2.info());
      this.wRegiment.add(str);
      this.wRegiment.posEnable[k] = true;
    }
  }

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("Wing");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    Point3d localPoint3d = new Point3d();
    for (int k = 0; k < j; k++) {
      String str1 = paramSectFile.var(i, k);
      String str2 = str1 + "_Way";
      if (!paramSectFile.sectionExist(str1)) {
        Plugin.builder.tipErr("MissionLoad: Section '" + str1 + "' not found");
      }
      else {
        int m = paramSectFile.sectionIndex(str2);
        if (m < 0) {
          Plugin.builder.tipErr("MissionLoad: Section '" + str2 + "' not found");
        }
        else {
          int n = paramSectFile.vars(m);
          if (n == 0) {
            Plugin.builder.tipErr("MissionLoad: Section '" + str2 + "' is empty");
          }
          else {
            String str3 = paramSectFile.get(str1, "Class", (String)null);
            if (str3 == null) {
              Plugin.builder.tipErr("MissionLoad: In section '" + str1 + "' field 'Class' not present");
            }
            else {
              Class localClass = null;
              try {
                localClass = ObjIO.classForName(str3);
              } catch (Exception localException) {
                Plugin.builder.tipErr("MissionLoad: In section '" + str1 + "' field 'Class' contains unknown class");
                continue;
              }

              int i1 = 0;
              int i2 = 0;
              for (i1 = 0; i1 < this.type.length; i1++) {
                for (i2 = 0; i2 < this.type[i1].item.length; i2++) {
                  if (this.type[i1].item[i2].clazz == localClass)
                    break;
                }
                if (i2 < this.type[i1].item.length)
                  break;
              }
              if (i1 >= this.type.length) {
                Plugin.builder.tipErr("MissionLoad: In section '" + str1 + "' field 'Class' contains unknown class");
              }
              else {
                boolean bool1 = paramSectFile.get(str1, "OnlyAI", 0, 0, 1) == 1;
                boolean bool2 = paramSectFile.get(str1, "Parachute", 1, 0, 1) == 1;
                int i3 = paramSectFile.get(str1, "Fuel", 100, 0, 100);
                int i4 = paramSectFile.get(str1, "Planes", 1, 1, 4);
                int i5 = -1;
                if (paramSectFile.exist(str1, "Skill"))
                  i5 = paramSectFile.get(str1, "Skill", 1, 0, 3);
                int[] arrayOfInt = new int[4];
                for (int i6 = 0; i6 < 4; i6++) {
                  if (paramSectFile.exist(str1, "Skill" + i6))
                    arrayOfInt[i6] = paramSectFile.get(str1, "Skill" + i6, 1, 0, 3);
                  else {
                    arrayOfInt[i6] = (i5 == -1 ? 1 : i5);
                  }
                }
                String[] arrayOfString1 = new String[4];
                for (int i7 = 0; i7 < 4; i7++) {
                  arrayOfString1[i7] = paramSectFile.get(str1, "skin" + i7, (String)null);
                }
                String[] arrayOfString2 = new String[4];
                for (int i8 = 0; i8 < 4; i8++)
                  arrayOfString2[i8] = paramSectFile.get(str1, "noseart" + i8, (String)null);
                String[] arrayOfString3 = new String[4];
                for (int i9 = 0; i9 < 4; i9++)
                  arrayOfString3[i9] = paramSectFile.get(str1, "pilot" + i9, (String)null);
                boolean[] arrayOfBoolean = new boolean[4];
                for (int i10 = 0; i10 < 4; i10++) {
                  arrayOfBoolean[i10] = (paramSectFile.get(str1, "numberOn" + i10, 1, 0, 1) == 1 ? 1 : false);
                }

                String str4 = str1.substring(0, str1.length() - 2);
                Regiment localRegiment = (Regiment)Actor.getByName(str4);
                int i11 = 0;
                int i12 = 0;
                int i13 = 0;
                if (localRegiment != null) {
                  makeRegimentList(localRegiment.getArmy());
                  i11 = this.regimentList.indexOf(localRegiment);
                  if (i11 >= 0) {
                    i13 = Character.getNumericValue(str1.charAt(str1.length() - 1)) - Character.getNumericValue('0');

                    i12 = Character.getNumericValue(str1.charAt(str1.length() - 2)) - Character.getNumericValue('0');

                    if (i13 < 0) i13 = 0;
                    if (i13 > 3) i13 = 3;
                    if (i12 < 0) i12 = 0;
                    if (i12 > 3) i12 = 3; 
                  }
                  else {
                    localRegiment = null;
                  }
                }
                if (localRegiment == null)
                {
                  int i14 = paramSectFile.get(str1, "Army", 0);
                  if ((i14 < 1) || (i14 >= Builder.armyAmount()))
                    i14 = 1;
                  makeRegimentList(i14);
                  localRegiment = (Regiment)this.regimentList.get(0);
                  str4 = localRegiment.name();
                }

                String str5 = paramSectFile.get(str1, "weapons", (String)null);
                int i15 = paramSectFile.get(str1, "StartTime", 0);
                if (i15 < 0) i15 = 0;

                PathAir localPathAir = new PathAir(Plugin.builder.pathes, i1, i2);
                localPathAir.setArmy(localRegiment.getArmy());
                localPathAir.sRegiment = str4;
                localPathAir.iRegiment = i11;
                localPathAir.iSquadron = i12;
                localPathAir.iWing = i13;
                localPathAir.fuel = i3;
                localPathAir.bOnlyAI = bool1;
                localPathAir.bParachute = bool2;
                localPathAir.planes = i4;
                localPathAir.skill = i5;
                localPathAir.skills = arrayOfInt;
                localPathAir.skins = arrayOfString1;
                localPathAir.noseart = arrayOfString2;
                localPathAir.pilots = arrayOfString3;
                localPathAir.bNumberOn = arrayOfBoolean;
                localPathAir.weapons = str5;

                if (!searchEnabledSlots(localPathAir)) {
                  localPathAir.destroy();
                  Plugin.builder.tipErr("MissionLoad: Section '" + str1 + "', regiment table very small");
                }
                else {
                  localPathAir.setName(localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing);
                  Property.set(localPathAir, "builderPlugin", this);
                  localPathAir.drawing(this.viewMap.containsKey(i1));
                  Object localObject1 = null;
                  Object localObject2;
                  for (int i16 = 0; i16 < n; i16++) {
                    localObject2 = paramSectFile.var(m, i16);
                    int i17 = -1;
                    if ("NORMFLY".equals(localObject2)) { i17 = 0;
                    } else if ("TAKEOFF".equals(localObject2)) { i17 = 1;
                    } else if ("LANDING".equals(localObject2)) { i17 = 2;
                    } else if ("GATTACK".equals(localObject2)) { i17 = 3;
                    } else {
                      Plugin.builder.tipErr("MissionLoad: Section '" + str2 + "' contains unknown type waypoint");
                      localPathAir.destroy();
                      continue;
                    }
                    String str6 = paramSectFile.value(m, i16);
                    if ((str6 == null) || (str6.length() <= 0)) {
                      Plugin.builder.tipErr("MissionLoad: Section '" + str2 + "' type '" + (String)localObject2 + "' is empty");
                      localPathAir.destroy();
                    }
                    else {
                      NumberTokenizer localNumberTokenizer = new NumberTokenizer(str6);
                      localPoint3d.jdField_x_of_type_Double = localNumberTokenizer.next(-1.0E+030D, -1.0E+030D, 1.0E+030D);
                      localPoint3d.jdField_y_of_type_Double = localNumberTokenizer.next(-1.0E+030D, -1.0E+030D, 1.0E+030D);
                      double d1 = localNumberTokenizer.next(0.0D, 0.0D, 10000.0D);
                      double d2 = localNumberTokenizer.next(0.0D, 0.0D, 1000.0D);
                      String str7 = null;
                      int i18 = 0;
                      String str8 = null;
                      str7 = localNumberTokenizer.next(null);
                      if (str7 != null) {
                        if (str7.equals("&0")) {
                          str8 = str7;
                          str7 = null;
                        } else if (str7.equals("&1")) {
                          str8 = str7;
                          str7 = null;
                        } else {
                          i18 = localNumberTokenizer.next(0);
                          str8 = localNumberTokenizer.next(null);
                        }
                      }
                      switch (i17) {
                      case 0:
                        i17 = 0;
                        break;
                      case 3:
                        if ((str7 != null) && (str7.startsWith("Bridge")))
                          str7 = " " + str7;
                        i17 = 3;
                        break;
                      case 1:
                        i17 = 1;
                        break;
                      case 2:
                        i17 = 2;
                      }

                      localObject1 = new PAir(localPathAir, (PPoint)localObject1, localPoint3d, i17, d1, d2);
                      if (str7 != null) {
                        ((PAir)localObject1).iTarget = i18;
                        ((PAir)localObject1).sTarget = str7;
                      }
                      if ((str8 != null) && (str8.equals("&1")))
                        ((PAir)localObject1).bRadioSilence = true;
                    }
                  }
                  if (i15 > 0) {
                    localObject2 = (PAir)localPathAir.point(0);
                    ((PAir)localObject2).jdField_time_of_type_Double = (i15 * 60.0D);
                    localPathAir.computeTimes(false);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public boolean save(SectFile paramSectFile) {
    if (Plugin.builder.pathes == null)
      return true;
    int i = paramSectFile.sectionIndex("Wing");
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    for (int j = 0; j < arrayOfObject.length; j++) {
      Actor localActor = (Actor)arrayOfObject[j];
      if (localActor == null) break;
      if ((localActor instanceof PathAir)) {
        PathAir localPathAir = (PathAir)localActor;
        int k = localPathAir.points();

        if (i <= -1)
          i = paramSectFile.sectionAdd("Wing");
        String str1 = localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing;

        String str2 = str1 + "_Way";
        paramSectFile.lineAdd(i, str1, "");
        int m = paramSectFile.sectionAdd(str1);
        paramSectFile.lineAdd(m, "Planes", "" + localPathAir.planes);
        if (localPathAir.bOnlyAI)
          paramSectFile.lineAdd(m, "OnlyAI", "1");
        if (!localPathAir.bParachute)
          paramSectFile.lineAdd(m, "Parachute", "0");
        if (localPathAir.skill != -1)
          paramSectFile.lineAdd(m, "Skill", "" + localPathAir.skill);
        for (int n = 0; n < 4; n++)
          if (localPathAir.skill != localPathAir.skills[n])
            paramSectFile.lineAdd(m, "Skill" + n, "" + localPathAir.skills[n]);
        for (int i1 = 0; i1 < 4; i1++)
          if (localPathAir.skins[i1] != null)
            paramSectFile.lineAdd(m, "skin" + i1, localPathAir.skins[i1]);
        for (int i2 = 0; i2 < 4; i2++)
          if (localPathAir.noseart[i2] != null)
            paramSectFile.lineAdd(m, "noseart" + i2, localPathAir.noseart[i2]);
        for (int i3 = 0; i3 < 4; i3++)
          if (localPathAir.pilots[i3] != null)
            paramSectFile.lineAdd(m, "pilot" + i3, localPathAir.pilots[i3]);
        for (int i4 = 0; i4 < 4; i4++)
          if (localPathAir.bNumberOn[i4] == 0)
            paramSectFile.lineAdd(m, "numberOn" + i4, "0");
        paramSectFile.lineAdd(m, "Class", ObjIO.classGetName(this.type[localPathAir._iType].item[localPathAir._iItem].clazz));
        paramSectFile.lineAdd(m, "Fuel", "" + localPathAir.fuel);
        if (localPathAir.weapons != null)
          paramSectFile.lineAdd(m, "weapons", "" + localPathAir.weapons);
        else
          paramSectFile.lineAdd(m, "weapons", "none");
        PAir localPAir1 = (PAir)localPathAir.point(0);
        if (localPAir1.jdField_time_of_type_Double > 0.0D) {
          paramSectFile.lineAdd(m, "StartTime", "" + (int)Math.round(localPAir1.jdField_time_of_type_Double / 60.0D));
        }
        int i5 = paramSectFile.sectionAdd(str2);
        for (int i6 = 0; i6 < k; i6++) {
          PAir localPAir2 = (PAir)localPathAir.point(i6);
          Point3d localPoint3d = localPAir2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
          switch (localPAir2.type()) {
          case 0:
            paramSectFile.lineAdd(i5, "NORMFLY", fmt(localPoint3d.jdField_x_of_type_Double) + " " + fmt(localPoint3d.jdField_y_of_type_Double) + " " + fmt(localPAir2.height) + " " + fmt(localPAir2.speed) + saveTarget(localPAir2) + saveRadioSilence(localPAir2));

            break;
          case 3:
            paramSectFile.lineAdd(i5, "GATTACK", fmt(localPoint3d.jdField_x_of_type_Double) + " " + fmt(localPoint3d.jdField_y_of_type_Double) + " " + fmt(localPAir2.height) + " " + fmt(localPAir2.speed) + saveTarget(localPAir2) + saveRadioSilence(localPAir2));

            break;
          case 1:
            paramSectFile.lineAdd(i5, "TAKEOFF", fmt(localPoint3d.jdField_x_of_type_Double) + " " + fmt(localPoint3d.jdField_y_of_type_Double) + " 0 0" + saveTarget(localPAir2) + saveRadioSilence(localPAir2));

            break;
          case 2:
            paramSectFile.lineAdd(i5, "LANDING", fmt(localPoint3d.jdField_x_of_type_Double) + " " + fmt(localPoint3d.jdField_y_of_type_Double) + " 0 0" + saveTarget(localPAir2) + saveRadioSilence(localPAir2));
          }

        }

      }

    }

    return true;
  }

  private String fmt(double paramDouble) {
    int i = paramDouble < 0.0D ? 1 : 0;
    if (i != 0) paramDouble = -paramDouble;
    double d = paramDouble + 0.005D - (int)paramDouble;
    if (d >= 0.1D) return (i != 0 ? "-" : "") + (int)paramDouble + "." + (int)(d * 100.0D);
    return (i != 0 ? "-" : "") + (int)paramDouble + ".0" + (int)(d * 100.0D);
  }

  private String saveTarget(PAir paramPAir) {
    if (!Actor.isValid(paramPAir.getTarget())) return "";
    if ((paramPAir.getTarget() instanceof PPoint)) {
      PPoint localPPoint = (PPoint)paramPAir.getTarget();
      Path localPath = (Path)localPPoint.getOwner();
      return " " + localPath.name() + " " + localPath.pointIndx(localPPoint);
    }
    return " " + paramPAir.getTarget().name() + " 0";
  }

  private String saveRadioSilence(PAir paramPAir)
  {
    return " " + (paramPAir.bRadioSilence ? "&1" : "&0");
  }

  private void clampSpeed(PAir paramPAir) {
    PathAir localPathAir = (PathAir)paramPAir.getOwner();
    if ((paramPAir.type() == 0) || (paramPAir.type() == 3)) {
      if (paramPAir.speed < this.type[localPathAir._iType].item[localPathAir._iItem].speedMin)
        paramPAir.speed = this.type[localPathAir._iType].item[localPathAir._iItem].speedMin;
      if (paramPAir.speed > this.type[localPathAir._iType].item[localPathAir._iItem].speedMax)
        paramPAir.speed = this.type[localPathAir._iType].item[localPathAir._iItem].speedMax;
    } else {
      paramPAir.speed = 0.0D;
    }
  }

  private void clampSpeed(PathAir paramPathAir) {
    int i = paramPathAir.points();
    for (int j = 0; j < i; j++)
      clampSpeed((PAir)paramPathAir.point(j));
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    PathAir localPathAir = null;
    try
    {
      Point3d localPoint3d = paramLoc.getPoint();
      int i = Plugin.builder.wSelect.comboBox1.getSelected();
      int j = Plugin.builder.wSelect.comboBox2.getSelected();
      Object localObject;
      if (Plugin.builder.selectedPath() != null) {
        localObject = Plugin.builder.selectedPath();
        if (!(localObject instanceof PathAir))
          return;
        localPathAir = (PathAir)localObject;
        if ((i - this.startComboBox1 != localPathAir._iType) || (j != localPathAir._iItem))
        {
          Plugin.builder.setSelected(null);
        }
      }
      PAir localPAir;
      if (Plugin.builder.selectedPoint() != null) {
        localObject = (PAir)Plugin.builder.selectedPoint();
        if (((PAir)localObject).type() == 2)
          return;
        localPAir = new PAir(Plugin.builder.selectedPath(), Plugin.builder.selectedPoint(), localPoint3d, 0, this.defaultHeight, this.defaultSpeed);
      } else {
        if ((i < this.startComboBox1) || (i >= this.startComboBox1 + this.type.length))
          return;
        i -= this.startComboBox1;
        if ((j < 0) || (j >= this.type[i].item.length)) {
          return;
        }
        localPathAir = new PathAir(Plugin.builder.pathes, i, j);
        localPathAir.setArmy(this.type[i].item[j].army);
        this.defaultArmy[this.type[i].item[j].army].load(localPathAir);

        if (!searchEnabledSlots(localPathAir)) {
          localPathAir.destroy();
          return;
        }
        localPathAir.setName(localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing);
        Property.set(localPathAir, "builderPlugin", this);
        localPathAir.drawing(this.viewMap.containsKey(i));
        localPAir = new PAir(localPathAir, null, localPoint3d, 0, this.defaultHeight, this.defaultSpeed);
      }
      clampSpeed((PAir)localPAir);
      Plugin.builder.setSelected(localPAir);
      PlMission.setChanged();
    } catch (Exception localException) {
      if ((localPathAir != null) && (localPathAir.points() == 0))
        localPathAir.destroy();
      System.out.println(localException);
    }
  }

  public void changeType() {
    int i = Plugin.builder.wSelect.comboBox1.getSelected() - this.startComboBox1;
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (i != localPathAir._iType) return;
    localPathAir.skins = new String[4];
    localPathAir.noseart = new String[4];
    localPathAir._iItem = j;
    Class localClass = this.type[i].item[j].clazz;
    String[] arrayOfString = Aircraft.getWeaponsRegistered(localClass);
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      int k = 0;
      for (; k < arrayOfString.length; k++) {
        String str = arrayOfString[k];
        if (str.equalsIgnoreCase(localPathAir.weapons))
          break;
      }
      if (k == arrayOfString.length)
        localPathAir.weapons = arrayOfString[0];
    }
    clampSpeed(localPathAir);
    fillDialogWay();
    fillSkins();
    fillNoseart();
    syncSkins();
    syncNoseart();
    syncPilots();
    resetMesh();
    PlMission.setChanged();
  }

  public void configure() {
    if (Plugin.getPlugin("Mission") == null)
      throw new RuntimeException("PlMisAir: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)Plugin.getPlugin("Mission"));
    if (this.jdField_sectFile_of_type_JavaLangString == null)
      throw new RuntimeException("PlMisAir: field 'sectFile' not defined");
    SectFile localSectFile = new SectFile(this.jdField_sectFile_of_type_JavaLangString, 0);
    int i = localSectFile.sections();
    if (i <= 0)
      throw new RuntimeException("PlMisAir: file '" + this.jdField_sectFile_of_type_JavaLangString + "' is empty");
    this.type = new Type[i];
    for (int j = 0; j < i; j++) {
      String str1 = localSectFile.sectionName(j);
      int k = localSectFile.vars(j);
      Item[] arrayOfItem = new Item[k];
      for (int m = 0; m < k; m++) {
        String str2 = localSectFile.var(j, m);
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.value(j, m));
        String str3 = localNumberTokenizer.next((String)null);
        int n = localNumberTokenizer.next(1, 1, Builder.armyAmount() - 1);
        Class localClass = null;
        try {
          localClass = ObjIO.classForName(str3);
        } catch (Exception localException) {
          throw new RuntimeException("PlMisAir: class '" + str3 + "' not found");
        }
        arrayOfItem[m] = new Item(str2, localClass, n);
      }
      this.type[j] = new Type(str1, arrayOfItem);
    }
  }

  void viewUpdate()
  {
    if (Plugin.builder.pathes == null)
      return;
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    Object localObject;
    PathAir localPathAir;
    for (int i = 0; i < arrayOfObject.length; i++) {
      localObject = (Actor)arrayOfObject[i];
      if (localObject == null) break;
      if ((localObject instanceof PathAir)) {
        localPathAir = (PathAir)localObject;
        localPathAir.drawing(this.viewMap.containsKey(localPathAir._iType));
      }
    }
    if (Plugin.builder.selectedPath() != null) {
      localObject = Plugin.builder.selectedPath();
      if ((localObject instanceof PathAir)) {
        localPathAir = (PathAir)localObject;
        if (!this.viewMap.containsKey(localPathAir._iType)) {
          Plugin.builder.setSelected(null);
        }
      }
    }
    if (!Plugin.builder.isFreeView())
      Plugin.builder.repaint();
  }

  void viewType(int paramInt, boolean paramBoolean) {
    if (paramBoolean) this.viewMap.put(paramInt, null); else
      this.viewMap.remove(paramInt);
    viewUpdate();
  }
  void viewType(int paramInt) {
    viewType(paramInt, this.viewType[paramInt].jdField_bChecked_of_type_Boolean);
  }
  public void viewTypeAll(boolean paramBoolean) {
    for (int i = 0; i < this.type.length; i++)
      if (this.viewType[i].jdField_bChecked_of_type_Boolean != paramBoolean) {
        this.viewType[i].jdField_bChecked_of_type_Boolean = paramBoolean;
        viewType(i, paramBoolean);
      }
  }

  private void fillComboBox1()
  {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    for (int i = 0; i < this.type.length; i++) {
      Plugin.builder.wSelect.comboBox1.add(I18N.technic(this.type[i].name));
    }
    if (this.startComboBox1 == 0)
      Plugin.builder.wSelect.comboBox1.setSelected(0, true, false); 
  }

  private void fillComboBox2(int paramInt1, int paramInt2) {
    if ((paramInt1 < this.startComboBox1) || (paramInt1 >= this.startComboBox1 + this.type.length)) {
      return;
    }
    if (Plugin.builder.wSelect.curFilledType != paramInt1) {
      Plugin.builder.wSelect.curFilledType = paramInt1;
      Plugin.builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.type[(paramInt1 - this.startComboBox1)].item.length; i++) {
        Plugin.builder.wSelect.comboBox2.add(I18N.plane(this.type[(paramInt1 - this.startComboBox1)].item[i].name));
      }
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, false);

    Class localClass = this.type[paramInt1].item[paramInt2].clazz;
    Plugin.builder.wSelect.setMesh(Aircraft.getPropertyMesh(localClass, currentCountry()), false);
  }

  public String[] actorInfo(Actor paramActor)
  {
    PathAir localPathAir = (PathAir)paramActor.getOwner();
    this._actorInfo[0] = (I18N.technic(this.type[localPathAir._iType].name) + "." + I18N.plane(this.type[localPathAir._iType].item[localPathAir._iItem].name) + ":" + localPathAir.typedName);

    PAir localPAir = (PAir)paramActor;
    int i = localPathAir.pointIndx(localPAir);
    this._actorInfo[1] = ("(" + i + ") " + Plugin.timeSecToString(localPAir.jdField_time_of_type_Double + (int)(World.getTimeofDay() * 60.0F * 60.0F)));
    return this._actorInfo;
  }

  public void syncSelector()
  {
    Plugin.builder.wSelect.tabsClient.addTab(1, this.tabActor);
    fillEditActor();
    PathAir localPathAir1 = (PathAir)Plugin.builder.selectedPath();
    fillComboBox2(localPathAir1._iType + this.startComboBox1, localPathAir1._iItem);
    Plugin.builder.wSelect.tabsClient.addTab(2, this.tabWay);
    fillDialogWay();
    PathAir localPathAir2 = (PathAir)Plugin.builder.selectedPath();
    for (int i = 0; i < localPathAir2.planes; i++) {
      Plugin.builder.wSelect.tabsClient.addTab(i + 3, this.tabSkin[i]);
      fillEditSkin(i);
    }
    fillSkins();
    fillNoseart();
    fillPilots();
    syncSkins();
    syncNoseart();
    syncPilots();
    resetMesh();
  }

  public void updateSelector() {
    fillDialogWay();
  }

  public void updateSelectorMesh() {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    PaintScheme localPaintScheme = Aircraft.getPropertyPaintScheme(this.type[localPathAir._iType].item[localPathAir._iItem].clazz, localPathAir.regiment().country());

    if (localPaintScheme != null) {
      HierMesh localHierMesh = Plugin.builder.wSelect.getHierMesh();
      if (localHierMesh != null)
        localPaintScheme.prepare(this.type[localPathAir._iType].item[localPathAir._iItem].clazz, localHierMesh, localPathAir.regiment(), localPathAir.iSquadron, localPathAir.iWing, 0);
    }
  }

  public void createGUI() {
    fillComboBox1();
    fillComboBox2(0, 0);
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisAir.this.fillComboBox2(i, 0);
        return false;
      }
    });
    Plugin.builder.wSelect.comboBox2.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 2)
          return false;
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i < PlMisAir.this.startComboBox1) || (i >= PlMisAir.this.startComboBox1 + PlMisAir.this.type.length))
          return false;
        int j = Plugin.builder.wSelect.comboBox2.getSelected();
        Class localClass = PlMisAir.this.type[(i - PlMisAir.this.startComboBox1)].item[j].clazz;
        Plugin.builder.wSelect.setMesh(Aircraft.getPropertyMesh(localClass, PlMisAir.this.currentCountry()), false);

        PlMisAir.this.resetMesh();
        PlMisAir.this.fillSkins();
        PlMisAir.this.fillNoseart();
        PlMisAir.this.fillPilots();
        PlMisAir.this.syncSkins();
        PlMisAir.this.syncNoseart();
        PlMisAir.this.syncPilots();

        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        if (localPathAir != null)
          localPathAir.updateTypedName();
        PlMisAir.this.fillEditActor();
        return false;
      }
    });
    int i = Plugin.builder.mDisplayFilter.subMenu.size() - 1;
    while (i >= 0) {
      if (this.pluginMission.viewBridge == Plugin.builder.mDisplayFilter.subMenu.getItem(i))
        break;
      i--;
    }
    i--;
    if (i >= 0) {
      int j = i;
      i = this.type.length - 1;
      this.viewType = new ViewItem[this.type.length];
      while (i >= 0) {
        ViewItem localViewItem = null;
        if ("de".equals(RTSConf.cur.locale.getLanguage())) {
          localViewItem = (ViewItem)Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, Plugin.builder.mDisplayFilter.subMenu, I18N.technic(this.type[i].name) + " " + Plugin.i18n("show"), null));
        }
        else {
          localViewItem = (ViewItem)Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("show") + " " + I18N.technic(this.type[i].name), null));
        }
        localViewItem.jdField_bChecked_of_type_Boolean = true;
        this.viewType[i] = localViewItem;
        viewType(i, true);
        i--;
      }
    }

    initEditActor();
    initEditWay();
    initEditSkin();
  }

  private boolean searchEnabledSlots(PathAir paramPathAir)
  {
    makeRegimentList(paramPathAir.getArmy());
    int i = this.regimentList.size();
    if (paramPathAir.iRegiment < 0) paramPathAir.iRegiment = 0;
    if (paramPathAir.iRegiment >= i) paramPathAir.iRegiment = (i - 1);
    for (int j = 0; j < i; j++) {
      Regiment localRegiment = (Regiment)this.regimentList.get(paramPathAir.iRegiment);
      paramPathAir.sRegiment = localRegiment.name();
      if (isEnabledRegiment(localRegiment))
        for (int k = 0; k < 4; k++) {
          ResSquadron localResSquadron = (ResSquadron)Actor.getByName(localRegiment.name() + paramPathAir.iSquadron);

          if (localResSquadron == null) {
            this.defaultArmy[paramPathAir.getArmy()].save(paramPathAir);
            return true;
          }
          if (isEnabledSquad(localResSquadron)) {
            for (int m = 0; m < 4; m++) {
              if (Actor.getByName(localRegiment.name() + paramPathAir.iSquadron + paramPathAir.iWing) == null) {
                this.defaultArmy[paramPathAir.getArmy()].save(paramPathAir);
                return true;
              }
              paramPathAir.iWing = ((paramPathAir.iWing + 1) % 4);
            }
          }
          paramPathAir.iWing = 0;
          paramPathAir.iSquadron = ((paramPathAir.iSquadron + 1) % 4);
        }
      else {
        paramPathAir.iWing = 0;
      }
      paramPathAir.iSquadron = 0;
      paramPathAir.iRegiment = ((paramPathAir.iRegiment + 1) % i);
    }
    return false;
  }

  private void fillEnabledRegiments(int paramInt) {
    int i = this.regimentList.size();
    for (int j = 0; j < i; j++)
      if (j == paramInt) {
        this.wRegiment.posEnable[j] = true;
      } else {
        Regiment localRegiment = (Regiment)this.regimentList.get(j);
        this.wRegiment.posEnable[j] = isEnabledRegiment(localRegiment);
      }
  }

  private String currentCountry()
  {
    if (this.wRegiment == null) return "ru";
    int i = this.wRegiment.getSelected();
    if (i < 0) return "ru";
    return ((Regiment)this.regimentList.get(i)).country();
  }

  private boolean isEnabledRegiment(Regiment paramRegiment) {
    if (paramRegiment.getOwnerAttachedCount() < 4)
      return true;
    this._squads = paramRegiment.getOwnerAttached(this._squads);
    int i = 0;
    for (int j = 0; j < 4; j++) {
      ResSquadron localResSquadron = (ResSquadron)this._squads[j];
      this._squads[j] = null;
      if ((localResSquadron == null) || (isEnabledSquad(localResSquadron)))
        i = 1;
    }
    return i;
  }

  private void fillEnabledSquads(Regiment paramRegiment, int paramInt) {
    for (int i = 0; i < 4; i++)
      this.wSquadron.posEnable[i] = true;
    this._squads = paramRegiment.getOwnerAttached(this._squads);
    for (int j = 0; j < 4; j++) {
      ResSquadron localResSquadron = (ResSquadron)this._squads[j];
      if (localResSquadron == null) break;
      this._squads[j] = null;
      if (localResSquadron.index() != paramInt)
        this.wSquadron.posEnable[localResSquadron.index()] = isEnabledSquad(localResSquadron);
    }
  }

  private boolean isEnabledSquad(ResSquadron paramResSquadron)
  {
    return paramResSquadron.getAttachedCount() < 4;
  }

  private void fillEnabledWings(ResSquadron paramResSquadron, int paramInt) {
    for (int i = 0; i < 4; i++)
      this.wWing.posEnable[i] = true;
    this._wings = paramResSquadron.getAttached(this._wings);
    for (int j = 0; j < 4; j++) {
      PathAir localPathAir = (PathAir)this._wings[j];
      if (localPathAir == null) break;
      this._wings[j] = null;
      if (localPathAir.iWing != paramInt)
        this.wWing.posEnable[localPathAir.iWing] = false;
    }
  }

  private void controlResized(GWindowDialogClient paramGWindowDialogClient, GWindow paramGWindow)
  {
    if (paramGWindow == null) return;
    paramGWindow.setSize(paramGWindowDialogClient.win.dx - paramGWindow.win.x - paramGWindowDialogClient.lAF().metric(1.0F), paramGWindow.win.dy);
  }
  private void editResized(GWindowDialogClient paramGWindowDialogClient) {
    controlResized(paramGWindowDialogClient, this.wArmy);
    controlResized(paramGWindowDialogClient, this.wRegiment);
    controlResized(paramGWindowDialogClient, this.wSquadron);
    controlResized(paramGWindowDialogClient, this.wWing);
    controlResized(paramGWindowDialogClient, this.wWeapons);
    controlResized(paramGWindowDialogClient, this.wFuel);
    controlResized(paramGWindowDialogClient, this.wPlanes);
    controlResized(paramGWindowDialogClient, this.wSkill);
  }

  public void initEditActor() {
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient() {
      public void resized() { super.resized(); PlMisAir.this.editResized(this);
      }
    });
    this.tabActor = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("AircraftActor"), localGWindowDialogClient);

    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 7.0F, 1.3F, Plugin.i18n("Army"), null));
    localGWindowDialogClient.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient, 9.0F, 1.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        for (int i = 1; i < Builder.armyAmount(); i++)
          add(I18N.army(Army.name(i))); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        int i = getSelected() + 1;
        int j = localPathAir.getArmy();
        int k = localPathAir.iRegiment;
        int m = localPathAir.iSquadron;
        int n = localPathAir.iWing;
        localPathAir.setArmy(i);
        PlMisAir.this.defaultArmy[i].load(localPathAir);
        if (!PlMisAir.this.searchEnabledSlots(localPathAir)) {
          localPathAir.setArmy(j);
          localPathAir.iRegiment = k;
          localPathAir.iSquadron = m;
          localPathAir.iWing = n;
          PlMisAir.this.searchEnabledSlots(localPathAir);
        }
        localPathAir.setName(localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing);
        PlMisAir.this.fillEditActor();
        PlMisAir.this.fillNoseart();
        PlMisAir.this.syncNoseart();
        Class localClass = PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].clazz;
        Plugin.builder.wSelect.setMesh(Aircraft.getPropertyMesh(localClass, PlMisAir.this.currentCountry()), false);
        PlMisAir.this.resetMesh();

        if (Path.player == localPathAir)
          PlMission.cur.missionArmy = i;
        PlMission.setChanged();
        PlMission.checkShowCurrentArmy();
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wRegiment = new GWindowComboControl(localGWindowDialogClient, 1.0F, 3.0F, 15.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.iRegiment = getSelected();
        PlMisAir.this.searchEnabledSlots(localPathAir);
        localPathAir.setName(localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing);
        PlMisAir.this.defaultArmy[localPathAir.getArmy()].save(localPathAir);
        PlMisAir.this.fillEditActor();
        PlMisAir.this.fillNoseart();
        PlMisAir.this.syncNoseart();
        Class localClass = PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].clazz;
        Plugin.builder.wSelect.setMesh(Aircraft.getPropertyMesh(localClass, PlMisAir.this.currentCountry()), false);
        PlMisAir.this.resetMesh();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("Squadron"), null));
    localGWindowDialogClient.addControl(this.wSquadron = new GWindowComboControl(localGWindowDialogClient, 9.0F, 5.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("1");
        add("2");
        add("3");
        add("4");
        this.posEnable = new boolean[4]; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.iSquadron = getSelected();
        PlMisAir.this.searchEnabledSlots(localPathAir);
        localPathAir.setName(localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing);
        PlMisAir.this.defaultArmy[localPathAir.getArmy()].save(localPathAir);
        PlMisAir.this.fillEditActor();
        PlMisAir.this.resetMesh();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("Wing"), null));
    localGWindowDialogClient.addControl(this.wWing = new GWindowComboControl(localGWindowDialogClient, 9.0F, 7.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("1");
        add("2");
        add("3");
        add("4");
        this.posEnable = new boolean[4]; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.iWing = getSelected();
        localPathAir.setName(localPathAir.sRegiment + localPathAir.iSquadron + localPathAir.iWing);
        PlMisAir.this.defaultArmy[localPathAir.getArmy()].save(localPathAir);
        PlMisAir.this.fillEditActor();
        PlMisAir.this.resetMesh();
        PlMission.setChanged();
        return false;
      }
    });
    this.lWeapons = new ArrayList();
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 7.0F, 1.3F, Plugin.i18n("Weapons"), null));
    localGWindowDialogClient.addControl(this.wWeapons = new GWindowComboControl(localGWindowDialogClient, 9.0F, 9.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        int i = getSelected();
        if (i < 0)
          localPathAir.weapons = null;
        else {
          localPathAir.weapons = ((String)PlMisAir.this.lWeapons.get(i));
        }
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 11.0F, 7.0F, 1.3F, Plugin.i18n("Fuel"), null));
    localGWindowDialogClient.addControl(this.wFuel = new GWindowEditControl(localGWindowDialogClient, 9.0F, 11.0F, 7.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bDelayedNotify = true;
        this.bNumericOnly = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        String str = getValue();
        int i = 0;
        if (str != null) try {
            i = Integer.parseInt(str);
          } catch (Exception localException) {
            setValue("" + ((PathAir)Plugin.builder.selectedPath()).fuel, false);
            return false;
          }

        if (i < 0)
          i = 0;
        else if (i > 100) {
          i = 100;
        }
        localPathAir.fuel = i;
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 13.0F, 7.0F, 1.3F, Plugin.i18n("Planes"), null));
    localGWindowDialogClient.addControl(this.wPlanes = new GWindowComboControl(localGWindowDialogClient, 9.0F, 13.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("1");
        add("2");
        add("3");
        add("4"); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.planes = (getSelected() + 1);
        PlMisAir.this.checkEditSkinTabs();
        PlMisAir.this.checkEditSkinSkills();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 15.0F, 7.0F, 1.3F, Plugin.i18n("Skill"), null));
    localGWindowDialogClient.addControl(this.wSkill = new GWindowComboControl(localGWindowDialogClient, 9.0F, 15.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add(Plugin.i18n("Rookie"));
        add(Plugin.i18n("Average"));
        add(Plugin.i18n("Veteran"));
        add(Plugin.i18n("Ace")); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.skill = getSelected();
        for (int i = 0; i < 4; i++)
          localPathAir.skills[i] = localPathAir.skill;
        PlMisAir.this.checkEditSkinSkills();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 17.0F, 7.0F, 1.3F, Plugin.i18n("OnlyAI"), null));
    localGWindowDialogClient.addControl(this.wOnlyAI = new GWindowCheckBox(localGWindowDialogClient, 9.0F, 17.0F, null) {
      public void preRender() {
        super.preRender();
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        if (localPathAir == null) return;
        setChecked(localPathAir.bOnlyAI, false);
        setEnable(PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].bEnablePlayer);
      }
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.bOnlyAI = isChecked();
        if ((isChecked()) && 
          (Path.player == localPathAir)) {
          Path.player = null;
          Path.playerNum = 0;
        }

        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 19.0F, 7.0F, 1.3F, Plugin.i18n("Parachute"), null));
    localGWindowDialogClient.addControl(this.wParachute = new GWindowCheckBox(localGWindowDialogClient, 9.0F, 19.0F, null) {
      public void preRender() {
        super.preRender();
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        if (localPathAir == null) return;
        setChecked(localPathAir.bParachute, false);
      }
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        localPathAir.bParachute = isChecked();
        PlMission.setChanged();
        return false;
      } } );
  }

  private void fillEditEnabled(PathAir paramPathAir) {
    makeRegimentList(paramPathAir.getArmy());
    fillEnabledRegiments(paramPathAir.iRegiment);
    fillEnabledSquads(paramPathAir.regiment(), paramPathAir.iSquadron);
    fillEnabledWings(paramPathAir.squadron(), paramPathAir.iWing);
    this.defaultArmy[paramPathAir.getArmy()].save(paramPathAir);
  }

  public void fillEditActor() {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    fillEditEnabled(localPathAir);
    this.wRegiment.setSelected(localPathAir.iRegiment, true, false);
    this.wSquadron.setSelected(localPathAir.iSquadron, true, false);
    this.wWing.setSelected(localPathAir.iWing, true, false);
    this.wArmy.setSelected(localPathAir.getArmy() - 1, true, false);
    this.wFuel.setValue("" + localPathAir.fuel, false);
    this.wPlanes.setSelected(localPathAir.planes - 1, true, false);
    if (localPathAir.skill != -1)
      this.wSkill.setSelected(localPathAir.skill, true, false);
    else {
      this.wSkill.setValue(Plugin.i18n("Custom"));
    }
    this.wWeapons.clear(false);
    this.lWeapons.clear();
    Class localClass = this.type[localPathAir._iType].item[localPathAir._iItem].clazz;
    String[] arrayOfString = Aircraft.getWeaponsRegistered(localClass);
    String str1 = this.type[localPathAir._iType].item[localPathAir._iItem].name;
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      int i = -1;
      for (int j = 0; j < arrayOfString.length; j++) {
        String str2 = arrayOfString[j];
        if (str2.equalsIgnoreCase(localPathAir.weapons))
          i = j;
        this.wWeapons.add(I18N.weapons(str1, str2));
        this.lWeapons.add(str2);
      }
      if (i == -1)
        i = 0;
      this.wWeapons.setSelected(i, true, false);
    }
  }

  private void fillDialogWay()
  {
    PAir localPAir = (PAir)Plugin.builder.selectedPoint();
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    int i = localPathAir.pointIndx(localPAir);
    this.wPrev.setEnable(i > 0);
    this.wNext.setEnable(i < localPathAir.points() - 1);
    this.wCur.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption("" + i + "(" + localPathAir.points() + ")");
    this.wHeight.setValue("" + (int)localPAir.height, false);
    this.wSpeed.setValue("" + (int)localPAir.speed, false);
    this.wType.setSelected(localPAir.type(), true, false);
    for (int j = 0; j < PAir.types.length; j++)
      this.wType.posEnable[j] = true;
    if (i > 0) this.wType.posEnable[1] = false;
    if (i < localPathAir.points() - 1) this.wType.posEnable[2] = false;
    this._curPointType = localPAir.type();

    int k = (int)Math.round(localPAir.jdField_time_of_type_Double / 60.0D + World.getTimeofDay() * 60.0F);
    this.wTimeH.setValue("" + k / 60 % 24, false);
    this.wTimeM.setValue("" + k % 60, false);
    if (localPAir.getTarget() != null) {
      if ((localPAir.getTarget() instanceof PPoint)) {
        if ((localPAir.getTarget() instanceof PAir))
          this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set(((PathAir)localPAir.getTarget().getOwner()).typedName);
        else if ((localPAir.getTarget() instanceof PNodes))
          this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set(Property.stringValue(localPAir.getTarget().getOwner(), "i18nName", ""));
        else
          this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set(localPAir.getTarget().getOwner().name());
      }
      else this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set(Property.stringValue(localPAir.getTarget().getClass(), "i18nName", ""));
    }
    else
      this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set("");
  }

  public void initEditWay()
  {
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabWay = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("Waypoint"), localGWindowDialogClient);

    localGWindowDialogClient.addControl(this.wPrev = new GWindowButton(localGWindowDialogClient, 1.0F, 1.0F, 5.0F, 1.6F, Plugin.i18n("&Prev"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          PAir localPAir = (PAir)Plugin.builder.selectedPoint();
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          int i = localPathAir.pointIndx(localPAir);
          if (i > 0) {
            Plugin.builder.setSelected(localPathAir.point(i - 1));
            PlMisAir.this.fillDialogWay();
            Plugin.builder.repaint();
          }
          return true;
        }
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wNext = new GWindowButton(localGWindowDialogClient, 9.0F, 1.0F, 5.0F, 1.6F, Plugin.i18n("&Next"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          PAir localPAir = (PAir)Plugin.builder.selectedPoint();
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          int i = localPathAir.pointIndx(localPAir);
          if (i < localPathAir.points() - 1) {
            Plugin.builder.setSelected(localPathAir.point(i + 1));
            PlMisAir.this.fillDialogWay();
            Plugin.builder.repaint();
          }
          return true;
        }
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wCur = new GWindowLabel(localGWindowDialogClient, 15.0F, 1.0F, 4.0F, 1.6F, "1(1)", null));

    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 7.0F, 1.3F, Plugin.i18n("Height"), null));
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 15.0F, 3.0F, 4.0F, 1.3F, Plugin.i18n("[M]"), null));
    localGWindowDialogClient.addControl(this.wHeight = new GWindowEditControl(localGWindowDialogClient, 9.0F, 3.0F, 5.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PAir localPAir = (PAir)Plugin.builder.selectedPoint();
        String str = getValue();
        double d = 0.0D;
        try { d = Double.parseDouble(str);
        } catch (Exception localException) {
          setValue("" + ((PAir)Plugin.builder.selectedPoint()).height, false);
          return false;
        }
        if (d < 0.0D)
          d = 0.0D;
        else if (d > 12000.0D) {
          d = 12000.0D;
        }
        localPAir.height = d;
        PlMisAir.this.defaultHeight = d;
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("Speed"), null));
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 15.0F, 5.0F, 4.0F, 1.3F, Plugin.i18n("[kM/H]"), null));
    localGWindowDialogClient.addControl(this.wSpeed = new GWindowEditControl(localGWindowDialogClient, 9.0F, 5.0F, 5.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PAir localPAir = (PAir)Plugin.builder.selectedPoint();
        String str = getValue();
        double d = 0.0D;
        try { d = Double.parseDouble(str);
        } catch (Exception localException) {
          setValue("" + ((PAir)Plugin.builder.selectedPoint()).speed, false);
          return false;
        }
        PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
        if ((localPAir.type() == 1) || (localPAir.type() == 2)) {
          d = 0.0D;
        } else if (d < PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].speedMin) {
          d = PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].speedMin;
          PlMisAir.this.defaultSpeed = d;
        } else if (d > PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].speedMax) {
          d = PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].speedMax;
          PlMisAir.this.defaultSpeed = d;
        } else {
          PlMisAir.this.defaultSpeed = d;
        }
        localPAir.speed = d;
        localPathAir.computeTimes();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("Time"), null));
    localGWindowDialogClient.addControl(this.wTimeH = new GWindowEditControl(localGWindowDialogClient, 9.0F, 7.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisAir.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 11.2F, 7.0F, 1.0F, 1.3F, ":", null));
    localGWindowDialogClient.addControl(this.wTimeM = new GWindowEditControl(localGWindowDialogClient, 11.5F, 7.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisAir.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 7.0F, 1.3F, Plugin.i18n("lType"), null));
    localGWindowDialogClient.addControl(this.wType = new GWindowComboControl(localGWindowDialogClient, 9.0F, 9.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        for (int i = 0; i < PAir.types.length; i++)
          add(Plugin.i18n(PAir.types[i]));
        boolean[] arrayOfBoolean = new boolean[PAir.types.length];
        for (int j = 0; j < PAir.types.length; j++)
          arrayOfBoolean[j] = true;
        this.posEnable = arrayOfBoolean; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          if (PlMisAir.this._curPointType != this.jdField_iSelected_of_type_Int) {
            PAir localPAir = (PAir)Plugin.builder.selectedPoint();
            int i = 1;
            if ((PlMisAir.this._curPointType == 1) || (PlMisAir.this._curPointType == 2))
              i = 0;
            int j = 1;
            if ((this.jdField_iSelected_of_type_Int == 1) || (this.jdField_iSelected_of_type_Int == 2))
              j = 0;
            if (i != j) {
              if (j != 0) {
                localPAir.height = PlMisAir.this.defaultHeight;
                localPAir.speed = PlMisAir.this.defaultSpeed;
                PlMisAir.this.clampSpeed(localPAir);
                PlMisAir.this.wHeight.setValue("" + (int)PlMisAir.this.defaultHeight, false);
                PlMisAir.this.wSpeed.setValue("" + (int)localPAir.speed, false);
              } else {
                PlMisAir.this.wHeight.setValue("0", false);
                PlMisAir.this.wSpeed.setValue("0", false);
                localPAir.height = 0.0D;
                localPAir.speed = 0.0D;
              }
            }
            PlMisAir.access$1702(PlMisAir.this, this.jdField_iSelected_of_type_Int);
            localPAir.setType(this.jdField_iSelected_of_type_Int);
            PlMission.setChanged();

            if ((this.jdField_iSelected_of_type_Int == 1) || (this.jdField_iSelected_of_type_Int == 2)) {
              localObject = Airport.nearest(localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), -1, 7);
              if (localObject != null) {
                if (((Airport)localObject).nearestRunway(localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), PlMisAir.nearestRunway))
                  localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(PlMisAir.nearestRunway.getPoint());
                else
                  localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(((Airport)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
              }
              localPAir.setTarget(null);
              localPAir.sTarget = null;
              PlMisAir.this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set("");
            } else {
              localObject = localPAir.getTarget();
              if ((localObject != null) && (
                ((this.jdField_iSelected_of_type_Int == 0) && (!(localObject instanceof PAir))) || ((this.jdField_iSelected_of_type_Int == 3) && ((localObject instanceof PAir)))))
              {
                localPAir.setTarget(null);
                localPAir.sTarget = null;
                PlMisAir.this.wTarget.jdField_cap_of_type_ComMaddoxGwindowGCaption.set("");
              }
            }

            Object localObject = (PathAir)Plugin.builder.selectedPath();
            ((PathAir)localObject).computeTimes();
          }
          return true;
        }
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 11.0F, 7.0F, 1.3F, Plugin.i18n("RadioSilence"), null));
    localGWindowDialogClient.addControl(this.wRadioSilence = new GWindowCheckBox(localGWindowDialogClient, 9.0F, 11.0F, null) {
      public void preRender() {
        super.preRender();
        PAir localPAir = (PAir)Plugin.builder.selectedPoint();
        if (localPAir == null) return;
        setChecked(localPAir.bRadioSilence, false);
      }
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PAir localPAir = (PAir)Plugin.builder.selectedPoint();
        localPAir.bRadioSilence = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 13.0F, 7.0F, 1.3F, Plugin.i18n("Target"), null));
    localGWindowDialogClient.addLabel(this.wTarget = new GWindowLabel(localGWindowDialogClient, 9.0F, 13.0F, 7.0F, 1.3F, "", null));
    localGWindowDialogClient.addControl(this.wTargetSet = new GWindowButton(localGWindowDialogClient, 1.0F, 15.0F, 5.0F, 1.6F, Plugin.i18n("&Set"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          Plugin.builder.beginSelectTarget();
        }
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wTargetClear = new GWindowButton(localGWindowDialogClient, 9.0F, 15.0F, 5.0F, 1.6F, Plugin.i18n("&Clear"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          PAir localPAir = (PAir)Plugin.builder.selectedPoint();
          localPAir.setTarget(null);
          localPAir.sTarget = null;
          PlMisAir.this.wTarget.cap.set("");
          PlMission.setChanged();
        }
        return false;
      }
    });
  }

  private void getTimeOut() {
    PAir localPAir = (PAir)Plugin.builder.selectedPoint();
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    String str = this.wTimeH.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 23.0D) d1 = 23.0D;
    str = this.wTimeM.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 59.0D) d2 = 59.0D;
    double d3 = (d1 * 60.0D + d2) * 60.0D - Math.round(World.getTimeofDay() * 60.0F * 60.0F);
    if (d3 < 0.0D) d3 = 0.0D;
    int i = localPathAir.pointIndx(localPAir);
    if (i == 0) {
      if (localPathAir == Path.player)
        localPAir.jdField_time_of_type_Double = 0.0D;
      else
        localPAir.jdField_time_of_type_Double = d3;
    } else if (localPAir.type() != 2) {
      PPoint localPPoint = localPathAir.point(i - 1);
      double d4 = d3 - localPPoint.jdField_time_of_type_Double;
      double d5 = 0.0D;
      if (d4 <= 0.0D) {
        d5 = this.type[localPathAir._iType].item[localPathAir._iItem].speedMax;
      } else {
        double d6 = localPPoint.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().distance(localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
        d5 = d6 / d4 * 3600.0D / 1000.0D;
        if (d5 > this.type[localPathAir._iType].item[localPathAir._iItem].speedMax)
          d5 = this.type[localPathAir._iType].item[localPathAir._iItem].speedMax;
        if (d5 < this.type[localPathAir._iType].item[localPathAir._iItem].speedMin)
          d5 = this.type[localPathAir._iType].item[localPathAir._iItem].speedMin;
      }
      localPAir.speed = d5;
    }
    localPathAir.computeTimes();
    PlMission.setChanged();
  }

  private void fillEditSkin(int paramInt)
  {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    this.wSkills[paramInt].setSelected(localPathAir.skills[paramInt], true, false);
  }
  private void fillSkins() {
    for (int i = 0; i < 4; i++) {
      this.wSkins[i].clear(false);
      this.wSkins[i].add(Plugin.i18n("Default"));
    }
    try {
      String str1 = Main.cur().netFileServerSkin.primaryPath();
      int j = Plugin.builder.wSelect.comboBox1.getSelected();
      if ((j < this.startComboBox1) || (j >= this.startComboBox1 + this.type.length))
        return;
      j -= this.startComboBox1;
      int k = Plugin.builder.wSelect.comboBox2.getSelected();
      String str2 = GUIAirArming.validateFileName(this.type[j].item[k].name);
      File localFile1 = new File(HomePath.toFileSystemName(str1 + "/" + str2, 0));

      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
        for (int m = 0; m < arrayOfFile.length; m++) {
          File localFile2 = arrayOfFile[m];
          if (localFile2.isFile()) {
            String str3 = localFile2.getName();
            String str4 = str3.toLowerCase();
            if ((!str4.endsWith(".bmp")) || 
              (str4.length() + str2.length() > 122)) continue;
            int n = BmpUtils.squareSizeBMP8Pal(str1 + "/" + str2 + "/" + str3);
            if ((n == 512) || (n == 1024))
              for (int i1 = 0; i1 < 4; i1++)
                this.wSkins[i1].add(str3);
            else
              System.out.println("Skin " + str1 + "/" + str2 + "/" + str3 + " NOT loaded");
          }
        }
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void syncSkins() {
    if (!(Plugin.builder.selectedPath() instanceof PathAir))
      return;
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    for (int i = 0; i < 4; i++)
      if (!syncComboControl(this.wSkins[i], localPathAir.skins[i]))
        localPathAir.skins[i] = null; 
  }

  private void fillNoseart() {
    for (int i = 0; i < 4; i++) {
      this.wNoseart[i].clear(false);
      this.wNoseart[i].add(Plugin.i18n("None"));
    }
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null)
      return;
    Class localClass = this.type[localPathAir._iType].item[localPathAir._iItem].clazz;
    int j = Property.intValue(localClass, "noseart", 0) != 1 ? 1 : 0;
    Object localObject;
    if (j == 0) {
      k = localPathAir.iRegiment;
      if ((k < 0) && (k >= this.regimentList.size())) {
        j = 1;
      } else {
        localObject = (Regiment)this.regimentList.get(k);
        j = !"us".equals(((Regiment)localObject).country()) ? 1 : 0;
      }
    }
    if (j != 0) {
      for (k = 0; k < 4; k++)
        this.wNoseart[k].setEnable(false);
      return;
    }
    for (int k = 0; k < 4; k++)
      this.wNoseart[k].setEnable(true);
    try
    {
      localObject = Main.cur().netFileServerNoseart.primaryPath();
      File localFile1 = new File(HomePath.toFileSystemName((String)localObject, 0));

      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
        for (int m = 0; m < arrayOfFile.length; m++) {
          File localFile2 = arrayOfFile[m];
          if (localFile2.isFile()) {
            String str1 = localFile2.getName();
            String str2 = str1.toLowerCase();
            if ((!str2.endsWith(".bmp")) || 
              (str2.length() > 122)) continue;
            if (BmpUtils.checkBMP8Pal((String)localObject + "/" + str1, 256, 512))
              for (int n = 0; n < 4; n++)
                this.wNoseart[n].add(str1);
            else
              System.out.println("Noseart " + (String)localObject + "/" + str1 + " NOT loaded");
          }
        }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void syncNoseart() {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    for (int i = 0; i < 4; i++)
      if (!syncComboControl(this.wNoseart[i], localPathAir.noseart[i]))
        localPathAir.noseart[i] = null; 
  }

  private void fillPilots() {
    for (int i = 0; i < 4; i++) {
      this.wPilots[i].clear(false);
      this.wPilots[i].add(Plugin.i18n("Default"));
    }
    try {
      String str1 = Main.cur().netFileServerPilot.primaryPath();
      File localFile1 = new File(HomePath.toFileSystemName(str1, 0));

      File[] arrayOfFile = localFile1.listFiles();
      if (arrayOfFile != null)
        for (int j = 0; j < arrayOfFile.length; j++) {
          File localFile2 = arrayOfFile[j];
          if (localFile2.isFile()) {
            String str2 = localFile2.getName();
            String str3 = str2.toLowerCase();
            if ((!str3.endsWith(".bmp")) || 
              (str3.length() > 122)) continue;
            if (BmpUtils.checkBMP8Pal(str1 + "/" + str2, 256, 256))
              for (int k = 0; k < 4; k++)
                this.wPilots[k].add(str2);
            else
              System.out.println("Pilot " + str1 + "/" + str2 + " NOT loaded");
          }
        }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void syncPilots() {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    for (int i = 0; i < 4; i++)
      if (!syncComboControl(this.wPilots[i], localPathAir.pilots[i]))
        localPathAir.pilots[i] = null; 
  }

  private boolean syncComboControl(GWindowComboControl paramGWindowComboControl, String paramString) {
    if (paramString == null) {
      paramGWindowComboControl.setSelected(0, true, false);
      return true;
    }
    int i = paramGWindowComboControl.size();
    for (int j = 1; j < i; j++) {
      String str = paramGWindowComboControl.get(j);
      if (paramString.equals(str)) {
        paramGWindowComboControl.setSelected(j, true, false);
        return true;
      }
    }
    paramGWindowComboControl.setSelected(0, true, false);
    return false;
  }
  private void checkEditSkinTabs() {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    int i = Plugin.builder.wSelect.tabsClient.sizeTabs();
    if (localPathAir.planes == i - 3)
      return;
    int j;
    if (localPathAir.planes > i - 3) {
      for (j = i - 3; j < localPathAir.planes; j++) {
        Plugin.builder.wSelect.tabsClient.addTab(j + 3, this.tabSkin[j]);
        fillEditSkin(j);
      }
    } else {
      j = Plugin.builder.wSelect.tabsClient.current;
      while (Plugin.builder.wSelect.tabsClient.sizeTabs() - 3 > localPathAir.planes)
        Plugin.builder.wSelect.tabsClient.removeTab(Plugin.builder.wSelect.tabsClient.sizeTabs() - 1);
      Plugin.builder.wSelect.tabsClient.setCurrent(j, false);
      if ((localPathAir == Path.player) && (localPathAir.planes >= Path.playerNum))
        Path.playerNum = 0; 
    }
  }

  private void checkEditSkinSkills() {
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    int i = localPathAir.skills[0];
    this.wSkills[0].setSelected(localPathAir.skills[0], true, false);
    localPathAir.skill = -2;
    for (int j = 1; j < localPathAir.planes; j++) {
      if (localPathAir.skills[j] != i) {
        localPathAir.skill = -1;
      }
      this.wSkills[j].setSelected(localPathAir.skills[j], true, false);
    }
    if (localPathAir.skill == -1) {
      this.wSkill.setValue(Plugin.i18n("Custom"));
    } else {
      localPathAir.skill = i;
      this.wSkill.setSelected(localPathAir.skill, true, false);
    }
  }

  private void resetMesh() {
    for (int i = 0; i < 4; i++)
      resetMesh(i); 
  }

  private void resetMesh(int paramInt) {
    if (Actor.isValid(this.actorMesh[paramInt])) {
      this.actorMesh[paramInt].destroy();
      this.actorMesh[paramInt] = null;
    }
  }

  private void checkMesh(int paramInt) {
    if (Actor.isValid(this.actorMesh[paramInt])) return;
    PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
    if (localPathAir == null) return;
    Class localClass = this.type[localPathAir._iType].item[localPathAir._iItem].clazz;
    this.meshName = Aircraft.getPropertyMesh(localClass, localPathAir.regiment().country());
    createMesh(paramInt);
    PaintScheme localPaintScheme = Aircraft.getPropertyPaintScheme(localClass, localPathAir.regiment().country());
    localPaintScheme.prepare(localClass, this.actorMesh[paramInt].hierMesh(), localPathAir.regiment(), localPathAir.iSquadron, localPathAir.iWing, paramInt, localPathAir.bNumberOn[paramInt]);
    prepareSkin(paramInt, localClass, localPathAir.skins[paramInt]);
    prepareNoseart(paramInt, localPathAir.noseart[paramInt]);
    preparePilot(paramInt, localPathAir.pilots[paramInt]);
  }
  private void createMesh(int paramInt) {
    if (Actor.isValid(this.actorMesh[paramInt])) return;
    if (this.meshName == null) return;
    double d = 20.0D;
    this.actorMesh[paramInt] = new ActorSimpleHMesh(this.meshName);
    d = this.actorMesh[paramInt].hierMesh().visibilityR();
    this.actorMesh[paramInt].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(new Orient(90.0F, 0.0F, 0.0F));
    d *= Math.cos(0.2617993877991494D) / Math.sin(this.camera3D[paramInt].FOV() * 3.141592653589793D / 180.0D / 2.0D);
    this.camera3D[paramInt].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(new Point3d(d, 0.0D, 0.0D), new Orient(180.0F, 0.0F, 0.0F));

    this.camera3D[paramInt].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
  }
  private void prepareSkin(int paramInt, Class paramClass, String paramString) {
    if (!Actor.isValid(this.actorMesh[paramInt])) return;
    if (paramString != null) {
      paramString = Main.cur().netFileServerSkin.primaryPath() + "/" + GUIAirArming.validateFileName(Property.stringValue(paramClass, "keyName", null)) + "/" + paramString;

      String str = "PaintSchemes/Cache/" + Finger.file(0L, paramString, -1);
      Aircraft.prepareMeshSkin(this.meshName, this.actorMesh[paramInt].hierMesh(), paramString, str);
    } else {
      Aircraft.prepareMeshCamouflage(this.meshName, this.actorMesh[paramInt].hierMesh());
    }
  }

  private void prepareNoseart(int paramInt, String paramString) {
    if (!Actor.isValid(this.actorMesh[paramInt])) return;
    if (paramString != null) {
      String str1 = Main.cur().netFileServerNoseart.primaryPath() + "/" + paramString;
      String str2 = paramString.substring(0, paramString.length() - 4);
      String str3 = "PaintSchemes/Cache/Noseart0" + str2 + ".tga";
      String str4 = "PaintSchemes/Cache/Noseart0" + str2 + ".mat";
      String str5 = "PaintSchemes/Cache/Noseart1" + str2 + ".tga";
      String str6 = "PaintSchemes/Cache/Noseart1" + str2 + ".mat";
      if (BmpUtils.bmp8PalTo2TGA4(str1, str3, str5))
        Aircraft.prepareMeshNoseart(this.actorMesh[paramInt].hierMesh(), str4, str6, str3, str5, null); 
    }
  }

  private void preparePilot(int paramInt, String paramString) {
    if (!Actor.isValid(this.actorMesh[paramInt])) return;
    if (paramString != null) {
      String str1 = Main.cur().netFileServerPilot.primaryPath() + "/" + paramString;
      String str2 = paramString.substring(0, paramString.length() - 4);
      String str3 = "PaintSchemes/Cache/Pilot" + str2 + ".tga";
      String str4 = "PaintSchemes/Cache/Pilot" + str2 + ".mat";
      if (BmpUtils.bmp8PalToTGA3(str1, str3))
        Aircraft.prepareMeshPilot(this.actorMesh[paramInt].hierMesh(), 0, str4, str3, null);
    }
  }

  private void initEditSkin()
  {
    for (this._planeIndx = 0; this._planeIndx < 4; this._planeIndx += 1) {
      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient() {
        int planeIndx = PlMisAir.this._planeIndx;

        public void resized() { super.resized();
          PlMisAir.this.wSkills[this.planeIndx].setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - PlMisAir.this.wSkills[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.x - lookAndFeel().metric(1.0F), PlMisAir.this.wSkills[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          PlMisAir.this.wSkins[this.planeIndx].setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - PlMisAir.this.wSkins[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.x - lookAndFeel().metric(1.0F), PlMisAir.this.wSkins[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          PlMisAir.this.wNoseart[this.planeIndx].setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - PlMisAir.this.wNoseart[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.x - lookAndFeel().metric(1.0F), PlMisAir.this.wNoseart[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          PlMisAir.this.wPilots[this.planeIndx].setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - PlMisAir.this.wPilots[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.x - lookAndFeel().metric(1.0F), PlMisAir.this.wPilots[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          PlMisAir.this.renders[this.planeIndx].setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - PlMisAir.this.renders[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.x - lookAndFeel().metric(1.0F), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - PlMisAir.this.renders[this.planeIndx].jdField_win_of_type_ComMaddoxGwindowGRegion.y - lookAndFeel().metric(1.0F));
        }
      });
      this.tabSkin[this._planeIndx] = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("Plane" + (1 + this._planeIndx)), localGWindowDialogClient);
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 7.0F, 1.3F, Plugin.i18n("Player"), null));
      localGWindowDialogClient.addControl(this.wPlayer[this._planeIndx] =  = new GWindowCheckBox(localGWindowDialogClient, 9.0F, 1.0F, null) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void preRender() { super.preRender();
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          if (localPathAir == null) return;
          setChecked((localPathAir == Path.player) && (this.planeIndx == Path.playerNum), false);
          setEnable((PlMisAir.this.type[localPathAir._iType].item[localPathAir._iItem].bEnablePlayer) && (!localPathAir.bOnlyAI)); }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          if (localPathAir == null) return false;
          if (isChecked()) {
            Path.player = localPathAir;
            Path.playerNum = this.planeIndx;
            PAir localPAir = (PAir)localPathAir.point(0);
            localPAir.time = 0.0D;
            PlMission.cur.missionArmy = localPathAir.getArmy();
            localPathAir.computeTimes();
          } else {
            Path.player = null;
            Path.playerNum = 0;
          }
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 7.0F, 1.3F, Plugin.i18n("Skill"), null));
      localGWindowDialogClient.addControl(this.wSkills[this._planeIndx] =  = new GWindowComboControl(localGWindowDialogClient, 9.0F, 3.0F, 7.0F) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void afterCreated() { super.afterCreated();
          setEditable(false);
          add(Plugin.i18n("Rookie"));
          add(Plugin.i18n("Average"));
          add(Plugin.i18n("Veteran"));
          add(Plugin.i18n("Ace")); }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          localPathAir.skills[this.planeIndx] = getSelected();
          PlMisAir.this.checkEditSkinSkills();
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("Skin"), null));
      localGWindowDialogClient.addControl(this.wSkins[this._planeIndx] =  = new GWindowComboControl(localGWindowDialogClient, 9.0F, 5.0F, 7.0F) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void afterCreated() { super.afterCreated();
          setEditable(false);
          add(Plugin.i18n("Default")); }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          if (getSelected() == 0)
            localPathAir.skins[this.planeIndx] = null;
          else
            localPathAir.skins[this.planeIndx] = get(getSelected());
          PlMisAir.this.resetMesh(this.planeIndx);
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("Noseart"), null));
      localGWindowDialogClient.addControl(this.wNoseart[this._planeIndx] =  = new GWindowComboControl(localGWindowDialogClient, 9.0F, 7.0F, 7.0F) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void afterCreated() { super.afterCreated();
          setEditable(false);
          add(Plugin.i18n("None")); }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          if (getSelected() == 0)
            localPathAir.noseart[this.planeIndx] = null;
          else
            localPathAir.noseart[this.planeIndx] = get(getSelected());
          PlMisAir.this.resetMesh(this.planeIndx);
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 7.0F, 1.3F, Plugin.i18n("Pilot"), null));
      localGWindowDialogClient.addControl(this.wPilots[this._planeIndx] =  = new GWindowComboControl(localGWindowDialogClient, 9.0F, 9.0F, 7.0F) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void afterCreated() { super.afterCreated();
          setEditable(false);
          add(Plugin.i18n("Default")); }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          if (getSelected() == 0)
            localPathAir.pilots[this.planeIndx] = null;
          else
            localPathAir.pilots[this.planeIndx] = get(getSelected());
          PlMisAir.this.resetMesh(this.planeIndx);
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 11.0F, 7.0F, 1.3F, Plugin.i18n("NumberOn"), null));
      localGWindowDialogClient.addControl(this.wPlayer[this._planeIndx] =  = new GWindowCheckBox(localGWindowDialogClient, 9.0F, 11.0F, null) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void preRender() { super.preRender();
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          if (localPathAir == null) return;
          setChecked(localPathAir.bNumberOn[this.planeIndx], false); }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PathAir localPathAir = (PathAir)Plugin.builder.selectedPath();
          localPathAir.bNumberOn[this.planeIndx] = isChecked();
          PlMisAir.this.resetMesh(this.planeIndx);
          PlMission.setChanged();
          return false;
        }
      });
      this.renders[this._planeIndx] = new GUIRenders(localGWindowDialogClient, 1.0F, 13.0F, 17.0F, 7.0F, true) {
        int planeIndx = PlMisAir.this._planeIndx;

        public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) { super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
          if (!paramBoolean) return;
          if (paramInt == 1)
          {
            float tmp42_41 = 0.0F; PlMisAir.this.animateMeshT[this.planeIndx] = tmp42_41; PlMisAir.this.animateMeshA[this.planeIndx] = tmp42_41;
            if (Actor.isValid(PlMisAir.this.actorMesh[this.planeIndx]))
              PlMisAir.this.actorMesh[this.planeIndx].pos.setAbs(new Orient(90.0F, 0.0F, 0.0F));
          } else if (paramInt == 0) {
            paramFloat1 -= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 2.0F;
            if (Math.abs(paramFloat1) < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 16.0F) PlMisAir.this.animateMeshA[this.planeIndx] = 0.0F; else
              PlMisAir.this.animateMeshA[this.planeIndx] = (-128.0F * paramFloat1 / this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
            paramFloat2 -= this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F;
            if (Math.abs(paramFloat2) < this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 16.0F) PlMisAir.this.animateMeshT[this.planeIndx] = 0.0F; else
              PlMisAir.this.animateMeshT[this.planeIndx] = (-128.0F * paramFloat2 / this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
          }
        }
      };
      this.camera3D[this._planeIndx] = new Camera3D();
      this.camera3D[this._planeIndx].set(50.0F, 1.0F, 800.0F);
      this.render3D[this._planeIndx] = new _Render3D(this.renders[this._planeIndx].renders, 1.0F);
      this.render3D[this._planeIndx].setCamera(this.camera3D[this._planeIndx]);
      LightEnvXY localLightEnvXY = new LightEnvXY();
      this.render3D[this._planeIndx].setLightEnv(localLightEnvXY);
      localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
      Vector3f localVector3f = new Vector3f(-2.0F, 1.0F, -1.0F); localVector3f.normalize();
      localLightEnvXY.sun().set(localVector3f);
    }
  }

  public void freeResources()
  {
    resetMesh();
  }
  static {
    Property.set(PlMisAir.class, "name", "MisAir");
  }

  class _Render3D extends Render
  {
    int planeIndx = PlMisAir.this._planeIndx;

    public void preRender() { PlMisAir.this.checkMesh(this.planeIndx);
      if (Actor.isValid(PlMisAir.this.actorMesh[this.planeIndx])) {
        if ((PlMisAir.this.animateMeshA[this.planeIndx] != 0.0F) || (PlMisAir.this.animateMeshT[this.planeIndx] != 0.0F)) {
          PlMisAir.this.actorMesh[this.planeIndx].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(PlMisAir.this._orient);
          PlMisAir.this._orient.set(PlMisAir.this._orient.azimut() + PlMisAir.this.animateMeshA[this.planeIndx] * Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec, PlMisAir.this._orient.tangage() + PlMisAir.this.animateMeshT[this.planeIndx] * Main3D.cur3D().guiManager.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec, 0.0F);

          PlMisAir.this._orient.wrap360();
          PlMisAir.this.actorMesh[this.planeIndx].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(PlMisAir.this._orient);
          PlMisAir.this.actorMesh[this.planeIndx].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        }
        PlMisAir.this.actorMesh[this.planeIndx].jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.preRender(PlMisAir.this.actorMesh[this.planeIndx]);
      } }

    public void render() {
      if (Actor.isValid(PlMisAir.this.actorMesh[this.planeIndx])) {
        Render.prepareStates();
        PlMisAir.this.actorMesh[this.planeIndx].jdField_draw_of_type_ComMaddoxIl2EngineActorDraw.render(PlMisAir.this.actorMesh[this.planeIndx]);
      }
    }

    public _Render3D(Renders paramFloat, float arg3) {
      super(localObject);
      setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
      useClearStencil(true);
    }
  }

  class ViewItem extends GWindowMenuItem
  {
    int indx;

    public void execute()
    {
      this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
      PlMisAir.this.viewType(this.indx);
    }
    public ViewItem(int paramGWindowMenu, GWindowMenu paramString1, String paramString2, String arg5) {
      super(paramString2, str);
      this.indx = paramGWindowMenu;
    }
  }

  public static class DefaultArmy
  {
    public int iRegiment;
    public int iSquadron;
    public int iWing;

    public void save(PathAir paramPathAir)
    {
      this.iRegiment = paramPathAir.iRegiment;
      this.iSquadron = paramPathAir.iSquadron;
      this.iWing = paramPathAir.iWing;
    }
    public void load(PathAir paramPathAir) {
      paramPathAir.iRegiment = this.iRegiment;
      paramPathAir.iSquadron = this.iSquadron;
      paramPathAir.iWing = this.iWing;
    }
  }

  public static class Type
  {
    public String name;
    public PlMisAir.Item[] item;

    public Type(String paramString, PlMisAir.Item[] paramArrayOfItem)
    {
      this.name = paramString; this.item = paramArrayOfItem;
    }
  }

  public static class Item
  {
    public String name;
    public Class clazz;
    public int army;
    public boolean bEnablePlayer;
    public double speedMin = 200.0D;
    public double speedMax = 500.0D;
    public double speedRunway = 200.0D;

    public Item(String paramString, Class paramClass, int paramInt) { this.name = paramString; this.clazz = paramClass; this.army = paramInt;
      this.bEnablePlayer = Property.containsValue(paramClass, "cockpitClass");
      String str = Property.stringValue(paramClass, "FlightModel", null);
      if (str != null) {
        SectFile localSectFile = FlightModelMain.sectFile(str);
        this.speedMin = localSectFile.get("Params", "Vmin", (float)this.speedMin);
        this.speedMax = localSectFile.get("Params", "VmaxH", (float)this.speedMax);
        this.speedRunway = this.speedMin;
      }
    }
  }
}