package kz.railways.filerepo.filerepo;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import kz.railways.filerepo.filerepo.annotation.Protected;
import kz.railways.filerepo.filerepo.model.AccessToken;
import kz.railways.filerepo.filerepo.model.RwRoleAuthority;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class ProtectedAnnotationAnalyzer {
    public void analyz(AccessToken accessToken, String nameClass, String currentMethodName) throws Exception {


        try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
                .whitelistPackages(getClass().getPackage().getName()).scan()) {

            ClassInfoList classInfos = result.getClassesWithAnnotation(RestController.class.getName());

            for (String className : classInfos.getNames()) {
                if (className.equals(nameClass)) {
                    Class cl = Class.forName(className);
                    Method[] methods = cl.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Protected.class)) {
                            if (method.getName().equals(currentMethodName)) {
                                // Получаем доступ к атрибутам
                                Protected aProtected = method.getAnnotation(Protected.class);
                                String[] accesses = aProtected.accesses();
                                if (this.hasRole(accesses, accessToken)) {
                                    System.out.println("Method with name " + method.getName() + " success");
                                } else {
                                    System.out.println("Method with name " + method.getName() + " ERROR*****");
                                    throw new Exception();
                                }
                            }
                        }
                    }
                }
            }
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
