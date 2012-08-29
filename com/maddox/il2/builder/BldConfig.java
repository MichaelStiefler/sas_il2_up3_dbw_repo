// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BldConfig.java

package com.maddox.il2.builder;

import com.maddox.rts.ObjIO;
import com.maddox.rts.SectFile;

// Referenced classes of package com.maddox.il2.builder:
//            Builder

public class BldConfig
{

    public BldConfig()
    {
        iconSize = 16;
        bSaveViewHLand = false;
        bShowLandscape = true;
        iLightLand = 128;
        iLightDestruction = 128;
        bShowGrid = true;
        bAnimateCamera = true;
        bActorOnLand = true;
        bEnableSelect = true;
        bViewBridge = true;
        bViewRunaway = true;
        bShowName = true;
        bShowTime = true;
        bShowArmy = new boolean[com.maddox.il2.builder.Builder.armyAmount()];
        iWaterLevel = 191;
        defFullDX = 32F;
        defFullDY = 24F;
    }

    public void save()
    {
        confFile.set(confSection, this);
        confFile.saveFile();
    }

    public void load(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        confFile = sectfile;
        confSection = s;
        confFile.get(confSection, this, com.maddox.il2.builder.BldConfig.class);
        if(iWaterLevel < 0)
            iWaterLevel = 0;
        if(iWaterLevel > 255)
            iWaterLevel = 255;
    }

    public com.maddox.rts.SectFile confFile;
    public java.lang.String confSection;
    public int iconSize;
    public boolean bSaveViewHLand;
    public boolean bShowLandscape;
    public int iLightLand;
    public int iLightDestruction;
    public boolean bShowGrid;
    public boolean bAnimateCamera;
    public boolean bActorOnLand;
    public boolean bEnableSelect;
    public boolean bViewBridge;
    public boolean bViewRunaway;
    public boolean bShowName;
    public boolean bShowTime;
    public boolean bShowArmy[];
    public int iWaterLevel;
    public float defFullDX;
    public float defFullDY;

    static 
    {
        com.maddox.rts.ObjIO.fields(com.maddox.il2.builder.BldConfig.class, new java.lang.String[] {
            "iconSize", "bSaveViewHLand", "bShowLandscape", "iLightLand", "iLightDestruction", "bShowGrid", "bAnimateCamera", "bActorOnLand", "bViewBridge", "bViewRunaway", 
            "bShowName", "bShowTime", "bShowArmy", "iWaterLevel", "defFullDX", "defFullDY"
        });
    }
}
