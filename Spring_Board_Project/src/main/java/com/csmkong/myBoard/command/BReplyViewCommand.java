package com.csmkong.myBoard.command;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.csmkong.myBoard.dao.BDao;
import com.csmkong.myBoard.dto.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		String bId = request.getParameter("bId");
		
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.reply_view(bId);
		
		model.addAttribute("reply_view",dtos);
		

	}

}
