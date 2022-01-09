package com.pharmacy.v3;

import com.pharmacy.v3.DTO.CartDTO;
import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.*;
import com.pharmacy.v3.Security.jwt.JwtUtils;
import com.pharmacy.v3.Services.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class V3ApplicationTests {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private CategoryService categoryService;

	@MockBean
	private UserRepository userRepository;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private OTPRepository otpRepository;
	@MockBean
	private CartRepository cartRepository;
	@MockBean
	private ItemRepository itemRepository;
	@MockBean
	HttpServletRequest request;
	@MockBean
	private CategoryRepository categoryRepository;

	//user service functions
	@Test
	public void getAllUsers(){
		Role role= roleRepository.findByRole("ROLE_USER");
		when(userRepository.findAll()).thenReturn(Stream
				.of(new User(26,"testingUser12","abcgdfe123@gmail.com","0771556181","password","pending",role),
						new User(25,"user123","sachithraka123.ks@gmail.com","0771556916","password","verified",role)).collect(Collectors.toList()));
		assertEquals(2,userService.getAllUsers().size());
	}
	@Test
	public void getUserByEmail(){
		Role role= roleRepository.findByRole("ROLE_USER");
		String userName="user123";
		User user=new User(25, "user123", "sachithraka123.ks@gmail.com", "0771556916", "password", "verified", role);
		when(userRepository.findByUsername(userName))
				.thenReturn(java.util.Optional.of(user));
		assertEquals(user,userService.getUserByUserName(userName));
	}

	@Test
	public void findUser(){
		Role role= roleRepository.findByRole("ROLE_USER");
		int id=25;
		User user=new User(25, "user123", "sachithraka123.ks@gmail.com", "0771556916", "password", "verified", role);
		when(userRepository.findById(id))
				.thenReturn(java.util.Optional.of(user));
		assertEquals(user,userService.findUser(id));
	}
	@Test
	public void directUserType(){
		Role role= roleRepository.findByRole("ROLE_USER");
		String userName="user123";
		User user=new User(25, "user123", "sachithraka123.ks@gmail.com", "0771556916", "password", "verified", role);
		when(userRepository.findByUsername(userName))
				.thenReturn(java.util.Optional.of(user));
		assertEquals(user,userService.directUserType(userName));
	}
	@Test
	public void deleteUser(){
		Role role= roleRepository.findByRole("ROLE_USER");
		User user=new User(25, "user123", "sachithraka123.ks@gmail.com", "0771556916", "password", "verified", role);
		userService.delete(user);
		verify(userRepository,times(1)).delete(user);
	}

	//cart service functions
	@Test
	public void addItemToCart(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		//Optional<Item> item=itemRepository.findById(10);
		//CartDTO cartDTO=new CartDTO(100,user,item,1000,199.1,false,11);
		Cart cart=new Cart(user,item,1000,2000,false);
		when(cartRepository.save(cart)).thenReturn(cart);
		assertEquals(cart,cartService.save(cart));
	}
	@Test
	public void getCart(){
		int id=111;
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Cart cart=new Cart(user,item,1000,2000,false);
		when(cartRepository.findById(id)).
				thenReturn(java.util.Optional.of(cart));
		assertEquals(cart,cartService.getCartFromId(111));
	}
	@Test
	public void getAll(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Cart cart=new Cart(user,item,1000,2000,false);
		Cart cart1=new Cart(user,item,1000,2000,false);
		when(cartRepository.findAll()).thenReturn(Stream.of(cart,cart1).collect(Collectors.toList()));
		assertEquals(2,cartService.all().size());
	}
	@Test
	public void deleteCart(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Cart cart=new Cart(user,item,1000,2000,false);
		cartService.delete(cart);
		verify(cartRepository,times(1)).delete(cart);
	}
	@Test
	public void getUserCart(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Cart cart=new Cart(user,item,1000,2000,false);
		Cart cart1=new Cart(user,item,1000,2000,false);
		when(cartRepository.findByUserAndIsPurchased(user,false)).
				thenReturn(Stream.of(cart,cart1).collect(Collectors.toList()));
		assertEquals(2,cartService.getUserCart(user).size());
	}

	//category functions

}
