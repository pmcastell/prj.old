// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JAX


public class b
{

    public final int bh()
    {
        return bi;
    }

    public final void bg(int i)
    {
        bj = bg = bc = (i << 24) >> 24;
    }

    public final void bf(int i)
    {
        be = i;
    }

    public final void be()
    {
        bp = 0;
    }

    public final void bd()
    {
        at();
        a2(bm);
        a3(bh & 0xffffffef);
        bh |= 4;
        bf = 0;
        bm = a6(65530);
        bq += 7;
    }

    public final void bc()
    {
        bl = 0x100 | bl & 0xff;
        bm = a6(65532);
        bh = 38;
        bg = 0;
        bf = 0;
        be = 0;
        bd = 0;
        bc = 0;
        a7 = false;
    }

    private final void bb()
    {
        bm++;
        a2(bm);
        at();
        a3(bh | 0x10);
        bh = (bh | 4) & -9;
        bf = 0;
        bm = a6(65534);
        bq += 5;
    }

    private final void ba()
    {
        if(a7 && (bh & 4) == 0)
        {
            at();
            a2(bm);
            a3(bh & 0xffffffef);
            bh |= 4;
            bf = 0;
            bm = a6(65534);
            bq += 7;
            a7 = false;
        }
    }

    public final int a9(int i)
    {
        ba();
        bp = i;
        bq = 0;
        for(; bp > 0; bp -= bq)
        {
            bo = a5();
            int j = bo >> 2 & 7;
            int k = bo >> 5 & 7;
            br += bq;
label0:
            switch(bo & 3)
            {
            default:
                break;

            case 0: // '\0'
                if((bo & 4) == 0)
                {
                    j = bo >> 3 & 0x1f;
                    bq = 2;
                    b b1;
                    switch(j)
                    {
                    case 0: // '\0'
                        bb();
                        break;

                    case 1: // '\001'
                        at();
                        a3(bh);
                        break;

                    case 2: // '\002'
                        au((bg & 0x80) == 0);
                        break;

                    case 3: // '\003'
                        be = 0;
                        break;

                    case 4: // '\004'
                        a2(bm + 1);
                        bm = a4();
                        bq += 4;
                        break;

                    case 5: // '\005'
                        b b3;
                        bh = (b3 = this).a9[++b3.bl];
                        bh |= 0x20;
                        as();
                        ba();
                        break;

                    case 6: // '\006'
                        au((bg & 0x80) != 0);
                        break;

                    case 7: // '\007'
                        be = -1;
                        break;

                    case 8: // '\b'
                        b b2;
                        bh = (b2 = this).a9[++b2.bl] | 0x20;
                        bm = a1();
                        as();
                        ba();
                        bq += 4;
                        break;

                    case 9: // '\t'
                        a3(bk);
                        break;

                    case 10: // '\n'
                        au(bd == 0);
                        break;

                    case 11: // '\013'
                        bh &= -5;
                        ba();
                        break;

                    case 12: // '\f'
                        bm = a1() + 1;
                        bq += 4;
                        break;

                    case 13: // '\r'
                        bk = bg = bc = (b1 = this).a9[++b1.bl];
                        break;

                    case 14: // '\016'
                        au(bd != 0);
                        break;

                    case 15: // '\017'
                        bh |= 4;
                        break;

                    case 16: // '\020'
                        ba.ao(bs, bo, a5());
                        break;

                    case 17: // '\021'
                        bi = bg = bc = bi - 1 & 0xff;
                        break;

                    case 18: // '\022'
                        au(be == 0);
                        break;

                    case 19: // '\023'
                        bk = bg = bc = bi;
                        break;

                    case 20: // '\024'
                        bi = bg = bc = a5();
                        break;

                    case 21: // '\025'
                        bi = bg = bc = bk & 0xff;
                        break;

                    case 22: // '\026'
                        au(be != 0);
                        break;

                    case 23: // '\027'
                        bd = 0;
                        break;

                    case 24: // '\030'
                        av(a5());
                        break;

                    case 25: // '\031'
                        bi = bg = bc = bi + 1 & 0xff;
                        break;

                    case 26: // '\032'
                        au((bc & 0xff) != 0);
                        break;

                    case 27: // '\033'
                        bf = 0;
                        break;

                    case 28: // '\034'
                        aw(a5());
                        break;

                    case 29: // '\035'
                        bj = bg = bc = bj + 1 & 0xff;
                        break;

                    case 30: // '\036'
                        au((bc & 0xff) == 0);
                        break;

                    case 31: // '\037'
                        bf = 8;
                        break;
                    }
                    break;
                }
                j >>= 1;
                bq = 4;
                switch(j)
                {
                case 0: // '\0'
                    bn = a5();
                    bq--;
                    break;

                case 1: // '\001'
                    bn = a4();
                    break;

                case 2: // '\002'
                    bn = a5() + bj & 0xff;
                    break;

                default:
                    bn = a4() + bj;
                    break;
                }
                switch(k)
                {
                case 0: // '\0'
                    ar();
                    break;

                case 1: // '\001'
                    ax(a7(bn));
                    break;

                case 2: // '\002'
                    bm = bn;
                    bq--;
                    break;

                case 3: // '\003'
                    bm = a7(bn);
                    bq++;
                    break;

                case 4: // '\004'
                    a8(bn, bi);
                    break;

                case 5: // '\005'
                    bi = bg = bc = a7(bn) & 0xff;
                    break;

                case 6: // '\006'
                    av(a7(bn));
                    break;

                default:
                    aw(a7(bn));
                    break;
                }
                break;

            case 1: // '\001'
                if(k != 4)
                {
                    int l;
                    switch(j)
                    {
                    case 0: // '\0'
                        bq = 6;
                        l = a7(a_());
                        break;

                    case 1: // '\001'
                        bq = 3;
                        l = a7(a5());
                        break;

                    case 2: // '\002'
                        bq = 2;
                        l = a5();
                        break;

                    case 3: // '\003'
                        bq = 4;
                        l = a7(a4());
                        break;

                    case 4: // '\004'
                        bq = 5;
                        l = a7(a0());
                        break;

                    case 5: // '\005'
                        bq = 4;
                        l = a7(a5() + bj & 0xff);
                        break;

                    case 6: // '\006'
                        bq = 4;
                        l = a7(a4() + bi);
                        break;

                    default:
                        bq = 4;
                        l = a7(a4() + bj);
                        break;
                    }
                    switch(k)
                    {
                    case 0: // '\0'
                        bk |= l;
                        bc = bk;
                        break;

                    case 1: // '\001'
                        bk &= l;
                        bc = bk;
                        break;

                    case 2: // '\002'
                        bk ^= l;
                        bc = bk;
                        break;

                    case 3: // '\003'
                        az(l);
                        break;

                    case 5: // '\005'
                        bk = l;
                        bc = bk;
                        break;

                    case 6: // '\006'
                        int j2 = l;
                        b b8 = this;
                        j2 = (0xff & j2) - (0xff & b8.bk);
                        b8.be = j2 - 1 >> 31;
                        b8.bc = -j2;
                        break;

                    case 4: // '\004'
                    default:
                        ay(l);
                        break;
                    }
                    bg = bc;
                    break;
                }
                switch(j)
                {
                case 0: // '\0'
                    bq = 6;
                    bn = a_();
                    break;

                case 1: // '\001'
                    bq = 3;
                    bn = a5();
                    break;

                case 2: // '\002'
                    bq = 2;
                    bn = bm;
                    bm++;
                    break;

                case 3: // '\003'
                    bq = 4;
                    bn = a4();
                    break;

                case 4: // '\004'
                    bq = 6;
                    bn = a0();
                    break;

                case 5: // '\005'
                    bq = 4;
                    bn = a5() + bj & 0xff;
                    break;

                case 6: // '\006'
                    bq = 5;
                    bn = a4() + bi;
                    break;

                default:
                    bq = 5;
                    bn = a4() + bj;
                    break;
                }
                a8(bn, bk);
                break;

            case 2: // '\002'
                if(k == 4 || k == 5)
                {
                    switch(j)
                    {
                    case 0: // '\0'
                        bq = 2;
                        bn = bm;
                        bm++;
                        break;

                    case 1: // '\001'
                        bq = 3;
                        bn = a5();
                        break;

                    case 2: // '\002'
                        bq = 2;
                        if(k == 4)
                            bk = bj;
                        else
                            bj = bk & 0xff;
                        bg = bc = bj;
                        break label0;

                    case 3: // '\003'
                        bq = 5;
                        bn = a4();
                        break;

                    case 4: // '\004'
                        ar();
                        break label0;

                    case 5: // '\005'
                        bq = 4;
                        bn = a5() + bi & 0xff;
                        break;

                    case 6: // '\006'
                        bq = 2;
                        if(k == 4)
                            bl = 0x100 | bj & 0xff;
                        else
                            bj = bl & 0xff;
                        bg = bc = bj;
                        break label0;

                    default:
                        bq = 4;
                        bn = a4() + bi;
                        break;
                    }
                    if(k == 4)
                    {
                        a8(bn, bj);
                        break;
                    }
                    if(j == 3)
                        bq--;
                    bj = bg = bc = a7(bn) & 0xff;
                    break;
                }
                int i1;
                switch(j)
                {
                case 1: // '\001'
                    bq = 5;
                    bn = a5();
                    i1 = a7(bn);
                    break;

                case 2: // '\002'
                    bq = 2;
                    if(k < 6)
                    {
                        i1 = bk;
                    } else
                    {
                        if(k == 6)
                            bg = bc = bj = bj - 1 & 0xff;
                        break label0;
                    }
                    break;

                case 3: // '\003'
                    bq = 6;
                    bn = a4();
                    i1 = a7(bn);
                    break;

                case 5: // '\005'
                    bq = 6;
                    bn = a5() + bj & 0xff;
                    i1 = a7(bn);
                    break;

                case 7: // '\007'
                    bq = 7;
                    bn = a4() + bj;
                    i1 = a7(bn);
                    break;

                case 4: // '\004'
                case 6: // '\006'
                default:
                    ar();
                    break label0;
                }
                switch(k)
                {
                case 0: // '\0'
                    int j1 = i1;
                    b b4 = this;
                    b4.be = -(0x80 & j1) >> 31;
                    i1 = j1 << 1;
                    break;

                case 1: // '\001'
                    int i2 = i1;
                    b b7 = this;
                    i2 = i2 << 1 | b7.be & 1;
                    b7.be = -(0x100 & i2) >> 31;
                    i1 = i2;
                    break;

                case 2: // '\002'
                    int k1 = i1;
                    b b5 = this;
                    b5.be = -(k1 & 1);
                    k1 = k1 >> 1 & 0x7f;
                    i1 = k1;
                    break;

                case 3: // '\003'
                    int l1 = i1;
                    b b6 = this;
                    l1 = l1 & 0xff | b6.be & 0x100;
                    b6.be = -(l1 & 1);
                    i1 = l1 >> 1;
                    break;

                case 6: // '\006'
                    i1--;
                    break;

                case 4: // '\004'
                case 5: // '\005'
                default:
                    i1++;
                    break;
                }
                bg = bc = i1;
                if(j != 2)
                    a8(bn, i1);
                else
                    bk = i1;
                break;

            case 3: // '\003'
                ar();
                break;
            }
        }

        return bp;
    }

