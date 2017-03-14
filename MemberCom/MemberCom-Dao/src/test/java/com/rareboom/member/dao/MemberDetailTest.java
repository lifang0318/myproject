package com.rareboom.member.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rareboom.member.dao.entity.MemberDetail;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberDetailFindCondition;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;
import com.rd.jbasis.datalink.persistence.BaseCondition;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "file:WebContent/WEB-INF/config/applicationContext.xml")
public class MemberDetailTest{
     
	@Autowired
    private IMemberDetailDao memberDetailDao;
	/*@Test
	public void testInsert() throws Exception{
         ApplicationContext ctx=new FileSystemXmlApplicationContext("file:WebContent/WEB-INF/config/applicationContext.xml");
		 DataSource dataSource=(DataSource) ctx.getBean("dataSource");
		 Connection conn=dataSource.getConnection();
		 System.out.println(conn.toString());
		
	MemberDetail meDetail=new MemberDetail();
	meDetail.setMemberId(String.valueOf(3));
	meDetail.setMemberAccount("13882019773");
	meDetail.setAge(10);
	meDetail.setHeadImage("image");
	meDetail.setMemberStatus("A");
	meDetail.setStatusName("激活");
	meDetail.setMemberType("A");
	meDetail.setTypeName("一般会员");
	meDetail.setNickName("小张");
	meDetail.setMemberName("张三");
	meDetail.setMemberPhone("138820197733");
	meDetail.setMemberMail("123456@qq.com");
	meDetail.setRegisterDate(new Date());
	meDetail.setRegisterTime(new Date());
	meDetail.setRegisterLocation("成都");
	meDetail.setChannelName("渠道");
	meDetail.setExtendProperty("扩展属性");
	meDetail.setVestEnterpriseId("1");
	meDetail.setVestEnterpriseName("佳联");
	memberDetailDao.insert(meDetail);
	}
  @Test
  public void testUpdate() throws Exception{
	         ApplicationContext ctx=new FileSystemXmlApplicationContext("file:WebContent/WEB-INF/config/applicationContext.xml");
			 DataSource dataSource=(DataSource) ctx.getBean("dataSource");
			 Connection conn=dataSource.getConnection();
			 System.out.println(conn.toString());
	
	    MemberDetail memberDetail=new MemberDetail();
	    memberDetail.setMemberId("1");
	    memberDetail.setMemberPhone("1388201975");

		memberDetailDao.update( memberDetail);

		
		}
  @Test
  public void testFind() throws Exception{
	         ApplicationContext ctx=new FileSystemXmlApplicationContext("file:WebContent/WEB-INF/config/applicationContext.xml");
			 DataSource dataSource=(DataSource) ctx.getBean("dataSource");
			 Connection conn=dataSource.getConnection();
			 System.out.println(conn.toString());
	
	    MemberDetail memberDetail=new MemberDetail();
	    memberDetail=memberDetailDao.get("1");
	    System.out.println(memberDetail.toJson());
		}
  @Test
  public void testFindList() throws Exception{
	         ApplicationContext ctx=new FileSystemXmlApplicationContext("file:WebContent/WEB-INF/config/applicationContext.xml");
			 DataSource dataSource=(DataSource) ctx.getBean("dataSource");
			 Connection conn=dataSource.getConnection();
			 System.out.println(conn.toString());
	
	  
	   MemberDetailFindCondition condition=new  MemberDetailFindCondition();
	   condition.setRegisterLocation("成都");
	   List<MemberDetail> list=memberDetailDao.findList(condition);
	   System.out.println(list.size());
		}*/
}
