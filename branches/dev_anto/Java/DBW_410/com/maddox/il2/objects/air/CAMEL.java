// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 20/03/2011 10:18:24 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CAMEL.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SOPWITH, PaintSchemeFMParWW1, Aircraft, NetAircraft, 
//            PaintScheme

public class CAMEL extends SOPWITH
{

    public CAMEL()
    {
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(super.FM.isPlayers())
        {
            UserCfg usercfg = World.cur().userCfg;
            if(usercfg.callsign.trim().equals("Csocso") || usercfg.callsign.trim().equals("Sani") || usercfg.callsign.trim().equals("CirX") || usercfg.callsign.trim().equals("Malone"))
                hierMesh().chunkVisible("Overlay22", true);
            else
                hierMesh().chunkVisible("Overlay22", false);
            if(usercfg.callsign.trim().equals("HolyGrail") || usercfg.callsign.trim().equals("JG54_s0cratzy"))
                hierMesh().chunkVisible("Overlay23", true);
            else
                hierMesh().chunkVisible("Overlay23", false);
            if(usercfg.callsign.trim().equals("Howlin"))
                hierMesh().chunkVisible("Overlay25", true);
            else
                hierMesh().chunkVisible("Overlay25", false);
            if(usercfg.callsign.trim().equals("Max") || usercfg.callsign.trim().equals("Beowolff"))
                hierMesh().chunkVisible("Overlay24", true);
            else
                hierMesh().chunkVisible("Overlay24", false);
            if(usercfg.callsign.trim().equals("Max") || usercfg.callsign.trim().equals("Beowolff"))
                hierMesh().chunkVisible("OverlayCFL", false);
            else
                hierMesh().chunkVisible("OverlayCFL", true);
            if(usercfg.callsign.trim().equals("Bombsaway") || usercfg.callsign.trim().equals("Highlander"))
                hierMesh().chunkVisible("Overlay26", true);
            else
                hierMesh().chunkVisible("Overlay26", false);
        }
        if(super.FM.isPlayers())
            ((FlightModelMain) (super.FM)).CT.setMagnetoControl(0);
    }

    protected void moveFan(float f)
    {
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - ((FlightModelMain) (super.FM)).Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("SpedometerProp", 0.0F, 0.0F, -dynamoOrient);
        int i = 0;
        int j = 0;
        int k = 1;
        if(oldProp[j] < 2)
        {
            i = Math.abs((int)(((FlightModelMain) (super.FM)).EI.engines[0].getw() * 0.06F));
            if(i >= 1)
                i = 1;
            if(i != oldProp[j] && hierMesh().isChunkVisible(Props[j][oldProp[j]]))
            {
                hierMesh().chunkVisible(Props[0][oldProp[j]], false);
                oldProp[j] = i;
                hierMesh().chunkVisible(Props[j][i], true);
            }
        }
        if(i == 0)
        {
            super.propPos[j] = (super.propPos[j] + 57.3F * ((FlightModelMain) (super.FM)).EI.engines[0].getw() * f) % 360F;
        } else
        {
            float f1 = 57.3F * ((FlightModelMain) (super.FM)).EI.engines[0].getw();
            f1 %= 2880F;
            f1 /= 2880F;
            if(f1 <= 0.5F)
                f1 *= 2.0F;
            else
                f1 = f1 * 2.0F - 2.0F;
            f1 *= 1200F;
            super.propPos[j] = (super.propPos[j] + f1 * f) % 360F;
        }
        hierMesh().chunkSetAngles(Props[j][0], 0.0F, 0.0F, super.propPos[j]);
        if(oldProp[k] < 2)
        {
            i = Math.abs((int)(((FlightModelMain) (super.FM)).EI.engines[0].getw() * 0.06F));
            if(i >= 1)
                i = 1;
            if(i != oldProp[k] && hierMesh().isChunkVisible(Props[k][oldProp[k]]))
            {
                hierMesh().chunkVisible(Props[k][oldProp[k]], false);
                oldProp[k] = i;
                hierMesh().chunkVisible(Props[k][i], true);
            }
        }
        if(i == 0)
        {
            super.propPos[k] = (super.propPos[k] + 57.3F * ((FlightModelMain) (super.FM)).EI.engines[0].getw() * f) % 360F;
        } else
        {
            float f2 = 57.3F * ((FlightModelMain) (super.FM)).EI.engines[0].getw();
            f2 %= 2880F;
            f2 /= 2880F;
            if(f2 <= 0.5F)
                f2 *= 2.0F;
            else
                f2 = f2 * 2.0F - 2.0F;
            f2 *= 1200F;
            super.propPos[k] = (super.propPos[k] + f2 * f) % 360F;
        }
        hierMesh().chunkSetAngles(Props[k][0], 0.0F, 0.0F, super.propPos[k]);
    }