    private final void a8(int i, int j)
    {
        i &= 0xffff;
        int k = i >> 8;
        if((a8[k] & 1) != 0)
            a9[i] = (byte)j;
        else
            ba.aq(bs, i, j);
    }

    private final int a7(int i)
    {
        i &= 0xffff;
        int j = i >> 8;
        if((a8[j] & 2) == 0)
            return a9[i];
        else
            return ba.ap(bs, i);
    }

    private final int a6(int i)
    {
        i &= 0xffff;
        return (a9[i + 1] & 0xff) << 8 | a9[i] & 0xff;
    }

    private final int a5()
    {
        return a9[bm++] & 0xff;
    }

    private final int a4()
    {
        byte byte0 = a9[bm++];
        byte byte1 = a9[bm++];
        return (byte1 & 0xff) << 8 | byte0 & 0xff;
    }

    private final void a3(int i)
    {
        a9[bl--] = (byte)i;
    }

    private final void a2(int i)
    {
        a9[bl--] = (byte)(i >> 8 & 0xff);
        a9[bl--] = (byte)(i & 0xff);
    }

    private final int a1()
    {
        byte byte0 = a9[++bl];
        byte byte1 = a9[++bl];
        return (byte1 & 0xff) << 8 | byte0 & 0xff;
    }

