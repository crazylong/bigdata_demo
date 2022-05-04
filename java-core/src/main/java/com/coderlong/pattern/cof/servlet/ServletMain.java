package com.coderlong.pattern.cof.servlet;


import java.util.ArrayList;
import java.util.List;

public class ServletMain {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "你好，<script>欢迎来到mashibing.com,我们是996，:)!!!";
        Response response = new Response();
        response.str = "----->response--->";

        FilterChain fc = new FilterChain();
        fc.add(new CharFilter())
                .add(new SensitiveFilter());
        fc.doFilter(request, response);

        System.out.println(request.str);
        System.out.println(response.str);
    }
}


class Request {
    String str;
}

class Response {
    String str;
}

interface Filter {
    void doFilter(Request request, Response response, FilterChain fc);
}

class FilterChain {

    int count = 0;
    List<Filter> filters = new ArrayList<>();

    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }

    public void doFilter(Request request, Response response) {
        if (count < filters.size()) {
            Filter filter = filters.get(count++);
            filter.doFilter(request, response, this);
        }
    }

}


class CharFilter implements Filter {


    @Override
    public void doFilter(Request request, Response response, FilterChain fc) {
        request.str = request.str.replace('<', '[').replace('>', ']') + "--->doCharFilterRequest";
        fc.doFilter(request, response);
        response.str += "doCharFilterResponse--->";
    }
}

class SensitiveFilter implements Filter {


    @Override
    public void doFilter(Request request, Response response, FilterChain fc) {
        request.str = request.str.replace("996", "955") + "--->doSensitiveFilterRequest";
        ;
        fc.doFilter(request, response);
        response.str += "doSensitiveFilterResponse--->";
    }
}

