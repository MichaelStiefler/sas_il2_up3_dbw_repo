package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CfgGObj;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Cfg;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;

public class GUISetup3D1 extends GameState
{
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public ComboCfg cCommon;
  public ComboCfg cVisible;
  public ComboCfg cObjLight;
  public ComboCfg cObjDetail;
  public ComboCfg cLandLight;
  public ComboCfg cLandDetail;
  public ComboCfg cSky;
  public GUIButton bApply;
  public GUIButton bCustom;
  public GUIButton bExit;
  private boolean bCommon;
  private boolean bRestoreRenderFocus = false;

  public void _enter() {
    if ((Mission.isPlaying()) && (RendersMain.getRenderFocus() == (Render)Actor.getByName("renderGUI")))
    {
      RendersMain.setRenderFocus(null);
      this.bRestoreRenderFocus = true;
    } else {
      this.bRestoreRenderFocus = false;
    }
    this.bCommon = this.cCommon.reset();
    this.dialogClient.setPosSize();
    this.client.activateWindow();
  }
  public void _leave() {
    if (this.bRestoreRenderFocus)
      RendersMain.setRenderFocus((Render)Actor.getByName("renderGUI"));
    this.client.hideWindow();
  }

  public GUISetup3D1(GWindowRoot paramGWindowRoot)
  {
    super(11);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));

    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("setup3d.info");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.cVisible = new ComboCfg(this.dialogClient, new Object[] { CfgTools.get("VisibilityDistance") }, new int[][] { { 0 }, { 1 }, { 2 }, { 3 } }, new String[] { i18n("setup3d.Visible0"), i18n("setup3d.Visible1"), i18n("setup3d.Visible2"), i18n("setup3d.Visible3") });

    this.cObjLight = new ComboCfg(this.dialogClient, new Object[] { CfgTools.get("DiffuseLight"), CfgTools.get("SpecularLight"), CfgTools.get("DynamicalLights"), CfgTools.get("Specular") }, new int[][] { { 0, 0, 0, 1 }, { 1, 1, 1, 1 }, { 2, 2, 1, 2 } }, new String[] { i18n("setup3d.ObjLight0"), i18n("setup3d.ObjLight1"), i18n("setup3d.ObjLight2") });

    this.cObjDetail = new ComboCfg(this.dialogClient, new Object[] { CfgTools.get("MeshDetail"), CfgTools.get("TexQual"), CfgTools.get("TexLarge") }, new int[][] { { 0, 1, 0 }, { 1, 2, 0 }, { 2, 3, 0 }, { 2, 3, 1 } }, new String[] { i18n("setup3d.ObjDetail0"), i18n("setup3d.ObjDetail1"), i18n("setup3d.ObjDetail2"), i18n("setup3d.ObjDetail3") });

    this.cLandLight = new ComboCfg(this.dialogClient, new Object[] { CfgTools.get("LandShading"), CfgTools.get("Shadows") }, new int[][] { { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 } }, new String[] { i18n("setup3d.LandLight0"), i18n("setup3d.LandLight1"), i18n("setup3d.LandLight2"), i18n("setup3d.LandLight3") });

    this.cLandDetail = new ComboCfg(this.dialogClient, new Object[] { CfgTools.get("Forest"), CfgTools.get("LandDetails"), CfgTools.get("LandGeom"), CfgTools.get("TexLandQual"), CfgTools.get("TexLandLarge"), CfgTools.get("HardwareShaders") }, new int[][] { { 0, 0, 0, 1, 0, 0 }, { 0, 0, 1, 2, 0, 0 }, { 1, 1, 2, 3, 0, 0 }, { 1, 2, 2, 3, 0, 0 }, { 2, 2, 2, 3, 1, 0 }, { 2, 2, 2, 3, 1, 1 } }, new String[] { i18n("setup3d.LandDetail0"), i18n("setup3d.LandDetail1"), i18n("setup3d.LandDetail2"), i18n("setup3d.LandDetail3"), i18n("setup3d.LandDetail4"), i18n("setup3d.LandDetail5") });

    this.cSky = new ComboCfgSky(this.dialogClient, new Object[] { CfgTools.get("Sky") }, new int[][] { { 0 }, { 1 }, { 2 } }, new String[] { i18n("setup3d.Sky0"), i18n("setup3d.Sky1"), i18n("setup3d.Sky2") });

    this.cCommon = new ComboCfg(this.dialogClient, new Object[] { this.cVisible, this.cObjLight, this.cObjDetail, this.cLandLight, this.cLandDetail, this.cSky }, new int[][] { { 0, 0, 1, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 1 }, { 2, 2, 2, 2, 3, 2 }, { 3, 2, 3, 3, 4, 2 } }, new String[] { i18n("setup3d.Common0"), i18n("setup3d.Common1"), i18n("setup3d.Common2"), i18n("setup3d.Common3"), i18n("setup3d.Common4") });

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;

    this.bApply = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bCustom = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.bExit = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));

    this.bCommon = false;
    this.cCommon.hideWindow();
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

      if (paramGWindow == GUISetup3D1.this.bApply) {
        int i = 0;
        if (GUISetup3D1.this.bCommon) {
          i |= GUISetup3D1.this.cCommon.apply();
        } else {
          i |= GUISetup3D1.this.cVisible.apply();
          i |= GUISetup3D1.this.cObjLight.apply();
          i |= GUISetup3D1.this.cObjDetail.apply();
          i |= GUISetup3D1.this.cLandLight.apply();
          i |= GUISetup3D1.this.cLandDetail.apply();
          i |= GUISetup3D1.this.cSky.apply();
        }
        CfgGObj.ApplyExtends(i);
        GUISetup3D1.this.cCommon.reset();
        return true;
      }if (paramGWindow == GUISetup3D1.this.bCustom) {
        GUISetup3D1.access$002(GUISetup3D1.this, !GUISetup3D1.this.bCommon);
        GUISetup3D1.this.cCommon.reset();
        setPosSize();
        return true;
      }if (paramGWindow == GUISetup3D1.this.bExit) {
        Main.stateStack().pop();
        return true;
      }
      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      if (GUISetup3D1.this.bCommon) {
        GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(104.0F), x1024(416.0F), 2.0F);
        setCanvasFont(0);
        setCanvasColor(GColor.Gray);
        draw(x1024(48.0F), y1024(24.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.Common"));

        draw(x1024(80.0F), y1024(176.0F), x1024(160.0F), y1024(48.0F), 0, GUISetup3D1.this.i18n("setup3d.Back"));
        draw(x1024(144.0F), y1024(120.0F), x1024(256.0F), y1024(48.0F), 2, GUISetup3D1.this.i18n("setup3d.Custom"));
        draw(x1024(240.0F), y1024(176.0F), x1024(160.0F), y1024(48.0F), 2, GUISetup3D1.this.i18n("setup3d.Apply"));
      } else {
        GUISeparate.draw(this, GColor.Gray, x1024(32.0F), y1024(504.0F), x1024(416.0F), 2.0F);
        setCanvasFont(0);
        setCanvasColor(GColor.Gray);
        draw(x1024(48.0F), y1024(24.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.Visible"));
        draw(x1024(48.0F), y1024(104.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.ObjLight"));
        draw(x1024(48.0F), y1024(184.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.ObjDetail"));
        draw(x1024(48.0F), y1024(264.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.LandLight"));
        draw(x1024(48.0F), y1024(344.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.LandDetail"));
        draw(x1024(48.0F), y1024(424.0F), x1024(384.0F), y1024(32.0F), 1, GUISetup3D1.this.i18n("setup3d.Sky"));

        draw(x1024(80.0F), y1024(576.0F), x1024(160.0F), y1024(48.0F), 0, GUISetup3D1.this.i18n("setup3d.Back"));
        draw(x1024(144.0F), y1024(520.0F), x1024(256.0F), y1024(48.0F), 2, GUISetup3D1.this.i18n("setup3d.Simple"));
        draw(x1024(240.0F), y1024(576.0F), x1024(160.0F), y1024(48.0F), 2, GUISetup3D1.this.i18n("setup3d.Apply"));
      }
    }

    public void setPosSize()
    {
      if (GUISetup3D1.this.bCommon) {
        GUISetup3D1.this.cCommon.showWindow();
        GUISetup3D1.this.cVisible.hideWindow();
        GUISetup3D1.this.cObjLight.hideWindow();
        GUISetup3D1.this.cObjDetail.hideWindow();
        GUISetup3D1.this.cLandLight.hideWindow();
        GUISetup3D1.this.cLandDetail.hideWindow();
        GUISetup3D1.this.cSky.hideWindow();

        set1024PosSize(0.0F, 512.0F, 480.0F, 256.0F);
        GUISetup3D1.this.cCommon.set1024PosSize(80.0F, 56.0F, 320.0F, 32.0F);

        GUISetup3D1.this.bExit.setPosC(x1024(56.0F), y1024(200.0F));
        GUISetup3D1.this.bCustom.setPosC(x1024(424.0F), y1024(144.0F));
        GUISetup3D1.this.bApply.setPosC(x1024(424.0F), y1024(200.0F));
      } else {
        GUISetup3D1.this.cCommon.hideWindow();
        GUISetup3D1.this.cVisible.showWindow();
        GUISetup3D1.this.cObjLight.showWindow();
        GUISetup3D1.this.cObjDetail.showWindow();
        GUISetup3D1.this.cLandLight.showWindow();
        GUISetup3D1.this.cLandDetail.showWindow();
        GUISetup3D1.this.cSky.showWindow();

        set1024PosSize(0.0F, 112.0F, 480.0F, 656.0F);

        GUISetup3D1.this.cVisible.set1024PosSize(80.0F, 56.0F, 320.0F, 32.0F);
        GUISetup3D1.this.cObjLight.set1024PosSize(80.0F, 136.0F, 320.0F, 32.0F);
        GUISetup3D1.this.cObjDetail.set1024PosSize(80.0F, 216.0F, 320.0F, 32.0F);
        GUISetup3D1.this.cLandLight.set1024PosSize(80.0F, 296.0F, 320.0F, 32.0F);
        GUISetup3D1.this.cLandDetail.set1024PosSize(80.0F, 376.0F, 320.0F, 32.0F);
        GUISetup3D1.this.cSky.set1024PosSize(80.0F, 456.0F, 320.0F, 32.0F);

        GUISetup3D1.this.bExit.setPosC(x1024(56.0F), y1024(600.0F));
        GUISetup3D1.this.bCustom.setPosC(x1024(424.0F), y1024(544.0F));
        GUISetup3D1.this.bApply.setPosC(x1024(424.0F), y1024(600.0F));
      }
    }
  }

  public class ComboCfgSky extends GUISetup3D1.ComboCfg
  {
    public boolean reset()
    {
      boolean bool = super.reset();
      this.posEnable[0] = (!Mission.isNet() ? 1 : false);
      return bool;
    }
    public ComboCfgSky(GWindow paramArrayOfObject, Object[] paramArrayOfInt, int[][] paramArrayOfString, String[] arg5) {
      super(paramArrayOfObject, paramArrayOfInt, paramArrayOfString, arrayOfString);
    }
  }

  public class ComboCfg extends GWindowComboControl
  {
    public Object[] cfg;
    public int[][] state;

    public boolean reset()
    {
      for (int i = 0; i < this.cfg.length; i++) {
        if ((this.cfg[i] instanceof Cfg))
          ((Cfg)this.cfg[i]).reset();
        else {
          ((ComboCfg)this.cfg[i]).reset();
        }
      }
      int j = this.state.length;
      int k = 0;
      if (this.jdField_posEnable_of_type_ArrayOfBoolean != null) {
        for (; k < j; k++) {
          int[] arrayOfInt1 = this.state[k];
          this.jdField_posEnable_of_type_ArrayOfBoolean[k] = true;
          for (int n = 0; n < this.cfg.length; n++)
          {
            Object localObject1;
            if ((this.cfg[n] instanceof CfgFlags)) {
              localObject1 = (CfgFlags)this.cfg[n];
              if (!((CfgFlags)localObject1).isEnabledFlag(0))
                this.jdField_posEnable_of_type_ArrayOfBoolean[k] = false;
            } else if ((this.cfg[n] instanceof CfgInt)) {
              localObject1 = (CfgInt)this.cfg[n];
              if (!((CfgInt)localObject1).isEnabledState(arrayOfInt1[n]))
                this.jdField_posEnable_of_type_ArrayOfBoolean[k] = false;
            }
          }
        }
      }
      k = 0;
      int m = 1;
      for (; k < j; k++) {
        m = 1;
        int[] arrayOfInt2 = this.state[k];
        for (int i1 = 0; i1 < this.cfg.length; i1++)
        {
          Object localObject2;
          if ((this.cfg[i1] instanceof CfgFlags)) {
            localObject2 = (CfgFlags)this.cfg[i1];
            m = ((CfgFlags)localObject2).get(0) == (arrayOfInt2[i1] != 0) ? 1 : 0;
          }
          else
          {
            int i2;
            int i3;
            if ((this.cfg[i1] instanceof CfgInt)) {
              localObject2 = (CfgInt)this.cfg[i1];
              i2 = ((CfgInt)localObject2).get();
              i3 = arrayOfInt2[i1];
              while ((i3 > 0) && (!((CfgInt)localObject2).isEnabledState(i3)))
                i3--;
              m = i2 == i3 ? 1 : 0;
            } else {
              localObject2 = (ComboCfg)this.cfg[i1];
              i2 = ((ComboCfg)localObject2).getSelected();
              i3 = arrayOfInt2[i1];
              while ((i3 > 0) && (localObject2.jdField_posEnable_of_type_ArrayOfBoolean[i3] == 0))
                i3--;
              m = i2 == i3 ? 1 : 0;
            }
          }
          if (m == 0)
            break;
        }
        if (m != 0)
          break;
      }
      if (k == j)
        k = j - 1;
      if (this.jdField_posEnable_of_type_ArrayOfBoolean != null) {
        while (k > 0) {
          if (this.jdField_posEnable_of_type_ArrayOfBoolean[k] != 0)
            break;
          k--;
        }
      }
      setSelected(k, true, false);
      return m;
    }

    public int apply() {
      int i = 0;
      int j = getSelected();
      int[] arrayOfInt = this.state[j];
      for (int k = 0; k < this.cfg.length; k++)
      {
        Object localObject;
        if ((this.cfg[k] instanceof CfgFlags)) {
          localObject = (CfgFlags)this.cfg[k];
          ((CfgFlags)localObject).set(0, arrayOfInt[k] != 0);
          i |= ((CfgFlags)localObject).apply();
        }
        else
        {
          int m;
          if ((this.cfg[k] instanceof CfgInt)) {
            localObject = (CfgInt)this.cfg[k];
            m = arrayOfInt[k];
            while ((m > 0) && (!((CfgInt)localObject).isEnabledState(m)))
              m--;
            ((CfgInt)localObject).set(m);
            i |= ((CfgInt)localObject).apply();
          } else {
            localObject = (ComboCfg)this.cfg[k];
            m = arrayOfInt[k];
            while ((m > 0) && (localObject.jdField_posEnable_of_type_ArrayOfBoolean[m] == 0))
              m--;
            ((ComboCfg)localObject).setSelected(m, true, false);
            i |= ((ComboCfg)localObject).apply();
          }
        }
      }
      return i;
    }

    public ComboCfg(GWindow paramArrayOfObject, Object[] paramArrayOfInt, int[][] paramArrayOfString, String[] arg5) {
      super(0.0F, 0.0F, 10.0F);
      setEditable(false);
      this.cfg = paramArrayOfInt;
      this.state = paramArrayOfString;
      Object localObject;
      for (int i = 0; i < localObject.length; i++)
        add(localObject[i]);
      if (!(paramArrayOfInt[0] instanceof ComboCfg))
        this.jdField_posEnable_of_type_ArrayOfBoolean = new boolean[localObject.length];
      reset();
    }
  }
}