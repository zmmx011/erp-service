package com.invenia.erpservice.user;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "_TCAUser", indexes = {
    @Index(name = "_TCAUser_IDX", columnList = "UserSeq", unique = true),
    @Index(name = "NCL1_TCAUser", columnList = "CompanySeq, EmpSeq"),
    @Index(name = "_TCAUser_UserID", columnList = "UserId", unique = true)
})
@Entity
@Getter
@Setter
public class User {

  @Column(name = "companyseq")
  private Integer companySeq;

  @Id
  @Column(name = "userseq", nullable = false)
  private Integer userSeq;

  @Column(name = "userid", length = 100)
  private String userId;

  @Column(name = "usertype", nullable = false)
  private Integer userType;

  @Column(name = "username", nullable = false, length = 40)
  private String userName;

  @Column(name = "empseq")
  private Integer empSeq;

  @Column(name = "loginpwd", length = 200)
  private String loginPwd;

  @Column(name = "password1", length = 200)
  private String password1;

  @Column(name = "password2", length = 200)
  private String password2;

  @Column(name = "password3", length = 200)
  private String password3;

  @Column(name = "custseq")
  private Integer custSeq;

  @Column(name = "deptseq")
  private Integer deptSeq;

  @Column(name = "usersecu")
  private Integer userSecu;

  @Column(name = "loginstatus")
  private Integer loginStatus;

  @Column(name = "logindate", length = 8)
  private String loginDate;

  @Column(name = "pwdchgdate", length = 8)
  private String pwdChgDate;

  @Column(name = "passhis", length = 2000)
  private String passHis;

  @Column(name = "pwdmailadder", length = 50)
  private String pwdMailAdder;

  @Column(name = "loginfailcnt")
  private Integer loginFailCnt;

  @Column(name = "pwdtype", length = 1)
  private String pwdType;

  @Column(name = "logintype")
  private Integer loginType;

  @Column(name = "managementtype")
  private Integer managementType;

  @Column(name = "lastuserseq")
  private Integer lastUserSeq;

  @Column(name = "lastdatetime")
  private Instant lastDateTime;

  @Column(name = "dsn", length = 20)
  private String dsn;

  @Column(name = "remark", length = 100)
  private String remark;

  @Column(name = "userlimitdate", length = 8)
  private String userlimitDate;

  @Column(name = "loginfailfirsttime")
  private Instant loginFailFirstTime;

  @Column(name = "islayoutadmin")
  private Integer isLayoutAdmin;

  @Column(name = "isgroupwareuser", length = 1)
  private String isGroupWareUser;

  @Column(name = "smusertype")
  private Integer sMUserType;

  @Column(name = "licensetype")
  private Integer licenseType;
}
