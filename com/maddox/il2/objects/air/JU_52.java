// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_52.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme6, TypeTransport, Aircraft, PaintScheme

public abstract class JU_52 extends com.maddox.il2.objects.air.Scheme6
    implements com.maddox.il2.objects.air.TypeTransport
{

    public JU_52()
    {
        bDynamoOperational = true;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 2: // '\002'
            if(FM.turret.length > 0)
                FM.turret[0].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            break;
        }
    }

    protected void moveFan(float f)
    {
        if(bDynamoOperational)
        {
            pk = java.lang.Math.abs((int)(FM.Vwld.length() / 14D));
            if(pk >= 1)
                pk = 1;
        }
        if(bDynamoRotary != (pk == 1))
        {
            bDynamoRotary = pk == 1;
            hierMesh().chunkVisible("Cart_D0", !bDynamoRotary);
            hierMesh().chunkVisible("CartRot_D0", bDynamoRotary);
        }
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - FM.Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("Cart_D0", 0.0F, dynamoOrient, 0.0F);
        super.moveFan(f);
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.5F) < shot.mass)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.5F) < shot.mass)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Engine3") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.5F) < shot.mass)
            FM.AS.hitEngine(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Turret"))
            FM.turret[0].bIsOperable = false;
        if(shot.chunkName.startsWith("Tail1") && com.maddox.il2.objects.air.Aircraft.Pd.z > 0.5D && com.maddox.il2.objects.air.Aircraft.Pd.x > -6D && com.maddox.il2.objects.air.Aircraft.Pd.x < -4.9499998092651367D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
            FM.AS.hitPilot(shot.initiator, 2, (int)(shot.mass * 1000F * 0.5F));
        if(shot.chunkName.startsWith("CF") && com.maddox.il2.objects.air.Aircraft.v1.x < -0.20000000298023224D && com.maddox.il2.objects.air.Aircraft.Pd.x > 2.5999999046325684D && com.maddox.il2.objects.air.Aircraft.Pd.z > 0.73500001430511475D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.178F)
            FM.AS.hitPilot(shot.initiator, com.maddox.il2.objects.air.Aircraft.Pd.y <= 0.0D ? 1 : 0, (int)(shot.mass * 900F));
        if(shot.chunkName.startsWith("WingLIn") && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 2.0999999046325684D)
            FM.AS.hitTank(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.mass * 30F)));
        if(shot.chunkName.startsWith("WingRIn") && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 2.0999999046325684D)
            FM.AS.hitTank(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.mass * 30F)));
        super.msgShot(shot);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.JU_52.moveGear(hierMesh(), f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bDynamoOperational;
    private float dynamoOrient;
    private boolean bDynamoRotary;
    private int pk;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_52.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
