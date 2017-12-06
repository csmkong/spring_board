package com.csmkong.myBoard.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.csmkong.myBoard.dao.BDao;
import com.csmkong.myBoard.dto.BDto;

public class BListCommand implements BCommand {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		System.out.println("execute ListCommand.");
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		System.out.println("get ArrayList.");
		model.addAttribute("list",dtos);
		System.out.println("insert ArrayList in list.");
		

	}

}
