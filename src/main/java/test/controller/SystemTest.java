package test.controller;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.dao.SysRoleMapper;
import test.dao.SysUserMapper;
import test.model.SysPrivilege;
import test.model.SysRole;
import test.model.SysUser;
import test.type.Enabled;

public class SystemTest {

	private static SqlSessionFactory sqlSessionFactory;
	@Before
	public void setUp() throws Exception {
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
	}

	@Test
	public void testSelectUserAndRoleById() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			Assert.assertNotNull(user);
			System.out.println("调用user.getRole()");
			Assert.assertNotNull(user.getSysRole());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByIdList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(1001L);
			List<SysUser> userList = userMapper.selectByIdList(idList);
			Assert.assertEquals(2, userList.size());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsertList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			List<SysUser> userList = new ArrayList<SysUser>();
			for(int i=0;i<2;i++) {
				SysUser user = new SysUser();
				user.setUserName("test"+i);
				user.setUserPassword("123456");
				user.setUserEmail("test@mybatis.tk");
				userList.add(user);
			}
			int result = userMapper.insertList(userList);
			Assert.assertEquals(2, result);
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateByMap() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", 1L);
			map.put("user_email", "test@mybatis.tk");
			map.put("user_password", "12345678");
			userMapper.updateByMap(map);
			SysUser user = userMapper.selectByPrimaryKey(1L);
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRolesSelect() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
			System.out.println("用户名：" + user.getUserName());
			for(SysRole role : user.getRoleList()) {
				System.out.println("角色名：" + role.getRoleName());
				for(SysPrivilege privilege : role.getPrivilegeList()) {
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectRoleByUserIdChoose() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysRoleMapper roleMapper = sqlSession.getMapper(SysRoleMapper.class);
			SysRole r = roleMapper.selectByPrimaryKey(2L);
			r.setEnabled(Enabled.enabled);
			roleMapper.updateByPrimaryKey(r);
			List<SysRole> sysRoleList = roleMapper.selectRoleByUserIdChoose(1L);
			for(SysRole role : sysRoleList) {
				System.out.println("角色名：" + role.getRoleName());
				if(role.getId().equals(1L)) {
					Assert.assertNotNull(role.getPrivilegeList());
				}else if(role.getId().equals(2L)) {
					Assert.assertNull(role.getPrivilegeList());
					continue;
				}
				for(SysPrivilege p : role.getPrivilegeList()) {
					System.out.println("权限名：" + p.getPrivilegeName());
				}
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateById() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysRoleMapper roleMapper = sqlSession.getMapper(SysRoleMapper.class);
			SysRole role = roleMapper.selectByPrimaryKey(2L);
			Assert.assertEquals(Enabled.enabled, role.getEnabled());
			role.setEnabled(Enabled.disabled);
			roleMapper.updateByPrimaryKey(role);
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	@Test
	public void testL1Cache() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		SysUser user1 = null;
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			user1 = userMapper.selectByPrimaryKey(1L);
			user1.setUserName("New Name");
			SysUser user2 = userMapper.selectByPrimaryKey(1L);
			Assert.assertEquals("New Name", user2.getUserName());
			Assert.assertEquals(user1,user2);
		}finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession");
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession2.getMapper(SysUserMapper.class);
			SysUser user2 = userMapper.selectByPrimaryKey(1L);
			Assert.assertNotEquals("New Name", user2.getUserName());
			Assert.assertNotEquals(user1, user2);
			userMapper.deleteByPrimaryKey(2L);
			SysUser user3 = userMapper.selectByPrimaryKey(1L);
			Assert.assertNotEquals(user2, user3);
		}finally {
			sqlSession2.close();
		}
	}
	
	@Test
	public void testL2Cache() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		SysRole role1 = null;
		try {
			SysRoleMapper roleMapper = sqlSession.getMapper(SysRoleMapper.class);
			role1 = roleMapper.selectByPrimaryKey(1L);
			role1.setRoleName("New Name");
			SysRole role2 = roleMapper.selectByPrimaryKey(1L);
			Assert.assertEquals("New Name", role2.getRoleName());
			Assert.assertEquals(role1, role2);
		}finally {
			sqlSession.close();
		}
		
		System.out.println("开启新的sqlSession");
		sqlSession = sqlSessionFactory.openSession();
		try {
			SysRoleMapper roleMapper = sqlSession.getMapper(SysRoleMapper.class);
			SysRole role2 = roleMapper.selectByPrimaryKey(1L);
			Assert.assertEquals("New Name", role2.getRoleName());
			Assert.assertNotEquals(role1, role2);
			SysRole role3 = roleMapper.selectByPrimaryKey(1L);
			Assert.assertNotEquals(role2, role3);
		}finally {
			sqlSession.close();
		}
	}
}
