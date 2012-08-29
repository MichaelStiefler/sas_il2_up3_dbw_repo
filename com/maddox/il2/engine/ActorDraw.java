// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorDraw.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Destroy;
import com.maddox.rts.ObjState;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundList;
import com.maddox.util.HashMapExt;
import java.util.Map;

// Referenced classes of package com.maddox.il2.engine:
//            LightPointActor, LightPointWorld, LightPoint, Loc, 
//            Config, Actor, Orient

public abstract class ActorDraw
    implements com.maddox.rts.Destroy
{

    public int preRender(com.maddox.il2.engine.Actor actor)
    {
        return 0;
    }

    public void render(com.maddox.il2.engine.Actor actor)
    {
    }

    public void renderShadowProjective(com.maddox.il2.engine.Actor actor)
    {
    }

    public void renderShadowVolume(com.maddox.il2.engine.Actor actor)
    {
    }

    public void renderShadowVolumeHQ(com.maddox.il2.engine.Actor actor)
    {
    }

    public void lightUpdate(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        if(lightMap != null)
        {
            java.util.Map.Entry entry = lightMap.nextEntry(null);
            if(flag)
                for(; entry != null; entry = lightMap.nextEntry(entry))
                {
                    com.maddox.il2.engine.LightPointActor lightpointactor = (com.maddox.il2.engine.LightPointActor)entry.getValue();
                    if(lightpointactor.light instanceof com.maddox.il2.engine.LightPointWorld)
                    {
                        com.maddox.il2.engine.LightPointWorld lightpointworld = (com.maddox.il2.engine.LightPointWorld)lightpointactor.light;
                        if(lightpointworld.I > 0.0F && lightpointworld.R > 0.0F)
                        {
                            computeAbsPos(loc, lightpointactor.relPos);
                            lightpointworld.setPos(absPos);
                        }
                    }
                }

            else
                for(; entry != null; entry = lightMap.nextEntry(entry))
                {
                    com.maddox.il2.engine.LightPointActor lightpointactor1 = (com.maddox.il2.engine.LightPointActor)entry.getValue();
                    if((lightpointactor1.light instanceof com.maddox.il2.engine.LightPoint) && !(lightpointactor1.light instanceof com.maddox.il2.engine.LightPointWorld))
                    {
                        com.maddox.il2.engine.LightPoint lightpoint = lightpointactor1.light;
                        if(lightpoint.I > 0.0F && lightpoint.R > 0.0F)
                        {
                            computeAbsPos(loc, lightpointactor1.relPos);
                            lightpoint.setPos(absPos);
                            lightpoint.addToRender();
                        }
                    }
                }

        }
    }

    private void computeAbsPos(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d)
    {
        relLocate.set(point3d);
        absLocate.add(relLocate, loc);
        absLocate.get(absPos);
    }

    public com.maddox.util.HashMapExt lightMap()
    {
        if(lightMap == null)
            lightMap = new HashMapExt();
        return lightMap;
    }

    public void soundUpdate(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc)
    {
        if(sounds != null && com.maddox.il2.engine.Config.cur.isSoundUse())
        {
            for(com.maddox.sound.SoundFX soundfx = sounds.get(); soundfx != null;)
            {
                int i = soundfx.getCaps();
                if(i == -1)
                {
                    com.maddox.sound.SoundFX soundfx1 = soundfx.next();
                    soundfx.destroy();
                    soundfx = soundfx1;
                } else
                {
                    if((i & 1) != 0)
                        if(actor == null || (i & 2) == 0)
                        {
                            if((i & 4) != 0)
                            {
                                if((i & 8) != 0)
                                    computeAheadUp(loc.getOrient());
                                else
                                    computeAhead(loc.getOrient());
                                soundfx.setPosition(loc.getPoint());
                                soundfx.setOrientation((float)ahead.x, (float)ahead.y, (float)ahead.z);
                                if((i & 8) != 0)
                                    soundfx.setTop((float)up.x, (float)up.y, (float)up.z);
                            } else
                            {
                                soundfx.setPosition(loc.getPoint());
                            }
                        } else
                        {
                            actor.getSpeed(vspeed);
                            if((i & 4) != 0)
                            {
                                if((i & 8) != 0)
                                    computeAheadUp(loc.getOrient());
                                else
                                    computeAhead(loc.getOrient());
                                soundfx.setPosition(loc.getPoint());
                                soundfx.setVelocity((float)vspeed.x, (float)vspeed.y, (float)vspeed.z);
                                soundfx.setOrientation((float)ahead.x, (float)ahead.y, (float)ahead.z);
                                if((i & 8) != 0)
                                    soundfx.setTop((float)up.x, (float)up.y, (float)up.z);
                            } else
                            {
                                soundfx.setPosition(loc.getPoint());
                                soundfx.setVelocity((float)vspeed.x, (float)vspeed.y, (float)vspeed.z);
                            }
                        }
                    soundfx = soundfx.next();
                }
            }

        }
    }

    private void computeAhead(com.maddox.il2.engine.Orient orient)
    {
        ahead.set(1.0D, 0.0D, 0.0D);
        orient.transform(ahead);
    }

    private void computeAheadUp(com.maddox.il2.engine.Orient orient)
    {
        ahead.set(1.0D, 0.0D, 0.0D);
        up.set(0.0D, 0.0D, 1.0D);
        orient.transform(ahead);
        orient.transform(up);
    }

    public com.maddox.sound.SoundList sounds()
    {
        if(sounds == null)
            sounds = new SoundList();
        return sounds;
    }

    public boolean isDestroyed()
    {
        return lightMap == null && sounds == null;
    }

    public void destroy()
    {
        if(lightMap != null)
        {
            for(java.util.Map.Entry entry = lightMap.nextEntry(null); entry != null; entry = lightMap.nextEntry(entry))
            {
                com.maddox.il2.engine.LightPointActor lightpointactor = (com.maddox.il2.engine.LightPointActor)entry.getValue();
                com.maddox.rts.ObjState.destroy(lightpointactor);
            }

            lightMap.clear();
            lightMap = null;
        }
        if(sounds != null)
        {
            sounds.destroy();
            sounds = null;
        }
    }

    public ActorDraw(com.maddox.il2.engine.ActorDraw actordraw)
    {
        uniformMaxDist = 0.0F;
        lightMap = null;
        sounds = null;
        if(actordraw != null)
        {
            lightMap = actordraw.lightMap;
            sounds = actordraw.sounds;
            uniformMaxDist = actordraw.uniformMaxDist;
        }
    }

    public ActorDraw()
    {
        uniformMaxDist = 0.0F;
        lightMap = null;
        sounds = null;
    }

    public static final int INVISIBLE = 0;
    public static final int SOLID = 1;
    public static final int TRANSPARENT = 2;
    public static final int SHADOW = 4;
    public float uniformMaxDist;
    private static com.maddox.JGP.Point3d absPos = new Point3d();
    private static com.maddox.il2.engine.Loc absLocate = new Loc();
    private static com.maddox.il2.engine.Loc relLocate = new Loc();
    protected com.maddox.util.HashMapExt lightMap;
    private static com.maddox.JGP.Vector3d vspeed = new Vector3d();
    private static com.maddox.JGP.Vector3d ahead = new Vector3d();
    private static com.maddox.JGP.Vector3d up = new Vector3d();
    protected com.maddox.sound.SoundList sounds;

}
