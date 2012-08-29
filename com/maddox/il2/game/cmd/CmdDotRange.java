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

public class CmdDotRange extends Cmd
{
  public static final String DEFAULT = "DEFAULT";
  public static final String FRIENDLY = "FRIENDLY";
  public static final String FOE = "FOE";
  public static final String DOT = "DOT";
  public static final String COLOR = "COLOR";
  public static final String TYPE = "TYPE";
  public static final String NAME = "NAME";
  public static final String ID = "ID";
  public static final String RANGE = "RANGE";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    boolean bool1 = paramMap.containsKey("FRIENDLY");
    boolean bool2 = paramMap.containsKey("FOE");
    if ((!bool1) && (!bool2)) {
      bool1 = bool2 = 1;
    }
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster())) {
      if (paramMap.containsKey("DEFAULT")) {
        if (bool1) {
          Main.cur().dotRangeFriendly.setDefault();
          ((NetUser)NetEnv.host()).replicateDotRange(true);
        }
        if (bool2) {
          Main.cur().dotRangeFoe.setDefault();
          ((NetUser)NetEnv.host()).replicateDotRange(false);
        }
        return CmdEnv.RETURN_OK;
      }
      if ((paramMap.containsKey("DOT")) || (paramMap.containsKey("COLOR")) || (paramMap.containsKey("TYPE")) || (paramMap.containsKey("NAME")) || (paramMap.containsKey("ID")) || (paramMap.containsKey("RANGE")))
      {
        double d1 = Cmd.arg(paramMap, "DOT", 0, -1.0D) * 1000.0D;
        double d2 = Cmd.arg(paramMap, "COLOR", 0, -1.0D) * 1000.0D;
        double d3 = Cmd.arg(paramMap, "TYPE", 0, -1.0D) * 1000.0D;
        double d4 = Cmd.arg(paramMap, "NAME", 0, -1.0D) * 1000.0D;
        double d5 = Cmd.arg(paramMap, "ID", 0, -1.0D) * 1000.0D;
        double d6 = Cmd.arg(paramMap, "RANGE", 0, -1.0D) * 1000.0D;

        if (bool1) {
          Main.cur().dotRangeFriendly.set(d1, d2, d3, d4, d5, d6);
          ((NetUser)NetEnv.host()).replicateDotRange(true);
        }
        if (bool2) {
          Main.cur().dotRangeFoe.set(d1, d2, d3, d4, d5, d6);
          ((NetUser)NetEnv.host()).replicateDotRange(false);
        }
        return CmdEnv.RETURN_OK;
      }
    }
    if (bool1) typeInfo(true);
    if (bool2) typeInfo(false);
    return CmdEnv.RETURN_OK;
  }

  private void typeInfo(boolean paramBoolean) {
    DotRange localDotRange = paramBoolean ? Main.cur().dotRangeFriendly : Main.cur().dotRangeFoe;
    INFO_HARD(paramBoolean ? "Friendly Dot Ranges:" : "Foe Dot Ranges:");
    INFO_HARD("  DOT\t" + localDotRange.dot() / 1000.0D + " km");
    INFO_HARD("  COLOR\t" + localDotRange.color() / 1000.0D + " km");
    INFO_HARD("  TYPE\t" + localDotRange.type() / 1000.0D + " km");
    INFO_HARD("  NAME\t" + localDotRange.name() / 1000.0D + " km");
    INFO_HARD("  ID\t" + localDotRange.id() / 1000.0D + " km");
    INFO_HARD("  RANGE\t" + localDotRange.range() / 1000.0D + " km");
  }

  public CmdDotRange() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("DEFAULT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("FRIENDLY", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("FOE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("DOT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("COLOR", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("TYPE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NAME", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("ID", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("RANGE", null);
    this._properties.put("NAME", "mp_dotrange");
    this._levelAccess = 1;
  }
}