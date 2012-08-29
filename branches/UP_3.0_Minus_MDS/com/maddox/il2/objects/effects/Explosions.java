// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Explosions.java

package com.maddox.il2.objects.effects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSoundListener;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3D;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSnapToLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.SfxExplosion;
import com.maddox.il2.objects.weapons.BallisticProjectile;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.effects:
//            MyMsgAction

public class Explosions
{
    static class MydataForSmoke
    {

        com.maddox.il2.engine.ActorHMesh a;
        float tim;

        MydataForSmoke(com.maddox.il2.engine.ActorHMesh actorhmesh, float f)
        {
            a = actorhmesh;
            tim = f;
        }
    }


    public Explosions()
    {
    }

    public static void HydrogenBalloonExplosion(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.engine.Loc loc1 = new Loc();
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        for(int i = 0; i < 2; i++)
        {
            loc1.set(loc);
            loc1.getPoint().x += com.maddox.il2.ai.World.Rnd().nextDouble(-12D, 12D);
            loc1.getPoint().y += com.maddox.il2.ai.World.Rnd().nextDouble(-12D, 12D);
            loc1.getPoint().z += com.maddox.il2.ai.World.Rnd().nextDouble(-3D, 3D);
            com.maddox.il2.engine.Eff3DActor.New(loc1, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
        }

        int k = com.maddox.il2.ai.World.Rnd().nextInt(2, 6);
        for(int j = 0; j < k; j++)
        {
            ((com.maddox.JGP.Tuple3d) (vector3d)).set(com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F), com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F), com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 1.5F));
            vector3d.normalize();
            ((com.maddox.JGP.Tuple3d) (vector3d)).scale(com.maddox.il2.ai.World.Rnd().nextFloat(4F, 15F));
            float f = com.maddox.il2.ai.World.Rnd().nextFloat(4F, 7F);
            com.maddox.il2.objects.weapons.BallisticProjectile ballisticprojectile = new BallisticProjectile(loc.getPoint(), vector3d, f);
            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (ballisticprojectile)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Aircraft/BlackHeavySPD.eff", f);
            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (ballisticprojectile)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Aircraft/BlackHeavyTSPD.eff", f);
            com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (ballisticprojectile)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Aircraft/FireSPD.eff", f);
        }

        com.maddox.il2.objects.sounds.SfxExplosion.crashAir(loc.getPoint(), 1);
    }

    public static void runByName(java.lang.String s, com.maddox.il2.engine.ActorHMesh actorhmesh, java.lang.String s1, java.lang.String s2, float f)
    {
        com.maddox.il2.objects.effects.Explosions.runByName(s, actorhmesh, s1, s2, f, ((com.maddox.il2.engine.Actor) (null)));
    }

    public static void runByName(java.lang.String s, com.maddox.il2.engine.ActorHMesh actorhmesh, java.lang.String s1, java.lang.String s2, float f, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.HookNamed hooknamed = null;
        if(s1 != null && !s1.equals(""))
        {
            int i = actorhmesh.mesh().hookFind(s1);
            if(i >= 0)
                hooknamed = new HookNamed(((com.maddox.il2.engine.ActorMesh) (actorhmesh)), s1);
            else
            if(s2 != null && !s2.equals(""))
            {
                int j = actorhmesh.mesh().hookFind(s2);
                if(j >= 0)
                    hooknamed = new HookNamed(((com.maddox.il2.engine.ActorMesh) (actorhmesh)), s2);
            }
        }
        if(s.equalsIgnoreCase("Tank"))
            com.maddox.il2.objects.effects.Explosions.Tank_Explode(((com.maddox.il2.engine.Actor) (actorhmesh)).pos.getAbsPoint());
        else
        if(s.equalsIgnoreCase("_TankSmoke_"))
        {
            if(f > 0.0F)
            {
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/TankDyingFire.eff", f * 0.7F);
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/TankDyingSmoke.eff", f);
            }
        } else
        if(s.equalsIgnoreCase("Car"))
        {
            com.maddox.il2.objects.effects.Explosions.Car_Explode(((com.maddox.il2.engine.Actor) (actorhmesh)).pos.getAbsPoint());
            if(f > 0.0F)
            {
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/CarDyingFire.eff", f * 0.7F);
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/CarDyingSmoke.eff", f);
            }
        } else
        if(s.equalsIgnoreCase("CarFuel"))
        {
            new com.maddox.rts.MsgAction(0.0D, ((java.lang.Object) (actorhmesh))) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.JGP.Point3d point3d = new Point3d();
                    ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                    com.maddox.il2.objects.effects.Explosions.ExplodeVagonFuel(point3d, point3d, 1.5F);
                }

            }
;
            if(f > 0.0F)
            {
                new com.maddox.il2.objects.effects.MyMsgAction(0.42999999999999999D, ((java.lang.Object) (actorhmesh)), ((java.lang.Object) (actor))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.JGP.Point3d point3d = new Point3d();
                        ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                        float f1 = 25F;
                        int k = 0;
                        float f2 = 50F;
                        com.maddox.il2.ai.MsgExplosion.send((com.maddox.il2.engine.Actor)obj, "Body", point3d, (com.maddox.il2.engine.Actor)super.obj2, 0.0F, f1, k, f2);
                    }

                }
;
                new com.maddox.rts.MsgAction(1.2D, ((java.lang.Object) (new MydataForSmoke(actorhmesh, f)))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (((com.maddox.il2.objects.effects.MydataForSmoke)obj).a)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((com.maddox.il2.objects.effects.MydataForSmoke)obj).tim);
                    }

                }
