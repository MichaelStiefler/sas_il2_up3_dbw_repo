// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetBanned.java

package com.maddox.il2.net;

import com.maddox.rts.HomePath;
import com.maddox.rts.NetAddress;
import com.maddox.rts.SFSReader;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import com.maddox.util.StrMath;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NetBanned
{

    public NetBanned()
    {
        name = new ArrayList();
        patt = new ArrayList();
        ip = new ArrayList();
    }

    public boolean isExist(java.lang.String s)
    {
        for(int i = 0; i < name.size(); i++)
            if(s.equals(name.get(i)))
                return true;

        for(int j = 0; j < patt.size(); j++)
            if(com.maddox.util.StrMath.simple((java.lang.String)patt.get(j), s))
                return true;

        return false;
    }

    public boolean isExist(com.maddox.rts.NetAddress netaddress)
    {
        int i = ip.size();
        if(i < 0)
            return false;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(netaddress.getHostAddress(), ".");
        if(numbertokenizer.countTokens() != 4)
            return false;
        for(int j = 0; j < 4; j++)
            tmp[j] = numbertokenizer.next(0);

        for(int k = 0; k < i; k++)
        {
            boolean flag = true;
            int ai[][] = (int[][])ip.get(k);
            for(int l = 0; l < 4; l++)
            {
                if(ai[l][0] == -1 || ai[l][0] <= tmp[l] && tmp[l] <= ai[l][1])
                    continue;
                flag = false;
                break;
            }

            if(flag)
                return true;
        }

        return false;
    }

    public void save()
    {
        save(fileName);
    }

    public void save(java.lang.String s)
    {
        try
        {
            java.io.PrintWriter printwriter = new PrintWriter(new BufferedWriter(new FileWriter(com.maddox.rts.HomePath.toFileSystemName(s, 0))));
            for(int i = 0; i < name.size(); i++)
            {
                java.lang.String s1 = (java.lang.String)name.get(i);
                printwriter.println("name " + com.maddox.util.UnicodeTo8bit.save(s1, true));
            }

            for(int j = 0; j < patt.size(); j++)
            {
                java.lang.String s2 = (java.lang.String)patt.get(j);
                printwriter.println("pattern " + com.maddox.util.UnicodeTo8bit.save(s2, true));
            }

            for(int k = 0; k < ip.size(); k++)
                printwriter.println("ip " + ipItem(k));

            printwriter.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("Save file " + s + " failed: " + ioexception.getMessage());
        }
    }

    public java.lang.String ipItem(int i)
    {
        int ai[][] = (int[][])ip.get(i);
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        for(int j = 0; j < 4; j++)
        {
            if(j != 0)
                stringbuffer.append('.');
            if(ai[j][0] == -1)
                stringbuffer.append('*');
            else
            if(ai[j][0] == ai[j][1])
            {
                stringbuffer.append(ai[j][0]);
            } else
            {
                stringbuffer.append(ai[j][0]);
                stringbuffer.append('-');
                stringbuffer.append(ai[j][1]);
            }
        }

        return stringbuffer.toString();
    }

    public void load()
    {
        load(fileName);
    }

    public void load(java.lang.String s)
    {
        try
        {
            java.io.BufferedReader bufferedreader = new BufferedReader(new SFSReader(s));
            do
            {
                java.lang.String s1 = bufferedreader.readLine();
                if(s1 == null)
                    break;
                int i = s1.length();
                if(i != 0)
                {
                    com.maddox.util.SharedTokenizer.set(s1);
                    java.lang.String s2 = com.maddox.util.SharedTokenizer.next(null);
                    if(s2 != null)
                    {
                        java.lang.String s3 = com.maddox.util.SharedTokenizer.next(null);
                        if(s3 != null)
                            if(s2.equals("name"))
                            {
                                if(!name.contains(s3))
                                    name.add(s3);
                            } else
                            if(s2.equals("pattern"))
                            {
                                if(!patt.contains(s3))
                                    patt.add(s3);
                            } else
                            if(s2.equals("ip"))
                            {
                                int ai[][] = ipItem(s3);
                                if(ai != null && findIpItem(ai) == -1)
                                    ip.add(ai);
                            }
                    }
                }
            } while(true);
            bufferedreader.close();
        }
        catch(java.io.IOException ioexception)
        {
            java.lang.System.out.println("File " + s + " load failed: " + ioexception.getMessage());
        }
    }

    public int[][] ipItem(java.lang.String s)
    {
        com.maddox.util.NumberTokenizer numbertokenizer;
        int ai[][];
        numbertokenizer = new NumberTokenizer(s, ".");
        if(numbertokenizer.countTokens() != 4)
            return null;
        ai = new int[4][2];
        int i = 0;
          goto _L1
_L5:
        java.lang.String s1;
        s1 = numbertokenizer.next();
        if("*".equals(s1))
        {
            ai[i][0] = ai[i][1] = -1;
            continue; /* Loop/switch isn't completed */
        }
        if(s1.indexOf("-") < 0) goto _L3; else goto _L2
_L2:
        com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(s1, "-");
        ai[i][0] = numbertokenizer1.next(-1);
        ai[i][1] = numbertokenizer1.next(-1);
        if(ai[i][0] == -1)
            return null;
        if(ai[i][1] == -1)
            return null;
        continue; /* Loop/switch isn't completed */
_L3:
        int j = java.lang.Integer.parseInt(s1);
        ai[i][0] = ai[i][1] = j;
        i++;
_L1:
        if(i < 4) goto _L5; else goto _L4
_L4:
        break MISSING_BLOCK_LABEL_176;
        java.lang.Exception exception;
        exception;
        return null;
        return ai;
    }

    public int findIpItem(int ai[][])
    {
        for(int i = 0; i < ip.size(); i++)
        {
            int ai1[][] = (int[][])ip.get(i);
            boolean flag = true;
            for(int j = 0; j < 4; j++)
            {
                for(int k = 0; k < 2; k++)
                    if(ai1[j][k] != ai[j][k])
                        flag = false;

            }

            if(flag)
                return i;
        }

        return -1;
    }

    public static java.lang.String fileName = "banned.txt";
    public java.util.ArrayList name;
    public java.util.ArrayList patt;
    public java.util.ArrayList ip;
    private static int tmp[] = new int[4];

}
