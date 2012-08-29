package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowTable.Column;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.CockpitPilot;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GUINetAircraft extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton bLoodout;
  public GUIButton bPrev;
  public GUIButton bFly;
  public Table wTable;
  public SectFile sectMission;
  public int serverPlace = -1;

  private static int[] crewFunction = new int[20];

  public static Item getItem(int paramInt)
  {
    if (paramInt < 0) return null;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    if (paramInt >= localGUINetAircraft.wTable.items.size()) return null;
    return (Item)localGUINetAircraft.wTable.items.get(paramInt);
  }

  protected static boolean isSelectedValid() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return false;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    return i < localGUINetAircraft.wTable.items.size();
  }
  protected static int selectedCockpitNum() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return 0;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    Item localItem = (Item)localGUINetAircraft.wTable.items.get(i);
    return localItem.iCockpitNum;
  }
  protected static String selectedSpawnName() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return null;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    Item localItem = (Item)localGUINetAircraft.wTable.items.get(i);
    return localItem.wingName + localItem.iAircraft;
  }
  protected static Regiment selectedRegiment() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return null;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    return ((Item)localGUINetAircraft.wTable.items.get(i)).reg;
  }
  protected static String selectedAircraftName() {
    return I18N.plane(selectedAircraftKeyName());
  }
  protected static String selectedAircraftKeyName() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return null;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    return ((Item)localGUINetAircraft.wTable.items.get(i)).keyName;
  }
  protected static int selectedAircraftNumInWing() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return 0;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    return ((Item)localGUINetAircraft.wTable.items.get(i)).iAircraft;
  }
  protected static String selectedWingName() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return null;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    return ((Item)localGUINetAircraft.wTable.items.get(i)).wingName;
  }
  protected static Class selectedAircraftClass() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return null;
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    return ((Item)localGUINetAircraft.wTable.items.get(i)).clsAircraft;
  }
  public static int serverPlace() {
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft.fillListItems();
    if (localGUINetAircraft.sectMission == null)
      return -1;
    String str = localGUINetAircraft.sectMission.get("MAIN", "player", (String)null);
    if (str == null)
      return localGUINetAircraft.serverPlace = -1;
    int i = localGUINetAircraft.sectMission.get("MAIN", "playerNum", 0, 0, 3);
    int j = localGUINetAircraft.wTable.items.size();
    for (int k = 0; k < j; k++) {
      Item localItem = (Item)localGUINetAircraft.wTable.items.get(k);
      if ((localItem.wingName.equals(str)) && (localItem.iAircraft == i))
      {
        return localGUINetAircraft.serverPlace = k;
      }
    }
    return localGUINetAircraft.serverPlace = -1;
  }

  private void fillListItems() {
    if ((Mission.cur() == null) || (Mission.cur().isDestroyed())) {
      this.sectMission = null;
      this.wTable.items.clear();
      return;
    }
    if (this.sectMission == Mission.cur().sectFile())
      return;
    this.wTable.items.clear();
    this.wTable.setSelect(-1, -1);
    this.serverPlace = -1;
    SectFile localSectFile = Mission.cur().sectFile();
    createListItems(localSectFile, 1);
    createListItems(localSectFile, 2);
    this.sectMission = localSectFile;
  }
  private void createListItems(SectFile paramSectFile, int paramInt) {
    int i = paramSectFile.sectionIndex("Wing");
    if (i < 0) return;
    int j = paramSectFile.vars(i);
    int k = 1;
    for (int m = 0; m < j; m++) {
      String str1 = paramSectFile.var(i, m);
      if (paramSectFile.sectionIndex(str1) < 0)
        continue;
      if (str1.length() < 3)
        continue;
      Regiment localRegiment = (Regiment)Actor.getByName(str1.substring(0, str1.length() - 2));
      if (localRegiment.getArmy() != paramInt)
        continue;
      String str2 = paramSectFile.get(str1, "Class", (String)null);
      if (str2 == null)
        continue;
      Class localClass = null;
      try {
        localClass = ObjIO.classForName(str2);
      } catch (Exception localException) {
        continue;
      }
      int n = paramSectFile.get(str1, "StartTime", 0);
      if (n > 0)
        continue;
      int i1 = paramSectFile.get(str1, "OnlyAI", 0, 0, 1) == 1 ? 1 : 0;
      if (i1 != 0)
        continue;
      Object localObject = Property.value(localClass, "cockpitClass");
      if (localObject == null)
        continue;
      int i2 = 0;
      Class[] arrayOfClass = null;
      if ((localObject instanceof Class)) {
        i2 = 1;
      } else {
        arrayOfClass = (Class[])(Class[])localObject;
        i2 = arrayOfClass.length;
      }

      String str3 = Property.stringValue(localClass, "keyName", null);

      PaintScheme localPaintScheme = Aircraft.getPropertyPaintScheme(localClass, localRegiment.country());
      int i3 = str1.charAt(str1.length() - 2) - '0';
      int i4 = str1.charAt(str1.length() - 1) - '0';

      Mat localMat = PaintScheme.makeMatGUI(localRegiment.name() + "GUI", localRegiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
      GTexture localGTexture = GTexture.New(localMat.Name());

      int i5 = 0;

      crewFunction[0] = 1;
      for (int i6 = 1; i6 < crewFunction.length; i6++)
        crewFunction[i6] = 7;
      String str4 = Property.stringValue(localClass, "FlightModel", null);
      SectFile localSectFile = FlightModelMain.sectFile(str4);
      i5 = localSectFile.get("Aircraft", "Crew", 1, 1, 20);
      for (int i9 = 0; i9 < crewFunction.length; i9++) {
        crewFunction[i9] = localSectFile.get("Aircraft", "CrewFunction" + i9, crewFunction[i9], 1, AircraftState.astateHUDPilotHits.length);
      }

      int i7 = paramSectFile.get(str1, "Planes", 1, 1, 4);
      for (int i8 = 0; i8 < i7; i8++)
        for (i9 = 0; i9 < i2; i9++) {
          if ((i9 > 0) && (CockpitPilot.class.isAssignableFrom(arrayOfClass[i9])))
            continue;
          Item localItem = new Item();
          localItem.indexInArmy = (k++);
          localItem.iSectWing = m;
          localItem.iAircraft = i8;
          localItem.iCockpitNum = i9;
          localItem.wingName = str1;
          localItem.keyName = str3;
          localItem.cocName = AircraftState.astateHUDPilotHits[1];
          if (arrayOfClass != null) {
            int i10 = Property.intValue(arrayOfClass[i9], "astatePilotIndx", 0);
            if (i10 < i5) {
              i10 = crewFunction[i10];
              if (i10 < AircraftState.astateHUDPilotHits.length) {
                localItem.cocName = AircraftState.astateHUDPilotHits[i10];
              }
            }
          }
          localItem.cocName = I18N.hud_log(localItem.cocName);
          localItem.reg = localRegiment;
          localItem.clsAircraft = localClass;
          localItem.number = localPaintScheme.typedName(localClass, localRegiment, i3, i4, i8);
          localItem.texture = localGTexture;
          this.wTable.items.add(localItem);
        }
    }
  }

  private String findPlayer(int paramInt)
  {
    NetUser localNetUser = (NetUser)NetEnv.host();
    if (localNetUser.getPlace() == paramInt)
      return localNetUser.uniqueName();
    List localList = NetEnv.hosts();
    for (int i = 0; i < localList.size(); i++) {
      localNetUser = (NetUser)localList.get(i);
      if (localNetUser.getPlace() == paramInt)
        return localNetUser.uniqueName();
    }
    return null;
  }

  public void _enter() {
    fillListItems();
    this.client.activateWindow();
    this.wTable.resized();
  }
  public void _leave() {
    this.client.hideWindow();
  }

  protected static void doFly()
  {
    GUINetAircraft localGUINetAircraft = (GUINetAircraft)get(44);
    localGUINetAircraft._doFly();
  }

  private void _doFly() {
    int i = ((NetUser)NetEnv.host()).getPlace();
    if (i < 0) return;

    fillListItems();
    Item localItem = (Item)this.wTable.items.get(i);
    Object localObject;
    if (localItem.iCockpitNum == 0)
    {
      localObject = World.cur().userCfg;
      int j = Main.cur().currentMissionFile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
      if (j != 1) {
        boolean bool = ((UserCfg)localObject).getWeapon(localItem.keyName) != null;
        if (bool)
          bool = Aircraft.weaponsExist(localItem.clsAircraft, ((UserCfg)localObject).getWeapon(localItem.keyName));
        if (!bool) {
          GUIAirArming.stateId = 3;
          Main.stateStack().push(55);
          return;
        }
      }
      Main.cur().resetUser();
      ((NetUser)NetEnv.host()).checkReplicateSkin(localItem.keyName);
      ((NetUser)NetEnv.host()).checkReplicateNoseart(localItem.keyName);
      ((NetUser)NetEnv.host()).checkReplicatePilot();
      String str = ((UserCfg)localObject).getWeapon(localItem.keyName);
      int k = (int)((UserCfg)localObject).fuel;
      if (j == 1) {
        str = Main.cur().currentMissionFile.get(selectedWingName(), "weapons", "default");
        k = Main.cur().currentMissionFile.get(selectedWingName(), "Fuel", 100, 0, 100);
      }
      try {
        CmdEnv.top().exec("spawn " + localItem.clsAircraft.getName() + " PLAYER NAME net" + localItem.wingName + localItem.iAircraft + " WEAPONS " + str + " FUEL " + k + (((UserCfg)localObject).netNumberOn ? "" : " NUMBEROFF") + " OVR");
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
      Aircraft localAircraft = World.getPlayerAircraft();
      if (!Actor.isValid(localAircraft)) {
        GWindowMessageBox localGWindowMessageBox = new GWindowMessageBox(Main3D.cur3D().guiManager.root, 20.0F, true, "Create aircraft", "Selected aircraft NOT created", 3, 0.0F);

        GUINetClientGuard localGUINetClientGuard = (GUINetClientGuard)Main.cur().netChannelListener;
        if (localGUINetClientGuard != null)
          localGUINetClientGuard.curMessageBox = localGWindowMessageBox;
        return;
      }
    }
    else {
      Main.cur().resetUser();
      ((NetUser)NetEnv.host()).checkReplicatePilot();
      localObject = new NetGunner("" + localItem.wingName + localItem.iAircraft, (NetUser)NetEnv.host(), 0, localItem.iCockpitNum);
    }

    ((NetUser)NetEnv.host()).doWaitStartCoopMission();
    if (Main.cur().netServerParams.isMaster()) {
      NetEnv.cur().connect.bindEnable(false);

      Main.cur().netServerParams.USGSupdate();

      if (Main.cur().netGameSpy != null) {
        Main.cur().netGameSpy.sendStatechanged();
      }
      Main.stateStack().change(45);
      Main.stateStack().change(47);
    } else {
      Main.stateStack().change(46);
      Main.stateStack().change(48);
    }
  }

  protected void init(GWindowRoot paramGWindowRoot)
  {
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("netair.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wTable = new Table(this.dialogClient);

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bLoodout = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bFly = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  public GUINetAircraft(GWindowRoot paramGWindowRoot) {
    super(44);
    init(paramGWindowRoot);
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (paramGWindow == GUINetAircraft.this.bPrev) {
        if (Main.cur().netServerParams.isMaster())
          Main.stateStack().change(45);
        else {
          Main.stateStack().change(46);
        }
        return true;
      }if (paramGWindow == GUINetAircraft.this.bLoodout) {
        if (((NetUser)NetEnv.host()).getPlace() == -1)
          return true;
        if (GUINetAircraft.selectedCockpitNum() > 0)
          return true;
        GUIAirArming.stateId = 3;
        Main.stateStack().push(55);
        return true;
      }if (paramGWindow == GUINetAircraft.this.bFly) {
        if (((NetUser)NetEnv.host()).getPlace() == -1)
          return true;
        GUINetAircraft.this._doFly();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(624.0F), x1024(924.0F), 2.5F);
      GUISeparate.draw(this, GColor.Gray, x1024(457.0F), y1024(686.0F), x1024(30.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(537.0F), y1024(686.0F), x1024(30.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(457.0F), y1024(640.0F), 1.0F, x1024(46.0F));
      GUISeparate.draw(this, GColor.Gray, x1024(567.0F), y1024(640.0F), 1.0F, x1024(46.0F));

      setCanvasColor(GColor.Gray);
      setCanvasFont(0);

      draw(x1024(680.0F), y1024(633.0F), x1024(176.0F), y1024(48.0F), 1, GUINetAircraft.this.i18n("netair.Arming"));
      draw(x1024(427.0F), y1024(633.0F), x1024(170.0F), y1024(48.0F), 1, GUINetAircraft.this.i18n("netair.Fly"));
      draw(x1024(15.0F), y1024(633.0F), x1024(140.0F), y1024(48.0F), 1, GUINetAircraft.this.i18n("netair.Brief"));
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUINetAircraft.this.wTable.set1024PosSize(32.0F, 32.0F, 960.0F, 560.0F);

      GUINetAircraft.this.bFly.setPosC(x1024(512.0F), y1024(689.0F));
      GUINetAircraft.this.bPrev.setPosC(x1024(85.0F), y1024(689.0F));
      GUINetAircraft.this.bLoodout.setPosC(x1024(768.0F), y1024(689.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList items = new ArrayList();

    public int countRows() { if (this.items == null) return 0;
      int i = this.items.size();
      NetUser localNetUser = (NetUser)NetEnv.host();
      if (localNetUser.getPlace() == -1)
        i++;
      List localList = NetEnv.hosts();
      for (int j = 0; j < localList.size(); j++) {
        localNetUser = (NetUser)localList.get(j);
        if (localNetUser.getPlace() == -1)
          i++;
      }
      return i; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      setCanvasFont(0);
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
      }
      if (paramBoolean) setCanvasColorWHITE(); else
        setCanvasColorBLACK();
      GUINetAircraft.Item localItem = null;
      if (paramInt1 < this.items.size())
        localItem = (GUINetAircraft.Item)this.items.get(paramInt1);
      String str = null;
      int i = 1;
      switch (paramInt2) {
      case 0:
        if (localItem == null) break;
        str = "" + localItem.indexInArmy;
        if (localItem.reg.getArmy() == 1) setCanvasColor(GColor.Red); else
          setCanvasColor(GColor.Blue); break;
      case 1:
        if (localItem != null) {
          float f = paramFloat2;
          setCanvasColorWHITE();
          draw(0.0F, 0.0F, f, f, localItem.texture);
          if (paramBoolean) setCanvasColorWHITE(); else
            setCanvasColorBLACK();
          draw(1.5F * f, 0.0F, paramFloat1 - 1.5F * f, paramFloat2, 0, localItem.reg.shortInfo());
        }
        return;
      case 2:
        if (localItem == null) break;
        i = 0;
        str = I18N.plane(localItem.keyName); break;
      case 3:
        if (localItem == null) break;
        str = localItem.number; break;
      case 4:
        if (localItem == null)
        {
          break;
        }

        str = localItem.cocName; break;
      case 5:
        i = 0;
        if (localItem != null) {
          str = GUINetAircraft.this.findPlayer(paramInt1);
        } else {
          int j = paramInt1 - this.items.size();
          NetUser localNetUser = (NetUser)NetEnv.host();
          if (localNetUser.getPlace() == -1) {
            if (j == 0) str = localNetUser.uniqueName(); else
              j--;
          }
          if (str != null) break;
          List localList = NetEnv.hosts();
          for (int k = 0; k < localList.size(); k++) {
            localNetUser = (NetUser)localList.get(k);
            if (localNetUser.getPlace() == -1) {
              if (j == 0) {
                str = localNetUser.uniqueName();
                break;
              }
              j--;
            }
          }
        }

      }

      if (str != null)
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, i, str); 
    }

    public void setSelect(int paramInt1, int paramInt2) {
      if ((GUINetAircraft.this.serverPlace == -1) && (this.items != null) && (paramInt1 >= 0) && (paramInt1 < this.items.size()))
      {
        ((NetUser)NetEnv.host()).requestPlace(paramInt1);
        ((NetUser)NetEnv.host()).setPilot(null);
        ((NetUser)NetEnv.host()).setSkin(null);
        ((NetUser)NetEnv.host()).setNoseart(null);
        GUINetAircraft.Item localItem = (GUINetAircraft.Item)this.items.get(paramInt1);
        EventLog.onTryOccupied("" + localItem.wingName + localItem.iAircraft, (NetUser)NetEnv.host(), Aircraft.netCockpitAstatePilotIndx(localItem.clsAircraft, localItem.iCockpitNum));
      }
      super.setSelect(paramInt1, paramInt2);
    }
    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = true;
      this.bSelecting = true;
      this.bSelectRow = true;
      addColumn("N", null);
      addColumn(I18N.gui("netair.Regiment"), null);
      addColumn(I18N.gui("netair.Plane"), null);
      addColumn(I18N.gui("netair.Number"), null);
      addColumn(I18N.gui("netair.Position"), null);
      addColumn(I18N.gui("netair.Player"), null);
      this.vSB.scroll = rowHeight(0);
      getColumn(0).setRelativeDx(1.0F);
      getColumn(1).setRelativeDx(4.0F);
      getColumn(2).setRelativeDx(4.0F);
      getColumn(3).setRelativeDx(4.0F);
      getColumn(4).setRelativeDx(4.0F);
      getColumn(5).setRelativeDx(4.0F);
      alignColumns();

      this.wClient.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super(0.0F, 0.0F, 100.0F, 100.0F);
    }
  }

  public static class Item
  {
    public int indexInArmy;
    public int iSectWing;
    public int iAircraft;
    public int iCockpitNum;
    public String cocName;
    public String wingName;
    public String keyName;
    public Regiment reg;
    public GTexture texture;
    public String number;
    public Class clsAircraft;
  }
}