package com.mindedu.api.filter;

import javax.servlet.http.HttpServletRequest;

public class MindEduSecurity {

    static final String[] excludes = {
            "/index.jsp",
            "/views/documents.txt",
            "/swagger-ui.html"
    };

    public static boolean isSecure(HttpServletRequest req) {
        String ctx = req.getContextPath();
        String path = req.getServletPath();
        if (path.startsWith(ctx)) {
            path = path.replaceFirst(ctx, "");
        }
        for (String ex : excludes) {
            if (ex.equals(path)) {
                return false;
            }
        }
        return true;
    }
}
