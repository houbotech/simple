package test.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespaceRef;

import test.model.SysRole;

@CacheNamespaceRef(SysRoleMapper.class)
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> selectRoleByUserId(Long userId);
    
    List<SysRole> selectRoleByUserIdChoose(Long userId);
}