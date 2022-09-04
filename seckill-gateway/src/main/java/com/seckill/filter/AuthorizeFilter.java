package com.seckill.filter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*****
 * @Author: 黑马训练营
 * @Description: com.changgou.filter
 ****/
@Component
public class AuthorizeFilter implements GlobalFilter,Ordered {

    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String ADMINAUTHORIZE_TOKEN = "Admin-Token-Itheima";

    /***
     * 过滤拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取request和response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取用户请求的地址
        String path = request.getURI().getPath();
        // /api/user/login放行
        if(path.equals("/api/brand") || path.equals("/api/user/login") || path.equals("/api/admin/login") || path.equals("/api/search") || path.equals("/api/activity/times")){
            //放行
            return chain.filter(exchange);
        }
        HttpMethod method = request.getMethod();
        System.out.println(method.name());
        // /sku/xxx   GET方式允许通过
        if(path.startsWith("/api/sku/") && request.getMethod().name().equalsIgnoreCase("GET")){
            //放行
            return chain.filter(exchange);
        }

        //获取用户请求头中的令牌
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN); //获取请求头中第1个Authorization参数

        //如果请求头中没有令牌，则有可能用的是参数传入的
        if(StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);//获取请求参数中第1个Authorization
        }

        //如果请求头和参数中都没有令牌，则直接拒绝用户访问各大微服务
        if(StringUtils.isEmpty(token)){
            //从Cookie中获取令牌数据
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            HttpCookie adminCookie = request.getCookies().getFirst(ADMINAUTHORIZE_TOKEN);
            if(cookie==null && adminCookie==null){
                //状态吗  401
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                //结束当前请求
                return response.setComplete();
            }
            //获取令牌
            if(cookie!=null){
                token = cookie.getValue();
            }else{
                token = adminCookie.getValue();
            }
            //将令牌封装到请求头中
            request.mutate().header(AUTHORIZE_TOKEN,"bearer "+token);
        }
        return chain.filter(exchange);
    }

    /***
     * 排序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
