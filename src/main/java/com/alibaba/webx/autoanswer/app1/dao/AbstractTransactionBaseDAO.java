package com.alibaba.webx.autoanswer.app1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.ibatis.sqlmap.client.SqlMapClient;

@Component("abstractTransactionBaseDAO")
public class AbstractTransactionBaseDAO extends SqlMapClientDaoSupport {
    @Autowired
    public void setBaseSqlMapClient(@Qualifier("workplatfromSqlMapClient") SqlMapClient  sqlMapClient){
        this.setSqlMapClient(sqlMapClient);
    }
}

