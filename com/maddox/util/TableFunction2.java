// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TableFunction2.java

package com.maddox.util;

import com.maddox.rts.SectFile;
import java.util.ArrayList;

// Referenced classes of package com.maddox.util:
//            NumberTokenizer

public class TableFunction2
{

    private static com.maddox.util.TableFunction2 find(java.lang.String s)
    {
        for(int i = 0; i < functions.size(); i++)
            if(s.equalsIgnoreCase(((com.maddox.util.TableFunction2)functions.get(i)).name))
                return (com.maddox.util.TableFunction2)functions.get(i);

        return null;
    }

    public static com.maddox.util.TableFunction2 Get(java.lang.String s)
    {
        com.maddox.util.TableFunction2 tablefunction2 = com.maddox.util.TableFunction2.find(s);
        if(tablefunction2 == null)
            throw new RuntimeException("TableFunction2 '" + s + "' is unknown (not loaded?)");
        else
            return tablefunction2;
    }

    public static void Load(java.lang.String s, com.maddox.rts.SectFile sectfile, java.lang.String s1, int i, int j)
    {
        if(com.maddox.util.TableFunction2.find(s) != null)
        {
            throw new RuntimeException("TableFunction2 '" + s + "' already loaded");
        } else
        {
            com.maddox.util.TableFunction2 tablefunction2 = new TableFunction2(s, sectfile, s1);
            tablefunction2.check(i, j);
            functions.add(tablefunction2);
            return;
        }
    }

    private void TableError(java.lang.String s)
    {
        throw new RuntimeException("TableFunction2 '" + name + "'error: " + s);
    }

    private TableFunction2(java.lang.String s, com.maddox.rts.SectFile sectfile, java.lang.String s1)
    {
        name = new String(s);
        int i = sectfile.sectionIndex(s1);
        int j = 0;
        for(com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, 0)); numbertokenizer.next() != null;)
            j++;

        if(j <= 0)
            TableError("# arg0");
        int k = sectfile.vars(i) - 1;
        if(k <= 0)
            TableError("# arg1");
        arg0 = new float[j];
        arg1 = new float[k];
        val = new float[k][j];
        com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(sectfile.line(i, 0));
        for(int l = 0; l < j; l++)
            arg0[l] = numbertokenizer1.nextFloat();

        for(int i1 = 0; i1 < k; i1++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer2 = new NumberTokenizer(sectfile.line(i, 1 + i1));
            arg1[i1] = numbertokenizer2.nextFloat();
            for(int j1 = 0; j1 < j; j1++)
                val[i1][j1] = numbertokenizer2.nextFloat();

        }

        for(int k1 = 1; k1 < j; k1++)
            if(arg0[k1 - 1] >= arg0[k1])
                TableError("arg0 non-increasing");

        for(int l1 = 1; l1 < k; l1++)
            if(arg1[l1 - 1] >= arg1[l1])
                TableError("arg1 non-increasing");

    }

    private void check(int i, int j)
    {
        int k = arg0.length;
        int l = arg1.length;
        for(int i1 = 0; i1 < l; i1++)
        {
            for(int j1 = 0; j1 < k; j1++)
            {
                if(i > 0 && j1 > 0 && val[i1][j1 - 1] > val[i1][j1])
                    TableError("decreased val for arg0");
                if(i < 0 && j1 > 0 && val[i1][j1 - 1] < val[i1][j1])
                    TableError("increased val for arg0");
                if(j > 0 && i1 > 0 && val[i1 - 1][j1] > val[i1][j1])
                    TableError("decreased val for arg1");
                if(j < 0 && i1 > 0 && val[i1 - 1][j1] < val[i1][j1])
                    TableError("increased val for arg1");
            }

        }

    }

    public float Value(float f, float f1)
    {
        int i = arg0.length;
        int j = arg1.length;
        int k;
        int l;
        float f2;
        if(f <= arg0[0])
        {
            k = l = 0;
            f2 = 0.0F;
        } else
        if(f >= arg0[i - 1])
        {
            k = l = i - 1;
            f2 = 0.0F;
        } else
        {
            k = 0;
            for(l = i - 1; l - k > 1;)
            {
                int i1 = l + k >> 1;
                if(f >= arg0[i1])
                    k = i1;
                else
                    l = i1;
            }

            f2 = (f - arg0[k]) / (arg0[l] - arg0[k]);
        }
        int j1;
        int k1;
        float f3;
        if(f1 <= arg1[0])
        {
            j1 = k1 = 0;
            f3 = 0.0F;
        } else
        if(f1 >= arg1[j - 1])
        {
            j1 = k1 = j - 1;
            f3 = 0.0F;
        } else
        {
            j1 = 0;
            for(k1 = j - 1; k1 - j1 > 1;)
            {
                int l1 = k1 + j1 >> 1;
                if(f1 >= arg1[l1])
                    j1 = l1;
                else
                    k1 = l1;
            }

            f3 = (f1 - arg1[j1]) / (arg1[k1] - arg1[j1]);
        }
        float f4 = val[j1][k];
        float f5 = val[j1][l];
        float f6 = val[k1][k];
        float f7 = val[k1][l];
        float f8 = f4 + (f5 - f4) * f2;
        float f9 = f6 + (f7 - f6) * f2;
        return f8 + (f9 - f8) * f3;
    }

    private java.lang.String name;
    private float arg0[];
    private float arg1[];
    private float val[][];
    private static java.util.ArrayList functions = new ArrayList();

}
