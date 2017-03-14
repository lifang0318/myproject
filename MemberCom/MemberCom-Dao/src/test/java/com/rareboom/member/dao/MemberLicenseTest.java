package com.rareboom.member.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rareboom.member.dao.entity.MemberLicense;
import com.rareboom.member.dao.entity.MemberSummary;
import com.rareboom.member.dao.lang.MemberSummaryFindCondition;
import com.rd.jbasis.datalink.persistence.BaseCondition;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "file:WebContent/WEB-INF/config/applicationContext.xml")
public class MemberLicenseTest{
     
	@Autowired
    private IMemberLicenseDao memberLicenseDao;
	/*@Test
	public void testInsert() throws Exception{
         ApplicationContext ctx=new FileSystemXmlApplicationContext("file:WebContent/WEB-INF/config/applicationContext.xml");
		 DataSource dataSource=(DataSource) ctx.getBean("dataSource");
		 Connection conn=dataSource.getConnection();
		 System.out.println(conn.toString());
		MemberLicense ml=new MemberLicense();
		ml.setCreateDate(new Date());
		ml.setEnterpriseId("A");
		ml.setStatusName("激活");
		ml.setCreateDate(new Date());
		ml.setCreateTime(new Date());
		ml.setMemberAccount("ABC");
		ml.setMemberName("lifang");
		ml.setLicenseStatus("B");
		ml.setValidTime(new Date());
		ml.setToken("a123");
		ml.setEnterpriseName("guibao");
		memberLicenseDao.insert(ml);

	}*/

}
