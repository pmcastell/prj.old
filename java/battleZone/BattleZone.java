// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JAX

import java.awt.*;

public final class BattleZone extends a
{

    public final void e()
    {
        super.y(cf);
    }

    public final void f()
    {
        super.e = (int)(super.n * (double)super.g);
        super.f = (int)(super.n * (double)super.h);
        ca = super.f / 2 << 15;
        b9 = super.e << 15;
        b8 = super.f << 15;
        super.d = createImage(super.e, super.f);
        super.c = super.d.getGraphics();
    }

    private final void bs()
    {
        boolean flag = false;
        boolean flag1 = false;
        int i = 8192;
        int j = 0;
        int k = 0;
        int l = b9;
        int i1 = b8;
        int j1 = 0;
        int k1 = b9;
        int l1 = b8;
        if(super.i())
            return;
        super.c.clearRect(0, 0, super.e, super.f);
        while(!flag) 
        {
            int k5 = i;
            BattleZone battlezone1 = this;
            int k2 = ((battlezone1.cd.a9[k5 + 1] & 0xff) << 8 | battlezone1.cd.a9[k5] & 0xff) & 0xffff;
            i += 2;
            int i3 = k2 >> 13;
            switch(i3)
            {
            case 0: // '\0'
                int j5 = i;
                BattleZone battlezone = this;
                int l2 = ((battlezone.cd.a9[j5 + 1] & 0xff) << 8 | battlezone.cd.a9[j5] & 0xff) & 0xffff;
                i += 2;
                int j3 = (l2 << 19) >> 19;
                int l3 = (k2 << 19) >> 19;
                j3 *= k;
                l3 *= k;
                int i2 = l2 >> 12 & -2;
                k1 += j3;
                l1 -= l3;
                if(i2 == 2)
                    i2 = j1;
                if(i2 != 0)
                {
                    if(flag1 && l1 < ca && i1 < ca)
                        super.c.setColor(Color.red);
                    else
                        super.c.setColor(ch[i2]);
                    super.c.drawLine(l >> 16, i1 >> 16, k1 >> 16, l1 >> 16);
                }
                l = k1;
                i1 = l1;
                break;

            case 1: // '\001'
                flag = true;
                break;

            case 2: // '\002'
                int k3 = (k2 << 27) >> 26;
                int i4 = (k2 << 19) >> 26 & -2;
                k3 *= k;
                i4 *= k;
                int j2 = k2 >> 4 & 0xe;
                k1 += k3;
                l1 -= i4;
                if(j2 == 2)
                    j2 = j1;
                if(j2 != 0)
                {
                    if(l1 < ca)
                        super.c.setColor(Color.red);
                    else
                        super.c.setColor(ch[j2]);
                    super.c.drawLine(l >> 16, i1 >> 16, k1 >> 16, l1 >> 16);
                }
                l = k1;
                i1 = l1;
                break;

            case 3: // '\003'
                if((k2 & 0x1000) == 0)
                {
                    j1 = k2 >> 4 & 0xf;
                    flag1 = j1 == 0;
                } else
                {
                    int j4 = (k2 >> 8 & 7) + 9;
                    int k4 = ~k2 & 0xff;
                    k = (int)(super.n * (double)((k4 << 16) >> j4));
                }
                break;

            case 4: // '\004'
                l = k1 = b9;
                i1 = l1 = b8;
                break;

            case 5: // '\005'
                int l4 = k2 & 0x1fff;
                if(l4 != 0)
                {
                    cg[j++] = i;
                    i = 8192 + l4 + l4;
                } else
                {
                    flag = true;
                }
                break;

            case 6: // '\006'
                i = cg[--j];
                break;

            default:
                int i5 = k2 & 0x1fff;
                if(i5 != 0)
                    i = 8192 + i5 + i5;
                else
                    flag = true;
                break;
            }
        }
        super.a();
    }

    private final int br(int i, int j)
    {
        i = i & 0xff | (j << 24) >> 16;
        return i;
    }

    private final int bq(int i, int j)
    {
        i = i & 0xffffff00 | j & 0xff;
        return i;
    }

    private final void bp(int i)
    {
        cd.a9[6160] = (byte)(i & 0xff);
        cd.a9[6168] = (byte)(i >> 8 & 0xff);
    }

    private final void bo()
    {
        int i;
        if(b5 >= b4)
        {
            i = b4 >> 2;
            i = b5 + i + (i >> 1);
        } else
        {
            i = b5 >> 2;
            i = b4 + i + (i >> 1);
        }
        cd.a9[6160] = (byte)(i & 0xff);
        cd.a9[6168] = (byte)(i >> 8 & 0xff);
    }

