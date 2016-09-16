package me.aipao.api;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;


/**
 * 接口调用返回结果封装
 * @author 帮杰
 *
 */
public class Result {

	public static final int CODE_SUCCESS = 0;
	public static final int CODE_FAILURE = 1;
	public static final int CODE_UNAUTH = -1;
	
    private int code;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

	public int getCode() {
		return code;
	}

	public Result setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public Result setData(String data) {
		this.data = data;
		return this;
	}

	public void render(Controller c) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", data);
		c.renderJson(map);
	}
	
	public static Result create(int code, String msg, Object data) {
		return new Result(code, msg, data);
	}
	
	public static Result success(String msg) {
		return create(CODE_SUCCESS, msg, null);
	}
	
	public static Result success(String msg, Object data) {
		return create(CODE_SUCCESS, msg, data);
	}
	
	public static Result failure(String msg) {
		return create(CODE_FAILURE, msg, null);
	}
	
	public static Result failure(String msg, Object data) {
		return create(CODE_FAILURE, msg, data);
	}
	
	public static Result unauth(String msg) {
		return create(CODE_UNAUTH, msg, null);
	}
	
	public static Result unauth(String msg, Object data) {
		return create(CODE_UNAUTH, msg, data);
	}
}
