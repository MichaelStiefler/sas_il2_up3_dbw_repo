// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GunGeneric.java

package com.maddox.il2.engine;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.rts.Message;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, GunProperties, MeshShared, ActorPosMove, 
//            LightPointActor, LightPoint, Loc, Eff3DActor, 
//            BulletProperties, ActorPos, Hook, ActorDraw, 
//            Interpolate

public abstract class GunGeneric extends com.maddox.il2.engine.Actor
{
    class Draw extends com.maddox.il2.engine.ActorDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            if(bLighting && light != null)
                if(!com.maddox.il2.engine.Actor.isValid(getOwner()))
                {
                    light.light.setEmit(0.0F, prop.emitR);
                    bLighting = false;
                } else
                {
                    long l = com.maddox.rts.Time.current();
                    long l1 = lastShotTime + (long)(prop.emitTime * 1000F);
                    if(l1 <= l)
                    {
                        light.light.setEmit(0.0F, prop.emitR);
                        bLighting = false;
                    } else
                    {
                        float f = prop.emitI;
                        if(l > lastShotTime)
                        {
                            float f1 = (2.0F * (float)(l - lastShotTime)) / (float)(l1 - lastShotTime);
                            if(f1 < 1.0F)
                                f *= f1;
                        }
                        light.light.setEmit(f, prop.emitR);
                        if(!prop.bUseHookAsRel)
                        {
                            pos.getAbs(com.maddox.il2.engine.GunGeneric.loc);
                            com.maddox.il2.engine.GunGeneric.loc.sub(getOwner().pos.getAbs());
                            com.maddox.il2.engine.GunGeneric.loc.get(light.relPos);
                        }
                    }
                }
            if(!bStarted)
                return 0;
            if(fireMesh != null)
            {
                pos.getRender(com.maddox.il2.engine.GunGeneric.loc);
                return fireMesh.preRender(com.maddox.il2.engine.GunGeneric.loc.getPoint());
            } else
            {
                return 0;
            }
        }

        public void render(com.maddox.il2.engine.Actor actor)
        {
            pos.getRender(com.maddox.il2.engine.GunGeneric.loc);
            if(!fireMesh.putToRenderArray(com.maddox.il2.engine.GunGeneric.loc))
            {
                fireMesh.setPos(com.maddox.il2.engine.GunGeneric.loc);
                fireMesh.render();
            }
        }

        Draw()
        {
        }
    }

    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public void doDestroy()
        {
            destroy();
        }

        public boolean tick()
        {
            if(curSkipTicks > 0)
            {
                curSkipTicks--;
            } else
            {
                if(!bStarted)
                {
                    doEffects(true);
                    bStarted = true;
                }
                interpolateStep();
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public java.lang.String prop_fireMesh()
    {
        return prop.fireMesh;
    }

    public java.lang.String prop_fire()
    {
        return prop.fire;
    }

    public java.lang.String prop_sprite()
    {
        return prop.sprite;
    }

    public float bulletTraceMeshLen(int i)
    {
        return _bulletTraceMeshLen[i];
    }

    protected int bullets()
    {
        return bulletss - hashCode();
    }

    protected void bullets(int i)
    {
        bulletss = i + hashCode();
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        fireMesh = null;
        com.maddox.il2.engine.Eff3DActor.finish(fire);
        fire = null;
        com.maddox.il2.engine.Eff3DActor.finish(smoke);
        smoke = null;
        com.maddox.il2.engine.Eff3DActor.finish(shells);
        shells = null;
        com.maddox.il2.engine.Eff3DActor.finish(sprite);
        sprite = null;
        if(sound != null)
        {
            sound.cancel();
            sound = null;
        }
        bullets(0);
        if(light != null)
        {
            light.light.setEmit(0.0F, prop.emitR);
            bLighting = false;
        }
        super.destroy();
    }

    public java.lang.String getHookName()
    {
        return hookName;
    }

    public boolean isEnablePause()
    {
        return prop.bEnablePause;
    }

    public boolean isPause()
    {
        return bPause;
    }

    public void setPause(boolean flag)
    {
        if(isEnablePause())
            bPause = flag;
    }

    public float bulletMassa()
    {
        return prop.bullet[0].massa;
    }

    public float bulletSpeed()
    {
        return prop.bullet[0].speed;
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
        loadBullets(prop.bullets);
    }

    public void loadBullets(int i)
    {
        if(isDestroyed() || !com.maddox.il2.engine.Actor.isValid(getOwner()))
        {
            bullets(0);
            return;
        }
        if(getOwner().isNetMirror())
            bullets(-1);
        else
            bullets(i);
    }

    public void _loadBullets(int i)
    {
        if(isDestroyed() || !com.maddox.il2.engine.Actor.isValid(getOwner()))
        {
            bullets(0);
            return;
        } else
        {
            bullets(i);
            return;
        }
    }

    public boolean isShots()
    {
        return interpolater.bExecuted;
    }

    public void shots(int i)
    {
        shots(i, 0.0F);
    }

    public void shots(int i, float f)
    {
        if(!interpolater.bExecuted && i != 0)
        {
            if(bullets() == 0)
                return;
            if(isPause())
                return;
            curSkipTicks = (int)((f + com.maddox.rts.Time.tickConstLenFs() / 2.0F) / com.maddox.rts.Time.tickConstLenFs());
            long l = (lastShotTime + (long)(_shotStep * 1000F)) - com.maddox.rts.Time.current();
            if(l > 0L)
            {
                int j = (int)((l + (long)(com.maddox.rts.Time.tickConstLen() / 2)) / (long)com.maddox.rts.Time.tickConstLen());
                if(curSkipTicks < j)
                    curSkipTicks = j;
            }
            curShotStep = 0.0D;
            curShots = i;
            interpolater.bExecuted = true;
            if(prop.bUseHookAsRel)
            {
                pos.setUpdateEnable(true);
                pos.resetAsBase();
            }
        } else
        if(interpolater.bExecuted && i != 0)
            curShots = i;
        else
        if(interpolater.bExecuted && i == 0)
        {
            if(bStarted)
            {
                doEffects(false);
                bStarted = false;
            }
            interpolater.bExecuted = false;
            if(prop.bUseHookAsRel)
                pos.setUpdateEnable(false);
        }
    }

    public abstract void doStartBullet(double d);

    public boolean getTickShot()
    {
        return bTickShot;
    }

    protected void interpolateStep()
    {
        bTickShot = false;
        double d;
        for(d = com.maddox.rts.Time.tickLenFs(); curShotStep < d; curShotStep += _shotStep)
        {
            if(bullets() == 0 || curShots == 0 || !com.maddox.il2.engine.Actor.isValid(getOwner()))
            {
                shots(0);
                return;
            }
            bTickShot = true;
            doStartBullet(curShotStep / d);
            lastShotTime = com.maddox.rts.Time.current();
            bLighting = true;
            if(prop.bCannon)
                fireCannon();
            bulletNum++;
            if(curShots > 0)
            {
                curShots -= prop.bulletsCluster;
                if(curShots < 0)
                    curShots = 0;
            }
            if(bullets() > 0)
            {
                bullets(bullets() - prop.bulletsCluster);
                if(bullets() < 0)
                    bullets(0);
            }
        }

        curShotStep -= d;
    }

    public void doEffects(boolean flag)
    {
        if(!flag)
        {
            com.maddox.il2.engine.Eff3DActor.setIntesity(fire, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(shells, 0.0F);
            if(sprite != null)
                sprite.drawing(false);
            if(!prop.bCannon)
                doSound(false);
        } else
        {
            com.maddox.il2.engine.Eff3DActor.setIntesity(fire, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(shells, 1.0F);
            if(sprite != null)
                sprite.drawing(true);
            if(!prop.bCannon)
                doSound(true);
        }
    }

    protected void fireCannon()
    {
        if(prop_fire() != null)
            com.maddox.il2.engine.Eff3DActor.New(pos, 1.0F, prop_fire(), -1F);
        if(prop.smoke != null)
            com.maddox.il2.engine.Eff3DActor.New(pos, 1.0F, prop.smoke, -1F);
        doSound(true);
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        return new GunProperties();
    }

    public void createdProperties()
    {
        if(prop.fireMesh != null && prop.fireMeshDay == null)
            prop.fireMeshDay = prop.fireMesh;
        if(prop.fire != null && prop.fireDay == null)
            prop.fireDay = prop.fire;
        if(prop.sprite != null && prop.spriteDay == null)
            prop.spriteDay = prop.sprite;
    }

    public static com.maddox.il2.engine.GunProperties getProperties(java.lang.Class class1)
    {
        com.maddox.il2.engine.GunProperties gunproperties = (com.maddox.il2.engine.GunProperties)com.maddox.rts.Property.value(class1, "_gun_properties", null);
        if(gunproperties != null)
            return gunproperties;
        com.maddox.il2.engine.GunGeneric gungeneric = null;
        try
        {
            gungeneric = (com.maddox.il2.engine.GunGeneric)class1.newInstance();
            java.lang.reflect.Method method = class1.getMethod("createProperties", null);
            gunproperties = (com.maddox.il2.engine.GunProperties)method.invoke(gungeneric, null);
            method = class1.getMethod("createdProperties", null);
            gungeneric.prop = gunproperties;
            method.invoke(gungeneric, null);
            gungeneric.destroy();
        }
        catch(java.lang.Exception exception)
        {
            if(com.maddox.il2.engine.Actor.isValid(gungeneric))
                gungeneric.destroy();
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            gunproperties = null;
        }
        return gunproperties;
    }

    private void loadProperties()
    {
        if(prop == null)
        {
            prop = (com.maddox.il2.engine.GunProperties)com.maddox.rts.Property.value(getClass(), "_gun_properties", null);
            if(prop == null)
            {
                prop = createProperties();
                createdProperties();
                com.maddox.rts.Property.set(getClass(), "_gun_properties", prop);
                prop.calculateSteps();
            }
            int i = prop.bullet.length;
            _bulletTraceMesh = new com.maddox.il2.engine.MeshShared[i];
            _bulletTraceMeshLen = new float[i];
            for(int j = 0; j < i; j++)
                if(prop.bullet[j].traceMesh != null)
                {
                    _bulletTraceMesh[j] = com.maddox.il2.engine.MeshShared.get(prop.bullet[j].traceMesh);
                    _bulletTraceMeshLen[j] = _bulletTraceMesh[j].visibilityR();
                    if(_bulletTraceMeshLen[j] < 1.0F)
                        _bulletTraceMeshLen[j] = 1.0F;
                } else
                {
                    _bulletTraceMeshLen[j] = 10F;
                }

        }
        float f = prop.shotFreq;
        if(prop.shotFreqDeviation > 0.0F)
            f += com.maddox.il2.ai.World.Rnd().nextFloat(-prop.shotFreqDeviation * f, prop.shotFreqDeviation * f);
        if(f < 0.001F)
            f = 0.001F;
        _shotStep = (float)prop.bulletsCluster / f;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    protected GunGeneric()
    {
        bulletNum = 0;
        bLighting = false;
        bPause = false;
        bStarted = false;
        lastShotTime = 0L;
        interpolater = new Interpolater();
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public void init()
    {
        int i = prop.bullet.length;
        bulletAG = new float[i];
        bulletKV = new float[i];
        initRealisticGunnery(true);
    }

    public void initRealisticGunnery(boolean flag)
    {
        int i = prop.bullet.length;
        for(int j = 0; j < i; j++)
            if(flag)
            {
                bulletAG[j] = -9.8F;
                bulletKV[j] = (-1.346275F * prop.bullet[j].kalibr * prop.bullet[j].kalibr) / (8F * prop.bullet[j].massa);
            } else
            {
                bulletAG[j] = 0.0F;
                bulletKV[j] = 0.0F;
            }

    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.Loc loc1)
    {
        set(actor, s);
        if(loc1 != null && shells != null)
        {
            shells.pos.setRel(loc1);
            shells.pos.setBase(actor, null, false);
            shells.visibilityAsBase(true);
            shells.pos.setUpdateEnable(false);
        }
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        set(actor, s);
        if(s1 != null && shells != null)
        {
            shells.pos.setBase(actor, actor.findHook(s1), false);
            shells.visibilityAsBase(true);
            if(prop.bUseHookAsRel)
            {
                shells.pos.changeHookToRel();
                shells.pos.setUpdateEnable(false);
            }
        }
    }

    protected void doSound(boolean flag)
    {
        if(sound != null)
            if(flag)
                sound.play();
            else
                sound.stop();
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        loadProperties();
        hookName = s;
        pos = new ActorPosMove(this);
        setOwner(actor);
        if(s != null)
        {
            com.maddox.il2.engine.Hook hook = actor.findHook(s);
            pos.setBase(actor, hook, false);
            chunkIndx = hook.chunkNum();
        } else
        {
            pos.setBase(actor, null, false);
            chunkIndx = -1;
        }
        pos.reset();
        if(prop.bUseHookAsRel)
            pos.changeHookToRel();
        if(prop_fireMesh() != null)
            fireMesh = new MeshShared(prop_fireMesh());
        if(!prop.bCannon)
        {
            if(prop_fire() != null)
            {
                fire = com.maddox.il2.engine.Eff3DActor.New(pos, 1.0F, prop_fire(), -1F);
                if(fire != null)
                {
                    fire.setUseIntensityAsSwitchDraw(true);
                    com.maddox.il2.engine.Eff3DActor.setIntesity(fire, 0.0F);
                }
            }
            if(prop.smoke != null)
            {
                smoke = com.maddox.il2.engine.Eff3DActor.New(pos, 1.0F, prop.smoke, -1F);
                if(smoke != null)
                {
                    smoke.setUseIntensityAsSwitchDraw(true);
                    com.maddox.il2.engine.Eff3DActor.setIntesity(smoke, 0.0F);
                }
            }
        }
        if(prop.sound != null)
        {
            pos.getAbs(loc);
            loc.sub(actor.pos.getAbs());
            if(prop.sound != null)
            {
                sound = actor.newSound(prop.sound, false);
                if(sound != null)
                {
                    com.maddox.sound.SoundFX soundfx = actor.getRootFX();
                    if(soundfx != null)
                        sound.setParent(soundfx);
                    sound.setPosition(loc.getPoint());
                }
            }
        }
        if(prop_sprite() != null)
        {
            sprite = com.maddox.il2.engine.Eff3DActor.New(pos, 1.0F, prop_sprite(), -1F);
            if(sprite != null)
                sprite.drawing(false);
        }
        if(prop.emitColor != null && prop.emitR > 0.0F)
        {
            pos.getAbs(loc);
            loc.sub(actor.pos.getAbs());
            light = new LightPointActor(new LightPoint(), loc.getPoint());
            light.light.setColor(prop.emitColor.x, prop.emitColor.y, prop.emitColor.z);
            light.light.setEmit(0.0F, prop.emitR);
            actor.draw.lightMap().put(getClass().getName() + s, light);
        }
        if(prop.shells != null)
        {
            shells = com.maddox.il2.engine.Eff3DActor.NewPosMove(linit, 1.0F, prop.shells, -1F);
            if(shells != null)
            {
                shells.setUseIntensityAsSwitchDraw(true);
                com.maddox.il2.engine.Eff3DActor.setIntesity(shells, 0.0F);
                shells.pos.setBase(this, null, false);
            }
        }
        if(fireMesh != null || light != null)
        {
            draw = new Draw();
            visibilityAsBase(true);
            drawing(true);
        }
        init();
        actor.interpPut(interpolater, null, -1L, null);
        if(prop.bUseHookAsRel)
            pos.setUpdateEnable(false);
    }

    public com.maddox.il2.engine.GunProperties prop;
    protected float _shotStep;
    private java.lang.String hookName;
    protected int chunkIndx;
    protected static java.util.Random sndRand = new Random();
    protected com.maddox.il2.engine.MeshShared fireMesh;
    protected com.maddox.il2.engine.Eff3DActor fire;
    protected com.maddox.il2.engine.Eff3DActor smoke;
    protected com.maddox.il2.engine.Eff3DActor shells;
    protected com.maddox.il2.engine.Eff3DActor sprite;
    protected com.maddox.il2.engine.LightPointActor light;
    protected com.maddox.sound.SoundFX sound;
    protected com.maddox.il2.engine.MeshShared _bulletTraceMesh[];
    protected float _bulletTraceMeshLen[];
    private int bulletss;
    protected int bulletNum;
    protected boolean bLighting;
    protected boolean bPause;
    public float bulletKV[];
    public float bulletAG[];
    private boolean bStarted;
    private int curSkipTicks;
    private double curShotStep;
    private int curShots;
    private long lastShotTime;
    protected boolean bTickShot;
    protected com.maddox.il2.engine.Interpolater interpolater;
    private static com.maddox.il2.engine.Loc loc = new Loc();
    private static com.maddox.il2.engine.Loc linit = new Loc();







}
