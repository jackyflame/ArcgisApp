package com.jf.greendaolib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by admin on 2017/8/9.
 */
@Entity
public class User extends BaseEntity{

    @Id
    private Long id;

    @Unique
    private String userName;

    @NotNull
    private String password;

    //@Property(nameInDb = "NICK_NAME") //改变列名
    private String nickName;

    //@Transient //不存储到数据库
    private String gasStation;

    @Generated(hash = 1207732571)
    public User(Long id, String userName, @NotNull String password, String nickName,
            String gasStation) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.gasStation = gasStation;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGasStation() {
        return this.gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }
    
}