    private final int a0()
    {
        return a6(a5()) + bi;
    }

    private final int a_()
    {
        return a6(a5() + bj & 0xff);
    }

    private final void az(int i)
    {
        if(bf == 0)
        {
            int j = ((bk & 0xff) + i) - be;
            be = -((j ^ i) & 0x100) >> 31;
            bd = ~(bk ^ i) & (bk ^ j) & 0x80;
            bk = j;
        } else
        {
            int k = ((bk & 0xf) + (i & 0xf)) - be;
            int l = (bk & 0xf0) + (i & 0xf0);
            if(k > 9)
            {
                l += 16;
                k += 6;
            }
            bd = ~(bk ^ i) & (bk ^ l) & 0x80;
            if(l > 144)
                l += 96;
            bk = l & 0xf0 | k & 0xf;
            be = 255 - l >> 31;
        }
        bc = bk;
    }

    private final void ay(int i)
    {
        int j = ((0xff & bk) - (0xff & i)) + ~be;
        bd = (bk ^ i) & (bk ^ j) & 0x80;
        if(bf == 0)
        {
            bk = j;
        } else
        {
            int k = ((bk & 0xf) - (i & 0xf)) + ~be;
            int l = (bk & 0xf0) - (i & 0xf0);
            if((k & 0xf0) != 0)
                k -= 6;
            if((k & 0x80) != 0)
                l -= 16;
            if((l & 0xf00) != 0)
                l -= 96;
            bk = l & 0xf0 | k & 0xf;
        }
        be = (j & 0xff00) - 1 >> 31;
        bc = bk;
    }

