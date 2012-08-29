package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.game.I18N;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Locale;

public class PlMisChief extends Plugin
{
  SectFile fSectsUnits;
  SectFile fSectsEmpty;
  Type[] type;
  private Point3d p = new Point3d();
  private int[] roadOffset = new int[1];
  private int[] timeMinAbs = new int[1];
  private double roadSpeed;
  private PlMission pluginMission;
  private int startComboBox1;
  ViewItem[] viewType;
  HashMapInt viewMap = new HashMapInt();

  private String[] _actorInfo = new String[2];
  GWindowTabDialogClient.Tab tabActor;
  GWindowTabDialogClient.Tab tabWay;
  GWindowLabel wName;
  GWindowComboControl wArmy;
  GWindowLabel wLSleepM;
  GWindowEditControl wSleepM;
  GWindowLabel wLSleepS;
  GWindowEditControl wSleepS;
  GWindowLabel wLSkill;
  GWindowComboControl wSkill;
  GWindowLabel wLSlowfire;
  GWindowEditControl wSlowfire;
  GWindowButton wPrev;
  GWindowButton wNext;
  GWindowLabel wCur;
  GWindowLabel wLTime;
  GWindowLabel wTime;
  GWindowLabel wLTimeOutH;
  GWindowEditControl wTimeOutH;
  GWindowLabel wLTimeOutM;
  GWindowEditControl wTimeOutM;
  GWindowLabel wLSpeed0;
  GWindowLabel wLSpeed1;
  GWindowEditControl wSpeed;

  public static Class ForceClassLoading(String paramString)
  {
    Class localClass;
    try
    {
      localClass = Class.forName(paramString);
    } catch (Exception localException1) {
      System.out.println("PlMisChief: class '" + paramString + "' not found.");
      localException1.printStackTrace();
      throw new RuntimeException("Failure");
    }

    int i = paramString.lastIndexOf('$');
    if (i >= 0) {
      String str = paramString.substring(0, i);
      try {
        ObjIO.classForName(str);
      } catch (Exception localException2) {
        System.out.println("PlMisChief: outer class '" + str + "' not found.");

        localException2.printStackTrace();
        throw new RuntimeException("Failure");
      }
    }

    return localClass;
  }

  public static int moveType(int paramInt)
  {
    PlMisChief localPlMisChief = (PlMisChief)Plugin.getPlugin("MisChief");
    return localPlMisChief.type[paramInt].moveType;
  }

  public static double speed(int paramInt1, int paramInt2) {
    PlMisChief localPlMisChief = (PlMisChief)Plugin.getPlugin("MisChief");
    return localPlMisChief.type[paramInt1].item[paramInt2].speed;
  }

  public static boolean isAirport(int paramInt1, int paramInt2) {
    PlMisChief localPlMisChief = (PlMisChief)Plugin.getPlugin("MisChief");
    return localPlMisChief.type[paramInt1].item[paramInt2].bAirport;
  }

