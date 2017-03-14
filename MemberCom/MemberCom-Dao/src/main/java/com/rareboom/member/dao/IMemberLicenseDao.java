package com.rareboom.member.dao;

import org.springframework.stereotype.Repository;

import com.rareboom.member.dao.entity.MemberLicense;
import com.rd.jbasis.datalink.persistence.IGeneralDao;
import com.rd.jbasis.datalink.persistence.IGeneralUpdateDao;

@Repository("memberLicenseDao")
public interface IMemberLicenseDao extends IGeneralDao<String, MemberLicense>,IGeneralUpdateDao<String,MemberLicense>{
    
}