    public final void ax(int i)
    {
        bd = i & 0x40;
        bg = i;
        bc = bk & i;
    }

    private final void aw(int i)
    {
        i = (0xff & i) - (0xff & bj);
        be = i - 1 >> 31;
        bg = bc = -i;
    }

    private final void av(int i)
    {
        i = (0xff & i) - (0xff & bi);
        be = i - 1 >> 31;
        bg = bc = -i;
    }

    private final void au(boolean flag)
    {
        if(flag)
        {
            int i = a9[bm++] + bm;
            int j = (bm & 0xff00) - (i & 0xff00);
            bq += 1 + ((-j | j) >>> 31);
            bm = i;
        } else
        {
            bm++;
        }
    }

    private final void at()
    {
        bh &= 0xffffff34;
        bh |= be & 1;
        bh |= bf;
        bh |= bg & 0x80;
        if((bc & 0xff) == 0)
            bh |= 2;
        if(bd != 0)
            bh |= 0x40;
    }

    private final void as()
    {
        be = -(bh & 1);
        bc = bh & 2 ^ 2;
        bf = bh & 8;
        bd = bh & 0x40;
        bg = bh & 0x80;
    }

    private final void ar()
    {
        bb();
    }

    public b(a a10, String s, int i)
    {
        a7 = false;
        ba = a10;
        a9 = ba.q(s, i);
        a8 = new byte[256];
        bs = a10.r();
        bc();
    }

    public b(a a10)
    {
        this(a10, "cpu0.ram", 0x10000);
    }

    public final int bs;
    public int br;
    public int bq;
    public int bp;
    public int bo;
    public int bn;
    public int bm;
    public int bl;
    public int bk;
    public int bj;
    public int bi;
    public int bh;
    public int bg;
    public int bf;
    public int be;
    public int bd;
    public int bc;
    public final a ba;
    public final byte a9[];
    public byte a8[];
    public boolean a7;
}
