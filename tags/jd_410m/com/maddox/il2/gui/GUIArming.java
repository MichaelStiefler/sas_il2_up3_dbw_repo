package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;

public class GUIArming extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIPocket[] pAircraft;
  public GWindowComboControl[] cWeapon;
  public GUIButton bAirArming;
  public GUIButton bBack;
  private Regiment regiment;
  private GTexture texRegiment;
  private Slot[] slot;
  private int playerNum;
  private int playerSlot;
  private boolean bSingleMission;

  public void enterPush(GameState paramGameState)
  {
    this.bSingleMission = (paramGameState.id() == 4);
    super.enterPush(paramGameState);
  }
  public void _enter() {
    UserCfg localUserCfg = World.cur().userCfg;
    try {
      SectFile localSectFile = Main.cur().currentMissionFile;
      String str1 = localSectFile.get("MAIN", "player", (String)null);
      String str2 = str1.substring(0, str1.length() - 1);
      String str3 = str2.substring(0, str2.length() - 1);
      this.regiment = ((Regiment)Actor.getByName(str3));
      Mat localMat = PaintScheme.makeMat(this.regiment.name(), this.regiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
      this.texRegiment = GTexture.New(localMat.Name());

      int i = localSectFile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
      World.cur().setWeaponsConstant(i == 1);

      this.playerNum = localSectFile.get("Main", "playerNum", 0);
      this.slot = new Slot[4];
      this.playerSlot = -1;
      int j = localSectFile.sectionIndex("Wing");
      int k = localSectFile.vars(j);
      Object localObject;
      int n;
      for (int m = 0; m < k; m++) {
        localObject = localSectFile.var(j, m);
        if (((String)localObject).startsWith(str2)) {
          n = ((String)localObject).charAt(((String)localObject).length() - 1) - '0';
          if (((String)localObject).equals(str1)) this.playerSlot = n;
          int i1 = localSectFile.get((String)localObject, "Planes", 0, 0, 4);
          Slot localSlot = new Slot();
          this.slot[n] = localSlot;
          localSlot.wingName = ((String)localObject);
          localSlot.players = i1;
          localSlot.fuel = localSectFile.get((String)localObject, "Fuel", 100, 0, 100);
          String str4 = localSectFile.get((String)localObject, "Class", (String)null);
          localSlot.planeClass = ObjIO.classForName(str4);
          localSlot.planeKey = Property.stringValue(localSlot.planeClass, "keyName", null);
          String str5 = localSectFile.get((String)localObject, "weapons", (String)null);
          localSlot.weapons = Aircraft.getWeaponsRegistered(localSlot.planeClass);
          localSlot.weapon = 0;
          for (int i2 = 0; i2 < localSlot.weapons.length; i2++) {
            if (localSlot.weapons[i2].equals(str5)) {
              localSlot.weapon = i2;
              break;
            }
          }
        }
      }
      for (m = 0; m < 4; m++)
        if (this.slot[m] == null) {
          this.pAircraft[m].setEnable(false);
          this.cWeapon[m].clear(false);
        }
        else {
          localObject = this.slot[m];
          if (World.cur().isWeaponsConstant()) {
            ((Slot)localObject).bEnable = false;
          }
          else if (this.bSingleMission) {
            ((Slot)localObject).bEnable = true;
          }
          else if (this.playerNum == 0) {
            if (this.playerSlot == 0)
              ((Slot)localObject).bEnable = true;
            else
              ((Slot)localObject).bEnable = (this.playerSlot == m);
          }
          else ((Slot)localObject).bEnable = false;

          this.pAircraft[m].cap = new GCaption(((Slot)localObject).players + " * " + I18N.plane(((Slot)localObject).planeKey));
          if (m == this.playerSlot)
            this.pAircraft[m].color = 255;
          else
            this.pAircraft[m].color = 0;
          this.pAircraft[m].setEnable(true);

          this.cWeapon[m].clear(false);
          if (((Slot)localObject).bEnable) {
            for (n = 0; n < ((Slot)localObject).weapons.length; n++)
            {
              if (!Aircraft.isWeaponDateOk(((Slot)localObject).planeClass, localObject.weapons[n]))
                continue;
              this.cWeapon[m].add(I18N.weapons(((Slot)localObject).planeKey, localObject.weapons[n]));
            }
            this.cWeapon[m].setSelected(((Slot)localObject).weapon, true, false);
          } else {
            this.cWeapon[m].add(I18N.weapons(((Slot)localObject).planeKey, localObject.weapons[localObject.weapon]));
            this.cWeapon[m].setSelected(0, true, false);
          }
        }
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      Main.stateStack().pop();
      return;
    }
    this.client.activateWindow();
  }

  public void _leave() {
    try {
      SectFile localSectFile = Main.cur().currentMissionFile;
      for (int i = 0; i < 4; i++) {
        if (this.slot[i] == null)
          continue;
        Slot localSlot = this.slot[i];
        if (!localSlot.bEnable)
          continue;
        localSectFile.set(localSlot.wingName, "weapons", localSlot.weapons[this.cWeapon[i].getSelected()]);
      }
      this.regiment = null;
      this.texRegiment = null;
      this.slot = null;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    World.cur().setUserCovers();
    this.client.hideWindow();
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

  public GUIArming(GWindowRoot paramGWindowRoot)
  {
    super(54);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("arming.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.pAircraft = new GUIPocket[4];
    this.cWeapon = new GWindowComboControl[4];
    for (int i = 0; i < 4; i++) {
      this.pAircraft[i] = new GUIPocket(this.dialogClient, "");
      this.pAircraft[i].setEnable(false);
      this.cWeapon[i] = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 0.0F, 0.0F, 1.0F)));
      this.cWeapon[i].setEditable(false);
    }

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.bBack = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.bAirArming = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));

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

      if (paramGWindow == GUIArming.this.bBack) {
        Main.stateStack().pop();
        return true;
      }if (paramGWindow == GUIArming.this.bAirArming) {
        if (GUIArming.this.bSingleMission) GUIAirArming.stateId = 0; else
          GUIAirArming.stateId = 1;
        Main.stateStack().push(55);
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      setCanvasColorWHITE();
      draw(x1024(80.0F), y1024(32.0F), x1024(64.0F), y1024(64.0F), GUIArming.this.texRegiment);
      setCanvasFont(1);
      draw(x1024(160.0F), y1024(48.0F), x1024(784.0F), y1024(32.0F), 0, I18N.regimentInfo(GUIArming.this.regiment.info()));

      GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(480.0F), x1024(962.0F), 2.0F);

      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(64.0F), y1024(144.0F), x1024(464.0F), y1024(32.0F), 1, GUIArming.this.i18n("arming.Aircraft"));
      draw(x1024(608.0F), y1024(144.0F), x1024(384.0F), y1024(32.0F), 1, GUIArming.this.i18n("arming.Weapons"));

      draw(x1024(96.0F), y1024(536.0F), x1024(320.0F), y1024(48.0F), 0, GUIArming.this.i18n("arming.Apply"));
      draw(x1024(430.0F), y1024(536.0F), x1024(492.0F), y1024(48.0F), 2, GUIArming.this.i18n("arming.WeaponDist"));
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 92.0F, 1024.0F, 616.0F);

      for (int i = 0; i < 4; i++) {
        GUIArming.this.pAircraft[i].set1024PosSize(32.0F, 192 + i * 64, 544.0F, 32.0F);
        GUIArming.this.cWeapon[i].set1024PosSize(608.0F, 192 + i * 64, 384.0F, 32.0F);
      }
      GUIArming.this.bBack.setPosC(x1024(56.0F), y1024(560.0F));
      GUIArming.this.bAirArming.setPosC(x1024(960.0F), y1024(560.0F));
    }
  }

  static class Slot
  {
    boolean bEnable;
    String wingName;
    int players;
    int fuel;
    Class planeClass;
    String planeKey;
    String[] weapons;
    int weapon;
  }
}