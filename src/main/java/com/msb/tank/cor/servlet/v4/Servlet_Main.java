package com.msb.tank.cor.servlet.v4;

import java.util.ArrayList;

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
    void doFilter(Request request, Response response, FilterChain filterChain);
}

class HTMLFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        request.str = request.str.replaceAll("<", "[").replaceAll(">", "]");
        // to understand why you need the filterChain as the third parameter, is to control whether you want to continue after this filter
        filterChain.doFilter(request, response);
        response.str += "------HTMLFilter()";
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
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        request.str = request.str.replaceAll("996", "955");
        filterChain.doFilter(request, response);
        response.str += "------SensitiveFilter()";
    }
}

class FilterChain {
    ArrayList<Filter> filters = new ArrayList<>();
    int index = 0;

    public FilterChain add(Filter filter) {
        filters.add(filter);
        return this;
    }

    public void doFilter(Request request, Response response) {
        if (index == filters.size()) return;
        Filter f = filters.get(index);
        index++;
         f.doFilter(request, response, this);
    }
}