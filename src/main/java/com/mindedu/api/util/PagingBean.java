package com.mindedu.api.util;

public abstract class PagingBean implements PagingBeanInter {

    public static final int pageListCount = 10;

    @Override
    public final int getPageStartNum(int sPage) {
        int pageCnt = pageListCount;
        int sRow = pageCnt * (sPage - 1) + 1;
        int start = sRow - 1;
        return start;
    }

    @Override
    public int getPageStartNum(int sPage, int pageListCount) {
        int pageCnt = pageListCount;
        int sRow = pageCnt * (sPage - 1) + 1;
        int start = sRow - 1;
        return start;
    }
}
