package com.eleven7.imall.common.mail;

import java.util.HashSet;
import java.util.Set;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.common.CommonConfig;
import com.eleven7.imall.common.MD5;

public class MailTemplateUtils {
	
	public static MailTemplate getTemplate4ActiveCode(Userinfo ui,String activecodeUrl)
	{
		MailTemplate mt = new MailTemplate();
		Set<String> toSet = new HashSet<String>();
		toSet.add(ui.getEmail());
		mt.setTo(toSet);
		mt.setFrom(CommonConfig.get("mail.from"));
		mt.setSubject(CommonConfig.get("register.subject"));
		String body ="<b>请访问下面的链接,激活你在IMALL的帐号:)</b><br><a href='%s'>%s</a>";
		body = String.format(body, activecodeUrl,activecodeUrl);
		mt.setBody(body);
		return mt;
		
	}

}
