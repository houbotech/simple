package test.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import test.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);
    
    SysUser selectUserAndRoleById(Long id);
    
    List<SysUser> selectByIdList(@Param("idList") List<Long> idList);
    
    int insertList(@Param("userList") List<SysUser> userList);
    
    int updateByMap(@Param("map") Map<String,Object> map);
    
    SysUser selectAllUserAndRolesSelect(Long id);
}