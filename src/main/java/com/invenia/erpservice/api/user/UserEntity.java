package com.invenia.erpservice.api.user;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "_tcaUser")
@Getter
@Setter
@ToString
public class UserEntity {

  @Id
  @Column(name = "UserSeq", nullable = false)
  private Integer userSeq;

  @Column(name = "CompanySeq")
  private Integer companySeq;

  @Column(name = "UserId", length = 100)
  private String userId;

  @Column(name = "UserType", nullable = false)
  private Integer userType;

  @Column(name = "UserName", nullable = false, length = 40)
  private String userName;

  @Column(name = "EmpSeq")
  private Integer empSeq;

  @Column(name = "LoginPwd", length = 200)
  private String loginPwd;

  @Column(name = "Password1", length = 200)
  private String password1;

  @Column(name = "Password2", length = 200)
  private String password2;

  @Column(name = "Password3", length = 200)
  private String password3;

  @Column(name = "CustSeq")
  private Integer custSeq;

  @Column(name = "DeptSeq")
  private Integer deptSeq;

  @Column(name = "UserSecu")
  private Integer userSecu;

  @Column(name = "LoginStatus")
  private Integer loginStatus;

  @Column(name = "LoginDate", length = 8)
  private String loginDate;

  @Column(name = "PwdChgDate", length = 8)
  private String pwdChgDate;

  @Column(name = "PassHis", length = 2000)
  private String passHis;

  @Column(name = "PwdMailAdder", length = 50)
  private String pwdMailAdder;

  @Column(name = "LoginFailCnt")
  private Integer loginFailCnt;

  @Column(name = "PwdType")
  private Character pwdType;

  @Column(name = "LoginType")
  private Integer loginType;

  @Column(name = "ManagementType")
  private Integer managementType;

  @Column(name = "LastUserSeq")
  private Integer lastUserSeq;

  @Column(name = "LastDateTime", nullable = false)
  private Instant lastDateTime;

  @Column(name = "Dsn", length = 20)
  private String dsn;

  @Column(name = "Remark", length = 100)
  private String remark;

  @Column(name = "UserlimitDate", length = 8)
  private String userlimitDate;

  @Lob
  @Column(name = "LoginFailFirstTime", insertable = false, updatable = false)
  private String loginFailFirstTime;

  @Column(name = "IsLayoutAdmin")
  private Integer isLayoutAdmin;

  @Column(name = "IsGroupWareUser", length = 1)
  private String isGroupWareUser;

  @Column(name = "SMUserType")
  private Integer sMUserType;

  @Column(name = "LicenseType")
  private Integer licenseType;

}
