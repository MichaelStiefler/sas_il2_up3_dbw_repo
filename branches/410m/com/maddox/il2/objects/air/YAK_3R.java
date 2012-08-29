// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_3R.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK, PaintSchemeFMPar05, NetAircraft

public class YAK_3R extends com.maddox.il2.objects.air.YAK
{

    public YAK_3R()
    {
        bHasEngine = true;
        flame = null;
        dust = null;
        trail = null;
        sprite = null;
    }

    public void destroy()
    {
        if(com.maddox.il2.engine.Actor.isValid(flame))
            flame.destroy();
        if(com.maddox.il2.engine.Actor.isValid(dust))
            dust.destroy();
        if(com.maddox.il2.engine.Actor.isValid(trail))
            trail.destroy();
        if(com.maddox.il2.engine.Actor.isValid(sprite))
            sprite.destroy();
        super.destroy();
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            flame = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1F);
            dust = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100D.eff", -1F);
            trail = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100T.eff", -1F);
            sprite = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine2EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100S.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
        }
    }

    protected void moveFan(float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        super.moveFan(f);
        if(isNetMirror())
        {
            if(FM.EI.engines[1].getStage() == 6)
            {
                com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 1.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 1.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 1.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
            } else
            {
                com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            }
        } else
        if(bHasEngine && FM.EI.engines[1].getControlThrottle() > 0.0F && FM.EI.engines[1].getStage() == 6)
        {
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
        } else
        {
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            bHasEngine = false;
            FM.AS.setEngineDies(this, 1);
            return cut(com.maddox.il2.objects.air.YAK_3R.partNames()[i]);

        case 3: // '\003'
        case 4: // '\004'
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        f1 = java.lang.Math.max(-f * 1500F, -60F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -85F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.YAK_3R.moveGear(hierMesh(), f);
    }

    public void update(float f)
    {
        super.update(f);
        if(isNetMirror())
            return;
        bPowR = this == com.maddox.il2.ai.World.getPlayerAircraft();
        if((double)FM.getAltitude() - com.maddox.il2.engine.Engine.land().HQ(FM.Loc.x, FM.Loc.y) > 5D && FM.M.fuel > 0.0F)
        {
            if(FM.EI.engines[1].getControlThrottle() > (bPowR ? 0.4120879F : 0.77F) && (FM.EI.engines[1].getStage() == 0 && FM.M.nitro > 0.0F))
            {
                FM.EI.engines[1].setStage(this, 6);
                if(bPowR)
                    com.maddox.il2.game.HUD.log("EngineI" + (FM.EI.engines[1].getStage() != 6 ? 48 : '1'));
            }
            if(FM.EI.engines[1].getControlThrottle() < (bPowR ? 0.4120879F : 0.77F) && FM.EI.engines[1].getStage() > 0)
            {
                FM.EI.engines[1].setEngineStops(this);
                if(bPowR)
                    com.maddox.il2.game.HUD.log("EngineI" + (FM.EI.engines[1].getStage() != 6 ? 48 : '1'));
            }
        }
        hierMesh().chunkSetAngles("OilRad_D0", 0.0F, FM.EI.engines[0].getControlRadiator() * 25F, 0.0F);
        hierMesh().chunkSetAngles("Water_luk", 0.0F, FM.EI.engines[0].getControlRadiator() * 12F, 0.0F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final float powR = 0.4120879F;
    private static final float powA = 0.77F;
    private boolean bHasEngine;
    private com.maddox.il2.engine.Eff3DActor flame;
    private com.maddox.il2.engine.Eff3DActor dust;
    private com.maddox.il2.engine.Eff3DActor trail;
    private com.maddox.il2.engine.Eff3DActor sprite;
    private boolean bPowR;
    private static com.maddox.JGP.Vector3d v = new Vector3d();

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-3R(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-3R.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYAK_3R.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.6576F);
        com.maddox.il2.objects.air.YAK_3R.weaponTriggersRegister(class1, new int[] {
            1
        });
        com.maddox.il2.objects.air.YAK_3R.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01"
        });
        com.maddox.il2.objects.air.YAK_3R.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVYaki 60"
        });
        com.maddox.il2.objects.air.YAK_3R.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
