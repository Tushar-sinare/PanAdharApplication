package com.netwin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.netwin.entity.NetwinCustomerDetails;
import com.netwin.entity.OurUser;
import com.netwin.repo.NetwinCustomerDetailsRepo;
import com.netwin.repo.OurUserRepo;

import java.util.Optional;

@Configuration
public class OurUserInfoUserDetailsService implements UserDetailsService {
	 @Autowired
    private OurUserRepo ourUserRepo;
   
	 @Autowired
	private NetwinCustomerDetailsRepo customerDetailsRepo;
public String custName = "SHARNIRA CBSV21";
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<OurUser> user = ourUserRepo.findByUserName(userName);
        Optional<NetwinCustomerDetails> user1 = customerDetailsRepo.findById(user.get().getNetwCustSrno());
       if(!user1.get().getNetwCustId().equals(custName)) {
    	   return (UserDetails)  new UsernameNotFoundException("Customer Authentication Failed");
       }
        return user.map(OurUserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("User Does Not Exist"));
    }
}
