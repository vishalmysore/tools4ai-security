package io.github.vishalmysore;

import io.github.vishalmysore.a2a.domain.AgentCard;
import io.github.vishalmysore.a2a.server.RealTimeAgentCardController;
import io.github.vishalmysore.a2a.server.SpringAwareAgentCardController;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class SecureRTAgentCardController extends SpringAwareAgentCardController implements AuthChecker {
    private final ConcurrentHashMap<String, AgentCard> userCards = new ConcurrentHashMap<>();
    public SecureRTAgentCardController(ApplicationContext context) {
        super(context);
    }

    @Override
    public boolean isMethodAllowed(Method method) {
        return isMethodInContext(method);
    }


    @Override
    public AgentCard getCachedAgentCard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : "a2ajava-guest";
        AgentCard card = userCards.get(userId);

        if (card == null) {
            init();
            card = userCards.get(userId);
        }

        return card != null ? card : super.getCachedAgentCard();
    }

    @Override
    public void storeCard(AgentCard card) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : "a2ajava-guest";
        userCards.put(userId, card);
        super.storeCard(card);
    }


}
