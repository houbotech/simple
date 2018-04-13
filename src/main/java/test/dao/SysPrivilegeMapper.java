package test.dao;

import java.util.List;
import test.model.SysPrivilege;

public interface SysPrivilegeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysPrivilege record);

    SysPrivilege selectByPrimaryKey(Long id);

    List<SysPrivilege> selectAll();

    int updateByPrimaryKey(SysPrivilege record);
    
    List<SysPrivilege> selectPrivilegeByRoleId(Long roleId);
}