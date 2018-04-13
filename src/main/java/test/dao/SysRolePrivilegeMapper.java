package test.dao;

import java.util.List;
import test.model.SysRolePrivilege;

public interface SysRolePrivilegeMapper {
    int insert(SysRolePrivilege record);

    List<SysRolePrivilege> selectAll();
}