package com.netwin.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.netwin.entity.OurUser;



public class OurUserInfoDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private int ntwnCustMassSrNo;

    public OurUserInfoDetails(OurUser ourUser){
        this.username = ourUser.getUserNames();
        this.password = ourUser.getPassword();
     this.ntwnCustMassSrNo = ourUser.getNetwCustSrno();
       
	
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
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
	@Override
	public String getUsername() {
		
		return this.username;
	}
	
	
    
}
