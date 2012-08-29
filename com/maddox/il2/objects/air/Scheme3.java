// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Scheme3.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Squares;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft

public abstract class Scheme3 extends com.maddox.il2.objects.air.Aircraft
{

    public Scheme3()
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -25F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -25F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -33F * f, 0.0F);
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -33F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -33F * f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        float f1 = -70F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 13: // '\r'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        default:
            break;

        case 11: // '\013'
            super.cutFM(17, j, actor);
            return super.cutFM(18, j, actor);

        case 12: // '\f'
            super.cutFM(17, j, actor);
            return super.cutFM(18, j, actor);

        case 17: // '\021'
            super.cutFM(18, j, actor);
            FM.cut(17, j, actor);
            FM.cut(18, j, actor);
            if(com.maddox.il2.ai.World.Rnd().nextBoolean())
            {
                FM.Sq.liftWingLOut *= 0.95F;
                FM.Sq.liftWingLMid *= 0.95F;
                FM.Sq.liftWingLIn *= 0.95F;
                FM.Sq.liftWingRIn *= 0.75F;
                FM.Sq.liftWingRMid *= 0.75F;
                FM.Sq.liftWingROut *= 0.75F;
            } else
            {
                FM.Sq.liftWingLOut *= 0.75F;
                FM.Sq.liftWingLMid *= 0.75F;
                FM.Sq.liftWingLIn *= 0.75F;
                FM.Sq.liftWingRIn *= 0.95F;
                FM.Sq.liftWingRMid *= 0.95F;
                FM.Sq.liftWingROut *= 0.95F;
            }
            break;

        case 18: // '\022'
            super.cutFM(17, j, actor);
            FM.cut(17, j, actor);
            FM.cut(18, j, actor);
            if(com.maddox.il2.ai.World.Rnd().nextBoolean())
            {
                FM.Sq.liftWingLOut *= 0.95F;
                FM.Sq.liftWingLMid *= 0.95F;
                FM.Sq.liftWingLIn *= 0.95F;
                FM.Sq.liftWingRIn *= 0.75F;
                FM.Sq.liftWingRMid *= 0.75F;
                FM.Sq.liftWingROut *= 0.75F;
            } else
            {
                FM.Sq.liftWingLOut *= 0.75F;
                FM.Sq.liftWingLMid *= 0.75F;
                FM.Sq.liftWingLIn *= 0.75F;
                FM.Sq.liftWingRIn *= 0.95F;
                FM.Sq.liftWingRMid *= 0.95F;
                FM.Sq.liftWingROut *= 0.95F;
            }
            break;
        }
        return super.cutFM(i, j, actor);
    }
}
