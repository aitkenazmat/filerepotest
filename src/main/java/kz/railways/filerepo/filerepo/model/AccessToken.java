package kz.railways.filerepo.filerepo.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccessToken {

    private String user_name;
    private Set<String> authorities;
    private Map<String, List<RwRoleAuthority>> detailed_roles;
    private Set<String> scope;
    private long exp;
    private String jti;
    private String client_id;


    public AccessToken() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Map<String, List<RwRoleAuthority>> getDetailed_roles() {
        return detailed_roles;
    }

    public void setDetailed_roles(Map<String, List<RwRoleAuthority>> detailed_roles) {
        this.detailed_roles = detailed_roles;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