;
            }
        } else
        if(s.equalsIgnoreCase("Artillery"))
        {
            com.maddox.il2.objects.effects.Explosions.Antiaircraft_Explode(((com.maddox.il2.engine.Actor) (actorhmesh)).pos.getAbsPoint());
            if(f > 0.0F)
            {
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/TankDyingFire.eff", f * 0.7F);
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/TankDyingSmoke.eff", f);
            }
        } else
        if(s.equalsIgnoreCase("Stationary"))
        {
            com.maddox.il2.objects.effects.Explosions.Antiaircraft_Explode(((com.maddox.il2.engine.Actor) (actorhmesh)).pos.getAbsPoint());
            if(f > 0.0F)
            {
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/TankDyingFire.eff", f * 0.7F);
                com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (actorhmesh)), ((com.maddox.il2.engine.Hook) (hooknamed)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/TankDyingSmoke.eff", f);
            }
        } else
        if(s.equalsIgnoreCase("Aircraft"))
            com.maddox.il2.objects.effects.Explosions.Antiaircraft_Explode(((com.maddox.il2.engine.Actor) (actorhmesh)).pos.getAbsPoint());
        else
        if(s.equalsIgnoreCase("Aircraft"))
            java.lang.System.out.println("*** Unknown named explode: '" + s + "'");
        else
        if(s.equalsIgnoreCase("WagonWoodExplosives"))
        {
            new com.maddox.rts.MsgAction(0.0D, ((java.lang.Object) (actorhmesh))) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.JGP.Point3d point3d = new Point3d();
                    ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                    com.maddox.il2.objects.effects.Explosions.ExplodeVagonArmor(point3d, point3d, 2.0F);
                }

            }
;
            if(f > 0.0F)
            {
                new com.maddox.il2.objects.effects.MyMsgAction(0.42999999999999999D, ((java.lang.Object) (actorhmesh)), ((java.lang.Object) (actor))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.JGP.Point3d point3d = new Point3d();
                        ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                        float f1 = 180F;
                        int k = 0;
                        float f2 = 140F;
                        com.maddox.il2.ai.MsgExplosion.send((com.maddox.il2.engine.Actor)obj, "Body", point3d, (com.maddox.il2.engine.Actor)super.obj2, 0.0F, f1, k, f2);
                    }

                }
;
                new com.maddox.rts.MsgAction(1.2D, ((java.lang.Object) (new MydataForSmoke(actorhmesh, f)))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (((com.maddox.il2.objects.effects.MydataForSmoke)obj).a)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((com.maddox.il2.objects.effects.MydataForSmoke)obj).tim);
                    }

                }
;
            }
        } else
        if(s.equalsIgnoreCase("WagonWood"))
        {
            new com.maddox.rts.MsgAction(0.0D, ((java.lang.Object) (actorhmesh))) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.JGP.Point3d point3d = new Point3d();
                    ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                    com.maddox.il2.objects.effects.Explosions.ExplodeVagonArmor(point3d, point3d, 2.0F);
                }

            }
;
            if(f > 0.0F)
                new com.maddox.rts.MsgAction(1.2D, ((java.lang.Object) (new MydataForSmoke(actorhmesh, f)))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (((com.maddox.il2.objects.effects.MydataForSmoke)obj).a)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((com.maddox.il2.objects.effects.MydataForSmoke)obj).tim);
                    }

                }
;
        } else
        if(s.equalsIgnoreCase("WagonFuel"))
        {
            new com.maddox.rts.MsgAction(0.0D, ((java.lang.Object) (actorhmesh))) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.JGP.Point3d point3d = new Point3d();
                    ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                    com.maddox.il2.objects.effects.Explosions.ExplodeVagonFuel(point3d, point3d, 2.0F);
                }

            }
;
            if(f > 0.0F)
            {
                new com.maddox.il2.objects.effects.MyMsgAction(0.42999999999999999D, ((java.lang.Object) (actorhmesh)), ((java.lang.Object) (actor))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.JGP.Point3d point3d = new Point3d();
                        ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                        float f1 = 180F;
                        int k = 0;
                        float f2 = 120F;
                        com.maddox.il2.ai.MsgExplosion.send((com.maddox.il2.engine.Actor)obj, "Body", point3d, (com.maddox.il2.engine.Actor)super.obj2, 0.0F, f1, k, f2);
                    }

                }
;
                new com.maddox.rts.MsgAction(1.2D, ((java.lang.Object) (new MydataForSmoke(actorhmesh, f)))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (((com.maddox.il2.objects.effects.MydataForSmoke)obj).a)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((com.maddox.il2.objects.effects.MydataForSmoke)obj).tim);
                    }

                }
;
            }
        } else
        if(s.equalsIgnoreCase("WagonMetal"))
        {
            new com.maddox.rts.MsgAction(0.0D, ((java.lang.Object) (actorhmesh))) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.JGP.Point3d point3d = new Point3d();
                    ((com.maddox.il2.engine.Actor)obj).pos.getAbs(point3d);
                    com.maddox.il2.objects.effects.Explosions.ExplodeVagonArmor(point3d, point3d, 2.0F);
                }

            }
;
            if(f > 0.0F)
                new com.maddox.rts.MsgAction(1.2D, ((java.lang.Object) (new MydataForSmoke(actorhmesh, f)))) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (((com.maddox.il2.objects.effects.MydataForSmoke)obj).a)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", ((com.maddox.il2.objects.effects.MydataForSmoke)obj).tim);
                    }

                }
