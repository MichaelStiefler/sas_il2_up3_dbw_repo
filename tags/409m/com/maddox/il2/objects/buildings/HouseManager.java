// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HouseManager.java

package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.buildings:
//            House

public class HouseManager extends com.maddox.il2.engine.Actor
{
    class HouseNet extends com.maddox.il2.engine.ActorNet
    {

        public void fullUpdateChannel(com.maddox.rts.NetChannel netchannel)
        {
            int i = houses / 64 + 1;
            try
            {
                for(int j = 0; j < i; j++)
                {
                    long l = 0L;
                    for(int k = 0; k < 64; k++)
                    {
                        int i1 = j * 64 + k;
                        if(i1 >= houses)
                            break;
                        if(com.maddox.il2.engine.Actor.isAlive(house[i1]))
                            l |= 1L << k;
                    }

                    if(l != houseInitState[j])
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                        netmsgguaranted.writeShort(j);
                        netmsgguaranted.writeLong(l);
                        postTo(netchannel, netmsgguaranted);
                    }
                }

            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.available() > 4 + com.maddox.rts.NetMsgInput.netObjReferenceLen())
            {
                int i = netmsginput.readUnsignedShort();
                long l = netmsginput.readLong();
                for(int k = 0; k < 64; k++)
                {
                    int i1 = i * 64 + k;
                    if(i1 >= houses)
                        break;
                    com.maddox.il2.objects.buildings.House house2 = house[i1];
                    if(com.maddox.il2.engine.Actor.isValid(house2))
                    {
                        boolean flag = (l & 1L << k) != 0L;
                        house2.setDiedFlag(!flag);
                    }
                }

            } else
            {
                int j = netmsginput.readInt();
                if(j >= houses)
                    return true;
                com.maddox.il2.objects.buildings.House house1 = house[j];
                if(!com.maddox.il2.engine.Actor.isAlive(house1))
                    return true;
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                if(netobj == null)
                    return true;
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                house1.doDieShow();
                com.maddox.il2.ai.World.onActorDied(house1, actor);
                postDie(j, actor, netmsginput.channel());
            }
            return true;
        }

        private void postDie(int i, com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel)
        {
            int j = countMirrors();
            if(isMirror())
                j++;
            if(netchannel != null)
                j--;
            if(j <= 0)
                return;
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeInt(i);
                netmsgguaranted.writeNetObj(actor.net);
                postExclude(netchannel, netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }


        public HouseNet(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }

        public HouseNet(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
        }
    }


    public void destroy()
    {
        if(isDestroyed())
            return;
        for(int i = 0; i < houses; i++)
        {
            if(com.maddox.il2.engine.Actor.isValid(house[i]))
                house[i].destroy();
            house[i] = null;
        }

        house = null;
        houseInitState = null;
        super.destroy();
    }

    public void fullUpdateChannel(com.maddox.rts.NetChannel netchannel)
    {
        ((com.maddox.il2.objects.buildings.HouseNet)net).fullUpdateChannel(netchannel);
    }

    public void onHouseDie(com.maddox.il2.objects.buildings.House house1, com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < houses; i++)
            if(house[i] == house1)
            {
                ((com.maddox.il2.objects.buildings.HouseNet)net).postDie(i, actor, null);
                return;
            }

    }

    private void createNetObj(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new HouseNet(this);
        else
            net = new HouseNet(this, netchannel, i);
    }

    public HouseManager(com.maddox.rts.SectFile sectfile, java.lang.String s, com.maddox.rts.NetChannel netchannel, int i)
    {
        houses = 0;
        house = null;
        houseInitState = null;
        int j = sectfile.sectionIndex(s);
        if(j < 0)
            return;
        int k = sectfile.vars(j);
        houses = k;
        house = new com.maddox.il2.objects.buildings.House[k];
        houseInitState = new long[k / 64 + 1];
        com.maddox.JGP.Point3d point3d = new Point3d();
        com.maddox.il2.engine.Orient orient = new Orient();
        com.maddox.il2.engine.ActorSpawnArg actorspawnarg = new ActorSpawnArg();
        for(int l = 0; l < k; l++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(j, l));
            numbertokenizer.next("");
            java.lang.String s1 = "com.maddox.il2.objects.buildings." + numbertokenizer.next("");
            boolean flag = numbertokenizer.next(1) == 1;
            double d = numbertokenizer.next(0.0D);
            double d1 = numbertokenizer.next(0.0D);
            float f = numbertokenizer.next(0.0F);
            com.maddox.il2.engine.ActorSpawn actorspawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(s1, false);
            if(actorspawn != null)
            {
                point3d.set(d, d1, 0.0D);
                actorspawnarg.point = point3d;
                orient.set(f, 0.0F, 0.0F);
                actorspawnarg.orient = orient;
                try
                {
                    com.maddox.il2.objects.buildings.House house1 = (com.maddox.il2.objects.buildings.House)actorspawn.actorSpawn(actorspawnarg);
                    if(!flag)
                    {
                        house1.setDiedFlag(true);
                    } else
                    {
                        int i1 = l / 64;
                        int j1 = l % 64;
                        houseInitState[i1] |= 1L << j1;
                    }
                    house[l] = house1;
                    house1.setOwner(this);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        }

        createNetObj(netchannel, i);
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.cur().houseManager))
            com.maddox.il2.ai.World.cur().houseManager.destroy();
        com.maddox.il2.ai.World.cur().houseManager = this;
    }

    private int houses;
    private com.maddox.il2.objects.buildings.House house[];
    private long houseInitState[];



}
