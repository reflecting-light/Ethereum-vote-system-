package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class LoginInfo {
    private String username;
    private String userAddress;
    private Integer nonce;
    private List<String> roleList;
    private List<String> permissionList;
}
