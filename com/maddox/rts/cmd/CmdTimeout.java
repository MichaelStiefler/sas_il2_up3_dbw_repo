package com.maddox.rts.cmd;

import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.util.QuoteTokenizer;
import java.util.HashMap;

public class CmdTimeout extends Cmd
{
  public Object exec(CmdEnv paramCmdEnv, String paramString)
  {
    QuoteTokenizer localQuoteTokenizer = new QuoteTokenizer(paramString);
    if (!localQuoteTokenizer.hasMoreTokens()) {
      ERR_HARD("timeout not present");
      return null;
    }
    String str = localQuoteTokenizer.nextToken();
    int i = 0;
    try {
      i = Integer.parseInt(str);
    } catch (Exception localException) {
      ERR_HARD("bad format timeout: " + str);
      return null;
    }

    new CmdTimeoutAction(paramCmdEnv, localQuoteTokenizer.getGap(), i);
    return CmdEnv.RETURN_OK;
  }
  public boolean isRawFormat() {
    return true;
  }
  public CmdTimeout() {
    this._properties.put("NAME", "timeout");
    this._levelAccess = 1;
  }
}