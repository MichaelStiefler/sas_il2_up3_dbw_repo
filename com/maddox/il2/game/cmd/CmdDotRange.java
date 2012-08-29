// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdDotRange.java

package com.maddox.il2.game.cmd;

import com.maddox.il2.game.DotRange;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdDotRange extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        boolean flag = map.containsKey("FRIENDLY");
        boolean flag1 = map.containsKey("FOE");
        if(!flag && !flag1)
            flag = flag1 = true;
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            if(map.containsKey("DEFAULT"))
            {
                if(flag)
                {
                    com.maddox.il2.game.Main.cur().dotRangeFriendly.setDefault();
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateDotRange(true);
                }
                if(flag1)
                {
                    com.maddox.il2.game.Main.cur().dotRangeFoe.setDefault();
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateDotRange(false);
                }
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
            if(map.containsKey("DOT") || map.containsKey("COLOR") || map.containsKey("TYPE") || map.containsKey("NAME") || map.containsKey("ID") || map.containsKey("RANGE"))
            {
                double d = com.maddox.rts.Cmd.arg(map, "DOT", 0, -1D) * 1000D;
                double d1 = com.maddox.rts.Cmd.arg(map, "COLOR", 0, -1D) * 1000D;
                double d2 = com.maddox.rts.Cmd.arg(map, "TYPE", 0, -1D) * 1000D;
                double d3 = com.maddox.rts.Cmd.arg(map, "NAME", 0, -1D) * 1000D;
                double d4 = com.maddox.rts.Cmd.arg(map, "ID", 0, -1D) * 1000D;
                double d5 = com.maddox.rts.Cmd.arg(map, "RANGE", 0, -1D) * 1000D;
                if(flag)
                {
                    com.maddox.il2.game.Main.cur().dotRangeFriendly.set(d, d1, d2, d3, d4, d5);
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateDotRange(true);
                }
                if(flag1)
                {
                    com.maddox.il2.game.Main.cur().dotRangeFoe.set(d, d1, d2, d3, d4, d5);
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateDotRange(false);
                }
                return com.maddox.rts.CmdEnv.RETURN_OK;
            }
        }
        if(flag)
            typeInfo(true);
        if(flag1)
            typeInfo(false);
        return com.maddox.rts.CmdEnv.RETURN_OK;
    }

    private void typeInfo(boolean flag)
    {
        com.maddox.il2.game.DotRange dotrange = flag ? com.maddox.il2.game.Main.cur().dotRangeFriendly : com.maddox.il2.game.Main.cur().dotRangeFoe;
        INFO_HARD(flag ? "Friendly Dot Ranges:" : "Foe Dot Ranges:");
        INFO_HARD("  DOT\t" + dotrange.dot() / 1000D + " km");
        INFO_HARD("  COLOR\t" + dotrange.color() / 1000D + " km");
        INFO_HARD("  TYPE\t" + dotrange.type() / 1000D + " km");
        INFO_HARD("  NAME\t" + dotrange.name() / 1000D + " km");
        INFO_HARD("  ID\t" + dotrange.id() / 1000D + " km");
        INFO_HARD("  RANGE\t" + dotrange.range() / 1000D + " km");
    }

    public CmdDotRange()
    {
        param.put("DEFAULT", null);
        param.put("FRIENDLY", null);
        param.put("FOE", null);
        param.put("DOT", null);
        param.put("COLOR", null);
        param.put("TYPE", null);
        param.put("NAME", null);
        param.put("ID", null);
        param.put("RANGE", null);
        _properties.put("NAME", "mp_dotrange");
        _levelAccess = 1;
    }

    public static final java.lang.String DEFAULT = "DEFAULT";
    public static final java.lang.String FRIENDLY = "FRIENDLY";
    public static final java.lang.String FOE = "FOE";
    public static final java.lang.String DOT = "DOT";
    public static final java.lang.String COLOR = "COLOR";
    public static final java.lang.String TYPE = "TYPE";
    public static final java.lang.String NAME = "NAME";
    public static final java.lang.String ID = "ID";
    public static final java.lang.String RANGE = "RANGE";
}
