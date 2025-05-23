package io.github.vishalmysore;

import io.github.vishalmysore.a2a.server.DyanamicTaskContoller;
import io.github.vishalmysore.common.server.SpringAwareJSONRpcController;
import io.github.vishalmysore.mcp.server.MCPToolsController;
import org.springframework.context.ApplicationContext;

public class SecureSpringJSONController extends SpringAwareJSONRpcController {

    private final SecureDyanamicTaskContoller secureDynamicTaskController;
    private final SecureMCPToolsController secureMCPToolsController;
    public SecureSpringJSONController(ApplicationContext applicationContext) {
        super(applicationContext);
        secureDynamicTaskController = new SecureDyanamicTaskContoller();
        secureMCPToolsController = new SecureMCPToolsController();
    }

    @Override
    public DyanamicTaskContoller getTaskController() {
        return secureDynamicTaskController;
    }

    @Override
    public MCPToolsController getMCPToolsController() {
        return secureMCPToolsController;
    }
}
