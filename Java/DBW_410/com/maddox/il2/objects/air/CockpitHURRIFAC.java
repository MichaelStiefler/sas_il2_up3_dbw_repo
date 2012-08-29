// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 8/01/2011 9:48:15 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CockpitHURRII.java

package com.maddox.il2.objects.air;

import com.maddox.sound.ReverbFXRoom;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitPilot, Cockpit, Aircraft

public class CockpitHURRIFAC extends CockpitHURRII
{
    public CockpitHURRIFAC()
    {
        if(acoustics != null)
            acoustics.globFX = new ReverbFXRoom(0.0F);
    }

    public void reflectWorldToInstruments(float f)
    {
    	super.reflectWorldToInstruments(f);
        mesh.chunkVisible("FONAR2", false);
    	mesh.chunkVisible("FONAR_GLASS2", false);
    	mesh.chunkVisible("XGlassDamage2", false);
    	mesh.chunkVisible("XGlassDamage3", false);
    	mesh.chunkVisible("XGlassDamage4", false);
    }

}