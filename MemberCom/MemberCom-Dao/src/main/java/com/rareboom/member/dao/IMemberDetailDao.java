package com.rareboom.member.dao;

import org.springframework.stereotype.Repository;

import com.rareboom.member.dao.entity.MemberDetail;
import com.rd.jbasis.datalink.persistence.IGeneralDao;
import com.rd.jbasis.datalink.persistence.IGeneralUpdateDao;


@Repository("memberDetailDao")
public interface IMemberDetailDao
		extends IGeneralDao<String, MemberDetail>, IGeneralUpdateDao<String, MemberDetail> {

}
