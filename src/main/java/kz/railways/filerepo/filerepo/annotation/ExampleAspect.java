package kz.railways.filerepo.filerepo.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.railways.filerepo.filerepo.ProtectedAnnotationAnalyzer;
import kz.railways.filerepo.filerepo.SecuredController;
import kz.railways.filerepo.filerepo.model.AccessToken;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;



@Aspect
@Component
public class ExampleAspect {

    @Around("@annotation(Protected)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String currentMethodName =
                joinPoint.getSignature().getDeclaringType().getMethod(joinPoint.getSignature().getName()).getName();

        String className = joinPoint.getSignature().getDeclaringType().getName();

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

            ProtectedAnnotationAnalyzer analyzer = new ProtectedAnnotationAnalyzer();
            analyzer.analyz(accessToken,className, currentMethodName);

            return joinPoint.proceed();

        } else {
            throw new Exception();
        }
    }
}
