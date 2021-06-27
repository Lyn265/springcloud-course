package com.lyn.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {

    private final static Logger LOG = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (!path.contains("/admin/")) {
            return chain.filter(exchange);
        }
        if (path.contains("/system/admin/user/login") ||
                path.contains("/system/admin/user/logout") ||
                path.contains("/system/admin/kaptcha")) {
            LOG.info("不需要控台登录认证");
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj == null) {
            LOG.info("token无效,请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } else {
            LOG.info("登录拦截开始,token:{}", token);
            //增加权限校验，gateway里没有LoginUserDto，所以全部用json操作
            boolean exist = false;
            JSONObject jsonLoginObj = JSON.parseObject(String.valueOf(obj));
            JSONArray requestsArr = jsonLoginObj.getJSONArray("requests");
            for (int i = 0; i < requestsArr.size(); i++) {
                String  str = requestsArr.get(i).toString();
                if(path.contains(str)){
                    exist = true;
                    break;
                }
            }
            if(exist){
                LOG.info("校验通过");
            }else {
                LOG.warn("校验未授权");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
