package kz.railways.filerepo.filerepo.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.railways.filerepo.filerepo.model.AccessToken;
import kz.railways.filerepo.filerepo.model.RwRoleAuthority;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


@Aspect
@Component
public class ExampleAspect {

    @Around("@annotation(Protected)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");

        if (authorization!=null) {
            String encryptedAccessToken = authorization.split(" ")[1];
            //get json
            String[] split_string = encryptedAccessToken.split("\\.");
            String base64EncodedBody = split_string[1];
            Base64 base64Url = new Base64(true);
            String json = new String(base64Url.decode(base64EncodedBody));
            //json to object
            ObjectMapper mapper = new ObjectMapper();
            AccessToken accessToken = mapper.readValue(json, AccessToken.class);

            Protected aProtected =  method.getAnnotation(Protected.class);
            String[] accesses = aProtected.accesses();

            if (this.hasRole(accesses, accessToken)) {
                System.out.println("Method with name " + method.getName() + " success");
            } else {
                System.out.println("Method with name " + method.getName() + " ERROR*****");
                throw new Exception();
            }
            return joinPoint.proceed();

        } else {
            throw new Exception();
        }
    }

    private boolean hasRole(String[] accesses, AccessToken accessToken) {

        boolean hasRole = false;

        OUTERMOST: for (String access : accesses) {
            String[] roleArr = access.split(";");

            String roleName = roleArr[0];
            Long objectId = Long.parseLong(roleArr[1]);
            Long operationId = Long.parseLong(roleArr[2]);

            for (Map.Entry<String, List<RwRoleAuthority>> entry :
                    accessToken.getDetailed_roles().entrySet()) {
                String key = entry.getKey();
                List<RwRoleAuthority> list = entry.getValue();

                if (roleName.equals(key)) {
                    for (RwRoleAuthority rwRoleAuthority : list) {
                        if (rwRoleAuthority.getObjectId() == objectId &&
                                rwRoleAuthority.getOperationId() == operationId) {
                            hasRole = true;
                            break OUTERMOST;
                        }
                    }
                }
            }
        }
        return hasRole;
    }
}
