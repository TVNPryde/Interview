package com.fyllo.interview.startup;

import com.fyllo.interview.common.PersistentQueue;
import com.fyllo.interview.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AppStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    WebClientService webClientService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        PersistentQueue queue = PersistentQueue.init();

        Flux.range(1, queue.getQueue())
                .flatMap(x -> webClientService.send())
                .blockLast();
    }
}
