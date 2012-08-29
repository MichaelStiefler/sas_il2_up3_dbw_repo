package com.maddox.il2.engine.cmd;

import com.maddox.il2.engine.Pre;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CmdPreload extends Cmd
{
  public static final String SAVE = "SAVE";
  public static final String REGISTER = "REGISTER";
  public static final String NOREGISTER = "NOREGISTER";
  public static final String CLEAR = "CLEAR";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (paramMap.containsKey("REGISTER")) {
      Pre.register(true);
    }
    if (paramMap.containsKey("NOREGISTER")) {
      Pre.register(false);
    }
    if (paramMap.containsKey("CLEAR")) {
      Pre.clear();
    }
    if (paramMap.containsKey("SAVE")) {
      Pre.save(Cmd.arg(paramMap, "SAVE", 0));
    }
    if (paramMap.containsKey("_$$")) {
      List localList = (List)paramMap.get("_$$");
      for (int i = 0; i < localList.size(); i++)
        Pre.loading((String)localList.get(i));
    }
    return CmdEnv.RETURN_OK;
  }

  public CmdPreload() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("SAVE", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("REGISTER", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NOREGISTER", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("CLEAR", null);
    this._properties.put("NAME", "preload");
    this._levelAccess = 0;
  }
}