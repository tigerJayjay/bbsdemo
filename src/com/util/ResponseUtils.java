package com.util;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

    public static void render(HttpServletResponse response, String jsonString) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.getWriter().write(jsonString);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
