package suho.pofol.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketMessageBrokerConfigurer : WebSocket 메시징 구성을 위한 메서드를 제공
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    //stomp 엔드포인트 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/chat").setAllowedOrigins("*").addInterceptors(new HandshakeInterceptor() {
//            @Override
//            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//
//                return request.getHeaders().getOrigin() != null;
//            }
//
//            @Override
//            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                       WebSocketHandler wsHandler, Exception exception) {
//                System.out.println(request.getHeaders());
//
//            }
//        }).withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableSimpleBroker("/topic", "/queue");
//    }

}
