package com.tqs.chateauduvin.controller;

import com.tqs.chateauduvin.model.Customer;
import com.tqs.chateauduvin.model.JWTRequest;
import com.tqs.chateauduvin.model.JWTResponse;
import com.tqs.chateauduvin.repository.CustomerRepository;
import com.tqs.chateauduvin.service.JWTUserDetailsService;

import java.util.Objects;

import com.tqs.chateauduvin.config.JWTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JWTAuthenticationController {
	@Autowired CustomerRepository customerRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTokenUtil jwtTokenUtil;

	@Autowired
	private JWTUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JWTResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody Customer customer) throws Exception {
		if(customerRepository.findByUsername(customer.getUsername()) == null)
			return ResponseEntity.ok(userDetailsService.save(customer));
		else
			return ResponseEntity.status(409).build();
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
