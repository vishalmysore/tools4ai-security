package io.github.vishalmysore;

import io.github.vishalmysore.mcp.domain.ListToolsResult;
import io.github.vishalmysore.mcp.domain.Tool;
import io.github.vishalmysore.mcp.server.MCPToolsController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SecureMCPToolsController extends MCPToolsController implements AuthChecker{

    private final ConcurrentHashMap<String, ListToolsResult> userToolsResults = new ConcurrentHashMap<>();
    @Override
    public boolean isMethodAllowed(Method method) {
        return isMethodInContext(method);
    }

    @Override
    public void storeListToolsResult(ListToolsResult toolsResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : "a2ajava-guest";
        userToolsResults.put(userId, toolsResult);
    }

    @Override
    public ResponseEntity<Map<String, List<Tool>>> listTools() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication != null ? authentication.getName() : "a2ajava-guest";
        ListToolsResult toolsResult = userToolsResults.get(userId);
        if (toolsResult == null) {
            super.init();
            toolsResult = userToolsResults.get(userId);
        }

        Map<String, List<Tool>> response = new HashMap<>();
        response.put("tools", toolsResult.getTools());
        return ResponseEntity.ok(response);
    }
}
