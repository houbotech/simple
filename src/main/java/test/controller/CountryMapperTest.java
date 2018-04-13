package test.controller;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.dao.CountryMapper;
import test.dao.SysUserMapper;
import test.model.Country;
import test.model.SysUser;

public class CountryMapperTest {
	private static SqlSessionFactory sqlSessionFactory;
	@Before
	public void setUp() throws Exception {
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
	}
	
	@Test
	public void testSelect() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			Country country = countryMapper.selectByPrimaryKey(2);
			System.out.println(country.getCountryname());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			Country country = new Country();
			country.setCountryname("China");
			country.setCountrycode("001");
			int result = countryMapper.insert(country);
			Assert.assertEquals(1, result);
			sqlSession.commit();
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testDelete() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			int result = countryMapper.deleteByPrimaryKey(4);
			Assert.assertEquals(1, result);
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdate() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			Country country = new Country();
			country.setId(3);
			int result = countryMapper.updateByPrimaryKey(country);
			Assert.assertEquals(1, result);
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateAllMessage() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			int result = countryMapper.updateAllMessage("China", "001");
			Assert.assertEquals(4, result);
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByCountry() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			Country country = new Country();
			country.setCountryname("C");
			country.setCountrycode("003");
			List<Country> result = countryMapper.selectByCountry(country);
			Assert.assertEquals(1, result.size());
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleById() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			SysUserMapper userMapper = sqlSession.getMapper(SysUserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getSysRole());
		}finally {
			sqlSession.close();
		}
	}
}
