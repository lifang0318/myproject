package com.rareboom.member.dao;

import org.springframework.stereotype.Repository;

import com.rareboom.member.dao.entity.MemberSummary;
import com.rd.jbasis.datalink.persistence.IGeneralDao;
import com.rd.jbasis.datalink.persistence.IGeneralUpdateDao;

@Repository("memberSummaryDao")
public interface IMemberSummaryDao extends IGeneralDao<String, MemberSummary>,IGeneralUpdateDao<String, MemberSummary>{
	
}
