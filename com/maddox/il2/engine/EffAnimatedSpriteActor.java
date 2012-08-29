// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EffAnimatedSprite.java

package com.maddox.il2.engine;

import com.maddox.rts.State;
import com.maddox.rts.States;

// Referenced classes of package com.maddox.il2.engine:
//            Eff3DActor, Eff3D, ActorPosStaticEff3D, ActorPosMove, 
//            ActorPos, Actor, Loc

class EffAnimatedSpriteActor extends com.maddox.il2.engine.Eff3DActor
{
    class _Finish extends com.maddox.rts.State
    {

        public void begin(int i)
        {
            destroy();
        }

        public _Finish(java.lang.Object obj)
        {
            super(obj);
        }
    }


    public void _setIntesity(float f)
    {
        if(states.getState() == 0)
        {
            ((com.maddox.il2.engine.Eff3D)draw).setIntesity(f);
            if(bUseIntensityAsSwitchDraw)
                drawing(f != 0.0F);
        }
    }

    public EffAnimatedSpriteActor(com.maddox.il2.engine.Eff3D eff3d, com.maddox.il2.engine.Loc loc)
    {
        draw = eff3d;
        if(_isStaticPos)
            pos = new ActorPosStaticEff3D(this, loc);
        else
            pos = new ActorPosMove(this, loc);
        states = new States(new java.lang.Object[] {
            new Eff3DActor.Ready(this, this), new _Finish(this)
        });
        states.setState(0);
        drawing(true);
    }

    public EffAnimatedSpriteActor(com.maddox.il2.engine.Eff3D eff3d, com.maddox.il2.engine.ActorPos actorpos)
    {
        draw = eff3d;
        pos = actorpos;
        states = new States(new java.lang.Object[] {
            new Eff3DActor.Ready(this, this), new _Finish(this)
        });
        states.setState(0);
        flags |= 3;
        actorpos.base().pos.addChildren(this);
    }
}