    private final void bn(int i, int j)
    {
        if(bw != b1)
        {
            bx = 1.0F / ((float)b1 * 64F);
            bw = b1;
        }
        int k = i << 16 | j & 0xffff;
        float f1 = (float)k * bx;
        k = (int)f1;
        cd.a9[6160] = (byte)(k & 0xff);
        cd.a9[6168] = (byte)(k >> 8 & 0xff);
    }

    private final void bm()
    {
        int i = b7 * b2;
        int j = b3 * b6;
        b0 = i >> 16;
        b_ = i & 0xffff;
        b0 += j >> 16;
        b_ += j & 0xffff;
        b0 += (b_ & 0x10000) >> 16;
        b_ = (b_ << 16) >> 16;
    }

    private final void bl()
    {
        int i = b7 * b3;
        int j = -b6 * b2;
        b1 = i >> 16;
        int k = i & 0xffff;
        b1 += j >> 16;
        k += j & 0xffff;
        b1 += (k & 0x10000) >> 16;
    }

    private final void bk(int i, int j)
    {
        switch(i)
        {
        case 19: // '\023'
        case 24: // '\030'
        case 26: // '\032'
        case 27: // '\033'
        case 28: // '\034'
        default:
            break;

        case 0: // '\0'
            b7 = bq(b7, j);
            break;

        case 1: // '\001'
            b7 = br(b7, j);
            break;

        case 2: // '\002'
            b6 = bq(b6, j);
            break;

        case 3: // '\003'
            b6 = br(b6, j);
            break;

        case 4: // '\004'
            b5 = bq(b5, j);
            break;

        case 5: // '\005'
            b5 = br(b5, j);
            break;

        case 6: // '\006'
            b4 = bq(b4, j);
            break;

        case 7: // '\007'
            b4 = br(b4, j);
            break;

        case 8: // '\b'
            b3 = bq(b3, j);
            break;

        case 9: // '\t'
            b3 = br(b3, j);
            break;

        case 10: // '\n'
            b2 = bq(b2, j);
            break;

        case 11: // '\013'
            b2 = br(b2, j) - b4;
            b3 -= b5;
            b2 = (b2 << 16) >> 16;
            b3 = (b3 << 16) >> 16;
            bl();
            cd.a9[6160] = (byte)(b1 & 0xff);
            cd.a9[6168] = (byte)(b1 >> 8 & 0xff);
            break;

        case 12: // '\f'
            BattleZone _tmp = this;
            bq(0, j);
            break;

        case 13: // '\r'
            by = bq(by, j);
            break;

        case 14: // '\016'
            by = br(by, j);
            break;

        case 15: // '\017'
            bz = bq(bz, j);
            break;

        case 16: // '\020'
            bz = br(bz, j);
            break;

        case 17: // '\021'
            b2 = br(b2, j);
            bl();
            b1 += b5;
            b1 = (b1 << 16) >> 16;
            bm();
            b0 += b4;
            b0 = (b0 << 16) >> 16;
            b_ &= 0xffffff00;
            bn(b0, b_);
            break;

        case 18: // '\022'
            bm();
            cd.a9[6160] = (byte)(b0 & 0xff);
            cd.a9[6168] = (byte)(b0 >> 8 & 0xff);
            break;

        case 20: // '\024'
            bn(bz, by);
            break;

        case 21: // '\025'
            b1 = bq(b1, j);
            break;

        case 22: // '\026'
            b1 = br(b1, j);
            break;

        case 23: // '\027'
            bp(b1);
            break;

        case 25: // '\031'
            bp(b0);
            break;

        case 29: // '\035'
            b5 = b5 - b7;
            b4 = br(b4, j) - b6;
            b5 = (b5 << 16) >> 16;
            b4 = (b4 << 16) >> 16;
            if(b5 < 0)
                b5 = -b5;
            if(b4 < 0)
                b4 = -b4;
            // fall through

        case 30: // '\036'
            bo();
            break;
        }
    }

