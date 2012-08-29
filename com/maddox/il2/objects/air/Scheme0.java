// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Scheme0.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.ManString;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft, HE_111Z, LI_2, PE_8, 
//            TypeGlider

public abstract class Scheme0 extends com.maddox.il2.objects.air.Aircraft
{

    public Scheme0()
    {
        traktor = new Vector3d();
        offsetReference = new Vector3f();
        bGliderSetUp = false;
        bHasCart = true;
        bHasBoosters = true;
        fireOutTime = 0L;
        boostThrust = 0.0F;
        stringLength = 0.0F;
        stringKx = 0.0F;
        bAttached = false;
    }

    public void hitProp(int i, int j, com.maddox.il2.engine.Actor actor)
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveFan(float f)
    {
    }

    protected void moveFlap(float f)
    {
        float f1 = -50F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
    }

    public void setGliderToTraktor()
    {
        if(!(FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            bGliderSetUp = true;
            return;
        }
        if(FM.AP.way.curr().getTarget() == null)
            FM.AP.way.next();
        if(FM.AP.way.curr().getTarget() != null && com.maddox.il2.engine.Actor.isValid(FM.AP.way.curr().getTarget()) && (FM.AP.way.curr().getTargetActorRandom() instanceof com.maddox.il2.objects.air.Aircraft))
        {
            com.maddox.il2.engine.Actor actor = FM.AP.way.curr().getTargetActorRandom();
            com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)actor).FM;
            if(flightmodel.Loc.x == 0.0D)
                return;
            double d = FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ(FM.Loc.x, FM.Loc.y);
            if(d < (double)(2.0F * FM.Gears.H))
            {
                FM.Loc.z = com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) + (double)FM.Gears.H + 0.0D;
                FM.Or.set(FM.Or.getYaw(), FM.Gears.Pitch, FM.Or.getRoll());
                FM.Or.wrap();
                ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(26);
                FM.Vwld.set(0.0D, 0.0D, 0.0D);
            } else
            {
                ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(24);
                FM.Vwld.set(flightmodel.Vwld);
            }
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)FM;
            com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)flightmodel;
            if(maneuver.Group != null)
                maneuver.Group.delAircraft(this);
            if(maneuver1.Group != null)
                maneuver1.Group.addAircraft(this);
            FM.Leader = flightmodel;
            if((actor instanceof com.maddox.il2.objects.air.HE_111Z) || (actor instanceof com.maddox.il2.objects.air.LI_2))
                ((com.maddox.il2.engine.ActorHMesh)actor).hierMesh().chunkVisible("Cart_D0", true);
            traktor.set(-3D, 0.0D, 0.0D);
            com.maddox.il2.engine.Hook hook = null;
            java.lang.String s = "";
            if(aircNumber() > 1)
                if((actor instanceof com.maddox.il2.objects.air.PE_8) && hierMesh() != null && hierMesh().visibilityR() < 11F)
                    switch(aircIndex())
                    {
                    case 0: // '\0'
                        s = "";
                        stringLength = 70F;
                        break;

                    case 1: // '\001'
                        s = "02";
                        stringLength = 150F;
                        break;

                    case 2: // '\002'
                        s = "01";
                        stringLength = 220F;
                        break;

                    case 3: // '\003'
                        s = "ANameYouCanNeverFind";
                        break;
                    }
                else
                if(aircIndex() > 0)
                    s = "ANameYouCanNeverFind";
            try
            {
                hook = actor.findHook("_TailHook" + s);
            }
            catch(java.lang.Exception exception) { }
            if(hook != null)
            {
                com.maddox.il2.engine.Loc loc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                hook.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), loc);
                traktor.x = loc.getPoint().x;
                traktor.y = loc.getPoint().y;
                traktor.z = loc.getPoint().z;
                com.maddox.JGP.Vector3f vector3f = new Vector3f();
                vector3f.set((float)traktor.x - stringLength, (float)traktor.y, (float)traktor.z);
                flightmodel.Or.transform(vector3f);
                FM.Loc.set(flightmodel.Loc);
                FM.Loc.x += vector3f.x;
                FM.Loc.y += vector3f.y;
                FM.Loc.z += vector3f.z;
                FM.Or.set(flightmodel.Or);
                FM.actor.pos.setAbs(FM.Loc, FM.Or);
                FM.actor.pos.reset();
                double d1 = FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ(FM.Loc.x, FM.Loc.y);
                if(d1 < (double)(2.0F * FM.Gears.H))
                {
                    FM.Loc.z = com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) + (double)FM.Gears.H + 0.0D;
                    debugprintln("FM.z=" + FM.Loc.z + " Land.z=" + com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) + " Gear.h=" + FM.Gears.H);
                    FM.Or.set(FM.Or.getYaw(), FM.Gears.Pitch, FM.Or.getRoll());
                    FM.Or.wrap();
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(26);
                    FM.Vwld.set(0.0D, 0.0D, 0.0D);
                    FM.actor.pos.setAbs(FM.Loc, FM.Or);
                    FM.actor.pos.reset();
                } else
                {
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(24);
                    FM.Vwld.set(flightmodel.Vwld);
                }
                offsetReference.set(stringLength - (float)traktor.x, (float)(-traktor.y), (float)(-traktor.z));
                FM.Offset.set(offsetReference);
                ((com.maddox.il2.ai.air.Maneuver)FM).followOffset.set(offsetReference);
            } else
            {
                ((com.maddox.il2.ai.air.Maneuver)FM).set_task(3);
                debugprintln("Glider attached to a vessel of unsupported type.. Glider autolands..");
                ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(49);
            }
        } else
        {
            ((com.maddox.il2.ai.air.Maneuver)FM).set_task(3);
            debugprintln("Incorrect glider way setup.. Glider autolands..");
            ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(49);
        }
        bGliderSetUp = true;
        bAttached = true;
    }

    public void onAircraftLoaded()
    {
        towString = new ActorSimpleMesh(com.maddox.rts.Property.stringValue(getClass(), "gliderString", "3DO/Arms/TowCable/mono.sim"));
        towString.pos.setBase(this, findHook("_TowHook"), false);
        towString.drawing(false);
        boostThrust = com.maddox.rts.Property.floatValue(getClass(), "gliderBoostThrust", 0.0F);
        stringLength = com.maddox.rts.Property.floatValue(getClass(), "gliderStringLength", 140F);
        stringKx = com.maddox.rts.Property.floatValue(getClass(), "gliderStringKx", 88F);
        towStringCutFactor = com.maddox.rts.Property.floatValue(getClass(), "gliderStringFactor", 1.89F);
        bHasCart = com.maddox.rts.Property.containsValue(getClass(), "gliderCart");
        bHasBoosters = com.maddox.rts.Property.containsValue(getClass(), "gliderBoosters");
        boosterFireOutTime = (long)(com.maddox.rts.Property.floatValue(getClass(), "gliderFireOut", 0.0F) * 1000F);
        super.onAircraftLoaded();
    }

    public void destroy()
    {
        if(com.maddox.il2.engine.Actor.isValid(towString) && !towString.isDestroyed())
            towString.destroy();
        super.destroy();
    }

    public void update(float f)
    {
        if(!bGliderSetUp)
        {
            setGliderToTraktor();
            ploughFM = FM;
        }
        super.update(f);
        if(!(FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        if((((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 24 || ((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 53 || ((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 26 || ((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 64 || ((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 0) && bAttached)
        {
            if(FM.Leader != null && !(FM.Leader.actor instanceof com.maddox.il2.objects.air.TypeGlider))
            {
                traktorFM = FM.Leader;
                ploughFM = FM;
                FM.Offset.set(offsetReference);
                towString.drawing(true);
                v.set(traktor);
                FM.Leader.Or.transform(v);
                v.add(FM.Leader.Loc);
                v.x -= FM.Leader.Vwld.x * (double)com.maddox.rts.Time.tickConstLenFs();
                v.y -= FM.Leader.Vwld.y * (double)com.maddox.rts.Time.tickConstLenFs();
                v.z -= FM.Leader.Vwld.z * (double)com.maddox.rts.Time.tickConstLenFs();
                v.sub(towString.pos.getAbsPoint());
                towString.mesh().setScaleXYZ((float)v.length(), 1.0F, 1.0F);
                if(v.length() > (double)(stringLength * towStringCutFactor))
                {
                    debugprintln("Glider string overstressed (" + (int)v.length() + "metres) and tears off..");
                    ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(49);
                }
                FM.Or.transformInv(v);
                o.setAT0(v);
                towString.pos.setRel(o);
                v.normalize();
                if(v.x < 0.99619501829147339D)
                    FM.Or.increment((float)v.y, (float)v.z, 0.0F);
                FM.EI.engines[0].initializeTowString(force());
                if(((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 26)
                {
                    FM.Or.setYPR(FM.Leader.Or.getYaw(), FM.Or.getPitch(), 0.0F);
                    FM.Or.wrap();
                    if(fireOutTime == 0L && FM.Leader.getSpeedKMH() > 40F && FM.Leader.CT.PowerControl > 0.99F)
                    {
                        fireOutTime = com.maddox.rts.Time.current() + boosterFireOutTime;
                        debugprintln("Firing up take-off rockets!.");
                        doFireBoosters();
                        FM.AS.setGliderBoostOn();
                    }
                }
            } else
            {
                towString.drawing(false);
                bAttached = false;
                FM.EI.engines[0].initializeTowString(0.0F);
            }
        } else
        {
            if(((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 21 && v.length() < (double)(stringLength * towStringCutFactor))
            {
                ((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(24);
                ((com.maddox.il2.ai.air.Pilot)FM).set_task(2);
                return;
            }
            towString.drawing(false);
            bAttached = false;
            FM.EI.engines[0].initializeTowString(0.0F);
            if(!FM.isReadyToDie())
            {
                FM.setReadyToDieSoftly(true);
                debugprintln("Craft out of control (MAN=" + com.maddox.il2.ai.air.ManString.name(((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver()) + "), abandoning flight.");
                traktorFM = null;
                if(((com.maddox.il2.ai.air.Maneuver)FM).get_maneuver() == 44)
                    ((com.maddox.il2.ai.air.Maneuver)FM).bombsOut = true;
            }
        }
        if(bHasCart && FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) > 45D)
        {
            debugprintln("Detaching take-off cart!.");
            doCutCart();
            FM.AS.setGliderCutCart();
            bHasCart = false;
        }
        if(bHasBoosters && com.maddox.rts.Time.current() > fireOutTime + 6000L && FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) > 202D)
        {
            debugprintln("Dropping used boosters!.");
            doCutBoosters();
            FM.AS.setGliderBoostOff();
            bHasBoosters = false;
        }
    }

    private float force()
    {
        float f = 0.0F;
        if(com.maddox.rts.Time.current() < fireOutTime)
            f += boostThrust;
        if(traktor == null)
            return 0.0F;
        tmpv.sub(traktorFM.Loc, ploughFM.Loc);
        ploughFM.Or.transformInv(tmpv);
        if(tmpv.x > (double)stringLength)
            f += ((float)tmpv.x - stringLength) * ((float)tmpv.x - stringLength) * stringKx;
        return f;
    }

    public void doCutCart()
    {
    }

    public void doCutBoosters()
    {
    }

    public void doFireBoosters()
    {
    }

    protected com.maddox.il2.objects.ActorSimpleMesh towString;
    private static com.maddox.il2.engine.Orient o = new Orient();
    protected static com.maddox.JGP.Vector3d v = new Vector3d();
    protected com.maddox.JGP.Vector3d traktor;
    private com.maddox.JGP.Vector3f offsetReference;
    protected boolean bGliderSetUp;
    protected boolean bHasCart;
    protected boolean bHasBoosters;
    protected float towStringCutFactor;
    protected long boosterFireOutTime;
    protected long fireOutTime;
    protected float boostThrust;
    protected float stringLength;
    protected float stringKx;
    private com.maddox.il2.fm.FlightModel traktorFM;
    private com.maddox.il2.fm.FlightModel ploughFM;
    private boolean bAttached;
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();

}
