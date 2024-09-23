package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.PhonebookService;
import com.javaex.util.JsonResult;
import com.javaex.vo.PersonVo;

@RestController
public class PhonebookController {

	@Autowired
	private PhonebookService phonebookService;

	/* 리스트 가져오기 */
	@GetMapping("/api/persons")
	public List<PersonVo> getList() {
		System.out.println("PhonebookController.getList()");

		List<PersonVo> personList = phonebookService.exeGetPersonList();

		return personList;
	}

	/* 등록 */
	@PostMapping("/api/persons")
	public int addPerson(@RequestBody PersonVo personVo) {
		System.out.println("PhonebookController.addPerson()");

		int count = phonebookService.exeWritePerson(personVo);

		return count;
	}

	/* 삭제 */
	@DeleteMapping("/api/persons/{no}")
	public JsonResult delPerson(@PathVariable(value = "no") int no) {
		System.out.println("PhonebookController.delPerson()");
		System.out.println(no);

		int count = phonebookService.exePersonDelete(no);

		if (count != 1) { // 삭제 실패
			return JsonResult.fail("해당번호가 없습니다");
		} else { // 삭제 성공
			return JsonResult.success(count);
		}
	}

	/* 수정폼 - 사람 1명 정보 가져오기 */
	@GetMapping("/api/persons/{no}")
	public JsonResult getPerson(@PathVariable(value = "no") int personId) {
		System.out.println("PhonebookController.getPerson()");

		PersonVo personVo = phonebookService.exeEditForm(personId);

		if (personVo == null) {
			return JsonResult.fail("해당번호가 없습니다.");
		} else {
			return JsonResult.success(personVo);
		}
	}

	/* 수정 */
	@PutMapping("api/persons/{no}")
	public JsonResult updatePerson(@PathVariable(value = "no") int personId, @RequestBody PersonVo personVo) {
		System.out.println("PhonebookController.updatePerson()");

		personVo.setPersonId(personId);
		
		int count = phonebookService.exePersonUpdate(personVo);
		
		if(count == 0) {
			return JsonResult.fail("수정될 데이터가 없습니다.");
		} else {
			return JsonResult.success(personVo);
		}
	}

}
