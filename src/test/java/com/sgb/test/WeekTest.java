package com.sgb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgb.bean.User;
import com.sgb.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
/*测试类*/
@SuppressWarnings("restriction")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext-redis.xml")
public class WeekTest {
	@Resource
	//redis的依赖
	private RedisTemplate<String, Object> redisTemplate;
	@Test
	public void testDate(){
		List<User> ulist=new ArrayList<User>();
		//循环10万次
		for (int i = 0; i < 100000; i++) {
			//new一个user对象
			User user = new User();
			// ID使用1-5万的顺序号
			user.setId(i+1);
			//姓名使用3个随机汉字模拟
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			//性别在女和男两个值中随机
			Random random = new Random();
			String sex = random.nextBoolean()?"男":"女";
			user.setSex(sex);
			//手机以13开头+9位随机数模拟
			String phone ="13"+ StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			//邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			int random2 = (int) (Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			
		}
		/*System.out.println("jdk的序列化方式");
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("jdk");
		long start = System.currentTimeMillis();
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");*/
		/*System.out.println("json的序列化方式");
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("json");
		long start = System.currentTimeMillis();
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");*/
		System.out.println("hash的序列化方式");
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
		long start = System.currentTimeMillis();
	    boundHashOps.put("hash", ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");
		
		
	}

}
