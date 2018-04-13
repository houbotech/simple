package test.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import test.type.Enabled;

public class SysRole implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6281123210107088991L;

	private Long id;

    private String roleName;

    private Enabled enabled;

    private Long createBy;

    private Date createTime;
    
    private List<SysPrivilege> privilegeList;
    

    public List<SysPrivilege> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<SysPrivilege> privilegeList) {
		this.privilegeList = privilegeList;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Enabled getEnabled() {
		return enabled;
	}

	public void setEnabled(Enabled enabled) {
		this.enabled = enabled;
	}
    
    
}