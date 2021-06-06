package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dao.ProposalDao;
import com.example.entity.Proposal;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProposalServiceImpl implements ProposalService{
    @Autowired
    private ProposalDao proposalDao;
    @Override
    public List<Proposal> findAll(String kind) {
        if(kind.equals("全部")){
            System.out.println("Service kind = " + kind);
            return proposalDao.selectList(null);
        }
        QueryWrapper<Proposal> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("type",kind);
        return proposalDao.selectList(queryWrapper);
    }

    @Override
    public List<Proposal> findFuzzy(String condition) {
        QueryWrapper<Proposal> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("names",condition);
        List<Proposal> proposalList = proposalDao.selectList(queryWrapper);
        if(proposalList.size()==0){

            queryWrapper.clear();
            queryWrapper.like("team",condition);
//            System.out.println("condition = " + queryWrapper);
            proposalList = proposalDao.selectList(queryWrapper);
//            System.out.println("proposal = " + proposalList);
        }

        return proposalList;
    }

    @Override
    public void addProposal(Proposal proposal) {
        proposalDao.insert(proposal);
    }

    @Override
    public void updateProposal(Proposal proposal) {
        proposalDao.updateById(proposal);
    }
}
