package com.duodian.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duodian.common.RedisConfig;
import com.duodian.dao.UserDao;
import com.duodian.domain.bean.User;
import com.duodian.utils.Qrcode;

import redis.clients.jedis.Jedis;

@Controller
public class PageController {

	@Autowired 
	RedisConfig redisConfig;
	
	@Resource
	UserDao userDao;
	
	@RequestMapping(value = {"/","/index"})
	public String index(){
		try (Jedis jedis  = redisConfig.redisPoolFactory().getResource()){
			jedis.set("test", "redistest");
			System.out.println(jedis.get("test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
      User user = userDao.findByEmail("ddd");
      if (user != null) {
        String userId = String.valueOf(user.getId());
        System.out.println(userId);
      }
		
		return "index";
	}
	
	@RequestMapping(value = "/socket")
	public String socket(){
		
		return "socket";
	}
	
	@RequestMapping(value = "/socket2")
	public String socket2(){
		
		return "socket2";
	}
	/**  
     * 根据URL生成二维码  
     * @param model  
     * @param request  
     * @param response  
     */  
    @RequestMapping("/powerInvite")  
    public void getQRCode(ModelMap model,HttpServletRequest request,  
                          HttpServletResponse response) { 
        String code = request.getParameter("code");
        //二维码图片输出流  
        OutputStream out = null;  
        try{  
            response.setContentType("image/jpeg;charset=UTF-8");  
            BufferedImage image = Qrcode.createQRCode(code);  
            //实例化输出流对象  
            out = response.getOutputStream();  
            //画图  
            ImageIO.write(image, "png", response.getOutputStream());  
            out.flush();  
            out.close();  
        }catch (Exception e){  
            e.printStackTrace();  
        }finally {  
            try{  
                if (null != response.getOutputStream()) {  
                    response.getOutputStream().close();  
                }  
                if (null != out) {  
                    out.close();  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }  
    }
    public static void main(String args[]){
		 String[] nums={"1","2","3","4"};
		 
		 for(int i=0;i<nums.length;i++){
			 System.out.println(nums[i]);
			 
			 
			 
		 }
		 
	}
}
