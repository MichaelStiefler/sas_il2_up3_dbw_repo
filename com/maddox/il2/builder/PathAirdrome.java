// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PathAirdrome.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;

// Referenced classes of package com.maddox.il2.builder:
//            Path, PPoint, Pathes, PlMapAirdrome, 
//            PAirdrome, Plugin, Builder

public class PathAirdrome extends com.maddox.il2.builder.Path
{
    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn
    {

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            if(actorspawnarg.isNoExistHARD(actorspawnarg.rawData, "data not present"))
                return null;
            com.maddox.il2.builder.PlMapAirdrome plmapairdrome = (com.maddox.il2.builder.PlMapAirdrome)com.maddox.il2.builder.Plugin.getPlugin("MapAirdrome");
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(actorspawnarg.rawData, " ");
            int i = numbertokenizer.next(0, 0, 2);
            int j = numbertokenizer.next(0);
            com.maddox.il2.builder.PathAirdrome pathairdrome = new PathAirdrome(com.maddox.il2.builder.Plugin.builder.pathes, i);
            com.maddox.rts.Property.set(pathairdrome, "builderPlugin", plmapairdrome);
            pathairdrome.drawing(plmapairdrome.mView.bChecked);
            java.lang.Object obj = null;
            com.maddox.JGP.Point3d point3d = new Point3d();
            for(int k = 0; k < j; k++)
            {
                point3d.set(numbertokenizer.next(0.0F), numbertokenizer.next(0.0F), 0.0D);
                point3d.z = com.maddox.il2.engine.Engine.land().HQ(point3d.x, point3d.y) + 0.20000000000000001D;
                obj = new PAirdrome(pathairdrome, ((com.maddox.il2.builder.PPoint) (obj)), point3d, i);
                com.maddox.rts.Property.set(obj, "builderPlugin", plmapairdrome);
                com.maddox.rts.Property.set(obj, "builderSpawn", "");
            }

            return pathairdrome;
        }

        public SPAWN()
        {
        }
    }


    public PathAirdrome(com.maddox.il2.builder.Pathes pathes, int i)
    {
        super(pathes);
        _iType = i;
    }

    public static void configure()
    {
    }

    public static java.lang.String toSpawnString(com.maddox.il2.builder.PathAirdrome pathairdrome)
    {
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("spawn ");
        stringbuffer.append(pathairdrome.getClass().getName());
        stringbuffer.append(" RAWDATA ");
        stringbuffer.append(pathairdrome._iType);
        stringbuffer.append(" ");
        int i = pathairdrome.points();
        stringbuffer.append(i);
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.PPoint ppoint = pathairdrome.point(j);
            stringbuffer.append(" ");
            stringbuffer.append(ppoint.pos.getAbsPoint().x);
            stringbuffer.append(" ");
            stringbuffer.append(ppoint.pos.getAbsPoint().y);
        }

        return stringbuffer.toString();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public int _iType;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.builder.PathAirdrome.class, new SPAWN());
    }
}
