package kz.railways.filerepo.filerepo.model;


import java.util.Objects;

public class RwRoleAuthority {

    private long id;
    private long objectId;
    private long operationId;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getObjectId() {
        return this.objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getOperationId() {
        return this.operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RwRoleAuthority that = (RwRoleAuthority) o;
        return id == that.id &&
                objectId == that.objectId &&
                operationId == that.operationId &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, objectId, operationId, role);
    }
}
