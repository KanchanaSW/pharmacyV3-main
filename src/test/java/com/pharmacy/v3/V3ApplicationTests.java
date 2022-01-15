package com.pharmacy.v3;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.*;
import com.pharmacy.v3.Repositories.*;
import com.pharmacy.v3.Security.jwt.JwtUtils;
import com.pharmacy.v3.Services.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
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
	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private ItemRequestsService itemRequestsService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private OrderedItemsService orderedItemsService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthService authService;

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
	@Mock
	HttpServletRequest request;
	@MockBean
	private CategoryRepository categoryRepository;
	@MockBean
	private InquiryRepository inquiryRepository;
	@MockBean
	private ItemRequestsRepository itemRequestsRepository;
	@MockBean
	private OrderedItemsRepository orderedItemsRepository;
	@MockBean
	private OrdersRepository ordersRepository;
	@MockBean
	private RoleRepository roleRepository1;
	@MockBean
	private AuthenticationManager authenticationManager;
	@MockBean
	private JwtUtils jwtUtils;

	//user service functions
	@Test
	public void registerUser(){
		String roleName="ROLE_USER";
		UserDTO user=new UserDTO("user123", "sachithraka123.ks@gmail.com", "0771556916", "password");
		authService.registerUserService(user,roleName);
		verify(userRepository,times(1)).save(any(User.class));
	}
/*	@Test
	public void registerUser_EmailExists(){
		when(userRepository.existsByEmail("sachithrakanchana.ks@gmail.com")).thenReturn(true);
		String roleName="ROLE_USER";
		UserDTO user=new UserDTO("user123", "sachithrakanchana.ks@gmail.com", "0771556916", "password");
		ResponseEntity<?> expect=ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		ResponseEntity<?> actual=authService.registerUserService(user,roleName);
		assertEquals(expect,(actual));
	}*/
