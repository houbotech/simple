package test.dao;

import java.util.List;
import test.model.SysUserRole;

public interface SysUserRoleMapper {
    int insert(SysUserRole record);

    List<SysUserRole> selectAll();
}