    public final int ao(int i, int j, int k)
    {
        if(k == 0)
        {
            int l = (cd.bh() - 3 & 0xff) >> 2;
            cd.bg(3);
            if(l <= 7 && bu)
                switch(l)
                {
                case 7: // '\007'
                    super.v(11);
                    super.v(12);
                    // fall through

                default:
                    super.w(l);
                    break;
                }
        } else
        {
            switch(k)
            {
            default:
                break;

            case 1: // '\001'
                super.u(13);
                cd.bg(-1);
                break;

            case 2: // '\002'
                int i1 = cd.a9[57] & 0xff;
                if(i1 != 0)
                {
                    for(; (i1 & 1) == 0; i1 >>= 1);
                    cd.a9[57] = (byte)(i1 >> 1);
                    cd.bf(-1);
                } else
                {
                    cd.be();
                }
                break;

            case 3: // '\003'
                byte byte0 = cd.a9[40];
                cd.bg(byte0);
                if(byte0 == 0)
                    cd.be();
                break;

            case 4: // '\004'
                cd.bg(3);
                byte byte1 = cd.a9[828];
                if(byte1 == 0)
                    super.p(0);
                break;

            case 5: // '\005'
                int j1 = cd.a9[184] & 0xff | (cd.a9[185] & 0xff) << 8;
                int l1 = 1000;
                int k1 = j1;
                int i2 = 0;
                for(; k1 != 0; k1 >>= 8)
                {
                    i2 += l1 * ((k1 & 0xf) + 10 * (k1 >> 4 & 0xf));
                    l1 *= 100;
                }

                j1 = i2;
                cd.bg(0);
                super.p(0);
                super.aa(j1, a.az);
                break;
            }
        }
        return 0;
    }

    private final void bj(int i, int j)
    {
        if(i == 6208 && bv != j)
        {
            bv = j;
            boolean flag = (j & 0x20) != 0;
            if(flag)
            {
                if((j & 1) != 0)
                {
                    super.v(13);
                    int k = 8 + (j >> 1 & 1);
                    super.w(k);
                }
                if((j & 4) != 0)
                    super.w(10);
                if((j & 0x80) != 0)
                    if((j & 0x10) == 0)
                    {
                        super.v(12);
                        super.u(11);
                    } else
                    {
                        super.v(11);
                        super.u(12);
                    }
            } else
            {
                super.v(12);
                super.v(11);
                super.v(13);
            }
            bu = flag;
        }
    }

    public final void aq(int i, int j, int k)
    {
        int l = j >> 8 & 0xff;
        switch(l)
        {
        case 19: // '\023'
        case 20: // '\024'
        case 21: // '\025'
        case 22: // '\026'
        case 23: // '\027'
        default:
            break;

        case 18: // '\022'
            bs();
            break;

        case 24: // '\030'
            int i1 = (j & 0xf0) >> 4;
            switch(i1)
            {
            case 4: // '\004'
                bj(j, k);
                break;

            case 6: // '\006'
            case 7: // '\007'
                bk(j - 6240, k);
                break;
            }
            break;
        }
    }

    public final int ap(int i, int j)
    {
        if((j & 0xffff) == 6186)
        {
            BattleZone battlezone = this;
            ci *= 0x41c64e6d;
            ci += 12345;
            return ci >>> 24;
        } else
        {
            return cd.a9[j];
        }
    }

    public final void ai(int i, int j)
    {
    }

    public final void ak(int i)
    {
        cc[1] |= 2;
    }

    public final void aj(int i)
    {
        cc[1] &= 0xfd;
    }

    public final void am(int i)
    {
        switch(i)
        {
        case 17: // '\021'
            cc[0] |= 1;
            break;

        case 32: // ' '
            cc[0] |= 2;
            break;

        case 49: // '1'
            cc[1] |= 1;
            break;

        case 50: // '2'
            cc[2] |= 1;
            break;

        case 38: // '&'
            cc[3] |= 1;
            break;

        case 40: // '('
            cc[4] |= 1;
            break;

        case 39: // '\''
            cc[5] |= 1;
            break;

        case 37: // '%'
            cc[6] |= 1;
            break;
        }
    }

    public final void al(int i)
    {
        switch(i)
        {
        case 17: // '\021'
            cc[0] &= 0xfe;
            break;

        case 32: // ' '
            cc[0] &= 0xfd;
            break;

        case 49: // '1'
            cc[1] &= 0xfe;
            break;

        case 50: // '2'
            cc[2] &= 0xfe;
            break;

        case 38: // '&'
            cc[3] &= 0xfe;
            break;

        case 40: // '('
            cc[4] &= 0xfe;
            break;

        case 39: // '\''
            cc[5] &= 0xfe;
            break;

        case 37: // '%'
            cc[6] &= 0xfe;
            break;
        }
    }

    public final void ah()
    {
    }

    public static void main(String args[])
    {
        BattleZone battlezone = new BattleZone();
        battlezone.a.z(args);
    }

