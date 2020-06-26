package org.jitsi.moderated.endpoints;

import org.jitsi.moderated.model.ClientConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rest/config")
public class ClientConfigEndpoint {
    
    @GetMapping()
    public ClientConfig get() {
        return ClientConfig.getInstance();
    }
}