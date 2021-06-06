package com.example.controller;

import com.example.entity.Proposal;
import com.example.entity.Result;
import com.example.service.ProposalService;
import com.example.utils.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @GetMapping("/findAll")
    public Result findAll(String kind){
        Result result = new Result();
        System.out.println("kind = " + kind);
        try{
            List<Proposal> proposalList=proposalService.findAll(kind);
            System.out.println(proposalList);
            result.setSuccess(true);
            result.setMsg("查询成功！！");
            result.setResult(proposalList);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("啊哦，出错了~");
            result.setResult(null);
        }
        return result;
    }
    @GetMapping("find")
    public Result findFuzzy(String condition){
        Result result = new Result();
        try{
            List<Proposal> proposalList = proposalService.findFuzzy(condition);
            result.setSuccess(true);
            result.setMsg("查询成功");
            result.setResult(proposalList);
        }catch(Exception e){
            System.out.println("error occured during finding :" + e);
            result.setSuccess(false);
            result.setMsg("发生了点小意外，嘿嘿");
        }
        return result;
    }

    @PostMapping("covers")
    public String coversUpload(@RequestBody MultipartFile file) throws Exception {
        String folder = "D:/vue-cli/elementui/src/assets/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "../../assets/img/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
//    @PostMapping("/vote")
//    public Result vote(@RequestBody Proposal proposal){
//        Result result = new Result();
//        System.out.println("proposal = " + proposal);
//        try{
//            proposal.setCount(proposal.getCount()+1);
//            proposalService.updateProposal(proposal);
//            result.setSuccess(true);
//            result.setMsg("已成功为您喜爱的选手投票！");
//        }catch (Exception e){
//            System.out.println("error occured during voting:" + e);
//            result.setSuccess(false);
//            result.setMsg("sorry,投票的过程中发生了点儿小意外~");
//        }
//        return result;
//    }
    @PostMapping("/addOrUpdate")
    public Result addProposal(@RequestBody Proposal proposal){
        Result result = new Result();
        System.out.println("proposal1 = " + proposal);
        try{
            if(proposal.getId()!=null){
                proposalService.updateProposal(proposal);
                result.setMsg("添加成功！！");
            }else {
                proposalService.addProposal(proposal);
                result.setMsg("更新成功！！");
            }

            result.setSuccess(true);

            System.out.println("proposal = " + proposal);
//            result.setResult(null);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("不好意思，添加的过程中出了点小意外，稍后再试吧~~");
            System.out.println("error occured:" + e);
        }
        return result;
    }
}
