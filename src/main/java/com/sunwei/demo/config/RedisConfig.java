//package com.sunwei.demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.MapPropertySource;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@ConditionalOnClass(RedisTemplate.class)
//public class RedisConfig {
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.timeout}")
//    private String timeout;
//    @Value("${spring.redis.password}")
//    private String password;
//
//    /****
//     * 重写Redis序列化方式，使用Json方式:
//     * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
//     * Spring Data JPA为我们提供了下面的Serializer：
//     * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
//     * 在此我们将自己配置RedisTemplate并定义Serializer。
//     * @param redisConnectionFactory
//     * @return
//     * @throws UnknownHostException
//     */
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
//        RedisTemplate template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        StringRedisSerializer serializer = new StringRedisSerializer();
//        //GenericFastJsonRedisSerializer serializer = new GenericFastJsonRedisSerializer();
//        // 全局开启AutoType，不建议使用
//        //ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        // 建议使用这种方式，小范围指定白名单
//        //ParserConfig.getGlobalInstance().addAccept("com.tellhow.sgpssc_uds");
//        template.setKeySerializer(new StringRedisSerializer());
//        // 设置值（value）的序列化采用FastJsonRedisSerializer。
//        template.setDefaultSerializer(serializer);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//    @Bean
//    public RedisClusterConfiguration getClusterConfiguration() {
//        if (host.split(",").length > 1) {
//            //如果是host是集群模式的才进行以下操作
//            Map<String, Object> source = new HashMap<String, Object>();
//            source.put("spring.redis.cluster.nodes", host);
//            source.put("spring.redis.cluster.timeout", timeout.replace("ms",""));
//            ////通过配置的信息计算  最大的跳转数
//            int redirects = host.split(",").length-1;
//            source.put("spring.redis.cluster.max-redirects", redirects);
//            if (password.length()>0){
//                source.put("spring.redis.cluster.password", password);
//            }
//            return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
//        } else {
//            return null;
//        }
//    }
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        if (host.split(",").length == 1) {
//            JedisConnectionFactory factory = new JedisConnectionFactory();
//            factory.setHostName(host.split(":")[0]);
//            factory.setPort(Integer.valueOf(host.split(":")[1]));
//            if (password.length()>0){
//                factory.setPassword(password);
//            }
//            factory.setTimeout(Integer.parseInt(timeout.replace("ms","")));
//            return factory;
//        } else {
//            JedisConnectionFactory jcf = new JedisConnectionFactory(getClusterConfiguration());
//            if (password.length()>0){
//                //集群的密码认证
//                jcf.setPassword(password);
//            }
//            return jcf;
//        }
//    }
//}
