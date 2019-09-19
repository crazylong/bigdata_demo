package com.coderlong.websocket.stomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {
    private Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                .setAllowedOrigins("*")
                //.setHandshakeHandler(myStompSessionHandler)
//                .setHandshakeHandler(new MyHandsHandler())
//                .addInterceptors(new MyHandlerShareInterceptor())
                .withSockJS();
    }

    @Bean
    @Override
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new CustomSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        super.configureWebSocketTransport(registry);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return super.configureMessageConverters(messageConverters);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        super.configureClientInboundChannel(registration);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        super.configureClientOutboundChannel(registration);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }
}
