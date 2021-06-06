package com.example.service;

import com.example.entity.Proposal;

import java.util.List;

public interface ProposalService {
    List<Proposal> findAll(String kind);
    List<Proposal> findFuzzy(String condition);
    void addProposal(Proposal proposal);
    void updateProposal(Proposal proposal);
}
