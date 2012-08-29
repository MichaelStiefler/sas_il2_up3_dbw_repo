package com.maddox.il2.builder;

import com.maddox.rts.ObjIO;
import com.maddox.rts.SectFile;

public class BldConfig
{
  public SectFile confFile;
  public String confSection;
  public int iconSize = 16;
  public boolean bSaveViewHLand = false;
  public boolean bShowLandscape = true;
  public int iLightLand = 128;
  public int iLightDestruction = 128;
  public boolean bShowGrid = true;
  public boolean bAnimateCamera = true;
  public boolean bActorOnLand = true;
  public boolean bEnableSelect = true;

  public boolean bViewBridge = true;
  public boolean bViewRunaway = true;

  public boolean bShowName = true;
  public boolean bShowTime = true;
  public boolean[] bShowArmy = new boolean[Builder.armyAmount()];
  public int iWaterLevel = 191;

  public void save()
  {
    this.confFile.set(this.confSection, this);
    this.confFile.saveFile();
  }
  public void load(SectFile paramSectFile, String paramString) {
    this.confFile = paramSectFile;
    this.confSection = paramString;
    this.confFile.get(this.confSection, this, BldConfig.class);
    if (this.iWaterLevel < 0) this.iWaterLevel = 0;
    if (this.iWaterLevel > 255) this.iWaterLevel = 255;
  }

  static
  {
    ObjIO.fields(class$com$maddox$il2$builder$BldConfig, new String[] { "iconSize", "bSaveViewHLand", "bShowLandscape", "iLightLand", "iLightDestruction", "bShowGrid", "bAnimateCamera", "bActorOnLand", "bViewBridge", "bViewRunaway", "bShowName", "bShowTime", "bShowArmy", "iWaterLevel" });
  }
}