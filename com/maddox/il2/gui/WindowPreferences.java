package com.maddox.il2.gui;

import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.engine.CfgGObj;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CfgTools;
import com.maddox.rts.ScreenMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class WindowPreferences
{
  public static GWindowFramed framed;
  public static GWindowComboControl comboResolution;
  public static ArrayList screenModes = new ArrayList();

  public static int _findVideoMode(ScreenMode paramScreenMode) {
    for (int i = 0; i < screenModes.size(); i++) {
      ScreenMode localScreenMode = (ScreenMode)screenModes.get(i);
      if ((localScreenMode.width() == paramScreenMode.width()) && (localScreenMode.height() == paramScreenMode.height()) && (localScreenMode.colourBits() == paramScreenMode.colourBits()))
      {
        return i;
      }
    }
    return 0;
  }

  public static GWindowFramed create(GWindowRoot paramGWindowRoot)
  {
    framed = (GWindowFramed)paramGWindowRoot.create(1.666667F, 8.333333F, 33.333332F, 25.0F, true, new GWindowFramed());

    framed.title = "Preferences";
    GWindowTabDialogClient localGWindowTabDialogClient = (GWindowTabDialogClient)framed.create(new GWindowTabDialogClient());
    framed.clientWindow = localGWindowTabDialogClient;

    ScreenMode[] arrayOfScreenMode = ScreenMode.all();
    for (int i = 0; i < arrayOfScreenMode.length; i++) {
      localObject1 = arrayOfScreenMode[i];
      if ((((ScreenMode)localObject1).colourBits() >= 15) && (((ScreenMode)localObject1).height() >= 240.0F)) {
        screenModes.add(localObject1);
      }
    }
    if (screenModes.size() > 0) {
      localObject1 = (GWindowScrollingDialogClient)localGWindowTabDialogClient.create(new GWindowScrollingDialogClient());

      localGWindowTabDialogClient.addTab("Video", (GWindow)localObject1);
      localGWindowDialogClient1 = (GWindowDialogClient)((GWindowScrollingDialogClient)localObject1).create(new GWindowDialogClient() {
        public void created() { super.created();
          setMetricSize(24.0F, 14.0F); }

        public GSize getMinSize(GSize paramGSize) {
          paramGSize.dx = lookAndFeel().metric(24.0F);
          paramGSize.dy = lookAndFeel().metric(14.0F);
          return paramGSize;
        }

        public void render()
        {
        }
      });
      ((GWindowScrollingDialogClient)localObject1).fixed = localGWindowDialogClient1;

      localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 2.0F, 8.0F, localGWindowDialogClient1.lookAndFeel().getComboHmetric(), "Resolution", null));

      localGWindowDialogClient1.addControl(WindowPreferences.comboResolution = new GWindowComboControl(localGWindowDialogClient1, 10.0F, 2.0F, 12.0F)
      {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2)
          {
            new WindowPreferences.3(this, 64, 0.2D, "");

            return true;
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      comboResolution.setEditable(false);
      for (int j = 0; j < screenModes.size(); j++) {
        ScreenMode localScreenMode = (ScreenMode)screenModes.get(j);
        comboResolution.add(localScreenMode.width() + "x" + localScreenMode.height() + "x" + localScreenMode.colourBits());
      }
      comboResolution.setSelected(_findVideoMode(ScreenMode.current()), true, false);
      comboResolution.resized();
    }

    Object localObject1 = (GWindowScrollingDialogClient)localGWindowTabDialogClient.create(new GWindowScrollingDialogClient());

    localGWindowTabDialogClient.addTab("Render", (GWindow)localObject1);
    GWindowDialogClient localGWindowDialogClient1 = (GWindowDialogClient)((GWindowScrollingDialogClient)localObject1).create(new GWindowDialogClient() {
      public void created() { super.created();
        setMetricSize(24.0F, 53.0F); }

      public GSize getMinSize(GSize paramGSize) {
        paramGSize.dx = lookAndFeel().metric(24.0F);
        paramGSize.dy = lookAndFeel().metric(14.0F);
        return paramGSize;
      }

      public void render()
      {
      }
    });
    ((GWindowScrollingDialogClient)localObject1).fixed = localGWindowDialogClient1;

    createCfgInt(localGWindowDialogClient1, 2.0F, 2.0F, 8.0F, 10.0F, 8.0F, "TexQual", true);
    createCfgInt(localGWindowDialogClient1, 4.0F, 2.0F, 8.0F, 10.0F, 8.0F, "TexMipFilter", true);
    createCfgInt(localGWindowDialogClient1, 6.0F, 2.0F, 8.0F, 10.0F, 8.0F, "LandDetails", true);
    createCfgInt(localGWindowDialogClient1, 8.0F, 2.0F, 8.0F, 10.0F, 8.0F, "LandShading", true);
    createCfgInt(localGWindowDialogClient1, 10.0F, 2.0F, 8.0F, 10.0F, 8.0F, "Forest", true);
    createCfgInt(localGWindowDialogClient1, 12.0F, 2.0F, 8.0F, 10.0F, 8.0F, "Sky", true);

    createCfgInt(localGWindowDialogClient1, 15.0F, 2.0F, 8.0F, 10.0F, 8.0F, "DynamicalLights", true);
    createCfgInt(localGWindowDialogClient1, 17.0F, 2.0F, 8.0F, 10.0F, 8.0F, "DiffuseLight", true);
    createCfgInt(localGWindowDialogClient1, 19.0F, 2.0F, 8.0F, 10.0F, 8.0F, "SpecularLight", true);
    createCfgInt(localGWindowDialogClient1, 21.0F, 2.0F, 8.0F, 10.0F, 8.0F, "Specular", true);
    createCfgInt(localGWindowDialogClient1, 23.0F, 2.0F, 8.0F, 10.0F, 8.0F, "Shadows", true);
    createCfgFlag(localGWindowDialogClient1, 25.0F, 10.0F, 2.0F, 8.0F, "ShadowsFlags", 0, true);

    CfgFlags localCfgFlags = (CfgFlags)CfgTools.get("TexFlags");
    int k = localCfgFlags.firstFlag();
    int m = localCfgFlags.countFlags();
    for (int n = 0; n < m; n++) {
      createCfgFlag(localGWindowDialogClient1, 28.0F + n * 1.5F, 14.0F, 2.0F, 12.0F, "TexFlags", n + k, true);
    }

    GWindowScrollingDialogClient localGWindowScrollingDialogClient = (GWindowScrollingDialogClient)localGWindowTabDialogClient.create(new GWindowScrollingDialogClient());

    localGWindowTabDialogClient.addTab("Sound", localGWindowScrollingDialogClient);
    GWindowDialogClient localGWindowDialogClient2 = (GWindowDialogClient)localGWindowScrollingDialogClient.create(new GWindowDialogClient() {
      public void created() { super.created();
        setMetricSize(24.0F, 28.0F); }

      public GSize getMinSize(GSize paramGSize) {
        paramGSize.dx = lookAndFeel().metric(24.0F);
        paramGSize.dy = lookAndFeel().metric(14.0F);
        return paramGSize;
      }

      public void render()
      {
      }
    });
    localGWindowScrollingDialogClient.fixed = localGWindowDialogClient2;

    float f = 2.0F;
    Collection localCollection = CfgTools.all();
    Iterator localIterator = localCollection.iterator();
    Object localObject2;
    Object localObject3;
    while (localIterator.hasNext()) {
      localObject2 = localIterator.next();
      if (((localObject2 instanceof CfgInt)) && (!(localObject2 instanceof CfgGObj))) {
        localObject3 = (CfgInt)localObject2;
        if (((CfgInt)localObject3).countStates() > 8) createSlider(localGWindowDialogClient2, f, 2.0F, 8.0F, 10.0F, 8.0F, ((CfgInt)localObject3).name(), true); else
          createCfgInt(localGWindowDialogClient2, f, 2.0F, 8.0F, 10.0F, 8.0F, ((CfgInt)localObject3).name(), true);
        f += 2.0F;
      }
    }
    f += 3.0F;
    localCollection = CfgTools.all();
    localIterator = localCollection.iterator();
    while (localIterator.hasNext()) {
      localObject2 = localIterator.next();
      if (((localObject2 instanceof CfgFlags)) && (!(localObject2 instanceof CfgGObj))) {
        localObject3 = (CfgFlags)localObject2;
        k = ((CfgFlags)localObject3).firstFlag();
        m = ((CfgFlags)localObject3).countFlags();
        for (int i1 = 0; i1 < m; i1++) {
          createCfgFlag(localGWindowDialogClient2, f, 14.0F, 2.0F, 12.0F, ((CfgFlags)localObject3).name(), i1 + k, true);
          f += 1.5F;
        }
      }
    }

    framed.resized();
    framed.close(false);
    return (GWindowFramed)(GWindowFramed)framed;
  }

  private static SliderCfgInt createSlider(GWindowDialogClient paramGWindowDialogClient, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, String paramString, boolean paramBoolean)
  {
    String str1 = paramString;
    String str2 = paramString + " toolTip ???";
    paramGWindowDialogClient.addLabel(new GWindowLabel(paramGWindowDialogClient, paramFloat2, paramFloat1, paramFloat3, paramGWindowDialogClient.lookAndFeel().getHSliderIntHmetric(), str1, str2));

    CfgInt localCfgInt = (CfgInt)CfgTools.get(paramString);
    SliderCfgInt localSliderCfgInt = new SliderCfgInt(paramGWindowDialogClient, localCfgInt.firstState(), localCfgInt.countStates(), 0, paramFloat4, paramFloat1, paramFloat5, localCfgInt, paramBoolean);

    localSliderCfgInt.setToolTip(str2);
    paramGWindowDialogClient.addControl(localSliderCfgInt);
    return localSliderCfgInt;
  }

  private static ComboCfgInt createCfgInt(GWindowDialogClient paramGWindowDialogClient, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, String paramString, boolean paramBoolean)
  {
    String str1 = paramString;
    String str2 = paramString + " toolTip ???";
    paramGWindowDialogClient.addLabel(new GWindowLabel(paramGWindowDialogClient, paramFloat2, paramFloat1, paramFloat3, paramGWindowDialogClient.lookAndFeel().getComboHmetric(), str1, str2));

    CfgInt localCfgInt = (CfgInt)CfgTools.get(paramString);
    ComboCfgInt localComboCfgInt = new ComboCfgInt(paramGWindowDialogClient, paramFloat4, paramFloat1, paramFloat5, localCfgInt, paramBoolean);
    localComboCfgInt.setToolTip(str2);
    paramGWindowDialogClient.addControl(localComboCfgInt);
    return localComboCfgInt;
  }

  private static CheckCfgFlag createCfgFlag(GWindowDialogClient paramGWindowDialogClient, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString, int paramInt, boolean paramBoolean)
  {
    CfgFlags localCfgFlags = (CfgFlags)CfgTools.get(paramString);

    String str1 = localCfgFlags.nameFlag(paramInt);
    String str2 = str1 + " toolTip ???";
    CheckCfgFlag localCheckCfgFlag = new CheckCfgFlag(paramGWindowDialogClient, paramFloat2, paramFloat1, localCfgFlags, paramInt, paramBoolean);
    localCheckCfgFlag.setToolTip(str2);
    paramGWindowDialogClient.addControl(new LabelFlag(paramGWindowDialogClient, paramFloat3, paramFloat1, paramFloat4, str1, str2, localCheckCfgFlag));
    return localCheckCfgFlag;
  }

  static class CheckCfgFlag extends GWindowCheckBox
  {
    public CfgFlags cfg;
    public int iFlag;
    public boolean bUpdate;
    public boolean bChanged;

    public CheckCfgFlag(GWindowDialogClient paramGWindowDialogClient, float paramFloat1, float paramFloat2, CfgFlags paramCfgFlags, int paramInt, boolean paramBoolean)
    {
      super(paramFloat1, paramFloat2, null);
      this.cfg = paramCfgFlags;
      this.iFlag = paramInt;
      this.bUpdate = paramBoolean;
      this.bChanged = false;
      setChecked(paramCfgFlags.get(paramInt), false);
      setEnable(paramCfgFlags.isEnabledFlag(paramInt));
      paramGWindowDialogClient.addControl(this);
    }
    public void resolutionChanged() {
      super.resolutionChanged();
      this.cfg.reset();
      setChecked(this.cfg.get(this.iFlag), false);
      setEnable(this.cfg.isEnabledFlag(this.iFlag));
    }
    public boolean notify(int paramInt1, int paramInt2) {
      if (paramInt1 == 2) {
        this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
        if (this.bUpdate) {
          this.cfg.set(this.iFlag, isChecked());
          int i = this.cfg.apply(this.iFlag);
          this.cfg.reset();
          this.cfg.applyExtends(i);
          boolean bool = this.cfg.get(this.iFlag);
          if (bool != isChecked())
            setChecked(bool, false);
        } else {
          this.bChanged = true;
        }
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }
  }

  static class LabelFlag extends GWindowLabel
  {
    WindowPreferences.CheckCfgFlag checkCfg;

    public LabelFlag(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, String paramString1, String paramString2, WindowPreferences.CheckCfgFlag paramCheckCfgFlag)
    {
      super(paramFloat1, paramFloat2 - 0.1F, paramFloat3, 1.2F, paramString1, paramString2);
      this.checkCfg = paramCheckCfgFlag;
      setEnable(paramCheckCfgFlag.isEnable());
    }
    public boolean notify(int paramInt1, int paramInt2) {
      if (paramInt1 == 2) {
        this.checkCfg.notify(2, 0);
      }
      return super.notify(paramInt1, paramInt2);
    }
  }

  static class ComboCfgInt extends GWindowComboControl
  {
    public CfgInt cfg;
    public boolean bUpdate;
    public boolean bChanged;

    public ComboCfgInt(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, CfgInt paramCfgInt, boolean paramBoolean)
    {
      super(paramFloat1, paramFloat2, paramFloat3);

      setEditable(false);
      this.bUpdate = paramBoolean;
      this.bChanged = false;
      this.cfg = paramCfgInt;
      refresh(true);
      int i = paramCfgInt.firstState();
      setSelected(paramCfgInt.get() - i, true, false);
      setEnable(paramCfgInt.isEnabled());
    }
    public void refresh(boolean paramBoolean) {
      int i = this.cfg.countStates();
      int j = this.cfg.firstState();
      if (this.jdField_posEnable_of_type_ArrayOfBoolean == null)
        this.jdField_posEnable_of_type_ArrayOfBoolean = new boolean[i];
      for (int k = 0; k < i; k++) {
        if (paramBoolean)
          add(this.cfg.nameState(k + j));
        this.jdField_posEnable_of_type_ArrayOfBoolean[k] = this.cfg.isEnabledState(k + j);
      }
      this.cfg.reset();
    }
    public void resolutionChanged() {
      super.resolutionChanged();
      refresh(false);
    }
    public boolean notify(int paramInt1, int paramInt2) {
      if (paramInt1 == 2) {
        int i = this.cfg.get() - this.cfg.firstState();
        if (i != getSelected()) {
          if (this.bUpdate) {
            this.cfg.set(getSelected() + this.cfg.firstState());
            int j = this.cfg.apply();
            this.cfg.reset();
            this.cfg.applyExtends(j);
            int k = this.cfg.get() - this.cfg.firstState();
            if (k != getSelected())
              setSelected(k, true, false);
          } else {
            this.bChanged = true;
          }
        }
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }
  }

  static class SliderCfgInt extends GWindowHSliderInt
  {
    public CfgInt cfg;
    public boolean bUpdate;
    public boolean bChanged;

    public SliderCfgInt(GWindow paramGWindow, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3, CfgInt paramCfgInt, boolean paramBoolean)
    {
      super(paramInt1, paramInt2, paramInt3, paramFloat1, paramFloat2, paramFloat3);

      this.bUpdate = paramBoolean;
      this.bChanged = false;
      this.cfg = paramCfgInt;
      int i = paramCfgInt.countStates();
      int j = paramCfgInt.firstState();
      this.jdField_posEnable_of_type_ArrayOfBoolean = new boolean[i];
      for (int k = 0; k < i; k++) {
        this.jdField_posEnable_of_type_ArrayOfBoolean[k] = paramCfgInt.isEnabledState(k + j);
      }
      paramCfgInt.reset();
      setPos(paramCfgInt.get(), false);
      setEnable(paramCfgInt.isEnabled());
    }
    public boolean notify(int paramInt1, int paramInt2) {
      if (paramInt1 == 2) {
        int i = this.cfg.get() - this.cfg.firstState();
        if (i != pos()) {
          if (this.bUpdate) {
            this.cfg.set(pos());
            int j = this.cfg.apply();
            this.cfg.reset();
            this.cfg.applyExtends(j);
            int k = this.cfg.get();
            if (k != pos())
              setPos(k, false);
          } else {
            this.bChanged = true;
          }
        }
        return true;
      }
      return super.notify(paramInt1, paramInt2);
    }
  }
}