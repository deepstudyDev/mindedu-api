package com.mindedu.api.util;

public interface PagingBeanInter {

    int getPageStartNum(int sPage);

    int getPageStartNum(int sPage, int pageListCount);
}
