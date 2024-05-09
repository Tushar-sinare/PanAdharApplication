package com.netwin.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.netwin.entity.OurUser;



public class OurUserInfoDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
    private String password;
    private int ntwnCustMassSrNo;

    public OurUserInfoDetails(OurUser ourUser){
        this.userName = ourUser.getUserNames();
        this.password = ourUser.getPassword();
     this.ntwnCustMassSrNo = ourUser.getNetwCustSrno();
       
	
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
	public int getNtwnCustMassSrNo() {
		return ntwnCustMassSrNo;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUserName() {
		return userName;
	}
	
	
    
}
