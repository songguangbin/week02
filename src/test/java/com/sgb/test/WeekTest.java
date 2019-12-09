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
/*������*/
@SuppressWarnings("restriction")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext-redis.xml")
public class WeekTest {
	@Resource
	//redis������
	private RedisTemplate<String, Object> redisTemplate;
	@Test
	public void testDate(){
		List<User> ulist=new ArrayList<User>();
		//ѭ��10���
		for (int i = 0; i < 100000; i++) {
			//newһ��user����
			User user = new User();
			// IDʹ��1-5���˳���
			user.setId(i+1);
			//����ʹ��3���������ģ��
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			//�Ա���Ů��������ֵ�����
			Random random = new Random();
			String sex = random.nextBoolean()?"��":"Ů";
			user.setSex(sex);
			//�ֻ���13��ͷ+9λ�����ģ��
			String phone ="13"+ StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			//������3-20�������ĸ + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.comģ��
			int random2 = (int) (Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			//����Ҫģ��18-70��֮�䣬�����ڴ�1949�굽2001��֮��
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			
		}
		/*System.out.println("jdk�����л���ʽ");
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("jdk");
		long start = System.currentTimeMillis();
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");*/
		/*System.out.println("json�����л���ʽ");
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("json");
		long start = System.currentTimeMillis();
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");*/
		System.out.println("hash�����л���ʽ");
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
		long start = System.currentTimeMillis();
	    boundHashOps.put("hash", ulist);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");
		
		
	}

}