    public void doSetSootState(int i, int j)
    {
        for(int k = 0; k < 2; k++)
        {
            if(((FlightModelMain) (super.FM)).AS.astateSootEffects[i][k] != null)
                Eff3DActor.finish(((FlightModelMain) (super.FM)).AS.astateSootEffects[i][k]);
            ((FlightModelMain) (super.FM)).AS.astateSootEffects[i][k] = null;
        }

        switch(j)
        {
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            ((FlightModelMain) (super.FM)).AS.astateSootEffects[i][0] = Eff3DActor.New(this, findHook("HolyGrail01"), null, 1.0F, "3DO/Effects/Aircraft/EngineBlackSmall.eff", -1F);
            break;

        case 4: // '\004'
        case 5: // '\005'
        default:
            return;
        }
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = Atmosphere.temperature((float)((Tuple3d) (((FlightModelMain) (super.FM)).Loc)).z) - 273.15F;
        float f2 = Pitot.Indicator((float)((Tuple3d) (((FlightModelMain) (super.FM)).Loc)).z, super.FM.getSpeedKMH());
        if(f2 < 0.0F)
            f2 = 0.0F;
        float f3 = (((((FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator() * f * f2) / (f2 + 50F)) * (((FlightModelMain) (super.FM)).EI.engines[0].tWaterOut - f1)) / 256F;
        ((FlightModelMain) (super.FM)).EI.engines[0].tWaterOut -= f3;
        if(((FlightModelMain) (super.FM)).AS.isMaster() && Config.isUSE_RENDER())
            if(((FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6 && ((FlightModelMain) (super.FM)).EI.engines[0].getRPM() > 650F)
                ((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 2);
            else
                ((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
    }

    protected int oldProp[] = {
        0, 0, 0, 0, 0, 0
    };
    protected static final String Props[][] = {
        {
            "Prop1_D0", "PropRot1_D0", "Prop1_D1"
        }, {
            "Prop2_D0", "PropRot2_D0", "Prop2_D1"
        }, {
            "Prop3_D0", "PropRot3_D0", "Prop3_D1"
        }, {
            "Prop4_D0", "PropRot4_D0", "Prop4_D1"
        }, {
            "Prop5_D0", "PropRot5_D0", "Prop5_D1"
        }, {
            "Prop6_D0", "PropRot6_D0", "Prop6_D1"
        }
    };
    private float dynamoOrient;
    private boolean bDynamoRotary;


    static 
    {
        Class class1 = com.maddox.il2.objects.air.CAMEL.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Camel");
        Property.set(class1, "meshName", "3DO/Plane/Camel/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMParWW1());
        Property.set(class1, "yearService", 1916F);
        Property.set(class1, "yearExpired", 1918F);
        Property.set(class1, "FlightModel", "FlightModels/Camel.fmd:SOPWITH");
        Property.set(class1, "originCountry", PaintScheme.countryBritain);
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitCAMEL.class
        });
        Property.set(class1, "LOSElevation", 0.66F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
    }
}