  private String makeName() {
    int i = 0;
    while (true) {
      String str = i + "_Chief";
      if (Actor.getByName(str) == null)
        return str;
      i++;
    }
  }

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("Chiefs");
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
      String str1 = localNumberTokenizer.next("");
      String str2 = localNumberTokenizer.next("");
      int m = localNumberTokenizer.next(0);
      if ((m < 1) && (m >= Builder.armyAmount())) {
        Plugin.builder.tipErr("MissionLoad: Wrong chief's army '" + m + "'");
      }
      else {
        float f1 = localNumberTokenizer.next(0.0F);
        int n = localNumberTokenizer.next(2, 0, 3);
        float f2 = localNumberTokenizer.next(1.0F, 0.5F, 100.0F);

        if (this.fSectsUnits.sectionIndex(str2) < 0) {
          Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + str2 + "'");
        }
        else
        {
          int i1 = str2.indexOf('.');
          if (i1 <= 0) {
            Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + str2 + "'");
          }
          else {
            String str3 = str2.substring(0, i1);
            String str4 = str2.substring(i1 + 1);

            String str5 = this.fSectsUnits.get(str3, str4);
            if (str5 == null) {
              Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + str2 + "'");
            }
            else if (Actor.getByName(str1) != null) {
              Plugin.builder.tipErr("MissionLoad: actor '" + str1 + "' alredy exist");
            }
            else
            {
              int i2 = 0;
              for (; i2 < this.type.length; i2++)
                if (this.type[i2].name.equals(str3))
                  break;
              if (i2 == this.type.length) {
                Plugin.builder.tipErr("MissionLoad: Wrong chief's category '" + str2 + "'");
              }
              else
              {
                int i3 = 0;
                for (; i3 < this.type[i2].item.length; i3++)
                  if (this.type[i2].item[i3].name.equals(str4))
                    break;
                if (i3 == this.type[i2].item.length) {
                  Plugin.builder.tipErr("MissionLoad: Wrong chief's type '" + str2 + "'");
                }
                else
                {
                  int i4 = paramSectFile.sectionIndex(str1 + "_Road");
                  if (i4 < 0) {
                    Plugin.builder.tipErr("MissionLoad: Wrong chief's road '" + str1 + "'");
                  }
                  else
                  {
                    PathChief localPathChief = new PathChief(Plugin.builder.pathes, this.type[i2].moveType, i2, i3, this.fSectsUnits, this.type[i2].item[i3].iSectUnits, this.p);

                    localPathChief.setName(str1);
                    localPathChief.setArmy(m);
                    localPathChief.drawing(this.viewMap.containsKey(i2));
                    localPathChief._sleep = Math.round(f1);
                    localPathChief._skill = n;
                    localPathChief._slowfire = f2;
                    Property.set(localPathChief, "builderPlugin", this);
                    Property.set(localPathChief, "i18nName", I18N.technic(this.type[i2].item[i3].name));

                    PNodes localPNodes = new PNodes(localPathChief, null, this.type[i2].item[i3].iconName, null);
                    this.roadOffset[0] = 0;
                    try {
                      while (true) {
                        this.timeMinAbs[0] = 0;
                        localPNodes.posXYZ = loadRoadSegments(paramSectFile, i4, this.roadOffset, this.timeMinAbs, this.type[i2].item[i3].speed / 2.0D);
                        localPNodes.speed = this.roadSpeed;
                        clampSpeed(localPNodes);
                        if (localPNodes.posXYZ == null)
                          break;
                        localPNodes.timeoutMin = this.timeMinAbs[0];
                        localPNodes = new PNodes(localPathChief, localPNodes, null);
                      }
                    } catch (Exception localException) {
                      Plugin.builder.tipErr("MissionLoad: Wrong chief's road '" + str1 + "'");
                      localPathChief.destroy();
                      continue;
                    }
                    int i5 = localPathChief.points();
                    if (i5 < 2) {
                      Plugin.builder.tipErr("MissionLoad: Wrong chief's road '" + str1 + "'");
                    }
                    else {
                      for (int i6 = 0; i6 < i5 - 1; i6++) {
                        localPNodes = (PNodes)localPathChief.point(i6);
                        this.p.set(localPNodes.posXYZ[0], localPNodes.posXYZ[1], localPNodes.posXYZ[2]);

                        localPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.p);
                      }
                      localPNodes = (PNodes)localPathChief.point(i5 - 2);
                      int i7 = localPNodes.posXYZ.length / 4 - 1;
                      this.p.set(localPNodes.posXYZ[(i7 * 4 + 0)], localPNodes.posXYZ[(i7 * 4 + 1)], localPNodes.posXYZ[(i7 * 4 + 2)]);

                      localPNodes = (PNodes)localPathChief.point(i5 - 1);
                      localPNodes.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.p);
                      localPathChief.updateUnitsPos();
                      localPathChief.computeTimesLoaded();
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private float[] loadRoadSegments(SectFile paramSectFile, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2, double paramDouble) {
    float[] arrayOfFloat = null;
    int i = paramArrayOfInt1[0];
    if (paramSectFile.vars(paramInt) <= i)
      return null;
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(paramInt, i));
    localNumberTokenizer.next(0.0F); localNumberTokenizer.next(0.0F); localNumberTokenizer.next(0.0F);
    paramArrayOfInt2[0] = localNumberTokenizer.next(0);
    int j = localNumberTokenizer.next(0);
    if (j == 0)
      return null;
    this.roadSpeed = localNumberTokenizer.next(paramDouble);
    arrayOfFloat = new float[j * 4];
    int k = 0;
    while (j-- > 0) {
      localNumberTokenizer = new NumberTokenizer(paramSectFile.line(paramInt, i++));
      arrayOfFloat[(k + 0)] = localNumberTokenizer.next(0.0F);
      arrayOfFloat[(k + 1)] = localNumberTokenizer.next(0.0F);
      Engine.land(); arrayOfFloat[(k + 2)] = Landscape.HQ(arrayOfFloat[(k + 0)], arrayOfFloat[(k + 1)]);
      arrayOfFloat[(k + 3)] = localNumberTokenizer.next(0.0F);
      localNumberTokenizer.next(0);
      k += 4;
    }
    paramArrayOfInt1[0] = (i - 1);
    return arrayOfFloat;
  }

  private void saveRoadSegment(PNodes paramPNodes, int paramInt1, SectFile paramSectFile, int paramInt2, double paramDouble) {
    float[] arrayOfFloat = paramPNodes.posXYZ;
    float f1 = arrayOfFloat[(paramInt1 * 4 + 0)];
    float f2 = arrayOfFloat[(paramInt1 * 4 + 1)];
    float f3 = arrayOfFloat[(paramInt1 * 4 + 2)];
    float f4 = arrayOfFloat[(paramInt1 * 4 + 3)];
    if (paramInt1 == 0) {
      if (paramPNodes.timeoutMin > 0.0D) {
        paramSectFile.lineAdd(paramInt2, formatPosFloat(f1), formatPosFloat(f2) + " " + formatPosFloat(f4) + " " + Math.round((paramPNodes.jdField_time_of_type_Double + paramPNodes.timeoutMin * 60.0D) / 60.0D) + " " + arrayOfFloat.length / 4 + " " + paramDouble);
      }
      else
      {
        paramSectFile.lineAdd(paramInt2, formatPosFloat(f1), formatPosFloat(f2) + " " + formatPosFloat(f4) + " 0 " + arrayOfFloat.length / 4 + " " + paramDouble);
      }
    }
    else
    {
      paramSectFile.lineAdd(paramInt2, formatPosFloat(f1), formatPosFloat(f2) + " " + formatPosFloat(f4));
    }
  }

  public boolean save(SectFile paramSectFile)
  {
    if (Plugin.builder.pathes == null)
      return true;
    int i = paramSectFile.sectionIndex("Chiefs");
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    for (int j = 0; j < arrayOfObject.length; j++) {
      Actor localActor = (Actor)arrayOfObject[j];
      if (localActor == null) break;
      if ((localActor instanceof PathChief)) {
        PathChief localPathChief = (PathChief)localActor;
        int k = localPathChief.points();
        if (k < 2) {
          new GWindowMessageBox(Plugin.builder.viewWindow.root, 20.0F, true, "Save error", "Chief '" + localPathChief.name() + "' contains only one waypoint", 3, 0.0F);

          return false;
        }
        if (i <= -1)
          i = paramSectFile.sectionAdd("Chiefs");
        String str1 = localPathChief.name();
        String str2 = this.type[localPathChief._iType].name + "." + this.type[localPathChief._iType].item[localPathChief._iItem].name;

        int m = localPathChief.getArmy();
        String str3 = str1 + "_Road";
        if (this.type[localPathChief._iType].moveType == 2)
          paramSectFile.lineAdd(i, str1, str2 + " " + m + " " + localPathChief._sleep + " " + localPathChief._skill + " " + localPathChief._slowfire);
        else {
          paramSectFile.lineAdd(i, str1, str2 + " " + m);
        }
        int n = paramSectFile.sectionAdd(str3);
        for (int i1 = 0; i1 < k - 1; i1++) {
          localPNodes = (PNodes)localPathChief.point(i1);
          int i2 = localPNodes.posXYZ.length / 4;
          for (int i3 = 0; i3 < i2 - 1; i3++)
            saveRoadSegment(localPNodes, i3, paramSectFile, n, localPNodes.speed);
        }
        PNodes localPNodes = (PNodes)localPathChief.point(k - 2);
        saveRoadSegment(localPNodes, localPNodes.posXYZ.length / 4 - 1, paramSectFile, n, localPNodes.speed);
      }
    }
    return true;
  }

  private String formatPosFloat(float paramFloat) {
    int i = paramFloat < 0.0D ? 1 : 0;
    if (i != 0) paramFloat = -paramFloat;
    double d = paramFloat + 0.005D - (int)paramFloat;
    if (d >= 0.1D) return (i != 0 ? "-" : "") + (int)paramFloat + "." + (int)(d * 100.0D);
    return (i != 0 ? "-" : "") + (int)paramFloat + ".0" + (int)(d * 100.0D);
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    Object localObject = null;
    try
    {
      Point3d localPoint3d = paramLoc.getPoint();
      int i = Plugin.builder.wSelect.comboBox1.getSelected();
      int j = Plugin.builder.wSelect.comboBox2.getSelected();
      if (Plugin.builder.selectedPath() != null) {
        localObject = Plugin.builder.selectedPath();
        if (!(localObject instanceof PathChief))
          return;
        PathChief localPathChief = (PathChief)localObject;
        if ((i - this.startComboBox1 != localPathChief._iType) || (j != localPathChief._iItem))
        {
          Plugin.builder.setSelected(null);
        }
      }
      PNodes localPNodes;
      if (Plugin.builder.selectedPoint() != null) {
        if ((this.type[(i - this.startComboBox1)].moveType == 3) && (((Path)localObject).points() >= 2))
          return;
        localPNodes = new PNodes(Plugin.builder.selectedPath(), Plugin.builder.selectedPoint(), localPoint3d);
        localPNodes.speed = (this.type[(i - this.startComboBox1)].item[j].speed / 2.0D);
      } else {
        if ((i < this.startComboBox1) || (i >= this.startComboBox1 + this.type.length))
          return;
        i -= this.startComboBox1;
        if ((j < 0) || (j >= this.type[i].item.length)) {
          return;
        }
        localObject = new PathChief(Plugin.builder.pathes, this.type[i].moveType, i, j, this.fSectsUnits, this.type[i].item[j].iSectUnits, localPoint3d);

        ((Path)localObject).setName(makeName());
        ((Path)localObject).setArmy(this.type[i].item[j].army);
        Property.set(localObject, "builderPlugin", this);
        Property.set(localObject, "i18nName", I18N.technic(this.type[i].item[j].name));

        ((Path)localObject).drawing(this.viewMap.containsKey(i));
        localPNodes = new PNodes((Path)localObject, null, this.type[i].item[j].iconName, localPoint3d);
        localPNodes.speed = (this.type[i].item[j].speed / 2.0D);
      }
      Plugin.builder.setSelected(localPNodes);
      PlMission.setChanged();
    } catch (Exception localException) {
      if ((localObject != null) && (((Path)localObject).points() == 0))
        ((Path)localObject).destroy();
      System.out.println(localException);
    }
  }

  private void clampSpeed(PNodes paramPNodes) {
    PathChief localPathChief = (PathChief)paramPNodes.getOwner();
    if (paramPNodes.speed > this.type[localPathChief._iType].item[localPathChief._iItem].speed)
      paramPNodes.speed = this.type[localPathChief._iType].item[localPathChief._iItem].speed;
    if (paramPNodes.speed < 1.0D)
      paramPNodes.speed = 1.0D; 
  }

  private void clampSpeed(PathChief paramPathChief) {
    int i = paramPathChief.points();
    for (int j = 0; j < i; j++)
      clampSpeed((PNodes)paramPathChief.point(j));
  }

  public void changeType() {
    int i = Plugin.builder.wSelect.comboBox1.getSelected() - this.startComboBox1;
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
    if (i != localPathChief._iType) return;
    localPathChief.setUnits(i, j, this.fSectsUnits, this.type[i].item[j].iSectUnits, localPathChief.point(0).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());

    Property.set(localPathChief, "i18nName", I18N.technic(this.type[i].item[j].name));
    if (moveType(i) == 2) {
      clampSpeed(localPathChief);
    }
    localPathChief.updateUnitsPos();
    PlMission.setChanged();
  }

  public void configure() {
    if (Plugin.getPlugin("Mission") == null)
      throw new RuntimeException("PlMisChief: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)Plugin.getPlugin("Mission"));
    if (this.jdField_sectFile_of_type_JavaLangString == null)
      throw new RuntimeException("PlMisChief: field 'sectFile' not defined");
    SectFile localSectFile = new SectFile(this.jdField_sectFile_of_type_JavaLangString, 0);
    int i = localSectFile.sections();
    if (i <= 0)
      throw new RuntimeException("PlMisChief: file '" + this.jdField_sectFile_of_type_JavaLangString + "' is empty");
    int j = 0;
    for (int k = 0; k < i; k++) {
      String str1 = localSectFile.sectionName(k);
      if (str1.indexOf('.') >= 0)
        continue;
      j++;
    }
    if (j == 0)
      throw new RuntimeException("PlMisChief: file '" + this.jdField_sectFile_of_type_JavaLangString + "' is empty");
    this.fSectsUnits = localSectFile;
    this.type = new Type[j];
    int m = 0;
    for (int n = 0; n < i; n++) {
      String str2 = localSectFile.sectionName(n);
      if (str2.indexOf('.') >= 0)
        continue;
      int i1 = localSectFile.vars(n);
      if (i1 < 2) {
        throw new RuntimeException("PlMisChief: file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '" + str2 + "' missing moveType and/or class");
      }
      if (localSectFile.varIndex(n, "moveType") != 0) {
        throw new RuntimeException("PlMisChief: file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '" + str2 + "' moveType must be first row");
      }
      String str3 = localSectFile.value(n, 0);
      int i2 = 0;
      if ("VEHICLE".equals(str3)) i2 = 0;
      else if ("TROOPER".equals(str3)) i2 = 1;
      else if ("SHIP".equals(str3)) i2 = 2;
      else if ("TRAIN".equals(str3)) i2 = 3;
      else {
        throw new RuntimeException("PlMisChief: file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '" + str2 + "' unknown moveType '" + str3 + "'");
      }

      Item[] arrayOfItem = new Item[i1 - 1];
      for (int i3 = 1; i3 < i1; i3++) {
        String str4 = localSectFile.var(n, i3);
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.value(n, i3));
        String str5 = localNumberTokenizer.next((String)null);
        int i4 = localNumberTokenizer.next(1, 1, Builder.armyAmount() - 1);
        String str6 = localNumberTokenizer.next("icons/tank.mat");
        arrayOfItem[(i3 - 1)] = new Item(i2, str2, str4, str5, localSectFile, i4, str6);
      }
      this.type[(m++)] = new Type(str2, i2, arrayOfItem);
    }

    this.fSectsEmpty = new SectFile();
  }

  void viewUpdate()
  {
    if (Plugin.builder.pathes == null)
      return;
    Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
    Object localObject;
    PathChief localPathChief;
    for (int i = 0; i < arrayOfObject.length; i++) {
      localObject = (Actor)arrayOfObject[i];
      if (localObject == null) break;
      if ((localObject instanceof PathChief)) {
        localPathChief = (PathChief)localObject;
        localPathChief.drawing(this.viewMap.containsKey(localPathChief._iType));
      }
    }
    if (Plugin.builder.selectedPath() != null) {
      localObject = Plugin.builder.selectedPath();
      if ((localObject instanceof PathChief)) {
        localPathChief = (PathChief)localObject;
        if (!this.viewMap.containsKey(localPathChief._iType)) {
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
        Plugin.builder.wSelect.comboBox2.add(I18N.technic(this.type[(paramInt1 - this.startComboBox1)].item[i].name));
      }
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, false);

    setSelectorMesh();
  }

  public void setSelectorMesh() {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    if ((i < this.startComboBox1) || (i >= this.startComboBox1 + this.type.length))
      return;
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    try {
      String str1 = this.fSectsUnits.var(this.type[(i - this.startComboBox1)].item[j].iSectUnits, 0);
      Class localClass = ForceClassLoading(str1);
      String str2 = Property.stringValue(localClass, "meshName", null);
      if (str2 == null) {
        Method localMethod = localClass.getMethod("getMeshNameForEditor", null);
        str2 = (String)localMethod.invoke(localClass, null);
      }
      Plugin.builder.wSelect.setMesh(str2, true);
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      Plugin.builder.wSelect.setMesh(null, true);
    }
  }

  public String[] actorInfo(Actor paramActor) {
    PathChief localPathChief = (PathChief)paramActor.getOwner();
    this._actorInfo[0] = (I18N.technic(this.type[localPathChief._iType].name) + "." + I18N.technic(this.type[localPathChief._iType].item[localPathChief._iItem].name));

    PNodes localPNodes = (PNodes)paramActor;
    int i = localPathChief.pointIndx(localPNodes);
    if (localPNodes.timeoutMin > 0.0D) {
      this._actorInfo[1] = ("(" + i + ") in:" + Plugin.timeSecToString(localPNodes.jdField_time_of_type_Double + (int)(World.getTimeofDay() * 60.0F * 60.0F)) + " out:" + Plugin.timeSecToString(localPNodes.jdField_time_of_type_Double + (int)(World.getTimeofDay() * 60.0F * 60.0F) + localPNodes.timeoutMin * 60.0D));
    }
    else
      this._actorInfo[1] = ("(" + i + ") " + Plugin.timeSecToString(localPNodes.jdField_time_of_type_Double + (int)(World.getTimeofDay() * 60.0F * 60.0F)));
    return this._actorInfo;
  }

  public void syncSelector()
  {
    PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
    fillComboBox2(localPathChief._iType + this.startComboBox1, localPathChief._iItem);
    Plugin.builder.wSelect.tabsClient.addTab(1, this.tabActor);
    this.wName.jdField_cap_of_type_ComMaddoxGwindowGCaption.set(Property.stringValue(localPathChief, "i18nName", ""));
    this.wArmy.setSelected(localPathChief.getArmy() - 1, true, false);
    if (moveType(localPathChief._iType) == 2) {
      this.wLSleepM.showWindow();
      this.wSleepM.showWindow();
      this.wLSleepS.showWindow();
      this.wSleepS.showWindow();
      this.wSleepM.setValue("" + localPathChief._sleep / 60 % 99, false);
      this.wSleepS.setValue("" + localPathChief._sleep % 60, false);
      this.wLSkill.showWindow();
      this.wSkill.showWindow();
      this.wSkill.setSelected(localPathChief._skill, true, false);
      this.wLSlowfire.showWindow();
      this.wSlowfire.showWindow();
      this.wSlowfire.setValue("" + localPathChief._slowfire);
    } else {
      this.wLSleepM.hideWindow();
      this.wSleepM.hideWindow();
      this.wLSleepS.hideWindow();
      this.wSleepS.hideWindow();
      this.wLSkill.hideWindow();
      this.wSkill.hideWindow();
      this.wLSlowfire.hideWindow();
      this.wSlowfire.hideWindow();
    }

    if (moveType(localPathChief._iType) != 3) {
      Plugin.builder.wSelect.tabsClient.addTab(2, this.tabWay);
      fillDialogWay();
    }
  }

  public void updateSelector() {
    fillDialogWay();
  }

  private void controlResized(GWindowDialogClient paramGWindowDialogClient, GWindow paramGWindow) {
    if (paramGWindow == null) return;
    paramGWindow.setSize(paramGWindowDialogClient.win.dx - paramGWindow.win.x - paramGWindowDialogClient.lAF().metric(1.0F), paramGWindow.win.dy);
  }
  private void editResized(GWindowDialogClient paramGWindowDialogClient) {
    controlResized(paramGWindowDialogClient, this.wName);
    controlResized(paramGWindowDialogClient, this.wArmy);
  }

  public void createGUI() {
    fillComboBox1();
    fillComboBox2(0, 0);
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisChief.this.fillComboBox2(i, 0);
        return false;
      }
    });
    Plugin.builder.wSelect.comboBox2.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 2)
          return false;
        PlMisChief.this.setSelectorMesh();
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

    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient() {
      public void resized() { super.resized(); PlMisChief.this.editResized(this);
      }
    });
    this.tabActor = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("ChiefActor"), localGWindowDialogClient);

    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 7.0F, 1.3F, Plugin.i18n("Name"), null));
    localGWindowDialogClient.addLabel(this.wName = new GWindowLabel(localGWindowDialogClient, 9.0F, 1.0F, 7.0F, 1.3F, "", null));
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 7.0F, 1.3F, Plugin.i18n("Army"), null));
    localGWindowDialogClient.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient, 9.0F, 3.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        for (int i = 1; i < Builder.armyAmount(); i++)
          add(I18N.army(Army.name(i))); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Path localPath = Plugin.builder.selectedPath();
        int i = getSelected() + 1;
        localPath.setArmy(i);
        PlMission.setChanged();
        PlMission.checkShowCurrentArmy();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSleepM = new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("Sleep"), null));
    localGWindowDialogClient.addControl(this.wSleepM = new GWindowEditControl(localGWindowDialogClient, 9.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisChief.this.getSleep();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSleepS = new GWindowLabel(localGWindowDialogClient, 11.2F, 5.0F, 1.0F, 1.3F, ":", null));
    localGWindowDialogClient.addControl(this.wSleepS = new GWindowEditControl(localGWindowDialogClient, 11.5F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisChief.this.getSleep();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSkill = new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("Skill"), null));
    localGWindowDialogClient.addControl(this.wSkill = new GWindowComboControl(localGWindowDialogClient, 9.0F, 7.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add(Plugin.i18n("Rookie"));
        add(Plugin.i18n("Average"));
        add(Plugin.i18n("Veteran"));
        add(Plugin.i18n("Ace")); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
        localPathChief._skill = getSelected();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSlowfire = new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 7.0F, 1.3F, Plugin.i18n("Slowfire"), null));
    localGWindowDialogClient.addControl(this.wSlowfire = new GWindowEditControl(localGWindowDialogClient, 9.0F, 9.0F, 3.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = (this.bNumericFloat = 1);
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str = getValue();
        float f = 1.0F;
        try {
          f = Float.parseFloat(str); } catch (Exception localException) {
        }
        if (f < 0.5F) f = 0.5F;
        if (f > 100.0F) f = 100.0F;
        setValue("" + f, false);
        PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
        localPathChief._slowfire = f;
        PlMission.setChanged();
        return false;
      }
    });
    initEditWay();
  }
  private void getSleep() {
    PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
    String str = this.wSleepM.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 99.0D) d1 = 99.0D;
    str = this.wSleepS.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 60.0D) d2 = 60.0D;
    localPathChief._sleep = (int)(d1 * 60.0D + d2);
    PlMission.setChanged();
  }

  private void fillDialogWay()
  {
    PNodes localPNodes = (PNodes)Plugin.builder.selectedPoint();
    PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
    int i = localPathChief.pointIndx(localPNodes);
    this.wCur.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption("" + i + "(" + localPathChief.points() + ")");
    this.wTime.jdField_cap_of_type_ComMaddoxGwindowGCaption = new GCaption(Plugin.timeSecToString(localPNodes.jdField_time_of_type_Double + World.getTimeofDay() * 60.0F * 60.0F));
    this.wPrev.setEnable(i > 0);
    if (i < localPathChief.points() - 1) {
      this.wNext.setEnable(true);
      this.wTimeOutH.setEnable(true);
      this.wTimeOutM.setEnable(true);
      this.wTimeOutH.setValue("" + (int)(localPNodes.timeoutMin / 60.0D % 24.0D), false);
      this.wTimeOutM.setValue("" + (int)(localPNodes.timeoutMin % 60.0D), false);
    } else {
      localPNodes.timeoutMin = 0.0D;
      this.wNext.setEnable(false);
      this.wTimeOutH.setEnable(false);
      this.wTimeOutM.setEnable(false);
      this.wTimeOutH.setValue("0", false);
      this.wTimeOutM.setValue("0", false);
    }
    if (moveType(localPathChief._iType) != 2) {
      this.wLSpeed0.hideWindow();
      this.wLSpeed1.hideWindow();
      this.wSpeed.hideWindow();
    }
    else {
      this.wLSpeed0.showWindow();
      this.wLSpeed1.showWindow();
      this.wSpeed.showWindow();
      this.wSpeed.setValue("" + (int)Math.round(localPNodes.speed * 3.6D), false);
    }
  }

  public void initEditWay() {
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabWay = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("Waypoint"), localGWindowDialogClient);

    localGWindowDialogClient.addControl(this.wPrev = new GWindowButton(localGWindowDialogClient, 1.0F, 1.0F, 5.0F, 1.6F, Plugin.i18n("&Prev"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          PNodes localPNodes = (PNodes)Plugin.builder.selectedPoint();
          PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
          int i = localPathChief.pointIndx(localPNodes);
          if (i > 0) {
            Plugin.builder.setSelected(localPathChief.point(i - 1));
            PlMisChief.this.fillDialogWay();
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
          PNodes localPNodes = (PNodes)Plugin.builder.selectedPoint();
          PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
          int i = localPathChief.pointIndx(localPNodes);
          if (i < localPathChief.points() - 1) {
            Plugin.builder.setSelected(localPathChief.point(i + 1));
            PlMisChief.this.fillDialogWay();
            Plugin.builder.repaint();
          }
          return true;
        }
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wCur = new GWindowLabel(localGWindowDialogClient, 15.0F, 1.0F, 4.0F, 1.6F, "1(1)", null));

    localGWindowDialogClient.addLabel(this.wLTime = new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 7.0F, 1.3F, Plugin.i18n("Time"), null));
    localGWindowDialogClient.addLabel(this.wTime = new GWindowLabel(localGWindowDialogClient, 9.0F, 3.0F, 6.0F, 1.3F, "0:00", null));

    localGWindowDialogClient.addLabel(this.wLTimeOutH = new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("TimeOut"), null));
    localGWindowDialogClient.addControl(this.wTimeOutH = new GWindowEditControl(localGWindowDialogClient, 9.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisChief.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLTimeOutM = new GWindowLabel(localGWindowDialogClient, 11.2F, 5.0F, 1.0F, 1.3F, ":", null));
    localGWindowDialogClient.addControl(this.wTimeOutM = new GWindowEditControl(localGWindowDialogClient, 11.5F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisChief.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSpeed0 = new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("Speed"), null));
    localGWindowDialogClient.addLabel(this.wLSpeed1 = new GWindowLabel(localGWindowDialogClient, 15.0F, 7.0F, 4.0F, 1.3F, Plugin.i18n("[kM/H]"), null));
    localGWindowDialogClient.addControl(this.wSpeed = new GWindowEditControl(localGWindowDialogClient, 9.0F, 7.0F, 5.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PNodes localPNodes = (PNodes)Plugin.builder.selectedPoint();
        String str = getValue();
        double d = 0.0D;
        try { d = Double.parseDouble(str) / 3.6D;
        } catch (Exception localException) {
          setValue("" + (int)Math.round(((PNodes)Plugin.builder.selectedPoint()).speed * 3.6D), false);
          return false;
        }
        PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
        if (d > PlMisChief.this.type[localPathChief._iType].item[localPathChief._iItem].speed)
          d = PlMisChief.this.type[localPathChief._iType].item[localPathChief._iItem].speed;
        localPNodes.speed = d;
        localPathChief.computeTimes();
        PlMission.setChanged();
        return false;
      } } );
  }

  private void getTimeOut() {
    PNodes localPNodes = (PNodes)Plugin.builder.selectedPoint();
    PathChief localPathChief = (PathChief)Plugin.builder.selectedPath();
    String str = this.wTimeOutH.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 12.0D) d1 = 12.0D;
    str = this.wTimeOutM.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 60.0D) d2 = 60.0D;
    localPNodes.timeoutMin = (d1 * 60.0D + d2);
    localPathChief.computeTimes();
    PlMission.setChanged();
  }
  static {
    Property.set(PlMisChief.class, "name", "MisChief");
  }

  class ViewItem extends GWindowMenuItem
  {
    int indx;

    public void execute()
    {
      this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
      PlMisChief.this.viewType(this.indx);
    }
    public ViewItem(int paramGWindowMenu, GWindowMenu paramString1, String paramString2, String arg5) {
      super(paramString2, str);
      this.indx = paramGWindowMenu;
    }
  }

  static class Type
  {
    public String name;
    public int moveType;
    public PlMisChief.Item[] item;

    public Type(String paramString, int paramInt, PlMisChief.Item[] paramArrayOfItem)
    {
      this.name = paramString;
      this.moveType = paramInt;
      this.item = paramArrayOfItem;
    }
  }

  static class Item
  {
    public String name;
    public int iSectUnits;
    public String shortClassName;
    public int army;
    public String iconName;
    public double speed;
    public boolean bAirport = false;

    public Item(int paramInt1, String paramString1, String paramString2, String paramString3, SectFile paramSectFile, int paramInt2, String paramString4) { this.name = paramString2;
      this.iconName = paramString4;
      this.iSectUnits = paramSectFile.sectionIndex(paramString1 + "." + paramString2);
      if (this.iSectUnits < 0)
        throw new RuntimeException("PlMisChief: section '" + paramString1 + "." + paramString2 + "' not found");
      this.army = paramInt2;
      if (paramInt1 == 3) {
        this.speed = 11.111111640930176D;
      } else {
        try {
          this.speed = -1.0D;
          int i = paramSectFile.vars(this.iSectUnits);
          for (int j = 0; j < i; j++) {
            String str = paramSectFile.var(this.iSectUnits, j);
            Class localClass = PlMisChief.ForceClassLoading(str);
            double d = Property.doubleValue(localClass, "speed", -1.0D);
            if (d < 0.0D)
              throw new RuntimeException("PlMisChief: property 'speed' NOT found in class '" + str);
            if ((d > 0.0D) && ((this.speed < 0.0D) || (d < this.speed)))
              this.speed = d;
            if ("true".equals(Property.stringValue(localClass, "IsAirport", (String)null)))
              this.bAirport = true;
          }
        } catch (Exception localException) {
          System.out.println(localException.getMessage());
          localException.printStackTrace();
          throw new RuntimeException(localException.toString());
        }
        if (this.speed <= 0.0D)
          throw new RuntimeException("PlMisChief: section '" + paramString1 + "." + paramString2 + "' speed == 0");
        if (paramInt1 == 2)
          this.speed *= 2.0D;
      }
    }
  }
}