// @WithMockUser(username = "admin",password = "password",roles = "ADMIN")
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
	public void getUserCartList(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Cart cart=new Cart(user,item,1000,2000,false);
		Cart cart1=new Cart(user,item,1000,2000,false);
		when(cartRepository.findByUserAndIsPurchased(user,false)).
				thenReturn(Stream.of(cart,cart1).collect(Collectors.toList()));
		assertEquals(2,cartService.getUserCart(user).size());
	}

	//category functions
	@Test
	public void getAllCategories(){
		Category category=new Category(111,"testCat");
		Category category1=new Category(112,"testCat2");
		when(categoryRepository.findAllByOrderByCategoryIdDesc()).thenReturn(Stream.of(category,category1).collect(Collectors.toList()));
		assertEquals(2,categoryService.getAllCategories().size());
	}
	@Test
	public void getCategory(){
		String catName="testCat";
		Category category=new Category(111,"testCat");
		when(categoryRepository.categoryIs(catName)).thenReturn(category);
		assertEquals(category,categoryService.getCategory(catName));
	}

	//inquiry functions
	@Test
	public void getAllInquiryByItemId(){
		int itemId=111;
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Inquiry inquiry=new Inquiry(user,item,"question","answer","date",true);
		Inquiry inquiry1=new Inquiry(user,item,"question","answer","date",true);
		when(inquiryRepository.findByItemItemId(itemId)).
				thenReturn(Stream.of(inquiry,inquiry1).collect(Collectors.toList()));
		assertEquals(2,inquiryService.getAllInquiryByItemId(itemId).size());
	}
	@Test
	public void getAllInquiryIsReplied(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Inquiry inquiry=new Inquiry(user,item,"question","answer","date",true);
		Inquiry inquiry1=new Inquiry(user,item,"question","answer","date",true);
		when(inquiryRepository.findByIsReplied(true)).
				thenReturn(Stream.of(inquiry,inquiry1).collect(Collectors.toList()));
		assertEquals(2,inquiryService.getAllInquiryIsReplied(true).size());
	}
	@Test
	public void getInquiryById(){
		int id=111;
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Inquiry inquiry=new Inquiry(user,item,"question","answer","date",true);
		when(inquiryRepository.findById(id)).thenReturn(Optional.of(inquiry));
		assertEquals(inquiry,inquiryService.getInquiryById(id));
	}
	@Test
	public void allInquires(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Inquiry inquiry=new Inquiry(user,item,"question","answer","date",true);
		Inquiry inquiry1=new Inquiry(user,item,"question","answer","date",true);
		when(inquiryRepository.findAll()).
				thenReturn(Stream.of(inquiry,inquiry1).collect(Collectors.toList()));
		assertEquals(2,inquiryService.allInquires().size());
	}
	@Test
	public void addNewInquiry(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Inquiry inquiry=new Inquiry(user,item,"question","answer","date",true);
		when(inquiryRepository.save(inquiry)).thenReturn(inquiry);
		assertEquals(inquiry,inquiryService.save(inquiry));
	}
	@Test
	public void deleteInquiry(){
		User user=userRepository.findUserByUsername("user123");
		Item item=new Item();
		Inquiry inquiry=new Inquiry(user,item,"question","answer","date",true);
		inquiryService.delete(inquiry);
		verify(inquiryRepository,times(1)).delete(inquiry);
	}

	//Item-Requests functions testing
	@Test
	public void getItemRequest(){
		int iReqId=111;
		ItemRequests itemRequests=new ItemRequests();
		when(itemRequestsRepository.findById(iReqId)).thenReturn(Optional.of(itemRequests));
		assertEquals(itemRequests,itemRequestsService.get(iReqId));
	}
	@Test
	public void getAllItemRequests(){
		ItemRequests i=new ItemRequests();
		ItemRequests i2=new ItemRequests();
		when(itemRequestsRepository.findAll()).
				thenReturn(Stream.of(i,i2).collect(Collectors.toList()));
		assertEquals(2,itemRequestsService.all().size());
	}
	@Test
	public void addNewItemRequest(){
		ItemRequests i=new ItemRequests();
		when(itemRequestsRepository.save(i)).thenReturn(i);
		assertEquals(i,itemRequestsService.save(i));
	}
	@Test
	public void deleteItemRequest(){
		ItemRequests i=new ItemRequests();
		itemRequestsService.delete(i);
		verify(itemRequestsRepository,times(1)).delete(i);
	}

	//item service functions
	@Test
	public void getItemList(){
		Item item=new Item();
		Item item1=new Item();
		when(itemRepository.findAll()).
				thenReturn(Stream.of(item,item1).collect(Collectors.toList()));
		assertEquals(2,itemService.getAll().size());
	}
	@Test
	public void getItem(){
		int id=111;
		Item item=new Item();
		when(itemRepository.findById(id)).thenReturn(Optional.of(item));
		assertEquals(item,itemService.find(id));
	}
	@Test
	public void addNewItem(){
		Item item=new Item();
		when(itemRepository.save(item)).thenReturn(item);
		assertEquals(item,itemService.save(item));
	}
	@Test
	public void deleteItem(){
		Item item=new Item();
		itemService.delete(item);
		verify(itemRepository,times(1)).delete(item);
	}

	//ordered-items service functions
	@Test
	public void getOrderedItemsList(){
		OrderedItems orderedItems=new OrderedItems();
		OrderedItems orderedItems1=new OrderedItems();
		when(orderedItemsRepository.findAll()).
				thenReturn(Stream.of(orderedItems,orderedItems1).collect(Collectors.toList()));
		assertEquals(2,orderedItemsService.getAll().size());
	}
	@Test
	public void getOrderedItem(){
		int id=111;
		OrderedItems orderedItems=new OrderedItems();
		when(orderedItemsRepository.findById(id)).thenReturn(Optional.of(orderedItems));
		assertEquals(orderedItems,orderedItemsService.get(id));
	}
	@Test
	public void addNewOrderedItem(){
		OrderedItems orderedItems=new OrderedItems();
		when(orderedItemsRepository.save(orderedItems)).thenReturn(orderedItems);
		assertEquals(orderedItems,orderedItemsService.save(orderedItems));
	}
	@Test
	public void deleteOrderedItem(){
		OrderedItems orderedItems=new OrderedItems();
		orderedItemsService.delete(orderedItems);
		verify(orderedItemsRepository,times(1)).delete(orderedItems);
	}

	//orders service function tsting
	@Test
	public void getAllOrders(){
		Orders orders=new Orders();
		Orders orders1=new Orders();
		when(ordersRepository.findAll())
				.thenReturn(Stream.of(orders,orders1).collect(Collectors.toList()));
		assertEquals(2,ordersService.all().size());
	}
	@Test
	public void getOrder(){
		int order=111;
		Orders orders=new Orders();
		when(ordersRepository.findById(order)).thenReturn(Optional.of(orders));
		assertEquals(orders,ordersService.get(order));
	}
	@Test
	public void addNewOrder(){
		Orders orders=new Orders();
		when(ordersRepository.save(orders)).thenReturn(orders);
		assertEquals(orders,ordersService.save(orders));
	}
	@Test
	public void deleteOrder(){
		Orders orders=new Orders();
		ordersService.deleteOrder(orders);
		verify(ordersRepository,times(1)).delete(orders);
	}
	@Test
	public void getAllPendingOrdersByStatus(){
		String status="pending";
		Orders orders=new Orders();
		Orders orders1=new Orders();
		when(ordersRepository.findByStatus(status))
				.thenReturn(Stream.of(orders,orders1).collect(Collectors.toList()));
		assertEquals(2,ordersService.getAllPendingOrdersByStatus(status).size());
	}

	//Role service functions testing
	@Test
	public void getRoleByName(){
		String role="ROLE_USER";
		Role role1=new Role();
		when(roleRepository1.findByRole(role)).thenReturn(role1);
		assertEquals(role1,roleService.getRoleByName(role));
	}

}
