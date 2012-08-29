// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HomePath.java

package com.maddox.rts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            RTS

public final class HomePath
{

    public static final java.util.List list()
    {
        java.util.ArrayList arraylist = new ArrayList();
        int i = 0;
        do
        {
            java.lang.String s = com.maddox.rts.HomePath.get(i);
            if(s == null)
                return arraylist;
            arraylist.add(s);
            i++;
        } while(true);
    }

    private static final java.lang.String subNames(java.lang.String s, java.lang.String s1, boolean flag)
    {
        int i;
        for(i = s.length() - 1; i >= 0; i--)
        {
            char c = s.charAt(i);
            if(c != '/' && c != '\\')
                continue;
            i++;
            break;
        }

        if(i <= 0)
            i = 0;
        int j;
        for(j = s1.length() - 1; j >= 0; j--)
        {
            char c1 = s1.charAt(j);
            if(c1 != '/' && c1 != '\\')
                continue;
            j++;
            break;
        }

        if(j == s1.length())
            return null;
        if(j <= 0)
            j = 0;
        int l;
        for(l = 0; l < i && l < j; l++)
        {
            char c2 = java.lang.Character.toLowerCase(s.charAt(l));
            if(c2 == '\\')
                c2 = '/';
            char c4 = java.lang.Character.toLowerCase(s1.charAt(l));
            if(c4 == '\\')
                c4 = '/';
            if(c2 != c4)
                break;
        }

        if(l != i && flag)
            return null;
        if(l < i)
            for(; l > 0; l--)
            {
                char c3 = java.lang.Character.toLowerCase(s.charAt(l));
                if(c3 != '\\' && c3 != '/')
                    continue;
                l++;
                break;
            }

        int i1 = l;
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        for(; l < i; l++)
        {
            char c5 = java.lang.Character.toLowerCase(s.charAt(l));
            if(c5 == '\\' || c5 == '/')
                stringbuffer.append("../");
        }

        l = i1;
        for(int k = s1.length(); l < k; l++)
        {
            char c6 = java.lang.Character.toLowerCase(s1.charAt(l));
            if(c6 == '\\')
                c6 = '/';
            stringbuffer.append(c6);
        }

        return stringbuffer.toString();
    }

    public static final java.lang.String toLocalName(java.lang.String s, int i)
    {
        if(!com.maddox.rts.HomePath.isFileSystemName(s))
            return s;
        java.lang.String s1 = com.maddox.rts.HomePath.get(i);
        if(s1 == null)
            return null;
        char c = s1.charAt(s1.length() - 1);
        if(c != '/' && c != '\\')
            return com.maddox.rts.HomePath.subNames(s1 + '/', s, true);
        else
            return com.maddox.rts.HomePath.subNames(s1, s, true);
    }

    public static final java.lang.String toLocalName(java.lang.String s, java.lang.String s1, int i)
    {
        s = com.maddox.rts.HomePath.toLocalName(s, i);
        s1 = com.maddox.rts.HomePath.toLocalName(s1, i);
        if(s == null || s1 == null)
            return null;
        else
            return com.maddox.rts.HomePath.subNames(s, s1, false);
    }

    public static final boolean isFileSystemName(java.lang.String s)
    {
        char c = s.charAt(0);
        if(c == '/' || c == '\\')
            return true;
        if(s.length() > 1)
        {
            char c1 = s.charAt(1);
            if(c1 == ':')
                return true;
        }
        return false;
    }

    public static final java.lang.String concatNames(java.lang.String s, java.lang.String s1)
    {
        if(com.maddox.rts.HomePath.isFileSystemName(s1))
            return s1;
        java.lang.StringBuffer stringbuffer = new StringBuffer(s);
        int i = stringbuffer.length();
        int j = -1;
        for(int k = 0; k < i; k++)
        {
            char c = java.lang.Character.toLowerCase(stringbuffer.charAt(k));
            if(c == '/' || c == '\\')
            {
                c = '/';
                j = k;
            }
            stringbuffer.setCharAt(k, c);
        }

        if(j >= 0 && j + 1 < i)
            stringbuffer.delete(j + 1, i);
        int l = 0;
        for(i = s1.length(); l < i; l++)
        {
            char c1 = s1.charAt(l);
            if(c1 == '.')
            {
                if(++l == i)
                    break;
                c1 = s1.charAt(l);
                if(c1 == '.')
                {
                    if(++l == i)
                        break;
                    c1 = s1.charAt(l);
                    if(c1 == '\\' || c1 == '/')
                    {
                        for(j--; j >= 0; j--)
                            if(stringbuffer.charAt(j) == '/')
                                break;

                        if(j < 0)
                            return null;
                        stringbuffer.delete(j + 1, stringbuffer.length());
                    }
                    continue;
                }
                if(c1 == '\\' || c1 == '/')
                    continue;
                l--;
                break;
            }
            if(c1 >= ' ')
                break;
        }

        if(l == i)
            return null;
        if(l > 0)
            stringbuffer.append(s1.substring(l));
        else
            stringbuffer.append(s1);
        i = stringbuffer.length();
        if(j < 0)
            j = 0;
        for(; j < i; j++)
        {
            char c2 = stringbuffer.charAt(j);
            if(c2 == '\\')
                stringbuffer.setCharAt(j, '/');
        }

        return stringbuffer.toString();
    }

    public static final java.lang.String toFileSystemName(java.lang.String s, int i)
    {
        if(com.maddox.rts.HomePath.isFileSystemName(s))
            return s.replace(notSeparator, java.io.File.separatorChar);
        java.lang.String s1 = com.maddox.rts.HomePath.get(i);
        if(s1 == null)
            return null;
        char c = s1.charAt(s1.length() - 1);
        java.lang.String s2 = null;
        if(c != '/' && c != '\\')
            s2 = com.maddox.rts.HomePath.concatNames(s1 + '/', s);
        else
            s2 = com.maddox.rts.HomePath.concatNames(s1, s);
        if(s2 != null)
            s2 = s2.replace(notSeparator, java.io.File.separatorChar);
        return s2;
    }

    public static final java.lang.String toFileSystemName(java.lang.String s, java.lang.String s1, int i)
    {
        if(com.maddox.rts.HomePath.isFileSystemName(s1))
            return s1.replace(notSeparator, java.io.File.separatorChar);
        if(!com.maddox.rts.HomePath.isFileSystemName(s))
            s = com.maddox.rts.HomePath.toFileSystemName(s, i);
        if(s == null)
            return null;
        java.lang.String s2 = com.maddox.rts.HomePath.concatNames(s, s1);
        if(s2 != null)
            s2 = s2.replace(notSeparator, java.io.File.separatorChar);
        return s2;
    }

    public static final native java.lang.String get(int i);

    public static final native void add(java.lang.String s);

    public static final native void remove(java.lang.String s);

    private HomePath()
    {
    }

    private static char notSeparator;

    static 
    {
        com.maddox.rts.RTS.loadNative();
        if(java.io.File.separatorChar == '/')
            notSeparator = '\\';
        else
            notSeparator = '/';
    }
}