;
        }
    }

    public static void shot(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor eff3dactor = com.maddox.il2.engine.Eff3DActor.New(l, 2.0F, "effects/sprites/spritesun.eff", -1F);
            ((com.maddox.il2.engine.Actor) (eff3dactor)).postDestroy(com.maddox.rts.Time.current() + 500L);
            return;
        }
    }

    public static void HouseExplode(int i, com.maddox.il2.engine.Loc loc, float af[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.JGP.Point3d point3d = new Point3d();
        java.lang.String s = "";
        boolean flag = false;
        byte byte0 = 0;
        switch(i)
        {
        case 0: // '\0'
        case 1: // '\001'
            s = "Wood";
            byte0 = 4;
            break;

        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
            s = "Rock";
            byte0 = 3;
            break;

        case 5: // '\005'
        case 6: // '\006'
            s = "Fuel";
            boolean flag1 = true;
            byte0 = 5;
            break;

        default:
            java.lang.System.out.println("WARNING: HouseExplode(): unknown type");
            return;
        }
        java.lang.String s1 = "effects/Explodes/Objects/House/" + s + "/Boiling.eff";
        java.lang.String s2 = "effects/Explodes/Objects/House/" + s + "/Boiling2.eff";
        java.lang.String s3 = "effects/Explodes/Objects/House/" + s + "/Pieces.eff";
        float f = 4F;
        float f1 = 1.0F;
        com.maddox.il2.engine.Eff3D.initSetBoundBox(af[0], af[1], af[2], af[3], af[4], af[5]);
        com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, s1, 3F);
        com.maddox.il2.engine.Eff3D.initSetBoundBox(af[0] + (af[3] - af[0]) * 0.25F, af[1] + (af[4] - af[1]) * 0.25F, af[2], af[3] - (af[3] - af[0]) * 0.25F, af[4] - (af[4] - af[1]) * 0.25F, af[2] + (af[5] - af[2]) * 0.5F);
        com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, s2, 3F);
        com.maddox.il2.objects.sounds.SfxExplosion.building(loc.getPoint(), ((int) (byte0)), af);
    }

    private static void ExplodeSurfaceWave(int i, float f, float f1)
    {
        if(i == 0)
            new ActorSnapToLand("3do/Effects/Explosion/DustRing.sim", true, l, 1.0F, f, 1.0F, 0.0F, f1);
        else
        if(i == 1)
            new ActorSnapToLand("3do/Effects/Explosion/WaterRing.sim", true, l, 0.2F, f, 1.0F, 0.0F, f1);
    }

    private static void SurfaceLight(int i, float f, float f1)
    {
        new ActorSnapToLand("3do/Effects/Explosion/LandLight.sim", true, l, 1.0F, f, i == 0 ? 1.0F : 0.5F, 0.0F, f1);
    }

    private static void SurfaceCrater(int i, float f, float f1)
    {
        if(i == 0)
        {
            new ActorSnapToLand("3do/Effects/Explosion/Crater.sim", true, l, 0.2F, f, f + 2.0F, 1.0F, 0.0F, f1);
            if(bEnableActorCrater)
            {
                int j;
                for(j = 64; j >= 2 && f < (float)j; j /= 2);
                if(j >= 2)
                    new ActorCrater("3do/Effects/Explosion/Crater" + j + "/Live.sim", l, f1);
            }
        }
    }

    private static void fontain(com.maddox.JGP.Point3d point3d, float f, float f1, int i, int j, boolean flag)
    {
        int k = 4 + (int)(java.lang.Math.random() * 2D);
        float f2 = 30F;
        o.set(0.0F, 90F, 0.0F);
        l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            java.lang.String s;
            float f3;
            float f4;
            float f5;
            float f6;
            if(j == 2)
            {
                s = "Bomb1000";
                f3 = 500F;
                f5 = 600F;
                f4 = 36F;
                k = 3 + (int)(java.lang.Math.random() * 3D);
                f6 = 1.6F;
            } else
            if(j == 0)
            {
                s = "Bomb250";
                f3 = 250F;
                f5 = 300F;
                f4 = 18F;
                f6 = 0.8F;
            } else
            {
                s = "RS82";
                f3 = 125F;
                f5 = 150F;
                f4 = 4.5F;
                k = 2 + (int)(java.lang.Math.random() * 2D);
                f6 = 0.6F;
            }
            java.lang.String s1 = "effects/Explodes/" + s + "/Land/Fontain.eff";
            java.lang.String s2 = "effects/Explodes/" + s + "/Land/Peaces.eff";
            java.lang.String s3 = "effects/Explodes/" + s + "/Land/Burn.eff";
            com.maddox.il2.engine.Eff3DActor eff3dactor = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s2, 3.5F);
            for(int i1 = 0; i1 < k; i1++)
            {
                float f8 = (float)(360D * java.lang.Math.random());
                float f9 = 90F + (2.0F * (float)java.lang.Math.random() - 1.0F) * f2;
                o.set(f8, f9, 0.0F);
                l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
                com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s1, f);
            }

            o.set(0.0F, 0.0F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(i, f3, f6);
            com.maddox.il2.objects.effects.Explosions.SurfaceLight(i, f5, 2.0F);
            float f7 = 80F;
            if(flag && !com.maddox.il2.game.Mission.isCoop() && !com.maddox.il2.game.Mission.isDogfight())
                if(j == 0)
                    f7 *= com.maddox.il2.game.Main.cur().mission.zutiMisc_BombsCat2_CratersVisibilityMultiplier;
                else
                if(j == 2)
                    f7 *= com.maddox.il2.game.Main.cur().mission.zutiMisc_BombsCat3_CratersVisibilityMultiplier;
                else
                    f7 *= com.maddox.il2.game.Main.cur().mission.zutiMisc_BombsCat1_CratersVisibilityMultiplier;
            com.maddox.il2.objects.effects.Explosions.SurfaceCrater(i, f4, f7);
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor eff3dactor1 = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s3, 1.0F);
            ((com.maddox.il2.engine.Actor) (eff3dactor1)).postDestroy(com.maddox.rts.Time.current() + 1500L);
            com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(((com.maddox.il2.engine.LightPoint) (new LightPointWorld())), new Point3d());
            lightpointactor.light.setColor(1.0F, 0.9F, 0.5F);
            lightpointactor.light.setEmit(1.0F, f5 * 2.0F);
            ((com.maddox.il2.engine.Actor) (eff3dactor1)).draw.lightMap().put("light", ((java.lang.Object) (lightpointactor)));
            break;

        case 1: // '\001'
            if(j == 2)
                com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Bomb1000/Water/Fontain.eff", f);
            else
            if(j == 0)
                com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Bomb250/Water/Fontain.eff", f);
            else
                com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/RS82/Water/Fontain.eff", f);
            o.set(0.0F, 0.0F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            if(j == 2)
            {
                com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(i, 750F, 15F);
                break;
            }
            if(j == 0)
                com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(i, 325F, 10F);
            else
                com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(i, 50F, 7F);
            break;

        case 2: // '\002'
            com.maddox.il2.objects.effects.Explosions.Tank_Explode(point3d);
            break;
        }
    }

    public static void Tank_Explode(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.Tank_ExplodeCollapse(point3d);
            float f = 31.25F;
            float f2 = 150F;
            float f1 = 6.75F;
            int i = 0;
            boolean flag = true;
            o.set(0.0F, 0.0F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(i, f, flag ? 0.6F : 0.8F);
            com.maddox.il2.objects.effects.Explosions.SurfaceLight(i, f2, 0.3F);
            return;
        }
    }

    public static void Antiaircraft_Explode(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.Tank_Explode(point3d);
            return;
        }
    }

    public static void Car_Explode(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.Tank_Explode(point3d);
            return;
        }
    }

    private static void Building_Explode(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        float f = 20F;
        byte byte0 = 3;
        com.maddox.JGP.Point3d point3d1 = new Point3d();
        java.lang.String s = "effects/Explodes/Objects/Building20m/SmokeBoiling.eff";
        java.lang.String s1 = "effects/Explodes/Objects/Building20m/SmokeBoiling2.eff";
        float f1 = 4F;
        float f2 = 1.0F;
        for(int i = 0; i < byte0 * byte0; i++)
        {
            double d = (java.lang.Math.random() - 0.5D) * (double)f;
            double d1 = (java.lang.Math.random() - 0.5D) * (double)f;
            ((com.maddox.JGP.Tuple3d) (point3d1)).set(((com.maddox.JGP.Tuple3d) (point3d)));
            point3d1.x += d;
            point3d1.y += d1;
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d1)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, java.lang.Math.random() < 0.5D ? s : s1, 3F);
        }

        o.set(0.0F, 0.0F, 0.0F);
        l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
        float f3 = 62.5F;
        float f5 = 150F;
        float f4 = 6.75F;
        int j = 0;
        boolean flag = false;
        com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(j, f3, flag ? 0.6F : 0.8F);
    }

    public static void Tank_ExplodeCollapse(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.sounds.SfxExplosion.crashTank(point3d, 0);
            com.maddox.il2.objects.effects.Explosions.explodeCollapse(point3d);
            return;
        }
    }

    private static void explodeCollapse(com.maddox.JGP.Point3d point3d)
    {
        o.set(0.0F, 90F, 0.0F);
        l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
        int i = 6 + (int)(java.lang.Math.random() * 2D);
        float f = 60F;
        java.lang.String s = "Objects/Tank_Collapse";
        float f1 = 31.25F;
        float f3 = 150F;
        float f2 = 6.75F;
        java.lang.String s1 = "effects/Explodes/" + s + "/Peaces1.eff";
        java.lang.String s2 = "effects/Explodes/" + s + "/Peaces2.eff";
        java.lang.String s3 = "effects/Explodes/" + s + "/Sparks.eff";
        java.lang.String s4 = "effects/Explodes/" + s + "/Burn.eff";
        java.lang.String s5 = "effects/Explodes/" + s + "/SmokeBoiling.eff";
        com.maddox.il2.engine.Eff3DActor eff3dactor = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s1, 3.5F);
        com.maddox.il2.engine.Eff3DActor eff3dactor1 = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s2, 3.5F);
        eff3dactor1 = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s3, 0.5F);
        eff3dactor1 = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s5, 2.5F);
        com.maddox.il2.engine.Eff3DActor eff3dactor2 = com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s4, 0.3F);
        ((com.maddox.il2.engine.Actor) (eff3dactor2)).postDestroy(com.maddox.rts.Time.current() + 1500L);
        com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(((com.maddox.il2.engine.LightPoint) (new LightPointWorld())), new Point3d(5D, 0.0D, 0.0D));
        lightpointactor.light.setColor(1.0F, 0.9F, 0.5F);
        lightpointactor.light.setEmit(1.0F, f3 * 2.0F);
        ((com.maddox.il2.engine.Actor) (eff3dactor2)).draw.lightMap().put("light", ((java.lang.Object) (lightpointactor)));
    }

    public static void Car_ExplodeCollapse(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.explodeCollapse(point3d);
            return;
        }
    }

    public static void AirDrop_Land(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        float f = 4F;
        float f1 = 1.0F;
        if(com.maddox.il2.game.Mission.isDeathmatch())
            bEnableActorCrater = false;
        com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 0, false);
        bEnableActorCrater = true;
        com.maddox.il2.objects.sounds.SfxExplosion.crashAir(point3d, 0);
    }

    public static void AirDrop_Water(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            float f = 4F;
            float f1 = 1.0F;
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 1, 0, false);
            com.maddox.il2.objects.sounds.SfxExplosion.crashAir(point3d, 2);
            return;
        }
    }

    public static void AirDrop_Air(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.explodeCollapse(point3d);
            com.maddox.il2.objects.sounds.SfxExplosion.crashAir(point3d, 1);
            return;
        }
    }

    public static void WreckageDrop_Water(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            float f = 3F;
            float f1 = 1.0F;
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 1, 1, false);
            com.maddox.il2.objects.sounds.SfxExplosion.crashParts(point3d, 2);
            return;
        }
    }

    public static void SomethingDrop_Water(com.maddox.JGP.Point3d point3d, float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 0.0F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            new ActorSnapToLand("3do/Effects/Explosion/WaterRing.sim", true, l, f * 0.2F, f, 1.0F, 0.0F, 2.5F);
            com.maddox.il2.objects.sounds.SfxExplosion.crashParts(point3d, 2);
            return;
        }
    }

    public static void AirFlak(com.maddox.JGP.Point3d point3d, int i)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        o.set(0.0F, 90F, 0.0F);
        l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
        java.lang.String s = "effects/Explodes/Air/Zenitka/";
        switch(i)
        {
        case 0: // '\0'
            s = s + "USSR_85mm/";
            break;

        case 1: // '\001'
            s = s + "Germ_88mm/";
            break;

        case 2: // '\002'
            s = s + "USSR_25mm/";
            break;

        default:
            s = s + "Germ_20mm/";
            break;
        }
        com.maddox.il2.objects.sounds.SfxExplosion.zenitka(point3d, i);
        float f = -1F;
        com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s + "SmokeBoiling.eff", f);
        com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s + "Burn.eff", f);
        com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s + "Sparks.eff", f);
        com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, s + "SparksP.eff", f);
    }

    public static void ExplodeFuel(com.maddox.JGP.Point3d point3d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.engine.Loc loc = new Loc(((com.maddox.JGP.Tuple3d) (point3d)));
        com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1F);
        com.maddox.il2.engine.Eff3DActor.New(loc, 1.0F, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1F);
        if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.ai.World.cur().land().HQ(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) > 3D)
        {
            com.maddox.il2.objects.sounds.SfxExplosion.crashAir(point3d, 1);
        } else
        {
            com.maddox.il2.ai.World.cur();
            if(com.maddox.il2.ai.World.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                com.maddox.il2.objects.sounds.SfxExplosion.crashAir(point3d, 2);
            else
                com.maddox.il2.objects.sounds.SfxExplosion.crashAir(point3d, 0);
        }
    }

    private static void LinearExplode(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, float f, float f1, java.lang.String s, java.lang.String s1)
    {
        double d = point3d.distance(point3d1);
        int i = (int)(((2D * d) / (double)f) * (double)f1);
        if(i < 2)
            i = 2;
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        float f2 = 4F;
        float f3 = 1.0F;
        for(int j = 0; j < i; j++)
        {
            ((com.maddox.JGP.Tuple3d) (point3d2)).interpolate(((com.maddox.JGP.Tuple3d) (point3d)), ((com.maddox.JGP.Tuple3d) (point3d1)), java.lang.Math.random());
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d2)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, java.lang.Math.random() < 0.5D ? s : s1, 3F);
        }

    }

    public static void ExplodeBridge(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.LinearExplode(point3d, point3d1, f, 1.0F, "effects/Explodes/Objects/Bridges/SmokeBoiling.eff", "effects/Explodes/Objects/Bridges/SmokeBoiling2.eff");
            com.maddox.il2.objects.sounds.SfxExplosion.bridge(point3d, point3d1, f);
            return;
        }
    }

    public static void ExplodeVagonArmor(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        for(int i = 0; i < 3; i++)
        {
            ((com.maddox.JGP.Tuple3d) (point3d2)).interpolate(((com.maddox.JGP.Tuple3d) (point3d)), ((com.maddox.JGP.Tuple3d) (point3d1)), java.lang.Math.random());
            com.maddox.il2.objects.effects.Explosions.AirFlak(point3d2, 0);
        }

        com.maddox.il2.objects.effects.Explosions.LinearExplode(point3d, point3d1, f, 0.5F, "effects/Explodes/Objects/VagonArmor/SmokeBoiling.eff", "effects/Explodes/Objects/VagonArmor/SmokeBoiling2.eff");
        com.maddox.il2.objects.sounds.SfxExplosion.wagon(point3d, point3d1, f, 6);
    }

    public static void ExplodeVagonFuel(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.LinearExplode(point3d, point3d1, f, 0.75F, "effects/Explodes/Objects/VagonFuel/SmokeBoilingFire.eff", "effects/Explodes/Objects/VagonFuel/SmokeBoilingFire2.eff");
            com.maddox.il2.objects.sounds.SfxExplosion.wagon(point3d, point3d1, f, 5);
            return;
        }
    }

    public static void bomb50_land(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, f1, "3DO/Effects/Fireworks/Tank_Burn.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, f1, "3DO/Effects/Fireworks/Tank_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, f1, "3DO/Effects/Fireworks/Tank_Sparks.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, f1, "3DO/Effects/Fireworks/Tank_SparksP.eff", -1F);
            return;
        }
    }

    public static void BOMB250_Land(com.maddox.JGP.Point3d point3d, float f, float f1, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 0, flag);
            return;
        }
    }

    public static void BOMB250_Water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 1, 0, false);
            return;
        }
    }

    public static void BOMB250_Object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 0, false);
            return;
        }
    }

    public static void BOMB1000a_Land(com.maddox.JGP.Point3d point3d, float f, float f1, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 2, flag);
            return;
        }
    }

    public static void BOMB1000a_Water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 1, 2, false);
            return;
        }
    }

    public static void BOMB1000a_Object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 2, false);
            return;
        }
    }

    public static void bomb1000_land(com.maddox.JGP.Point3d point3d, float f, float f1, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.SurfaceLight(0, 10000F, 1.0F);
            com.maddox.il2.objects.effects.Explosions.SurfaceCrater(0, 112.1F, 600F);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(0, 2000F, 4.6F);
            point3d.z += 5D;
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(buff).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(circle).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(column).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(flare).eff", 0.1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(ring).eff", -1F);
            return;
        }
    }

    public static void bomb1000_water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.SurfaceLight(0, 10000F, 1.0F);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(1, 3000F, 6.6F);
            point3d.z += 5D;
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(buff).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(circle).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(column).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(flare).eff", 0.1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FAB-1000(ring).eff", -1F);
            return;
        }
    }

    public static void bomb1000_object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        else
            return;
    }

    public static void bombFatMan_land(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.SurfaceLight(0, 910000F, 0.5F);
            com.maddox.il2.objects.effects.Explosions.SurfaceCrater(0, 312.1F, 900F);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(0, 4000F, 4.6F);
            point3d.z += 5D;
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(shock).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(buff).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(circleL).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(column).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(flare).eff", 0.1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(ring).eff", -1F);
            return;
        }
    }

    public static void bombFatMan_water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.SurfaceLight(0, 910000F, 0.5F);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(1, 7000F, 6.6F);
            point3d.z += 5D;
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(shock).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(buff).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(circle).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(column).eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(flare).eff", 0.1F);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "3DO/Effects/Fireworks/FatMan(ring).eff", -1F);
            return;
        }
    }

    public static void bombFatMan_object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        else
            return;
    }

    public static void bomb5000_land(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        else
            return;
    }

    public static void bomb5000_water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        else
            return;
    }

    public static void bomb5000_object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        else
            return;
    }

    public static void bomb999999_object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        else
            return;
    }

    public static void RS82_Land(com.maddox.JGP.Point3d point3d, float f, float f1, boolean flag)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 1, flag);
            return;
        }
    }

    public static void RS82_Water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 1, 1, false);
            return;
        }
    }

    public static void RS82_Object(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.fontain(point3d, f, f1, 0, 1, false);
            return;
        }
    }

    public static void Explode10Kg_Object(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3f vector3f, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.setAT0(vector3f);
            o.set(o.azimut(), o.tangage() + 180F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Explode10Kg/Object/Sparks.eff", f);
            return;
        }
    }

    public static void Explode10Kg_Land(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Explode10Kg/Land/Fontain.eff", f);
            return;
        }
    }

    public static void Explode10Kg_Water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Explode10Kg/Water/Fontain.eff", f);
            o.set(0.0F, 0.0F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(1, 17.5F, 4F);
            return;
        }
    }

    public static void Bullet_Object(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.setAT0(vector3d);
            o.set(o.azimut(), o.tangage() + 180F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Object/Sparks.eff", f);
            return;
        }
    }

    public static void Bullet_Water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Bullet/Water/Fontain.eff", f);
            return;
        }
    }

    public static void Bullet_Land(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Bullet/Land/Fontain.eff", f);
            return;
        }
    }

    public static void Cannon_Object(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3f vector3f, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.setAT0(vector3f);
            o.set(o.azimut(), o.tangage() + 180F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Object/Sparks.eff", f);
            return;
        }
    }

    public static void Cannon_Water(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Water/Fontain.eff", f);
            o.set(0.0F, 0.0F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.objects.effects.Explosions.ExplodeSurfaceWave(1, 17.5F, 4F);
            return;
        }
    }

    public static void Cannon_Land(com.maddox.JGP.Point3d point3d, float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            return;
        } else
        {
            o.set(0.0F, 90F, 0.0F);
            l.set(((com.maddox.JGP.Tuple3d) (point3d)), o);
            com.maddox.il2.engine.Eff3DActor.New(l, 1.0F, "effects/Explodes/Cannon/Land/Fontain.eff", f);
            return;
        }
    }

    public static void generateSound(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f, int i, float f1)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            if(actor == null)
                com.maddox.il2.objects.sounds.SfxExplosion.shell(point3d, 1, f, i, f1);
            else
            if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                com.maddox.il2.objects.sounds.SfxExplosion.shell(point3d, 2, f, i, f1);
            else
                com.maddox.il2.objects.sounds.SfxExplosion.shell(point3d, 0, f, i, f1);
    }

    public static void generateRocket(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f, int i, float f1)
    {
        com.maddox.il2.objects.effects.Explosions.generateRocket(actor, point3d, f, i, f1, 0);
    }

    public static void generateRocket(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f, int i, float f1, int j)
    {
        com.maddox.il2.objects.effects.Explosions.generate(actor, point3d, f > 15F ? f : 15F, i, f1, false, j);
    }

    public static void generate(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f, int i, float f1, boolean flag)
    {
        com.maddox.il2.objects.effects.Explosions.generate(actor, point3d, f, i, f1, flag, 0);
    }

    public static void generate(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f, int i, float f1, boolean flag, int j)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(actor != null)
        {
            com.maddox.il2.objects.effects.Explosions.generateSound(actor, point3d, f, i, f1);
            rel.set(((com.maddox.JGP.Tuple3d) (point3d)));
            actor.pos.getAbs(tmpLoc);
            actor.pos.getCurrent(l);
            l.interpolate(tmpLoc, 0.5D);
            rel.sub(l);
            if(i == 2)
            {
                if(f1 < 3F)
                {
                    switch(rnd.nextInt(1, 2))
                    {
                    case 1: // '\001'
                        com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/Termit1W.eff", 10F);
                        break;

                    case 2: // '\002'
                        com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/Termit1SM.eff", -1F);
                        break;
                    }
                } else
                {
                    com.maddox.JGP.Vector3d vector3d = new Vector3d();
                    for(int j1 = 0; j1 < 36; j1++)
                    {
                        ((com.maddox.JGP.Tuple3d) (vector3d)).set(com.maddox.il2.ai.World.Rnd().nextDouble(-20D, 20D), com.maddox.il2.ai.World.Rnd().nextDouble(-20D, 20D), com.maddox.il2.ai.World.Rnd().nextDouble(3D, 20D));
                        float f2 = com.maddox.il2.ai.World.Rnd().nextFloat(3F, 15F);
                        com.maddox.il2.objects.weapons.BallisticProjectile ballisticprojectile = new BallisticProjectile(point3d, vector3d, f2);
                        com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (ballisticprojectile)), ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Fireworks/PhosfourousFire.eff", f2);
                    }

                }
                return;
            }
            if(actor instanceof com.maddox.il2.objects.ActorLand)
            {
                if(j > 0)
                {
                    if(j == 1)
                        if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                            com.maddox.il2.objects.effects.Explosions.bombFatMan_water(point3d, -1F, 1.0F);
                        else
                            com.maddox.il2.objects.effects.Explosions.bombFatMan_land(point3d, -1F, 1.0F);
                } else
                if(f < 15F)
                {
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(point3d, 4F, 1.0F);
                } else
                if(f < 50F)
                {
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.RS82_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.RS82_Land(point3d, 4F, 1.0F, flag);
                } else
                if(f < 450F)
                {
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.BOMB250_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.BOMB250_Land(point3d, 4F, 1.0F, flag);
                } else
                if(f < 3000F)
                {
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.BOMB1000a_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.BOMB1000a_Land(point3d, 4F, 1.0F, flag);
                } else
                if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                    com.maddox.il2.objects.effects.Explosions.bomb1000_water(point3d, -1F, 1.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.bomb1000_land(point3d, -1F, 1.0F, flag);
            } else
            if(j > 0)
            {
                if(j == 1)
                {
                    if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 50D)
                        if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                            com.maddox.il2.objects.effects.Explosions.bombFatMan_water(point3d, -1F, 1.0F);
                        else
                            com.maddox.il2.objects.effects.Explosions.bombFatMan_land(point3d, -1F, 1.0F);
                    com.maddox.il2.objects.effects.Explosions.bomb50_land(point3d, -1F, 10F);
                }
            } else
            if(f < 50F)
            {
                if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 5D)
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.RS82_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.RS82_Land(point3d, 4F, 1.0F, flag);
                com.maddox.il2.objects.effects.Explosions.bomb50_land(point3d, -1F, 1.0F);
            } else
            if(f < 450F)
            {
                if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 10D)
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.BOMB250_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.BOMB250_Land(point3d, 4F, 1.0F, flag);
                com.maddox.il2.objects.effects.Explosions.bomb50_land(point3d, -1F, 2.0F);
            } else
            if(f < 3000F)
            {
                if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 20D)
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.BOMB1000a_Water(point3d, 4F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.BOMB1000a_Land(point3d, 4F, 1.0F, flag);
                com.maddox.il2.objects.effects.Explosions.bomb50_land(point3d, -1F, 2.0F);
            } else
            {
                if(((com.maddox.JGP.Tuple3d) (point3d)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y) < 50D)
                    if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y))
                        com.maddox.il2.objects.effects.Explosions.bomb1000_water(point3d, -1F, 1.0F);
                    else
                        com.maddox.il2.objects.effects.Explosions.bomb1000_land(point3d, -1F, 1.0F, flag);
                com.maddox.il2.objects.effects.Explosions.bomb50_land(point3d, -1F, 10F);
            }
        }
    }

    private static void playShotSound(com.maddox.il2.ai.Shot shot1)
    {
        double d = shot1.p.distanceSquared(com.maddox.il2.engine.Engine.soundListener().absPos);
    }

    public static void generateShot(com.maddox.il2.engine.Actor actor, com.maddox.il2.ai.Shot shot1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        float f = shot1.mass;
        com.maddox.il2.objects.effects.Explosions.playShotSound(shot1);
        rel.set(((com.maddox.JGP.Tuple3d) (shot1.p)));
        actor.pos.getAbs(tmpLoc);
        actor.pos.getCurrent(l);
        l.interpolate(tmpLoc, shot1.tickOffset);
        rel.sub(l);
        if(com.maddox.il2.ai.World.cur().isArcade() && !(actor instanceof com.maddox.il2.objects.air.Aircraft))
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.75F, "3DO/Effects/Fireworks/Sprite.eff", 30F);
        if(!(actor instanceof com.maddox.il2.objects.ActorLand))
            switch(rnd.nextInt(1, 4))
            {
            case 1: // '\001'
                com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/Debris1A.eff", -1F);
                break;

            case 2: // '\002'
                com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/Debris1B.eff", -1F);
                break;

            case 3: // '\003'
                com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/Debris1C.eff", -1F);
                break;

            case 4: // '\004'
                com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/Debris1D.eff", -1F);
                break;
            }
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            return;
        switch(shot1.bodyMaterial)
        {
        case 0: // '\0'
            if(f < 1.0F)
            {
                com.maddox.il2.objects.effects.Explosions.Cannon_Land(shot1.p, 4F, 1.0F);
                break;
            }
            if(f < 5F)
            {
                com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(shot1.p, 4F, 1.0F);
                break;
            }
            if(f < 50F)
                com.maddox.il2.objects.effects.Explosions.RS82_Land(shot1.p, 4F, 1.0F, false);
            else
                com.maddox.il2.objects.effects.Explosions.BOMB250_Land(shot1.p, 4F, 1.0F, false);
            break;

        case 3: // '\003'
            break;

        case 1: // '\001'
            if(f < 0.023F)
            {
                com.maddox.il2.objects.effects.Explosions.Bullet_Water(shot1.p, 0.5F, 1.0F);
                break;
            }
            if(f < 0.701F)
            {
                com.maddox.il2.objects.effects.Explosions.Cannon_Water(shot1.p, 4F, 1.0F);
                break;
            }
            if(f < 8.55F)
            {
                com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(shot1.p, 4F, 1.0F);
                break;
            }
            if(f < 24.2F)
                com.maddox.il2.objects.effects.Explosions.RS82_Water(shot1.p, 4F, 1.0F);
            else
                com.maddox.il2.objects.effects.Explosions.BOMB250_Water(shot1.p, 4F, 1.0F);
            break;

        case 2: // '\002'
            com.maddox.il2.objects.effects.Explosions.Bullet_Object(shot1.p, shot1.v, 0.5F, 1.0F);
            break;

        default:
            com.maddox.il2.objects.effects.Explosions.Bullet_Object(shot1.p, shot1.v, 1.0F, 1.0F);
            break;
        }
    }

    public static void generateExplosion(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f, int i, float f1, double d)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.objects.effects.Explosions.generateSound(actor, point3d, f, i, f1);
        rel.set(((com.maddox.JGP.Tuple3d) (point3d)));
        if(actor != null)
        {
            actor.pos.getAbs(tmpLoc);
            actor.pos.getCurrent(l);
            l.interpolate(tmpLoc, d);
            rel.sub(l);
        }
        if(actor == null)
            return;
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            boolean flag = com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (point3d)).x, ((com.maddox.JGP.Tuple3d) (point3d)).y);
            if(f < 0.001F)
            {
                if(!flag)
                    com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/12_Burn.eff", -1F);
            } else
            if(f < 0.005F)
            {
                if(!flag)
                    com.maddox.il2.engine.Eff3DActor.New(rel, 1.0F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1F);
            } else
            if(f < 0.05F)
            {
                if(flag)
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(point3d, 4F, 1.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(point3d, 4F, 1.0F);
            } else
            if(f < 1.0F)
            {
                if(flag)
                    com.maddox.il2.objects.effects.Explosions.RS82_Water(point3d, 4F, 1.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.RS82_Land(point3d, 4F, 1.0F, false);
            } else
            if(f < 15F)
            {
                if(flag)
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Water(point3d, 4F, 1.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.Explode10Kg_Land(point3d, 4F, 1.0F);
            } else
            if(f < 50F)
            {
                if(flag)
                    com.maddox.il2.objects.effects.Explosions.RS82_Water(point3d, 4F, 1.0F);
                else
                    com.maddox.il2.objects.effects.Explosions.RS82_Land(point3d, 4F, 1.0F, false);
            } else
            if(flag)
                com.maddox.il2.objects.effects.Explosions.BOMB250_Water(point3d, 4F, 1.0F);
            else
                com.maddox.il2.objects.effects.Explosions.BOMB250_Land(point3d, 4F, 1.0F, false);
        } else
        if(f < 0.001F)
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/12_Burn.eff", -1F);
        else
        if(f < 0.003F)
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/12mmPluff.eff", 0.15F);
        else
        if(f < 0.005F)
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.5F, "3DO/Effects/Fireworks/20_Burn.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.5F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.5F, "3DO/Effects/Fireworks/20_Sparks.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.5F, "3DO/Effects/Fireworks/20_SparksP.eff", -1F);
        } else
        if(f < 0.01F)
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.75F, "3DO/Effects/Fireworks/20_Burn.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.75F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.75F, "3DO/Effects/Fireworks/20_Sparks.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 0.75F, "3DO/Effects/Fireworks/20_SparksP.eff", -1F);
        } else
        if(f < 0.02F)
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/20_Burn.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/20_Sparks.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/20_SparksP.eff", -1F);
        } else
        if(f < 1.0F)
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/37_Burn.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/37_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/37_Sparks.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 1.0F, "3DO/Effects/Fireworks/37_SparksP.eff", -1F);
        } else
        if(f < 9999F)
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 3F, "3DO/Effects/Fireworks/37_Burn.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 3F, "3DO/Effects/Fireworks/37_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 3F, "3DO/Effects/Fireworks/37_Sparks.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), rel, 3F, "3DO/Effects/Fireworks/37_SparksP.eff", -1F);
        }
    }

    public static void generateComicBulb(com.maddox.il2.engine.Actor actor, java.lang.String s, float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.il2.ai.World.cur().isArcade())
        {
            return;
        } else
        {
            com.maddox.il2.engine.Eff3DActor.New(actor, ((com.maddox.il2.engine.Hook) (null)), ((com.maddox.il2.engine.Loc) (null)), 1.0F, "3DO/Effects/Debug/msg" + s + ".eff", f);
            return;
        }
    }

    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.il2.engine.Loc l = new Loc();
    private static com.maddox.il2.engine.Loc rel = new Loc();
    private static com.maddox.il2.engine.Loc tmpLoc = new Loc();
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();
    private static com.maddox.JGP.Point3d ap;
    private static final int LAND = 0;
    private static final int WATER = 1;
    private static final int OBJECT = 2;
    private static final int BOMB250 = 0;
    private static final int BOMB1000 = 2;
    private static final int RS82 = 1;
    public static final int HOUSEEXPL_WOOD_SMALL = 0;
    public static final int HOUSEEXPL_WOOD_MIDDLE = 1;
    public static final int HOUSEEXPL_ROCK_MIDDLE = 2;
    public static final int HOUSEEXPL_ROCK_BIG = 3;
    public static final int HOUSEEXPL_ROCK_HUGE = 4;
    public static final int HOUSEEXPL_FUEL_SMALL = 5;
    public static final int HOUSEEXPL_FUEL_BIG = 6;
    private static boolean bEnableActorCrater = true;

}
