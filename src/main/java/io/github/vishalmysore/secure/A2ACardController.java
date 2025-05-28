package io.github.vishalmysore.secure;


import io.github.vishalmysore.SecureRTAgentCardController;
import io.github.vishalmysore.a2a.domain.AgentCard;
import io.github.vishalmysore.a2a.server.RealTimeAgentCardController;
import io.github.vishalmysore.a2a.server.SpringAwareAgentCardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("secureA2ACardController")
@RequestMapping(RealTimeAgentCardController.WELL_KNOWN_PATH)
public class A2ACardController extends SecureRTAgentCardController {


    @Autowired
    public A2ACardController(ApplicationContext context) {
        super(context);
    }

    @GetMapping(value = RealTimeAgentCardController.AGENT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentCard> getAgentCardForMyApp() {
        AgentCard card = getCachedAgentCard();
        card.getCapabilities().setStreaming(false);
        return ResponseEntity.ok(card);

    }

}