    private final void bi()
    {
        int i = 0;
        int j = 0;
        if(cc[0] != 0)
            i |= 0x10;
        if(cc[1] != 0)
            i |= 0x20;
        if(cc[2] != 0)
            i |= 0x40;
        j = -cc[3] >> 31 & 1;
        j |= -cc[4] >> 31 & 2;
        j |= -cc[5] >> 31 & 4;
        j |= -cc[6] >> 31 & 8;
        j = ce[j];
        cd.a9[6184] = (byte)(i | j);
    }

    public final void an()
    {
        bi();
        bt = 6243 + cd.a9(bt);
        cd.bd();
        bi();
        bt = 6243 + cd.a9(bt);
        cd.bd();
        bi();
        bt = 6243 + cd.a9(bt);
        cd.bd();
        bi();
        bt = 6243 + cd.a9(bt);
        cd.bd();
        bi();
        bt = 6243 + cd.a9(bt);
        cd.bd();
        bi();
        bt = 6243 + cd.a9(bt);
        cd.bd();
    }

    public BattleZone()
    {
        super(0);
        BattleZone _tmp = this;
        char _tmp1 = '\u1820';
        BattleZone _tmp2 = this;
        char _tmp3 = '\u2000';
        BattleZone _tmp4 = this;
        char _tmp5 = '\u1200';
        BattleZone _tmp6 = this;
        char _tmp7 = '\u1860';
        BattleZone _tmp8 = this;
        char _tmp9 = '\u1810';
        BattleZone _tmp10 = this;
        char _tmp11 = '\u1818';
        BattleZone _tmp12 = this;
        byte _tmp13 = 24;
        BattleZone _tmp14 = this;
        byte _tmp15 = 18;
        BattleZone _tmp16 = this;
        char _tmp17 = '\u182A';
        BattleZone _tmp18 = this;
        byte _tmp19 = 6;
        BattleZone _tmp20 = this;
        byte _tmp21 = 7;
        ca = 2000;
        bx = 0.015625F;
        bw = 1;
        bv = 0;
        bu = true;
        bt = 6250;
        String s = "Battle Zone";
        a.a5 = s;
        super.g = 252;
        super.h = 225;
        float f1 = 40F;
        a.a0 = 1000F / f1;
        for(int i = 0; i < 5; i++)
            cd.a8[i] = 1;

        for(int j = 32; j < 48; j++)
            cd.a8[j] = 1;

        cd.a8[24] = 2;
        System.arraycopy(cd.a9, 30720, cd.a9, 63488, 2048);
        cd.bc();
        super.l(cd.a9, 2560, 255, 21);
        super.k("English", 21);
        super.k("French", 149);
        super.k("German", 85);
        super.k("Spanish", 213);
    }

    public static int ci = (int)System.currentTimeMillis();
    public static final Color ch[];
    public static int cg[] = new int[16];
    public static final String cf[] = {
        "atbz_sd_clocktick.au", "atbz_sd_hitObstacle.au", "atbz_sd_motionblocked_lp.au", "atbz_sd_clocktick.au", "atbz_sd_enemyinrange.au", "atbz_sd_ufohit_lp.au", "atbz_sd_ufo.au", "atbz_sd_highscore.au", "atbz_sd_enemyishit.au", "atbz_sd_playerishit.au", 
        "atbz_sd_fire.au", "atbz_sd_engineidle_lp.au", "atbz_sd_enginemove_lp.au", "atbz_sd_missile.au"
    };
    public static final int ce[] = {
        0, 10, 5, 0, 9, 8, 4, 0, 6, 2, 
        1, 0, 0, 0, 0, 0
    };
    public final b cd = new b(this);
    public final byte cc[] = new byte[7];
    public int ca;
    public int b9;
    public int b8;
    public int b7;
    public int b6;
    public int b5;
    public int b4;
    public int b3;
    public int b2;
    public int b1;
    public int b0;
    public int b_;
    public int bz;
    public int by;
    public float bx;
    public int bw;
    public int bv;
    public boolean bu;
    public int bt;

    static 
    {
        ch = (new Color[] {
            Color.red, new Color(4352), new Color(0x22202), new Color(0x43304), new Color(0x64406), new Color(0x85508), new Color(0xa660a), new Color(0xc770c), new Color(0xe880e), new Color(0x109910), 
            new Color(0x12aa12), new Color(0x14bb14), new Color(0x16cc16), new Color(0x18dd18), new Color(0x1aee1a), new Color(0x1cff1c)
        });
    }
}
