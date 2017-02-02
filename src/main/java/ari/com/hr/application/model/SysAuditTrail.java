
package ari.com.hr.application.model;

import ari.com.hr.application.ref.EnumActionType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="sys_audit_trail")
public class SysAuditTrail extends ModelSerializable {
    
    @Column(name = "action_type")
    EnumActionType actionType;
    
    
}
