// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnicodeTo8bit.java

package com.maddox.util;


public class UnicodeTo8bit
{

    public UnicodeTo8bit()
    {
    }

    public static java.lang.String save(java.lang.String s, boolean flag)
    {
        int i = s.length();
        java.lang.StringBuffer stringbuffer = new StringBuffer(i * 2);
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            switch(c)
            {
            case 32: // ' '
                if(j == 0 || flag)
                {
                    stringbuffer.append('\\');
                    stringbuffer.append("u0020");
                } else
                {
                    stringbuffer.append(' ');
                }
                break;

            case 92: // '\\'
                stringbuffer.append('\\');
                stringbuffer.append('\\');
                break;

            case 9: // '\t'
                stringbuffer.append('\\');
                stringbuffer.append('t');
                break;

            case 10: // '\n'
                stringbuffer.append('\\');
                stringbuffer.append('n');
                break;

            case 13: // '\r'
                stringbuffer.append('\\');
                stringbuffer.append('r');
                break;

            case 12: // '\f'
                stringbuffer.append('\\');
                stringbuffer.append('f');
                break;

            default:
                if(c < ' ' || c > '~')
                {
                    stringbuffer.append('\\');
                    stringbuffer.append('u');
                    stringbuffer.append(com.maddox.util.UnicodeTo8bit.toHex(c >> 12 & 0xf));
                    stringbuffer.append(com.maddox.util.UnicodeTo8bit.toHex(c >> 8 & 0xf));
                    stringbuffer.append(com.maddox.util.UnicodeTo8bit.toHex(c >> 4 & 0xf));
                    stringbuffer.append(com.maddox.util.UnicodeTo8bit.toHex(c & 0xf));
                } else
                {
                    stringbuffer.append(c);
                }
                break;
            }
        }

        return stringbuffer.toString();
    }

    public static java.lang.String load(java.lang.String s)
    {
        return com.maddox.util.UnicodeTo8bit.load(s, true);
    }

    public static java.lang.String load(java.lang.String s, boolean flag)
    {
        int i = s.length();
        java.lang.StringBuffer stringbuffer = new StringBuffer(i);
        for(int j = 0; j < i;)
        {
            char c = s.charAt(j++);
            if(c == '\\')
            {
                c = s.charAt(j++);
                if(c == 'u')
                {
                    int k = 0;
                    boolean flag1 = false;
                    int l = j;
                    for(int i1 = 0; i1 < 4; i1++)
                    {
                        if(flag1)
                            break;
                        c = s.charAt(j++);
                        switch(c)
                        {
                        case 48: // '0'
                        case 49: // '1'
                        case 50: // '2'
                        case 51: // '3'
                        case 52: // '4'
                        case 53: // '5'
                        case 54: // '6'
                        case 55: // '7'
                        case 56: // '8'
                        case 57: // '9'
                            k = ((k << 4) + c) - 48;
                            break;

                        case 97: // 'a'
                        case 98: // 'b'
                        case 99: // 'c'
                        case 100: // 'd'
                        case 101: // 'e'
                        case 102: // 'f'
                            k = ((k << 4) + 10 + c) - 97;
                            break;

                        case 65: // 'A'
                        case 66: // 'B'
                        case 67: // 'C'
                        case 68: // 'D'
                        case 69: // 'E'
                        case 70: // 'F'
                            k = ((k << 4) + 10 + c) - 65;
                            break;

                        case 58: // ':'
                        case 59: // ';'
                        case 60: // '<'
                        case 61: // '='
                        case 62: // '>'
                        case 63: // '?'
                        case 64: // '@'
                        case 71: // 'G'
                        case 72: // 'H'
                        case 73: // 'I'
                        case 74: // 'J'
                        case 75: // 'K'
                        case 76: // 'L'
                        case 77: // 'M'
                        case 78: // 'N'
                        case 79: // 'O'
                        case 80: // 'P'
                        case 81: // 'Q'
                        case 82: // 'R'
                        case 83: // 'S'
                        case 84: // 'T'
                        case 85: // 'U'
                        case 86: // 'V'
                        case 87: // 'W'
                        case 88: // 'X'
                        case 89: // 'Y'
                        case 90: // 'Z'
                        case 91: // '['
                        case 92: // '\\'
                        case 93: // ']'
                        case 94: // '^'
                        case 95: // '_'
                        case 96: // '`'
                        default:
                            if(flag)
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                            stringbuffer.append('\\');
                            stringbuffer.append('u');
                            j = l;
                            flag1 = true;
                            break;
                        }
                    }

                    if(!flag1)
                        stringbuffer.append((char)k);
                } else
                if(c == 't')
                    stringbuffer.append('\t');
                else
                if(c == 'r')
                    stringbuffer.append('\r');
                else
                if(c == 'n')
                    stringbuffer.append('\n');
                else
                if(c == 'f')
                {
                    stringbuffer.append('\f');
                } else
                {
                    stringbuffer.append('\\');
                    stringbuffer.append(c);
                }
            } else
            {
                stringbuffer.append(c);
            }
        }

        return stringbuffer.toString();
    }

    private static char toHex(int i)
    {
        return hexDigit[i & 0xf];
    }

    private static final char hexDigit[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };

}
