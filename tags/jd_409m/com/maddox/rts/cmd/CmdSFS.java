package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.SFS;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdSFS extends Cmd
{
  public static final String MOUNT = "MOUNT";
  public static final String UNMOUNT = "UNMOUNT";
  public static boolean bMountError = false;

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    int i = 0;
    String str1;
    if (Cmd.nargs(paramMap, "MOUNT") > 0) {
      str1 = Cmd.arg(paramMap, "MOUNT", 0);
      String str2 = null;
      if (Cmd.nargs(paramMap, "MOUNT") > 1)
        str2 = Cmd.arg(paramMap, "MOUNT", 1);
      try {
        if (str2 != null) SFS.mountAs(str1, str2, 0); else
          SFS.mount(str1, 0);
      } catch (Exception localException2) {
        bMountError = true;
        ERR_HARD("SFS library (" + str1 + ") NOT Mounted: " + localException2.getMessage());
        localException2.printStackTrace();
      }
      i = 1;
    }
    if (Cmd.nargs(paramMap, "UNMOUNT") > 0) {
      str1 = Cmd.arg(paramMap, "UNMOUNT", 0);
      try {
        SFS.unMount(str1);
      } catch (Exception localException1) {
        ERR_HARD("SFS library (" + str1 + ") NOT UnMounted: " + localException1.getMessage());
        localException1.printStackTrace();
      }
      i = 1;
    }

    if (i != 0)
      return CmdEnv.RETURN_OK;
    ERR_HARD("Bad command format");
    return null;
  }

  public CmdSFS() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("MOUNT", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("UNMOUNT", null);
    this._properties.put("NAME", "sfs");
    this._levelAccess = 0;
  }
}