// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombGun.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank, Bomb, GunEmpty

public class BombGun extends com.maddox.il2.engine.Interpolate
    implements com.maddox.il2.ai.BulletEmitter
{

    public BombGun()
    {
        ready = true;
        bHide = false;
        bExternal = true;
        bCassette = false;
        bulletClass = null;
        bombDelay = 0.0F;
        bulletMassa = 0.048F;
        numBombs = 0;
    }

    public void doDestroy()
    {
        ready = false;
        if(bomb != null)
        {
            bomb.destroy();
            bomb = null;
        }
    }

    private boolean nameEQ(com.maddox.il2.engine.HierMesh hiermesh, int i, int j)
    {
        if(hiermesh == null)
            return false;
        hiermesh.setCurChunk(i);
        java.lang.String s = hiermesh.chunkName();
        hiermesh.setCurChunk(j);
        java.lang.String s1 = hiermesh.chunkName();
        int l = java.lang.Math.min(s.length(), s1.length());
        for(int k = 0; k < l; k++)
        {
            char c = s.charAt(k);
            if(c == '_')
                return true;
            if(c != s1.charAt(k))
                return false;
        }

        return true;
    }

    public com.maddox.il2.ai.BulletEmitter detach(com.maddox.il2.engine.HierMesh hiermesh, int i)
    {
        if(!ready)
            return com.maddox.il2.objects.weapons.GunEmpty.get();
        if(i == -1 || nameEQ(hiermesh, i, hook.chunkNum()))
        {
            ready = false;
            bExecuted = true;
            return com.maddox.il2.objects.weapons.GunEmpty.get();
        } else
        {
            return this;
        }
    }

    protected int bullets()
    {
        return actor == null ? 0 : bulletss - actor.hashCode();
    }

    protected void bullets(int i)
    {
        if(actor != null)
            bulletss = i + actor.hashCode();
        else
            bulletss = 0;
    }

    public void hide(boolean flag)
    {
        bHide = flag;
        if(bHide)
        {
            if(com.maddox.il2.engine.Actor.isValid(bomb) && bExternal)
                bomb.drawing(false);
        } else
        if(com.maddox.il2.engine.Actor.isValid(bomb) && bExternal)
            bomb.drawing(true);
    }

    public boolean isHide()
    {
        return bHide;
    }

    public boolean isEnablePause()
    {
        return false;
    }

    public boolean isPause()
    {
        return false;
    }

    public void setPause(boolean flag)
    {
    }

    public void setBombDelay(float f)
    {
        bombDelay = f;
        if(bomb != null)
            bomb.delayExplosion = bombDelay;
    }

    public boolean isExternal()
    {
        return bExternal;
    }

    public boolean isCassette()
    {
        return bCassette;
    }

    public float bulletMassa()
    {
        return bulletMassa;
    }

    public int countBullets()
    {
        return bullets();
    }

    public boolean haveBullets()
    {
        return bullets() != 0;
    }

    public void loadBullets()
    {
        loadBullets(bulletsFull);
    }

    public void _loadBullets(int i)
    {
        loadBullets(i);
    }

    public void loadBullets(int i)
    {
        bullets(i);
        if(bullets() != 0)
        {
            if(!com.maddox.il2.engine.Actor.isValid(bomb))
                newBomb();
        } else
        if(com.maddox.il2.engine.Actor.isValid(bomb))
        {
            bomb.destroy();
            bomb = null;
        }
    }

    public java.lang.Class bulletClass()
    {
        return bulletClass;
    }

    public void setBulletClass(java.lang.Class class1)
    {
        bulletClass = class1;
        bulletMassa = com.maddox.rts.Property.floatValue(bulletClass, "massa", bulletMassa);
    }

    public boolean isShots()
    {
        return bExecuted;
    }

    public void shots(int i, float f)
    {
        shots(i);
    }

    public void shots(int i)
    {
        if(isHide())
            return;
        if(isCassette() && i != 0)
            i = bullets();
        if(bullets() == -1 && i == -1)
            i = 25;
        if(!bExecuted && i != 0)
        {
            if(bullets() == 0)
                return;
            if(bomb instanceof com.maddox.il2.objects.weapons.FuelTank)
                bullets(1);
            curShotStep = 0;
            curShots = i;
            bExecuted = true;
        } else
        if(bExecuted && i != 0)
            curShots = i;
        else
        if(bExecuted && i == 0)
            bExecuted = false;
    }

    protected void interpolateStep()
    {
        bTickShot = false;
        if(curShotStep == 0)
        {
            if(bullets() == 0 || curShots == 0 || !com.maddox.il2.engine.Actor.isValid(actor))
            {
                shots(0);
                return;
            }
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)actor).FM;
                if(flightmodel.getOverload() < 0.0F)
                    return;
            }
            bTickShot = true;
            if(bomb != null)
            {
                bomb.pos.setUpdateEnable(true);
                bomb.pos.resetAsBase();
                bomb.start();
                if(sound != null)
                    sound.play();
                bomb = null;
            }
            if(curShots > 0)
                curShots--;
            if(bullets() > 0)
                bullets(bullets() - 1);
            if(bullets() != 0)
                newBomb();
            curShotStep = shotStep;
        }
        curShotStep--;
    }

    public boolean tick()
    {
        interpolateStep();
        return ready;
    }

    private void newBomb()
    {
        try
        {
            bomb = (com.maddox.il2.objects.weapons.Bomb)bulletClass.newInstance();
            bomb.index = numBombs++;
            bomb.pos.setBase(actor, hook, false);
            bomb.pos.changeHookToRel();
            bomb.pos.resetAsBase();
            if(!bExternal)
                bomb.drawing(false);
            else
                bomb.visibilityAsBase(true);
            bomb.pos.setUpdateEnable(false);
            bomb.delayExplosion = bombDelay;
        }
        catch(java.lang.Exception exception) { }
    }

    public java.lang.String getHookName()
    {
        return hook.name();
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.Loc loc1)
    {
        set(actor, s);
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        set(actor, s);
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        this.actor = actor;
        java.lang.Class class1 = getClass();
        bExternal = com.maddox.rts.Property.containsValue(class1, "external");
        bCassette = com.maddox.rts.Property.containsValue(class1, "cassette");
        bulletClass = (java.lang.Class)com.maddox.rts.Property.value(class1, "bulletClass", null);
        bullets(com.maddox.rts.Property.intValue(class1, "bullets", 1));
        bulletsFull = bullets();
        setBulletClass(bulletClass);
        float f = com.maddox.rts.Property.floatValue(class1, "shotFreq", 0.5F);
        if(f < 0.001F)
            f = 0.001F;
        shotStep = (int)((1.0F / f + com.maddox.rts.Time.tickConstLenFs() / 2.0F) / com.maddox.rts.Time.tickConstLenFs());
        if(shotStep <= 0)
            shotStep = 1;
        hook = (com.maddox.il2.engine.HookNamed)actor.findHook(s);
        newBomb();
        java.lang.String s1 = com.maddox.rts.Property.stringValue(getClass(), "sound", null);
        if(s1 != null)
        {
            bomb.pos.getAbs(loc);
            loc.sub(this.actor.pos.getAbs());
            sound = this.actor.newSound(s1, false);
            if(sound != null)
            {
                com.maddox.sound.SoundFX soundfx = this.actor.getRootFX();
                if(soundfx != null)
                {
                    sound.setParent(soundfx);
                    sound.setPosition(loc.getPoint());
                }
            }
        }
        this.actor.interpPut(this, null, -1L, null);
    }

    protected boolean ready;
    protected com.maddox.il2.objects.weapons.Bomb bomb;
    protected boolean bHide;
    protected boolean bExternal;
    protected boolean bCassette;
    protected com.maddox.il2.engine.HookNamed hook;
    protected java.lang.Class bulletClass;
    protected int bulletsFull;
    private int bulletss;
    protected int shotStep;
    protected float bombDelay;
    protected com.maddox.sound.SoundFX sound;
    protected float bulletMassa;
    private int curShotStep;
    private int curShots;
    protected boolean bTickShot;
    protected int numBombs;
    private static com.maddox.il2.engine.Loc loc = new Loc();

}
