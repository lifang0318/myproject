package com.rareboom.member.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;
import com.rd.jbasis.datalink.persistence.BaseCondition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/config/applicationContext.xml")
public class MemberSummaryTest{
     
	@Autowired
    private IMemberSummaryDao memberSummaryDao;
	/*@Test
	public void testInsert() throws Exception{
         ApplicationContext ctx=new FileSystemXmlApplicationContext("file:WebContent/WEB-INF/config/applicationContext.xml");
		 DataSource dataSource=(DataSource) ctx.getBean("dataSource");
		 Connection conn=dataSource.getConnection();
		 System.out.println(conn.toString());
		
	MemberSummary memberSummary=new MemberSummary();
		memberSummary.setMemberId(String.valueOf(5));
		memberSummary.setMemberAccount("13882019775");
		memberSummary.setLoginPassword("1234567");
		memberSummary.setHeadImage("image");
		memberSummary.setMemberStatus("A");
		memberSummary.setStatusName("激活");
		memberSummary.setMemberType("A");
		memberSummary.setTypeName("一般会员");
		memberSummary.setNickName("小张");
		memberSummary.setMemberName("张三");
		memberSummary.setMemberPhone("138820197733");
		memberSummary.setMemberMail("123456@qq.com");
		memberSummary.setRegisterDate(new Date());
		memberSummary.setRegisterTime(new Date());
		memberSummary.setRegisterLocation("成都");
		memberSummary.setValidTime(new Date());
		memberSummary.setLastLoginDate(new Date());
		memberSummary.setLastLoginTime(new Date());
		memberSummary.setToken("token");
		memberSummaryDao.insert(memberSummary);

	}
	@Test
	public void testUpdate() throws Exception{
	   MemberSummary memberSummary=new MemberSummary();
	   memberSummary.setMemberAccount("AAA");
		memberSummary.setLoginPassword("12345");
		memberSummary.setLastLoginDate(new Date());
		memberSummary.setLastLoginTime(new Date());
		
		
		memberSummaryDao.update(memberSummary);

	}

   @Test 
   public void testFind() throws Exception{
	   
	   //MemberSummaryFindCondition condition=new  MemberSummaryFindCondition();
	   //condition.setMemberType("A");
	   MemberSummary memberSummary=new MemberSummary();
	   memberSummary=memberSummaryDao.get("1");
	   System.out.println(memberSummary.toString());
	}
 @Test
 public void testFindList() throws Exception{
	   
	   MemberSummaryFindCondition condition=new  MemberSummaryFindCondition();
	   condition.setMemberType("A");
	   List<MemberSummary> list=memberSummaryDao.findList(condition);
	   System.out.println(list.size());
	   



	}
 @Test
 public void testFindCount() throws Exception{
	   
	   MemberSummaryFindCondition condition=new  MemberSummaryFindCondition();
	   condition.setMemberType("A");
	   System.out.println(memberSummaryDao.findCount(condition));
	}*/
}
