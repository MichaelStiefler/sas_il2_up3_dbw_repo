// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LA_7R.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
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
//            LA_X, PaintSchemeFMPar05, Aircraft, NetAircraft

public class LA_7R extends com.maddox.il2.objects.air.LA_X
{

    public LA_7R()
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
            return cut(com.maddox.il2.objects.air.Aircraft.partNames()[i]);

        case 3: // '\003'
        case 4: // '\004'
            return false;
        }
        return super.cutFM(i, j, actor);
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
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "La");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/La-7R(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/La-7R.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitLA_7R.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.92F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunB20s 200", "MGunB20s 200", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunB20s 200", "MGunB20s 200", "BombGunFAB50 1", "BombGunFAB50 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunB20s 200", "MGunB20s 200", "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xDROPTANK", new java.lang.String[] {
            "MGunB20s 200", "MGunB20s 200", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
