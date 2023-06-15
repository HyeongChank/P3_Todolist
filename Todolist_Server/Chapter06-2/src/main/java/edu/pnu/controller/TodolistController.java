package edu.pnu.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.ImageLoad;
import edu.pnu.domain.Members;
import edu.pnu.domain.Todolist;
import edu.pnu.service.TodoService;


//@CrossOrigin(origins = "https://todolist-45c52.web.app")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TodolistController {
	
	@Autowired
	TodoService ts ;
	
	@GetMapping("/api/model")
	public List<Todolist> getTodo(){

		return ts.gettodo();
	}
//	@GetMapping("/api/insert")
//	public List<Todolist> insertTodo(){
//
//		List<Todolist> todoList = new ArrayList<>();
//		for(long i=0; i<2L; i++) {
//			Todolist tl = new Todolist();
//
//			tl.setTodo("todo chech connect"+ i);
//			tl.setSuccess("success");
//			tl.setCdate("2023-05-02");
//			tl.setContent("connect alright");
//			todoList.add(tl);
//		}
//		return ts.insertTodo(todoList);
//	}
//	
	@PostMapping("/api/insertimage")
	public ResponseEntity<String> insertImage(
			@RequestParam("file") MultipartFile file,
			@RequestParam("num") int num,
			@RequestParam("imageLoad") String imageLoadStr) throws IOException {
		ImageLoad imageload = new ObjectMapper().readValue(imageLoadStr, ImageLoad.class);
		System.out.println("imageLoad");
		String cdate = imageload.getCdate();
		String mid = imageload.getMid();
		System.out.println(cdate);
		System.out.println(mid);
		System.out.println(num);
		return ResponseEntity.ok(ts.insertimage(file, cdate, mid, num));
	}
	@PostMapping("/api/getImage")
	public ResponseEntity<Resource> getimage(@RequestBody ImageLoad imageload) throws MalformedURLException{
		System.out.println(imageload);
		String mid = imageload.getMid();
	    String cdate = imageload.getCdate();
	    int num = imageload.getNum();
	    System.out.println(mid + cdate);

				
		return ts.getimage(mid, cdate, num);

	}
	
	@PostMapping("/api/insertone")
	public ResponseEntity<String> insertone(@RequestBody Todolist tl) {
		List<Todolist> todoList = new ArrayList<>();
		todoList.add(tl);
		return ts.insertTodo(todoList);
	}
	@PostMapping("/api/delete")
	public List<Todolist> delete(@RequestBody Todolist tl){
		List<Todolist> todoList = new ArrayList<>();
		todoList.add(tl);
		return ts.deleteTodo(todoList);
	}
	@PostMapping("/api/update")
	public ResponseEntity<List<Todolist>> update(@RequestBody Todolist tl){
		List<Todolist> todoList = new ArrayList<>();
		todoList.add(tl);
		ts.updateTodo(todoList);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Contrl-Allow-Origin","*");
//		json 형태로 담기 위해 memberlist에 담음
		return ResponseEntity.ok().headers(headers).body(todoList);
	}
	
	@PostMapping("/api/insertMembers")
	public ResponseEntity<String> insertMembers(@RequestBody Members mb){
		//list는 필요없음
		List<Members> memberList = new ArrayList<>();
		String mid = mb.getMid();
		System.out.println(mid);
		memberList.add(mb);

		return ts.insertMembers(mb, mid, memberList);
	}
	
	
	@PostMapping("/api/login")
	public ResponseEntity<Map<String,String>> getMembers(@RequestBody Members mb){
	    String id = mb.getMid();
	    String password = mb.getPassword();
	    System.out.println(id + password);
	    return ts.getMembers(mb, id, password);
	}
	@PostMapping("/api/success")
	public ResponseEntity<String> success(@RequestBody Todolist tl){
		Long id = tl.getId();
		String success = tl.getSuccess();
		return ts.successTodo(tl, id, success);
	}
	@PostMapping("/api/findlogin")
	public ResponseEntity<String> findlogin(@RequestBody Members mb){
		
		String memail = mb.getMemail();
		System.out.println(memail);
		return ts.findlogin(mb);
	}
	@PostMapping("/api/authenInfo")
	public ResponseEntity<Map<String,String>> authenInfo(@RequestBody Members mb){
		String memail = mb.getMemail();
		return ts.authenInfo(mb);
	}
	@PostMapping("/api/updateInfo")
	public ResponseEntity<String> updateInfo(@RequestBody Members mb){
		String memail = mb.getMemail();
		return ts.updateInfo(mb);
	}

}
