package com.msb.tank.cor.servlet.v3;

import java.util.ArrayList;

public class Servlet_Main {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "hello, <script> welcome to msb, everyone is 996";
        Response response = new Response();
        response.str = "response";

        FilterChain chain = new FilterChain();
        chain.add(new HTMLFilter()).add(new SensitiveFilter());
        chain.doFilter(request, response, chain);
        System.out.println(request.str);
        System.out.println(response.str);
    }
}

interface Filter {
    boolean doFilter(Request request, Response response, FilterChain filterChain);
}

class HTMLFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        request.str = request.str.replaceAll("<", "[").replaceAll(">", "]");
        filterChain.doFilter(request, response, filterChain);
        response.str += "------HTMLFilter()";
        return true;
    }

}

class Request {
    String str;
}

class Response {
    String str;
}

class SensitiveFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        request.str = request.str.replaceAll("996", "955");
        filterChain.doFilter(request, response, filterChain);
        response.str += "------SensitiveFilter()";
        return true;
    }
}

class FilterChain implements Filter {
    ArrayList<Filter> filters = new ArrayList<>();
    int index = 0;

    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean doFilter(Request request, Response response, FilterChain filterChain) {
        if (index == filters.size()) return false;
        Filter f = filters.get(index);
        index++;
        return f.doFilter(request, response, filterChain);
    }
}