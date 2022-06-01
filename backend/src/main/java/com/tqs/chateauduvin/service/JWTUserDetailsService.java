package com.tqs.chateauduvin.service;

// This code was retrieved from the guide https://www.javainuse.com/spring/boot-jwt
// All credits go to the author

import java.util.ArrayList;

import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user = customerDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public Customer save(Customer customer) {
		Customer newCustomer = new Customer();
		newCustomer.setUsername(customer.getUsername());
		newCustomer.setPassword(bcryptEncoder.encode(customer.getPassword()));
		newCustomer.setName(customer.getName());
		newCustomer.setPhone(customer.getPhone());
		return customerDao.save(newCustomer);
	}
}