package io.github.vishalmysore;

import com.t4a.JsonUtils;
import com.t4a.detect.ActionCallback;
import com.t4a.processor.AIProcessor;
import io.github.vishalmysore.a2a.domain.*;
import io.github.vishalmysore.a2a.server.DyanamicTaskContoller;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Log
public class SecureDyanamicTaskContoller extends DyanamicTaskContoller {
    //SecureGeminiV2ActionProcessor secureGeminiV2ActionProcessor;
   // public SecureDyanamicTaskContoller() {
     //   super();
      //  secureGeminiV2ActionProcessor = new SecureGeminiV2ActionProcessor();
   // }
    protected void processTaskLogicForSyncndAsync(TaskSendParams taskSendParams, ActionCallback actionCallback, boolean isAsync, Task task, String taskId) {
        if (isAsync) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            getNonBlockingService().execute(() -> {
                SecurityContext contextHolder = SecurityContextHolder.createEmptyContext();
                contextHolder.setAuthentication(authentication);
                SecurityContextHolder.setContext(contextHolder);
                try {
                    processTaskLogic(taskSendParams, task, taskId, actionCallback);
                } finally {
                    SecurityContextHolder.clearContext();
                }
            });
        } else {
            processTaskLogic(taskSendParams, task, taskId, actionCallback);
        }
    }


   // @Override
   // public AIProcessor getBaseProcessor() {
     //   return secureGeminiV2ActionProcessor;
   // }
}
