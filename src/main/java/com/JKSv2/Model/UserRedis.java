package com.JKSv2.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("UserRedis")
public class UserRedis implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String username;
    private String password;
    private int enabled;

    String registeredfrom; // if from web application then set websiteUser else from jk samadhan
                           // application then set mobile - SKY

    public String getRegisteredfrom() {
        return registeredfrom;
    }

    public void setRegisteredfrom(String registeredfrom) {
        this.registeredfrom = registeredfrom;
    }

    String preferedcommunicationlang; // For English set value as eng, For Hindi set value as hindi, for Urdu set
                                      // value as urdu - SKY

    public String getPreferedcommunicationlang() {
        return preferedcommunicationlang;
    }

    public void setPreferedcommunicationlang(String preferedcommunicationlang) {
        this.preferedcommunicationlang = preferedcommunicationlang;
    }

    private String user_type;
    // 14th feb 2024
    private String user_classification;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String mobile;
    private String email;
    private String designation;
    private String department;
    private String department_type;
    private String gender;
    private String date_of_birth;
    private String region;
    private String district;
    private String pincode;
    private String created_date;
    private String created_by;
    private String user_assigned;
    private String userType_of_assigned_user;
    private String emailalerts;
    private String office_name;

    // Random IDs and UUID - SKY
    private Long userid;
    private String uuid;
    private String createdbyid;
    // Random IDs and UUID - SKY

    @Column(columnDefinition = "varchar")
    private String address;

    private String userflag;

    private String block;
    private String panchayat;
    private String municipality;
    private String ward;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    //// new code tushar///
    private Integer user_level;

    public Integer getUser_level() {
        return user_level;
    }

    public void setUser_level(Integer user_level) {
        this.user_level = user_level;
    }

   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDepartment_type() {
        return department_type;
    }

    public void setDepartment_type(String department_type) {
        this.department_type = department_type;
    }

    public String getUser_classification() {
        return user_classification;
    }

    public void setUser_classification(String user_classification) {
        this.user_classification = user_classification;
    }

    public String getUser_assigned() {
        return user_assigned;
    }

    public void setUser_assigned(String user_assigned) {
        this.user_assigned = user_assigned;
    }

    public String getUserType_of_assigned_user() {
        return userType_of_assigned_user;
    }

    public void setUserType_of_assigned_user(String userType_of_assigned_user) {
        this.userType_of_assigned_user = userType_of_assigned_user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedbyid() {
        return createdbyid;
    }

    public void setCreatedbyid(String createdbyid) {
        this.createdbyid = createdbyid;
    }

    public String getEmailalerts() {
        return emailalerts;
    }

    public void setEmailalerts(String emailalerts) {
        this.emailalerts = emailalerts;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getUserflag() {
        return userflag;
    }

    public void setUserflag(String userflag) {
        this.userflag = userflag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    @Override
    public String toString() {
        return "UserRedis [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
                + ", registeredfrom=" + registeredfrom + ", preferedcommunicationlang=" + preferedcommunicationlang
                + ", user_type=" + user_type + ", user_classification=" + user_classification + ", first_name="
                + first_name + ", middle_name=" + middle_name + ", last_name=" + last_name + ", mobile=" + mobile
                + ", email=" + email + ", designation=" + designation + ", department=" + department
                + ", department_type=" + department_type + ", gender=" + gender + ", date_of_birth=" + date_of_birth
                + ", region=" + region + ", district=" + district + ", pincode=" + pincode + ", created_date="
                + created_date + ", created_by=" + created_by + ", user_assigned=" + user_assigned
                + ", userType_of_assigned_user=" + userType_of_assigned_user + ", emailalerts=" + emailalerts
                + ", office_name=" + office_name + ", userid=" + userid + ", uuid=" + uuid + ", createdbyid="
                + createdbyid + ", address=" + address + ", userflag=" + userflag + ", block=" + block + ", panchayat="
                + panchayat + ", municipality=" + municipality + ", ward=" + ward + ", user_level=" + user_level
                + "]";
    }

}
