package com.msb.tank.cor.servlet.v2;

import java.util.ArrayList;
import java.util.Objects;

public class Servlet_Main {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "hello, <script> welcome to msb, everyone is 996";
        Response response = new Response();
        response.str = "response";

        FilterChain chain = new FilterChain();
        chain.add(new HTMLFilter()).add(new SensitiveFilter());
        chain.doFilter(request, response);
        System.out.println(request.str);
        System.out.println(response.str);
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