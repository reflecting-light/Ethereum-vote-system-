package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("comedyteam")
@Accessors(chain = true)
public class Proposal {
    @TableId(type = IdType.INPUT)
    private String id;
    private String team;
    private String names;
    private String masterwork;
    private String work;
    private String type;
    private String cover;
    private String discription;
    private Integer count;
}
