/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.aipao.plugin.sms;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * @author 帮杰
 *
 */
public class Sms {

	private static final Log logger = Log.getLog(Sms.class);
	
	public static interface Sign {

		/**
		 * 活动验证
		 */
		public static final String ACTIVITY_ACK = "活动验证";
		
		/**
		 * 变更验证
		 */
		public static final String CHANGE_ACK = "变更验证";

		/**
		 * 登录验证
		 */
		public static final String LOGIN_ACK = "登录验证";
		
		/**
		 * 注册验证
		 */
		public static final String REGISTER_ACK = "注册验证";
		
		/**
		 * 身份验证
		 */
		public static final String ID_VERIFICATION = "身份验证";

	}
	
	public static interface Code {
		/**
		 * 
			模板名称:
			身份验证验证码
			模板ID:
			SMS_2550278
			*模板内容:
			验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！
		 */
		public static final String ID_VERIFICATION = "SMS_2550278";
		
		/**
		 * 
			模板名称:
			登陆确认验证码
			模板ID:
			SMS_2550277
			*模板内容:
			验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。
		 */
		public static final String LOGIN_ACK = "SMS_2550277";

		/**
		 * 
			模板名称:
			登陆异常验证码
			模板ID:
			SMS_2550276
			*模板内容:
			验证码${code}，您正尝试异地登陆${product}，若非本人操作，请勿泄露。
		 */
		public static final String LOGIN_AMOMALY = "SMS_2550276";
		
		/**
		 * 
			模板名称:
			用户注册验证码
			模板ID:
			SMS_2550275
			*模板内容:
			验证码${code}，您正在注册成为${product}用户，感谢您的支持！
		 */
		public static final String USER_REGISTER = "SMS_2550275";
		
		/**
		 * 
			模板名称:
			活动确认验证码
			模板ID:
			SMS_2550274
			*模板内容:
			验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。
		 */
		public static final String ACTIVITY_ACK = "SMS_2550274";

		/**
		 * 
			模板名称:
			修改密码验证码
			模板ID:
			SMS_2550273
			*模板内容:
			验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。
		 */
		public static final String CHANGE_PASS = "SMS_2550273";
		
		/**
		 * 
			模板名称:
			信息变更验证码
			模板ID:
			SMS_2550272
			*模板内容:
			验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。
		 */
		public static final String SIGNIFICANT_CHANGE = "SMS_2550272";
	}
	
	public static class Para {

		private String code;
		private String product;
		private String item;
		
		public Para(String code,String product) {
			this.code = code;
			this.product = product;
		}
		
		public Para(String code,String product,String item) {
			this.code = code;
			this.product = product;
			this.item = item;
		}

		public String getCode() {
			return code;
		}

		public Para setCode(String code) {
			this.code = code;
			return this;
		}

		public String getProduct() {
			return product;
		}

		public Para setProduct(String product) {
			this.product = product;
			return this;
		}

		public String getItem() {
			return item;
		}

		public Para setItem(String item) {
			this.item = item;
			return this;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			if (code!=null) {
				sb.append("\"code\":"+"\""+code+"\",");
			}
			if (product!=null) {
				sb.append("\"product\":"+"\""+product+"\",");
			}
			if (item!=null) {
				sb.append("\"item\":"+"\""+item+"\",");
			}
			String s = sb.toString();
			s = s.endsWith(",")?s.substring(0, s.length()-1):s;
			s = s+"}";
			return s;
		}
		
	}
	
	private static String url = PropKit.use("config.txt").get("sms.url");
	private static String appkey = PropKit.use("config.txt").get("sms.appkey");
	private static String secret = PropKit.use("config.txt").get("sms.secret");
	
	public static boolean send(String sign,String templateCode,String para,String receiver) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName(sign);
		req.setSmsTemplateCode(templateCode);
		req.setSmsParam(para);
		req.setRecNum(receiver);
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			logger.info("短信发送报告："+rsp.getBody());
			return rsp.isSuccess();
		} catch (ApiException e) {
			logger.error("sms sending error", e);
		}
		return false;
	}
	
	public static boolean send(String sign,String templateCode,Para para,String receiver) {
		return send(sign, templateCode, para.toString(), receiver);
	}
	
	public static boolean send(String code,String receiver) {
		Para para =  new Para(code, "");
		return send(Sign.ID_VERIFICATION, Code.ID_VERIFICATION, para, receiver);
	}
	
	public static void main(String[] args) throws ApiException {
		String code = "123456";
		String phone = "17771427044";
		//System.out.println(sendRegisterVerifingCode(code,phone));
		System.out.println(send(code,phone));
	}
	
}
