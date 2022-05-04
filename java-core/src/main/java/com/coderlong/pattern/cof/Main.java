package com.coderlong.pattern.cof;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Msg msg = new Msg();
        msg.setMsg( "你好，<script>欢迎来到mashibing.com,我们是996，:)");
        FilterChain fc = new FilterChain();
        fc.add(new CharFilter());
        fc.add(new SensitiveFilter());

        FilterChain fc2 = new FilterChain();
        fc2.add(new FaceFilter());
        fc2.add(new UrlFilter());

        fc.add(fc2);

        fc.doFilter(msg);
        System.out.println(msg.getMsg());
    }
}


@Data
class Msg {
    private String name;
    private String msg;
}

interface Filter {
    boolean doFilter(Msg msg);
}

class FilterChain implements Filter {

    List<Filter> list = new ArrayList<>();
    public FilterChain add(Filter filter) {
        list.add(filter);
        return this;
    }

    @Override
    public boolean doFilter(Msg msg) {
        for(Filter filter : list) {
            if(!filter.doFilter(msg)) {
                return false;
            }
        }

        return true;
    }
}


class CharFilter implements Filter {

    @Override
    public boolean doFilter(Msg msg) {
        String m = msg.getMsg().replace("<", "[");
        m = m.replace(">", "]");
        msg.setMsg(m);
        return true;
    }
}

class SensitiveFilter implements Filter {

    @Override
    public boolean doFilter(Msg msg) {
        msg.setMsg(msg.getMsg().replace("996", "955"));
        return true;
    }
}

class FaceFilter implements Filter {

    @Override
    public boolean doFilter(Msg msg) {
        msg.setMsg(msg.getMsg().replace(":)", "v^v"));
        return true;
    }
}

class UrlFilter implements Filter {

    @Override
    public boolean doFilter(Msg msg) {
        msg.setMsg(msg.getMsg().replace("mashibing.com", "https://mashibing.com"));
        return true;
    }
}