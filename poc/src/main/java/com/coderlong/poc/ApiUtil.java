//package com.coderlong.poc;
//
//import org.springframework.web.context.request.NativeWebRequest;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Optional;
//
//public class ApiUtil {
//    static Optional<NativeWebRequest> getRequest() {
//        return Optional.empty();
//    }
//
//    public static void setExampleResponse(String cookie, String example) {
//        try {
//            NativeWebRequest req = getRequest().get();
//
//            HttpServletResponse res = req.getNativeResponse(HttpServletResponse.class);
//            res.encodeURL("");
//            res.setCharacterEncoding("UTF-8");
//            res.addHeader("Content-Type", "application/json");
//            res.addHeader("bfc_token", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweGQyYjliNDM5N2I0MzcwMTQwM2U0NDhkYzQ5NWRkNjIyMDA0MDIwYjUiLCJleHAiOjE1OTg0MDg4MTEsImlhdCI6MTU5NzgwNDAxMX0.m7EjkFUHXtaz985bZZBrOlYmSzWroy6TD9JOldfhVp0bIggfD8Ix2J45On1NWOd-Cf8uJHXmAYiG43AZ8YYGAA");
//            Cookie cookie1 = new Cookie("__cfduid", "d69c1de8ae82993516f95c1f335dd40561597850427");
//            res.addCookie(cookie1);
//
//            //Cookie cookie2 = new Cookie("token", "Bearer%20eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIweGQyYjliNDM5N2I0MzcwMTQwM2U0NDhkYzQ5NWRkNjIyMDA0MDIwYjUiLCJleHAiOjE1OTg0MDg4MTEsImlhdCI6MTU5NzgwNDAxMX0.m7EjkFUHXtaz985bZZBrOlYmSzWroy6TD9JOldfhVp0bIggfD8Ix2J45On1NWOd-Cf8uJHXmAYiG43AZ8YYGAA");
//            //res.addCookie(cookie2);
//
//            res.getWriter().print(example);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
