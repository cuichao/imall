package com.eleven7.imall.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController {
	
	@RequestMapping(method = RequestMethod.GET)
	public void getCaptcha(HttpServletRequest request,HttpServletResponse response)
	{
	  	try {
			String certcode = this.getCertPic(0, 0, response.getOutputStream());
			request.getSession().setAttribute("captcha", certcode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/check",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkCaptcha(@RequestParam(value = "captcha",required=true)String captcha,HttpServletRequest request)
	{
	  	return CaptchaUtils.checkCaptcha(captcha, request);
	}
	
	
	//验证码图片中可以出现的字符集，可根据需要修改  
    private char mapTable[]={  
            'a','b','c','d','e','h',  
            'j','k','m','n','p','q',  
            'r','s','t','u','v','w',  
            'x','y','z','0','2','3',  
            '4','5','6','7','8','9'  
    };  
      
    private String getCertPic(int width, int height,OutputStream os) {  
        if(width<=0) {  
            width=60;  
        }  
        if(height<=0) {  
            height=20;  
        }  
          
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
        //获取图形上下文  
        Graphics g = image.getGraphics();  
        //设定背景颜色  
        g.setColor(new Color(0xDCDCDC));  
        g.fillRect(0, 0, width, height);  
        //画边框  
        g.setColor(Color.black);  
        g.drawRect(0, 0, width-1, height-1);  
        //随机产生的验证码  
        String strEnsure="";  
        //4代表4位验证码，如果要生成等多位的验证码 ，则加大数值  
          
        for(int i=0; i<4; i++) {  
            strEnsure += mapTable[(int)(mapTable.length*Math.random())];  
        }  
        //将验证码显示在图像中，如果要生成更多位的验证码，增加drawString语句  
        g.setColor(Color.black);  
        g.setFont(new Font("Atlantic Inline", Font.PLAIN,18));  
        String str = strEnsure.substring(0, 1);  
        g.drawString(str, 8, 17);  
         str = strEnsure.substring(1, 2);  
         g.drawString(str, 20, 15);  
         str = strEnsure.substring(2, 3);  
         g.drawString(str, 35, 18);  
         str = strEnsure.substring(3, 4);  
         g.drawString(str, 45, 15);  
         //随机产生10个干扰点  
           
         Random random = new Random();  
         for(int i=0; i<10; i++ ) {  
             int x = random.nextInt(width);  
             int y = random.nextInt(height);  
             g.drawOval(x, y, 1, 1);  
         }  
        //释放图形上下文  
         g.dispose();  
         try{  
             //输出图像到页面  
             ImageIO.write(image, "JPEG", os);  
         }catch(IOException e) {  
             return "";  
         }  
        return strEnsure;  
    }  

}
