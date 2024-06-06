package com.msb.tank.cor.servlet.v1;

import java.util.ArrayList;

public class Servlet_Main {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "hello, ";
        Response response = new Response();
        response.str = "";

        FilterChain chain = new FilterChain();
        chain.add(new HTMLFilter()).add(new SensitiveFilter());
        chain.doFilter(request, response);
    }
}

interface Filter {
    boolean doFilter(Request request, Response response);
}

class HTMLFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response) {
        request.str = request.str.replaceAll("<", "[").replaceAll(">", "]");
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
    public boolean doFilter(Request request, Response response) {
        request.str = request.str.replaceAll("996", "955");
        return true;
    }
}

class FilterChain implements Filter {
    ArrayList<Filter> filters = new ArrayList<>();

    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public boolean doFilter(Request request, Response response) {
        for (Filter filter : filters) {
            filter.doFilter(request, response);
        }
        return true;
    }
}